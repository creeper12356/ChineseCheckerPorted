package io.github.creeper12356;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import io.github.creeper12356.screen.BasicMenuScreen;
import io.github.creeper12356.screen.MainMenuScreen;
import io.github.creeper12356.screen.RoundMenuScreen;
import io.github.creeper12356.screen.StoryMenuScreen;
import io.github.creeper12356.utils.Resource;

public class MyGame extends Game {
    private BasicMenuScreen[] screens = new BasicMenuScreen[2];
    private int lastScreen = -1;
    private int currentScreen = -1;
    /**
     * @brief 切换屏幕
     * @details 在外部调用此方法切换屏幕
     * @param index 屏幕索引
     */
    public void setScreen(int index) {
        lastScreen = currentScreen;
        currentScreen = index;
        setScreen(screens[index]);
        Gdx.input.setInputProcessor(screens[index].getStage());
    }

    /**
     * @brief 返回上一个屏幕
     */
    public void navigateBack() {
        if (lastScreen != -1) {
            setScreen(lastScreen);
        }
    }

    @Override
    public void create() {
        screens[0] = new MainMenuScreen(this);
        screens[1] = new StoryMenuScreen(this);
        // setScreen(0);
        setScreen(new RoundMenuScreen(this));
    }

    @Override
    public void dispose() {
        Resource.dispose();

        screens[0].dispose();
        screens[1].dispose();
        System.out.println("Game disposed");
        super.dispose();
    }
}
