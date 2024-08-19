/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import io.github.creeper12356.utils.Resource;
import io.github.creeper12356.utils.Utils;
import io.github.creeper12356.utils.XTimer;
import io.github.creeper12356.utils.XTimerListener;

public class Continental
implements Viewable,
XTimerListener {
    static final int WIDTH = 7;
    static final int HEIGHT = 7;
    static final int NONE = 0;
    static final int EMPTY = 1;
    static final int BALL = 2;
    static final int STATUS_MENU = 0;
    static final int STATUS_PLAY = 1;
    static final int PS_READY = 0;
    static final int PS_SELECT = 1;
    static final int PS_MOVE = 2;
    static final int PS_DISAPPEAR = 3;
    static final int PS_COMPLETE = 4;
    static final int PMSTATE_MAIN = 0;
    static final int PMSTATE_QUITYESNO = 1;
    static int HGAB = 33;
    static int VGAB = 29;
    static int BoardZeroPosH;
    static int BoardZeroPosV;
    public static boolean newGame;
    byte[][] board;
    int curSelH;
    int curSelV;
    Animation aniJump;
    int movePosX;
    int movePosY;
    int moveDir;
    int disappearPosX;
    int disappearPosY;
    boolean bUndo;
    int delPosX;
    int delPosY;
    int undoPosX;
    int undoPosY;
    int undoToX;
    int undoToY;
    Image[] imgBall;
    Image imgArrow;
    Image[] imgPopupMenu;
    Image[] imgUI;
    Image[] imgRoundChip;
    int status;
    int playState;
    boolean bPopupMenu;
    int menuSel;
    int popupmenuState;
    int menuLength;
    boolean bHelp;
    int curTime;
    public static int bestTime;
    Animation aniTimer;
    boolean bNewRecord;
    Animation aniReadyDelay;
    boolean bReadyDelay;
    int ballcx;
    int ballcy;

    Continental() {
    }

    public void init() {
        if (Resource.isQVGA()) {
            HGAB = 33;
            VGAB = 29;
            BoardZeroPosH = Resource.boardPosX + 20;
            BoardZeroPosV = Resource.boardPosY + 14;
            this.ballcx = 14;
            this.ballcy = 16;
        } else {
            HGAB = 17;
            VGAB = 16;
            BoardZeroPosH = Resource.boardPosX + 15;
            BoardZeroPosV = Resource.boardPosY + 10;
            this.ballcx = 8;
            this.ballcy = 9;
        }
        this.board = new byte[7][7];
        this.aniJump = new Animation();
        this.aniTimer = new Animation();
        this.aniReadyDelay = new Animation();
        this.imgBall = new Image[5];
        this.imgPopupMenu = new Image[2];
        this.imgUI = new Image[6];
        this.imgRoundChip = new Image[2];
        try {
            this.imgArrow = Image.createImage((String)"/miniarrow.png");
            this.imgBall[0] = Image.createImage((String)"/miniball_0.png");
            this.imgBall[1] = Image.createImage((String)"/miniball_1.png");
            this.imgBall[2] = Image.createImage((String)"/miniball_2.png");
            this.imgBall[3] = Image.createImage((String)"/miniball_3.png");
            this.imgBall[4] = Image.createImage((String)"/miniball_4.png");
            this.imgPopupMenu[0] = Image.createImage((String)"/menuchip_1.png");
            this.imgPopupMenu[1] = Image.createImage((String)"/selicon_0.png");
            this.imgUI[0] = Image.createImage((String)"/uichip_15.png");
            this.imgUI[1] = Image.createImage((String)"/uichip_16.png");
            this.imgUI[2] = Image.createImage((String)"/rankchip_16.png");
            this.imgUI[3] = Image.createImage((String)"/button_help.png");
            this.imgUI[4] = Image.createImage((String)"/minitext_0.png");
            this.imgUI[5] = Image.createImage((String)"/minitext_1.png");
            this.imgRoundChip[0] = Image.createImage((String)"/special.png");
            this.imgRoundChip[1] = Image.createImage((String)"/roundchip_4.png");
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.newStart();
    }

    public void clear() {
        this.board = null;
        this.aniJump = null;
        this.aniTimer = null;
        this.aniReadyDelay = null;
        this.imgArrow = null;
        this.imgBall = null;
        this.imgPopupMenu = null;
        this.imgUI = null;
        System.gc();
    }

    public void performed(XTimer xTimer) {
        Resource.aMainView.repaint();
    }

    public void setStatus(int n) {
    }

    public int getStatus() {
        return this.status;
    }

    void newStart() {
        int n;
        int n2;
        for (n2 = 0; n2 < 7; ++n2) {
            for (n = 0; n < 7; ++n) {
                this.board[n][n2] = 2;
            }
        }
        this.board[0][0] = 0;
        this.board[1][0] = 0;
        this.board[0][1] = 0;
        this.board[1][1] = 0;
        this.board[5][0] = 0;
        this.board[6][0] = 0;
        this.board[5][1] = 0;
        this.board[6][1] = 0;
        this.board[0][5] = 0;
        this.board[1][5] = 0;
        this.board[0][6] = 0;
        this.board[1][6] = 0;
        this.board[5][5] = 0;
        this.board[5][6] = 0;
        this.board[6][5] = 0;
        this.board[6][6] = 0;
        this.board[3][3] = 1;
        Image image = Resource.loadImage("/pattern_" + Resource.getRand(4) + ".png");
        for (n2 = 0; n2 < Resource.totalHeight; n2 += image.getHeight()) {
            for (n = 0; n < Resource.totalWidth; n += image.getWidth()) {
                Resource.getBackBuffer().drawImage(image, n, n2, 20);
            }
        }
        Image image2 = Resource.loadImage("/miniboard.png");
        Resource.getBackBuffer().drawImage(image2, Resource.boardPosX, Resource.boardPosY, 20);
        image2 = null;
        this.curSelH = 2;
        this.curSelV = 2;
        this.status = 1;
        this.playState = 0;
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.setTime(100L);
        Resource.aTimer2.resume();
        this.bUndo = false;
        this.bPopupMenu = false;
        this.bHelp = false;
        this.curTime = 0;
        this.aniTimer.init(1, 1000, false);
        this.aniReadyDelay.init(2, 1500, false);
        this.bReadyDelay = true;
        Utils.playSound(13, false);
    }

    public void paint(Graphics graphics) {
        graphics.setFont(Resource.sf);
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (this.board[j][i] != 2 || this.curSelH == j && this.curSelV == i) continue;
                graphics.drawImage(this.imgBall[0], BoardZeroPosH + j * HGAB - this.ballcx, BoardZeroPosV + i * VGAB - this.ballcy, 4 | 0x10);
            }
        }
        if (this.bHelp) {
            TouchDo.setTouchArea(5, -1, 0);
            this.drawHelp(graphics);
            return;
        }
        if (this.bPopupMenu) {
            TouchDo.setTouchArea(5, -2, this.popupmenuState);
            this.drawPopupMenu(graphics);
            return;
        }
        if (this.bUndo) {
            TouchDo.setTouchArea(5, this.playState, -1);
        } else {
            TouchDo.setTouchArea(5, this.playState, 0);
        }
        if (this.playState == 0) {
            if (this.board[this.curSelH][this.curSelV] == 2) {
                graphics.drawImage(this.imgBall[0], BoardZeroPosH + this.curSelH * HGAB - this.ballcx, BoardZeroPosV + this.curSelV * VGAB - this.ballcy, 4 | 0x10);
            }
        } else if (this.playState == 1) {
            if (this.aniJump.getFrame() == 0) {
                graphics.drawImage(this.imgBall[1], BoardZeroPosH + this.curSelH * HGAB - this.ballcx - 1, BoardZeroPosV + this.curSelV * VGAB - this.ballcy - 1, 4 | 0x10);
            } else {
                graphics.drawImage(this.imgBall[2], BoardZeroPosH + this.curSelH * HGAB - this.ballcx, BoardZeroPosV + this.curSelV * VGAB - this.ballcy, 4 | 0x10);
            }
            this.aniJump.frameProcess();
        } else if (this.playState == 2) {
            this.movePosX = 0;
            this.movePosY = 0;
            if (this.moveDir == 1) {
                this.movePosX = -(this.aniJump.getFrame() * (HGAB / 4) * 2);
                this.movePosY = 0;
            } else if (this.moveDir == 2) {
                this.movePosX = this.aniJump.getFrame() * (HGAB / 4) * 2;
                this.movePosY = 0;
            } else if (this.moveDir == 3) {
                this.movePosX = 0;
                this.movePosY = -(this.aniJump.getFrame() * (VGAB / 4) * 2);
            } else if (this.moveDir == 4) {
                this.movePosX = 0;
                this.movePosY = this.aniJump.getFrame() * (VGAB / 4 * 2);
            }
            graphics.drawImage(this.imgBall[3], BoardZeroPosH + this.curSelH * HGAB + this.movePosX - this.ballcx, BoardZeroPosV + this.curSelV * VGAB + this.movePosY - this.ballcy, 4 | 0x10);
            if (this.aniJump.frameProcess() == 0) {
                this.moveBall();
                this.playState = 3;
                this.aniJump.init(4, 100, false);
            }
        } else if (this.playState == 3) {
            if (this.aniJump.getFrame() % 2 == 0) {
                graphics.drawImage(this.imgBall[4], BoardZeroPosH + this.disappearPosX * HGAB - this.ballcx, BoardZeroPosV + this.disappearPosY * VGAB - this.ballcy / 2, 4 | 0x10);
            }
            if (this.aniJump.frameProcess() == 0) {
                if (this.checkComplete()) {
                    this.playState = 4;
                    this.aniJump.init(1, 2000, false);
                    Utils.playSound(18, false);
                    if (this.curTime < bestTime) {
                        bestTime = this.curTime;
                        Resource.saveRank();
                        this.bNewRecord = true;
                    } else {
                        this.bNewRecord = false;
                    }
                } else {
                    this.playState = 0;
                }
            }
            graphics.drawImage(this.imgBall[0], BoardZeroPosH + this.curSelH * HGAB - this.ballcx, BoardZeroPosV + this.curSelV * VGAB - this.ballcy, 4 | 0x10);
        } else if (this.playState == 4) {
            graphics.drawImage(this.imgUI[4], Resource.halfWidth - this.imgUI[4].getWidth() / 2, Resource.halfHeight - this.imgUI[4].getHeight(), 4 | 0x10);
            if (this.bNewRecord) {
                graphics.drawImage(this.imgUI[5], Resource.halfWidth - this.imgUI[5].getWidth() / 2, Resource.halfHeight - this.imgUI[4].getHeight() - 2, 4 | 0x20);
            }
            if (this.aniJump.frameProcess() == 0) {
                this.newStart();
            }
        }
        if (this.playState == 0 && !this.bReadyDelay) {
            graphics.drawImage(this.imgArrow, BoardZeroPosH + this.curSelH * HGAB, BoardZeroPosV + this.curSelV * VGAB, 4 | 0x10);
        }
        if (this.bUndo) {
            graphics.drawImage(this.imgUI[1], Resource.totalWidth - 1 + 10, Resource.totalHeight - 1 - 50, 8 | 0x20);
        }
        graphics.drawImage(this.imgUI[0], 1, 1, 4 | 0x10);
        Resource.drawBigNum(graphics, 1 + this.imgUI[0].getWidth() + 1, 1, this.curTime, 1);
        if (Resource.lcdSize == 4) {
            graphics.drawImage(this.imgUI[2], Resource.halfWidth + 65, 1, 4 | 0x10);
            Resource.drawBigNum(graphics, Resource.halfWidth + 65 + this.imgUI[2].getWidth() + 1, 1, bestTime, 1);
        } else {
            graphics.drawImage(this.imgUI[2], Resource.halfWidth, 1, 4 | 0x10);
            Resource.drawBigNum(graphics, Resource.halfWidth + this.imgUI[2].getWidth() + 1, 1, bestTime, 1);
        }
        graphics.drawImage(this.imgUI[3], 7, Resource.totalHeight - 1 - 53, 4 | 0x20);
        if (!(this.playState == 4 || this.bReadyDelay || this.bHelp || this.bPopupMenu || this.aniTimer.frameProcess() != 0)) {
            ++this.curTime;
            this.aniTimer.init(1, 1000, false);
        }
        if (this.bReadyDelay) {
            if (this.aniReadyDelay.getFrame() == 0) {
                graphics.drawImage(this.imgRoundChip[0], Resource.halfWidth, Resource.halfHeight, 1 | 2);
            } else {
                graphics.drawImage(this.imgRoundChip[1], Resource.halfWidth, Resource.halfHeight, 1 | 2);
            }
            if (this.aniReadyDelay.frameProcess() == 0) {
                this.bReadyDelay = false;
                if (newGame) {
                    this.bHelp = true;
                    newGame = false;
                    Resource.saveGame();
                }
            }
            return;
        }
    }

    void drawPopupMenu(Graphics graphics) {
        int n;
        int n2;
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
        if (Resource.isQVGA()) {
            n2 = 96;
            n = 86;
        } else {
            n2 = 55;
            n = 50;
        }
        int n3 = Resource.halfWidth - n2;
        int n4 = Resource.halfHeight - n;
        graphics.setColor(148, 65, 24);
        graphics.setFont(Resource.sf);
        if (Resource.isQVGA()) {
            n2 = 190;
            n = 186;
        } else {
            n2 = 110;
            n = 100;
        }
        graphics.fillRect(n3 + 1, n4, n2 - 1, n + 1);
        graphics.fillRect(n3, n4 + 1, n2 + 1, n - 1);
        graphics.setColor(255, 230, 209);
        if (Resource.isQVGA()) {
            graphics.fillRect(n3 + 2, n4 + 2, 187, 183);
            graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 22, n4 - 21, 4 | 0x10);
        } else {
            graphics.fillRect(n3 + 2, n4 + 2, 107, 97);
            graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 11, n4 - 9, 4 | 0x10);
        }
        if (this.popupmenuState == 0) {
            int n5;
            if (Resource.isQVGA()) {
                graphics.setFont(Font.getFont((int)0, (int)0, (int)16));
                n2 = 58;
                n = 19;
                n5 = 33;
            } else {
                n2 = 24;
                n = 15;
                n5 = 15;
            }
            graphics.setColor(148, 65, 24);
            graphics.setFont(Resource.sf);
            graphics.drawString("1.\u7ee7\u7eed\u6e38\u620f", n3 + n2 + 10, n4 + n + n5 * 0, 4 | 0x10);
            graphics.drawString("2.\u91cd\u65b0\u5f00\u59cb", n3 + n2 + 10, n4 + n + n5 * 1, 4 | 0x10);
            graphics.drawString("3.\u6e38\u620f\u8bbe\u7f6e", n3 + n2 + 10, n4 + n + n5 * 2, 4 | 0x10);
            graphics.drawString("4.\u6e38\u620f\u5e2e\u52a9", n3 + n2 + 10, n4 + n + n5 * 3, 4 | 0x10);
            graphics.drawString("5.\u9000\u51fa\u6e38\u620f", n3 + n2 + 10, n4 + n + n5 * 4, 4 | 0x10);
            graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + n + this.menuSel * n5, 4 | 0x10);
        }
        if (this.popupmenuState == 1) {
            graphics.setColor(148, 65, 24);
            graphics.setFont(Resource.sf);
            if (Resource.isQVGA()) {
                graphics.drawString("\u9000\u51fa\u6e38\u620f \uff1f", n3 + 58, n4 + 30, 4 | 0x10);
                graphics.drawString("1. \u662f", n3 + 58, n4 + 60, 4 | 0x10);
                graphics.drawString("2. \u5426", n3 + 58, n4 + 95, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + 60 + this.menuSel * 35, 4 | 0x10);
            } else {
                graphics.drawString("\u9000\u51fa\u6e38\u620f \uff1f", n3 + 12, n4 + 15, 4 | 0x10);
                graphics.drawString("1. \u662f", n3 + 24, n4 + 30, 4 | 0x10);
                graphics.drawString("2. \u5426", n3 + 24, n4 + 45, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 7, n4 + 30 + this.menuSel * 15, 4 | 0x10);
            }
        }
    }

    void drawHelp(Graphics graphics) {
        int n;
        int n2;
        int n3;
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
        if (Resource.isQVGA()) {
            n3 = 96;
            n2 = 86;
        } else {
            n3 = 55;
            n2 = 60;
        }
        int n4 = Resource.halfWidth - n3;
        int n5 = Resource.halfHeight - n2;
        graphics.setColor(148, 65, 24);
        if (Resource.isQVGA()) {
            n3 = 190;
            n2 = 181;
        } else {
            n3 = 110;
            n2 = 125;
        }
        graphics.fillRect(n4 + 1, n5, n3 - 1, n2 + 1);
        graphics.fillRect(n4, n5 + 1, n3 + 1, n2 - 1);
        graphics.setColor(255, 230, 209);
        if (Resource.isQVGA()) {
            graphics.fillRect(n4 + 2, n5 + 2, 187, 178);
            graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 22, n5 - 21, 4 | 0x10);
        } else {
            graphics.fillRect(n4 + 2, n5 + 2, 107, 122);
            graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 11, n5 - 9, 4 | 0x10);
        }
        if (Resource.isQVGA()) {
            n3 = 48;
            n2 = 30;
            n = 20;
        } else {
            n3 = 14;
            n2 = 15;
            n = 15;
        }
        graphics.setColor(148, 65, 24);
        graphics.setFont(Resource.sf);
        graphics.drawString("\u4f60\u53ef\u4ee5\u8df3\u8fc7\u5468\u8fb9", n4 + n3, n5 + n2 + n * 0, 4 | 0x10);
        graphics.drawString("\u7684\u5176\u4ed6\u9a6c\u6765\u5411\u524d", n4 + n3, n5 + n2 + n * 1, 4 | 0x10);
        graphics.drawString("\u63a8\u8fdb\uff0c\u76f4\u5230\u5b8c\u6210", n4 + n3, n5 + n2 + n * 2, 4 | 0x10);
        graphics.drawString("\u6240\u6709\u7684\u9a6c\u3002", n4 + n3, n5 + n2 + n * 3, 4 | 0x10);
        graphics.drawString("\u79fb\u52a8: \u65b9\u5411\u952e", n4 + n3, n5 + n2 + n * 5, 4 | 0x10);
        graphics.drawString("\u9009\u62e9: OK\u952e", n4 + n3, n5 + n2 + n * 6, 4 | 0x10);
    }

    void psReadyKeyProcess(int n) {
        switch (n) {
            case -3: 
            case 52: {
                if (this.curSelH <= 0 || this.board[this.curSelH - 1][this.curSelV] == 0) break;
                --this.curSelH;
                Utils.playSound(5, false);
                break;
            }
            case -4: 
            case 54: {
                if (this.curSelH >= 6 || this.board[this.curSelH + 1][this.curSelV] == 0) break;
                ++this.curSelH;
                Utils.playSound(5, false);
                break;
            }
            case -1: 
            case 50: {
                if (this.curSelV <= 0 || this.board[this.curSelH][this.curSelV - 1] == 0) break;
                --this.curSelV;
                Utils.playSound(5, false);
                break;
            }
            case -2: 
            case 56: {
                if (this.curSelV >= 6 || this.board[this.curSelH][this.curSelV + 1] == 0) break;
                ++this.curSelV;
                Utils.playSound(5, false);
                break;
            }
            case -5: 
            case 53: {
                if (this.board[this.curSelH][this.curSelV] == 2) {
                    this.playState = 1;
                    this.aniJump.init(2, 100, true);
                    Utils.playSound(6, false);
                    break;
                }
                Utils.playSound(4, false);
            }
        }
    }

    void psSelectKeyProcess(int n) {
        int n2 = -1;
        switch (n) {
            case -3: 
            case 52: {
                n2 = 1;
                break;
            }
            case -4: 
            case 54: {
                n2 = 2;
                break;
            }
            case -1: 
            case 50: {
                n2 = 3;
                break;
            }
            case -2: 
            case 56: {
                n2 = 4;
                break;
            }
            case -8: 
            case -5: 
            case 48: 
            case 53: {
                this.playState = 0;
                Utils.playSound(6, false);
            }
        }
        if (n2 != -1) {
            if (this.checkMove(n2)) {
                this.bUndo = false;
                this.playState = 2;
                this.movePosX = 0;
                this.movePosY = 0;
                this.moveDir = n2;
                this.aniJump.init(4, 100, false);
                Utils.playSound(0, false);
            } else {
                Utils.playSound(4, false);
            }
        }
    }

    void keyPopupMenu(int n) {
        switch (n) {
            case -1: {
                this.menuSel = this.menuSel == 0 ? this.menuLength - 1 : this.menuSel - 1;
                Utils.playSound(5, false);
                break;
            }
            case -2: {
                this.menuSel = this.menuSel == this.menuLength - 1 ? 0 : this.menuSel + 1;
                Utils.playSound(5, false);
                break;
            }
            case -5: {
                Utils.playSound(17, false);
                this.goMenu();
                break;
            }
            case -8: 
            case -7: 
            case 48: {
                Utils.playSound(17, false);
                if (this.popupmenuState == 0) {
                    this.bPopupMenu = false;
                    if (this.playState != 0) break;
                    this.bReadyDelay = true;
                    break;
                }
                if (this.popupmenuState != 1) break;
                this.menuSel = 1;
                this.goMenu();
            }
        }
        if (this.popupmenuState == 0) {
            if (n == 49) {
                this.menuSel = 0;
            } else if (n == 50) {
                this.menuSel = 1;
            } else if (n == 51) {
                this.menuSel = 2;
            } else if (n == 52) {
                this.menuSel = 3;
            } else if (n == 53) {
                this.menuSel = 4;
            } else {
                return;
            }
            Utils.playSound(17, false);
            this.goMenu();
            return;
        }
        if (this.popupmenuState == 1) {
            if (n == 49) {
                this.menuSel = 0;
            } else if (n == 50) {
                this.menuSel = 1;
            } else {
                return;
            }
            Utils.playSound(17, false);
            this.goMenu();
            return;
        }
    }

    void goMenu() {
        if (this.menuSel == 0) {
            if (this.popupmenuState == 0) {
                this.bPopupMenu = false;
                if (this.playState == 0) {
                    this.bReadyDelay = true;
                }
            } else if (this.popupmenuState == 1) {
                Resource.aMainView.setStatus(0, 1);
            }
        } else if (this.menuSel == 1) {
            if (this.popupmenuState == 0) {
                this.newStart();
            } else if (this.popupmenuState == 1) {
                this.popupmenuState = 0;
                this.menuSel = 3;
                this.menuLength = 4;
            }
        } else if (this.menuSel == 2) {
            Resource.aMainView.openPopup(6);
        } else if (this.menuSel == 3) {
            if (this.popupmenuState == 0) {
                this.bHelp = true;
            }
        } else if (this.menuSel == 4 && this.popupmenuState == 0) {
            this.popupmenuState = 1;
            this.menuSel = 1;
            this.menuLength = 2;
        }
    }

    protected void popupMenu() {
        this.bPopupMenu = true;
        this.menuLength = 5;
        this.menuSel = 0;
        this.popupmenuState = 0;
        if (this.playState == 0 && this.bReadyDelay) {
            this.bReadyDelay = false;
        }
    }

    public void keyPressed(int n) {
        if (this.bReadyDelay) {
            return;
        }
        if (this.bHelp) {
            if (n == 48 || n == -8 || n == -5 || n == 53) {
                this.bHelp = false;
                return;
            }
            return;
        }
        if (this.bPopupMenu) {
            this.keyPopupMenu(n);
            return;
        }
        if (n == -7) {
            this.popupMenu();
        }
        if (n == -8 && this.playState == 0) {
            this.popupMenu();
        }
        switch (this.playState) {
            case 0: {
                this.psReadyKeyProcess(n);
                break;
            }
            case 1: {
                this.psSelectKeyProcess(n);
            }
        }
        if (this.playState == 4) {
            return;
        }
        if (n == 35 && this.bUndo) {
            this.undoMove();
        }
        if (n == 42) {
            this.bHelp = true;
        }
    }

    public void keyReleased(int n) {
    }

    boolean checkMove(int n) {
        int n2 = this.curSelH;
        int n3 = this.curSelV;
        int n4 = this.curSelH;
        int n5 = this.curSelV;
        switch (n) {
            case 1: {
                --n4;
                n2 -= 2;
                break;
            }
            case 2: {
                ++n4;
                n2 += 2;
                break;
            }
            case 3: {
                --n5;
                n3 -= 2;
                break;
            }
            case 4: {
                ++n5;
                n3 += 2;
            }
        }
        if (n2 < 0 || n2 > 6) {
            return false;
        }
        if (n3 < 0 || n3 > 6) {
            return false;
        }
        return this.board[n4][n5] == 2 && this.board[n2][n3] == 1;
    }

    void undoMove() {
        if (!this.bUndo) {
            return;
        }
        this.board[this.delPosX][this.delPosY] = 2;
        this.board[this.undoPosX][this.undoPosY] = 1;
        this.board[this.undoToX][this.undoToY] = 2;
        this.bUndo = false;
        this.playState = 0;
        this.curSelH = this.undoToX;
        this.curSelV = this.undoToY;
    }

    void moveBall() {
        int n = this.curSelH;
        int n2 = this.curSelV;
        int n3 = this.curSelH;
        int n4 = this.curSelV;
        switch (this.moveDir) {
            case 1: {
                --n3;
                n -= 2;
                break;
            }
            case 2: {
                ++n3;
                n += 2;
                break;
            }
            case 3: {
                --n4;
                n2 -= 2;
                break;
            }
            case 4: {
                ++n4;
                n2 += 2;
            }
        }
        this.board[this.curSelH][this.curSelV] = 1;
        this.board[n3][n4] = 1;
        this.board[n][n2] = 2;
        this.delPosX = n3;
        this.delPosY = n4;
        this.undoPosX = n;
        this.undoPosY = n2;
        this.undoToX = this.curSelH;
        this.undoToY = this.curSelV;
        this.bUndo = true;
        this.disappearPosX = n3;
        this.disappearPosY = n4;
        this.curSelH = n;
        this.curSelV = n2;
    }

    boolean checkComplete() {
        int n = 0;
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (this.board[j][i] == 2) {
                    ++n;
                }
                if (n <= 1) continue;
                return false;
            }
        }
        return true;
    }
}
