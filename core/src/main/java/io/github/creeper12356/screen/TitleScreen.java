package io.github.creeper12356.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

    int state = LOGO;
    int process = 1;
    long time;
    // Animation aniJumpAvata;

    public TitleScreen() {
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

        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }
    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        // batch.begin();
        // batch.draw(imgBackground, 0, 0);
        // batch.draw(imgTitleChip[1], Resource.halfWidth - imgTitleChip[1].getWidth() /
        // 2, 36);
        // batch.end();

        if (this.state == LOGO) {
            if (this.process == 1) {
                this.time = 0;
                ++this.process;
            } else if (this.process == 2) {
                if (this.time > 1500L) {
                    ++this.process;
                }
            }
            time += delta * 1000;
            batch.begin();
            Gdx.gl.glClearColor(1, 1, 1, 1); // 设置清屏颜色为白色
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // 清屏
            if (this.process <= 2) {
                batch.draw(imgMobilebus, 0, Gdx.graphics.getHeight() / 2 - imgMobilebus.getHeight() / 2);
            } else if (this.process == 3) {
                // graphics.setFont(Resource.sf);
                Resource.drawStringAtCenter("是否开启声音", font, batch);
                Resource.drawStringAtBottomLeft("是", font, batch);
                Resource.drawStringAtBottomRight("否", font, batch);
            }
            batch.end();
        } else if (this.state == TITLE) {
            if (this.process == 0) {
                // Utils.playSound(9, false);
                ++this.process;
            }
            batch.begin();
            batch.draw(imgBackground, 0, 0);
            Resource.drawImageAtBottom(imgTitleChip[0], batch);
            Resource.drawImageAtCenter(imgTitleChip[1], batch);
            batch.end();

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
