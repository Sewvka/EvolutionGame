package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen {
    final EvolutionGame game;
    private final Viewport viewport;

    public LoadingScreen(final EvolutionGame game) {
        this.game = game;
        viewport = new ExtendViewport(EvolutionGame.WORLD_SIZE_X, EvolutionGame.WORLD_SIZE_Y, game.getDrawer().getCamera());
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
        viewport.update(width, height);
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
