package io.github.creeper12356;

import com.badlogic.gdx.Game;

import io.github.creeper12356.screen.TitleScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new TitleScreen());
    }
}
