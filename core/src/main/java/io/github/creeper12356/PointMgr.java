/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import io.github.creeper12356.utils.Resource;

public class PointMgr {
    int point = 0;
    int plus1 = -1;
    int plus2 = -1;
    Animation ani1 = new Animation();
    Image imgPlus;

    PointMgr() {
        this.ani1.init(0, 0, false);
        try {
            this.imgPlus = Image.createImage((String)"/smallplus.png");
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    int getPoint() {
        return this.point;
    }

    void setPoint(int n) {
        this.point = n;
    }

    void addPoint(int n) {
        if (this.plus1 == -1) {
            this.plus1 = n;
            this.ani1.init(20, 100, false);
        } else if (this.plus2 == -1) {
            this.plus2 = n;
        } else {
            this.point += this.plus1;
            this.plus1 = this.plus2;
            this.plus2 = n;
        }
    }

    void draw(Graphics graphics) {
        if (this.plus1 != -1) {
            int n = this.ani1.getFrame() == 19 ? -4 : 0;
            int n2 = 0;
            int n3 = Resource.isQVGA() ? 2 : 1;
            Resource.drawSmallNum(graphics, Resource.totalWidth - 2, 11 * n3 + n, this.plus1, 2);
            if (this.plus1 < 10) {
                n2 = 12;
            } else if (this.plus1 < 100) {
                n2 = 18;
            } else if (this.plus1 < 1000) {
                n2 = 24;
            }
            graphics.drawImage(this.imgPlus, Resource.totalWidth - 2 - n2 * n3, 11 * n3 + n, 4 | 0x10);
            if (this.plus2 != -1) {
                Resource.drawSmallNum(graphics, Resource.totalWidth - 2, 20 * n3 + n, this.plus2, 2);
                if (this.plus2 < 10) {
                    n2 = 12;
                } else if (this.plus2 < 100) {
                    n2 = 18;
                } else if (this.plus2 < 1000) {
                    n2 = 24;
                }
                graphics.drawImage(this.imgPlus, Resource.totalWidth - 2 - n2 * n3, 20 * n3 + n, 4 | 0x10);
            }
            if (this.ani1.frameProcess() == 0) {
                if (this.plus2 != -1) {
                    this.point += this.plus1;
                    this.plus1 = this.plus2;
                    this.plus2 = -1;
                    this.ani1.init(20, 100, false);
                } else {
                    this.point += this.plus1;
                    this.plus1 = -1;
                }
            }
        }
        Resource.drawBigNum(graphics, Resource.totalWidth, 1, this.point, 2);
    }
}
