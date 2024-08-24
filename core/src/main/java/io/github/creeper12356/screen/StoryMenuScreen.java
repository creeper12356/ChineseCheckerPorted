package io.github.creeper12356.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.creeper12356.MyGame;
import io.github.creeper12356.utils.Resource;

public class StoryMenuScreen extends BasicMenuScreen {

    private Table table;

    private Texture imgTextNew;
    private Texture imgTextContinue;

    public StoryMenuScreen(MyGame myGame) {
        super(myGame, false, true);

        Stage stage = getStage();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        imgTextNew = Resource.loadImage("menutext_31.png");
        imgTextContinue = Resource.loadImage("menutext_32.png");

        addImageButton(imgTextNew, imgTextNew, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Resource.newGame();
                Resource.stageNum = 1;
                Resource.saveGame();
                StoryMenuScreen.this.myGame.setScreen(MyGame.SCREEN_ROUND_MENU);
            }
        });

        addImageButton(imgTextContinue, imgTextContinue, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Resource.loadGame();
                StoryMenuScreen.this.myGame.setScreen(MyGame.SCREEN_ROUND_MENU);
            }
        });

    }

    @Override
    public void dispose() {
        imgTextNew.dispose();
        imgTextContinue.dispose();
        super.dispose();
    }

    private void addImageButton(Texture upTexture, Texture downTexture, EventListener listener) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));

        ImageButton button = new ImageButton(style);
        button.addListener(listener);
        table.add(button).pad(7).row();
    }

}
