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

public class MiniGameView
implements Viewable,
XTimerListener {
    static final int MINI_1 = 1;
    static final int MINI_2 = 2;
    final int READY;
    final int HELP;
    final int PLAY;
    final int RESULT;
    static Image[] imgPiece;
    static Image[] imgCard;
    Image imgArrow;
    Image imgClock;
    Image[] imgReady;
    Image[] imgOX;
    Image[] imgResult;
    Image[] imgPopupMenu;
    int card_x;
    int card_y;
    int gameType;
    int state;
    MiniGameCard[] cards;
    int curSelCard;
    int prevSelCard;
    int checkCount;
    int getPoint;
    int bonusPoint;
    boolean bCheckTwoCard;
    boolean bPrevCard;
    boolean bCursor;
    boolean bTimeOver;
    boolean bSucceed;
    boolean bHelp;
    public static boolean bNewGame1;
    public static boolean bNewGame2;
    int gameLevel;
    Animation ani1 = new Animation();
    Animation ani2 = new Animation();
    Animation aniGameTime = new Animation();
    int keyDelay;
    boolean bPopupMenu;
    int menuSel;
    int menuLength;
    int popupmenuState;
    static final int PMSTATE_MAIN = 0;
    static final int PMSTATE_QUITYESNO = 1;

    public MiniGameView() {
        this.READY = 1;
        this.HELP = 2;
        this.PLAY = 3;
        this.RESULT = 4;
    }

    public void paint(Graphics graphics) {
        TouchDo.setTouchArea(4, this.state, this.gameType);
        if (this.gameType == 1) {
            this.drawMiniGame1(graphics);
        } else if (this.gameType == 2) {
            this.drawMiniGame2(graphics);
        }
        Resource.pointMgr.draw(graphics);
    }

    public void keyPressed(int n) {
        if (this.gameType == 1) {
            this.keyMiniGame1(n);
        } else if (this.gameType == 2) {
            this.keyMiniGame2(n);
        }
    }

    public void keyReleased(int n) {
    }

    public void init() {
        this.bHelp = false;
        this.bPopupMenu = false;
        this.menuSel = 0;
        this.menuLength = 0;
        this.popupmenuState = 0;
        imgCard = new Image[3];
        this.imgReady = new Image[2];
        this.imgOX = new Image[2];
        this.imgResult = new Image[2];
        this.imgPopupMenu = new Image[2];
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.setTime(100L);
        Resource.aTimer2.resume();
        try {
            MiniGameView.imgCard[0] = Image.createImage((String)"/minicard_b.png");
            MiniGameView.imgCard[1] = Image.createImage((String)"/minicard_f.png");
            MiniGameView.imgCard[2] = Image.createImage((String)"/minicard_t.png");
            this.imgArrow = Image.createImage((String)"/miniarrow.png");
            this.imgClock = Image.createImage((String)"/miniclock.png");
            this.imgReady[0] = Image.createImage((String)"/bonus.png");
            this.imgReady[1] = Image.createImage((String)"/roundchip_4.png");
            this.imgOX[0] = Image.createImage((String)"/oo.png");
            this.imgOX[1] = Image.createImage((String)"/xx.png");
            this.imgResult[0] = Image.createImage((String)"/succeed.png");
            this.imgResult[1] = Image.createImage((String)"/failed.png");
            this.imgPopupMenu[0] = Image.createImage((String)"/menuchip_1.png");
            this.imgPopupMenu[1] = Image.createImage((String)"/selicon_0.png");
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (Resource.isQVGA()) {
            if (Resource.lcdSize == 4) {
                this.card_x = Resource.halfWidth - 103;
                this.card_y = Resource.halfHeight - 100;
            } else {
                this.card_x = Resource.halfWidth - (imgCard[0].getWidth() + 3) * 5 / 2;
                this.card_y = Resource.halfHeight - 100;
            }
        } else {
            this.card_x = Resource.halfWidth - (imgCard[0].getWidth() + 1) * 5 / 2;
            this.card_y = Resource.halfHeight - 49;
        }
        if (this.gameType == 1) {
            this.initMiniGame1();
        } else if (this.gameType == 2) {
            this.initMiniGame2();
        }
        this.keyDelay = 0;
    }

    public void clear() {
        imgCard = null;
        this.imgArrow = null;
        this.imgClock = null;
        this.imgReady = null;
        this.imgOX = null;
        this.imgResult = null;
        this.imgPopupMenu = null;
        this.cards = null;
        System.gc();
    }

    public void setStatus(int n) {
        this.gameType = n;
    }

    public int getStatus() {
        return 0;
    }

    public void performed(XTimer xTimer) {
        Resource.aMainView.repaint();
    }

    void initMiniGame1() {
        int n;
        int[] nArray = new int[10];
        this.cards = new MiniGameCard[20];
        for (n = 0; n < 20; ++n) {
            this.cards[n] = new MiniGameCard();
        }
        for (n = 0; n < 10; ++n) {
            int n2;
            int n3;
            boolean bl;
            block2: do {
                n3 = Resource.getRand(25);
                bl = true;
                for (n2 = 0; n2 < n; ++n2) {
                    if (nArray[n2] != n3) continue;
                    bl = false;
                    continue block2;
                }
            } while (!bl);
            nArray[n] = n3;
            int n4 = 0;
            n2 = Resource.getRand(20);
            while (n4 != 2) {
                if (this.cards[n2].item == -1) {
                    this.cards[n2].init(n);
                    n2 = Resource.getRand(20);
                    ++n4;
                    continue;
                }
                if (n2 == 19) {
                    n2 = 0;
                    continue;
                }
                ++n2;
            }
        }
        imgPiece = new Image[10];
        for (n = 0; n < 10; ++n) {
            MiniGameView.imgPiece[n] = Resource.loadImage("/dia_" + nArray[n] + "_n.png");
        }
        nArray = null;
        this.curSelCard = 0;
        this.prevSelCard = -1;
        this.getPoint = 0;
        this.bonusPoint = 0;
        this.checkCount = 0;
        this.bCheckTwoCard = false;
        this.bCursor = false;
        this.bSucceed = false;
        this.aniGameTime.init(300, 100, false);
        this.state = 1;
        this.ani1.init(2, 2000, false);
        Utils.playSound(13, false);
    }

    void drawMiniGame1(Graphics graphics) {
        graphics.setColor(90, 8, 0);
        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        if (this.bHelp) {
            this.drawHelp1(graphics);
            return;
        }
        if (this.bPopupMenu) {
            this.drawPopupMenu(graphics);
            return;
        }
        int n = Resource.isQVGA() ? 3 : 1;
        for (int i = 0; i < 20; ++i) {
            this.cards[i].draw(graphics, this.card_x + i % 5 * (imgCard[0].getWidth() + n), this.card_y + i / 5 * (imgCard[0].getHeight() + n));
        }
        if (this.bCursor) {
            graphics.drawImage(this.imgArrow, this.card_x + (this.curSelCard % 5 * (imgCard[0].getWidth() + n) + imgCard[0].getWidth() / 2), this.card_y + (this.curSelCard / 5 * (imgCard[0].getHeight() + n) + imgCard[0].getHeight() / 2 + 5), 4 | 0x10);
        }
        if (Resource.isQVGA()) {
            if (Resource.lcdSize == 4) {
                graphics.drawImage(this.imgClock, Resource.halfWidth - 137, Resource.halfHeight + 77, 4 | 0x10);
                int n2 = Resource.halfWidth - 126;
                int n3 = Resource.halfHeight + 75;
                int n4 = (300 - this.aniGameTime.getFrame()) / 10 * 4;
                graphics.setColor(255, 82, 90);
                graphics.drawLine(n2, n3, n2, n3 - n4);
                graphics.drawLine(n2 + 4, n3, n2 + 4, n3 - n4);
                graphics.setColor(255, 115, 123);
                graphics.drawLine(n2 + 1, n3, n2 + 1, n3 - n4);
                graphics.drawLine(n2 + 3, n3, n2 + 3, n3 - n4);
                graphics.setColor(255, 255, 255);
                graphics.drawLine(n2 + 2, n3, n2 + 2, n3 - n4);
                graphics.setColor(246, 49, 65);
                graphics.drawLine(n2 + 5, n3, n2 + 5, n3 - n4);
                graphics.drawLine(n2 + 7, n3, n2 + 7, n3 - n4);
                graphics.setColor(197, 32, 41);
                graphics.drawLine(n2 + 6, n3, n2 + 6, n3 - n4);
            } else {
                graphics.drawImage(this.imgClock, Resource.halfWidth - 96, Resource.halfHeight - 130, 4 | 0x10);
                int n5 = Resource.halfWidth - 67;
                int n6 = Resource.halfHeight - 121;
                int n7 = (300 - this.aniGameTime.getFrame()) / 10 * 4;
                graphics.setColor(255, 82, 90);
                graphics.drawLine(n5, n6, n5 + n7, n6);
                graphics.drawLine(n5, n6 + 4, n5 + n7, n6 + 4);
                graphics.setColor(255, 115, 123);
                graphics.drawLine(n5, n6 + 1, n5 + n7, n6 + 1);
                graphics.drawLine(n5, n6 + 3, n5 + n7, n6 + 3);
                graphics.setColor(255, 255, 255);
                graphics.drawLine(n5, n6 + 2, n5 + n7, n6 + 2);
                graphics.setColor(246, 49, 65);
                graphics.drawLine(n5, n6 + 5, n5 + n7, n6 + 5);
                graphics.drawLine(n5, n6 + 7, n5 + n7, n6 + 7);
                graphics.setColor(197, 32, 41);
                graphics.drawLine(n5, n6 + 6, n5 + n7, n6 + 6);
            }
        } else {
            graphics.drawImage(this.imgClock, Resource.halfWidth - 54, Resource.halfHeight - 66, 4 | 0x10);
            int n8 = Resource.halfWidth - 38;
            int n9 = Resource.halfHeight - 59;
            int n10 = (300 - this.aniGameTime.getFrame()) / 10 * 3;
            graphics.setColor(255, 82, 90);
            graphics.drawLine(n8, n9, n8 + n10, n9);
            graphics.drawLine(n8, n9 + 2, n8 + n10, n9 + 2);
            graphics.setColor(255, 115, 123);
            graphics.drawLine(n8, n9 + 1, n8 + n10, n9 + 1);
            graphics.setColor(246, 49, 65);
            graphics.drawLine(n8, n9 + 3, n8 + n10, n9 + 3);
        }
        if (this.state == 1) {
            if (this.ani1.getFrame() == 0) {
                graphics.drawImage(this.imgReady[0], Resource.halfWidth, Resource.halfHeight, 1 | 2);
            } else if (this.ani1.getFrame() == 1) {
                graphics.drawImage(this.imgReady[1], Resource.halfWidth, Resource.halfHeight, 1 | 2);
            }
            if (this.ani1.frameProcess() == 0) {
                this.bCursor = true;
                this.state = 3;
                if (bNewGame1) {
                    this.bHelp = true;
                    bNewGame1 = false;
                    Resource.saveGame();
                }
            }
        } else if (this.state == 3) {
            if (this.bCheckTwoCard) {
                if (this.ani1.frameProcess() == 0) {
                    if (this.cards[this.prevSelCard].item == this.cards[this.curSelCard].item) {
                        this.cards[this.prevSelCard].setStatic();
                        this.cards[this.curSelCard].setStatic();
                        this.prevSelCard = -1;
                        Resource.pointMgr.addPoint(10);
                        this.getPoint += 10;
                        ++this.checkCount;
                        Utils.playSound(19, false);
                        if (this.checkCount == 10) {
                            if (this.getPoint == 100) {
                                this.bonusPoint = 100;
                                Resource.pointMgr.addPoint(100);
                            }
                            this.bCursor = false;
                            this.state = 4;
                            this.ani1.init(3, 1000, false);
                            this.bSucceed = true;
                            Utils.playSound(18, false);
                        }
                    } else {
                        this.cards[this.prevSelCard].turnBack();
                        this.cards[this.curSelCard].turnBack();
                        this.prevSelCard = -1;
                    }
                    this.bCheckTwoCard = false;
                    this.bCursor = true;
                }
            } else if (this.aniGameTime.frameProcess() == 0) {
                this.bCursor = false;
                this.state = 4;
                this.ani1.init(3, 1000, false);
                this.bSucceed = false;
                Utils.playSound(18, false);
            }
        } else if (this.state == 4) {
            if (this.ani1.getFrame() == 0) {
                if (this.bSucceed) {
                    graphics.drawImage(this.imgResult[0], Resource.halfWidth, Resource.halfHeight, 1 | 0x20);
                } else {
                    graphics.drawImage(this.imgResult[1], Resource.halfWidth, Resource.halfHeight, 1 | 0x20);
                }
                this.ani1.frameProcess();
            } else {
                Resource.drawPanel(graphics, Resource.halfWidth - 55, Resource.halfHeight - 40, 110, 85);
                graphics.setColor(0xFFFFFF);
                graphics.setFont(Resource.sf);
                graphics.drawString("\u603b\u5206", Resource.halfWidth - 50, Resource.halfHeight - 30, 4 | 0x10);
                graphics.drawString("\u5956\u91d1", Resource.halfWidth - 50, Resource.halfHeight - 15, 4 | 0x10);
                graphics.drawString("\u5f53\u524d\u5206\u6570", Resource.halfWidth - 50, Resource.halfHeight + 15, 4 | 0x10);
                graphics.drawLine(Resource.halfWidth - 50, Resource.halfHeight + 7, Resource.halfWidth + 50, Resource.halfHeight + 7);
                graphics.drawString("" + this.getPoint + "", Resource.halfWidth + 50, Resource.halfHeight - 30, 8 | 0x10);
                graphics.drawString("" + this.bonusPoint + "", Resource.halfWidth + 50, Resource.halfHeight - 15, 8 | 0x10);
                graphics.drawString("" + Resource.pointMgr.getPoint() + "", Resource.halfWidth + 50, Resource.halfHeight + 15, 8 | 0x10);
                if (this.ani1.frameProcess() == 0) {
                    Resource.stageNum = 3;
                    Resource.saveGame();
                    Resource.aMainView.setStatus(0, 4);
                }
            }
        }
    }

    public void popupMenu() {
        this.bPopupMenu = true;
        this.menuLength = 4;
        this.menuSel = 0;
        this.popupmenuState = 0;
    }

    void keyMiniGame1(int n) {
        if (this.bHelp) {
            this.bHelp = false;
            return;
        }
        if (this.bPopupMenu) {
            this.keyPopupMenu(n);
            return;
        }
        if (this.state == 4) {
            // empty if block
        }
        if (!this.bCursor) {
            return;
        }
        switch (n) {
            case -8: 
            case -7: 
            case 48: {
                this.popupMenu();
                break;
            }
            case -3: 
            case 52: {
                this.curSelCard = this.curSelCard == 0 ? 4 : --this.curSelCard;
                Utils.playSound(5, false);
                break;
            }
            case -4: 
            case 54: {
                this.curSelCard = this.curSelCard == 19 ? 15 : ++this.curSelCard;
                Utils.playSound(5, false);
                break;
            }
            case -1: 
            case 50: {
                this.curSelCard = this.curSelCard < 5 ? (this.curSelCard += 15) : (this.curSelCard -= 5);
                Utils.playSound(5, false);
                break;
            }
            case -2: 
            case 56: {
                this.curSelCard = this.curSelCard > 14 ? (this.curSelCard -= 15) : (this.curSelCard += 5);
                Utils.playSound(5, false);
                break;
            }
            case -5: 
            case 53: {
                if (this.cards[this.curSelCard].state == 0) {
                    this.cards[this.curSelCard].turnFront();
                    if (this.prevSelCard != -1) {
                        this.bCheckTwoCard = true;
                        this.bCursor = false;
                        this.ani1.init(1, 500, false);
                        break;
                    }
                    this.prevSelCard = this.curSelCard;
                    break;
                }
                if (this.cards[this.curSelCard].state != 1) break;
                this.cards[this.curSelCard].turnBack();
                if (this.prevSelCard == -1) break;
                this.prevSelCard = -1;
            }
        }
    }

    void drawHelp1(Graphics graphics) {
        int n;
        int n2;
        int n3;
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
            n3 = 38;
            n2 = 30;
            n = 20;
        } else {
            n3 = 14;
            n2 = 15;
            n = 15;
        }
        graphics.setColor(148, 65, 24);
        graphics.setFont(Resource.sf);
        graphics.drawString("\u6e38\u620f\u76ee\u6807\u662f\u627e\u51fa", n4 + n3, n5 + n2 + n * 0, 4 | 0x10);
        graphics.drawString("\u76f8\u540c\u56fe\u6848\u7684\u5361\u7247\u3002", n4 + n3, n5 + n2 + n * 1, 4 | 0x10);
        graphics.drawString("\u901a\u8fc7\u8be5\u6e38\u620f\u4f60\u80fd", n4 + n3, n5 + n2 + n * 2, 4 | 0x10);
        graphics.drawString("\u83b7\u5f97\u989d\u5916\u7684\u5206\u6570\uff0c", n4 + n3, n5 + n2 + n * 3, 4 | 0x10);
        graphics.drawString("\u5206\u6570\u591a\u5c11\u53d6\u51b3\u4e8e", n4 + n3, n5 + n2 + n * 4, 4 | 0x10);
        graphics.drawString("\u6309\u65f6\u5b8c\u6210\u7684\u6bd4\u7387", n4 + n3, n5 + n2 + n * 5, 4 | 0x10);
    }

    void initMiniGame2() {
        this.cards = new MiniGameCard[16];
        for (int i = 0; i < 16; ++i) {
            this.cards[i] = new MiniGameCard();
        }
        this.makeQuestion(0);
        this.getPoint = 0;
        this.bonusPoint = 0;
        this.state = 1;
        this.ani1.init(2, 2000, false);
        this.bCursor = false;
        Utils.playSound(13, false);
    }

    void drawMiniGame2(Graphics graphics) {
        graphics.setColor(90, 8, 0);
        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        if (this.bHelp) {
            this.drawHelp2(graphics);
            return;
        }
        if (this.bPopupMenu) {
            this.drawPopupMenu(graphics);
            return;
        }
        int n = this.gameLevel == 0 ? 5 : (this.gameLevel == 1 ? 10 : (this.gameLevel == 2 ? 15 : 15));
        int n2 = Resource.isQVGA() ? 3 : 1;
        for (int i = 0; i < n; ++i) {
            this.cards[i].draw(graphics, this.card_x + i % 5 * (imgCard[0].getWidth() + n2), this.card_y + i / 5 * (imgCard[0].getHeight() + n2));
        }
        this.cards[15].draw(graphics, this.card_x + 2 * (imgCard[0].getWidth() + n2), this.card_y + 3 * (imgCard[0].getHeight() + n2));
        if (this.bCursor) {
            graphics.drawImage(this.imgArrow, this.card_x + (this.curSelCard % 5 * (imgCard[0].getWidth() + n2) + imgCard[0].getWidth() / 2), this.card_y + (this.curSelCard / 5 * (imgCard[0].getHeight() + n2) + imgCard[0].getHeight() / 2 + 5), 4 | 0x10);
        }
        if (Resource.isQVGA()) {
            if (Resource.lcdSize == 4) {
                graphics.drawImage(this.imgClock, Resource.halfWidth - 137, Resource.halfHeight + 77, 4 | 0x10);
                int n3 = Resource.halfWidth - 126;
                int n4 = Resource.halfHeight + 75;
                int n5 = (50 - this.aniGameTime.getFrame()) / 10 * 28;
                graphics.setColor(255, 82, 90);
                graphics.drawLine(n3, n4, n3, n4 - n5);
                graphics.drawLine(n3 + 4, n4, n3 + 4, n4 - n5);
                graphics.setColor(255, 115, 123);
                graphics.drawLine(n3 + 1, n4, n3 + 1, n4 - n5);
                graphics.drawLine(n3 + 3, n4, n3 + 3, n4 - n5);
                graphics.setColor(255, 255, 255);
                graphics.drawLine(n3 + 2, n4, n3 + 2, n4 - n5);
                graphics.setColor(246, 49, 65);
                graphics.drawLine(n3 + 5, n4, n3 + 5, n4 - n5);
                graphics.drawLine(n3 + 7, n4, n3 + 7, n4 - n5);
                graphics.setColor(197, 32, 41);
                graphics.drawLine(n3 + 6, n4, n3 + 6, n4 - n5);
            } else {
                graphics.drawImage(this.imgClock, Resource.halfWidth - 96, Resource.halfHeight - 130, 4 | 0x10);
                int n6 = Resource.halfWidth - 67;
                int n7 = Resource.halfHeight - 121;
                int n8 = (50 - this.aniGameTime.getFrame()) / 10 * 28;
                graphics.setColor(255, 82, 90);
                graphics.drawLine(n6, n7, n6 + n8, n7);
                graphics.drawLine(n6, n7 + 4, n6 + n8, n7 + 4);
                graphics.setColor(255, 115, 123);
                graphics.drawLine(n6, n7 + 1, n6 + n8, n7 + 1);
                graphics.drawLine(n6, n7 + 3, n6 + n8, n7 + 3);
                graphics.setColor(255, 255, 255);
                graphics.drawLine(n6, n7 + 2, n6 + n8, n7 + 2);
                graphics.setColor(246, 49, 65);
                graphics.drawLine(n6, n7 + 5, n6 + n8, n7 + 5);
                graphics.drawLine(n6, n7 + 7, n6 + n8, n7 + 7);
                graphics.setColor(197, 32, 41);
                graphics.drawLine(n6, n7 + 6, n6 + n8, n7 + 6);
            }
        } else {
            graphics.drawImage(this.imgClock, Resource.halfWidth - 54, Resource.halfHeight - 66, 4 | 0x10);
            int n9 = Resource.halfWidth - 38;
            int n10 = Resource.halfHeight - 59;
            int n11 = (50 - this.aniGameTime.getFrame()) / 10 * 12;
            graphics.setColor(255, 82, 90);
            graphics.drawLine(n9, n10, n9 + n11, n10);
            graphics.drawLine(n9, n10 + 2, n9 + n11, n10 + 2);
            graphics.setColor(255, 115, 123);
            graphics.drawLine(n9, n10 + 1, n9 + n11, n10 + 1);
            graphics.setColor(246, 49, 65);
            graphics.drawLine(n9, n10 + 3, n9 + n11, n10 + 3);
        }
        if (this.state == 1) {
            if (this.ani1.getFrame() == 0) {
                graphics.drawImage(this.imgReady[0], Resource.halfWidth, Resource.halfHeight, 1 | 2);
            } else if (this.ani1.getFrame() == 1) {
                graphics.drawImage(this.imgReady[1], Resource.halfWidth, Resource.halfHeight, 1 | 2);
            }
            if (this.ani1.frameProcess() == 0) {
                this.ani1.init(10, 900, false);
                this.bPrevCard = true;
                this.state = 3;
                if (bNewGame2) {
                    this.bHelp = true;
                    bNewGame2 = false;
                    Resource.saveGame();
                }
            }
        }
        if (this.state == 3) {
            if (this.bPrevCard) {
                if (this.ani1.getFrame() < 5) {
                    this.cards[0 + this.ani1.getFrame()].turnFront();
                    this.cards[5 + this.ani1.getFrame()].turnFront();
                    this.cards[10 + this.ani1.getFrame()].turnFront();
                } else {
                    this.cards[0 + this.ani1.getFrame() - 5].turnBack();
                    this.cards[5 + this.ani1.getFrame() - 5].turnBack();
                    this.cards[10 + this.ani1.getFrame() - 5].turnBack();
                }
                if (this.ani1.frameProcess() == 0) {
                    this.bPrevCard = false;
                    this.bCursor = true;
                    this.cards[15].turnFront();
                }
            } else if (this.bCheckTwoCard) {
                if (this.cards[this.curSelCard].item == this.cards[15].item) {
                    graphics.drawImage(this.imgOX[0], Resource.halfWidth, Resource.halfHeight, 1 | 2);
                } else {
                    graphics.drawImage(this.imgOX[1], Resource.halfWidth, Resource.halfHeight, 1 | 2);
                    if (this.ani1.getFrame() == 3) {
                        this.cards[this.cards[15].item].turnFront();
                    }
                }
                if (this.ani1.frameProcess() == 0) {
                    this.makeQuestion(this.gameLevel + 1);
                }
            } else if (this.bTimeOver) {
                graphics.drawImage(this.imgOX[1], Resource.halfWidth, Resource.halfHeight, 1 | 2);
                if (this.ani1.getFrame() == 0) {
                    this.cards[this.cards[15].item].turnFront();
                }
                if (this.ani1.frameProcess() == 0) {
                    this.makeQuestion(this.gameLevel + 1);
                }
            } else if (this.aniGameTime.frameProcess() == 0) {
                this.bTimeOver = true;
                this.ani1.init(2, 500, false);
                this.bCursor = false;
                Utils.playSound(18, false);
            }
        } else if (this.state == 4) {
            Resource.drawPanel(graphics, Resource.halfWidth - 55, Resource.halfHeight - 40, 110, 85);
            graphics.setColor(0xFFFFFF);
            graphics.setFont(Resource.sf);
            graphics.drawString("\u603b\u5206", Resource.halfWidth - 50, Resource.halfHeight - 30, 4 | 0x10);
            graphics.drawString("\u5956\u91d1", Resource.halfWidth - 50, Resource.halfHeight - 15, 4 | 0x10);
            graphics.drawString("\u5f53\u524d\u5206\u6570", Resource.halfWidth - 50, Resource.halfHeight + 15, 4 | 0x10);
            graphics.drawLine(Resource.halfWidth - 50, Resource.halfHeight + 7, Resource.halfWidth + 50, Resource.halfHeight + 7);
            graphics.drawString("" + this.getPoint + "", Resource.halfWidth + 50, Resource.halfHeight - 30, 8 | 0x10);
            graphics.drawString("" + this.bonusPoint + "", Resource.halfWidth + 50, Resource.halfHeight - 15, 8 | 0x10);
            graphics.drawString("" + Resource.pointMgr.getPoint() + "", Resource.halfWidth + 50, Resource.halfHeight + 15, 8 | 0x10);
            if (this.ani1.frameProcess() == 0) {
                Resource.stageNum = 5;
                Resource.saveGame();
                Resource.aMainView.setStatus(0, 4);
            }
        }
    }

    void keyMiniGame2(int n) {
        if (this.bHelp) {
            this.bHelp = false;
            return;
        }
        if (this.bPopupMenu) {
            this.keyPopupMenu(n);
            return;
        }
        if (this.state == 4) {
            // empty if block
        }
        if (!this.bCursor) {
            return;
        }
        switch (n) {
            case -8: 
            case -7: 
            case 48: {
                this.popupMenu();
                break;
            }
            case -3: 
            case 52: {
                this.curSelCard = this.curSelCard == 0 ? 4 : --this.curSelCard;
                Utils.playSound(5, false);
                break;
            }
            case -4: 
            case 54: {
                this.curSelCard = this.curSelCard == 14 ? 10 : ++this.curSelCard;
                Utils.playSound(5, false);
                break;
            }
            case -1: 
            case 50: {
                this.curSelCard = this.curSelCard < 5 ? (this.curSelCard += 10) : (this.curSelCard -= 5);
                Utils.playSound(5, false);
                break;
            }
            case -2: 
            case 56: {
                this.curSelCard = this.curSelCard > 9 ? (this.curSelCard -= 10) : (this.curSelCard += 5);
                Utils.playSound(5, false);
                break;
            }
            case -5: 
            case 53: {
                if (!this.cards[this.curSelCard].turnFront()) break;
                this.bCheckTwoCard = true;
                this.ani1.init(8, 200, false);
                this.bCursor = false;
                if (this.cards[this.curSelCard].item == this.cards[15].item) {
                    if (this.gameLevel == 0) {
                        Resource.pointMgr.addPoint(20);
                        this.getPoint += 20;
                    } else if (this.gameLevel == 1) {
                        Resource.pointMgr.addPoint(30);
                        this.getPoint += 30;
                    } else if (this.gameLevel == 2) {
                        Resource.pointMgr.addPoint(50);
                        this.getPoint += 50;
                    }
                    Utils.playSound(19, false);
                    break;
                }
                Utils.playSound(20, false);
            }
        }
    }

    void makeQuestion(int n) {
        int n2;
        int n3;
        this.bCheckTwoCard = false;
        this.bTimeOver = false;
        this.gameLevel = n;
        if (n == 0) {
            n3 = 5;
        } else if (n == 1) {
            n3 = 10;
        } else if (n == 2) {
            n3 = 15;
        } else {
            if (this.getPoint == 100) {
                this.bonusPoint = 100;
                Resource.pointMgr.addPoint(100);
            }
            this.state = 4;
            this.ani1.init(1, 2000, false);
            Utils.playSound(18, false);
            return;
        }
        int[] nArray = new int[n3];
        for (n2 = 0; n2 < 16; ++n2) {
            this.cards[n2].init(-1);
        }
        for (n2 = 0; n2 < n3; ++n2) {
            int n4;
            boolean bl;
            block2: do {
                n4 = Resource.getRand(25);
                bl = true;
                for (int i = 0; i < n2; ++i) {
                    if (nArray[i] != n4) continue;
                    bl = false;
                    continue block2;
                }
            } while (!bl);
            nArray[n2] = n4;
            this.cards[n2].init(n2);
        }
        this.cards[15].init(Resource.getRand(n3));
        imgPiece = new Image[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            MiniGameView.imgPiece[n2] = Resource.loadImage("/dia_" + nArray[n2] + "_n.png");
        }
        nArray = null;
        this.ani1.init(10, 900, false);
        this.bPrevCard = true;
        this.bCursor = false;
        this.aniGameTime.init(50, 100, false);
        this.curSelCard = 0;
        System.gc();
    }

    void drawHelp2(Graphics graphics) {
        int n;
        int n2;
        int n3;
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
            graphics.setFont(Resource.sf);
            n3 = 38;
            n2 = 30;
            n = 20;
        } else {
            n3 = 14;
            n2 = 15;
            n = 15;
        }
        graphics.setColor(148, 65, 24);
        graphics.drawString("\u6e38\u620f\u76ee\u6807\u662f\u627e\u51fa", n4 + n3, n5 + n2 + n * 0, 4 | 0x10);
        graphics.drawString("\u76f8\u540c\u56fe\u6848\u7684\u5361\u7247\u3002", n4 + n3, n5 + n2 + n * 1, 4 | 0x10);
        graphics.drawString("\u901a\u8fc7\u8be5\u6e38\u620f\u4f60\u80fd", n4 + n3, n5 + n2 + n * 2, 4 | 0x10);
        graphics.drawString("\u83b7\u5f97\u989d\u5916\u7684\u5206\u6570\uff0c", n4 + n3, n5 + n2 + n * 3, 4 | 0x10);
        graphics.drawString("\u5206\u6570\u591a\u5c11\u53d6\u51b3\u4e8e", n4 + n3, n5 + n2 + n * 4, 4 | 0x10);
        graphics.drawString("\u6309\u65f6\u5b8c\u6210\u7684\u6bd4\u7387", n4 + n3, n5 + n2 + n * 5, 4 | 0x10);
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
            } else if (this.popupmenuState == 1) {
                Resource.aMainView.setStatus(0, 1);
            }
        } else if (this.menuSel == 1) {
            if (this.popupmenuState == 0) {
                Graphics graphics = Resource.getBackBuffer();
                graphics.setColor(90, 8, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                Resource.aMainView.openPopup(6);
            } else if (this.popupmenuState == 1) {
                this.popupmenuState = 0;
                this.menuSel = 3;
                this.menuLength = 4;
            }
        } else if (this.menuSel == 2) {
            if (this.popupmenuState == 0) {
                this.bHelp = true;
            }
        } else if (this.menuSel == 3 && this.popupmenuState == 0) {
            this.popupmenuState = 1;
            this.menuSel = 1;
            this.menuLength = 2;
        }
    }

    void drawPopupMenu(Graphics graphics) {
        int n;
        int n2;
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
        if (Resource.isQVGA()) {
            n2 = 190;
            n = 166;
        } else {
            n2 = 110;
            n = 100;
        }
        graphics.fillRect(n3 + 1, n4, n2 - 1, n + 1);
        graphics.fillRect(n3, n4 + 1, n2 + 1, n - 1);
        graphics.setColor(255, 230, 209);
        if (Resource.isQVGA()) {
            graphics.fillRect(n3 + 2, n4 + 2, 187, 163);
            graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 22, n4 - 21, 4 | 0x10);
        } else {
            graphics.fillRect(n3 + 2, n4 + 2, 107, 97);
            graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 11, n4 - 9, 4 | 0x10);
        }
        if (this.popupmenuState == 0) {
            int n5;
            if (Resource.isQVGA()) {
                graphics.setFont(Resource.sf);
                n2 = 58;
                n = 30;
                n5 = 20;
            } else {
                n2 = 24;
                n = 15;
                n5 = 15;
            }
            graphics.setColor(148, 65, 24);
            graphics.drawString("1.\u7ee7\u7eed\u6e38\u620f", n3 + n2, n4 + n + n5 * 0, 4 | 0x10);
            graphics.drawString("2.\u6e38\u620f\u8bbe\u7f6e", n3 + n2, n4 + n + n5 * 1, 4 | 0x10);
            graphics.drawString("3.\u6e38\u620f\u5e2e\u52a9", n3 + n2, n4 + n + n5 * 2, 4 | 0x10);
            graphics.drawString("4.\u7ed3\u675f\u6e38\u620f", n3 + n2, n4 + n + n5 * 3, 4 | 0x10);
            if (Resource.isQVGA()) {
                graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + n + this.menuSel * n5, 4 | 0x10);
            } else {
                graphics.drawImage(this.imgPopupMenu[1], n3 + 7, n4 + n + this.menuSel * n5, 4 | 0x10);
            }
        }
        if (this.popupmenuState == 1) {
            graphics.setColor(148, 65, 24);
            if (Resource.isQVGA()) {
                graphics.setFont(Resource.sf);
                graphics.drawString("\u7ed3\u675f\u6e38\u620f ?", n3 + 58, n4 + 30, 4 | 0x10);
                graphics.drawString("1.\u662f", n3 + 58, n4 + 50, 4 | 0x10);
                graphics.drawString("2.\u5426", n3 + 58, n4 + 70, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + 50 + this.menuSel * 20, 4 | 0x10);
            } else {
                graphics.drawString("\u7ed3\u675f\u6e38\u620f ?", n3 + 12, n4 + 15, 4 | 0x10);
                graphics.drawString("1.\u662f", n3 + 24, n4 + 30, 4 | 0x10);
                graphics.drawString("2.\u5426", n3 + 24, n4 + 45, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 7, n4 + 30 + this.menuSel * 15, 4 | 0x10);
            }
        }
    }
}
