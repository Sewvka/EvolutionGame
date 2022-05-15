package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.nsu.ccfit.evolution.server.Client;

public class GameScreen implements Screen {
    final EvolutionGame game;
    protected final Client client;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final StageMultiplexer stageMultiplexer;
    public static final float WORLD_SIZE_X = 1280;
    public static final float WORLD_SIZE_Y = 720;

    public GameScreen(EvolutionGame game, Client client) {
        this.game = game;
        this.client = client;
        stageMultiplexer = new StageMultiplexer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_SIZE_X, WORLD_SIZE_Y);
        viewport = new ExtendViewport(WORLD_SIZE_X, WORLD_SIZE_Y, camera);
        viewport.setWorldSize(WORLD_SIZE_X, WORLD_SIZE_Y);
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
