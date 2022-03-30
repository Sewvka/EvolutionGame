package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen implements Screen {
    final EvolutionGame game;

    public LoadingScreen(final EvolutionGame game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (game.getLoader().isLoaded()) {
            game.setScreen(new GameScreen(game));
        }
        else {
            game.getDrawer().begin();
            game.getDrawer().drawLoading();
            game.getDrawer().end();
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

    }
}
