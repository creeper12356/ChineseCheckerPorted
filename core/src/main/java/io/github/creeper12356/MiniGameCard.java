/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

import io.github.creeper12356.utils.Utils;

class MiniGameCard {
    static final int BACK = 0;
    static final int FRONT = 1;
    static final int TURN_FRONT = 2;
    static final int TURN_BACK = 3;
    static final int STATIC_FRONT = 4;
    int state;
    int item = -1;
    Animation aniTurn = new Animation();

    MiniGameCard() {
    }

    void init(int n) {
        this.state = 0;
        this.item = n;
        this.aniTurn.init(0, 0, false);
    }

    boolean turnFront() {
        if (this.state != 0) {
            return false;
        }
        if (this.item == -1) {
            return false;
        }
        this.state = 2;
        this.aniTurn.init(2, 100, false);
        Utils.playSound(6, false);
        return true;
    }

    boolean turnBack() {
        if (this.state != 1) {
            return false;
        }
        this.state = 3;
        this.aniTurn.init(2, 100, false);
        Utils.playSound(6, false);
        return true;
    }

    void setStatic() {
        if (this.state == 1) {
            this.state = 4;
        }
    }

    void draw(Graphics graphics, int n, int n2) {
        if (this.state == 0) {
            graphics.drawImage(MiniGameView.imgCard[0], n, n2, 4 | 0x10);
        } else if (this.state == 1 || this.state == 4) {
            graphics.drawImage(MiniGameView.imgCard[1], n, n2, 4 | 0x10);
            graphics.drawImage(MiniGameView.imgPiece[this.item], n + MiniGameView.imgCard[1].getWidth() / 2, n2 + MiniGameView.imgCard[1].getHeight() / 2, 1 | 2);
        } else if (this.state == 2) {
            if (this.aniTurn.getFrame() == 0) {
                graphics.drawImage(MiniGameView.imgCard[2], n + MiniGameView.imgCard[1].getWidth() / 2, n2, 1 | 0x10);
            }
            if (this.aniTurn.getFrame() == 1) {
                graphics.drawImage(MiniGameView.imgCard[1], n, n2, 4 | 0x10);
            }
            if (this.aniTurn.frameProcess() == 0) {
                this.state = 1;
            }
        } else if (this.state == 3) {
            if (this.aniTurn.getFrame() == 0) {
                graphics.drawImage(MiniGameView.imgCard[2], n + MiniGameView.imgCard[1].getWidth() / 2, n2, 1 | 0x10);
            }
            if (this.aniTurn.getFrame() == 1) {
                graphics.drawImage(MiniGameView.imgCard[0], n, n2, 4 | 0x10);
            }
            if (this.aniTurn.frameProcess() == 0) {
                this.state = 0;
            }
        }
    }
}
