/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class DiaApp
extends MIDlet {
    static Object suspend_lock = new Object();
    static boolean isSuspended;
    static boolean isAliveCondition;
    static long startmem;
    DiaGameCard game;

    public void startApp() {
        startmem = Runtime.getRuntime().freeMemory() / 1000L;
        if (this.game == null) {
            this.game = new DiaGameCard(this);
        }
        Display.getDisplay((MIDlet)this).setCurrent((Displayable)this.game);
    }

    public void destroyApp(boolean bl) {
    }

    public void pauseApp() {
        isSuspended = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resumeApp() {
        isSuspended = false;
        Object object = suspend_lock;
        synchronized (object) {
            suspend_lock.notify();
        }
    }

    public static boolean isSuspended() {
        return isSuspended;
    }
}
