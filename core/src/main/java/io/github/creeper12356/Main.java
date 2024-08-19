package io.github.creeper12356;

import com.badlogic.gdx.Game;

import io.github.creeper12356.screen.MainMenuScreen;
import io.github.creeper12356.screen.TitleScreen;
import io.github.creeper12356.utils.Resource;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen());
    }

    @Override
    public void dispose() {
        Resource.generator.dispose();
        super.dispose();
    }
}
