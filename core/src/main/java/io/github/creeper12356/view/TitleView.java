
/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import java.util.TimerTask;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import io.github.creeper12356.utils.Resource;

public class TitleView
        extends TimerTask {
    public static final int LOGO = 1;
    public static final int TITLE = 2;
    Image[] imgTitleChip = new Image[2];
    Image imgMobilebus;
    int state = 1;
    int process = 1;
    long time;
    Animation aniJumpAvata;



    public void run() {
        Resource.aMainView.repaint();
    }


    public void paint(Graphics graphics) {
        if (this.state == 1) {
            if (this.process == 1) {
                this.time = System.currentTimeMillis();
                ++this.process;
            } else if (this.process == 2) {
                if (System.currentTimeMillis() - this.time > 1500L) {
                    ++this.process;
                }
            } else if (this.process == 3) {
                // empty if block
            }
            graphics.setColor(255, 255, 255);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            graphics.setColor(0);
            if (this.process == 1) {
                graphics.drawImage(this.imgMobilebus, Resource.halfWidth, Resource.halfHeight, 3);
            } else if (this.process == 2) {
                graphics.drawImage(this.imgMobilebus, Resource.halfWidth, Resource.halfHeight, 3);
            } else if (this.process == 3) {
                graphics.setFont(Resource.sf);
                graphics.drawString("\u662f\u5426\u5f00\u542f\u58f0\u97f3\uff1f", Resource.halfWidth,
                        Resource.halfHeight, 1 | 0x10);
                graphics.drawString("\u662f", 3, Resource.totalHeight - 3, 4 | 0x20);
                graphics.drawString("\u5426", Resource.totalWidth - 3, Resource.totalHeight - 3, 8 | 0x20);
            }
        } else if (this.state == 2) {
            int[] nArray = new int[] { 0, 1, 2, 1 };
            if (this.process == 0) {
                Utils.playSound(9, false);
                ++this.process;
            }
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            if (this.aniJumpAvata.getFrame() != 0) {
                graphics.drawImage(this.imgTitleChip[0], Resource.halfWidth - this.imgTitleChip[0].getWidth() / 2,
                        Resource.totalHeight - 36, 0);
            }
            this.aniJumpAvata.frameProcess();
        }
    }

    public void clear() {
        this.imgTitleChip[0] = null;
        this.imgTitleChip[1] = null;
        this.aniJumpAvata = null;
        this.imgMobilebus = null;
    }

    public void setStatus(int n) {
    }

    public int getStatus() {
        return 0;
    }
}
