package dev.felnull.fnjl.util;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class FNRuntimeUtil {
    private static final Map<Runnable, Long> lastTimes = new HashMap<>();

    public static void oneDayClockTimer(int hours, int minutes, Runnable runnable) {
        oneDayClockTimer(hours, minutes, runnable, false);
    }

    public static void oneDayClockTimer(int hours, int minutes, Runnable runnable, boolean daemon) {
        Timer timer = new Timer(daemon);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long lastTime = 0;
                if (lastTimes.containsKey(runnable))
                    lastTime = lastTimes.get(runnable);

                if (System.currentTimeMillis() - lastTime > 60 * 60 * 1000) {
                    Date date = new Date();
                    if (date.getHours() == hours && date.getMinutes() == minutes) {
                        runnable.run();
                        lastTimes.put(runnable, System.currentTimeMillis());
                    }
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    /**
     * マルチ非同期実行
     *
     * @param runnable  実行
     * @param runnables 実行
     * @return CompletableFuture
     */
    public static CompletableFuture<Void> multipleRun(Runnable runnable, Runnable... runnables) {
        return multipleRun(null, -1, runnable, runnables);
    }

    /**
     * マルチ非同期実行
     *
     * @param threadName スレッド名
     * @param runnable   実行
     * @param runnables  実行
     * @return CompletableFuture
     */
    public static CompletableFuture<Void> multipleRun(String threadName, Runnable runnable, Runnable... runnables) {
        return multipleRun(threadName, -1, runnable, runnables);
    }

    /**
     * @param max       最大同時thread
     * @param runnable  実行
     * @param runnables 実行
     * @return CompletableFuture
     */
    public static CompletableFuture<Void> multipleRun(int max, Runnable runnable, Runnable... runnables) {
        return multipleRun(null, max, runnable, runnables);
    }

    /**
     * マルチ非同期実行
     *
     * @param threadName スレッド名
     * @param max        最大同時thread
     * @param runnable   実行
     * @param runnables  実行
     * @return CompletableFuture
     */
    public static CompletableFuture<Void> multipleRun(String threadName, int max, Runnable runnable, Runnable... runnables) {
        if (max <= 0)
            max = 1 + runnables.length;
        int mx = max;
        return CompletableFuture.runAsync(() -> {
            Object obj = new Object();
            AtomicInteger fin = new AtomicInteger();
            AtomicInteger maxFin = new AtomicInteger();
            List<Thread> runners = new ArrayList<>();
            runners.add(new Thread(() -> {
                runnable.run();
                synchronized (obj) {
                    fin.getAndIncrement();
                    if (fin.get() >= maxFin.get())
                        obj.notifyAll();
                }
            }));
            for (Runnable run : runnables) {
                runners.add(new Thread(() -> {
                    run.run();
                    synchronized (obj) {
                        fin.getAndIncrement();
                        if (fin.get() >= maxFin.get())
                            obj.notifyAll();
                    }
                }));
            }
            if (threadName != null) {
                for (int i = 0; i < runners.size(); i++) {
                    runners.get(i).setName(threadName + "-" + i);
                }
            }
            do {
                List<Thread> rms = new ArrayList<>();
                int ct = 0;
                for (Thread runner : runners) {
                    rms.add(runner);
                    ct++;
                    if (ct >= mx)
                        break;
                }
                maxFin.set(ct);
                rms.forEach(Thread::start);
                runners.removeAll(rms);
                synchronized (obj) {
                    try {
                        if (fin.get() < maxFin.get())
                            obj.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                fin.set(0);
            } while (!runners.isEmpty());
        });
    }

}
