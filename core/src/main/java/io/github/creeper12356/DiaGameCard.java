/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import io.github.creeper12356.utils.Resource;
import io.github.creeper12356.utils.Utils;
import io.github.creeper12356.utils.XTimer;
import io.github.creeper12356.utils.XTimerListener;

public class DiaGameCard
extends Canvas
implements XTimerListener {
    static final int STATE_INIT = 0;
    static final int STATE_TITLE = 1;
    static final int STATE_MAIN = 2;
    int state;
    int process;
    long loadingTime;
    boolean isPlayingSound;
    static DiaApp diaapp;
    TitleView titleView;
    Image imgTouchBg;
    Image imgTouch1;
    Image imgTouch3;
    Image imgTouch4;
    Image imgTouch5;
    Image imgTouch6;
    Image imgTouch7;
    Image imgTouch9;
    Image imgTouchOk;
    Image imgTouchC;
    Image imgTouchU;
    Image imgTouchD;
    Image imgTouchL;
    Image imgTouchR;
    int action = 100;
    boolean initComp;
    int loadProgress;

    DiaGameCard(DiaApp diaApp) {
        diaapp = diaApp;
        Resource.totalWidth = 240;
        Resource.totalHeight = 320;
        Resource.halfWidth = Resource.totalWidth / 2;
        Resource.halfHeight = Resource.totalHeight / 2;
        Resource.checkScreenSize();
        this.setFullScreenMode(true);
        this.state = 0;
        this.process = 0;
        Resource.aTimer2 = new XTimer(Resource.gameSpeed);
        Resource.aTimer2.start();
        Resource.aTimer2.cancel();
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.setTime(Resource.gameSpeed);
        Resource.aTimer2.resume();
        this.loadingTime = 0L;
        Utils.soundPlayInit();
        Resource.loadConfig();
        Utils.setVolume();
        try {
            this.imgTouchBg = Image.createImage((String)"/touch/tbg.png");
            this.imgTouch1 = Image.createImage((String)"/touch/t1.png");
            this.imgTouch3 = Image.createImage((String)"/touch/t3.png");
            this.imgTouch4 = Image.createImage((String)"/touch/t4.png");
            this.imgTouch5 = Image.createImage((String)"/touch/t5.png");
            this.imgTouch6 = Image.createImage((String)"/touch/t6.png");
            this.imgTouch7 = Image.createImage((String)"/touch/t7.png");
            this.imgTouch9 = Image.createImage((String)"/touch/t9.png");
            this.imgTouchOk = Image.createImage((String)"/touch/tok.png");
            this.imgTouchC = Image.createImage((String)"/touch/tc.png");
            this.imgTouchU = Image.createImage((String)"/touch/tu.png");
            this.imgTouchD = Image.createImage((String)"/touch/td.png");
            this.imgTouchL = Image.createImage((String)"/touch/tl.png");
            this.imgTouchR = Image.createImage((String)"/touch/tr.png");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    void init() {
        int n;
        if (Resource.isQVGA()) {
            Resource.boardPosX = Resource.halfWidth - 119;
            Resource.boardPosY = Resource.lcdSize == 4 ? Resource.halfHeight - 95 : Resource.halfHeight - 101;
            Resource.boardZeroPosX = Resource.halfWidth - 119 + 20;
            Resource.boardZeroPosY = Resource.boardPosY + 5;
            Resource.HGAB = 11;
            Resource.VGAB = 16;
        } else {
            Resource.boardPosX = Resource.halfWidth - 67;
            Resource.boardPosY = Resource.halfHeight - 59;
            Resource.boardZeroPosX = Resource.halfWidth - 67 + 13;
            Resource.boardZeroPosY = Resource.boardPosY + 4;
            Resource.HGAB = 6;
            Resource.VGAB = 9;
        }
        Resource.imgBackBuffer = Image.createImage((int)Resource.totalWidth, (int)Resource.totalHeight);
        try {
            for (n = 0; n < 10; ++n) {
                Resource.imgSmallNum[n] = Image.createImage((String)("/smallnum_" + n + ".png"));
                Resource.imgBigNum[n] = Image.createImage((String)("/bignum_" + n + ".png"));
            }
            Resource.imgArrow[0] = Image.createImage((String)"/arrow_red_l.png");
            Resource.imgArrow[1] = Image.createImage((String)"/arrow_red_r.png");
            Resource.imgArrow[2] = Image.createImage((String)"/arrow_red_u.png");
            Resource.imgArrow[3] = Image.createImage((String)"/arrow_red_d.png");
            Resource.imgBallonChip[0] = Image.createImage((String)"/balloonchip_0.png");
            Resource.imgBallonChip[1] = Image.createImage((String)"/balloonchip_1.png");
            Resource.imgBallonChip[2] = Image.createImage((String)"/balloonchip_2.png");
            Resource.imgBallonChip[3] = Image.createImage((String)"/talkbutton_up.png");
            Resource.imgBallonChip[4] = Image.createImage((String)"/talkbutton_dn.png");
            Resource.imgPanelEdge[0] = Image.createImage((String)"/paneledge_0.png");
            Resource.imgPanelEdge[1] = Image.createImage((String)"/paneledge_1.png");
            Resource.imgPanelEdge[2] = Image.createImage((String)"/paneledge_2.png");
            Resource.imgPanelEdge[3] = Image.createImage((String)"/paneledge_3.png");
            Resource.imgButton[0] = Image.createImage((String)"/button_ok2.png");
            Utils.loadSoundIS();
            Resource.aniTalkButton.init(2, 100, true);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        Resource.aMainView = new MainView(this);
        for (n = 0; n < 3; ++n) {
            Resource.players[n] = new Player(n);
        }
        Resource.aMainView.aBoardView = new BoardView();
        for (n = 5; n < 25; ++n) {
            Resource.enableDiaList[n] = 0;
        }
        Resource.enableDiaList[0] = 1;
        Resource.enableDiaList[1] = 1;
        Resource.enableDiaList[2] = 1;
        Resource.enableDiaList[3] = 1;
        Resource.enableDiaList[4] = 1;
        Resource.bOpenSpecialGame = false;
        Continental.newGame = true;
        MiniGameView.bNewGame1 = true;
        MiniGameView.bNewGame2 = true;
        for (n = 0; n < 6; ++n) {
            Resource.stageClear[n] = 0;
        }
        Resource.loadGame();
        Resource.loadRank();
        this.titleView = new TitleView();
    }

    protected void pointerPressed(int n, int n2) {
        this.action = TouchDo.getTouchAction(n, n2);
        if (this.action < 100) {
            this.keyPressed(this.action);
            TouchDo.init();
        }
    }

    public void keyPressed(int n) {
        if (this.state == 1) {
            if (this.titleView.state == 1) {
                if (this.titleView.process == 3) {
                    if (n == -7) {
                        Utils.openVolume();
                        this.titleView.process = 0;
                        this.titleView.state = 2;
                        Resource.saveConfig();
                    } else if (n == -6) {
                        Utils.offVolume();
                        this.titleView.process = 0;
                        this.titleView.state = 2;
                        Resource.saveConfig();
                    }
                }
            } else if (this.titleView.state == 2) {
                this.titleView.clear();
                this.titleView = null;
                Resource.aTimer2.cancel();
                System.gc();
                this.state = 2;
                Resource.aMainView.setStatus(0, 1);
            }
        } else {
            Resource.aMainView.keyPressed(n);
        }
    }

    public void KeyRelease(int n) {
        Resource.aMainView.keyReleased(n);
    }

    public void paint(Graphics graphics) {
        if (this.state == 0) {
            graphics.setColor(255, 255, 255);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            graphics.setColor(0, 0, 0);
            if (this.loadProgress % 3 == 0) {
                graphics.drawString("\u52a0\u8f7d\u4e2d.", Resource.halfWidth - 24, Resource.halfHeight, 4 | 0x10);
            } else if (this.loadProgress % 3 == 1) {
                graphics.drawString("\u52a0\u8f7d\u4e2d..", Resource.halfWidth - 24, Resource.halfHeight, 4 | 0x10);
            } else if (this.loadProgress % 3 == 2) {
                graphics.drawString("\u52a0\u8f7d\u4e2d...", Resource.halfWidth - 24, Resource.halfHeight, 4 | 0x10);
            }
            if (this.process == 0) {
                this.loadingTime = System.currentTimeMillis();
                this.initComp = false;
                this.loadProgress = 0;
                LoadThread loadThread = new LoadThread();
                Thread thread = new Thread(loadThread);
                thread.start();
                ++this.process;
            } else if (this.process == 1) {
                ++this.loadProgress;
                if (this.initComp) {
                    ++this.process;
                }
            } else if (this.process == 2 && System.currentTimeMillis() - this.loadingTime > 1000L) {
                this.state = 1;
            }
        } else if (this.state == 1) {
            this.titleView.paint(graphics);
            switch (this.titleView.state) {
                case 1: {
                    if (this.titleView.process != 3) break;
                    TouchDo.setTouchArea(1, 1, 0);
                    break;
                }
                case 2: {
                    TouchDo.setTouchArea(1, 2, 0);
                }
            }
        } else {
            Resource.aMainView.paint(graphics);
        }
        for (int i = 0; i < TouchDo.touchNum; ++i) {
            if (TouchDo.touchArray[i][5] != 1) continue;
            graphics.drawImage(this.imgTouchBg, TouchDo.touchArray[i][0] + 2, TouchDo.touchArray[i][1] + 2, 0);
            if (TouchDo.touchArray[i][4] == -5) {
                graphics.drawImage(this.imgTouchOk, TouchDo.touchArray[i][0] + 2 + 15, TouchDo.touchArray[i][1] + 2 + 15, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == -8) {
                graphics.drawImage(this.imgTouchC, TouchDo.touchArray[i][0] + 2 + 15, TouchDo.touchArray[i][1] + 2 + 15, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == -1) {
                graphics.drawImage(this.imgTouchU, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == -2) {
                graphics.drawImage(this.imgTouchD, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == -3) {
                graphics.drawImage(this.imgTouchL, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == -4) {
                graphics.drawImage(this.imgTouchR, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == 49) {
                graphics.drawImage(this.imgTouch1, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == 51) {
                graphics.drawImage(this.imgTouch3, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == 52) {
                graphics.drawImage(this.imgTouch4, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == 53) {
                graphics.drawImage(this.imgTouch5, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == 54) {
                graphics.drawImage(this.imgTouch6, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] == 55) {
                graphics.drawImage(this.imgTouch7, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
                continue;
            }
            if (TouchDo.touchArray[i][4] != 57) continue;
            graphics.drawImage(this.imgTouch9, TouchDo.touchArray[i][0] + 2 + 16, TouchDo.touchArray[i][1] + 2 + 16, 1 | 2);
        }
    }

    public void hideNotify() {
        this.suspend();
    }

    public void suspend() {
        this.isPlayingSound = Utils.repeat;
        Utils.stopSound();
        Utils.stopVib();
        if (Resource.aMainView.getStatus() == 4) {
            RankView rankView = (RankView)Resource.aMainView.currentView;
            if (rankView.state == 4 && rankView.process != 1) {
                rankView.rank.Stop();
                rankView.process = 1;
            }
        }
    }

    public void showNotify() {
        this.resume();
    }

    public void resume() {
        if (this.state == 2) {
            if (Resource.aMainView.getStatus() == 1) {
                Resource.aMainView.aBoardView.pauseMenu();
            } else if (Resource.aMainView.getStatus() == 9) {
                Resource.aMainView.continentalView.popupMenu();
            } else if (Resource.aMainView.getStatus() == 8) {
                Resource.aMainView.miniGameView.popupMenu();
            }
        }
        if (this.isPlayingSound) {
            Utils.playSound(Utils.currentSoundID, true);
        }
    }

    public void performed(XTimer xTimer) {
        if (this.state == 0) {
            this.repaint();
        }
        if (this.state == 1) {
            this.titleView.run();
        }
    }

    public void exit() {
        diaapp.destroyApp(true);
        diaapp.notifyDestroyed();
    }

    class LoadThread
    implements Runnable {
        LoadThread() {
        }

        public void run() {
            DiaGameCard.this.init();
            DiaGameCard.this.initComp = true;
        }
    }
}
