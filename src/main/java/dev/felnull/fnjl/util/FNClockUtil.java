package dev.felnull.fnjl.util;

import java.util.*;

public class FNClockUtil {
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
}
