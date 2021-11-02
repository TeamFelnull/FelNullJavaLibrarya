package dev.felnull;

import dev.felnull.fnjl.ProgressLogger;

public class TestMain {
    public static void main(String[] args) throws Exception {
        ProgressLogger pl = new ProgressLogger(10, 100, ProgressLogger.BarType.CUBE);
        pl.setStartStr(null);
        pl.setEndStr(null);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            pl.printProgress(i + 1);
        }

    }
}
