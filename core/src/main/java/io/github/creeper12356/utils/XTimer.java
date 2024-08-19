package io.github.creeper12356.utils;
/*
 * Decompiled with CFR 0.152.
 */

public class XTimer
implements Runnable {
    private Thread runner;
    private boolean isRun;
    private long time;
    private Object lockObject;
    private boolean isActivate;
    private boolean isCancel;
    private boolean isWait;
    private int loopCount;
    private int currentLoopCount;
    private XTimerListener aXTimerListener;

    public XTimer() {
        this(0L);
    }

    public XTimer(long l) {
        this.time = l;
        this.lockObject = new Object();
        this.loopCount = 0;
        this.currentLoopCount = 0;
    }

    public void setLoopCount(int n) {
        this.loopCount = n;
        this.currentLoopCount = 0;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long l) {
        this.time = l;
    }

    public void setPriority(int n) {
        this.runner.setPriority(n);
    }

    public void setTimerListener(XTimerListener xTimerListener) {
        this.aXTimerListener = null;
        this.aXTimerListener = xTimerListener;
    }

    public void pause() {
        this.isActivate = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resume() {
        Object object;
        this.isActivate = true;
        if (this.isWait) {
            object = this.runner;
            synchronized (object) {
                this.isCancel = true;
                this.runner.notify();
            }
        }
        object = this.lockObject;
        synchronized (object) {
            this.lockObject.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void cancel() {
        this.isCancel = true;
        this.isActivate = false;
        if (this.isWait) {
            Thread thread = this.runner;
            synchronized (thread) {
                this.runner.notify();
            }
            try {
                Thread.sleep(100L);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public boolean isActivate() {
        return this.isActivate && this.isRun;
    }

    public void start() {
        if (this.runner == null) {
            this.runner = new Thread(this);
            this.isRun = true;
            this.runner.start();
            this.isActivate = true;
        } else {
            this.stop();
            this.start();
        }
    }

    public void stop() {
        if (this.runner != null) {
            this.cancel();
            this.isRun = false;
            this.isActivate = false;
            try {
                this.runner.join();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.runner = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (this.isRun) {
            Object object = this.lockObject;
            synchronized (object) {
                try {
                    while (!this.isActivate) {
                        this.lockObject.wait(100000L);
                    }
                    this.isWait = true;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            object = this.runner;
            synchronized (object) {
                try {
                    if (this.time > 0L) {
                        this.runner.wait(this.time);
                    }
                    XTimer xTimer = this;
                    xTimer.runner.yield();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.isWait = false;
            if (this.isCancel) {
                this.isCancel = false;
                continue;
            }
            this.performed();
            if (this.loopCount <= 0) continue;
            ++this.currentLoopCount;
            if (this.currentLoopCount < this.loopCount) continue;
            this.isActivate = false;
        }
    }

    private void performed() {
        if (this.aXTimerListener != null) {
            this.aXTimerListener.performed(this);
        }
    }
}
