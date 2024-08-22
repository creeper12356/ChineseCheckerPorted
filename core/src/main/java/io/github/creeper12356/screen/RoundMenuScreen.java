package io.github.creeper12356.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.creeper12356.MyGame;
import io.github.creeper12356.core.Player;
import io.github.creeper12356.utils.RectangleActor;
import io.github.creeper12356.utils.Resource;

public class RoundMenuScreen extends BasicMenuScreen {
    private RectangleActor rectangleActor;
    private SpriteBatch batch;
    private Table tableOption;

    private Texture[] imgSelDia = new Texture[2]; // 引用Resource的图片，无需dispose
    private Texture imgRound; // "关卡"
    private Texture imgStage; // "第几关"

    private Texture imgButtonOk;
    private Texture imgButtonLeft;
    private Texture imgButtonRight;

    public RoundMenuScreen(MyGame myGame) {
        super(myGame, false, false);

        Stage stage = getStage();

        batch = new SpriteBatch();

        rectangleActor = new RectangleActor();
        stage.addActor(rectangleActor);

        this.imgRound = Resource.loadImage("roundchip_3.png");

        this.imgButtonOk = Resource.loadImage("touch/tok.png");
        this.imgButtonLeft = Resource.loadImage("touch/tl.png");
        this.imgButtonRight = Resource.loadImage("touch/tr.png");

        tableOption = new Table();
        tableOption.setFillParent(true);
        stage.addActor(tableOption);

        ImageButton imageButtonOk = getImageButton(imgButtonOk, imgButtonOk, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                myGame.setScreen(3);
            }
        });
        ImageButton imageButtonLeft = getImageButton(imgButtonLeft, imgButtonLeft, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Resource.players[0].setDiaType((Resource.players[0].getDiaType() + 24) % 25);
                RoundMenuScreen.this.imgSelDia[0] = Resource.imgDiaAvt[Resource.players[0].getDiaType()];
            }
        });
        ImageButton imageButtonRight = getImageButton(imgButtonRight, imgButtonRight, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: 玩家可选的棋子有限制
                Resource.players[0].setDiaType((Resource.players[0].getDiaType() + 1) % 25);
                RoundMenuScreen.this.imgSelDia[0] = Resource.imgDiaAvt[Resource.players[0].getDiaType()];
            }
        });

        tableOption.bottom().right();
        tableOption.add(imageButtonLeft).padRight(5);
        tableOption.add(imageButtonRight).padRight(5);
        tableOption.add(imageButtonOk).padRight(5);

    }

    @Override
    public void show() {
        super.show();
        // TODO: 在别处初始化
        Resource.stageNum = 1;

        if (Resource.imgDiaAvt[0] == null) {
            // 首次加载棋子头像
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

        Resource.imgPlayer[0] = Resource.replaceTexture(Resource.imgPlayer[0], "char_0_n.png");
        Resource.imgPlayer[1] = Resource.replaceTexture(Resource.imgPlayer[1], "char_0_good.png");
        Resource.imgPlayer[2] = Resource.replaceTexture(Resource.imgPlayer[2], "char_0_bad.png");

        Resource.imgEnemy[0] = Resource.replaceTexture(Resource.imgEnemy[0], "char_" + Resource.stageNum + "_n.png");
        Resource.imgEnemy[1] = Resource.replaceTexture(Resource.imgEnemy[1], "char_" + Resource.stageNum + "_good.png");
        Resource.imgEnemy[2] = Resource.replaceTexture(Resource.imgEnemy[2], "char_" + Resource.stageNum + "_bad.png");

        if (Resource.stageNum < 6) {
            imgStage = Resource.replaceTexture(imgStage,
                    "roundnum_" + Resource.stageNum + ".png");
        } else if (Resource.stageNum == 6) {
            imgStage = Resource.replaceTexture(imgStage, "roundchip_0.png");
        }

        // this.aniArrow.init(2, 2, 300, false);
        // this.aniStageMap.init(2, 100, true);
        // this.process = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // int n9 = 108;
        // int n10 = 0;
        // if (this.aniArrow.getType() == 0 && this.aniArrow.getFrame() == 0) {
        // n10 = -2;
        // } else if (this.aniArrow.getType() == 1 && this.aniArrow.getFrame() == 0) {
        // n10 = 2;
        // }
        batch.begin();

        batch.draw(imgSelDia[1],
                Resource.imgPlayer[1].getWidth() + 20,
                106);
        batch.draw(Resource.imgEnemy[0],
                0,
                106);
        batch.draw(imgSelDia[0],
                240 - Resource.imgPlayer[0].getWidth() - imgSelDia[0].getWidth(),
                106);
        batch.draw(Resource.imgPlayer[0],
                240 - Resource.imgPlayer[0].getWidth(),
                106);

        // TODO: 此处绘制关卡位置和原游戏有出入
        batch.draw(imgRound, 240 / 2 - imgRound.getWidth() / 2, 190);
        batch.draw(imgStage, 240 / 2 - imgStage.getWidth() / 2, 190 - imgStage.getHeight() - 1);
        batch.end();

        // n10 = Resource.halfWidth - (this.imgRoundChip[0].getWidth() +
        // this.imgRoundChip[1].getWidth() + 3) / 2;
        // if (Resource.stageNum < 6) {
        // graphics.drawImage(this.imgRoundChip[1], n10,
        // Resource.halfHeight - 30 - this.imgRoundChip[1].getHeight(), 4 | 0x10);
        // graphics.drawImage(this.imgRoundChip[0], n10 +
        // this.imgRoundChip[1].getWidth() + 3,
        // Resource.halfHeight - 30 - this.imgRoundChip[0].getHeight(), 4 | 0x10);
        // } else if (Resource.stageNum == 6) {
        // graphics.drawImage(this.imgRoundChip[0], n10,
        // Resource.halfHeight - 30 - this.imgRoundChip[0].getHeight(), 4 | 0x10);
        // graphics.drawImage(this.imgRoundChip[1], n10 +
        // this.imgRoundChip[0].getWidth() + 3,
        // Resource.halfHeight - 30 - this.imgRoundChip[1].getHeight(), 4 | 0x10);
        // }
        // if (this.process == 0) {
        // if (this.imgButton[0] == null) {
        // this.imgButton[0] = Resource.loadImage("/button_move.png");
        // }
        // if (this.imgButton[1] == null) {
        // this.imgButton[1] = Resource.loadImage("/button_ok.png");
        // }
        // graphics.setColor(255, 255, 255);
        // graphics.setFont(Resource.sf);
        // // 选择您比赛的马
        // graphics.drawString("\u9009\u62e9\u60a8\u6bd4\u8d5b\u7684\u9a6c", 2,
        // Resource.totalHeight - this.imgButton[0].getHeight() - 15, 4 | 0x10);
        // graphics.drawImage(this.imgButton[0], 2, Resource.totalHeight -
        // this.imgButton[0].getHeight(),
        // 4 | 0x10);
        // graphics.drawImage(this.imgButton[1], this.imgButton[0].getWidth() + 10,
        // Resource.totalHeight - this.imgButton[1].getHeight(), 4 | 0x10);
        // n11 = 50;
        // n = 94;
        // n12 = 20;
        // for (int i = 0; i < 6; ++i) {
        // if (i < Resource.stageNum - 1) {
        // graphics.drawImage(this.imgStageMap[0], Resource.halfWidth - n11 + i * n12,
        // Resource.halfHeight + n, 1 | 0x20);
        // continue;
        // }
        // if (i == Resource.stageNum - 1) {
        // if (this.aniStageMap.getFrame() != 0)
        // continue;
        // graphics.drawImage(this.imgStageMap[1], Resource.halfWidth - n11 + i * n12,
        // Resource.halfHeight + n, 1 | 0x20);
        // continue;
        // }
        // graphics.drawImage(this.imgStageMap[1], Resource.halfWidth - n11 + i * n12,
        // Resource.halfHeight + n, 1 | 0x20);
        // }
        // this.aniStageMap.frameProcess();
        // graphics.drawImage(this.imgStageMap[2], Resource.halfWidth - n11 + 5 * n12,
        // Resource.halfHeight + n - this.imgStageMap[1].getHeight() - 1, 1 | 0x20);
        // this.aniArrow.frameProcess();
        // graphics.setClip(0, 0, Resource.totalWidth, Resource.totalHeight);
        // } else if (this.process == 1) {
        // if (this.imgRoundChip[2] == null) {
        // this.imgRoundChip[2] = Resource.loadImage("/roundchip_1.png");
        // }
        // if (this.aniFrame.getFrame() < 5) {
        // if (this.aniFrame.getFrame() % 2 == 0) {
        // graphics.drawImage(this.imgRoundChip[2],
        // Resource.halfWidth - this.imgRoundChip[2].getWidth() / 2, Resource.halfHeight
        // - this.imgRoundChip[2].getHeight() + this.imgRoundChip[2].getHeight() / 3,
        // 4 | 0x10);
        // }
        // } else {
        // graphics.setColor(0, 0, 0);
        // graphics.drawImage(this.imgRoundChip[2],
        // Resource.halfWidth - this.imgRoundChip[2].getWidth() / 2, Resource.halfHeight
        // - this.imgRoundChip[2].getHeight() + this.imgRoundChip[2].getHeight() / 3,
        // 4 | 0x10);
        // graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        // }
        // if (this.aniFrame.frameProcess() == 0) {
        // Resource.aMainView.setStatus(1, 0);
        // this.clearAVT();
        // }
        // }
        // graphics.setClip(0, 0, Resource.totalWidth, Resource.totalHeight);
        // TouchDo.setTouchArea(2, 4, this.process);
        // break;
    }

    @Override
    public void dispose() {
        rectangleActor.dispose();

        imgRound.dispose();
        if (imgStage != null) {
            imgStage.dispose();
        }
        imgButtonOk.dispose();
        imgButtonLeft.dispose();
        imgButtonRight.dispose();

        batch.dispose();
        super.dispose();
    }

}
