package io.github.creeper12356.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import io.github.creeper12356.utils.Resource;

public class StoryMenuScreen implements Screen {

    private Stage stage;
    private Table table;

    private Texture imgBackground;
    private Texture imgTextNew;
    private Texture imgTextContinue;

    private Texture imgTouchButtonBackground;
    private Texture imgTouchButtonCancel;

    public StoryMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        imgBackground = Resource.loadImage("background.png");
        Image img = new Image(imgBackground);
        img.setFillParent(true);
        stage.addActor(img);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        imgTextNew = Resource.loadImage("menutext_31.png");
        imgTextContinue = Resource.loadImage("menutext_32.png");

        addImageButton(imgTextNew, imgTextNew, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("New");
            }
        });

        addImageButton(imgTextContinue, imgTextContinue, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Continue");
            }
        });

        imgTouchButtonBackground = Resource.loadImage("touch/tbg.png");
        imgTouchButtonCancel = Resource.loadImage("touch/tc.png");

        Stack stack = new Stack();
        Image image = new Image(imgTouchButtonCancel);
        image.setScaling(Scaling.fit);
        stack.add(image);


        // TODO: 抽象出右下角按钮
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(imgTouchButtonBackground));
        ImageButton cancelButton = new ImageButton(style);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Cancel");
            }
        });
        stack.add(cancelButton);

        Table cancelTable = new Table();
        cancelTable.setFillParent(true);
        cancelTable.bottom().right();
        cancelTable.add(stack);

        stage.addActor(cancelTable);
        

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        imgBackground.dispose();
        imgTextNew.dispose();
        imgTextContinue.dispose();

        imgTouchButtonBackground.dispose();
        imgTouchButtonCancel.dispose();
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
