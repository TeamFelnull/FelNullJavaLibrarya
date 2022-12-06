package dev.felnull.fnjl.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FNRuntimeUtil {
    private static final Map<Runnable, Long> lastTimes = new HashMap<>();
    private static final List<Timer> timers = new ArrayList<>();

    /**
     * １日ごとに決まった時間に実行
     *
     * @param timer    タイマー
     * @param executor エクスキューター
     * @param hours    時
     * @param minutes  分
     * @param runnable 実行
     */
    public static void loopDayRunner(@NotNull Timer timer, @NotNull Executor executor, @Range(from = 0, to = 23) int hours, @Range(from = 0, to = 59) int minutes, @NotNull Runnable runnable) {
        loopDayRunner(timer, executor, hours, minutes, 0, runnable);
    }

    /**
     * １日ごとに決まった時間に実行
     *
     * @param timer    タイマー
     * @param executor エクスキューター
     * @param hours    時
     * @param minutes  分
     * @param second   秒
     * @param runnable 実行
     */
    public static void loopDayRunner(@NotNull Timer timer, @NotNull Executor executor, @Range(from = 0, to = 23) int hours, @Range(from = 0, to = 59) int minutes, @Range(from = 0, to = 59) int second, @NotNull Runnable runnable) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(Calendar.HOUR_OF_DAY) > hours || (calendar.get(Calendar.HOUR_OF_DAY) == hours && calendar.get(Calendar.MINUTE) > minutes) || (calendar.get(Calendar.HOUR_OF_DAY) == hours && calendar.get(Calendar.MINUTE) == minutes && calendar.get(Calendar.SECOND) >= second))
            calendar.add(Calendar.DATE, 1);

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, second);

        loopDayRunner_(calendar, timer, executor, runnable);
    }

    private static void loopDayRunner_(Calendar calendar, Timer timer, Executor executor, Runnable runnable) {
        dayRunner(timer, executor, calendar.getTime(), () -> {
            calendar.add(Calendar.DATE, 1);
            loopDayRunner_(calendar, timer, executor, runnable);
            runnable.run();
        });
    }

    /**
     * 指定の日付時刻に実行
     *
     * @param timer    タイマー
     * @param executor エクスキューター
     * @param date     日付時刻
     * @param runnable 実行
     */
    public static void dayRunner(@NotNull Timer timer, @NotNull Executor executor, @NotNull Date date, @NotNull Runnable runnable) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                CompletableFuture.runAsync(runnable, executor);
            }
        };
        timer.schedule(task, date);
    }

    /**
     * 指定時間ごとに繰り返して実行
     *
     * @param timer         タイマー
     * @param executor      エクスキューター
     * @param delayTimeUnit 時間単位
     * @param delayDuration 間隔
     * @param runnable      実行
     */
    public static void loopRunner(@NotNull Timer timer, @NotNull Executor executor, @NotNull TimeUnit delayTimeUnit, long delayDuration, @NotNull Runnable runnable) {
        loopRunner(timer, executor, false, delayTimeUnit, delayDuration, runnable);
    }

    /**
     * 指定時間ごとに繰り返して実行
     *
     * @param timer         タイマー
     * @param executor      エクスキューター
     * @param startDelay    最初に間隔を開けるかどうか
     * @param delayTimeUnit 時間単位
     * @param delayDuration 間隔
     * @param runnable      実行
     */
    public static void loopRunner(@NotNull Timer timer, @NotNull Executor executor, boolean startDelay, @NotNull TimeUnit delayTimeUnit, long delayDuration, @NotNull Runnable runnable) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                CompletableFuture.runAsync(runnable, executor);
            }
        };
        long time = delayTimeUnit.toMillis(delayDuration);
        timer.scheduleAtFixedRate(task, startDelay ? time : 0, time);
    }

    /**
     * 指定時間後に遅れて実行
     *
     * @param timer         タイマー
     * @param executor      エクスキューター
     * @param delayTimeUnit 時間単位
     * @param delayDuration 遅れ
     * @param runnable      実行
     */
    public static void delayRunner(@NotNull Timer timer, @NotNull Executor executor, @NotNull TimeUnit delayTimeUnit, long delayDuration, @NotNull Runnable runnable) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                CompletableFuture.runAsync(runnable, executor);
            }
        };
        timer.schedule(task, delayTimeUnit.toMillis(delayDuration));
    }

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
        timers.add(timer);
    }

    /**
     * マルチ非同期実行
     *
     * @param runnable  実行
     * @param runnables 実行
     * @return CompletableFuture
     */
    @Deprecated
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
    @Deprecated
    public static CompletableFuture<Void> multipleRun(String threadName, Runnable runnable, Runnable... runnables) {
        return multipleRun(threadName, -1, runnable, runnables);
    }

    /**
     * @param max       最大同時thread
     * @param runnable  実行
     * @param runnables 実行
     * @return CompletableFuture
     */
    @Deprecated
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
    @Deprecated
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

    /**
     * このクラスで登録したタスクをすべて停止
     */
    public static void kill() {
        for (Timer timer : timers) {
            timer.cancel();
            timer.purge();
        }
        timers.clear();
        lastTimes.clear();
    }

}
