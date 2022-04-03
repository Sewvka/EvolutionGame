package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StageScreen implements Screen {
    final EvolutionGame game;
    protected final Viewport viewport;
    protected final Stage stage;

    public StageScreen(EvolutionGame game) {
        this.game = game;
        viewport = new ExtendViewport(EvolutionGame.WORLD_SIZE_X, EvolutionGame.WORLD_SIZE_Y, game.getCamera());
        stage = new Stage(viewport);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(220, 220, 220, 0);
        game.getCamera().update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
    }
}
