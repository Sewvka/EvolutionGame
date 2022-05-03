package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.nsu.ccfit.evolution.EvolutionGame;
import ru.nsu.ccfit.evolution.constants.GameConstants;

public class GameScreen implements Screen {
    final EvolutionGame game;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final StageMultiplexer stageMultiplexer;

    public GameScreen(EvolutionGame game) {
        this.game = game;
        stageMultiplexer = new StageMultiplexer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConstants.SIZE_X, GameConstants.SIZE_Y);
        viewport = new ExtendViewport(GameConstants.SIZE_X, GameConstants.SIZE_Y, camera);
        viewport.setWorldSize(GameConstants.SIZE_X, GameConstants.SIZE_Y);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(220, 220, 220, 0);
        camera.update();
        stageMultiplexer.update();
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
        stageMultiplexer.dispose();
    }

    public void addStage(Stage stage) {
        stageMultiplexer.addStage(stage);
    }

    public Viewport getViewport() {
        return viewport;
    }
}
