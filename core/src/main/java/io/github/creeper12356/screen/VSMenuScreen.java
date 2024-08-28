package io.github.creeper12356.screen;

import io.github.creeper12356.MyGame;
import io.github.creeper12356.core.Player;
import io.github.creeper12356.utils.Resource;

public class VSMenuScreen extends BasicMenuScreen {

    public VSMenuScreen(MyGame myGame) {
        super(myGame, true, true);
    }

    @Override
    public void show() {
        super.show();

        if (Resource.imgDiaAvt[0] == null) {
            // 首次加载棋子头像
            for (int i = 0; i < 25; ++i) {
                Resource.imgDiaAvt[i] = Resource.loadImage("dia_avt_" + i + ".png");
            }
        }
        // 初始化游戏数据
        Resource.gameMode = Resource.GAMEMODE_VS;
        Resource.playerCnt = 2;
        Resource.wjPlayers[0].setType(Player.PLAYERTYPE_HUMAN);
        Resource.wjPlayers[1].setType(Player.PLAYERTYPE_HUMAN);
        Resource.wjPlayers[0].setCharID(0);
        Resource.wjPlayers[1].setCharID(1);
        Resource.wjPlayers[0].setDiaType(0);
        Resource.wjPlayers[1].setDiaType(1);
        Resource.wjPlayers[0].setAiLevel(1);
        Resource.wjPlayers[1].setAiLevel(1);

        // this.imgSelDia[0] = Resource.imgDiaAvt[Resource.players[0].getDiaType()];
        // this.imgSelDia[1] = Resource.imgDiaAvt[Resource.players[1].getDiaType()];

        Resource.imgPlayer[0] = Resource.replaceTexture(Resource.imgPlayer[0], "char_0_n.png");
        Resource.imgPlayer[1] = Resource.replaceTexture(Resource.imgPlayer[1], "char_0_good.png");
        Resource.imgPlayer[2] = Resource.replaceTexture(Resource.imgPlayer[2], "char_0_bad.png");

        Resource.imgEnemy[0] = Resource.replaceTexture(Resource.imgEnemy[0], "char_" + Resource.stageNum + "_n.png");
        Resource.imgEnemy[1] = Resource.replaceTexture(Resource.imgEnemy[1], "char_" + Resource.stageNum + "_good.png");
        Resource.imgEnemy[2] = Resource.replaceTexture(Resource.imgEnemy[2], "char_" + Resource.stageNum + "_bad.png");

    }

    @Override
    public void handleOkClicked() {
        myGame.setScreen(MyGame.SCREEN_WJ_BOARD);
    }

}
