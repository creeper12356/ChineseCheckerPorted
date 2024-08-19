package io.github.creeper12356.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.creeper12356.utils.Resource;

public class TitleScreen implements Screen {
    public static final int LOGO = 1;
    public static final int TITLE = 2;

    private SpriteBatch batch;
    private Texture[] imgTitleChip = new Texture[2];
    private Texture imgMobilebus;
    private Texture imgCharBig;
    private Texture imgBackground;

    private BitmapFont font;

    int state = 1;
    int process = 1;
    long time;
    // Animation aniJumpAvata;

    @Override
    public void show() {
        batch = new SpriteBatch();

        imgMobilebus = Resource.loadImage("mobilebus.png");
        imgTitleChip[0] = Resource.loadImage("titlechip_0.png");
        imgTitleChip[1] = Resource.loadImage("titlechip_1.png");
        imgCharBig = Resource.loadImage("charbig.png");
        imgBackground = Resource.loadImage("background.png");
        // Resource.getBackBuffer().drawImage(backgroundImage, 0, 0, 0);
        // Resource.getBackBuffer().drawImage(this.imgTitleChip[1],
        // Resource.halfWidth - this.imgTitleChip[1].getWidth() / 2, 36, 20);
        // this.aniJumpAvata = new Animation();
        // this.aniJumpAvata.init(4, 200, true);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(imgBackground, 0, 0);
        batch.draw(imgTitleChip[1], Resource.halfWidth - imgTitleChip[1].getWidth() / 2, 36);
        batch.end();

        if (this.state == LOGO) {
            if (this.process == 1) {
                this.time = System.currentTimeMillis();
                ++this.process;
            } else if (this.process == 2) {
                if (System.currentTimeMillis() - this.time > 1500L) {
                    ++this.process;
                }
            }
            batch.begin();
            batch.setColor(Color.WHITE);
            if (this.process <= 2) {
                // TODO: anchor: CENTER, LEFTBOTTOM, RIGHTBOTTOM
                batch.draw(imgMobilebus, Resource.halfWidth, Resource.halfHeight);
            } else if (this.process == 3) {
                // graphics.setFont(Resource.sf);
                font.setColor(Color.BLACK);
                font.draw(batch, "是否开启声音？", Resource.halfWidth, Resource.halfHeight);
                font.draw(batch, "是", 3, Resource.totalHeight - 3);
                font.draw(batch, "否", Resource.totalWidth - 3, Resource.totalHeight - 3);

                // graphics.drawString("是否开启声音？", Resource.halfWidth,
                // Resource.halfHeight, 1 | 0x10);
                // graphics.drawString("是", 3, Resource.totalHeight - 3, 4 | 0x20);
                // graphics.drawString("否", Resource.totalWidth - 3, Resource.totalHeight - 3, 8
                // | 0x20);
            }
        } else if (this.state == TITLE) {
            // int[] nArray = new int[] { 0, 1, 2, 1 };
            if (this.process == 0) {
                // Utils.playSound(9, false);
                ++this.process;
            }
            // graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
            // if (this.aniJumpAvata.getFrame() != 0) {
            // graphics.drawImage(this.imgTitleChip[0], Resource.halfWidth -
            // this.imgTitleChip[0].getWidth() / 2,
            // Resource.totalHeight - 36, 0);
            // }
            // this.aniJumpAvata.frameProcess();
        }
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
        batch.dispose();
        imgMobilebus.dispose();
        imgTitleChip[0].dispose();
        imgTitleChip[1].dispose();
        imgCharBig.dispose();
        imgBackground.dispose();
        font.dispose();
    }

}
