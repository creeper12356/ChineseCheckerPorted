package io.github.creeper12356.utils;
/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 *  javax.microedition.media.PlayerListener
 */
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

public class MMFPlayer
implements PlayerListener,
Runnable {
    private Thread runner;
    private boolean isRun;
    private Object lockObject;
    private boolean isActivate;
    Player sndPlayer;
    Manager sndManager;
    private int currentVolume;
    private int currentID;
    private int MAX_VOLUME;
    private static final String MEDIA_TYPE = "mmf";
    private boolean isLoop;

    public MMFPlayer() {
        try {
            this.MAX_VOLUME = 5;
            this.currentVolume = 2;
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.lockObject = new Object();
        this.runner = new Thread(this);
        this.isRun = true;
        this.isLoop = false;
        this.isActivate = false;
        this.currentID = -1;
    }

    public void setData(byte[] byArray, int n) {
    }

    public int getID() {
        return this.currentID;
    }

    public void run() {
        while (this.isRun) {
            try {
                this.isRun = false;
                this.isActivate = false;
            }
            catch (Exception exception) {}
        }
    }

    public void play(boolean bl) {
        this.isActivate = true;
        this.isRun = true;
        this.isLoop = bl;
        try {
            this.sndPlayer.start();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void stop() {
        try {
            if (this.sndPlayer != null) {
                this.sndPlayer.stop();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void close() {
        try {
            if (this.sndPlayer != null) {
                this.sndPlayer.stop();
                this.sndPlayer.close();
                this.sndPlayer = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void dispose() {
    }

    public void volumeUp() {
    }

    public void volumeDown() {
    }

    public void playerUpdate(Player player, String string, Object object) {
    }
}
