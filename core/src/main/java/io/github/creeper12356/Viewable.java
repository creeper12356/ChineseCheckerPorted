/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public interface Viewable {
    public void paint(Graphics var1);

    public void keyPressed(int var1);

    public void keyReleased(int var1);

    public void init();

    public void clear();

    public void setStatus(int var1);

    public int getStatus();
}
