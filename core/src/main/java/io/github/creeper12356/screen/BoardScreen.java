package io.github.creeper12356.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.creeper12356.MyGame;
import io.github.creeper12356.core.DiaBoard;
import io.github.creeper12356.core.Player;
import io.github.creeper12356.utils.Resource;
import io.github.creeper12356.utils.Utils;

public class BoardScreen extends BasicMenuScreen {
    private SpriteBatch batch;

    private Stage stageMove;
    private Stage stageAni;

    private Texture imgBoard;

    public static final int GAMEREADY = 0;
    public static final int SELECTDIA = 1; // 选中棋子的动画
    public static final int MOVEDIA = 2; // 展示移动棋子提示
    public static final int MOVINGDIA = 3; // 移动棋子的动画
    public static final int COMTHINK = 4; // 电脑思考
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
    public static final int COMMOVEDIA = 15; // 电脑移动棋子的动画
    public static final int HOMEIN = 16; // 到达家的动画

    public static final int PMSTATE_MAIN = 0;
    public static final int PMSTATE_QUITYESNO = 1;
    public static final int PMSTATE_QUITYESNO2 = 2;
    public static final int PMSTATE_QUITYESNO3 = 3;
    public static final int TALK_START = 0;
    public static final int TALK_OPPONENTCOMBO = 1;
    public static final int TALK_MYCOMBO = 2;
    public static final int TALK_WIN = 3;
    public static final int TALK_LOSE = 4;

    private DiaBoard diaBoard;
    private Player[] players;
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

    Texture[] imgDia = new Texture[3];
    Texture[] imgDiaKing = new Texture[3];
    Texture[] imgDiaSel = new Texture[3];
    Texture[] imgDiaKingSel = new Texture[3];
    Texture[] imgButton = new Texture[10];
    Texture[] imgMoveNum = new Texture[6];
    // Image[] imgDia = new Image[3];
    // Image[] imgDiaKing = new Image[3];
    // Image[] imgDiaSel = new Image[3];
    // Image[] imgDiaKingSel = new Image[3];
    // Image[] imgShadow = new Image[3];
    // Image[] imgMoveNum = new Image[6];
    // Image[] imgUI = new Image[16];
    // Image[] imgPlayer = new Image[3];
    // Image[] imgEnemy = new Image[3];
    // Image[] imgRoundChip = new Image[3];
    // Image[] imgRoundNum = new Image[4];
    // Image[] imgResult = new Image[4];
    // Image[] imgRankChip = new Image[7];
    // Image[] imgAimMark = new Image[4];
    // Image[] imgPopupMenu = new Image[2];
    // Image imgHomeIn;
    // Image imgTouchEffect;

    static final String[] strTIMEOUT = new String[] { "你花太长时间了！",
            "你用不用这么久啊！",
            "快点吧！我等到花儿也谢了！",
            "我想你最好快点走！", "这样走对么？",
            "跟蜗牛似的！快点！", "菜鸟！快点！",
            "菜鸟！跑哪去了？",
            "我再这样等下去，美好的时光都溜走了…",
            "为什么你需要这么久？",
            "就算你想得再多，结果都是一样！",
            "嘿！麻烦你快点吧。",
            "有本事你就快点走！", "我是一个急性子",
            "嘿！快点吧！", "我要限制时间！",
            "我等不及了，你得马上走。",
            "不行的话就放弃吧！" };
    static final String[] strMYCOMBO = new String[] { "", "嘿嘿...", "啊！", "呵呵...",
            "咳咳咳...", "啊哈哈哈..", "看我的精彩表演！" };
    static final String[] strOPPONENTCOMBO = new String[] {
            "下一次能走得短一点么？",
            "这对我来说没什么好处。",
            "你这一步走得不错！", "很好很强大！",
            "你不应该走这么远...", "别太贪心了哦。",
            "嗯...", "这对我来说没什么好处。", "哎呀！",
            "天啊！", "哎呀...", "哎！" };
    static final String[] strWIN = new String[] { "", "哈哈哈，我赢了！",
            "我真的赢了吗？",
            "如果你想做得专业点，就应该像我刚才展示的那样做！",
            "见识到我的精湛技术了吧",
            "看来我还是宝刀未老！",
            "很明显！一开始就注定是我赢了！" };
    static final String[] strLOSE = new String[] { "", "怎么回事？我输了？",
            "什么？我输了？", "终于，某人输了。",
            "哎！我错了。", "此时状态不佳！",
            "你知道...我是故意输的。" };
    static final String[] strREADY = new String[] { "", "今天感觉不错！",
            "很快你就可以见证我的胜利！",
            "我任何时刻都可以接受你的投降。",
            "让你见识下我精湛的技术。",
            "哈哈哈！服不服输？",
            "让我来教你几招吧。" };
    static final String[] strTALK_YC = new String[] { "我能行的！",
            "不采取行动就像你是故意输的！",
            "哦呵！", "加油！", "我肯定会赢的！",
            "现在基本上已经赢了！", "天啊！",
            "我还能做得更好吗？", "哎！非常抱歉！",
            "哎！", "唉！", "这次我从你这学到了不少！",
            "哈哈哈！", "看我的精彩表演！",
            "看我精彩的移动！", "哈哈哈哈",
            "今天是我的幸运日！",
            "今天是我的幸运日！",
            "不要再移这么长了！",
            "这次你走得很棒！",
            "请不要再走这么长了！",
            "看起来走得不错哦！", "嗯...很好很好！",
            "王者终究是王者！", "哈哈，我赢了！",
            "哈哈哈哈，我赢了，我赢了",
            "这次比赛我赢了我。",
            "呵呵，我赢了。",
            "啊哈哈哈哈，我赢了",
            "啊哈哈哈，赢了赢了..." };
    static final String[] strTALK_YC_COMBO = new String[] { "还不错。",
            "请走得短一点！", "看起来不错。",
            "这次你走得不错...", "没办法了...", "哎呀！",
            "不错！", "还不赖...", "请走得短一点。",
            "不错！", "还不赖...", "请走得短一点。" };

    static int[] enemyFaceGood = new int[] { 11, 22, 22, 24, 16, 16, 20, 20, 22, 28, 8, 30 };
    static int[] enemyFaceBad = new int[] { 12, 24, 18, 22, 16, 16, 20, 22, 20, 22, 2, 18 };
    static int[] myFace = new int[] { 6, 18, 6, 10 };

