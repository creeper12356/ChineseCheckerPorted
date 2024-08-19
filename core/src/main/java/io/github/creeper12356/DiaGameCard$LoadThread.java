/*
 * Decompiled with CFR 0.152.
 */
class DiaGameCard.LoadThread
implements Runnable {
    DiaGameCard.LoadThread() {
    }

    public void run() {
        DiaGameCard.this.init();
        DiaGameCard.this.initComp = true;
    }
}
