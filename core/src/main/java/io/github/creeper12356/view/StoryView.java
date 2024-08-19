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

public class StoryView
implements XTimerListener,
Viewable {
    public static final int STORY_OPENING = 1;
    public static final int STORY_FAILED = 2;
    public static final int STORY_END = 3;
    static final String[] strOpen = new String[]{"\u516c\u5143\u4e8c\u96f6\u4e8c\u96f6\u5e74\uff0c\u5404\u79cd\u798f\u5229\u8bbe\u65bd\u5341\u5206\u9f50\u5168\uff0c\u4e16\u754c\u53d8\u5f97\u5982\u6b64\u8212\u9002\uff0c\u6240\u6709\u4eba\u90fd\u53ef\u4ee5\u4eab\u53d7\u5230\uff01\u4f46\u4eba\u4eec\u5374\u53d8\u5f97\u8d8a\u6765\u8d8a\u8fdf\u949d\u548c\u611a\u8822\uff0c\u4e14\u4e0d\u613f\u610f\u5f00\u52a8\u52a8\u8111\u7b4b\u3002", "\u52a9\u7406\uff1a\u603b\u7406\u5148\u751f\uff0c\u4eba\u4eec\u90fd\u53d8\u5f97\u611a\u8822\u4e86!", "\u653f\u5e9c\u5fc5\u987b\u5f97\u505a\u70b9\u4e8b\u60c5\uff01", "\u603b\u7406\uff1a\u8ba9\u4eba\u4eec\u73a9\u8df3\u68cb\u5427\u3002", "\u901a\u8fc7\u6cd5\u5f8b\uff0c\u5f3a\u5236\u4eba\u4eec\u73a9\u8df3\u68cb\u6765\u953b\u70bc\u4ed6\u4eec\u7684\u8111\u5b50\u4e3a\u4e86\u4f7f\u8df3\u68cb\u66f4\u53d7\u6b22\u8fce\uff0c\u653f\u5e9c\u51b3\u5b9a\u8ba9\u6700\u540e\u7684\u51a0\u519b\u613f\u671b\u6210\u771f\uff0c\u8fd9\u4e2a\u51b3\u5b9a\u53ef\u4ee5\u8ba9\u4eba\u4eec\u66f4\u591a\u7684\u73a9\u8fd9\u4e2a\u6e38\u620f"};
    static final String[] strSPEECH = new String[]{"\u54c7\uff01\u5feb\u770b\u62a5\u7eb8!", "\u65b0\u95fb\u62a5\u9053:\u516c\u5f00\u7ade\u8d5b\u8df3\u68cb\uff0c\u6700\u540e\u7684\u8d62\u5bb6\u5c06\u53ef\u4ee5\u68a6\u60f3\u6210\u771f\u3002", "\u6211\u5c06\u6210\u4e3a\u6700\u540e\u7684\u8d62\u5bb6\uff0c\u6211\u53ef\u4ee5\u8ba9\u6211\u7684\u68a6\u60f3\u53d8\u6210\u73b0\u5b9e\uff0c\u4f60\u77e5\u9053\u6211\u7684\u68a6\u60f3\u662f...", "\u7b49\u7740\u5427\uff0c\u6211\u8d62\u4e86\u540e\uff0c\u4f1a\u8ba9\u4f60\u77e5\u9053\u7684!", "\u54c7\uff01\u4f60\u770b\u5230\u57ce\u5821\u4e2d\u7684\u8df3\u68cb\u7ade\u8d5b\u4e86\u4e48\uff1f", "\u592a\u76db\u5927\u4e86\uff01", "\u8fd9\u662f\u4e2a\u597d\u5730\u65b9\uff0c\u6211\u611f\u5230\u7d27\u5f20!", "\u4f60\u770b\u4e0a\u53bb\u50cf\u662f\u6700\u540e\u52a0\u5165\u7684\u5019\u9009\u4eba.\u770b\u8d77\u6765\u6240\u6709\u7684\u9009\u624b\u90fd\u5728\u8fd9\u4e86\uff0c\u73b0\u5728\u8ba9\u6211\u4eec\u5f00\u59cb\u6bd4\u8d5b\u5427\u5982\u679c\u4f60\u6210\u4e3a\u4e86\u7ade\u8d5b\u6700\u540e\u80dc\u5229\u7684\u9009\u624b\uff0c\u4f60\u5fc5\u987b\u548c\u6211\u5bf9\u6297\u5b8c\u6210\u6700\u540e\u4e00\u8f6e\u7684\u7ade\u8d5b\uff0c\u5982\u679c\u4f60\u5728\u6700\u540e\u4e00\u8f6e\u4e2d\u80dc\u5229\u4e86\uff0c\u4f60\u5c06\u8d62\u5f97\u8df3\u68cb\u4e4b\u738b\u4e0e\u94bb\u77f3\u7687\u51a0\u5f00\u59cb\u6bd4\u8d5b\uff01\u6211\u4eec\u9f13\u638c\uff01", "\u4f60\u592a\u68d2\u4e86\uff01\u73b0\u5728\u6211\u5c06\u94bb\u77f3\u7687\u51a0\u8d60\u4e0e\u4f60\uff0c\u901a\u8fc7\u603b\u7406\u7684\u6307\u4ee4\u53ef\u4ee5\u628a\u4f60\u7684\u613f\u671b\u53d8\u6210\u73b0\u5b9e\uff0c\u8bf7\u544a\u8bc9\u6211\u4f60\u7684\u613f\u671b!", "\u6211\u7684\u613f\u671b\uff0c\u6211\u7684\u613f\u671b\u662f\uff0c\u6211\u60f3\u5403\u5c3d\u53ef\u80fd\u591a\u7684\u9762\u6761\uff0c\u8bf7\u4e70\u7ed9\u6211!", "\u54ce\u5440\uff01", "\u6700\u540e\u7684\u8d62\u5bb6\u8fc7\u5ea6\u996e\u98df\u800c\u5bfc\u81f4\u4f4f\u9662\uff0c\u66b4\u996e\u66b4\u98df\u662f\u4e0d\u597d\u7684\u3002"};
    int state;
    int scene;
    int frame;
    int[] cloudPos = new int[4];
    Animation aniFrame = new Animation();
    Animation aniFrame2 = new Animation();
    Image[] imgBGChip = new Image[4];
    Image[] imgChar = new Image[2];
    Image[] imgCharBig = new Image[2];
    Image[] imgNPC = new Image[2];
    Image[] imgScene;
    Image imgSkip;
    StringMgr strmgr;
    int[] rainPosX;
    int[] rainPosY;
    boolean playonce = false;

    StoryView() {
    }

    public void paint(Graphics graphics) {
        TouchDo.setTouchArea(3, this.state, this.scene);
        switch (this.state) {
            case 1: {
                this.drawOpening(graphics);
                graphics.drawImage(this.imgSkip, Resource.totalWidth - 1 - 2, Resource.totalHeight - 1 - 10, 40);
                break;
            }
            case 2: {
                this.drawFailed(graphics);
                break;
            }
            case 3: {
                this.drawEnding(graphics);
            }
        }
    }

    public void keyPressed(int n) {
        int n2 = -1;
        if ((n == -5 || n == -6 || n == 53) && this.strmgr != null && this.strmgr.bDraw) {
            n2 = this.strmgr.keyReturn();
        }
        if (this.state == 1) {
            if (n == 35) {
                Utils.stopSound();
                Resource.stageNum = 1;
                Resource.saveGame();
                Resource.aMainView.setStatus(0, 4);
            } else if ((n == -5 || n == -6 || n == 53) && n2 == 0) {
                if (this.scene == 0) {
                    this.nextScene();
                } else if (this.scene == 12) {
                    if (this.frame == 0) {
                        this.aniFrame.init(1, 2000, false);
                        this.aniFrame2.init(2, 100, true);
                        this.frame = 1;
                    } else if (this.frame == 2) {
                        this.aniFrame.init(1, 2000, false);
                        this.strmgr = new StringMgr(strOpen[3], 18, 3);
                        this.strmgr.start();
                        this.frame = 3;
                    } else if (this.frame == 3) {
                        Graphics graphics = Resource.getBackBuffer();
                        graphics.setColor(0, 0, 0);
                        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                        this.strmgr = new StringMgr(strOpen[4], 18, 3);
                        this.strmgr.start();
                        this.frame = 4;
                    } else if (this.frame == 4) {
                        this.nextScene();
                    }
                } else if (this.scene == 13) {
                    if (this.frame == 0) {
                        this.frame = 1;
                        this.strmgr = null;
                        Utils.playSound(7, true);
                    }
                } else if (this.scene == 1) {
                    if (this.frame == 1) {
                        this.strmgr = new StringMgr(strSPEECH[1], 14, 3);
                        this.strmgr.start();
                        this.frame = 2;
                    } else if (this.frame == 2) {
                        this.strmgr = new StringMgr(strSPEECH[2], 14, 3);
                        this.strmgr.start();
                        this.frame = 3;
                    } else if (this.frame == 3) {
                        this.strmgr = new StringMgr(strSPEECH[3], 14, 3);
                        this.strmgr.start();
                        this.frame = 4;
                    } else if (this.frame == 4) {
                        this.strmgr = null;
                        this.frame = 5;
                        this.aniFrame.init(2, 400, false);
                    }
                } else if (this.scene == 2) {
                    if (this.frame == 0) {
                        this.strmgr = new StringMgr(strSPEECH[5], 14, 3);
                        this.strmgr.start();
                        this.frame = 1;
                    } else if (this.frame == 1) {
                        this.strmgr = null;
                        this.aniFrame.init(2, 400, false);
                        this.frame = 2;
                    } else if (this.frame == 2) {
                        this.nextScene();
                    }
                } else if (this.scene == 3) {
                    if (this.frame == 0) {
                        this.frame = 1;
                        this.aniFrame.init(2, 400, false);
                    }
                } else if (this.scene == 4 && this.frame == 0) {
                    this.frame = 1;
                    this.aniFrame.init(2, 400, false);
                }
            }
        } else if (this.state == 2) {
            if ((n == -5 || n == -6 || n == 53) && this.scene == 0) {
                this.scene = 1;
            }
            if (this.scene == 1) {
                if (n == 49) {
                    Resource.aMainView.setStatus(0, 4);
                } else if (n == 50) {
                    Resource.aMainView.setStatus(0, 1);
                }
            }
        } else if (this.state == 3 && (n == -5 || n == -6 || n == 53) && n2 == 0) {
            if (this.scene == 0) {
                if (this.frame == 0) {
                    this.strmgr = new StringMgr(strSPEECH[9], 14, 3);
                    this.strmgr.start();
                    this.frame = 1;
                } else if (this.frame == 1) {
                    this.strmgr = new StringMgr(strSPEECH[10], 14, 3);
                    this.strmgr.start();
                    this.frame = 2;
                } else if (this.frame == 2) {
                    this.strmgr = null;
                    this.frame = 3;
                    this.aniFrame.init(2, 400, false);
                }
            } else if (this.scene == 2) {
                Resource.aMainView.setStatus(0, 5);
            }
        }
    }

    public void keyReleased(int n) {
    }

    public void init() {
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.setTime(100L);
        Resource.aTimer2.resume();
        this.scene = 0;
        this.frame = 0;
        if (this.state == 1) {
            Resource.newGame();
            Resource.saveGame();
            this.aniFrame.init(21, 800, false);
            try {
                this.imgBGChip[0] = Image.createImage((String)"/bgchip_3.png");
                this.imgBGChip[1] = Image.createImage((String)"/bgchip_1.png");
                this.imgBGChip[2] = Image.createImage((String)"/bgchip_0.png");
                this.imgBGChip[3] = Image.createImage((String)"/bgchip_2.png");
                this.imgChar[0] = Image.createImage((String)"/char_0_n.png");
                this.imgChar[1] = Image.createImage((String)"/char_6_n.png");
                this.imgNPC[0] = Image.createImage((String)"/npc_0.png");
                this.imgCharBig[0] = Image.createImage((String)"/charbig.png");
                this.imgCharBig[1] = Image.createImage((String)"/charbigface.png");
                this.imgSkip = Image.createImage((String)"/skip.png");
                this.initScene(11);
            }
            catch (IOException iOException) {}
        } else if (this.state == 2) {
            this.drawFailedBG();
            try {
                this.imgBGChip[0] = Image.createImage((String)"/failed_0.png");
                this.imgBGChip[1] = Image.createImage((String)"/failed_1.png");
                this.imgCharBig[0] = Image.createImage((String)"/failed_2.png");
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.rainPosX = new int[5];
            this.rainPosY = new int[5];
            this.aniFrame.init(2, 500, true);
            this.aniFrame2.init(1, 0, false);
            Utils.playSound(11, false);
        } else if (this.state == 3) {
            Resource.drawInCastle(Resource.getBackBuffer());
            try {
                this.imgChar[0] = Image.createImage((String)"/char_0_n.png");
                this.imgChar[1] = Image.createImage((String)"/char_6_n.png");
                this.imgNPC[1] = Image.createImage((String)"/char_0_good.png");
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.initScene(1);
            this.strmgr = new StringMgr(strSPEECH[8], 14, 3);
            this.strmgr.start();
            Utils.playSound(7, true);
        }
    }

    public void clear() {
        int n;
        for (n = 0; n < 4; ++n) {
            this.imgBGChip[n] = null;
        }
        for (n = 0; n < 2; ++n) {
            this.imgChar[n] = null;
            this.imgCharBig[n] = null;
            this.imgNPC[n] = null;
        }
        this.rainPosX = null;
        this.rainPosY = null;
        if (Resource.aTimer2.isActivate()) {
            Resource.aTimer2.cancel();
        }
        try {
            Thread.sleep(200L);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setStatus(int n) {
        this.state = n;
    }

    public int getStatus() {
        return this.state;
    }

    public void performed(XTimer xTimer) {
        if (this.state == 1 && this.scene == 2) {
            for (int i = 0; i < 2; ++i) {
                int n = i;
                this.cloudPos[n] = this.cloudPos[n] - 2;
                if (this.cloudPos[i] >= 0 - this.imgBGChip[1].getWidth()) continue;
                this.cloudPos[i] = Resource.totalWidth + 10;
                if (i == 0) {
                    this.cloudPos[2] = Resource.halfHeight - 35 + Resource.getRand(30);
                    continue;
                }
                this.cloudPos[3] = Resource.halfHeight + 5 + Resource.getRand(15);
            }
        }
        Resource.aMainView.repaint();
    }

    void nextScene() {
        if (this.state == 1) {
            if (this.scene == 0) {
                this.initScene(12);
                this.scene = 12;
                this.frame = 0;
            } else if (this.scene == 12) {
                this.imgScene = null;
                System.gc();
                Utils.stopSound();
                this.strmgr = new StringMgr(strSPEECH[0], 14, 3);
                this.scene = 1;
                this.frame = 0;
                this.aniFrame.init(12, 100, true);
                this.aniFrame2.init(55, 100, false);
            } else if (this.scene == 1) {
                this.initScene(13);
                this.scene = 13;
                this.frame = 0;
                this.aniFrame.init(1, 2000, false);
            } else if (this.scene == 2) {
                this.strmgr = new StringMgr(strSPEECH[6], 14, 3);
                this.strmgr.start();
                Resource.drawInCastle(Resource.getBackBuffer());
                this.scene = 3;
                this.frame = 0;
                Utils.playSound(7, true);
            } else if (this.scene == 3) {
                this.strmgr = new StringMgr(strSPEECH[7], 14, 3);
                this.strmgr.start();
                this.initScene(0);
                this.scene = 4;
                this.frame = 0;
            } else if (this.scene == 4) {
                Utils.stopSound();
                Resource.stageNum = 1;
                Resource.saveGame();
                Resource.aMainView.setStatus(0, 4);
            }
        } else if (this.state == 3 && this.scene == 0) {
            this.scene = 2;
            this.frame = 0;
            this.strmgr = new StringMgr(strSPEECH[11], 18, 3);
            this.strmgr.start();
        }
    }

    void initScene(int n) {
        boolean bl = Resource.isQVGA();
        switch (n) {
            case 11: {
                int n2;
                this.imgScene = new Image[3];
                try {
                    this.imgScene[0] = Image.createImage((String)"/bgchip_14.png");
                    this.imgScene[1] = Image.createImage((String)"/openchip_00.png");
                    this.imgScene[2] = Image.createImage((String)"/openchip_01.png");
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                Graphics graphics = Resource.getBackBuffer();
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                int n3 = Resource.isQVGA() ? 108 : 54;
                graphics.setColor(160, 178, 163);
                graphics.fillRect(0, Resource.halfHeight - n3, Resource.totalWidth, n3);
                for (int i = 0; i < Resource.totalWidth; i += this.imgScene[0].getWidth()) {
                    graphics.drawImage(this.imgScene[0], i, Resource.halfHeight - n3, 4 | 0x10);
                }
                if (Resource.isQVGA()) {
                    n2 = 96;
                    n3 = 28;
                } else {
                    n2 = 48;
                    n3 = 14;
                }
                graphics.drawImage(this.imgScene[1], Resource.halfWidth - n2, Resource.halfHeight, 4 | 0x20);
                graphics.drawImage(this.imgScene[2], Resource.halfWidth + n3, Resource.halfHeight, 4 | 0x20);
                this.imgScene = null;
                System.gc();
                this.strmgr = new StringMgr(strOpen[0], 18, 3);
                this.strmgr.start();
                break;
            }
            case 12: {
                this.imgScene = new Image[5];
                try {
                    this.imgScene[0] = Image.createImage((String)"/openchip_02.png");
                    this.imgScene[1] = Image.createImage((String)"/openchip_03.png");
                    this.imgScene[2] = Image.createImage((String)"/openchip_05.png");
                    this.imgScene[3] = Image.createImage((String)"/openchip_04.png");
                    this.imgScene[4] = Image.createImage((String)"/openchip_15.png");
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                Graphics graphics = Resource.getBackBuffer();
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                for (int i = 0; i < Resource.totalWidth; i += this.imgScene[2].getWidth()) {
                    graphics.drawImage(this.imgScene[2], i, Resource.halfHeight, 4 | 0x20);
                }
                if (Resource.isQVGA()) {
                    graphics.drawImage(this.imgScene[0], Resource.halfWidth - 78, Resource.halfHeight, 4 | 0x20);
                } else {
                    graphics.drawImage(this.imgScene[0], Resource.halfWidth - 39, Resource.halfHeight, 4 | 0x20);
                }
                this.strmgr = new StringMgr(strOpen[1], 18, 3);
                this.strmgr.start();
                Utils.playSound(14, true);
                break;
            }
            case 13: {
                int n4;
                this.imgScene = new Image[9];
                try {
                    this.imgScene[0] = Image.createImage((String)"/openchip_06.png");
                    this.imgScene[1] = Image.createImage((String)"/openchip_07.png");
                    this.imgScene[2] = Image.createImage((String)"/openchip_08.png");
                    this.imgScene[3] = Image.createImage((String)"/openchip_09.png");
                    this.imgScene[4] = Image.createImage((String)"/openchip_10.png");
                    this.imgScene[5] = Image.createImage((String)"/openchip_11.png");
                    this.imgScene[6] = Image.createImage((String)"/openchip_12.png");
                    this.imgScene[7] = Image.createImage((String)"/openchip_13.png");
                    this.imgScene[8] = Image.createImage((String)"/openchip_14.png");
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                int n5 = Resource.isQVGA() ? 24 : 12;
                Graphics graphics = Resource.getBackBuffer();
                graphics.setColor(200, 222, 203);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.halfHeight + n5);
                graphics.setColor(246, 189, 123);
                graphics.fillRect(0, Resource.halfHeight + n5, Resource.totalWidth, Resource.totalHeight);
                for (n4 = 0; n4 < Resource.totalWidth; n4 += this.imgScene[0].getWidth()) {
                    graphics.drawImage(this.imgScene[0], n4, Resource.halfHeight - n5, 4 | 0x10);
                }
                graphics.setColor(160, 178, 163);
                graphics.fillRect(0, Resource.halfHeight, Resource.totalWidth, n5);
                n5 = Resource.isQVGA() ? 16 : 8;
                for (n4 = 0; n4 < Resource.totalWidth; n4 += this.imgScene[1].getWidth()) {
                    graphics.drawImage(this.imgScene[1], n4, Resource.halfHeight + n5, 4 | 0x10);
                }
                if (Resource.isQVGA()) {
                    graphics.drawImage(this.imgScene[5], Resource.halfWidth - 82, Resource.halfHeight - 28, 4 | 0x10);
                    graphics.drawImage(this.imgScene[5], Resource.halfWidth - 24, Resource.halfHeight - 58, 4 | 0x10);
                    graphics.drawImage(this.imgScene[6], Resource.halfWidth - 12, Resource.halfHeight - 18, 4 | 0x10);
                    graphics.drawImage(this.imgScene[7], Resource.halfWidth + 46, Resource.halfHeight - 50, 4 | 0x10);
                    graphics.drawImage(this.imgScene[8], Resource.halfWidth + 12, Resource.halfHeight - 2, 4 | 0x10);
                    graphics.drawImage(this.imgScene[8], Resource.halfWidth + 12, Resource.halfHeight - 2, 4 | 0x10);
                    if (Resource.lcdSize == 4) {
                        graphics.drawImage(this.imgScene[3], Resource.halfWidth - 48, Resource.halfHeight - 115, 4 | 0x10);
                        graphics.drawImage(this.imgScene[4], Resource.halfWidth, Resource.halfHeight - 97, 4 | 0x10);
                    } else {
                        graphics.drawImage(this.imgScene[3], Resource.halfWidth - 48, Resource.halfHeight - 128, 4 | 0x10);
                        graphics.drawImage(this.imgScene[4], Resource.halfWidth, Resource.halfHeight - 106, 4 | 0x10);
                    }
                    graphics.drawImage(this.imgScene[2], Resource.halfWidth - 12, Resource.halfHeight + 26, 4 | 0x10);
                    graphics.drawImage(this.imgScene[2], Resource.halfWidth + 28, Resource.halfHeight - 28, 4 | 0x10);
                } else {
                    graphics.drawImage(this.imgScene[5], Resource.halfWidth - 41, Resource.halfHeight - 14, 4 | 0x10);
                    graphics.drawImage(this.imgScene[5], Resource.halfWidth - 12, Resource.halfHeight - 29, 4 | 0x10);
                    graphics.drawImage(this.imgScene[6], Resource.halfWidth - 6, Resource.halfHeight - 9, 4 | 0x10);
                    graphics.drawImage(this.imgScene[7], Resource.halfWidth + 23, Resource.halfHeight - 25, 4 | 0x10);
                    graphics.drawImage(this.imgScene[8], Resource.halfWidth + 6, Resource.halfHeight - 1, 4 | 0x10);
                    graphics.drawImage(this.imgScene[8], Resource.halfWidth + 6, Resource.halfHeight - 1, 4 | 0x10);
                    graphics.drawImage(this.imgScene[3], Resource.halfWidth - 24, Resource.halfHeight - 64, 4 | 0x10);
                    graphics.drawImage(this.imgScene[4], Resource.halfWidth, Resource.halfHeight - 53, 4 | 0x10);
                    graphics.drawImage(this.imgScene[2], Resource.halfWidth - 6, Resource.halfHeight + 13, 4 | 0x10);
                    graphics.drawImage(this.imgScene[2], Resource.halfWidth + 14, Resource.halfHeight - 14, 4 | 0x10);
                }
                this.imgScene = null;
                System.gc();
                this.strmgr = new StringMgr("\u7ade\u8d5b\u65e5...", 18, 3);
                this.strmgr.start();
                break;
            }
            case 0: {
                int n6;
                Image image;
                Graphics graphics = Resource.getBackBuffer();
                for (int i = 0; i < 5 && Resource.halfWidth - 17 - (image = Resource.loadImage("/char_" + (i + 1) + "_n.png")).getWidth() - i * 20 >= -image.getWidth(); ++i) {
                    n6 = Resource.isQVGA() ? 26 : 17;
                    graphics.drawImage(image, Resource.halfWidth - n6 - image.getWidth() - i * 20, Resource.totalHeight - image.getHeight(), 4 | 0x10);
                }
                image = Resource.loadImage("/char_6_n.png");
                graphics.drawImage(image, Resource.halfWidth - image.getWidth() / 2, Resource.totalHeight - image.getHeight(), 4 | 0x10);
                image = Resource.loadImage("/char_0_n.png");
                n6 = Resource.isQVGA() ? 120 : 60;
                graphics.drawImage(image, Resource.halfWidth + n6 - image.getWidth(), Resource.totalHeight - image.getHeight(), 4 | 0x10);
                break;
            }
            case 1: {
                int n7;
                int n8;
                int n9;
                Graphics graphics = Resource.getBackBuffer();
                if (bl) {
                    n9 = 90;
                    n8 = 6;
                    n7 = 18;
                } else {
                    n9 = 60;
                    n8 = 3;
                    n7 = 9;
                }
                graphics.drawImage(this.imgChar[0], Resource.halfWidth + n9 - this.imgChar[0].getWidth(), Resource.totalHeight - this.imgChar[0].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgNPC[1], Resource.halfWidth + n9 - this.imgChar[0].getWidth() + n8, Resource.totalHeight - this.imgChar[0].getHeight() + n7, 4 | 0x10);
                graphics.drawImage(this.imgChar[1], Resource.halfWidth - this.imgChar[1].getWidth() / 2, Resource.totalHeight - this.imgChar[1].getHeight(), 4 | 0x10);
                break;
            }
            case 2: {
                int n10;
                int n11;
                int n12;
                int n13;
                Graphics graphics = Resource.getBackBuffer();
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                if (bl) {
                    n13 = 70;
                    n12 = 140;
                } else {
                    n13 = 35;
                    n12 = 70;
                }
                graphics.setColor(255, 205, 49);
                graphics.fillRect(0, Resource.halfHeight - n13, Resource.totalWidth, n12);
                if (bl) {
                    graphics.setColor(255, 82, 90);
                    graphics.fillRect(0, Resource.halfHeight - 70, Resource.totalWidth, 40);
                    graphics.setColor(230, 131, 82);
                    graphics.fillRect(0, Resource.halfHeight - 70 + 40, Resource.totalWidth, 6);
                    graphics.setColor(255, 164, 49);
                    graphics.fillRect(0, Resource.halfHeight - 70 + 46, Resource.totalWidth, 8);
                } else {
                    graphics.setColor(255, 82, 90);
                    graphics.fillRect(0, Resource.halfHeight - 35, Resource.totalWidth, 20);
                    graphics.setColor(230, 131, 82);
                    graphics.fillRect(0, Resource.halfHeight - 35 + 20, Resource.totalWidth, 3);
                    graphics.setColor(255, 164, 49);
                    graphics.fillRect(0, Resource.halfHeight - 35 + 23, Resource.totalWidth, 4);
                }
                if (bl) {
                    n13 = 90;
                    n11 = 70;
                    n10 = 6;
                    n12 = 18;
                } else {
                    n13 = 60;
                    n11 = 35;
                    n10 = 3;
                    n12 = 9;
                }
                graphics.drawImage(this.imgNPC[0], Resource.halfWidth - this.imgNPC[0].getWidth() / 2, Resource.halfHeight + n11 - this.imgNPC[0].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgChar[0], Resource.halfWidth + n13 - this.imgChar[0].getWidth(), Resource.halfHeight + n11 - this.imgChar[0].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgNPC[1], Resource.halfWidth + n13 - this.imgChar[0].getWidth() + n10, Resource.halfHeight + n11 - this.imgChar[0].getHeight() + n12, 4 | 0x10);
                graphics.drawImage(this.imgBGChip[0], 0, Resource.halfHeight + n11 - this.imgBGChip[0].getHeight(), 4 | 0x10);
            }
        }
    }

    void drawOpening(Graphics graphics) {
        graphics.setFont(Resource.sf);
        if (this.scene == 0) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            graphics.setColor(255, 255, 255);
            this.strmgr.drawTalk(graphics, Resource.halfWidth - 54, Resource.halfHeight + 10);
        } else if (this.scene == 12) {
            if (this.frame == 2) {
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                graphics.setColor(255, 255, 255);
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 54, Resource.halfHeight - 10);
                return;
            }
            if (this.frame == 4) {
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                graphics.setColor(255, 255, 255);
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 54, Resource.halfHeight - 20);
                return;
            }
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            if (this.frame == 0 || this.frame == 3) {
                if (Resource.isQVGA()) {
                    graphics.drawImage(this.imgScene[4], Resource.halfWidth - 42, Resource.halfHeight - 101, 4 | 0x10);
                } else {
                    graphics.drawImage(this.imgScene[4], Resource.halfWidth - 22, Resource.halfHeight - 51, 4 | 0x10);
                }
            } else if (this.frame == 1) {
                int n = 0;
                n = this.aniFrame2.getFrame() == 0 ? 1 : 0;
                if (Resource.isQVGA()) {
                    graphics.drawImage(this.imgScene[1], Resource.halfWidth - 42 + n, Resource.halfHeight - 101, 4 | 0x10);
                    if (Resource.lcdSize == 4) {
                        graphics.drawImage(this.imgScene[3], Resource.halfWidth - 0, Resource.halfHeight + 10, 1 | 0x10);
                    } else {
                        graphics.drawImage(this.imgScene[3], Resource.halfWidth - 20, Resource.halfHeight - 140, 1 | 0x10);
                    }
                } else {
                    graphics.drawImage(this.imgScene[1], Resource.halfWidth - 22 + n, Resource.halfHeight - 51, 4 | 0x10);
                    graphics.drawImage(this.imgScene[3], Resource.halfWidth - 10, Resource.halfHeight - 70, 1 | 0x10);
                }
                this.aniFrame2.frameProcess();
            }
            graphics.setColor(255, 255, 255);
            this.strmgr.drawTalk(graphics, Resource.halfWidth - 54, Resource.halfHeight + 10);
            if (this.frame == 1 && this.aniFrame.frameProcess() == 0) {
                this.frame = 2;
                this.strmgr = new StringMgr(strOpen[2], 18, 3);
                this.strmgr.start();
            }
        } else if (this.scene == 13) {
            if (this.frame == 0) {
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                graphics.setColor(255, 255, 255);
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 34, Resource.halfHeight - 10);
            } else if (this.frame == 1) {
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                if (this.aniFrame.frameProcess() == 0) {
                    this.strmgr = new StringMgr(strSPEECH[4], 14, 3);
                    this.strmgr.start();
                    MenuView.drawBG(Resource.getBackBuffer());
                    this.scene = 2;
                    this.frame = 0;
                    this.cloudPos[0] = Resource.halfWidth - 30;
                    this.cloudPos[1] = this.cloudPos[0] + Resource.halfWidth;
                    this.cloudPos[2] = Resource.halfHeight - 35 + Resource.getRand(30);
                    this.cloudPos[3] = Resource.halfHeight + 5 + Resource.getRand(15);
                }
            }
        } else if (this.scene == 1) {
            if (this.frame != 5) {
                int n;
                if (this.aniFrame.getFrame() < 8) {
                    n = 0;
                } else {
                    n = 1;
                    if (this.aniFrame.getFrame() == 9 && !this.playonce) {
                        Utils.playSound(8, true);
                        this.playonce = true;
                    }
                }
                int n2 = this.aniFrame2.getFrame() * 2;
                for (int i = 0; i < 10 * this.imgBGChip[0].getWidth(); i += this.imgBGChip[0].getWidth()) {
                    if (n2 > i + this.imgBGChip[0].getWidth()) continue;
                    if (n2 + Resource.totalWidth < i) break;
                    graphics.drawImage(this.imgBGChip[0], i - n2, Resource.halfHeight - n, 4 | 0x10);
                }
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.halfHeight);
                graphics.fillRect(0, Resource.halfHeight + this.imgBGChip[0].getHeight() - 1, Resource.totalWidth, Resource.totalHeight);
                int n3 = Resource.isQVGA() ? 29 : 58;
                graphics.drawImage(this.imgNPC[0], Resource.halfWidth + n3 - n2, Resource.halfHeight + this.imgBGChip[0].getHeight() - this.imgNPC[0].getHeight() - n, 4 | 0x10);
                n3 = Resource.isQVGA() ? 128 : 128;
                graphics.drawImage(this.imgChar[0], Resource.halfWidth + n3 - n2, Resource.halfHeight + this.imgBGChip[0].getHeight() - this.imgChar[0].getHeight() - n, 4 | 0x10);
            }
            if (this.frame == 0) {
                if (this.aniFrame2.frameProcess() == 0) {
                    this.strmgr.start();
                    this.frame = 1;
                }
                this.aniFrame.frameProcess();
            } else if (this.frame == 1 || this.frame == 2 || this.frame == 3 || this.frame == 4) {
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 44, Resource.halfHeight + this.imgBGChip[0].getHeight() - this.imgChar[0].getHeight(), 2, 0, Utils.RGB(197, 32, 41));
                this.aniFrame.frameProcess();
            } else if (this.frame == 5) {
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                if (this.aniFrame.frameProcess() == 0) {
                    this.nextScene();
                }
            }
        } else if (this.scene == 2) {
            if (this.frame == 0 || this.frame == 1) {
                graphics.setColor(139, 213, 255);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                graphics.drawImage(this.imgBGChip[2], Resource.halfWidth, Resource.totalHeight, 1 | 0x20);
                for (int i = 0; i < Resource.totalWidth; i += this.imgBGChip[3].getWidth()) {
                    graphics.drawImage(this.imgBGChip[3], i, 0, 4 | 0x10);
                }
                graphics.drawImage(this.imgBGChip[1], this.cloudPos[0], this.cloudPos[2], 4 | 0x10);
                graphics.drawImage(this.imgBGChip[1], this.cloudPos[1], this.cloudPos[3], 4 | 0x10);
                graphics.drawImage(this.imgCharBig[0], Resource.halfWidth + 22, Resource.totalHeight - this.imgCharBig[0].getHeight(), 4 | 0x10);
                int n = 0;
                int n4 = 0;
                if (Resource.isQVGA()) {
                    n = 10;
                    n4 = 38;
                } else {
                    n = 5;
                    n4 = 19;
                }
                graphics.drawImage(this.imgCharBig[1], Resource.halfWidth + 22 + n, Resource.totalHeight - this.imgCharBig[0].getHeight() + n4, 4 | 0x10);
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 44, Resource.totalHeight - this.imgCharBig[0].getHeight(), 2, 0, Utils.RGB(197, 32, 41));
            } else if (this.frame == 2) {
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                if (this.aniFrame.frameProcess() == 0) {
                    Utils.stopSound();
                    this.nextScene();
                }
            }
        } else if (this.scene == 3) {
            if (this.frame == 0) {
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                graphics.drawImage(this.imgCharBig[0], Resource.halfWidth + 22, Resource.totalHeight - this.imgCharBig[0].getHeight(), 4 | 0x10);
                int n = 0;
                int n5 = 0;
                if (Resource.isQVGA()) {
                    n = 10;
                    n5 = 38;
                } else {
                    n = 5;
                    n5 = 19;
                }
                graphics.drawImage(this.imgCharBig[1], Resource.halfWidth + 22 + n, Resource.totalHeight - this.imgCharBig[0].getHeight() + n5, 4 | 0x10);
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 44, Resource.totalHeight - this.imgCharBig[0].getHeight(), 2, 0, Utils.RGB(197, 32, 41));
            } else if (this.frame == 1) {
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                if (this.aniFrame.frameProcess() == 0) {
                    this.nextScene();
                }
            }
        } else if (this.scene == 4) {
            if (this.frame == 0) {
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 44, Resource.totalHeight - this.imgChar[0].getHeight(), 5, 0, Utils.RGB(197, 32, 41));
            } else if (this.frame == 1) {
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                if (this.aniFrame.frameProcess() == 0) {
                    this.nextScene();
                }
            }
        }
    }

    void drawFailedBG() {
        int n;
        int n2;
        Graphics graphics = Resource.getBackBuffer();
        graphics.setColor(0, 0, 0);
        graphics.setFont(Resource.sf);
        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        graphics.setColor(160, 178, 163);
        if (Resource.isQVGA()) {
            n2 = 70;
            n = 140;
        } else {
            n2 = 35;
            n = 70;
        }
        graphics.fillRect(0, Resource.halfHeight - n2, Resource.totalWidth, n);
        Image image = Resource.loadImage("/bgchip_14.png");
        for (int i = 0; i < Resource.totalWidth; i += image.getWidth()) {
            graphics.drawImage(image, i, Resource.halfHeight - n2, 4 | 0x10);
        }
        image = Resource.loadImage("/char_0_n.png");
        if (Resource.isQVGA()) {
            n2 = 120;
            n = 70;
        } else {
            n2 = 60;
            n = 35;
        }
        graphics.drawImage(image, Resource.halfWidth + n2 - image.getWidth(), Resource.halfHeight + n - image.getHeight(), 4 | 0x10);
        int n3 = 6;
        int n4 = 10;
        Image image2 = Resource.loadImage("/char_0_bad.png");
        graphics.drawImage(image2, Resource.halfWidth + n2 - image.getWidth() + n3, Resource.halfHeight + n - image.getHeight() + n4, 4 | 0x10);
        if (Resource.isQVGA()) {
            n2 = 58;
            n = 54;
        } else {
            n2 = 29;
            n = 27;
        }
        image2 = Resource.loadImage("/bgchip_13.png");
        graphics.drawImage(image2, Resource.halfWidth + n2, Resource.halfHeight - n, 4 | 0x10);
    }

    void drawFailed(Graphics graphics) {
        int n;
        int n2;
        int n3;
        int n4 = this.aniFrame.getFrame() == 0 ? 0 : 1;
        graphics.setFont(Resource.sf);
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
        int n5 = Resource.isQVGA() ? 18 : 9;
        graphics.drawImage(this.imgBGChip[0], Resource.halfWidth - this.imgBGChip[0].getWidth(), Resource.halfHeight - n5 + (n4 == 0 ? 1 : 0), 4 | 0x10);
        n5 = Resource.isQVGA() ? 22 : 11;
        graphics.drawImage(this.imgBGChip[1], Resource.halfWidth + this.imgBGChip[1].getWidth() / 10 * 2, Resource.halfHeight - n5 + (n4 == 1 ? 1 : 0), 4 | 0x10);
        if (Resource.isQVGA()) {
            n3 = 62;
            n5 = 28;
            n2 = 10;
            n = 20;
        } else {
            n3 = 31;
            n5 = 14;
            n2 = 5;
            n = 10;
        }
        if (this.aniFrame2.frameProcess() == 0) {
            for (n4 = 0; n4 < 5; ++n4) {
                this.rainPosX[n4] = Resource.halfWidth + n3 + n4 * n2 + Resource.getRand(5);
                this.rainPosY[n4] = Resource.halfHeight - n5 + n + Resource.getRand(5);
            }
            this.aniFrame2.init(1, 300, false);
        }
        graphics.setColor(255, 255, 255);
        if (Resource.isQVGA()) {
            n3 = 20;
            n5 = 2;
        } else {
            n3 = 10;
            n5 = 1;
        }
        for (n4 = 0; n4 < 5; ++n4) {
            graphics.drawLine(this.rainPosX[n4], this.rainPosY[n4] - n3, this.rainPosX[n4] + n5, this.rainPosY[n4]);
        }
        this.aniFrame.frameProcess();
        if (this.scene == 1) {
            graphics.drawImage(this.imgCharBig[0], 15, Resource.halfHeight + 25, 4 | 0x10);
        }
    }

    void drawEnding(Graphics graphics) {
        int n = 0;
        graphics.setFont(Resource.sf);
        if (this.scene == 0) {
            if (this.frame == 0 || this.frame == 1 || this.frame == 2) {
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                if (this.frame == 0) {
                    n = 5;
                } else if (this.frame == 1) {
                    n = 2;
                } else if (this.frame == 2) {
                    n = 5;
                }
                this.strmgr.drawTalk(graphics, Resource.halfWidth - 44, Resource.totalHeight - this.imgChar[0].getHeight(), n, 0, Utils.RGB(197, 32, 41));
            } else if (this.frame == 3) {
                graphics.setColor(328965);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                if (this.aniFrame.frameProcess() == 0) {
                    this.nextScene();
                }
            }
        } else if (this.scene == 2) {
            graphics.setColor(328965);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            graphics.setColor(255, 255, 255);
            this.strmgr.drawTalk(graphics, Resource.halfWidth - 54, Resource.halfHeight - 20);
        }
    }
}
