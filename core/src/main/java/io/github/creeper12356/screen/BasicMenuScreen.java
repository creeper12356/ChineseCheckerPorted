package io.github.creeper12356.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.creeper12356.MyGame;
import io.github.creeper12356.utils.Resource;

public class BasicMenuScreen implements Screen {
    protected MyGame myGame;
    protected OrthographicCamera camera;
    protected Viewport viewport;

    private Stage stage;
    private Table table;

    private Texture imgBackground;
    private Texture imgTouchButtonOk;
    private Texture imgTouchButtonCancel;

    public BasicMenuScreen(MyGame myGame, boolean okVisible, boolean cancelVisible) {
        this.myGame = myGame;

        camera = new OrthographicCamera();
        viewport = new FitViewport(Resource.totalWidth, Resource.totalHeight, camera);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport);

        table = new Table();
        table.setFillParent(true);

        // 加载图片
        imgBackground = Resource.loadImage("background.png");
        imgTouchButtonCancel = Resource.loadImage(Resource.language + "/touch/tc.png");
        imgTouchButtonOk = Resource.loadImage(Resource.language + "/touch/tok.png");

        // 添加背景图片
        Image img = new Image(imgBackground);
        img.setFillParent(true);
        stage.addActor(img);

        // 底部Ok Cancel 布局
        table.bottom();
        if (okVisible) {
            ImageButton imageButtonOk = getImageButton(imgTouchButtonOk, imgTouchButtonOk, new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleOkClicked();
                }
            });
            table.add(imageButtonOk).left().expandX();
        }
        if (cancelVisible) {
            ImageButton imageButtonCancel = getImageButton(imgTouchButtonCancel, imgTouchButtonCancel,
                    new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            BasicMenuScreen.this.myGame.navigateBack();
                        }
                    });
            table.add(imageButtonCancel).expandX().right();
        }

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        imgTouchButtonOk.dispose();
        imgTouchButtonCancel.dispose();
    }

    protected ImageButton getImageButton(Texture upTexture, Texture downTexture, EventListener listener) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));

        ImageButton button = new ImageButton(style);
        button.addListener(listener);
        return button;
    }

    public Stage getStage() {
        return stage;
    }

    public void handleOkClicked() {

    }

}
