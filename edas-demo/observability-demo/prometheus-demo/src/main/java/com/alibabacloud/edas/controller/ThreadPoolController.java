package com.alibabacloud.edas.controller;

import com.alibabacloud.edas.exporter.PrometheusExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController()
public class ThreadPoolController implements InitializingBean {
    private ExecutorService threadPool;

    private static Logger logger = LoggerFactory.getLogger(ThreadPoolController.class);

    @Autowired
    private PrometheusExporter monitor;


    /**
     * Execute a runnable
     *
     * @return
     */
    @RequestMapping("/execute")
    public String execute() {
        threadPool.execute(() -> {
            logger.info("Invoke inside thread pool");
        });

        return "ok";
    }

    /**
     * Init method
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        threadPool = new ThreadPoolExecutor(
                4, 100, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

        monitor.monitor((ThreadPoolExecutor) threadPool, "requests-thread-pool");
    }
}
