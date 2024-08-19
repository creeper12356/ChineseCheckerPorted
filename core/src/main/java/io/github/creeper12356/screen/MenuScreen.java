package io.github.creeper12356.screen;

import java.io.IOException;
import java.util.Calendar;
// import javax.microedition.lcdui.Font;
// import javax.microedition.lcdui.Graphics;
// import javax.microedition.lcdui.Image;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

import io.github.creeper12356.Animation;
import io.github.creeper12356.core.Player;
import io.github.creeper12356.utils.Resource;
import io.github.creeper12356.utils.TouchDo;

public class MenuScreen
        implements Screen,
        InputProcessor {

    public static final int MAIN_MENU = 1;
    public static final int STORY_MENU = 2;
    public static final int VS_MENU = 3;
    public static final int ROUND_MENU = 4;
    public static final int MISC_MENU = 5;
    public static final int SPECIAL_MENU = 6;
    public static final int GIFT_MENU = 7;
    public static final int GAMEZONE_MENU = 8;
    public static final int PAUSE_MENU = 9;

    public static final int PROCESS_DIASELECT = 0;
    public static final int PROCESS_ROUNDSTART = 1;
    public static final int PROCESS_VSCONFIG = 0;
    public static final int PROCESS_VSHELP = 1;
    public static final int PROCESS_NEWGAME = 1;
    public static final int PROCESS_NOTSAVED = 2;
    public static final int PROCESS_VIEWPIECE = 1;
    public static final int PROCESS_GETSPECIALGAME = 1;

    int state;
    int lastState;
    int process;
    int currentSel;
    int[] cloudPos = new int[4];
    int menuLength;
    int newDia;
    int menuGap;
    int stringWidth;

    Texture[] imgBGChip = new Texture[2];
    Texture[] imgMenuText = new Texture[7];
    Texture[] imgMenuTextW = new Texture[8];
    Texture[] imgMenuTextY = new Texture[8];
    Texture imgMenuBG;
    Texture[] imgMenuChip = new Texture[5];
    Texture imgSelIcon;
    Texture[] imgSelDia = new Texture[3];
    Texture[] imgRoundChip = new Texture[3];
    Texture[] imgButton = new Texture[4];
    Texture[] imgStageMap = new Texture[3];

    Animation aniFrame = new Animation();
    Animation aniArrow = new Animation();
    Animation aniStageMap = new Animation();

    public MenuScreen() {
        // this.stringWidth =
        // Font.getDefaultFont().stringWidth("\u9152\u919b\u81c2\u78ca\u752b\u514d\u4eff\u8304\u4fc3");
        this.imgMenuText[2] = Resource.loadImage("menutext_2.png");
        this.imgMenuText[3] = Resource.loadImage("menutext_3.png");
        this.imgMenuText[4] = Resource.loadImage("menutext_4.png");
        this.imgMenuText[5] = Resource.loadImage("menutext_5.png");
        this.imgMenuText[6] = Resource.loadImage("menutext_6.png");
        for (int i = 0; i < 7; ++i) {
            this.imgMenuTextW[i] = Resource.loadImage("menutext_1" + i + ".png");
            this.imgMenuTextY[i] = Resource.loadImage("menutext_2" + i + ".png");
        }
        this.imgMenuBG = Resource.loadImage("bgmenu.png");
        this.imgBGChip[1] = Resource.loadImage("bgchip_1.png");
        this.imgMenuChip[0] = Resource.loadImage("menuchip_0.png");
        this.imgMenuChip[1] = Resource.loadImage("textchip_0.png");
        this.imgMenuChip[2] = Resource.loadImage("textchip_1.png");
        this.imgMenuChip[3] = Resource.loadImage("textchip_2.png");
        this.imgMenuChip[4] = Resource.loadImage("menuchip_1.png");
        this.imgStageMap[0] = Resource.loadImage("uichip_5.png");
        this.imgStageMap[1] = Resource.loadImage("uichip_14.png");
        this.imgStageMap[2] = Resource.loadImage("crown.png");

        this.cloudPos[0] = Resource.halfWidth - 30;
        this.cloudPos[1] = this.cloudPos[0] + Resource.halfWidth;
        this.cloudPos[2] = Resource.halfHeight - 35 + Resource.getRand(30);
        this.cloudPos[3] = Resource.halfHeight + 5 + Resource.getRand(15);
    }

    // @Override
    // public void performed(XTimer xTimer) {
    // for (int i = 0; i < 2; ++i) {
    // int n = i;
    // this.cloudPos[n] = this.cloudPos[n] - 2;
    // if (this.cloudPos[i] >= 0 - this.imgBGChip[1].getWidth())
    // continue;
    // this.cloudPos[i] = Resource.totalWidth + 10;
    // if (i == 0) {
    // this.cloudPos[2] = Resource.halfHeight - 35 + Resource.getRand(30);
    // continue;
    // }
    // this.cloudPos[3] = Resource.halfHeight + 5 + Resource.getRand(15);
    // }
    // Resource.aMainView.repaint();
    // }

    public void drawPause(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        graphics.drawString("是否继续游戏？", Resource.halfWidth, Resource.halfHeight,
                1 | 2);
    }

    public void init() {
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.setTime(100L);
        Resource.aTimer2.resume();
        this.currentSel = 0;
        this.menuGap = Resource.isQVGA() ? 30 : 15;
        switch (this.state) {
            case MAIN_MENU: {
                // MenuScreen.drawBG(Resource.getBackBuffer());
                this.menuLength = 7;
                this.imgSelIcon = Resource.loadImage("selicon_1.png");
                if (this.imgMenuTextW[0] == null || this.imgMenuTextW[1] == null) {
                    this.imgMenuText[2] = Resource.loadImage("menutext_2.png");
                    this.imgMenuText[3] = Resource.loadImage("menutext_3.png");
                    this.imgMenuText[4] = Resource.loadImage("menutext_4.png");
                    this.imgMenuText[5] = Resource.loadImage("menutext_5.png");
                    this.imgMenuText[6] = Resource.loadImage("menutext_6.png");
                    for (int i = 0; i < 7; ++i) {
                        this.imgMenuTextW[i] = Resource.loadImage("menutext_1" + i + ".png");
                        this.imgMenuTextY[i] = Resource.loadImage("menutext_2" + i + ".png");
                    }
                }
                if (this.imgMenuBG == null) {
                    this.imgMenuBG = Resource.loadImage("bgmenu.png");
                }
                System.gc();
                // if (Resource.aMainView.aBoardView != null)
                // break;
                // Resource.aMainView.aBoardView = new BoardView();
                break;
            }
            case STORY_MENU: {
                this.menuLength = 2;
                this.currentSel = 0;
                this.process = 0;
                break;
            }
            case VS_MENU: {
                this.menuLength = 4;
                this.imgSelIcon = Resource.loadImage("/selicon_0.png");
                Resource.aMainView.aBoardView.loadVSImg();
                if (Resource.imgDiaAvt[0] == null) {
                    for (int i = 0; i < 25; ++i) {
                        Resource.imgDiaAvt[i] = Resource.loadImage("/dia_avt_" + i + ".png");
                    }
                }
                Resource.players[0].setType(Player.PLAYERTYPE_HUMAN);
                Resource.players[0].setDiaType(0);
                this.imgSelDia[0] = Resource.imgDiaAvt[Resource.players[0].getDiaType()];
                Resource.players[1].setType(Player.PLAYERTYPE_CPU);
                Resource.players[1].setDiaType(1);
                this.imgSelDia[1] = Resource.imgDiaAvt[Resource.players[1].getDiaType()];
                Resource.players[2].setType(Player.PLAYERTYPE_OFF);
                Resource.players[2].setDiaType(-1);
                this.imgSelDia[2] = null;

                Resource.players[0].setAiLevel(6);
                Resource.players[1].setAiLevel(3 + Resource.getRand(1));
                Resource.players[2].setAiLevel(5 + Resource.getRand(1));

                this.imgButton[0] = Resource.loadImage("button_help.png");
                this.imgButton[1] = Resource.loadImage("button_lr.png");
                this.imgButton[2] = Resource.loadImage("button_ud.png");
                this.imgButton[3] = Resource.loadImage("button_ok3.png");
                this.aniArrow.init(2, 2, 300, false);
                this.process = 0;
                break;
            }
            case ROUND_MENU: {
                MenuScreen.drawBG(Resource.getBackBuffer());
                if (Resource.imgDiaAvt[0] == null) {
                    for (int i = 0; i < 25; ++i) {
                        Resource.imgDiaAvt[i] = Resource.loadImage("dia_avt_" + i + ".png");
                    }
                }

                // 初始化游戏数据
                Resource.gameMode = Resource.GAMEMODE_STORY;
                Resource.playerCnt = 2;
                Resource.players[0].setType(Player.PLAYERTYPE_HUMAN);
                Resource.players[1].setType(Player.PLAYERTYPE_CPU);
                Resource.players[0].setCharID(0);
                Resource.players[1].setCharID(Resource.stageNum);
                Resource.players[0].setDiaType(0);
                Resource.players[1].setDiaType(Resource.getRand(24) + 1);
                Resource.players[0].setAiLevel(6);
                Resource.players[1].setAiLevel(Resource.stageNum);

                this.imgSelDia[0] = Resource.imgDiaAvt[Resource.players[0].getDiaType()];
                this.imgSelDia[1] = Resource.imgDiaAvt[Resource.players[1].getDiaType()];
                Resource.imgPlayer[0] = Resource.loadImage("char_0_n.png");
                Resource.imgPlayer[1] = Resource.loadImage("char_0_good.png");
                Resource.imgPlayer[2] = Resource.loadImage("char_0_bad.png");

                Resource.imgEnemy[0] = Resource.loadImage("char_" + Resource.stageNum + "_n.png");
                Resource.imgEnemy[1] = Resource.loadImage("char_" + Resource.stageNum + "_good.png");
                Resource.imgEnemy[2] = Resource.loadImage("char_" + Resource.stageNum + "_bad.png");

                if (Resource.stageNum < 6) {
                    this.imgRoundChip[0] = Resource.loadImage("roundnum_" + Resource.stageNum + ".png");
                } else if (Resource.stageNum == 6) {
                    this.imgRoundChip[0] = Resource.loadImage("roundchip_0.png");
                }

                this.imgRoundChip[1] = Resource.loadImage("roundchip_3.png");
                this.imgRoundChip[2] = Resource.loadImage("roundchip_1.png");
                this.imgButton[0] = Resource.loadImage("button_move.png");
                this.imgButton[1] = Resource.loadImage("button_ok.png");

                this.aniArrow.init(2, 2, 300, false);
                this.aniStageMap.init(2, 100, true);
                this.process = 0;
                break;
            }
            case MISC_MENU: {
                // TODO
                this.newDia = 0;
                for (int i = 5; i < 25; ++i) {
                    if (i == 11 || i == 21 || Resource.enableDiaList[i] != 0)
                        continue;
                    Resource.enableDiaList[i] = (byte) i;
                    ++this.newDia;
                    break;
                }
                if (Resource.enableDiaList[11] == 0 && Resource.players[1].getDiaType() == 12) {
                    Resource.enableDiaList[11] = 0;
                    ++this.newDia;
                }
                if (Resource.enableDiaList[21] == 0) {
                    Calendar calendar = Calendar.getInstance();
                    if (calendar.get(7) == 1) {
                        Resource.enableDiaList[21] = 1;
                        ++this.newDia;
                    }
                }
                if (this.newDia == 0) {
                    this.setStatus(1);
                    this.init();
                }
                this.process = 0;
                if (Resource.bOpenSpecialGame)
                    break;
                Resource.bOpenSpecialGame = true;
                this.process = 1;
                break;
            }
            case SPECIAL_MENU: {
                for (int i = 0; i < 25; ++i) {
                    Resource.imgDiaAvt[i] = Resource.loadImage("dia_avt_" + i + ".png");
                }
                break;
            }
        }
    }

    public void clearAVT() {
        for (int i = 0; i < 25; ++i) {
            Resource.imgDiaAvt[i] = null;
        }
    }

    public void clearMM() {
        this.imgMenuText[2] = null;
        this.imgMenuText[3] = null;
        this.imgMenuText[4] = null;
        this.imgMenuText[5] = null;
        this.imgMenuText[6] = null;
        for (int i = 0; i < 7; ++i) {
            this.imgMenuTextW[i] = null;
            this.imgMenuTextY[i] = null;
        }
        this.imgMenuBG = null;
    }

    public void clear() {
        this.imgRoundChip[0] = null;
        this.imgRoundChip[1] = null;
        this.imgRoundChip[2] = null;
        this.imgButton[0] = null;
        this.imgButton[1] = null;
        if (Resource.aTimer2.isActivate()) {
            Resource.aTimer2.cancel();
        }
        try {
            Thread.sleep(200L);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public void setStatus(int n) {
        this.state = n;
    }

    public int getStatus() {
        return this.state;
    }

    public void hideNotify() {
        this.lastState = this.state;
        this.state = 9;
    }

    void goMenu(int n) {
        switch (this.state) {
            case 1: {
                // Utils.playSound(17, false);
                if (n == 0) {
                    Resource.aMainView.setStatus(0, 2);
                    break;
                }
                if (n == 1) {
                    Resource.aMainView.setStatus(0, 3);
                    break;
                }
                if (n == 2) {
                    Resource.aMainView.openPopup(6);
                    break;
                }
                if (n == 3) {
                    Resource.aMainView.openPopup(7);
                    break;
                }
                if (n == 4) {
                    for (int i = 0; i < 25; ++i) {
                        Resource.imgDiaAvt[i] = Resource.loadImage("/dia_avt_" + i + ".png");
                    }
                    this.state = 6;
                    this.menuLength = 2;
                    this.currentSel = 0;
                    this.process = 0;
                    break;
                }
                if (n == 5) {
                    Resource.aMainView.setStatus(4, 0);
                    break;
                }
                if (n == 6) {
                    Resource.aMainView.appDestroy();
                    break;
                }
                if (n != 7)
                    break;
                break;
            }
            case 2: {
                Utils.playSound(17, false);
                if (n == 0) {
                    if (Resource.stageNum > 0) {
                        this.process = 1;
                        break;
                    }
                    Resource.aMainView.setStatus(2, 1);
                    this.clearMM();
                    break;
                }
                if (n != 1)
                    break;
                if (Resource.stageNum == 0) {
                    this.process = 2;
                    break;
                }
                if (Resource.stageNum == 21) {
                    Resource.aMainView.setStatus(8, 1);
                } else if (Resource.stageNum == 41) {
                    Resource.aMainView.setStatus(8, 2);
                } else {
                    Resource.aMainView.setStatus(0, 4);
                }
                this.clearMM();
                break;
            }
            case 3: {
                Utils.playSound(17, false);
                if (this.process == 1) {
                    this.process = 0;
                    return;
                }
                if (n == 0 || n == 1 || n == 2) {
                    if (Resource.players[n].getType() == 1) {
                        if (n == 0)
                            break;
                        Resource.players[n].type = 2;
                        break;
                    }
                    if (Resource.players[n].getType() == 2) {
                        if (n == 2) {
                            Resource.players[n].type = 3;
                            this.imgSelDia[n] = null;
                            break;
                        }
                        Resource.players[n].type = 1;
                        Resource.players[n].diaType = Resource.players[n].diaType >= 0 ? --Resource.players[n].diaType
                                : 0;
                        this.selectChangeDia(n, 2);
                        break;
                    }
                    if (Resource.players[n].getType() != 3)
                        break;
                    Resource.players[n].type = 1;
                    Resource.players[n].diaType = Resource.players[n].diaType >= 0 ? --Resource.players[n].diaType : 0;
                    this.selectChangeDia(n, 2);
                    break;
                }
                if (n != 3)
                    break;
                int n2 = 0;
                if (Resource.players[0].type != 3) {
                    ++n2;
                }
                if (Resource.players[1].type != 3) {
                    ++n2;
                }
                if (Resource.players[2].type != 3) {
                    ++n2;
                }
                if (n2 < 2) {
                    return;
                }
                Resource.gameMode = Resource.GAMEMODE_VS;
                Resource.playerCnt = n2;
                Resource.aMainView.setStatus(1, 0);
                break;
            }
            case 4: {
                if (this.process != 0)
                    break;
                Utils.playSound(17, false);
                this.process = 1;
                this.aniFrame.init(8, 400, false);
                Utils.playSound(9, false);
                break;
            }
            case 6: {
                Utils.playSound(17, false);
                if (n == 0) {
                    this.process = 1;
                    break;
                }
                if (n != 1 || !Resource.bOpenSpecialGame)
                    break;
                Resource.aMainView.aBoardView = null;
                System.gc();
                Resource.aMainView.setStatus(9, 0);
                if (Resource.imgDiaAvt[0] != null)
                    break;
                for (int i = 0; i < 25; ++i) {
                    Resource.imgDiaAvt[i] = Resource.loadImage("/dia_avt_" + i + ".png");
                }
                break;
            }
        }
    }

    boolean selectChangeDia(int n, int n2) {
        int n3;
        boolean bl = true;
        if (n2 == 1) {
            if (Resource.players[n].diaType == 0) {
                for (n3 = 24; n3 >= 0; --n3) {
                    if (Resource.enableDiaList[n3] == 0)
                        continue;
                    Resource.players[n].diaType = n3;
                    break;
                }
            } else {
                for (n3 = Resource.players[n].diaType - 1; n3 >= 0; --n3) {
                    if (Resource.enableDiaList[n3] == 0)
                        continue;
                    Resource.players[n].diaType = n3;
                    break;
                }
            }
        } else if (n2 == 2) {
            n3 = Resource.players[n].diaType;
            do {
                if (++n3 != 25)
                    continue;
                n3 = 0;
            } while (Resource.enableDiaList[n3] == 0);
            Resource.players[n].diaType = n3;
        }
        for (n3 = 0; n3 < 3; ++n3) {
            if (n3 == n || Resource.players[n3].getType() == 3
                    || Resource.players[n].diaType != Resource.players[n3].diaType)
                continue;
            bl = false;
        }
        if (!bl && this.selectChangeDia(n, n2)) {
            return true;
        }
        this.imgSelDia[n] = Resource.imgDiaAvt[Resource.players[n].diaType];
        return true;
    }

    void drawVSHelp(Graphics graphics) {
        int n;
        int n2;
        int n3;
        int n4;
        if (Resource.isQVGA()) {
            n4 = 96;
            n3 = 86;
        } else {
            n4 = 48;
            n3 = 43;
        }
        int n5 = Resource.halfWidth - n4;
        int n6 = Resource.halfHeight - n3;
        graphics.setColor(148, 65, 24);
        graphics.setFont(Resource.sf);
        if (Resource.isQVGA()) {
            n2 = 180;
            n = 166;
        } else {
            n2 = 95;
            n = 83;
        }
        graphics.fillRect(n5 + 1, n6, n2 - 1, n + 1);
        graphics.fillRect(n5, n6 + 1, n2 + 1, n - 1);
        graphics.setColor(255, 230, 209);
        graphics.fillRect(n5 + 2, n6 + 2, n2 - 3, n - 3);
        if (Resource.isQVGA()) {
            n4 = 22;
            n3 = 21;
        } else {
            n4 = 11;
            n3 = 9;
        }
        graphics.drawImage(this.imgMenuChip[4], Resource.halfWidth - n4, n6 - n3, 4 | 0x10);
        if (Resource.isQVGA()) {
            n4 = 56;
            n3 = 58;
            n = 40;
        } else {
            n4 = 33;
            n3 = 29;
            n = 20;
        }
        graphics.drawImage(this.imgButton[1], Resource.halfWidth - n4, Resource.halfHeight - n3, 4 | 0x10);
        graphics.drawImage(this.imgButton[2], Resource.halfWidth - n4, Resource.halfHeight - n3 + n * 1, 4 | 0x10);
        graphics.drawImage(this.imgButton[3], Resource.halfWidth - n4, Resource.halfHeight - n3 + n * 2, 4 | 0x10);
        graphics.setColor(148, 65, 24);
        if (Resource.isQVGA()) {
            n4 = 30;
            n3 = 58;
            n = 40;
            graphics.setFont(Font.getFont((int) 0, (int) 0, (int) 16));
        } else {
            n4 = 20;
            n3 = 30;
            n = 20;
        }
        graphics.drawString("\u9009\u62e9\u9a6c\u79cd", Resource.halfWidth - n4, Resource.halfHeight - n3, 4 | 0x10);
        graphics.drawString("\u4e0a\u4e0b\u79fb\u52a8", Resource.halfWidth - n4, Resource.halfHeight - n3 + n * 1,
                4 | 0x10);
        graphics.drawString("\u73a9\u5bb6\u5c5e\u6027", Resource.halfWidth - n4, Resource.halfHeight - n3 + n * 2,
                4 | 0x10);
    }

    static void drawBG(Graphics graphics) {
        Image image = Resource.loadImage("/background.png");
        graphics.setColor(139, 213, 255);
        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        if (image == null) {
            return;
        }
        graphics.drawImage(image, 0, 0, 0);
        image = null;
    }

    void drawSpecialMenu(Graphics graphics) {
        int n;
        int n2;
        if (this.process == 0) {
            TouchDo.setTouchArea(2, 6, 0);
        } else if (this.process == 1) {
            TouchDo.setTouchArea(2, 6, 1);
        }
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
        if (this.process == 1) {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            if (Resource.lcdSize == 4) {
                this.drawDiaListWVGA(graphics);
                return;
            }
            if (Resource.isQVGA()) {
                n9 = Resource.halfWidth - 114;
                n8 = Resource.halfHeight - 140;
                n7 = 22;
                n6 = 50;
                n5 = 46;
                n4 = 52;
                n3 = 34;
                Resource.drawPanel(graphics, Resource.halfWidth - 120, Resource.halfHeight - 140, 240, 276);
            } else {
                n9 = Resource.halfWidth - 57;
                n8 = Resource.halfHeight - 70;
                n7 = 11;
                n6 = 25;
                n5 = 23;
                n4 = 26;
                n3 = 17;
                Resource.drawPanel(graphics, Resource.halfWidth - 60, Resource.halfHeight - 70, 120, 138);
            }
            for (int i = 0; i < 25; ++i) {
                if (Resource.enableDiaList[i] != 0) {
                    graphics.drawImage(Resource.imgDiaAvt[i], n9 + n5 * (i % 5) + n7, n8 + n4 * (i / 5) + n6, 1 | 0x20);
                    continue;
                }
                Image image = Resource.loadImage("/diashadow00.png");
                graphics.drawImage(image, n9 + n5 * (i % 5) + n7, n8 + n4 * (i / 5) + n6, 1 | 0x20);
                image = null;
                graphics.drawImage(this.imgMenuText[6], n9 + n5 * (i % 5) + n7, n8 + n4 * (i / 5) + n3, 1 | 2);
            }
            return;
        }
        int n10 = Resource.halfWidth - this.imgMenuText[4].getWidth() / 2;
        int n11 = Resource.halfHeight - (this.imgMenuText[4].getHeight() + this.imgMenuText[5].getHeight()) / 2;
        if (Resource.isQVGA()) {
            n2 = 30;
            n = 19;
        } else {
            n2 = 15;
            n = 10;
        }
        graphics.drawImage(this.imgMenuText[4], n10, n11, 4 | 0x10);
        graphics.drawImage(this.imgSelIcon, n10 - this.imgSelIcon.getWidth() - 2, n11 + this.currentSel * this.menuGap,
                4 | 0x10);
        if (Resource.bOpenSpecialGame) {
            graphics.drawImage(this.imgMenuText[5], n10, n11 + n2, 4 | 0x10);
        } else {
            for (int i = 0; i < 4; ++i) {
                graphics.drawImage(this.imgMenuText[6], n10 + i * n + 13, n11 + n2, 4 | 0x10);
            }
        }
    }

    void drawDiaListWVGA(Graphics graphics) {
        int n = Resource.halfWidth - 155;
        int n2 = Resource.halfHeight - 107;
        int n3 = 22;
        int n4 = 50;
        int n5 = 44;
        int n6 = 52;
        int n7 = 34;
        Resource.drawPanel(graphics, Resource.halfWidth - 160, Resource.halfHeight - 109, Resource.totalWidth,
                Resource.totalHeight);
        for (int i = 0; i < 25; ++i) {
            if (Resource.enableDiaList[i] != 0) {
                graphics.drawImage(Resource.imgDiaAvt[i], n + n5 * (i % 7) + n3, n2 + n6 * (i / 7) + n4, 1 | 0x20);
                continue;
            }
            Image image = Resource.loadImage("/diashadow00.png");
            graphics.drawImage(image, n + n5 * (i % 7) + n3, n2 + n6 * (i / 7) + n4, 1 | 0x20);
            image = null;
            graphics.drawImage(this.imgMenuText[6], n + n5 * (i % 7) + n3, n2 + n6 * (i / 7) + n7, 1 | 2);
        }
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void render(float delta) {
        switch (this.state) {
            case PAUSE_MENU: {
                this.drawPause(graphics);
                break;
            }
            case MAIN_MENU: {
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                if (this.imgMenuBG == null) {
                    this.imgMenuBG = Resource.loadImage("/bgmenu.png");
                }
                graphics.drawImage(this.imgMenuBG, 0, 0, 0);
                int n = 0;
                int n2 = 0;
                if (Resource.isQVGA()) {
                    n2 = Resource.halfWidth - 60;
                    n = Resource.halfHeight - 102;
                    for (int i = 0; i < this.menuLength; ++i) {
                        if (this.currentSel == i) {
                            graphics.drawImage(this.imgMenuTextY[i], Resource.halfWidth, n + 7 + 35 * i, 17);
                            continue;
                        }
                        graphics.drawImage(this.imgMenuTextW[i], Resource.halfWidth, n + 7 + 35 * i, 17);
                    }
                    graphics.drawImage(this.imgSelIcon, n2 + 10, n + 7 + this.currentSel * 35, 4 | 0x10);
                    TouchDo.setTouchArea(2, 1, 0);
                    break;
                }
                n2 = Resource.halfWidth - 28;
                n = Resource.halfHeight - 56;
                graphics.setColor(16770691);
                graphics.fillRect(n2 + 2, n, 52, 2);
                graphics.fillRect(n2 + 2, n + 100, 52, 2);
                graphics.fillRect(n2, n + 2, 2, 98);
                graphics.fillRect(n2 + 54, n + 2, 2, 98);
                graphics.setColor(9718485);
                graphics.fillRect(n2 + 2, n + 2, 52, 98);
                for (int i = 0; i < this.menuLength; ++i) {
                    if (this.currentSel == i) {
                        graphics.drawImage(this.imgMenuTextY[i], n2 + 32, n + 5 + 13 * i, 17);
                        continue;
                    }
                    graphics.drawImage(this.imgMenuTextW[i], n2 + 32, n + 5 + 13 * i, 17);
                }
                graphics.drawImage(this.imgSelIcon, n2 + 3, n + 5 + this.currentSel * 13, 4 | 0x10);
                break;
            }
            case STORY_MENU: {
                graphics.setFont(Resource.sf);
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                int n = Resource.halfWidth - this.imgMenuText[3].getWidth() / 2;
                int n3 = Resource.halfHeight - this.imgMenuText[3].getHeight() / 2;
                graphics.drawImage(this.imgMenuText[3], n, n3, 4 | 0x10);
                graphics.drawImage(this.imgSelIcon, n - this.imgSelIcon.getWidth() - 2,
                        n3 + 4 + this.currentSel * this.menuGap, 4 | 0x10);
                if (this.process == 1) {
                    Resource.drawPanel(graphics, Resource.halfWidth - 70, Resource.halfHeight - 35, 140, 70);
                    graphics.setColor(0xFFFFFF);
                    graphics.drawString("\u662f\u5426\u5220\u9664\u65e7\u7684\u5b58\u6863", Resource.halfWidth - 65,
                            Resource.halfHeight - 30, 4 | 0x10);
                    graphics.drawString("\u5e76\u5f00\u59cb\u65b0\u6e38\u620f?", Resource.halfWidth - 65,
                            Resource.halfHeight - 10, 4 | 0x10);
                    graphics.drawString(" 1.\u662f  2.\u5426", Resource.halfWidth - 55, Resource.halfHeight + 15,
                            4 | 0x10);
                } else if (this.process == 2) {
                    Resource.drawPanel(graphics, Resource.halfWidth - 70, Resource.halfHeight - 35, 140, 70);
                    graphics.setColor(0xFFFFFF);
                    graphics.drawString("\u6ca1\u6709\u4efb\u4f55\u5b58\u6863.", Resource.halfWidth - 55,
                            Resource.halfHeight - 30, 4 | 0x10);
                    graphics.drawString("\u662f\u5426\u5f00\u59cb\u65b0\u6e38\u620f?", Resource.halfWidth - 55,
                            Resource.halfHeight - 10, 4 | 0x10);
                    graphics.drawString(" 1.\u662f  2.\u5426", Resource.halfWidth - 55, Resource.halfHeight + 15,
                            4 | 0x10);
                }
                TouchDo.setTouchArea(2, 2, this.process);
                break;
            }
            case VS_MENU: {
                int n;
                int n4;
                int n5;
                int n6;
                int n7;
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
                if (Resource.isQVGA()) {
                    n7 = Resource.halfWidth - 104;
                    n6 = Resource.halfHeight - 110;
                } else {
                    n7 = Resource.halfWidth - 52;
                    n6 = Resource.halfHeight - 62;
                }
                for (int i = 0; i < 4; ++i) {
                    int n8;
                    if (Resource.isQVGA()) {
                        n5 = 200;
                        n8 = 52;
                    } else {
                        n5 = 100;
                        n8 = 26;
                    }
                    graphics.setColor(106, 90, 24);
                    graphics.drawLine(n7 + 1, n6, n7 + n5, n6);
                    graphics.drawLine(n7 + 1, n6 + n8 + 1, n7 + n5, n6 + n8 + 1);
                    graphics.drawLine(n7, n6 + 1, n7, n6 + n8);
                    graphics.drawLine(n7 + n5 + 1, n6 + 1, n7 + n5 + 1, n6 + n8);
                    graphics.setColor(246, 189, 123);
                    graphics.drawRect(n7 + 1, n6 + 1, n5 - 1, n8 - 1);
                    if (this.currentSel != i) {
                        graphics.setColor(255, 230, 209);
                    } else {
                        graphics.setColor(255, 194, 163);
                    }
                    graphics.fillRect(n7 + 2, n6 + 2, n5 - 2, n8 - 2);
                    if (i < 3) {
                        if (Resource.isQVGA()) {
                            n4 = 14;
                            n = 16;
                            n5 = 40;
                            n8 = 24;
                        } else {
                            n4 = 7;
                            n = 8;
                            n5 = 20;
                            n8 = 12;
                        }
                        graphics.setColor(246, 189, 123);
                        graphics.fillRect(n7 + n4 + 1, n6 + n, n5 - 1, n8 + 1);
                        graphics.fillRect(n7 + n4, n6 + n + 1, n5 + 1, n8 - 1);
                        graphics.drawImage(Resource.imgBigNum[i + 1], n7 + n4 + 2, n6 + n + 2, 4 | 0x10);
                        graphics.drawImage(this.imgMenuChip[0], n7 + n4 * 2 + 4, n6 + n + 2, 4 | 0x10);
                        graphics.setColor(16, 24, 106);
                        if (Resource.isQVGA()) {
                            n4 = 142;
                            n = 12;
                            n5 = 50;
                            n8 = 32;
                        } else {
                            n4 = 71;
                            n = 6;
                            n5 = 25;
                            n8 = 16;
                        }
                        graphics.fillRect(n7 + n4 + 1, n6 + n, n5 - 1, n8 + 1);
                        graphics.fillRect(n7 + n4, n6 + n + 1, n5 + 1, n8 - 1);
                        if (Resource.players[i].getType() == 1) {
                            graphics.drawImage(this.imgMenuChip[2], n7 + n4 + 2, n6 + n * 2, 4 | 0x10);
                        } else if (Resource.players[i].getType() == 2) {
                            graphics.drawImage(this.imgMenuChip[1], n7 + n4 + 3, n6 + n * 2, 4 | 0x10);
                        } else if (Resource.players[i].getType() == 3) {
                            graphics.drawImage(this.imgMenuChip[3], n7 + n4 + 5, n6 + n * 2, 4 | 0x10);
                        }
                        if (Resource.isQVGA()) {
                            n4 = 96;
                            n = 24;
                        } else {
                            n4 = 48;
                            n = 14;
                        }
                        if (this.imgSelDia[i] != null) {
                            graphics.drawImage(this.imgSelDia[i], n7 + n4, n6 + n, 1 | 2);
                        }
                        if (i == this.currentSel && Resource.players[i].getType() != 3) {
                            int[] nArray = new int[] { 0, 0 };
                            if (this.aniArrow.getType() == 0) {
                                if (this.aniArrow.getFrame() == 0) {
                                    nArray[0] = 2;
                                }
                            } else if (this.aniArrow.getType() == 1 && this.aniArrow.getFrame() == 0) {
                                nArray[1] = 2;
                            }
                            if (Resource.isQVGA()) {
                                n4 = 74;
                                n = 28;
                            } else {
                                n4 = 37;
                                n = 14;
                            }
                            graphics.drawImage(Resource.imgArrow[0], n7 + n4 - nArray[0], n6 + n, 8 | 2);
                            if (Resource.isQVGA()) {
                                n4 = 120;
                                n = 28;
                            } else {
                                n4 = 60;
                                n = 14;
                            }
                            graphics.drawImage(Resource.imgArrow[1], n7 + n4 + nArray[1], n6 + n, 4 | 2);
                            this.aniArrow.frameProcess();
                        }
                    } else {
                        if (Resource.isQVGA()) {
                            n4 = 104;
                            n = 28;
                        } else {
                            n4 = 52;
                            n = 14;
                        }
                        graphics.drawImage(this.imgMenuText[2], n7 + n4, n6 + n, 1 | 2);
                    }
                    if (Resource.isQVGA()) {
                        n5 = 200;
                        n8 = 52;
                    } else {
                        n5 = 100;
                        n8 = 26;
                    }
                    n6 += n8 + 1;
                }
                if (Resource.isQVGA()) {
                    n4 = 124;
                    n5 = 26;
                } else {
                    n4 = 62;
                    n5 = 13;
                }
                if (Resource.isQVGA()) {
                    n7 = Resource.halfWidth - 104;
                    n6 = Resource.halfHeight - 110;
                    n6 += n5;
                } else {
                    n7 = Resource.halfWidth - 52;
                    n6 = Resource.halfHeight - 62;
                    n6 += n5;
                }
                n = Resource.isQVGA() ? 54 : 27;
                graphics.drawImage(this.imgSelIcon, n7 - this.imgSelIcon.getWidth() / 2,
                        n6 + this.currentSel * n - this.imgSelIcon.getHeight() / 2, 4 | 0x10);
                graphics.drawImage(this.imgButton[0], 1, Resource.totalHeight - (this.imgButton[0].getHeight() + 1),
                        4 | 0x10);
                if (this.process == 1) {
                    this.drawVSHelp(graphics);
                }
                TouchDo.setTouchArea(2, 3, this.process);
                break;
            }
            case ROUND_MENU: {
                int n = 54;
                int n9 = 108;
                graphics.drawImage(Resource.imgBackBuffer, 0, 0, 0);
                graphics.setColor(0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.halfHeight - n);
                graphics.fillRect(0, Resource.halfHeight + n, Resource.totalWidth, Resource.halfHeight - n);
                int n10 = 0;
                if (this.aniArrow.getType() == 0 && this.aniArrow.getFrame() == 0) {
                    n10 = -2;
                } else if (this.aniArrow.getType() == 1 && this.aniArrow.getFrame() == 0) {
                    n10 = 2;
                }
                int n11 = 120;
                int n12 = 16;
                n = 54;
                graphics.drawImage(this.imgSelDia[1],
                        Resource.halfWidth - n11 + Resource.imgEnemy[0].getWidth() + n12
                                - this.imgSelDia[1].getWidth() / 2,
                        Resource.halfHeight + n - this.imgSelDia[1].getHeight(), 4 | 0x10);
                graphics.drawImage(Resource.imgEnemy[0], Resource.halfWidth - n11,
                        Resource.halfHeight + n - Resource.imgEnemy[0].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgSelDia[0],
                        Resource.halfWidth + n11 - Resource.imgPlayer[0].getWidth() - n12
                                - this.imgSelDia[0].getWidth() / 2 + n10,
                        Resource.halfHeight + n - this.imgSelDia[0].getHeight(), 4 | 0x10);
                graphics.drawImage(Resource.imgPlayer[0], Resource.halfWidth + n11 - Resource.imgPlayer[0].getWidth(),
                        Resource.halfHeight + n - Resource.imgPlayer[0].getHeight(), 4 | 0x10);
                if (this.imgRoundChip[0] == null) {
                    this.imgRoundChip[0] = Resource.loadImage("/roundnum_" + Resource.stageNum + ".png");
                }
                if (this.imgRoundChip[1] == null) {
                    this.imgRoundChip[1] = Resource.loadImage("/roundchip_3.png");
                }
                n10 = Resource.halfWidth - (this.imgRoundChip[0].getWidth() + this.imgRoundChip[1].getWidth() + 3) / 2;
                if (Resource.stageNum < 6) {
                    graphics.drawImage(this.imgRoundChip[1], n10,
                            Resource.halfHeight - 30 - this.imgRoundChip[1].getHeight(), 4 | 0x10);
                    graphics.drawImage(this.imgRoundChip[0], n10 + this.imgRoundChip[1].getWidth() + 3,
                            Resource.halfHeight - 30 - this.imgRoundChip[0].getHeight(), 4 | 0x10);
                } else if (Resource.stageNum == 6) {
                    graphics.drawImage(this.imgRoundChip[0], n10,
                            Resource.halfHeight - 30 - this.imgRoundChip[0].getHeight(), 4 | 0x10);
                    graphics.drawImage(this.imgRoundChip[1], n10 + this.imgRoundChip[0].getWidth() + 3,
                            Resource.halfHeight - 30 - this.imgRoundChip[1].getHeight(), 4 | 0x10);
                }
                if (this.process == 0) {
                    if (this.imgButton[0] == null) {
                        this.imgButton[0] = Resource.loadImage("/button_move.png");
                    }
                    if (this.imgButton[1] == null) {
                        this.imgButton[1] = Resource.loadImage("/button_ok.png");
                    }
                    graphics.setColor(255, 255, 255);
                    graphics.setFont(Resource.sf);
                    graphics.drawString("\u9009\u62e9\u60a8\u6bd4\u8d5b\u7684\u9a6c", 2,
                            Resource.totalHeight - this.imgButton[0].getHeight() - 15, 4 | 0x10);
                    graphics.drawImage(this.imgButton[0], 2, Resource.totalHeight - this.imgButton[0].getHeight(),
                            4 | 0x10);
                    graphics.drawImage(this.imgButton[1], this.imgButton[0].getWidth() + 10,
                            Resource.totalHeight - this.imgButton[1].getHeight(), 4 | 0x10);
                    n11 = 50;
                    n = 94;
                    n12 = 20;
                    for (int i = 0; i < 6; ++i) {
                        if (i < Resource.stageNum - 1) {
                            graphics.drawImage(this.imgStageMap[0], Resource.halfWidth - n11 + i * n12,
                                    Resource.halfHeight + n, 1 | 0x20);
                            continue;
                        }
                        if (i == Resource.stageNum - 1) {
                            if (this.aniStageMap.getFrame() != 0)
                                continue;
                            graphics.drawImage(this.imgStageMap[1], Resource.halfWidth - n11 + i * n12,
                                    Resource.halfHeight + n, 1 | 0x20);
                            continue;
                        }
                        graphics.drawImage(this.imgStageMap[1], Resource.halfWidth - n11 + i * n12,
                                Resource.halfHeight + n, 1 | 0x20);
                    }
                    this.aniStageMap.frameProcess();
                    graphics.drawImage(this.imgStageMap[2], Resource.halfWidth - n11 + 5 * n12,
                            Resource.halfHeight + n - this.imgStageMap[1].getHeight() - 1, 1 | 0x20);
                    this.aniArrow.frameProcess();
                    graphics.setClip(0, 0, Resource.totalWidth, Resource.totalHeight);
                } else if (this.process == 1) {
                    if (this.imgRoundChip[2] == null) {
                        this.imgRoundChip[2] = Resource.loadImage("/roundchip_1.png");
                    }
                    if (this.aniFrame.getFrame() < 5) {
                        if (this.aniFrame.getFrame() % 2 == 0) {
                            graphics.drawImage(this.imgRoundChip[2],
                                    Resource.halfWidth - this.imgRoundChip[2].getWidth() / 2, Resource.halfHeight
                                            - this.imgRoundChip[2].getHeight() + this.imgRoundChip[2].getHeight() / 3,
                                    4 | 0x10);
                        }
                    } else {
                        graphics.setColor(0, 0, 0);
                        graphics.drawImage(this.imgRoundChip[2],
                                Resource.halfWidth - this.imgRoundChip[2].getWidth() / 2, Resource.halfHeight
                                        - this.imgRoundChip[2].getHeight() + this.imgRoundChip[2].getHeight() / 3,
                                4 | 0x10);
                        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                    }
                    if (this.aniFrame.frameProcess() == 0) {
                        Resource.aMainView.setStatus(1, 0);
                        this.clearAVT();
                    }
                }
                graphics.setClip(0, 0, Resource.totalWidth, Resource.totalHeight);
                TouchDo.setTouchArea(2, 4, this.process);
                break;
            }
            case MISC_MENU: {
                int n = Resource.halfWidth - 50;
                int n13 = Resource.halfHeight - 20;
                int n14 = 0;
                if (this.process == 1) {
                    n14 = 15;
                }
                graphics.setColor(0, 0, 0);
                graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
                graphics.setColor(106, 90, 24);
                graphics.drawLine(n + 1, n13, n + 100, n13);
                graphics.drawLine(n + 1, n13 + 40, n + 100, n13 + 40 + n14);
                graphics.drawLine(n, n13 + 1, n, n13 + 39 + n14);
                graphics.drawLine(n + 101, n13 + 1, n + 101, n13 + 39 + n14);
                graphics.setColor(246, 189, 123);
                graphics.drawRect(n + 1, n13 + 1, 99, 38 + n14);
                graphics.setColor(255, 230, 209);
                graphics.fillRect(n + 2, n13 + 2, 98, 38 + n14);
                graphics.setColor(106, 90, 24);
                graphics.setFont(Resource.sf);
                if (this.process == 1) {
                    graphics.drawString("\u9690\u85cf\u6e38\u620f\u5df2\u6dfb", n + 5, n13 + 7, 4 | 0x10);
                    graphics.drawString("\u52a0\uff0c\u53ef\u4ece\u201c\u7279", n + 5, n13 + 22, 4 | 0x10);
                    graphics.drawString("\u522b\u83dc\u5355\u201d\u8fdb\u5165", n + 5, n13 + 37, 4 | 0x10);
                } else {
                    graphics.drawString("\u4f60\u5df2\u7ecf\u83b7\u5f97", n + 5, n13 + 7, 4 | 0x10);
                    graphics.drawString("\u65b0\u7684\u9a6c\u79cd\u3002", n + 5, n13 + 22, 4 | 0x10);
                }
                TouchDo.setTouchArea(2, 5, this.process);
                break;
            }
            case SPECIAL_MENU: {
                this.drawSpecialMenu(graphics);
                break;
            }
            case GIFT_MENU: {
                int n = this.stringWidth / 2;
                Resource.drawPanel(graphics, Resource.halfWidth - 60, Resource.halfHeight - 60, 120, 90);
                graphics.setColor(106, 90, 24);
                graphics.drawString("\u5f53\u524d\u64cd\u4f5c\u9700\u8981\u8bbf\u95ee", Resource.halfWidth - n,
                        Resource.halfHeight - 55, 20);
                graphics.drawString("\u7f51\u7edc\uff0c\u53ef\u80fd\u4f1a\u4ea7\u751f", Resource.halfWidth - n,
                        Resource.halfHeight - 55 + 15, 20);
                graphics.drawString("\u6570\u636e\u6d41\u91cf.(\u5927\u6982\u610f\u601d)", Resource.halfWidth - n,
                        Resource.halfHeight - 55 + 30, 20);
                graphics.drawString("\u662f\u5426\u7ee7\u7eed?", Resource.halfWidth - n, Resource.halfHeight - 55 + 45,
                        20);
                graphics.setColor(24, 90, 106);
                graphics.drawString("OK:\u786e\u5b9a  CLR:\u53d6\u6d88", Resource.halfWidth,
                        Resource.halfHeight - 55 + 65, 17);
                break;
            }
            case GAMEZONE_MENU: {
                int n = this.stringWidth / 2;
                Resource.drawPanel(graphics, Resource.halfWidth - 60, Resource.halfHeight - 60, 120, 90);
                graphics.setColor(106, 90, 24);
                graphics.drawString("\u5f53\u524d\u64cd\u4f5c\u9700\u8981\u8bbf\u95ee", Resource.halfWidth - n,
                        Resource.halfHeight - 55, 20);
                graphics.drawString("\u7f51\u7edc\uff0c\u53ef\u80fd\u4f1a\u4ea7\u751f", Resource.halfWidth - n,
                        Resource.halfHeight - 55 + 15, 20);
                graphics.drawString("\u6570\u636e\u6d41\u91cf.(\u5927\u6982\u610f\u601d)", Resource.halfWidth - n,
                        Resource.halfHeight - 55 + 30, 20);
                graphics.drawString("\u662f\u5426\u7ee7\u7eed?", Resource.halfWidth - n, Resource.halfHeight - 55 + 45,
                        20);
                graphics.setColor(24, 90, 106);
                graphics.drawString("OK:\u786e\u5b9a  CLR:\u53d6\u6d88", Resource.halfWidth,
                        Resource.halfHeight - 55 + 65, 17);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (this.state == MAIN_MENU) {
            if ((this.process == 0 || this.process == 1 || this.process == 2) && keyCode >= 0 && keyCode <= 6) {
                this.currentSel = keyCode;
                keyCode = TouchDo.KEY_RETURN;
            }
        } else if (this.state == STORY_MENU) {
            if (this.process == 0) {
                if (keyCode == TouchDo.KEY_NUM1) {
                    this.currentSel = 0;
                    keyCode = TouchDo.KEY_RETURN;
                } else if (keyCode == TouchDo.KEY_NUM2) {
                    this.currentSel = 1;
                    keyCode = TouchDo.KEY_RETURN;
                }
            } else if (this.process == 1) {
                // empty if block
            }
        } else if (this.state == VS_MENU) {
            if (this.process == 0) {
                if (keyCode == 0 || keyCode == 1) {
                    this.currentSel = 0;
                } else if (keyCode == 2 || keyCode == 3 || keyCode == 4) {
                    this.currentSel = 1;
                } else if (keyCode == 5 || keyCode == 6 || keyCode == 7) {
                    this.currentSel = 2;
                } else if (keyCode == 8) {
                    this.currentSel = 3;
                }
                if (keyCode == 0 || keyCode == 2 || keyCode == 5) {
                    keyCode = TouchDo.KEY_NUM4;
                } else if (keyCode == 1 || keyCode == 3 || keyCode == 6) {
                    keyCode = TouchDo.KEY_NUM6;
                } else if (keyCode == 4 || keyCode == 7 || keyCode == 8) {
                    keyCode = TouchDo.KEY_NUM5;
                }
            } else if (this.process == 1 && keyCode == 9) {
                keyCode = TouchDo.KEY_STAR;
            }
        } else if (this.state == SPECIAL_MENU && this.process == 0) {
            if (keyCode == TouchDo.KEY_NUM1) {
                this.currentSel = 0;
                keyCode = TouchDo.KEY_RETURN;
            } else if (keyCode == TouchDo.KEY_NUM2) {
                this.currentSel = 1;
                keyCode = TouchDo.KEY_RETURN;
            }
        }
        switch (this.state) {
            case PAUSE_MENU: {
                if (keyCode == TouchDo.KEY_MENU) {
                    Resource.aMainView.appDestroy();
                    break;
                }
                if (keyCode != TouchDo.KEY_CONFIRM)
                    break;
                this.setStatus(this.lastState);
                break;
            }
            case GIFT_MENU: {
                if (keyCode != TouchDo.KEY_RETURN && keyCode == TouchDo.KEY_CANCEL) {
                    Resource.aMainView.setStatus(0, 1);
                }
                return;
            }
            case GAMEZONE_MENU: {
                if (keyCode != TouchDo.KEY_RETURN && keyCode == TouchDo.KEY_CANCEL) {
                    Resource.aMainView.setStatus(0, 1);
                }
                return;
            }
        }

        
        switch (keyCode) {
            case TouchDo.KEY_NUM1: {
                if (this.state == STORY_MENU && this.process == 1) {
                    Resource.aMainView.setStatus(2, 1);
                    this.clearMM();
                    break;
                }
                if (this.state != STORY_MENU || this.process != 2)
                    break;
                Resource.aMainView.setStatus(2, 1);
                this.clearMM();
                break;
            }
            case TouchDo.KEY_NUM2: {
                if (this.state == STORY_MENU && this.process == 1 || this.state == STORY_MENU && this.process == 2) {
                    this.process = 0;
                    this.currentSel = 1;
                    break;
                }
            }
            case TouchDo.KEY_UP: {
                if (this.state == VS_MENU && this.process == 1 || this.state == ROUND_MENU)
                    break;
                Utils.playSound(5, false);
                this.currentSel = this.currentSel > 0 ? (this.currentSel = this.currentSel - 1) : this.menuLength - 1;
                break;
            }
            case TouchDo.KEY_DOWN:
            case TouchDo.KEY_NUM8: {
                if (this.state == VS_MENU && this.process == 1 || this.state == ROUND_MENU || this.state == STORY_MENU && this.process == 1)
                    break;
                Utils.playSound(5, false);
                this.currentSel = this.currentSel < this.menuLength - 1 ? (this.currentSel = this.currentSel + 1) : 0;
                break;
            }
            case TouchDo.KEY_CONFIRM:
            case TouchDo.KEY_RETURN:
            case TouchDo.KEY_NUM5: {
                if (this.state == MISC_MENU) {
                    Utils.playSound(17, false);
                    if (this.process == 1) {
                        this.process = 0;
                        break;
                    }
                    Resource.newGame();
                    Resource.saveGame();
                    Resource.aMainView.setStatus(0, 1);
                    break;
                }
                this.goMenu(this.currentSel);
                break;
            }
            case TouchDo.KEY_LEFT:
            case TouchDo.KEY_NUM4: {
                if (this.state == VS_MENU && this.process == 0) {
                    if (this.currentSel >= 3 || Resource.players[this.currentSel].type == 3)
                        break;
                    this.selectChangeDia(this.currentSel, 1);
                    this.aniArrow.init(0, 2, 200, false);
                    break;
                }
                if (this.state != ROUND_MENU || this.process != 0)
                    break;
                this.selectChangeDia(0, 1);
                this.aniArrow.init(0, 2, 200, false);
                break;
            }
            case TouchDo.KEY_RIGHT:
            case TouchDo.KEY_NUM6: {
                if (this.state == VS_MENU && this.process == 0) {
                    if (this.currentSel >= 3 || Resource.players[this.currentSel].type == 3)
                        break;
                    this.selectChangeDia(this.currentSel, 2);
                    this.aniArrow.init(1, 2, 200, false);
                    break;
                }
                if (this.state != ROUND_MENU || this.process != 0)
                    break;
                this.selectChangeDia(0, 2);
                this.aniArrow.init(1, 2, 200, false);
                break;
            }
            case TouchDo.KEY_CANCEL:
            case TouchDo.KEY_MENU:
            case TouchDo.KEY_NUM0: {
                if (this.state == MAIN_MENU)
                    break;
                if (this.state == VS_MENU && this.process == 1) {
                    this.process = 0;
                    break;
                }
                if (this.state == SPECIAL_MENU && this.process == 1) {
                    this.process = 0;
                    break;
                }
                Resource.aMainView.setStatus(0, 1);
                Resource.aMainView.aBoardView.clearVSImg();
                this.clearAVT();
                break;
            }
            case TouchDo.KEY_STAR: {
                if (this.state != VS_MENU)
                    break;
                this.process = this.process == 1 ? 0 : 1;
            }
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyUp'");
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchDown'");
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchUp'");
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchDragged'");
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
    }
}
