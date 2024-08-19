package io.github.creeper12356.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import io.github.creeper12356.utils.Resource;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Table table;
    private BitmapFont font;

    private Texture imgBackground;
    private Texture imgBackgroundMenu;

    private Texture[] imgMenuTextUp = new Texture[7];
    private Texture[] imgMenuTextDown = new Texture[7];

    public MainMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        imgBackground = Resource.loadImage("background.png");
        Image img = new Image(imgBackground);
        img.setFillParent(true);
        stage.addActor(img);

        imgBackgroundMenu = Resource.loadImage("bgmenu.png");
        Image imgMenu = new Image(imgBackgroundMenu);
        imgMenu.setPosition(0, Gdx.graphics.getHeight(), Align.topLeft);
        stage.addActor(imgMenu);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        font = Resource.generator.generateFont(Resource.parameter);

        for (int i = 0; i < 7; ++i) {
            imgMenuTextUp[i] = Resource.loadImage("menutext_1" + i + ".png");
            imgMenuTextDown[i] = Resource.loadImage("menutext_2" + i + ".png");
        }

        addImageButton(imgMenuTextUp[0], imgMenuTextDown[0], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Play");
            }
        });

        addImageButton(imgMenuTextUp[1], imgMenuTextDown[1], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Rank");
            }
        });

        addImageButton(imgMenuTextUp[2], imgMenuTextDown[2], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Story");
            }
        });

        addImageButton(imgMenuTextUp[3], imgMenuTextDown[3], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option");
            }
        });

        addImageButton(imgMenuTextUp[4], imgMenuTextDown[4], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Help");
            }
        });

        addImageButton(imgMenuTextUp[5], imgMenuTextDown[5], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("About");
            }
        });

        addImageButton(imgMenuTextUp[6], imgMenuTextDown[6], new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit");
            }
        });

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
        font.dispose();

        imgBackground.dispose();
        imgBackgroundMenu.dispose();

        for (int i = 0; i < 7; ++i) {
            imgMenuTextUp[i].dispose();
            imgMenuTextDown[i].dispose();
        }
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
