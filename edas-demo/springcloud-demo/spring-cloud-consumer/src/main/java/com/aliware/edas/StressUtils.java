package com.aliware.edas;

import java.util.concurrent.TimeUnit;

public class StressUtils {
    private static volatile boolean started = false;
    private static volatile long delta = 500;

    public static void start() {
        if (started) {
            return;
        }

        halfCoreUsage();
    }

    public static boolean stop() {
        boolean alreadyStarted = started;
        started = false;

        return alreadyStarted;
    }

    private static void halfCoreUsage() {
        started = true;

        long begin = System.currentTimeMillis();

        for (;started;) {
            long slices = 10000;
            while (slices -- > 0) {

            };

            if (System.currentTimeMillis() - begin < delta) {
                continue;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(delta);
            } catch (InterruptedException e) {
            }

            begin = System.currentTimeMillis();
        }
    }
}
