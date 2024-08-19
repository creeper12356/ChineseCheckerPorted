package io.github.creeper12356;

public class Animation {
    int aniType;
    int aniFrame;
    int aniMaxFrame;
    int aniSumTime;
    int aniFrameDelay;
    boolean aniRepeat;
    boolean aniEnd;
    static int pause;

    public void init(int n, int n2, boolean bl) {
        this.aniFrame = 0;
        this.aniMaxFrame = n;
        this.aniSumTime = 0;
        this.aniFrameDelay = n2;
        this.aniRepeat = bl;
        this.aniEnd = false;
    }

    public void init(int n, int n2, int n3, boolean bl) {
        this.aniType = n;
        this.aniFrame = 0;
        this.aniMaxFrame = n2;
        this.aniSumTime = 0;
        this.aniFrameDelay = n3;
        this.aniRepeat = bl;
        this.aniEnd = false;
    }

    public int frameProcess() {
        if (this.aniEnd) {
            return 0;
        }
        this.aniSumTime += 100;
        if (this.aniSumTime >= this.aniFrameDelay) {
            this.aniSumTime = 0;
            ++this.aniFrame;
            if (this.aniFrame == this.aniMaxFrame) {
                if (this.aniRepeat) {
                    this.aniFrame = 0;
                    return 1;
                }
                --this.aniFrame;
                this.aniEnd = true;
                return 0;
            }
        }
        return 1;
    }

    public int getFrame() {
        return this.aniFrame;
    }

    public int getType() {
        return this.aniType;
    }

    public boolean isEnd() {
        return this.aniEnd;
    }
}
