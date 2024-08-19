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

import io.github.creeper12356.core.DiaBoard;

public class BoardView
implements XTimerListener,
Viewable {
    public static final int GAMEREADY = 0;
    public static final int SELECTDIA = 1;
    public static final int MOVEDIA = 2;
    public static final int MOVINGDIA = 3;
    public static final int COMTHINK = 4;
    public static final int COMBO_MSG = 5;
    public static final int TIMEOUT_MSG = 6;
    public static final int CHANGETURN = 7;
    public static final int GAMEOVER = 8;
    public static final int VIEWRESULT = 9;
    public static final int NEXTROUND = 10;
    public static final int NEXTSTAGE = 11;
    public static final int READYTALK = 12;
    public static final int RETURNVSMENU = 13;
    public static final int GAMEFAILED = 14;
    public static final int COMMOVEDIA = 15;
    public static final int HOMEIN = 16;
    public static final int PMSTATE_MAIN = 0;
    public static final int PMSTATE_QUITYESNO = 1;
    public static final int PMSTATE_QUITYESNO2 = 2;
    public static final int PMSTATE_QUITYESNO3 = 3;
    public static final int TALK_START = 0;
    public static final int TALK_OPPONENTCOMBO = 1;
    public static final int TALK_MYCOMBO = 2;
    public static final int TALK_WIN = 3;
    public static final int TALK_LOSE = 4;
    DiaBoard diaBoard;
    Player[] players;
    int state;
    long elapsedtime;
    int cnt;
    boolean bTimeOutTalk = false;
    int currentPlayer;
    int timeOut;
    int timeOutCnt;
    int roundCnt;
    int[] winCnt = new int[2];
    int[] winList = new int[3];
    boolean bWin;
    int stagePoint;
    int showmessageCnt;
    int[] showmessageOrder = new int[2];
    int showComboCnt;
    int showDoNotMove;
    int showDoNotMove_x;
    int showDoNotMove_y;
    int showAimMark;
    int showPopupMenu;
    int menuSel;
    int menuLength;
    int popupmenuState;
    boolean checkHomeIn;
    Image[] imgDia = new Image[3];
    Image[] imgDiaKing = new Image[3];
    Image[] imgDiaSel = new Image[3];
    Image[] imgDiaKingSel = new Image[3];
    Image[] imgShadow = new Image[3];
    Image[] imgMoveNum = new Image[6];
    Image[] imgUI = new Image[16];
    Image[] imgPlayer = new Image[3];
    Image[] imgEnemy = new Image[3];
    Image[] imgRoundChip = new Image[3];
    Image[] imgRoundNum = new Image[4];
    Image[] imgResult = new Image[4];
    Image[] imgRankChip = new Image[7];
    Image[] imgAimMark = new Image[4];
    Image[] imgPopupMenu = new Image[2];
    Image imgHomeIn;
    Image imgTouchEffect;
    int aniDiaFrame;
    int aniDiaMaxFrame;
    int aniDiaSumTime;
    int aniDiaFrameDelay;
    boolean aniDiaRepeat;
    Animation aniMoveGuide = new Animation();
    Animation aniJumpAvata = new Animation();
    Animation aniComboCnt = new Animation();
    Animation aniDoNotMove = new Animation();
    Animation aniFrame = new Animation();
    Animation aniAimMark = new Animation();
    Animation aniStateUI = new Animation();
    StringMgr strmgr;
    static final String[] strTIMEOUT = new String[]{"\u4f60\u82b1\u592a\u957f\u65f6\u95f4\u4e86\uff01", "\u4f60\u7528\u4e0d\u7528\u8fd9\u4e48\u4e45\u554a\uff01", "\u5feb\u70b9\u5427\uff01\u6211\u7b49\u5230\u82b1\u513f\u4e5f\u8c22\u4e86\uff01", "\u6211\u60f3\u4f60\u6700\u597d\u5feb\u70b9\u8d70\uff01", "\u8fd9\u4e48\u8d70\u5bf9\u4e48\uff1f", "\u8ddf\u8717\u725b\u4f3c\u7684\uff01\u5feb\u70b9\uff01", "\u83dc\u9e1f\uff01\u5feb\u70b9\uff01", "\u83dc\u9e1f\uff01\u8dd1\u54ea\u53bb\u4e86\uff1f", "\u6211\u518d\u8fd9\u6837\u7b49\u4e0b\u53bb\uff0c\u7f8e\u597d\u7684\u65f6\u5149\u90fd\u6e9c\u8d70\u4e86\u2026", "\u4e3a\u4ec0\u4e48\u4f60\u9700\u8981\u8fd9\u4e48\u4e45\uff1f", "\u5c31\u7b97\u4f60\u60f3\u5f97\u518d\u591a\uff0c\u7ed3\u679c\u90fd\u662f\u4e00\u6837\uff01", "\u563f\uff01\u9ebb\u70e6\u4f60\u5feb\u70b9\u5427\u3002", "\u6709\u672c\u4e8b\u4f60\u5c31\u5feb\u70b9\u8d70\uff01", "\u6211\u662f\u4e00\u4e2a\u6025\u6027\u5b50", "\u563f\uff01\u5feb\u70b9\u5427\uff01", "\u6211\u8981\u9650\u5236\u65f6\u95f4\uff01", "\u6211\u7b49\u4e0d\u53ca\u4e86\uff0c\u4f60\u5f97\u9a6c\u4e0a\u8d70\u3002", "\u4e0d\u884c\u7684\u8bdd\u5c31\u653e\u5f03\u5427\uff01"};
    static final String[] strMYCOMBO = new String[]{"", "\u563f\u563f...", "\u554a\uff01", "\u5475\u5475...", "\u54b3\u54b3\u54b3...", "\u554a\u54c8\u54c8\u54c8..", "\u770b\u6211\u7684\u7cbe\u5f69\u8868\u6f14\uff01"};
    static final String[] strOPPONENTCOMBO = new String[]{"\u4e0b\u4e00\u6b21\u80fd\u8d70\u5f97\u77ed\u4e00\u70b9\u4e48\uff1f", "\u8fd9\u5bf9\u6211\u6765\u8bf4\u6ca1\u4ec0\u4e48\u597d\u5904\u3002", "\u4f60\u8fd9\u4e00\u6b65\u8d70\u5f97\u4e0d\u9519\uff01", "\u5f88\u597d\u5f88\u5f3a\u5927\uff01", "\u4f60\u4e0d\u5e94\u8be5\u8d70\u8fd9\u4e48\u8fdc...", "\u522b\u592a\u8d2a\u5fc3\u4e86\u54e6\u3002", "\u55ef...", "\u8fd9\u5bf9\u6211\u6765\u8bf4\u6ca1\u4ec0\u4e48\u597d\u5904\u3002", "\u54ce\u5440\uff01", "\u5929\u554a\uff01", "\u54ce\u5440...", "\u54ce\uff01"};
    static final String[] strWIN = new String[]{"", "\u54c8\u54c8\u54c8\uff0c\u6211\u8d62\u4e86\uff01", "\u6211\u771f\u7684\u8d62\u4e86\u5417\uff1f", "\u5982\u679c\u4f60\u60f3\u505a\u5f97\u4e13\u4e1a\u70b9\uff0c\u5c31\u5e94\u8be5\u50cf\u6211\u521a\u624d\u5c55\u793a\u7684\u90a3\u6837\u505a\uff01", "\u89c1\u8bc6\u5230\u6211\u7684\u7cbe\u6e5b\u6280\u672f\u4e86\u5427", "\u770b\u6765\u6211\u8fd8\u662f\u5b9d\u5200\u672a\u8001\uff01", "\u5f88\u660e\u663e\uff01\u4e00\u5f00\u59cb\u5c31\u6ce8\u5b9a\u662f\u6211\u8d62\u4e86\uff01"};
    static final String[] strLOSE = new String[]{"", "\u600e\u4e48\u56de\u4e8b\uff1f\u6211\u8f93\u4e86\uff1f", "\u4ec0\u4e48\uff1f\u6211\u8f93\u4e86\uff1f", "\u7ec8\u4e8e\uff0c\u67d0\u4eba\u8f93\u4e86\u3002", "\u54ce\uff01\u6211\u9519\u4e86\u3002", "\u6b64\u65f6\u72b6\u6001\u4e0d\u4f73\uff01", "\u4f60\u77e5\u9053...\u6211\u6545\u610f\u8f93\u7684\u3002"};
    static final String[] strREADY = new String[]{"", "\u4eca\u5929\u611f\u89c9\u4e0d\u9519\uff01", "\u5f88\u5feb\u4f60\u5c31\u53ef\u4ee5\u89c1\u8bc1\u6211\u7684\u80dc\u5229\uff01", "\u6211\u4efb\u4f55\u65f6\u523b\u90fd\u53ef\u4ee5\u63a5\u53d7\u4f60\u7684\u6295\u964d\u3002", "\u8ba9\u4f60\u89c1\u8bc6\u4e0b\u6211\u7cbe\u6e5b\u7684\u6280\u672f\u3002", "\u5475\u5475\u5475\uff01\u670d\u4e0d\u670d\u8f93\uff1f", "\u8ba9\u6211\u6765\u6559\u4f60\u51e0\u62db\u5427\u3002"};
    static final String[] strTALK_YC = new String[]{"\u6211\u80fd\u884c\u7684\uff01", "\u4e0d\u91c7\u53d6\u884c\u52a8\u5c31\u50cf\u4f60\u662f\u6545\u610f\u8f93\u7684\uff01", "\u5466\u5475\uff01", "\u52a0\u6cb9\uff01", "\u6211\u80af\u5b9a\u4f1a\u8d62\u7684\uff01", "\u73b0\u5728\u57fa\u672c\u4e0a\u5df2\u7ecf\u8d62\u4e86\uff01", "\u5929\u554a\uff01", "\u6211\u8fd8\u80fd\u505a\u5f97\u66f4\u597d\u4e48\uff1f", "\u54ce\uff01\u975e\u5e38\u62b1\u6b49\uff01", "\u54ce!", "\u5509\uff01", "\u8fd9\u6b21\u6211\u4ece\u4f60\u8fd9\u5b66\u5230\u4e86\u4e0d\u5c11\uff01", "\u54c8\u54c8\u54c8\uff01", "\u770b\u6211\u7684\u7cbe\u5f69\u8868\u6f14\uff01", "\u770b\u6211\u7cbe\u5f69\u7684\u79fb\u52a8\uff01", "\u54c8\u54c8\u54c8\u54c8", "\u4eca\u5929\u662f\u6211\u7684\u5e78\u8fd0\u65e5\uff01", "\u4eca\u5929\u662f\u6211\u7684\u5e78\u8fd0\u65e5\uff01", "\u4e0d\u8981\u518d\u79fb\u8fd9\u4e48\u957f\u4e86\uff01", "\u8fd9\u6b21\u4f60\u8d70\u7684\u5f88\u68d2\uff01", "\u8bf7\u4e0d\u8981\u5728\u8d70\u8fd9\u4e48\u957f\u4e86\uff01", "\u770b\u8d77\u6765\u8d70\u7684\u4e0d\u9519\u54e6\uff01", "\u55ef...\u5f88\u597d\u5f88\u597d\uff01", "\u738b\u8005\u7ec8\u7a76\u662f\u738b\u8005\uff01", "\u54c8\u54c8\uff0c\u6211\u8d62\u4e86\uff01", "\u54c8\u54c8\u54c8\uff0c\u6211\u8d62\u4e86\uff0c\u6211\u8d62\u4e86", "\u8fd9\u6b21\u6bd4\u8d5b\u6211\u8d62\u4e86\u6211\u3002", "\u5475\u5475\uff0c\u6211\u8d62\u4e86\u3002", "\u554a\u54c8\u54c8\u54c8\u54c8\uff0c\u6211\u8d62\u4e86", "\u554a\u54c8\u54c8\u54c8\uff0c\u8d62\u4e86\u8d62\u4e86..."};
    static final String[] strTALK_YC_COMBO = new String[]{"\u8fd8\u4e0d\u9519\u3002", "\u8bf7\u8d70\u5f97\u77ed\u4e00\u70b9\uff01", "\u770b\u8d77\u6765\u4e0d\u9519\u3002", "\u8fd9\u6b21\u4f60\u8d70\u5f97\u4e0d\u9519...", "\u6ca1\u529e\u6cd5\u4e86...", "\u54ce\u5440\uff01", "\u4e0d\u9519\uff01", "\u8fd8\u4e0d\u8d56...", "\u8bf7\u8d70\u7684\u77ed\u4e00\u70b9\u3002", "\u4e0d\u9519\uff01", "\u8fd8\u4e0d\u8d56...", "\u8bf7\u8d70\u7684\u77ed\u4e00\u70b9\u3002"};
    static int[] enemyFaceGood = new int[]{11, 22, 22, 24, 16, 16, 20, 20, 22, 28, 8, 30};
    static int[] enemyFaceBad = new int[]{12, 24, 18, 22, 16, 16, 20, 22, 20, 22, 2, 18};
    static int[] myFace = new int[]{6, 18, 6, 10};

    BoardView() {
        this.diaBoard = new DiaBoard();
        this.players = new Player[3];
        for (int i = 0; i < 3; ++i) {
            this.players[i] = Resource.players[i];
        }
        try {
            this.imgShadow[0] = Image.createImage((String)"/shadow_0.png");
            this.imgShadow[1] = Image.createImage((String)"/shadow_1.png");
            this.imgShadow[2] = Image.createImage((String)"/shadow_2.png");
            this.imgMoveNum[0] = Image.createImage((String)"/movenum_1.png");
            this.imgMoveNum[1] = Image.createImage((String)"/movenum_3.png");
            this.imgMoveNum[2] = Image.createImage((String)"/movenum_6.png");
            this.imgMoveNum[3] = Image.createImage((String)"/movenum_9.png");
            this.imgMoveNum[4] = Image.createImage((String)"/movenum_7.png");
            this.imgMoveNum[5] = Image.createImage((String)"/movenum_4.png");
            this.imgUI[0] = Image.createImage((String)"/uichip_0.png");
            this.imgUI[1] = Image.createImage((String)"/uichip_1.png");
            this.imgUI[2] = Image.createImage((String)"/menuchip_0.png");
            this.imgUI[3] = Image.createImage((String)"/uichip_2.png");
            this.imgUI[4] = Image.createImage((String)"/uichip_3.png");
            this.imgUI[5] = Image.createImage((String)"/uichip_4.png");
            this.imgUI[13] = Image.createImage((String)"/combopanel.png");
            this.imgUI[14] = Image.createImage((String)"/uichip_12.png");
            this.imgUI[15] = Image.createImage((String)"/uichip_13.png");
            this.imgRoundChip[0] = Image.createImage((String)"/roundchip_2.png");
            this.imgRoundChip[1] = Image.createImage((String)"/roundchip_4.png");
            this.imgRoundChip[2] = Image.createImage((String)"/roundchip_5.png");
            this.imgRoundNum[0] = Image.createImage((String)"/roundnum_1.png");
            this.imgRoundNum[1] = Image.createImage((String)"/roundnum_2.png");
            this.imgRoundNum[2] = Image.createImage((String)"/roundchip_0.png");
            this.imgRoundNum[3] = Image.createImage((String)"/roundnum_3.png");
            this.imgResult[0] = Image.createImage((String)"/result_0.png");
            this.imgResult[1] = Image.createImage((String)"/result_1.png");
            this.imgResult[2] = Image.createImage((String)"/result_2.png");
            this.imgResult[3] = Image.createImage((String)"/result_3.png");
            this.imgAimMark[0] = Image.createImage((String)"/aimchip_0.png");
            this.imgAimMark[1] = Image.createImage((String)"/aimchip_1.png");
            this.imgAimMark[2] = Image.createImage((String)"/aimchip_2.png");
            this.imgAimMark[3] = Image.createImage((String)"/aimchip_3.png");
            this.imgHomeIn = Image.createImage((String)"/homein.png");
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.timeOutCnt = 0;
    }

    public void loadVSImg() {
        try {
            this.imgUI[7] = Image.createImage((String)"/uichip_6.png");
            this.imgUI[8] = Image.createImage((String)"/uichip_7.png");
            this.imgUI[9] = Image.createImage((String)"/uichip_8.png");
            this.imgUI[10] = Image.createImage((String)"/uichip_9.png");
            this.imgUI[11] = Image.createImage((String)"/rankchip_8.png");
            this.imgUI[12] = Image.createImage((String)"/rankchip_9.png");
            this.imgRankChip[0] = Image.createImage((String)"/rankchip_0.png");
            this.imgRankChip[1] = Image.createImage((String)"/rankchip_1.png");
            this.imgRankChip[2] = Image.createImage((String)"/rankchip_2.png");
            this.imgRankChip[3] = Image.createImage((String)"/rankchip_3.png");
            this.imgRankChip[4] = Image.createImage((String)"/rankchip_4.png");
            this.imgRankChip[5] = Image.createImage((String)"/rankchip_5.png");
            this.imgRankChip[6] = Image.createImage((String)"/rankchip_6.png");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void clearVSImg() {
        this.imgUI[7] = null;
        this.imgUI[8] = null;
        this.imgUI[9] = null;
        this.imgUI[10] = null;
        this.imgUI[11] = null;
        this.imgUI[12] = null;
        this.imgRankChip[0] = null;
        this.imgRankChip[1] = null;
        this.imgRankChip[2] = null;
        this.imgRankChip[3] = null;
        this.imgRankChip[4] = null;
        this.imgRankChip[5] = null;
        this.imgRankChip[6] = null;
    }

    public void performed(XTimer xTimer) {
        Resource.aMainView.repaint();
    }

    public void run() {
    }

    public void paint(Graphics graphics) {
        int n;
        int n2;
        if (this.showPopupMenu == 1) {
            this.drawPopupMenu(graphics);
            TouchDo.setTouchArea(6, this.state, this.popupmenuState);
            return;
        }
        TouchDo.setTouchArea(6, this.state, -1);
        if (!(this.state != 1 && this.state != 2 || Resource.gameMode != Resource.GAMEMODE_STORY || this.bTimeOutTalk)) {
            this.timeOut += 100;
            if (this.timeOut >= 8000) {
                ++this.timeOutCnt;
                this.timeOutCnt %= 3;
                this.bTimeOutTalk = true;
                this.showmessageCnt = 0;
                this.showmessageOrder[0] = 1;
                this.strmgr = new StringMgr(strTIMEOUT[(this.players[1].charID - 1) * 3 + this.timeOutCnt], 14, 1);
                this.strmgr.start();
                this.strmgr.setAutoMode();
                Utils.playSound(12, false);
            }
        }
        if (this.state == 12) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            this.drawBattleTalk(graphics);
            if (this.strmgr.getEndAutoMode()) {
                ++this.showmessageCnt;
                if (this.showmessageCnt < 2) {
                    this.strmgr = this.showmessageOrder[this.showmessageCnt] == 1 ? new StringMgr(strREADY[this.players[this.showmessageOrder[this.showmessageCnt]].charID], 14, 1) : new StringMgr(this.getGameTalk(0, this.players[1].charID), 14, 1);
                    this.strmgr.start();
                    this.strmgr.setAutoMode();
                    Utils.playSound(12, false);
                } else {
                    this.state = 0;
                    Utils.playSound(13, false);
                }
            }
        } else if (this.state == 0) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            if (this.aniMoveGuide.getFrame() == 0) {
                graphics.drawImage(this.imgRoundChip[2], Resource.halfWidth - this.imgRoundChip[2].getWidth() / 2, Resource.halfHeight - this.imgRoundChip[2].getHeight(), 4 | 0x10);
            } else if (this.aniMoveGuide.getFrame() == 1) {
                graphics.drawImage(this.imgRoundChip[1], Resource.halfWidth - this.imgRoundChip[1].getWidth() / 2, Resource.halfHeight - this.imgRoundChip[1].getHeight(), 4 | 0x10);
            }
            if (this.aniMoveGuide.frameProcess() == 0) {
                this.state = 1;
                this.aniAimMark.init(8, 100, false);
                this.showAimMark = 1;
            }
        } else if (this.state == 1) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            if (this.showAimMark == 1) {
                n2 = Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPosX() * Resource.HGAB;
                n = Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPosY() * Resource.VGAB;
                int n3 = 5 - this.aniAimMark.getFrame() % 2 * 3;
                graphics.drawImage(this.imgAimMark[0], n2 - this.imgAimMark[0].getWidth() - n3, n - this.imgAimMark[0].getHeight() / 2, 4 | 0x10);
                graphics.drawImage(this.imgAimMark[1], n2 - this.imgAimMark[1].getWidth() / 2, n - this.imgAimMark[1].getHeight() - n3 - 1, 4 | 0x10);
                graphics.drawImage(this.imgAimMark[2], n2 + n3 + 1, n - this.imgAimMark[2].getHeight() / 2, 4 | 0x10);
                graphics.drawImage(this.imgAimMark[3], n2 - this.imgAimMark[3].getWidth() / 2, n + n3 + 1, 4 | 0x10);
                if (this.aniAimMark.frameProcess() == 0) {
                    this.showAimMark = 0;
                }
            }
        } else if (this.state == 4) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            if (this.aniDiaFrame >= 4) {
                if (this.players[this.currentPlayer].getType() == 2) {
                    this.players[this.currentPlayer].getComMove(this.players[0]);
                    this.players[this.currentPlayer].computeMoveGuide();
                    this.players[this.currentPlayer].initMovingDia(this.players[this.currentPlayer].movingList[0]);
                    this.state = 3;
                    this.timeOut = 0;
                    this.aniDiaFrame = 0;
                    this.aniDiaMaxFrame = 5;
                    this.aniDiaSumTime = 0;
                    this.aniDiaFrameDelay = 200;
                    this.aniDiaRepeat = false;
                }
            } else {
                ++this.aniDiaFrame;
            }
        } else if (this.state == 2) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            this.drawMoveGuide(graphics);
        } else if (this.state == 3) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
        } else if (this.state == 15) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
        } else if (this.state == 5) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            this.drawBattleTalk(graphics);
            if (this.strmgr.getEndAutoMode()) {
                ++this.showmessageCnt;
                if (this.showmessageCnt < 2) {
                    this.state = 5;
                    if (this.showmessageOrder[this.showmessageCnt] == 1) {
                        n2 = Resource.getRand(2);
                        this.strmgr = new StringMgr(strOPPONENTCOMBO[(this.players[1].charID - 1) * 2 + n2], 14, 1);
                    } else {
                        this.strmgr = new StringMgr(this.getGameTalk(1, this.players[1].charID), 14, 1);
                    }
                    this.strmgr.start();
                    this.strmgr.setAutoMode();
                    Utils.playSound(12, false);
                } else {
                    this.players[0].charFace = 0;
                    this.players[1].charFace = 0;
                    this.players[this.currentPlayer].endTurn();
                    this.diaBoard.clearPassed();
                    this.changeNextPlayer();
                }
            }
        } else if (this.state == 7) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
        } else if (this.state == 8) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            this.drawGameOver(graphics);
        } else if (this.state == 9) {
            graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            this.drawPoint(graphics);
            this.drawAvata(graphics);
            this.drawShadow(graphics);
            this.drawDias(graphics);
            this.drawViewResult(graphics);
            this.aniMoveGuide.frameProcess();
        } else if (this.state == 10) {
            graphics.setColor(0, 0, 0);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            if (this.aniFrame.frameProcess() == 0) {
                this.nextRoundInit();
                this.state = 0;
                Utils.playSound(13, false);
            }
        } else if (this.state == 11) {
            graphics.setColor(0, 0, 0);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            if (this.aniFrame.frameProcess() == 0) {
                if (Resource.stageNum < 6) {
                    if (Resource.stageNum == 2) {
                        Resource.stageNum = 21;
                        Resource.saveGame();
                        Resource.aMainView.setStatus(8, 1);
                    } else if (Resource.stageNum == 4) {
                        Resource.stageNum = 41;
                        Resource.saveGame();
                        Resource.aMainView.setStatus(8, 2);
                    } else {
                        ++Resource.stageNum;
                        Resource.saveGame();
                        Resource.aMainView.setStatus(0, 4);
                    }
                } else if (Resource.stageNum == 6) {
                    RankView.newRecord();
                    Resource.saveRank();
                    Resource.newGame();
                    Resource.saveGame();
                    Resource.aMainView.setStatus(2, 3);
                }
            }
        } else if (this.state == 13) {
            graphics.setColor(0, 0, 0);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            if (this.aniFrame.frameProcess() == 0) {
                MenuView.drawBG(Resource.getBackBuffer());
                Resource.aMainView.setStatus(0, 3);
            }
        } else if (this.state == 14) {
            graphics.setColor(0, 0, 0);
            graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            if (this.aniFrame.frameProcess() == 0) {
                Resource.pointMgr.setPoint(Resource.pointMgr.getPoint() - this.stagePoint);
                Resource.aMainView.setStatus(2, 2);
            }
        }
        if (this.showComboCnt == 99) {
            graphics.drawImage(this.imgHomeIn, Resource.halfWidth - this.imgHomeIn.getWidth() / 2, Resource.halfHeight - this.imgHomeIn.getHeight() / 2, 4 | 0x10);
            if (this.aniComboCnt.frameProcess() == 0) {
                this.showComboCnt = -1;
                this.endTurn();
            }
        } else if (this.showComboCnt != -1) {
            graphics.drawImage(this.imgUI[13], Resource.halfWidth - this.imgUI[13].getWidth() / 2, Resource.halfHeight - this.imgUI[13].getHeight() / 2, 4 | 0x10);
            if (Resource.isQVGA()) {
                n2 = 1;
                n = 12;
            } else {
                n2 = -1;
                n = 8;
            }
            Resource.drawBigNum(graphics, Resource.halfWidth - n2, Resource.halfHeight - this.imgUI[13].getHeight() / 2 + n, this.showComboCnt, 5);
            if (this.aniComboCnt.frameProcess() == 0) {
                this.showComboCnt = -1;
            }
        }
        if (this.showDoNotMove != -1) {
            graphics.drawImage(this.imgUI[14], this.showDoNotMove_x, this.showDoNotMove_y, 4 | 0x10);
            if (this.aniDoNotMove.frameProcess() == 0) {
                this.showDoNotMove = -1;
            }
        }
        if (this.bTimeOutTalk) {
            this.drawBattleTalk(graphics);
            if (this.strmgr.getEndAutoMode()) {
                this.bTimeOutTalk = false;
                this.timeOut = 0;
            }
        }
    }

    protected void pauseMenu() {
        this.showPopupMenu = 1;
        this.menuSel = 0;
        this.menuLength = 5;
        this.popupmenuState = 0;
    }

    public void keyPressed(int n) {
        if (this.showPopupMenu == 1) {
            this.keyMenu(n);
            return;
        }
        if (n == -7 || n == -8) {
            switch (this.state) {
                case 1: 
                case 2: 
                case 3: {
                    if (this.state == 2 && n == -8) break;
                    this.pauseMenu();
                }
            }
        }
        if (this.state == 1) {
            switch (n) {
                case -3: 
                case 52: {
                    this.players[this.currentPlayer].searchDia(1);
                    this.showAimMark = 0;
                    break;
                }
                case -4: 
                case 54: {
                    this.players[this.currentPlayer].searchDia(2);
                    this.showAimMark = 0;
                    break;
                }
                case -1: 
                case 50: {
                    this.players[this.currentPlayer].searchDia(3);
                    this.showAimMark = 0;
                    break;
                }
                case -2: 
                case 56: {
                    this.players[this.currentPlayer].searchDia(4);
                    this.showAimMark = 0;
                    break;
                }
                case 49: {
                    this.players[this.currentPlayer].searchDia(6);
                    this.showAimMark = 0;
                    break;
                }
                case 51: {
                    this.players[this.currentPlayer].searchDia(8);
                    this.showAimMark = 0;
                    break;
                }
                case 55: {
                    this.players[this.currentPlayer].searchDia(7);
                    this.showAimMark = 0;
                    break;
                }
                case 57: {
                    this.players[this.currentPlayer].searchDia(9);
                    this.showAimMark = 0;
                    break;
                }
                case -6: 
                case -5: 
                case 53: {
                    if (this.players[this.currentPlayer].computeMoveGuide() == 0) {
                        Utils.playSound(4, false);
                        Utils.playVib(2);
                        this.showDoNotMove_x = Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPosX() * Resource.HGAB - 7;
                        this.showDoNotMove_y = Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPosY() * Resource.VGAB - 7;
                        this.showDoNotMove = 1;
                        this.aniDoNotMove.init(2, 100, false);
                        break;
                    }
                    Utils.playSound(6, false);
                    this.state = 2;
                    this.aniDiaFrame = 0;
                    this.aniDiaMaxFrame = 2;
                    this.aniDiaSumTime = 0;
                    this.aniDiaFrameDelay = 500;
                    this.aniDiaRepeat = true;
                    this.aniMoveGuide.init(this.players[this.currentPlayer].possibleDirCnt, 100, false);
                    this.checkHomeIn = this.players[this.currentPlayer].checkHomeCurDia();
                }
            }
        } else if (this.state == 2) {
            int n2 = -1;
            if (n == 49) {
                n2 = 0;
            }
            if (n == 51) {
                n2 = 1;
            }
            if (n == 54) {
                n2 = 2;
            }
            if (n == 57) {
                n2 = 3;
            }
            if (n == 55) {
                n2 = 4;
            }
            if (n == 52) {
                n2 = 5;
            }
            if (n2 != -1) {
                if (this.players[this.currentPlayer].initMovingDia(n2)) {
                    this.state = 3;
                    this.timeOut = 0;
                    this.aniDiaFrame = 0;
                    this.aniDiaMaxFrame = 5;
                    this.aniDiaSumTime = 0;
                    this.aniDiaFrameDelay = 200;
                    this.aniDiaRepeat = false;
                }
                return;
            }
            switch (n) {
                case -8: 
                case 48: {
                    if (this.players[this.currentPlayer].moveCnt > 0) {
                        if (this.players[this.currentPlayer].checkHomeCurDia() && !this.checkHomeIn) {
                            this.state = 16;
                            this.showComboCnt = 99;
                            this.aniComboCnt.init(2, 500, false);
                            Utils.playSound(16, false);
                            break;
                        }
                        this.endTurn();
                        break;
                    }
                    this.state = 1;
                }
            }
        } else if (this.state == 9) {
            if (this.aniMoveGuide.getFrame() < 1) {
                return;
            }
            if (n == -5 || n == 53) {
                if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                    if (this.winCnt[0] == 1) {
                        Resource.accuMove += this.players[0].accuMoveCnt;
                        Resource.accuCombo += this.players[0].accuCombo;
                        Resource.stageClear[Resource.stageNum - 1] = (byte)this.roundCnt;
                        this.state = 11;
                        this.aniFrame.init(2, 400, false);
                    } else if (this.winCnt[1] == 1) {
                        this.state = 14;
                        this.aniFrame.init(2, 400, false);
                    }
                } else if (this.aniFrame.isEnd()) {
                    this.state = 13;
                    this.aniFrame.init(2, 400, false);
                }
            }
        }
    }

    public void keyReleased(int n) {
    }

    void keyMenu(int n) {
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
                    this.showPopupMenu = 0;
                    break;
                }
                if (this.popupmenuState == 1) {
                    this.menuSel = 1;
                    this.goMenu();
                    break;
                }
                if (this.popupmenuState != 2 && this.popupmenuState != 3) break;
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
        if (this.popupmenuState == 2 || this.popupmenuState == 3) {
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
                this.showPopupMenu = 0;
            } else if (this.popupmenuState == 1) {
                Resource.aMainView.setStatus(0, 1);
                Resource.aMainView.aBoardView.clearVSImg();
            } else if (this.popupmenuState == 2) {
                Resource.aMainView.setStatus(0, 1);
                Resource.aMainView.aBoardView.clearVSImg();
            } else if (this.popupmenuState == 3) {
                this.currentPlayer = 1;
                this.bWin = false;
                this.strmgr = new StringMgr(strWIN[this.players[1].charID], 14, 1);
                this.players[0].charFace = 2;
                this.players[1].charFace = 1;
                this.showmessageOrder[0] = 1;
                this.showmessageOrder[1] = 0;
                this.showmessageCnt = 0;
                this.strmgr.start();
                this.strmgr.setAutoMode();
                Utils.playSound(12, false);
                this.state = 8;
                this.showPopupMenu = 0;
            }
        } else if (this.menuSel == 1) {
            if (this.popupmenuState == 1) {
                this.popupmenuState = 0;
                this.menuSel = 4;
                this.menuLength = 5;
                return;
            }
            if (this.popupmenuState == 2 || this.popupmenuState == 3) {
                this.popupmenuState = 0;
                this.menuSel = 1;
                this.menuLength = 5;
                return;
            }
            if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                this.popupmenuState = 3;
                this.menuSel = 1;
                this.menuLength = 2;
                return;
            }
            if (Resource.gameMode == Resource.GAMEMODE_VS) {
                this.popupmenuState = 2;
                this.menuSel = 1;
                this.menuLength = 2;
            }
        } else if (this.menuSel == 2) {
            if (this.popupmenuState == 0) {
                Resource.aMainView.openPopup(6);
            }
        } else if (this.menuSel == 3) {
            if (this.popupmenuState == 0) {
                Resource.aMainView.openPopup(7);
            }
        } else if (this.menuSel == 4 && this.popupmenuState == 0) {
            this.popupmenuState = 1;
            this.menuSel = 1;
            this.menuLength = 2;
        }
    }

    public void init() {
        Resource.aTimer2.setTime(Resource.gameSpeed);
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.resume();
        this.elapsedtime = System.currentTimeMillis();
        this.imgTouchEffect = Resource.loadImage("/toucheffect.png");
        Image image = Resource.loadImage("/background.png");
        Resource.getBackBuffer().drawImage(image, 0, 0, 0);
        image = null;
        Image image2 = Resource.loadImage("/board.png");
        Resource.getBackBuffer().drawImage(image2, Resource.boardPosX, Resource.boardPosY, 20);
        image2 = null;
        this.imgPopupMenu[0] = Resource.loadImage("/menuchip_1.png");
        this.imgPopupMenu[1] = Resource.loadImage("/selicon_0.png");
        this.diaBoard.clearPassed();
        this.diaBoard.clearOnDia();
        if (Resource.playerCnt == 2) {
            try {
                this.players[0].init(this.players[0].getType(), 0, this.players[0].diaType, this.diaBoard);
                this.imgDia[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_n.png"));
                this.imgDiaKing[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_king_n.png"));
                this.imgDiaSel[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_s.png"));
                this.imgDiaKingSel[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_king_s.png"));
                this.players[1].init(this.players[1].getType(), 3, this.players[1].diaType, this.diaBoard);
                this.imgDia[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_n.png"));
                this.imgDiaKing[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_king_n.png"));
                this.imgDiaSel[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_s.png"));
                this.imgDiaKingSel[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_king_s.png"));
                this.players[2].type = 3;
                if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                    this.imgPlayer[0] = Resource.imgPlayer[0];
                    this.imgPlayer[1] = Resource.imgPlayer[1];
                    this.imgPlayer[2] = Resource.imgPlayer[2];
                    this.imgEnemy[0] = Resource.imgEnemy[0];
                    this.imgEnemy[1] = Resource.imgEnemy[1];
                    this.imgEnemy[2] = Resource.imgEnemy[2];
                }
            }
            catch (IOException iOException) {}
        } else if (Resource.playerCnt == 3) {
            try {
                this.players[0].init(this.players[0].getType(), 0, this.players[0].diaType, this.diaBoard);
                this.imgDia[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_n.png"));
                this.imgDiaKing[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_king_n.png"));
                this.imgDiaSel[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_s.png"));
                this.imgDiaKingSel[0] = Image.createImage((String)("/dia_" + this.players[0].diaType + "_king_s.png"));
                this.players[1].init(this.players[1].getType(), 2, this.players[1].diaType, this.diaBoard);
                this.imgDia[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_n.png"));
                this.imgDiaKing[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_king_n.png"));
                this.imgDiaSel[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_s.png"));
                this.imgDiaKingSel[1] = Image.createImage((String)("/dia_" + this.players[1].diaType + "_king_s.png"));
                this.players[2].init(this.players[2].getType(), 4, this.players[2].diaType, this.diaBoard);
                this.imgDia[2] = Image.createImage((String)("/dia_" + this.players[2].diaType + "_n.png"));
                this.imgDiaKing[2] = Image.createImage((String)("/dia_" + this.players[2].diaType + "_king_n.png"));
                this.imgDiaSel[2] = Image.createImage((String)("/dia_" + this.players[2].diaType + "_s.png"));
                this.imgDiaKingSel[2] = Image.createImage((String)("/dia_" + this.players[2].diaType + "_king_s.png"));
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.timeOut = 0;
        this.bTimeOutTalk = false;
        this.currentPlayer = 0;
        this.winCnt[0] = 0;
        this.winCnt[1] = 0;
        this.roundCnt = 0;
        this.winList[0] = -1;
        this.winList[1] = -1;
        this.winList[2] = -1;
        this.stagePoint = 0;
        this.showComboCnt = -1;
        this.showDoNotMove = -1;
        this.showAimMark = 0;
        this.showPopupMenu = 0;
        if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            this.state = 12;
            this.showmessageCnt = 0;
            this.showmessageOrder[0] = 1;
            this.showmessageOrder[1] = 0;
            this.strmgr = new StringMgr(strREADY[this.players[this.showmessageOrder[this.showmessageCnt]].charID], 14, 1);
            this.strmgr.start();
            this.strmgr.setAutoMode();
            Utils.playSound(12, false);
        } else {
            this.state = 0;
            Utils.playSound(13, false);
        }
        this.aniMoveGuide.init(2, 1600, false);
        this.aniJumpAvata.init(4, 200, true);
        this.aniStateUI.init(2, 200, true);
    }

    public void clear() {
        this.imgTouchEffect = null;
    }

    public void setStatus(int n) {
    }

    public int getStatus() {
        return 0;
    }

    void nextRoundInit() {
        this.diaBoard.clearPassed();
        this.diaBoard.clearOnDia();
        this.players[0].init(this.players[0].getType(), 0, this.players[0].diaType, this.diaBoard);
        this.players[1].init(this.players[1].getType(), 3, this.players[1].diaType, this.diaBoard);
        this.showComboCnt = -1;
        this.showDoNotMove = -1;
        this.showAimMark = 0;
        this.showPopupMenu = 0;
        this.stagePoint = 0;
        this.aniMoveGuide.init(2, 1000, false);
        this.timeOut = 0;
        this.bTimeOutTalk = false;
        this.currentPlayer = 0;
        ++this.roundCnt;
    }

    void changeNextPlayer() {
        this.players[this.currentPlayer].sortingDia();
        do {
            ++this.currentPlayer;
            if (this.currentPlayer < 3) continue;
            this.currentPlayer = 0;
        } while (this.players[this.currentPlayer].getType() == 3 || this.players[this.currentPlayer].calcPoint() == 10);
        if (this.players[this.currentPlayer].getType() == 1) {
            this.state = 1;
            this.aniAimMark.init(8, 100, false);
            this.showAimMark = 1;
        } else if (this.players[this.currentPlayer].getType() == 2) {
            this.state = 4;
            this.aniDiaFrame = 0;
        }
        this.aniJumpAvata.init(4, 200, true);
        this.timeOut = 0;
    }

    void endTurn() {
        if (this.players[this.currentPlayer].calcPoint() == 10) {
            this.players[this.currentPlayer].endTurn();
            if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                if (this.currentPlayer == 0) {
                    this.bWin = true;
                    this.strmgr = new StringMgr(strLOSE[this.players[1].charID], 14, 1);
                    this.players[0].charFace = 1;
                    this.players[1].charFace = 2;
                    if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                        Resource.pointMgr.addPoint(100);
                        this.stagePoint += 100;
                    }
                } else {
                    this.bWin = false;
                    this.strmgr = new StringMgr(strWIN[this.players[1].charID], 14, 1);
                    this.players[0].charFace = 2;
                    this.players[1].charFace = 1;
                }
                this.showmessageOrder[0] = 1;
                this.showmessageOrder[1] = 0;
                this.showmessageCnt = 0;
                this.strmgr.start();
                this.strmgr.setAutoMode();
                Utils.playSound(12, false);
                this.state = 8;
                return;
            }
            if (Resource.gameMode == Resource.GAMEMODE_VS) {
                if (Resource.playerCnt == 2) {
                    this.winList[0] = this.currentPlayer + 1;
                    this.state = 8;
                    this.aniFrame.init(1, 1000, false);
                    this.winList[1] = this.winList[0] == 1 ? 2 : 1;
                    this.players[this.currentPlayer].rank = 1;
                    if (this.currentPlayer == 0) {
                        this.players[1].rank = 2;
                    } else {
                        this.players[0].rank = 2;
                    }
                    return;
                }
                if (Resource.playerCnt == 3) {
                    if (this.winList[0] != -1) {
                        this.winList[1] = this.currentPlayer + 1;
                        this.players[this.currentPlayer].rank = 2;
                        if (0 != this.currentPlayer && 0 != this.winList[0] - 1) {
                            this.players[0].rank = 3;
                            this.winList[2] = 1;
                        } else if (1 != this.currentPlayer && 1 != this.winList[0] - 1) {
                            this.players[1].rank = 3;
                            this.winList[2] = 2;
                        } else if (2 != this.currentPlayer && 2 != this.winList[0] - 1) {
                            this.players[2].rank = 3;
                            this.winList[2] = 3;
                        }
                    } else {
                        this.winList[0] = this.currentPlayer + 1;
                        this.players[this.currentPlayer].rank = 1;
                        Utils.playSound(15, false);
                    }
                    this.state = 8;
                    this.aniFrame.init(1, 1000, false);
                    return;
                }
            }
        }
        if (this.players[this.currentPlayer].moveCnt > 2 && Resource.gameMode == Resource.GAMEMODE_STORY) {
            this.state = 5;
            this.showmessageCnt = 0;
            if (this.currentPlayer == 0) {
                this.showmessageOrder[0] = 0;
                this.showmessageOrder[1] = 1;
                this.players[0].charFace = 1;
                this.players[1].charFace = 2;
            } else {
                this.showmessageOrder[0] = 1;
                this.showmessageOrder[1] = 0;
                this.players[0].charFace = 2;
                this.players[1].charFace = 1;
            }
            if (this.bTimeOutTalk) {
                this.bTimeOutTalk = false;
            }
            this.strmgr = this.showmessageOrder[this.showmessageCnt] == 1 ? new StringMgr(strMYCOMBO[this.players[this.showmessageOrder[this.showmessageCnt]].charID], 14, 1) : new StringMgr(this.getGameTalk(2, this.players[1].charID), 14, 1);
            this.strmgr.start();
            this.strmgr.setAutoMode();
            Utils.playSound(12, false);
        } else {
            this.players[this.currentPlayer].endTurn();
            this.diaBoard.clearPassed();
            this.changeNextPlayer();
        }
    }

    void drawBattleTalk(Graphics graphics) {
        int n;
        int n2;
        int n3;
        graphics.setFont(Resource.sf);
        if (this.showmessageOrder[this.showmessageCnt] == 0) {
            n3 = Resource.totalWidth - this.imgPlayer[0].getWidth() - 84 - 6;
            n2 = Resource.totalHeight - this.imgPlayer[0].getHeight() + 15 + 6 + 10;
            n = 2;
        } else {
            n3 = this.imgEnemy[0].getWidth() + 1;
            n2 = 31;
            n = 1;
        }
        this.strmgr.drawTalk(graphics, n3, n2, n, 1, Utils.RGB(197, 32, 41));
    }

    void drawShadow(Graphics graphics, int n, int n2, int n3, int n4) {
        int n5 = this.players[n].getDiaRank(n2) == 0 ? 0 : 1;
        graphics.drawImage(this.imgShadow[n5], n3, n4, 4 | 0x10);
    }

    void drawDia(Graphics graphics, int n, int n2, int n3, int n4, boolean bl) {
        Image[] imageArray = this.players[n].getDiaRank(n2) == 0 ? (bl ? this.imgDiaSel : this.imgDia) : (bl ? this.imgDiaKingSel : this.imgDiaKing);
        graphics.drawImage(imageArray[n], n3 - imageArray[n].getWidth() / 2, n4 - (imageArray[n].getHeight() - 4), 4 | 0x10);
    }

    void drawShadow(Graphics graphics) {
        for (int i = 0; i < 3; ++i) {
            if (this.players[i].getType() == 3) continue;
            for (int j = 0; j < 10; ++j) {
                if (j == this.players[i].currentSel && i == this.currentPlayer && this.state == 3) {
                    this.drawShadow(graphics, i, j, Resource.boardZeroPosX + this.players[i].getDiaPixx(), Resource.boardZeroPosY + this.players[i].getDiaPixy());
                    continue;
                }
                this.drawShadow(graphics, i, j, Resource.boardZeroPosX + this.players[i].getDiaPosX(j) * Resource.HGAB, Resource.boardZeroPosY + this.players[i].getDiaPosY(j) * Resource.VGAB);
            }
        }
    }

    void drawDias(Graphics graphics) {
        graphics.setFont(Resource.sf);
        for (int i = 0; i < 3; ++i) {
            if (this.players[i].getType() == 3) continue;
            for (int j = 0; j < 10; ++j) {
                if (j == this.players[i].getCurrentSel() && i == this.currentPlayer) continue;
                this.drawDia(graphics, i, j, Resource.boardZeroPosX + this.players[i].getDiaPosX(j) * Resource.HGAB, Resource.boardZeroPosY + this.players[i].getDiaPosY(j) * Resource.VGAB, false);
            }
            if (i != this.currentPlayer) continue;
            if (this.state == 1) {
                this.drawDia(graphics, i, this.players[i].getCurrentSel(), Resource.boardZeroPosX + this.players[i].getDiaPosX() * Resource.HGAB, Resource.boardZeroPosY + this.players[i].getDiaPosY() * Resource.VGAB, true);
                continue;
            }
            if (this.state == 2) {
                boolean bl = false;
                if (this.aniDiaFrame == 0) {
                    bl = true;
                } else if (this.aniDiaFrame == 1) {
                    bl = false;
                }
                this.drawDia(graphics, i, this.players[i].getCurrentSel(), Resource.boardZeroPosX + this.players[i].getDiaPosX() * Resource.HGAB, Resource.boardZeroPosY + this.players[i].getDiaPosY() * Resource.VGAB, bl);
                this.aniDiaSumTime += 100;
                if (this.aniDiaSumTime <= this.aniDiaFrameDelay) continue;
                this.aniDiaSumTime = 0;
                ++this.aniDiaFrame;
                if (this.aniDiaFrame != this.aniDiaMaxFrame) continue;
                this.aniDiaFrame = 0;
                continue;
            }
            if (this.state == 3) continue;
            this.drawDia(graphics, i, this.players[i].getCurrentSel(), Resource.boardZeroPosX + this.players[i].getDiaPosX() * Resource.HGAB, Resource.boardZeroPosY + this.players[i].getDiaPosY() * Resource.VGAB, false);
        }
        if (this.state == 3) {
            int[] nArray = new int[]{0, -3, -5, -3, 0};
            if (this.aniDiaFrame == 4) {
                graphics.drawImage(this.imgTouchEffect, Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPixx(), Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPixy() + 2, 1 | 2);
            }
            this.drawDia(graphics, this.currentPlayer, this.players[this.currentPlayer].getCurrentSel(), Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPixx(), Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPixy() + nArray[this.aniDiaFrame], false);
            this.aniDiaSumTime += 100;
            if (this.aniDiaSumTime >= this.aniDiaFrameDelay) {
                this.players[this.currentPlayer].movingDia(4);
                this.aniDiaSumTime = 0;
                ++this.aniDiaFrame;
                if (this.aniDiaFrame == this.aniDiaMaxFrame) {
                    int n;
                    if (this.players[this.currentPlayer].jumpMove >= 2) {
                        this.showComboCnt = this.players[this.currentPlayer].jumpMove - 1;
                        this.aniComboCnt.init(2, 200, false);
                        if (this.currentPlayer == 0) {
                            int n2 = 5;
                            for (n = 1; n < this.showComboCnt; ++n) {
                                n2 *= 2;
                            }
                            if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                                Resource.pointMgr.addPoint(n2);
                                this.stagePoint += n2;
                            }
                        }
                    }
                    if (this.players[this.currentPlayer].getType() == 1) {
                        if (this.players[this.currentPlayer].isMoreMove()) {
                            this.state = 2;
                            this.aniDiaFrame = 0;
                            this.aniDiaMaxFrame = 2;
                            this.aniDiaSumTime = 0;
                            this.aniDiaFrameDelay = 500;
                            this.aniDiaRepeat = true;
                            this.aniMoveGuide.init(this.players[this.currentPlayer].possibleDirCnt, 160, false);
                        } else if (this.players[this.currentPlayer].checkHomeCurDia() && !this.checkHomeIn) {
                            this.state = 16;
                            this.showComboCnt = 99;
                            this.aniComboCnt.init(2, 500, false);
                            Utils.playSound(16, false);
                        } else {
                            this.endTurn();
                        }
                    } else if (this.players[this.currentPlayer].getType() == 2) {
                        if (this.players[this.currentPlayer].moveCnt < this.players[this.currentPlayer].movingListCnt) {
                            n = this.players[this.currentPlayer].moveCnt;
                            this.players[this.currentPlayer].computeMoveGuide();
                            this.players[this.currentPlayer].initMovingDia(this.players[this.currentPlayer].movingList[n]);
                            if (this.currentPlayer == 1) {
                                this.state = 3;
                            }
                            this.timeOut = 0;
                            this.aniDiaFrame = 0;
                            this.aniDiaMaxFrame = 5;
                            this.aniDiaSumTime = 0;
                            this.aniDiaFrameDelay = 200;
                            this.aniDiaRepeat = false;
                        } else {
                            this.endTurn();
                        }
                    }
                }
            }
        }
    }

    void drawMoveGuide(Graphics graphics) {
        int n = this.players[this.currentPlayer].getDiaPosX() * Resource.HGAB + Resource.boardZeroPosX;
        int n2 = this.players[this.currentPlayer].getDiaPosY() * Resource.VGAB + Resource.boardZeroPosY;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        for (int i = 0; i < 6; ++i) {
            if (this.players[this.currentPlayer].getMoveGuide(i) == 0) continue;
            if (i == 0) {
                n3 = n - Resource.HGAB;
                n4 = n2 - Resource.VGAB;
            } else if (i == 1) {
                n3 = n + Resource.HGAB;
                n4 = n2 - Resource.VGAB;
            } else if (i == 2) {
                n3 = n + Resource.HGAB + Resource.HGAB;
                n4 = n2;
            } else if (i == 3) {
                n3 = n + Resource.HGAB;
                n4 = n2 + Resource.VGAB;
            } else if (i == 4) {
                n3 = n - Resource.HGAB;
                n4 = n2 + Resource.VGAB;
            } else if (i == 5) {
                n3 = n - Resource.HGAB - Resource.HGAB;
                n4 = n2;
            }
            if (this.players[this.currentPlayer].getMoveGuide(i) == 2) {
                if (i == 0) {
                    n3 -= Resource.HGAB;
                    n4 -= Resource.VGAB;
                } else if (i == 1) {
                    n3 += Resource.HGAB;
                    n4 -= Resource.VGAB;
                } else if (i == 2) {
                    n3 = n3 + Resource.HGAB + Resource.HGAB;
                } else if (i == 3) {
                    n3 += Resource.HGAB;
                    n4 += Resource.VGAB;
                } else if (i == 4) {
                    n3 -= Resource.HGAB;
                    n4 += Resource.VGAB;
                } else if (i == 5) {
                    n3 = n3 - Resource.HGAB - Resource.HGAB;
                }
            }
            if (n5 > this.aniMoveGuide.getFrame()) continue;
            graphics.drawImage(this.imgMoveNum[i], n3 - this.imgMoveNum[i].getWidth() / 2, n4 - this.imgMoveNum[i].getHeight() / 2, 4 | 0x10);
            ++n5;
        }
        this.aniMoveGuide.frameProcess();
    }

    void drawAvata(Graphics graphics) {
        if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            graphics.drawImage(this.imgPlayer[0], Resource.totalWidth - this.imgPlayer[0].getWidth(), Resource.totalHeight - this.imgPlayer[0].getHeight(), 4 | 0x10);
            graphics.drawImage(this.imgEnemy[0], 0, 0, 4 | 0x10);
            if (this.players[0].charFace != 0) {
                if (this.players[0].charFace == 1) {
                    graphics.drawImage(this.imgPlayer[1], Resource.totalWidth - this.imgPlayer[0].getWidth() + myFace[0], Resource.totalHeight - this.imgPlayer[0].getHeight() + myFace[1], 4 | 0x10);
                } else if (this.players[0].charFace == 2) {
                    graphics.drawImage(this.imgPlayer[2], Resource.totalWidth - this.imgPlayer[0].getWidth() + myFace[2], Resource.totalHeight - this.imgPlayer[0].getHeight() + myFace[3], 4 | 0x10);
                }
            }
            if (this.players[1].charFace != 0) {
                if (this.players[1].charFace == 1) {
                    graphics.drawImage(this.imgEnemy[1], enemyFaceGood[(this.players[1].charID - 1) * 2], enemyFaceGood[(this.players[1].charID - 1) * 2 + 1], 4 | 0x10);
                } else if (this.players[1].charFace == 2) {
                    graphics.drawImage(this.imgEnemy[2], enemyFaceBad[(this.players[1].charID - 1) * 2], enemyFaceBad[(this.players[1].charID - 1) * 2 + 1], 4 | 0x10);
                }
            }
            int n = 0;
            if (this.currentPlayer != 0) {
                n = 1;
            } else if (this.aniStateUI.getFrame() == 0) {
                n = 15;
            }
            graphics.drawImage(this.imgUI[n], Resource.totalWidth - 2 - this.imgUI[n].getWidth(), Resource.totalHeight - this.imgUI[n].getHeight(), 4 | 0x10);
            n = 0;
            if (this.currentPlayer != 1) {
                n = 1;
            } else if (this.aniStateUI.getFrame() == 0) {
                n = 15;
            }
            graphics.drawImage(this.imgUI[n], 2, this.imgEnemy[0].getHeight() - this.imgUI[n].getHeight(), 4 | 0x10);
        } else if (Resource.gameMode == Resource.GAMEMODE_VS) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9 = 0;
            int[] nArray = new int[]{0, 1, 2, 1};
            int n10 = 0;
            if (this.players[0].rank != 0) {
                n9 = 6 + this.players[0].rank;
            } else if (this.currentPlayer == 0) {
                n9 = 0;
                n10 = nArray[this.aniJumpAvata.getFrame()];
                if (this.aniStateUI.getFrame() == 0) {
                    n9 = 15;
                }
            } else {
                n9 = 1;
                n10 = 0;
            }
            if (Resource.isQVGA()) {
                n8 = 34;
                n7 = 8;
                n6 = 24;
                n5 = 4;
                n4 = 48;
                n3 = 22;
                n2 = 82;
                n = 42;
                n10 *= 2;
            } else {
                n8 = 17;
                n7 = 4;
                n6 = 12;
                n5 = 1;
                n4 = 24;
                n3 = 11;
                n2 = 41;
                n = 21;
            }
            graphics.drawImage(this.imgShadow[2], Resource.totalWidth - n8, Resource.totalHeight - n7, 4 | 0x10);
            graphics.drawImage(Resource.imgDiaAvt[this.players[0].diaType], Resource.totalWidth - n6 - Resource.imgDiaAvt[this.players[0].diaType].getWidth() / 2, Resource.totalHeight - n5 - Resource.imgDiaAvt[this.players[0].diaType].getHeight() - n10, 4 | 0x10);
            graphics.drawImage(this.imgUI[n9], Resource.totalWidth - n4 - this.imgUI[n9].getWidth(), Resource.totalHeight - this.imgUI[n9].getHeight(), 4 | 0x10);
            graphics.drawImage(Resource.imgBigNum[1], Resource.totalWidth - n2, Resource.totalHeight - n, 4 | 0x10);
            graphics.drawImage(this.imgUI[2], Resource.totalWidth - n2 + Resource.imgBigNum[1].getWidth(), Resource.totalHeight - n, 4 | 0x10);
            if (this.players[1].rank != 0) {
                n9 = 6 + this.players[1].rank;
            } else if (this.currentPlayer == 1) {
                n9 = 0;
                n10 = nArray[this.aniJumpAvata.getFrame()];
                if (this.aniStateUI.getFrame() == 0) {
                    n9 = 15;
                }
            } else {
                n9 = 1;
                n10 = 0;
            }
            if (Resource.isQVGA()) {
                n8 = 14;
                n7 = 46;
                n6 = 24;
                n5 = 52;
                n4 = 48;
                n3 = 32;
                n2 = 48;
                n = 12;
                n10 *= 2;
            } else {
                n8 = 7;
                n7 = 23;
                n6 = 12;
                n5 = 26;
                n4 = 24;
                n3 = 16;
                n2 = 24;
                n = 6;
            }
            graphics.drawImage(this.imgShadow[2], n8, n7, 4 | 0x10);
            graphics.drawImage(Resource.imgDiaAvt[this.players[1].diaType], n6 - Resource.imgDiaAvt[this.players[1].diaType].getWidth() / 2, n5 - Resource.imgDiaAvt[this.players[1].diaType].getHeight() - n10, 4 | 0x10);
            graphics.drawImage(this.imgUI[n9], n4, n3, 4 | 0x10);
            graphics.drawImage(Resource.imgBigNum[2], n2, n, 4 | 0x10);
            graphics.drawImage(this.imgUI[2], n2 + Resource.imgBigNum[2].getWidth(), n, 4 | 0x10);
            if (Resource.playerCnt == 3) {
                if (this.players[2].rank != 0) {
                    n9 = 6 + this.players[2].rank;
                } else if (this.currentPlayer == 2) {
                    n9 = 0;
                    n10 = nArray[this.aniJumpAvata.getFrame()];
                    if (this.aniStateUI.getFrame() == 0) {
                        n9 = 15;
                    }
                } else {
                    n9 = 1;
                    n10 = 0;
                }
                if (Resource.isQVGA()) {
                    n8 = 32;
                    n7 = 46;
                    n6 = 24;
                    n5 = 52;
                    n4 = 48;
                    n3 = 32;
                    n2 = 82;
                    n = 12;
                    n10 *= 2;
                } else {
                    n8 = 16;
                    n7 = 23;
                    n6 = 12;
                    n5 = 26;
                    n4 = 24;
                    n3 = 16;
                    n2 = 41;
                    n = 6;
                }
                graphics.drawImage(this.imgShadow[2], Resource.totalWidth - n8, n7, 4 | 0x10);
                graphics.drawImage(Resource.imgDiaAvt[this.players[2].diaType], Resource.totalWidth - n6 - Resource.imgDiaAvt[this.players[2].diaType].getWidth() / 2, n5 - Resource.imgDiaAvt[this.players[2].diaType].getHeight() - n10, 4 | 0x10);
                graphics.drawImage(this.imgUI[n9], Resource.totalWidth - n4 - this.imgUI[n9].getWidth(), n3, 4 | 0x10);
                graphics.drawImage(Resource.imgBigNum[3], Resource.totalWidth - n2, n, 4 | 0x10);
                graphics.drawImage(this.imgUI[2], Resource.totalWidth - n2 + Resource.imgBigNum[3].getWidth(), n, 4 | 0x10);
            }
            this.aniJumpAvata.frameProcess();
        }
        if (this.state == 2 || this.state == 1 || this.state == 3) {
            this.aniStateUI.frameProcess();
        }
    }

    void drawGameOver(Graphics graphics) {
        if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            graphics.drawImage(this.imgUI[3], Resource.totalWidth - this.imgPlayer[0].getWidth() - this.imgUI[3].getWidth(), Resource.totalHeight - this.imgUI[3].getHeight(), 4 | 0x10);
            if (this.bWin) {
                graphics.drawImage(this.imgUI[4], Resource.totalWidth - this.imgPlayer[0].getWidth() - this.imgUI[3].getWidth() / 2 - this.imgUI[4].getWidth() / 2, Resource.totalHeight - this.imgUI[3].getHeight() / 2 - this.imgUI[4].getHeight() / 2, 4 | 0x10);
            } else {
                graphics.drawImage(this.imgUI[5], Resource.totalWidth - this.imgPlayer[0].getWidth() - this.imgUI[3].getWidth() / 2 - this.imgUI[5].getWidth() / 2, Resource.totalHeight - this.imgUI[3].getHeight() / 2 - this.imgUI[5].getHeight() / 2, 4 | 0x10);
            }
            this.drawBattleTalk(graphics);
            if (this.strmgr.getEndAutoMode()) {
                ++this.showmessageCnt;
                if (this.showmessageCnt < 2) {
                    this.strmgr = this.bWin ? new StringMgr(this.getGameTalk(3, this.players[1].charID), 14, 1) : new StringMgr(this.getGameTalk(4, this.players[1].charID), 14, 1);
                    this.strmgr.start();
                    this.strmgr.setAutoMode();
                    Utils.playSound(12, false);
                } else {
                    this.players[0].endTurn();
                    this.players[1].endTurn();
                    this.players[0].charFace = 0;
                    this.players[1].charFace = 0;
                    if (this.bWin) {
                        this.winCnt[0] = this.winCnt[0] + 1;
                    } else {
                        this.winCnt[1] = this.winCnt[1] + 1;
                    }
                    this.state = 9;
                    Utils.playSound(10, false);
                    this.aniMoveGuide.init(2, 3000, false);
                }
            }
        } else if (Resource.playerCnt == 2) {
            int n = Resource.imgBigNum[this.winList[0]].getWidth() + this.imgUI[2].getWidth() + 2 + this.imgUI[4].getWidth();
            int n2 = Resource.halfWidth - n / 2;
            graphics.drawImage(Resource.imgBigNum[this.winList[0]], n2, Resource.halfHeight - Resource.imgBigNum[this.winList[0]].getHeight(), 4 | 0x10);
            graphics.drawImage(this.imgUI[2], n2 += Resource.imgBigNum[this.winList[0]].getWidth() + 1, Resource.halfHeight - this.imgUI[2].getHeight(), 4 | 0x10);
            graphics.drawImage(this.imgUI[4], n2 += this.imgUI[2].getWidth() + 1, Resource.halfHeight - this.imgUI[4].getHeight() + 1, 4 | 0x10);
            if (this.aniFrame.frameProcess() == 0) {
                this.players[this.winList[0] - 1].endTurn();
                this.state = 9;
                Utils.playSound(10, false);
                this.aniMoveGuide.init(2, 3000, false);
            }
        } else if (Resource.playerCnt == 3) {
            if (this.winList[1] != -1) {
                int n = Resource.imgBigNum[this.winList[1]].getWidth() + this.imgUI[2].getWidth() + 2 + this.imgUI[4].getWidth();
                int n3 = Resource.halfWidth - n / 2;
                graphics.drawImage(Resource.imgBigNum[this.winList[1]], n3, Resource.halfHeight - Resource.imgBigNum[this.winList[1]].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgUI[2], n3 += Resource.imgBigNum[this.winList[1]].getWidth() + 1, Resource.halfHeight - this.imgUI[2].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgUI[4], n3 += this.imgUI[2].getWidth() + 1, Resource.halfHeight - this.imgUI[4].getHeight() + 1, 4 | 0x10);
                if (this.aniFrame.frameProcess() == 0) {
                    this.players[this.winList[1] - 1].endTurn();
                    this.state = 9;
                    Utils.playSound(10, false);
                    this.aniMoveGuide.init(2, 3000, false);
                }
            } else {
                int n = Resource.imgBigNum[this.winList[0]].getWidth() + this.imgUI[2].getWidth() + 2 + this.imgUI[4].getWidth();
                int n4 = Resource.halfWidth - n / 2;
                graphics.drawImage(Resource.imgBigNum[this.winList[0]], n4, Resource.halfHeight - Resource.imgBigNum[this.winList[0]].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgUI[2], n4 += Resource.imgBigNum[this.winList[0]].getWidth() + 1, Resource.halfHeight - this.imgUI[2].getHeight(), 4 | 0x10);
                graphics.drawImage(this.imgUI[4], n4 += this.imgUI[2].getWidth() + 1, Resource.halfHeight - this.imgUI[4].getHeight() + 1, 4 | 0x10);
                if (this.aniFrame.frameProcess() == 0) {
                    this.players[this.currentPlayer].endTurn();
                    this.diaBoard.clearPassed();
                    this.changeNextPlayer();
                }
            }
        }
    }

    void drawViewResult(Graphics graphics) {
        if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            int n;
            int n2;
            int n3;
            int n4;
            if (Resource.isQVGA()) {
                n4 = 110;
                n3 = 102;
            } else {
                n4 = 55;
                n3 = 51;
            }
            int n5 = Resource.halfWidth - n4;
            int n6 = Resource.halfHeight - n3;
            if (Resource.isQVGA()) {
                n2 = 222;
                n = 170;
            } else {
                n2 = 111;
                n = 85;
            }
            Resource.drawPanel(graphics, n5, n6, n2, n);
            graphics.setColor(246, 189, 123);
            if (Resource.isQVGA()) {
                n4 = 4;
                n3 = 70;
                n2 = 214;
                n = 70;
            } else {
                n4 = 2;
                n3 = 35;
                n2 = 107;
                n = 35;
            }
            graphics.drawLine(n5 + n4, n6 + n3, n5 + n2, n6 + n);
            if (Resource.isQVGA()) {
                n4 = 222;
                n3 = 170;
            } else {
                n4 = 111;
                n3 = 85;
            }
            graphics.drawImage(Resource.imgButton[0], n5 + n4 - Resource.imgButton[0].getWidth() - 3, n6 + n3 - Resource.imgButton[0].getHeight() - 3, 4 | 0x10);
            if (Resource.isQVGA()) {
                n4 = 24;
                n3 = 16;
                n = 28;
            } else {
                n4 = 12;
                n3 = 8;
                n = 15;
            }
            graphics.drawImage(this.imgResult[0], n5 + n4, n6 + n3 + n * 0, 4 | 0x10);
            graphics.drawImage(this.imgResult[1], n5 + n4, n6 + n3 + n * 1, 4 | 0x10);
            graphics.drawImage(this.imgResult[2], n5 + n4, n6 + n3 + n * 2, 4 | 0x10);
            graphics.drawImage(this.imgResult[3], n5 + n4, n6 + n3 + n * 3, 4 | 0x10);
            if (Resource.isQVGA()) {
                n4 = 88;
                n3 = 88;
            } else {
                n4 = 44;
                n3 = 44;
            }
            Resource.drawBigNum(graphics, Resource.halfWidth + n4, Resource.halfHeight - n3, this.players[0].accuMoveCnt, 2);
            if (Resource.isQVGA()) {
                n4 = 88;
                n3 = 58;
            } else {
                n4 = 44;
                n3 = 29;
            }
            Resource.drawBigNum(graphics, Resource.halfWidth + n4, Resource.halfHeight - n3, this.players[0].maxCombo, 2);
            if (Resource.isQVGA()) {
                n4 = 88;
                n3 = 22;
            } else {
                n4 = 44;
                n3 = 11;
            }
            Resource.drawBigNum(graphics, Resource.halfWidth + n4, Resource.halfHeight - n3, this.stagePoint, 2);
            if (Resource.isQVGA()) {
                n4 = 88;
                n3 = 6;
            } else {
                n4 = 44;
                n3 = 3;
            }
            Resource.drawBigNum(graphics, Resource.halfWidth + n4, Resource.halfHeight + n3, Resource.pointMgr.getPoint(), 2);
        } else {
            int n;
            int n7;
            if (Resource.isQVGA()) {
                n7 = 104;
                n = 84;
            } else {
                n7 = 52;
                n = 42;
            }
            int n8 = Resource.halfWidth - n7;
            int n9 = Resource.halfHeight - n;
            int n10 = 0;
            for (int i = 0; i < 3; ++i) {
                int n11;
                int n12;
                graphics.setColor(32, 115, 238);
                if (Resource.isQVGA()) {
                    n7 = 26;
                    n = 54;
                    n12 = 206;
                } else {
                    n7 = 13;
                    n = 27;
                    n12 = 103;
                }
                graphics.drawLine(n8 + n7, n9, n8 + n12 - 1, n9);
                graphics.drawLine(n8 + n7, n9 + n, n8 + n12 - 1, n9 + n);
                graphics.drawLine(n8 + n12, n9 + 1, n8 + n12, n9 + n - 1);
                graphics.setColor(180, 246, 255);
                if (Resource.isQVGA()) {
                    n7 = 24;
                    n = 1;
                    n12 = 182;
                    n11 = 53;
                } else {
                    n7 = 12;
                    n = 1;
                    n12 = 91;
                    n11 = 26;
                }
                graphics.fillRect(n8 + n7, n9 + n, n12, n11);
                graphics.drawImage(this.imgUI[10], n8, n9, 4 | 0x10);
                if (i < 2 || i == 2 && Resource.playerCnt == 3) {
                    if (Resource.isQVGA()) {
                        n7 = 8;
                        n = 12;
                    } else {
                        n7 = 4;
                        n = 6;
                    }
                    graphics.drawImage(this.imgRankChip[i], n8 + n7, n9 + n, 4 | 0x10);
                    n10 = this.winList[i] - 1;
                    if (this.players[n10].getType() == 1) {
                        n10 += 3;
                    } else if (this.players[n10].getType() == 2) {
                        n10 = 6;
                    }
                    if (Resource.isQVGA()) {
                        n7 = 48;
                        n = 38;
                    } else {
                        n7 = 24;
                        n = 19;
                    }
                    graphics.drawImage(this.imgRankChip[n10], n8 + n7, n9 + n, 4 | 0x10);
                }
                graphics.setColor(16, 24, 106);
                if (Resource.isQVGA()) {
                    n7 = 164;
                    n = 16;
                    n12 = 38;
                    n11 = 32;
                } else {
                    n7 = 82;
                    n = 8;
                    n12 = 19;
                    n11 = 16;
                }
                graphics.fillRect(n8 + n7 + 1, n9 + n, n12 - 1, n11 + 1);
                graphics.fillRect(n8 + n7, n9 + n + 1, n12 + 1, n11 - 1);
                if (Resource.isQVGA()) {
                    n7 = 120;
                    n = 18;
                } else {
                    n7 = 60;
                    n = 9;
                }
                graphics.drawImage(this.imgUI[11], n8 + n7, n9 + n, 4 | 0x10);
                if (Resource.isQVGA()) {
                    n7 = 120;
                    n = 34;
                } else {
                    n7 = 60;
                    n = 17;
                }
                graphics.drawImage(this.imgUI[12], n8 + n7, n9 + n, 4 | 0x10);
                if (i < 2 || i == 2 && Resource.playerCnt == 3) {
                    if (Resource.isQVGA()) {
                        n7 = 204;
                        n = 18;
                    } else {
                        n7 = 102;
                        n = 9;
                    }
                    Resource.drawSmallNum(graphics, n8 + n7, n9 + n, this.players[this.winList[i] - 1].accuMoveCnt, 2);
                    if (Resource.isQVGA()) {
                        n7 = 204;
                        n = 34;
                    } else {
                        n7 = 102;
                        n = 17;
                    }
                    Resource.drawSmallNum(graphics, n8 + n7, n9 + n, this.players[this.winList[i] - 1].accuCombo, 2);
                    if (Resource.isQVGA()) {
                        n7 = 98;
                        n = 50;
                    } else {
                        n7 = 49;
                        n = 25;
                    }
                    graphics.drawImage(Resource.imgDiaAvt[this.players[this.winList[i] - 1].diaType], n8 + n7 - Resource.imgDiaAvt[this.players[this.winList[i] - 1].diaType].getWidth() / 2, n9 + n - Resource.imgDiaAvt[this.players[this.winList[i] - 1].diaType].getHeight(), 4 | 0x10);
                }
                n = Resource.isQVGA() ? 54 : 27;
                n9 += n;
            }
        }
    }

    void drawPopupMenu(Graphics graphics) {
        int n;
        int n2;
        graphics.setFont(Resource.sf);
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
        if (Resource.isQVGA()) {
            n2 = 190;
            n = 166;
        } else {
            n2 = 110;
            n = 100;
        }
        graphics.fillRect(n3 + 1, n4, n2 - 1, n + 1 + 40);
        graphics.fillRect(n3, n4 + 1, n2 + 1, n - 1 + 40);
        graphics.setColor(255, 230, 209);
        graphics.fillRect(n3 + 2, n4 + 2, 187, 203);
        graphics.drawImage(this.imgPopupMenu[0], Resource.halfWidth - 22, n4 - 21, 4 | 0x10);
        if (this.popupmenuState == 0) {
            n2 = 48;
            n = 30;
            int n5 = 36;
            graphics.setColor(148, 65, 24);
            graphics.setFont(Resource.sf);
            graphics.drawString("1.\u7ee7\u7eed\u6e38\u620f", n3 + n2 + 10, n4 + n + n5 * 0, 4 | 0x10);
            if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                graphics.drawString("2.\u653e\u5f03\u56de\u5408", n3 + n2 + 10, n4 + n + n5 * 1, 4 | 0x10);
            } else {
                graphics.drawString("2.\u653e\u5f03\u6e38\u620f", n3 + n2 + 10, n4 + n + n5 * 1, 4 | 0x10);
            }
            graphics.drawString("3.\u6e38\u620f\u8bbe\u7f6e", n3 + n2 + 10, n4 + n + n5 * 2, 4 | 0x10);
            graphics.drawString("4.\u6e38\u620f\u5e2e\u52a9", n3 + n2 + 10, n4 + n + n5 * 3, 4 | 0x10);
            graphics.drawString("5.\u9000\u51fa\u6e38\u620f", n3 + n2 + 10, n4 + n + n5 * 4, 4 | 0x10);
            graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + n + this.menuSel * n5, 4 | 0x10);
        }
        if (this.popupmenuState == 1) {
            graphics.setColor(148, 65, 24);
            graphics.setFont(Resource.sf);
            if (Resource.isQVGA()) {
                graphics.drawString("\u9000\u51fa\u6e38\u620f ?", n3 + 58, n4 + 30, 4 | 0x10);
                graphics.drawString("1. \u662f", n3 + 58, n4 + 70, 4 | 0x10);
                graphics.drawString("2. \u5426", n3 + 58, n4 + 110, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + 70 + this.menuSel * 40, 4 | 0x10);
            } else {
                graphics.drawString("\u9000\u51fa\u6e38\u620f ?", n3 + 12, n4 + 15, 4 | 0x10);
                graphics.drawString("1. \u662f", n3 + 24, n4 + 30, 4 | 0x10);
                graphics.drawString("2. \u5426", n3 + 24, n4 + 45, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 7, n4 + 30 + this.menuSel * 15, 4 | 0x10);
            }
        } else if (this.popupmenuState == 2 || this.popupmenuState == 3) {
            graphics.setColor(148, 65, 24);
            graphics.setFont(Resource.sf);
            if (Resource.isQVGA()) {
                graphics.drawString("\u653e\u5f03\u6e38\u620f \uff1f", n3 + 58, n4 + 30, 4 | 0x10);
                graphics.drawString("1. \u662f", n3 + 58, n4 + 70, 4 | 0x10);
                graphics.drawString("2. \u5426", n3 + 58, n4 + 110, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 17, n4 + 70 + this.menuSel * 40, 4 | 0x10);
            } else {
                graphics.drawString("\u653e\u5f03\u6e38\u620f \uff1f", n3 + 12, n4 + 15, 4 | 0x10);
                graphics.drawString("1. \u662f", n3 + 24, n4 + 30, 4 | 0x10);
                graphics.drawString("2. \u5426", n3 + 24, n4 + 45, 4 | 0x10);
                graphics.drawImage(this.imgPopupMenu[1], n3 + 7, n4 + 30 + this.menuSel * 15, 4 | 0x10);
            }
        }
    }

    String getGameTalk(int n, int n2) {
        int n3 = 0;
        int[] nArray = new int[]{0, 1, 2, 3, 4, 5};
        int[] nArray2 = new int[]{18, 19, 20, 21, 22, 23};
        int[] nArray3 = new int[]{12, 13, 14, 15, 16, 17};
        int[] nArray4 = new int[]{24, 25, 26, 27, 28, 29};
        int[] nArray5 = new int[]{6, 7, 8, 9, 10, 11};
        switch (n) {
            case 0: {
                n3 = nArray[n2 - 1];
                break;
            }
            case 1: {
                return strTALK_YC_COMBO[(n2 - 1) * 2 + Resource.getRand(2)];
            }
            case 2: {
                n3 = nArray3[n2 - 1];
                break;
            }
            case 3: {
                n3 = nArray4[n2 - 1];
                break;
            }
            case 4: {
                n3 = nArray5[n2 - 1];
            }
        }
        return strTALK_YC[n3];
    }

    void drawPoint(Graphics graphics) {
        if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            Resource.pointMgr.draw(graphics);
        }
    }
}
