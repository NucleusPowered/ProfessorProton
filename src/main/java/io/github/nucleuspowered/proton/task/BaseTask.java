package io.github.nucleuspowered.proton.task;

public abstract class BaseTask implements Runnable {

    protected boolean suppressWarnings;

    public BaseTask(boolean suppressWarnings) {
        this.suppressWarnings = suppressWarnings;
    }

}
