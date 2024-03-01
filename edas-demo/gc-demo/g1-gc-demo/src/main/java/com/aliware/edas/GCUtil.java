package com.aliware.edas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import com.aliware.edas.models.Objects;

public class GCUtil {

    private static List<Object> OLD_OBJECTS = new ArrayList<>();

    private static ScheduledExecutorService SCHED = new ScheduledThreadPoolExecutor(200);

    private static List generate1MYoungObjects() {
        List tmp = Objects.OneKObject.toList(1024);

        return tmp;
    }

    private static void generateOldObjects() {
        // 1023 KB, Not a "BIG" Object
        OLD_OBJECTS.add(new Objects(1023 * 1024));

        System.out.println(new Date() +
                " ==> Add an old object, current size: " + OLD_OBJECTS.size()
        );
    }

    private static void generateBigObject() {
        Object o = new Objects.OneMObject();

        System.out.println(new Date() +
                " ==> Big Object: " + o.toString()
        );
    }

    private static void removeOldObjects() {
        // 1023 KB, Not a "BIG" Object
        System.out.println(new Date() +
                " ==> Remove an old object, current size before remove: " +
                OLD_OBJECTS.size()
        );

        if (OLD_OBJECTS.size() <= 0) {
            return;
        }

        OLD_OBJECTS.remove(0);
    }

    static class YoungGCRunner implements Runnable {

        int speed = 1024;

        YoungGCRunner(int speed) {
            this.speed = speed;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0 ; i < speed ; i ++) {
                generate1MYoungObjects();
            }

            System.out.println(new Date() +
                    " ==>  " + (speed) + "M new Objects generated, time spend: "
                    + (System.currentTimeMillis() - start) + "ms"
            );
        }
    }

    static class PromotionRunner implements Runnable {
        int speed = 1024;

        PromotionRunner(int speed) {
            this.speed = speed;
        }

        @Override
        public void run() {
            for (int i = 0 ; i < speed ; i ++) {
                generateOldObjects();
            }
        }
    }

    static class RemoveRunner implements Runnable {
        @Override
        public void run() {
            removeOldObjects();
        }
    }

    static class BigObjectRunner implements Runnable {
        @Override
        public void run() {
            generateBigObject();
        }
    }


    public static void startScheduling() {
        SCHED.scheduleAtFixedRate(new YoungGCRunner(212 * 1024),
                1, 1, TimeUnit.SECONDS);

        SCHED.scheduleAtFixedRate(new YoungGCRunner(212 * 1024),
                1, 1, TimeUnit.SECONDS);

        SCHED.scheduleAtFixedRate(new PromotionRunner(1),
                1, 20, TimeUnit.SECONDS);

        SCHED.scheduleAtFixedRate(new RemoveRunner(),
                1, 1, TimeUnit.MINUTES);

        SCHED.scheduleAtFixedRate(new BigObjectRunner(),
                1, 10, TimeUnit.SECONDS);

    }

    public static void stopScheduling() {
        SCHED.shutdownNow();
    }

}
