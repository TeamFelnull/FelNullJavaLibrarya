package dev.felnull.fnjl.debug;


/**
 * 処理時間計測
 *
 * @author MORIMORI0317
 */
public class ProcessTimeMeasure {
    private long elapsedTime;
    private long lastElapsedTime;
    private int sampleCount;
    private long lastPrintTime;
    private long lastResetTime;

    public void process(Runnable runnable) {
        long st = System.nanoTime();
        runnable.run();
        lap(System.nanoTime() - st);
    }

    public void lap(long elapsed) {
        elapsedTime += elapsed;
        lastElapsedTime = elapsed;
        sampleCount++;
    }

    public void reset() {
        elapsedTime = 0;
        sampleCount = 0;
        lastResetTime = System.currentTimeMillis();
    }

    public MeasureResult getResult() {
        if (elapsedTime <= 0 || sampleCount <= 0)
            throw new IllegalStateException("Unmeasured");
        return new MeasureResult(elapsedTime, sampleCount, lastElapsedTime);
    }

    public void printResult(long time) {
        if (System.currentTimeMillis() - lastPrintTime >= time) {
            System.out.println(getResult());
            lastPrintTime = System.currentTimeMillis();
        }
    }

    public void printResult(long time, long resetTime) {
        printResult(time);
        if (System.currentTimeMillis() - lastResetTime >= resetTime) {
            reset();
            lastResetTime = System.currentTimeMillis();
        }
    }

    public static class MeasureResult {
        private final long elapsed;
        private final long sampleCount;
        private final long lastElapsedTime;

        public MeasureResult(long elapsed, long sampleCount, long lastElapsedTime) {
            this.elapsed = elapsed;
            this.sampleCount = sampleCount;
            this.lastElapsedTime = lastElapsedTime;
        }

        public double getAverage() {
            return (double) elapsed / (double) sampleCount;
        }

        @Override
        public String toString() {
            double avg = getAverage();
            return String.format("Elapsed Time: %.3fμs %06dns, Average Time: %.3fμs %.3fns, Count %s, Last Lap: %.3fμs %06dns", elapsed / 1000000d, elapsed, avg / 1000000d, avg, sampleCount, lastElapsedTime / 1000000d, lastElapsedTime);
        }

        public long getElapsed() {
            return elapsed;
        }

        public long getSampleCount() {
            return sampleCount;
        }

        public long getLapTime() {
            return lastElapsedTime;
        }
    }
}
