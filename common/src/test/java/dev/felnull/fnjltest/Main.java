package dev.felnull.fnjltest;

import dev.felnull.fnjl.util.FNRuntimeUtil;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        FNRuntimeUtil.loopDayRunner(new Timer(), executorService, 19, 38, 20, () -> {
            System.out.println("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ: " + new Date());
        });

        Thread.sleep(1000 * 114514);
    }
}