    public BoardScreen(MyGame myGame) {
        super(myGame, false, false);
        batch = new SpriteBatch();

        imgBoard = Resource.loadImage("board.png");

        this.diaBoard = new DiaBoard();
        this.players = new Player[3];
        for (int i = 0; i < 3; ++i) {
            this.players[i] = Resource.players[i];
        }

        imgButton[0] = Resource.loadImage("touch/t1.png");
        imgButton[1] = Resource.loadImage("touch/t3.png");
        imgButton[2] = Resource.loadImage("touch/t6.png");
        imgButton[3] = Resource.loadImage("touch/t9.png");
        imgButton[4] = Resource.loadImage("touch/t7.png");
        imgButton[5] = Resource.loadImage("touch/t4.png");
        imgButton[6] = Resource.loadImage("touch/tu.png");
        imgButton[7] = Resource.loadImage("touch/td.png");
        imgButton[8] = Resource.loadImage("touch/tc.png");
        imgButton[9] = Resource.loadImage("touch/tok.png");

        imgMoveNum[0] = Resource.loadImage("movenum_1.png");
        imgMoveNum[1] = Resource.loadImage("movenum_3.png");
        imgMoveNum[2] = Resource.loadImage("movenum_6.png");
        imgMoveNum[3] = Resource.loadImage("movenum_9.png");
        imgMoveNum[4] = Resource.loadImage("movenum_7.png");
        imgMoveNum[5] = Resource.loadImage("movenum_4.png");

        stageMove = new Stage(viewport);
        stageAni = new Stage(viewport);
        ImageButton imageButton1 = getImageButton(imgButton[0], imgButton[0], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMoveNumButtonClicked(0, Resource.DIR_LU);
            }
        });

        ImageButton imageButton3 = getImageButton(imgButton[1], imgButton[1], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMoveNumButtonClicked(1, Resource.DIR_RU);
            }
        });

        ImageButton imageButton6 = getImageButton(imgButton[2], imgButton[2], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMoveNumButtonClicked(2, Resource.DIR_RIGHT);
            }
        });

        ImageButton imageButton9 = getImageButton(imgButton[3], imgButton[3], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMoveNumButtonClicked(3, Resource.DIR_RD);
            }
        });

        ImageButton imageButton7 = getImageButton(imgButton[4], imgButton[4], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMoveNumButtonClicked(4, Resource.DIR_LD);
            }
        });

        ImageButton imageButton4 = getImageButton(imgButton[5], imgButton[5], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleMoveNumButtonClicked(5, Resource.DIR_LEFT);
            }
        });

        ImageButton imageButtonUp = getImageButton(imgButton[6], imgButton[6], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players[currentPlayer].searchDia(Resource.DIR_UP);
            }
        });

        ImageButton imageButtonDown = getImageButton(imgButton[7], imgButton[7], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players[currentPlayer].searchDia(Resource.DIR_DOWN);
            }
        });
        ImageButton imageButtonCancel = getImageButton(imgButton[8], imgButton[8], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state == MOVEDIA) {
                    if (players[currentPlayer].getMoveCnt() == 0) {
                        state = GAMEREADY;
                    } else {
                        endTurn();
                    }
                } else {
                    myGame.setScreen(0);
                }
            }
        });

        ImageButton imageButtonOk = getImageButton(imgButton[9], imgButton[9], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state == GAMEREADY) {
                    if (players[currentPlayer].computeMoveGuide() > 0) {
                        state = MOVEDIA;
                    }
                }
            }
        });

        // TODO: magic numbers
        imageButton1.setPosition(17, 81);
        imageButton3.setPosition(52, 81);
        imageButton6.setPosition(79, 44);
        imageButton9.setPosition(45, 8);
        imageButton7.setPosition(9, 8);
        imageButton4.setPosition(8, 44);
        imageButtonUp.setPosition(81, 8);
        imageButtonDown.setPosition(117, 8);
        imageButtonCancel.setPosition(141, 44);
        imageButtonOk.setPosition(43, 44);

        stageMove.addActor(imageButton1);
        stageMove.addActor(imageButton3);
        stageMove.addActor(imageButton6);
        stageMove.addActor(imageButton9);
        stageMove.addActor(imageButton7);
        stageMove.addActor(imageButton4);
        stageMove.addActor(imageButtonUp);
        stageMove.addActor(imageButtonDown);
        stageMove.addActor(imageButtonCancel);
        stageMove.addActor(imageButtonOk);

        // try {
        // this.imgShadow[0] = Image.createImage((String) "/shadow_0.png");
        // this.imgShadow[1] = Image.createImage((String) "/shadow_1.png");
        // this.imgShadow[2] = Image.createImage((String) "/shadow_2.png");
        // this.imgMoveNum[0] = Image.createImage((String) "/movenum_1.png");
        // this.imgMoveNum[1] = Image.createImage((String) "/movenum_3.png");
        // this.imgMoveNum[2] = Image.createImage((String) "/movenum_6.png");
        // this.imgMoveNum[3] = Image.createImage((String) "/movenum_9.png");
        // this.imgMoveNum[4] = Image.createImage((String) "/movenum_7.png");
        // this.imgMoveNum[5] = Image.createImage((String) "/movenum_4.png");
        // this.imgUI[0] = Image.createImage((String) "/uichip_0.png");
        // this.imgUI[1] = Image.createImage((String) "/uichip_1.png");
        // this.imgUI[2] = Image.createImage((String) "/menuchip_0.png");
        // this.imgUI[3] = Image.createImage((String) "/uichip_2.png");
        // this.imgUI[4] = Image.createImage((String) "/uichip_3.png");
        // this.imgUI[5] = Image.createImage((String) "/uichip_4.png");
        // this.imgUI[13] = Image.createImage((String) "/combopanel.png");
        // this.imgUI[14] = Image.createImage((String) "/uichip_12.png");
        // this.imgUI[15] = Image.createImage((String) "/uichip_13.png");
        // this.imgRoundChip[0] = Image.createImage((String) "/roundchip_2.png");
        // this.imgRoundChip[1] = Image.createImage((String) "/roundchip_4.png");
        // this.imgRoundChip[2] = Image.createImage((String) "/roundchip_5.png");
        // this.imgRoundNum[0] = Image.createImage((String) "/roundnum_1.png");
        // this.imgRoundNum[1] = Image.createImage((String) "/roundnum_2.png");
        // this.imgRoundNum[2] = Image.createImage((String) "/roundchip_0.png");
        // this.imgRoundNum[3] = Image.createImage((String) "/roundnum_3.png");
        // this.imgResult[0] = Image.createImage((String) "/result_0.png");
        // this.imgResult[1] = Image.createImage((String) "/result_1.png");
        // this.imgResult[2] = Image.createImage((String) "/result_2.png");
        // this.imgResult[3] = Image.createImage((String) "/result_3.png");
        // this.imgAimMark[0] = Image.createImage((String) "/aimchip_0.png");
        // this.imgAimMark[1] = Image.createImage((String) "/aimchip_1.png");
        // this.imgAimMark[2] = Image.createImage((String) "/aimchip_2.png");
        // this.imgAimMark[3] = Image.createImage((String) "/aimchip_3.png");
        // this.imgHomeIn = Image.createImage((String) "/homein.png");
        // } catch (IOException iOException) {
        // // empty catch block
        // }
        this.timeOutCnt = 0;
    }

    @Override
    public void show() {
        super.show();

        // migrated from init() function

        // Resource.aTimer2.setTime(Resource.gameSpeed);
        // Resource.aTimer2.setTimerListener(this);
        // Resource.aTimer2.resume();
        this.elapsedtime = System.currentTimeMillis();
        // this.imgTouchEffect = Resource.loadImage("/toucheffect.png");
        // Image image = Resource.loadImage("/background.png");
        // Resource.getBackBuffer().drawImage(image, 0, 0, 0);
        // image = null;
        // Image image2 = Resource.loadImage("/board.png");
        // Resource.getBackBuffer().drawImage(image2, Resource.boardPosX,
        // Resource.boardPosY, 20);
        // image2 = null;
        // this.imgPopupMenu[0] = Resource.loadImage("/menuchip_1.png");
        // this.imgPopupMenu[1] = Resource.loadImage("/selicon_0.png");
        this.diaBoard.clearPassed();
        this.diaBoard.clearOnDia();
        if (Resource.playerCnt == 2) {
            this.players[0].init(this.players[0].getType(), 0, this.players[0].getDiaType(), this.diaBoard);
            this.replaceImgDia(0);
            this.players[1].init(this.players[1].getType(), 3, this.players[1].getDiaType(), this.diaBoard);
            this.replaceImgDia(1);
            this.players[2].setType(Player.PLAYERTYPE_OFF);

            if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                // TODO:
                // this.imgPlayer[0] = Resource.imgPlayer[0];
                // this.imgPlayer[1] = Resource.imgPlayer[1];
                // this.imgPlayer[2] = Resource.imgPlayer[2];
                // this.imgEnemy[0] = Resource.imgEnemy[0];
                // this.imgEnemy[1] = Resource.imgEnemy[1];
                // this.imgEnemy[2] = Resource.imgEnemy[2];
            }

        } else if (Resource.playerCnt == 3) {
            this.players[0].init(this.players[0].getType(), 0, this.players[0].getDiaType(), this.diaBoard);
            this.replaceImgDia(0);
            this.players[1].init(this.players[1].getType(), 2, this.players[1].getDiaType(), this.diaBoard);
            this.replaceImgDia(1);
            this.players[2].init(this.players[2].getType(), 4, this.players[2].getDiaType(), this.diaBoard);
            this.replaceImgDia(2);
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
            this.state = GAMEREADY;
            this.showmessageCnt = 0;
            this.showmessageOrder[0] = 1;
            this.showmessageOrder[1] = 0;
            // this.strmgr = new
            // StringMgr(strREADY[this.players[this.showmessageOrder[this.showmessageCnt]].charID],
            // 14,
            // 1);
            // this.strmgr.start();
            // this.strmgr.setAutoMode();
            // Utils.playSound(12, false);
        } else {
            this.state = GAMEREADY;
            // Utils.playSound(13, false);
        }
        // this.aniMoveGuide.init(2, 1600, false);
        // this.aniJumpAvata.init(4, 200, true);
        // this.aniStateUI.init(2, 200, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        System.out.println("this.state == " + state);
        batch.begin();
        batch.draw(imgBoard, 0, Resource.halfHeight - imgBoard.getHeight() / 2 - 10);

        // migrated from paint()
        // int n;
        // int n2;
        // if (this.showPopupMenu == 1) {
        // this.drawPopupMenu(graphics);
        // TouchDo.setTouchArea(6, this.state, this.popupmenuState);
        // return;
        // }
        // TouchDo.setTouchArea(6, this.state, -1);
        if ((this.state == SELECTDIA || this.state == MOVEDIA) &&
                Resource.gameMode == Resource.GAMEMODE_STORY &&
                !this.bTimeOutTalk) {
            // 催促对话

            // this.timeOut += 100;
            // if (this.timeOut >= 8000) {
            // ++this.timeOutCnt;
            // this.timeOutCnt %= 3;
            // this.bTimeOutTalk = true;
            // this.showmessageCnt = 0;
            // this.showmessageOrder[0] = 1;
            // this.strmgr = new StringMgr(strTIMEOUT[(this.players[1].charID - 1) * 3 +
            // this.timeOutCnt], 14, 1);
            // this.strmgr.start();
            // this.strmgr.setAutoMode();
            // Utils.playSound(12, false);
            // }
        }
        if (this.state == READYTALK) {
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // this.drawBattleTalk(graphics);
            // if (this.strmgr.getEndAutoMode()) {
            // ++this.showmessageCnt;
            // if (this.showmessageCnt < 2) {
            // this.strmgr = this.showmessageOrder[this.showmessageCnt] == 1
            // ? new
            // StringMgr(strREADY[this.players[this.showmessageOrder[this.showmessageCnt]].charID],
            // 14, 1)
            // : new StringMgr(this.getGameTalk(0, this.players[1].charID), 14, 1);
            // this.strmgr.start();
            // this.strmgr.setAutoMode();
            // Utils.playSound(12, false);
            // } else {
            // this.state = 0;
            // Utils.playSound(13, false);
            // }
            // }
        } else if (this.state == GAMEREADY) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // if (this.aniMoveGuide.getFrame() == 0) {
            // graphics.drawImage(this.imgRoundChip[2], Resource.halfWidth -
            // this.imgRoundChip[2].getWidth() / 2,
            // Resource.halfHeight - this.imgRoundChip[2].getHeight(), 4 | 0x10);
            // } else if (this.aniMoveGuide.getFrame() == 1) {
            // graphics.drawImage(this.imgRoundChip[1], Resource.halfWidth -
            // this.imgRoundChip[1].getWidth() / 2,
            // Resource.halfHeight - this.imgRoundChip[1].getHeight(), 4 | 0x10);
            // }
            // if (this.aniMoveGuide.frameProcess() == 0) {
            // this.state = 1;
            // this.aniAimMark.init(8, 100, false);
            // this.showAimMark = 1;
            // }
        } else if (this.state == SELECTDIA) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            if (this.showAimMark == 1) {
                // n2 = Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPosX() *
                // Resource.HGAB;
                // n = Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPosY() *
                // Resource.VGAB;
                // int n3 = 5 - this.aniAimMark.getFrame() % 2 * 3;
                // graphics.drawImage(this.imgAimMark[0], n2 - this.imgAimMark[0].getWidth() -
                // n3,
                // n - this.imgAimMark[0].getHeight() / 2, 4 | 0x10);
                // graphics.drawImage(this.imgAimMark[1], n2 - this.imgAimMark[1].getWidth() /
                // 2,
                // n - this.imgAimMark[1].getHeight() - n3 - 1, 4 | 0x10);
                // graphics.drawImage(this.imgAimMark[2], n2 + n3 + 1, n -
                // this.imgAimMark[2].getHeight() / 2, 4 | 0x10);
                // graphics.drawImage(this.imgAimMark[3], n2 - this.imgAimMark[3].getWidth() /
                // 2, n + n3 + 1, 4 | 0x10);
                // if (this.aniAimMark.frameProcess() == 0) {
                // this.showAimMark = 0;
                // }
            }
        } else if (this.state == COMTHINK) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // if (this.aniDiaFrame >= 4) {
            if (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_CPU) {
                this.players[this.currentPlayer].getComMove(this.players[0]);

                Image imgMovingDia = getInitMoveDiaActionImage();
                SequenceAction sequence = Actions.sequence();
                // 添加多个移动动画
                for (int i = 0; i < players[currentPlayer].getMovingListCnt(); ++i) {
                    int movingDir = players[currentPlayer].getMovingList()[i];
                    players[currentPlayer].computeMoveGuide();
                    this.players[this.currentPlayer].initMovingDia(movingDir);
                    this.addMoveDiaAction(sequence, movingDir, players[currentPlayer].getMoveGuide(movingDir));
                    sequence.addAction(Actions.delay(Resource.aniDelayDuration));
                }
                sequence.addAction(new RunnableAction() {
                    @Override
                    public void run() {
                        stageAni.clear();
                        timeOut = 0;
                        endTurn();
                    }
                });
                imgMovingDia.addAction(sequence);
                stageAni.addActor(imgMovingDia);
                this.state = MOVINGDIA;
                this.timeOut = 0;
                // this.aniDiaFrame = 0;
                // this.aniDiaMaxFrame = 5;
                // this.aniDiaSumTime = 0;
                // this.aniDiaFrameDelay = 200;
                // this.aniDiaRepeat = false;
            }
            // } else {
            // ++this.aniDiaFrame;
            // }
        } else if (this.state == MOVEDIA) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            this.drawMoveGuide(batch);

        } else if (this.state == MOVINGDIA) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);

        } else if (this.state == COMMOVEDIA) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
        } else if (this.state == COMBO_MSG) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // this.drawBattleTalk(graphics);
            // if (this.strmgr.getEndAutoMode()) {
            // ++this.showmessageCnt;
            // if (this.showmessageCnt < 2) {
            // this.state = 5;
            // if (this.showmessageOrder[this.showmessageCnt] == 1) {
            // n2 = Resource.getRand(2);
            // this.strmgr = new StringMgr(strOPPONENTCOMBO[(this.players[1].charID - 1) * 2
            // + n2], 14, 1);
            // } else {
            // this.strmgr = new StringMgr(this.getGameTalk(1, this.players[1].charID), 14,
            // 1);
            // }
            // this.strmgr.start();
            // this.strmgr.setAutoMode();
            // Utils.playSound(12, false);
            // } else {
            // this.players[0].charFace = 0;
            // this.players[1].charFace = 0;
            // this.players[this.currentPlayer].endTurn();
            // this.diaBoard.clearPassed();
            // this.changeNextPlayer();
            // }
            // }
        } else if (this.state == CHANGETURN) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
        } else if (this.state == GAMEOVER) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // this.drawGameOver(graphics);
        } else if (this.state == VIEWRESULT) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // this.drawViewResult(graphics);
            // this.aniMoveGuide.frameProcess();
        } else if (this.state == NEXTROUND) {
            // graphics.setColor(0, 0, 0);
            // graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            // if (this.aniFrame.frameProcess() == 0) {
            // this.nextRoundInit();
            // this.state = 0;
            // Utils.playSound(13, false);
            // }
        } else if (this.state == NEXTSTAGE) {
            // graphics.setColor(0, 0, 0);
            // graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            // if (this.aniFrame.frameProcess() == 0) {
            // if (Resource.stageNum < 6) {
            // if (Resource.stageNum == 2) {
            // Resource.stageNum = 21;
            // Resource.saveGame();
            // Resource.aMainView.setStatus(8, 1);
            // } else if (Resource.stageNum == 4) {
            // Resource.stageNum = 41;
            // Resource.saveGame();
            // Resource.aMainView.setStatus(8, 2);
            // } else {
            // ++Resource.stageNum;
            // Resource.saveGame();
            // Resource.aMainView.setStatus(0, 4);
            // }
            // } else if (Resource.stageNum == 6) {
            // RankView.newRecord();
            // Resource.saveRank();
            // Resource.newGame();
            // Resource.saveGame();
            // Resource.aMainView.setStatus(2, 3);
            // }
            // }
        } else if (this.state == RETURNVSMENU) {
            // graphics.setColor(0, 0, 0);
            // graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            // if (this.aniFrame.frameProcess() == 0) {
            // MenuView.drawBG(Resource.getBackBuffer());
            // Resource.aMainView.setStatus(0, 3);
            // }
        } else if (this.state == GAMEFAILED) {
            // graphics.setColor(0, 0, 0);
            // graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
            // if (this.aniFrame.frameProcess() == 0) {
            // Resource.pointMgr.setPoint(Resource.pointMgr.getPoint() - this.stagePoint);
            // Resource.aMainView.setStatus(2, 2);
            // }
        }
        // if (this.showComboCnt == 99) {
        // graphics.drawImage(this.imgHomeIn, Resource.halfWidth -
        // this.imgHomeIn.getWidth() / 2,
        // Resource.halfHeight - this.imgHomeIn.getHeight() / 2, 4 | 0x10);
        // if (this.aniComboCnt.frameProcess() == 0) {
        // this.showComboCnt = -1;
        // this.endTurn();
        // }
        // } else if (this.showComboCnt != -1) {
        // graphics.drawImage(this.imgUI[13], Resource.halfWidth -
        // this.imgUI[13].getWidth() / 2,
        // Resource.halfHeight - this.imgUI[13].getHeight() / 2, 4 | 0x10);
        // if (Resource.isQVGA()) {
        // n2 = 1;
        // n = 12;
        // } else {
        // n2 = -1;
        // n = 8;
        // }
        // Resource.drawBigNum(graphics, Resource.halfWidth - n2,
        // Resource.halfHeight - this.imgUI[13].getHeight() / 2 + n, this.showComboCnt,
        // 5);
        // if (this.aniComboCnt.frameProcess() == 0) {
        // this.showComboCnt = -1;
        // }
        // }
        // if (this.showDoNotMove != -1) {
        // graphics.drawImage(this.imgUI[14], this.showDoNotMove_x,
        // this.showDoNotMove_y, 4 | 0x10);
        // if (this.aniDoNotMove.frameProcess() == 0) {
        // this.showDoNotMove = -1;
        // }
        // }
        // if (this.bTimeOutTalk) {
        // this.drawBattleTalk(graphics);
        // if (this.strmgr.getEndAutoMode()) {
        // this.bTimeOutTalk = false;
        // this.timeOut = 0;
        // }
        // }

        batch.end();

        stageMove.draw();
        stageAni.act(delta);
        stageAni.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();

        stageMove.dispose();
        stageAni.dispose();

        imgBoard.dispose();
        Texture[][] textureArrays = { imgDia, imgDiaKing, imgDiaSel, imgDiaKingSel, imgButton, imgMoveNum };
        for (Texture[] textures : textureArrays) {
            for (Texture texture : textures) {
                if (texture != null) {
                    texture.dispose();
                }
            }
        }
        super.dispose();
    }

    private void replaceImgDia(int playerIndex) {
        this.imgDia[playerIndex] = Resource.replaceTexture(this.imgDia[2],
                "dia_" + this.players[playerIndex].getDiaType() + "_n.png");
        this.imgDiaKing[playerIndex] = Resource.replaceTexture(this.imgDiaKing[2],
                "dia_" + this.players[playerIndex].getDiaType() + "_king_n.png");
        this.imgDiaSel[playerIndex] = Resource.replaceTexture(this.imgDiaSel[2],
                "dia_" + this.players[playerIndex].getDiaType() + "_s.png");
        this.imgDiaKingSel[playerIndex] = Resource.replaceTexture(this.imgDiaKingSel[2],
                "dia_" + this.players[playerIndex].getDiaType() + "_king_s.png");
    }

    // TODO: magic number
    /**
     * @brief 整数的横坐标转为像素横坐标
     * @param posx
     * @return
     */
    private int getDiaPixx(int posx) {
        return 20 + posx * Resource.HGAB;
    }

    /**
     * @brief 整数的纵坐标转为像素纵坐标
     * @param posy
     * @return
     */
    private int getDiaPixy(int posy) {
        return Resource.halfHeight + imgBoard.getHeight() / 2 - 10 - 6 - posy * Resource.VGAB;
    }

    private void drawDias(SpriteBatch batch) {
        for (int i = 0; i < 3; ++i) {
            if (this.players[i].getType() == Player.PLAYERTYPE_OFF)
                continue;
            for (int j = 0; j < 10; ++j) {
                if (j == this.players[i].getCurrentSel() && i == this.currentPlayer)
                    continue;
                this.drawDia(batch,
                        i,
                        j,
                        getDiaPixx(this.players[i].getDiaPosX(j)),
                        getDiaPixy(this.players[i].getDiaPosY(j)),
                        false);
            }
            if (i != this.currentPlayer)
                continue;
            if (this.state == GAMEREADY) {
                this.drawDia(batch,
                        i,
                        this.players[i].getCurrentSel(),
                        getDiaPixx(this.players[i].getDiaPosX()),
                        getDiaPixy(this.players[i].getDiaPosY()),
                        true);
                continue;
            }
            if (this.state == SELECTDIA) {
                boolean bl = false;
                // if (this.aniDiaFrame == 0) {
                // bl = true;
                // } else if (this.aniDiaFrame == 1) {
                // bl = false;
                // }
                this.drawDia(batch,
                        i,
                        this.players[i].getCurrentSel(),
                        getDiaPixx(this.players[i].getDiaPosX()),
                        getDiaPixy(this.players[i].getDiaPosY()),
                        bl);
                // this.aniDiaSumTime += 100;
                // if (this.aniDiaSumTime <= this.aniDiaFrameDelay)
                // continue;
                // this.aniDiaSumTime = 0;
                // ++this.aniDiaFrame;
                // if (this.aniDiaFrame != this.aniDiaMaxFrame)
                // continue;
                // this.aniDiaFrame = 0;
                continue;
            }
            if (this.state == MOVINGDIA)
                continue;
            this.drawDia(batch,
                    i,
                    this.players[i].getCurrentSel(),
                    getDiaPixx(this.players[i].getDiaPosX()),
                    getDiaPixy(this.players[i].getDiaPosY()),
                    true);
        }

        if (this.state == MOVINGDIA) {
            // int[] nArray = new int[] { 0, -3, -5, -3, 0 };
            // if (this.aniDiaFrame == 4) {
            // graphics.drawImage(this.imgTouchEffect,
            // Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPixx(),
            // Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPixy() + 2, 1
            // | 2);
            // }
            // this.drawDia(graphics, this.currentPlayer,
            // this.players[this.currentPlayer].getCurrentSel(),
            // Resource.boardZeroPosX + this.players[this.currentPlayer].getDiaPixx(),
            // Resource.boardZeroPosY + this.players[this.currentPlayer].getDiaPixy() +
            // nArray[this.aniDiaFrame],
            // false);
            // this.aniDiaSumTime += 100;
            // if (this.aniDiaSumTime >= this.aniDiaFrameDelay) {
            // this.players[this.currentPlayer].movingDia(4);
            // this.aniDiaSumTime = 0;
            // ++this.aniDiaFrame;
            // if (this.aniDiaFrame == this.aniDiaMaxFrame) {
            // int n;
            // if (this.players[this.currentPlayer].getJumpMove() >= 2) {
            // // 存在两次及以上的跳跃
            // this.showComboCnt = this.players[this.currentPlayer].getJumpMove() - 1;
            // this.aniComboCnt.init(2, 200, false);
            // if (this.currentPlayer == 0) {
            // int n2 = 5;
            // for (n = 1; n < this.showComboCnt; ++n) {
            // n2 *= 2;
            // }
            // if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            // Resource.pointMgr.addPoint(n2);
            // this.stagePoint += n2;
            // }
            // }
            // }
            // if (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_HUMAN) {
            // if (this.players[this.currentPlayer].isMoreMove()) {
            // this.state = 2;
            // this.aniDiaFrame = 0;
            // this.aniDiaMaxFrame = 2;
            // this.aniDiaSumTime = 0;
            // this.aniDiaFrameDelay = 500;
            // this.aniDiaRepeat = true;
            // this.aniMoveGuide.init(this.players[this.currentPlayer].possibleDirCnt, 160,
            // false);
            // } else if (this.players[this.currentPlayer].checkHomeCurDia() &&
            // !this.checkHomeIn) {
            // // 棋子到家
            // this.state = 16;
            // this.showComboCnt = 99;
            // this.aniComboCnt.init(2, 500, false);
            // Utils.playSound(16, false);
            // } else {
            // this.endTurn();
            // }
            // } else if (this.players[this.currentPlayer].getType() ==
            // Player.PLAYERTYPE_CPU) {
            // if (this.players[this.currentPlayer].moveCnt <
            // this.players[this.currentPlayer].movingListCnt) {
            // n = this.players[this.currentPlayer].moveCnt;
            // this.players[this.currentPlayer].computeMoveGuide();
            // this.players[this.currentPlayer]
            // .initMovingDia(this.players[this.currentPlayer].movingList[n]);
            // if (this.currentPlayer == 1) {
            // this.state = 3;
            // }
            // this.timeOut = 0;
            // this.aniDiaFrame = 0;
            // this.aniDiaMaxFrame = 5;
            // this.aniDiaSumTime = 0;
            // this.aniDiaFrameDelay = 200;
            // this.aniDiaRepeat = false;
            // } else {
            // this.endTurn();
            // }
            // }
            // }
            // }
        }
    }

    /**
     * @brief 绘制一个棋子
     * @details 棋子正下方的位置为posx, posy
     * @param batch
     * @param playerIndex 玩家编号
     * @param diaIndex    棋子编号
     * @param posx        棋子x坐标
     * @param posy        棋子y坐标
     * @param sel         是否被选中
     */
    private void drawDia(SpriteBatch batch, int playerIndex, int diaIndex, int posx, int posy, boolean sel) {
        Texture[] imageArray = this.players[playerIndex].getDiaRank(diaIndex) == 0
                ? (sel ? this.imgDiaSel : this.imgDia)
                : (sel ? this.imgDiaKingSel : this.imgDiaKing);
        batch.draw(imageArray[playerIndex], posx - imageArray[playerIndex].getWidth() / 2, posy);
    }

    void drawAvata(SpriteBatch batch) {
        if (Resource.gameMode == Resource.GAMEMODE_STORY) {
            batch.draw(Resource.imgPlayer[0], Resource.totalWidth - Resource.imgPlayer[0].getWidth(), 0);
            batch.draw(Resource.imgEnemy[0], 0, Resource.totalHeight - Resource.imgEnemy[0].getHeight());
            if (this.players[0].getCharFace() != 0) {
                if (this.players[0].getCharFace() == 1) {
                    // graphics.drawImage(this.imgPlayer[1],
                    // Resource.totalWidth - this.imgPlayer[0].getWidth() + myFace[0],
                    // Resource.totalHeight - this.imgPlayer[0].getHeight() + myFace[1], 4 | 0x10);
                } else if (this.players[0].getCharFace() == 2) {
                    // graphics.drawImage(this.imgPlayer[2],
                    // Resource.totalWidth - this.imgPlayer[0].getWidth() + myFace[2],
                    // Resource.totalHeight - this.imgPlayer[0].getHeight() + myFace[3], 4 | 0x10);
                }
            }
            if (this.players[1].getCharFace() != 0) {
                if (this.players[1].getCharFace() == 1) {
                    // graphics.drawImage(this.imgEnemy[1], enemyFaceGood[(this.players[1].charID -
                    // 1) * 2],
                    // enemyFaceGood[(this.players[1].charID - 1) * 2 + 1], 4 | 0x10);
                } else if (this.players[1].getCharFace() == 2) {
                    // graphics.drawImage(this.imgEnemy[2], enemyFaceBad[(this.players[1].charID -
                    // 1) * 2],
                    // enemyFaceBad[(this.players[1].charID - 1) * 2 + 1], 4 | 0x10);
                }
            }
            // int n = 0;
            // if (this.currentPlayer != 0) {
            // n = 1;
            // } else if (this.aniStateUI.getFrame() == 0) {
            // n = 15;
            // }
            // graphics.drawImage(this.imgUI[n], Resource.totalWidth - 2 -
            // this.imgUI[n].getWidth(),
            // Resource.totalHeight - this.imgUI[n].getHeight(), 4 | 0x10);
            // n = 0;
            // if (this.currentPlayer != 1) {
            // n = 1;
            // } else if (this.aniStateUI.getFrame() == 0) {
            // n = 15;
            // }
            // graphics.drawImage(this.imgUI[n], 2, this.imgEnemy[0].getHeight() -
            // this.imgUI[n].getHeight(), 4 | 0x10);
        }
        // else if (Resource.gameMode == Resource.GAMEMODE_VS) {
        // int n;
        // int n2;
        // int n3;
        // int n4;
        // int n5;
        // int n6;
        // int n7;
        // int n8;
        // int n9 = 0;
        // int[] nArray = new int[] { 0, 1, 2, 1 };
        // int n10 = 0;
        // if (this.players[0].rank != 0) {
        // n9 = 6 + this.players[0].rank;
        // } else if (this.currentPlayer == 0) {
        // n9 = 0;
        // n10 = nArray[this.aniJumpAvata.getFrame()];
        // if (this.aniStateUI.getFrame() == 0) {
        // n9 = 15;
        // }
        // } else {
        // n9 = 1;
        // n10 = 0;
        // }
        // if (Resource.isQVGA()) {
        // n8 = 34;
        // n7 = 8;
        // n6 = 24;
        // n5 = 4;
        // n4 = 48;
        // n3 = 22;
        // n2 = 82;
        // n = 42;
        // n10 *= 2;
        // } else {
        // n8 = 17;
        // n7 = 4;
        // n6 = 12;
        // n5 = 1;
        // n4 = 24;
        // n3 = 11;
        // n2 = 41;
        // n = 21;
        // }
        // graphics.drawImage(this.imgShadow[2], Resource.totalWidth - n8,
        // Resource.totalHeight - n7, 4 | 0x10);
        // graphics.drawImage(Resource.imgDiaAvt[this.players[0].diaType],
        // Resource.totalWidth - n6 -
        // Resource.imgDiaAvt[this.players[0].diaType].getWidth() / 2,
        // Resource.totalHeight - n5 -
        // Resource.imgDiaAvt[this.players[0].diaType].getHeight() - n10,
        // 4 | 0x10);
        // graphics.drawImage(this.imgUI[n9], Resource.totalWidth - n4 -
        // this.imgUI[n9].getWidth(),
        // Resource.totalHeight - this.imgUI[n9].getHeight(), 4 | 0x10);
        // graphics.drawImage(Resource.imgBigNum[1], Resource.totalWidth - n2,
        // Resource.totalHeight - n, 4 | 0x10);
        // graphics.drawImage(this.imgUI[2], Resource.totalWidth - n2 +
        // Resource.imgBigNum[1].getWidth(),
        // Resource.totalHeight - n, 4 | 0x10);
        // if (this.players[1].rank != 0) {
        // n9 = 6 + this.players[1].rank;
        // } else if (this.currentPlayer == 1) {
        // n9 = 0;
        // n10 = nArray[this.aniJumpAvata.getFrame()];
        // if (this.aniStateUI.getFrame() == 0) {
        // n9 = 15;
        // }
        // } else {
        // n9 = 1;
        // n10 = 0;
        // }
        // if (Resource.isQVGA()) {
        // n8 = 14;
        // n7 = 46;
        // n6 = 24;
        // n5 = 52;
        // n4 = 48;
        // n3 = 32;
        // n2 = 48;
        // n = 12;
        // n10 *= 2;
        // } else {
        // n8 = 7;
        // n7 = 23;
        // n6 = 12;
        // n5 = 26;
        // n4 = 24;
        // n3 = 16;
        // n2 = 24;
        // n = 6;
        // }
        // graphics.drawImage(this.imgShadow[2], n8, n7, 4 | 0x10);
        // graphics.drawImage(Resource.imgDiaAvt[this.players[1].diaType],
        // n6 - Resource.imgDiaAvt[this.players[1].diaType].getWidth() / 2,
        // n5 - Resource.imgDiaAvt[this.players[1].diaType].getHeight() - n10, 4 |
        // 0x10);
        // graphics.drawImage(this.imgUI[n9], n4, n3, 4 | 0x10);
        // graphics.drawImage(Resource.imgBigNum[2], n2, n, 4 | 0x10);
        // graphics.drawImage(this.imgUI[2], n2 + Resource.imgBigNum[2].getWidth(), n, 4
        // | 0x10);
        // if (Resource.playerCnt == 3) {
        // if (this.players[2].rank != 0) {
        // n9 = 6 + this.players[2].rank;
        // } else if (this.currentPlayer == 2) {
        // n9 = 0;
        // n10 = nArray[this.aniJumpAvata.getFrame()];
        // if (this.aniStateUI.getFrame() == 0) {
        // n9 = 15;
        // }
        // } else {
        // n9 = 1;
        // n10 = 0;
        // }
        // if (Resource.isQVGA()) {
        // n8 = 32;
        // n7 = 46;
        // n6 = 24;
        // n5 = 52;
        // n4 = 48;
        // n3 = 32;
        // n2 = 82;
        // n = 12;
        // n10 *= 2;
        // } else {
        // n8 = 16;
        // n7 = 23;
        // n6 = 12;
        // n5 = 26;
        // n4 = 24;
        // n3 = 16;
        // n2 = 41;
        // n = 6;
        // }
        // graphics.drawImage(this.imgShadow[2], Resource.totalWidth - n8, n7, 4 |
        // 0x10);
        // graphics.drawImage(Resource.imgDiaAvt[this.players[2].diaType],
        // Resource.totalWidth - n6 -
        // Resource.imgDiaAvt[this.players[2].diaType].getWidth() / 2,
        // n5 - Resource.imgDiaAvt[this.players[2].diaType].getHeight() - n10, 4 |
        // 0x10);
        // graphics.drawImage(this.imgUI[n9], Resource.totalWidth - n4 -
        // this.imgUI[n9].getWidth(), n3, 4 | 0x10);
        // graphics.drawImage(Resource.imgBigNum[3], Resource.totalWidth - n2, n, 4 |
        // 0x10);
        // graphics.drawImage(this.imgUI[2], Resource.totalWidth - n2 +
        // Resource.imgBigNum[3].getWidth(), n,
        // 4 | 0x10);
        // }
        // this.aniJumpAvata.frameProcess();
        // }
        if (this.state == 2 || this.state == 1 || this.state == 3) {
            // this.aniStateUI.frameProcess();
        }
    }

    void drawMoveGuide(SpriteBatch batch) {
        this.players[this.currentPlayer].computeMoveGuide();
        for (int i = 0; i < 6; ++i) {
            if (this.players[this.currentPlayer].getMoveGuide(i) == 0)
                continue;

            int targetPosx = this.players[this.currentPlayer].getDiaPosX()
                    + Resource.hInc[i] * this.players[this.currentPlayer].getMoveGuide(i);
            int targetPosy = this.players[this.currentPlayer].getDiaPosY()
                    + Resource.vInc[i] * this.players[this.currentPlayer].getMoveGuide(i);
            batch.draw(imgMoveNum[i], getDiaPixx(targetPosx) - imgMoveNum[i].getWidth() / 2, getDiaPixy(targetPosy));
            // if (n5 > this.aniMoveGuide.getFrame())
            // continue;
            // graphics.drawImage(this.imgMoveNum[i], n3 - this.imgMoveNum[i].getWidth() /
            // 2,
            // n4 - this.imgMoveNum[i].getHeight() / 2, 4 | 0x10);
            // ++n5;
        }
        // this.aniMoveGuide.frameProcess();
    }

    private void handleMoveNumButtonClicked(int movingDir, int searchDir) {
        if (state == GAMEREADY) {
            players[currentPlayer].searchDia(searchDir);
        } else if (state == MOVEDIA) {
            int moveStep = players[currentPlayer].getMoveGuide(movingDir);
            if (moveStep > 0) {
                Image imgMovingDia = getInitMoveDiaActionImage();
                SequenceAction sequence = Actions.sequence();
                this.state = MOVINGDIA;
                players[currentPlayer].initMovingDia(movingDir);
                this.addMoveDiaAction(sequence, movingDir, moveStep);
                sequence.addAction(new RunnableAction() {
                    @Override
                    public void run() {
                        stageAni.clear();
                        if (players[currentPlayer].isMoreMove()) {
                            state = MOVEDIA;
                        } else {
                            endTurn();
                        }
                    }
                });

                imgMovingDia.addAction(sequence);

                stageAni.addActor(imgMovingDia);

            }
        }
    }

    private void changeNextPlayer() {
        this.players[this.currentPlayer].sortingDia();
        do {
            ++this.currentPlayer;
            if (this.currentPlayer < 3)
                continue;
            this.currentPlayer = 0;
        } while (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_OFF
                || this.players[this.currentPlayer].calcPoint() == 10);
        if (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_HUMAN) {
            // this.state = SELECTDIA; //TODO
            this.state = GAMEREADY;
            // this.aniAimMark.init(8, 100, false);
            this.showAimMark = 1;
        } else if (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_CPU) {
            this.state = COMTHINK;
            // this.aniDiaFrame = 0;
        }
        // this.aniJumpAvata.init(4, 200, true);
        this.timeOut = 0;
    }

    /**
     * @brief 结束本回合
     */
    void endTurn() {
        if (this.players[this.currentPlayer].calcPoint() == 10) {
            // 当前玩家已经胜利
            this.players[this.currentPlayer].endTurn();
            if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                if (this.currentPlayer == 0) {
                    this.bWin = true;
                    // this.strmgr = new StringMgr(strLOSE[this.players[1].charID], 14, 1);
                    this.players[0].setCharFace(1);
                    this.players[1].setCharFace(2);
                    if (Resource.gameMode == Resource.GAMEMODE_STORY) {
                        // Resource.pointMgr.addPoint(100);
                        this.stagePoint += 100;
                    }
                } else {
                    this.bWin = false;
                    // this.strmgr = new StringMgr(strWIN[this.players[1].charID], 14, 1);
                    this.players[0].setCharFace(2);
                    this.players[1].setCharFace(1);
                }
                this.showmessageOrder[0] = 1;
                this.showmessageOrder[1] = 0;
                this.showmessageCnt = 0;
                // this.strmgr.start();
                // this.strmgr.setAutoMode();
                Utils.playSound(12, false);
                this.state = 8;
                return;
            }
            if (Resource.gameMode == Resource.GAMEMODE_VS) {
                // if (Resource.playerCnt == 2) {
                // this.winList[0] = this.currentPlayer + 1;
                // this.state = 8;
                // this.aniFrame.init(1, 1000, false);
                // this.winList[1] = this.winList[0] == 1 ? 2 : 1;
                // this.players[this.currentPlayer].rank = 1;
                // if (this.currentPlayer == 0) {
                // this.players[1].rank = 2;
                // } else {
                // this.players[0].rank = 2;
                // }
                // return;
                // }
                // if (Resource.playerCnt == 3) {
                // if (this.winList[0] != -1) {
                // this.winList[1] = this.currentPlayer + 1;
                // this.players[this.currentPlayer].rank = 2;
                // if (0 != this.currentPlayer && 0 != this.winList[0] - 1) {
                // this.players[0].rank = 3;
                // this.winList[2] = 1;
                // } else if (1 != this.currentPlayer && 1 != this.winList[0] - 1) {
                // this.players[1].rank = 3;
                // this.winList[2] = 2;
                // } else if (2 != this.currentPlayer && 2 != this.winList[0] - 1) {
                // this.players[2].rank = 3;
                // this.winList[2] = 3;
                // }
                // } else {
                // this.winList[0] = this.currentPlayer + 1;
                // this.players[this.currentPlayer].rank = 1;
                // Utils.playSound(15, false);
                // }
                // this.state = 8;
                // this.aniFrame.init(1, 1000, false);
                // return;
                // }
            }
        }
        // 本局仍未结束
        // TODO: 连击动画
        // if (this.players[this.currentPlayer].getMoveCnt() > 2 && Resource.gameMode ==
        // Resource.GAMEMODE_STORY) {
        // this.state = 5;
        // this.showmessageCnt = 0;
        // if (this.currentPlayer == 0) {
        // this.showmessageOrder[0] = 0;
        // this.showmessageOrder[1] = 1;
        // this.players[0].setCharFace(1);
        // this.players[1].setCharFace(2);
        // } else {
        // this.showmessageOrder[0] = 1;
        // this.showmessageOrder[1] = 0;
        // this.players[0].setCharFace(2);
        // this.players[1].setCharFace(1);
        // }
        // if (this.bTimeOutTalk) {
        // this.bTimeOutTalk = false;
        // }
        // // this.strmgr = this.showmessageOrder[this.showmessageCnt] == 1
        // // ? new
        // //
        // StringMgr(strMYCOMBO[this.players[this.showmessageOrder[this.showmessageCnt]].charID],
        // // 14, 1)
        // // : new StringMgr(this.getGameTalk(2, this.players[1].charID), 14, 1);
        // // this.strmgr.start();
        // // this.strmgr.setAutoMode();
        // // Utils.playSound(12, false);
        // } else {
        this.players[this.currentPlayer].endTurn();
        this.diaBoard.clearPassed();
        this.changeNextPlayer();
        // }
    }

    /**
     * @brief 获取移动棋子动画的初始图片
     * @return 图片
     */
    private Image getInitMoveDiaActionImage() {
        Image imgMovingDia = new Image(
                players[currentPlayer].getDiaRank(players[currentPlayer].getCurrentSel()) == 0
                        ? imgDia[currentPlayer]
                        : imgDiaKing[currentPlayer]);
        float initX = getDiaPixx(players[currentPlayer].getDiaPosX()) - imgMovingDia.getWidth() / 2;
        float initY = getDiaPixy(players[currentPlayer].getDiaPosY());
        imgMovingDia.setPosition(initX, initY);
        return imgMovingDia;

    }

    /**
     * @brief 添加移动棋子的动作
     * @param sequence  目标动作序列
     * @param movingDir 移动方向
     * @param moveStep  移动步数（1或2）
     */
    private void addMoveDiaAction(SequenceAction sequence, int movingDir, int moveStep) {
        int amountX = Resource.hInc[movingDir] * moveStep
                * Resource.HGAB;
        int amountY = -Resource.vInc[movingDir] * moveStep
                * Resource.VGAB;
        float jumpHeight = moveStep * 5;
        Action jumpUp = Actions.moveBy(0, jumpHeight, Resource.aniJumpDuration, Interpolation.pow2Out);
        Action jumpDown = Actions.moveBy(0, -jumpHeight, Resource.aniJumpDuration, Interpolation.pow2In);
        Action jump = Actions.sequence(jumpUp, jumpDown);
        sequence.addAction(Actions.parallel(Actions.moveBy(amountX, amountY, Resource.aniMoveDuration), jump));
    }

    @Override
    public Stage getStage() {
        // 在棋盘屏幕中，stageMove是响应点击事件的stage
        return stageMove;
    }
}
