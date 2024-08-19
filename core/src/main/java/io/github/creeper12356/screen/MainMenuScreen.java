package io.github.creeper12356.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import io.github.creeper12356.utils.Resource;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Table table;
    private BitmapFont font;

    private Texture imgBackground;
    private Texture imgBackgroundMenu;

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

        addLabel("故事模式", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
            }
        });

        addLabel("对战模式", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
            }
        });

        addLabel("游戏设置", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
            }
        });

        addLabel("游戏帮助", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
            }
        });

        addLabel("特别菜单", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
            }
        });

        addLabel("排行榜", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
            }
        });

        addLabel("退出游戏", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the corresponding screen
                System.out.println(event.toString());
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
    }

    private void addLabel(String title, EventListener listener) {
        Label label = new Label(title, new Label.LabelStyle(font, Color.WHITE));
        label.addListener(listener);
        table.add(label).pad(7).row();
    }

}
