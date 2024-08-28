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
import com.badlogic.gdx.utils.Timer;

import io.github.creeper12356.MyGame;
import io.github.creeper12356.core.DiaBoard;
import io.github.creeper12356.core.Player;
import io.github.creeper12356.core.WJDiaPiece;
import io.github.creeper12356.core.WJPlayer;
import io.github.creeper12356.utils.Resource;

public class WJBoardScreen extends BasicMenuScreen {
    private SpriteBatch batch;

    private Stage stageMove;
    private Stage stageAni;

    private Texture imgBoard;

    public static final int GAMEREADY = 0;
    public static final int MOVEDIA = 2; // 展示移动棋子提示
    public static final int MOVINGDIA = 3; // 移动棋子的动画
    public static final int COMTHINK = 4; // 电脑思考
    public static final int GAMEOVER = 8;
    public static final int VIEWRESULT = 9;

    private DiaBoard diaBoard;
    private WJPlayer[] players;
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

    Texture[] imgDia = new Texture[7];
    Texture[] imgDiaSel = new Texture[7];
    Texture[] imgButton = new Texture[10];
    Texture[] imgMoveNum = new Texture[6];

    // 游戏结束的图片，分别为背景、胜利、失败
    Texture[] imgGameOver = new Texture[3];
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
    static int[] enemyFaceGood = new int[] { 11, 22, 22, 24, 16, 16, 20, 20, 22, 28, 8, 30 };
    static int[] enemyFaceBad = new int[] { 12, 24, 18, 22, 16, 16, 20, 22, 20, 22, 2, 18 };
    static int[] myFace = new int[] { 6, 18, 6, 10 };

