package com.alibabacloud.edas.exporter;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongGauge;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.prometheus.PrometheusHttpServer;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Component
public class PrometheusExporter implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(PrometheusExporter.class);

    private static final String INSTRUMENTATION_SCOPE = "io.cloudapp.threadpool";

    @Value("${cloudapp.prometheus.exporter.port:9464}")
    private int prometheusPort = 9464;

    private static final AttributeKey<String> KEY1 =
            AttributeKey.stringKey("thread_pool_name");

    private Meter meter;

    private LongGauge activeCount;

    private LongGauge completedTaskCount;

    private LongGauge corePoolSize;

    private LongGauge poolSize;

    private LongGauge largestPoolSize;

    private LongGauge maximumPoolSize;

    private LongGauge taskCount;

    private Collection<ThreadPoolMonitorObject> monitoringThreadPools =
            new ArrayList();

    private final Lock readLock;

    private final Lock writeLock;

    public PrometheusExporter() {
        // unfair read/write locks.
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(false);

        readLock = rwl.readLock();
        writeLock = rwl.writeLock();
    }

    static class ThreadPoolMonitorObject {
        final SoftReference<ThreadPoolExecutor> ref;

        final Attributes attrs;

        ThreadPoolMonitorObject(ThreadPoolExecutor executor, String poolName) {
            ref = new SoftReference<>(executor);
            attrs = Attributes.of(KEY1, poolName);
        }

        boolean isStale() {
            return ref.get() == null;
        }
    }


    /**
     * do the statics job every second.
     */
    @Scheduled(fixedDelay = 1000)
    public void run() {
        filterStaleObjects();

        runAfterFilterStaled();
    }

    private void runAfterFilterStaled() {
        if (monitoringThreadPools.isEmpty()) {
            return;
        }

        readLock.lock();
        try {
            monitoringThreadPools.stream().forEach(
                    this::tryAddThreadPoolCounter
            );
        } finally {
            readLock.unlock();
        }
    }

    private void filterStaleObjects() {
        Collection<ThreadPoolMonitorObject> filtered = monitoringThreadPools
                .stream()
                .filter(object -> ! object.isStale())
                .collect(Collectors.toList())
        ;

        if (filtered.size() == monitoringThreadPools.size()) {
            return;
        }

        try {
            writeSafeBlock(() -> monitoringThreadPools = filtered);
        } catch (Exception e) {
            logger.warn("Reset monitoring objects after filtered error.", e);
        }

    }

    /**
     * Do the action inside a write lock.
     *
     * @param callable
     * @throws Exception
     */
    private void writeSafeBlock(Callable callable) throws Exception {
        writeLock.lock();

        try {
            callable.call();
        } finally {
            writeLock.unlock();
        }
    }

    public void monitor(ThreadPoolExecutor executor, String poolName) {
        ThreadPoolMonitorObject obj = new ThreadPoolMonitorObject(
                executor, poolName);

        try {
            writeSafeBlock(() -> monitoringThreadPools.add(obj));
        } catch (Exception e) {
            logger.warn("Error adding thread pool objects into the list.", e);
        }
    }

    protected boolean tryAddThreadPoolCounter(ThreadPoolMonitorObject object) {
        ThreadPoolExecutor executor = object.ref.get();
        if (executor == null) {
            return false;
        }

        Attributes attrs = object.attrs;

        activeCount.set(executor.getActiveCount(), attrs);
        completedTaskCount.set(executor.getCompletedTaskCount(), attrs);
        corePoolSize.set(executor.getCorePoolSize(), attrs);
        poolSize.set(executor.getPoolSize(), attrs);
        largestPoolSize.set(executor.getLargestPoolSize(), attrs);
        maximumPoolSize.set(executor.getMaximumPoolSize(), attrs);
        taskCount.set(executor.getTaskCount(), attrs);

        return true;
    }

    private Attributes getAttributes(String poolName) {
        return Attributes.of(KEY1, poolName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MetricReader reader = PrometheusHttpServer.builder()
                .setPort(prometheusPort)
                .build();

        SdkMeterProvider provider = SdkMeterProvider.builder()
                .registerMetricReader(reader)
                .setResource(Resource.getDefault())
                .build();

        logger.info("Prometheus metrics exporter port listening on port: {}, " +
                "you can change it via setting property with key: " +
                "cloudapp.prometheus.exporter.port", prometheusPort);

        meter = provider.meterBuilder(INSTRUMENTATION_SCOPE).build();

        activeCount = meter.gaugeBuilder("active_count")
                .setDescription("Thread pool active count")
                .ofLongs()
                .build();

        completedTaskCount = meter.gaugeBuilder("completed_task_count")
                .setDescription("Thread pool completed task count")
                .ofLongs()
                .build();

        corePoolSize = meter.gaugeBuilder("core_pool_size")
                .setDescription("Thread pool core pool size")
                .ofLongs()
                .build();

        poolSize = meter.gaugeBuilder("pool_size")
                .setDescription("Thread pool pool size")
                .ofLongs()
                .build();

        largestPoolSize = meter.gaugeBuilder("largest_pool_size")
                .setDescription("Thread pool largest pool size")
                .ofLongs()
                .build();

        maximumPoolSize = meter.gaugeBuilder("maximum_pool_size")
                .setDescription("Thread pool maximum pool size")
                .ofLongs()
                .build();

        taskCount = meter.gaugeBuilder("task_count")
                .setDescription("Thread pool task count")
                .ofLongs()
                .build();
    }
}
