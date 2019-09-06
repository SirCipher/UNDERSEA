package com.type2labs.undersea.utilities.lang;

/**
 * A task which will be rerun if there is a failure that occurs
 */
public abstract class ReschedulableTask implements Runnable {

    public static int maxRunCount = 3;

    private int runCount;
    private long rerunTimeWindow = 1000L;

    public int getRunCount() {
        return runCount;
    }

    public void incrementRunCount() {
        this.runCount++;
    }

    public long getRerunTimeWindow() {
        return rerunTimeWindow;
    }

    public void setRerunTimeWindow(long rerunTimeWindow) {
        this.rerunTimeWindow = rerunTimeWindow;
    }

    @Override
    public final void run() {
        this.innerRun();
    }

    public abstract void innerRun();

    public static ReschedulableTask wrap(Runnable runnable) {
        return new ReschedulableTask() {
            @Override
            public void innerRun() {
                runnable.run();
            }
        };
    }

}