    public WJBoardScreen(MyGame myGame) {
        super(myGame, false, false);
        batch = new SpriteBatch();

        imgBoard = Resource.loadImage("board.png");

        this.diaBoard = new DiaBoard();
        this.players = new WJPlayer[2];
        for (int i = 0; i < 2; ++i) {
            this.players[i] = Resource.wjPlayers[i];
        }

        imgButton[0] = Resource.loadImage("touch/t1.png");
        imgButton[1] = Resource.loadImage("touch/t3.png");
        imgButton[2] = Resource.loadImage("touch/t6.png");
        imgButton[3] = Resource.loadImage("touch/t9.png");
        imgButton[4] = Resource.loadImage("touch/t7.png");
        imgButton[5] = Resource.loadImage("touch/t4.png");
        imgButton[6] = Resource.loadImage("touch/tu.png");
        imgButton[7] = Resource.loadImage("touch/td.png");
        imgButton[8] = Resource.loadImage(Resource.language + "/touch/tc.png");
        imgButton[9] = Resource.loadImage(Resource.language + "/touch/tok.png");

        imgMoveNum[0] = Resource.loadImage("movenum_1.png");
        imgMoveNum[1] = Resource.loadImage("movenum_3.png");
        imgMoveNum[2] = Resource.loadImage("movenum_6.png");
        imgMoveNum[3] = Resource.loadImage("movenum_9.png");
        imgMoveNum[4] = Resource.loadImage("movenum_7.png");
        imgMoveNum[5] = Resource.loadImage("movenum_4.png");

        imgGameOver[0] = Resource.loadImage("uichip_2.png");
        imgGameOver[1] = Resource.loadImage("uichip_3.png");
        imgGameOver[2] = Resource.loadImage("uichip_4.png");

        imgDia[WJDiaPiece.TYPE_TANG] = Resource.loadImage("tang.png");
        imgDia[WJDiaPiece.TYPE_MONKEY] = Resource.loadImage("sun.png");
        imgDia[WJDiaPiece.TYPE_PIG] = Resource.loadImage("pig.png");
        imgDia[WJDiaPiece.TYPE_SHA] = Resource.loadImage("sha.png");
        imgDia[WJDiaPiece.TYPE_HORSE] = Resource.loadImage("horse.png");
        imgDia[WJDiaPiece.TYPE_DEMON] = Resource.loadImage("demon.png");
        imgDia[WJDiaPiece.TYPE_DEMON_KING] = Resource.loadImage("demon_king.png");

        imgDiaSel[WJDiaPiece.TYPE_TANG] = Resource.loadImage("tang_sel.png");
        imgDiaSel[WJDiaPiece.TYPE_MONKEY] = Resource.loadImage("sun_sel.png");
        imgDiaSel[WJDiaPiece.TYPE_PIG] = Resource.loadImage("pig_sel.png");
        imgDiaSel[WJDiaPiece.TYPE_SHA] = Resource.loadImage("sha_sel.png");
        imgDiaSel[WJDiaPiece.TYPE_HORSE] = Resource.loadImage("horse_sel.png");
        imgDiaSel[WJDiaPiece.TYPE_DEMON] = Resource.loadImage("demon_sel.png");
        imgDiaSel[WJDiaPiece.TYPE_DEMON_KING] = Resource.loadImage("demon_king_sel.png");


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
                if (state == GAMEREADY) {
                    players[currentPlayer].searchDia(Resource.DIR_UP);
                }
            }
        });

        ImageButton imageButtonDown = getImageButton(imgButton[7], imgButton[7], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state == GAMEREADY) {
                    players[currentPlayer].searchDia(Resource.DIR_DOWN);
                }
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
                    myGame.setScreen(MyGame.SCREEN_MAIN_MENU);
                }
            }
        });

        ImageButton imageButtonOk = getImageButton(imgButton[9], imgButton[9], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("ok clicked");
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
        // }
        this.timeOutCnt = 0;
    }

    @Override
    public void show() {
        super.show();

        this.diaBoard.clearPassed();
        this.diaBoard.clearOnDia();
        this.players[0].init(this.players[0].getType(), 0, this.diaBoard);
        this.players[1].init(this.players[1].getType(), 3, this.diaBoard);

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
        this.state = GAMEREADY;
        this.showmessageCnt = 0;
        this.showmessageOrder[0] = 1;
        this.showmessageOrder[1] = 0;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        System.out.println("this.state == " + state);
        System.out.println("this.currentPlayer[sel] == " + players[currentPlayer].getCurrentSel());
        batch.begin();
        batch.draw(imgBoard, 0, Resource.halfHeight - imgBoard.getHeight() / 2 - 10);

        if (this.state == GAMEREADY) {
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
        } else if (this.state == GAMEOVER) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            this.drawGameOver(batch);
        } else if (this.state == VIEWRESULT) {
            // this.drawPoint(graphics);
            this.drawAvata(batch);
            // this.drawShadow(graphics);
            this.drawDias(batch);
            // this.drawViewResult(graphics);
            // this.aniMoveGuide.frameProcess();
        }
        batch.end();

        if (this.state != GAMEOVER) {
            stageMove.draw();
        }
        stageAni.act(delta);
        stageAni.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();

        stageMove.dispose();
        stageAni.dispose();

        imgBoard.dispose();
        Texture[][] textureArrays = { imgDia, imgDiaSel, imgButton, imgMoveNum,
                imgGameOver };
        for (Texture[] textures : textureArrays) {
            for (Texture texture : textures) {
                if (texture != null) {
                    texture.dispose();
                }
            }
        }
        super.dispose();
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
        for (int i = 0; i < 2; ++i) {
            // 遍历每个玩家
            for (int j = 0; j < 10; ++j) {
                // 遍历每个棋子
                if (j == this.players[i].getCurrentSel() && i == this.currentPlayer)
                    continue;
                this.drawDia(batch,
                 players[i].getDia()[j].getType(),
                 getDiaPixx(this.players[i].getDiaPosX(j)),
                 getDiaPixy(this.players[i].getDiaPosY(j)), 
                 false);
            }

            if (i != this.currentPlayer)
                continue;

            // 绘制当前玩家选中的棋子
            if (this.state == GAMEREADY || this.state == MOVEDIA) {
                this.drawDia(batch,
                        players[i].getDia()[players[i].getCurrentSel()].getType(),
                        getDiaPixx(this.players[i].getDiaPosX()),
                        getDiaPixy(this.players[i].getDiaPosY()),
                        true);
            } else if (this.state == MOVINGDIA) {
                // draw nothing
            } else {
                this.drawDia(batch,
                        players[i].getDia()[players[i].getCurrentSel()].getType(),
                        getDiaPixx(this.players[i].getDiaPosX()),
                        getDiaPixy(this.players[i].getDiaPosY()),
                        false);
            }

        }
    }

    private void drawDia(SpriteBatch batch, int diaType, int posx, int posy, boolean sel) {
        Texture img = sel ? imgDiaSel[diaType] : imgDia[diaType];
        batch.draw(img, posx - img.getWidth() / 2, posy);
    }

    void drawAvata(SpriteBatch batch) {
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

    void drawGameOver(SpriteBatch batch) {
        batch.draw(imgGameOver[0],
                Resource.totalWidth - Resource.imgPlayer[0].getWidth() - imgGameOver[0].getWidth(),
                0);

        if (this.bWin) {
            batch.draw(imgGameOver[1],
                    Resource.totalWidth - Resource.imgPlayer[0].getWidth() - imgGameOver[0].getWidth() / 2
                            - imgGameOver[1].getWidth() / 2,
                    imgGameOver[0].getHeight() / 2 - imgGameOver[1].getHeight() / 2);
        } else {
            batch.draw(imgGameOver[2],
                    Resource.totalWidth - Resource.imgPlayer[0].getWidth() - imgGameOver[0].getWidth() / 2
                            - imgGameOver[2].getWidth() / 2,
                    imgGameOver[0].getHeight() / 2 - imgGameOver[2].getHeight() / 2);
        }
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
            if (this.currentPlayer < 2)
                continue;
            this.currentPlayer = 0;
        } while (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_OFF
                || this.players[this.currentPlayer].calcPoint() == 10);
        if (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_HUMAN) {
            this.state = GAMEREADY;
            this.showAimMark = 1;
        } else if (this.players[this.currentPlayer].getType() == Player.PLAYERTYPE_CPU) {
            this.state = COMTHINK;
        }
        this.timeOut = 0;
    }

    /**
     * @brief 结束本回合
     */
    void endTurn() {
        if (this.players[this.currentPlayer].calcPoint() == 10) {
            // 当前玩家已经胜利
            this.players[this.currentPlayer].endTurn();
            if (this.currentPlayer == 0) {
                this.bWin = true;
                // this.strmgr = new StringMgr(strLOSE[this.players[1].charID], 14, 1);
                this.players[0].setCharFace(1);
                this.players[1].setCharFace(2);
                this.stagePoint += 100;
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
            // Utils.playSound(12, false);
            this.state = GAMEOVER;

            if (bWin && Resource.stageNum < 6) {
                ++Resource.stageNum;
                Resource.saveGame();
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    myGame.setScreen(MyGame.SCREEN_ROUND_MENU);
                }
            }, 5);

            return;
        }
        // 本局仍未结束
        this.players[this.currentPlayer].endTurn();
        this.diaBoard.clearPassed();
        this.changeNextPlayer();
    }

    /**
     * @brief 获取移动棋子动画的初始图片
     * @return 图片
     */
    private Image getInitMoveDiaActionImage() {
        Image imgMovingDia = new Image(
                imgDia[players[currentPlayer].getDia()[players[currentPlayer].getCurrentSel()].getType()]);
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
