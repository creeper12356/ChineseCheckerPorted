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
import com.badlogic.gdx.utils.Scaling;

import io.github.creeper12356.utils.Resource;

public class BasicMenuScreen implements Screen {
    private Stage stage;
    private Table table;

    private Texture imgBackground;
    private Texture imgTouchButtonBackground;
    private Texture imgTouchButtonOk;
    private Texture imgTouchButtonCancel;

    public BasicMenuScreen(boolean okVisible, boolean cancelVisible) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        // 加载图片
        imgBackground = Resource.loadImage("background.png");
        imgTouchButtonBackground = Resource.loadImage("touch/tbg.png");
        imgTouchButtonCancel = Resource.loadImage("touch/tc.png");
        imgTouchButtonOk = Resource.loadImage("touch/tok.png");

        // 添加背景图片
        Image img = new Image(imgBackground);
        img.setFillParent(true);
        stage.addActor(img);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(imgTouchButtonBackground));

        // 底部Ok Cancel 布局
        table.bottom();
        if (okVisible) {
            Stack stackOk = getStackedImageButton(imgTouchButtonOk, style, new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Ok");
                }
            });
            table.add(stackOk).left().expandX();
        }
        if (cancelVisible) {
            Stack stackCancel = getStackedImageButton(imgTouchButtonCancel, style, new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Cancel");
                }
            });
            table.add(stackCancel).expandX().right();
        }

        stage.addActor(table);
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
        stage.dispose();

        imgBackground.dispose();
        imgTouchButtonBackground.dispose();
        imgTouchButtonOk.dispose();
        imgTouchButtonCancel.dispose();
    }

    private Stack getStackedImageButton(Texture texture, ImageButton.ImageButtonStyle style, EventListener listener) {
        Stack stack = new Stack();

        Image img = new Image(texture);
        img.setScaling(Scaling.fit);
        stack.add(img);

        ImageButton button = new ImageButton(style);
        button.addListener(listener);
        stack.add(button);

        return stack;
    }

    protected Stage getStage() {
        return stage;
    }

}
