package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen implements Screen {
    final EvolutionGame game;
    private final OrthographicCamera camera;


    public LoadingScreen(final EvolutionGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWorldSizeY(), game.getWorldSizeX());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(220, 220, 220, 0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        if (game.assets.update()) {
            game.setScreen(new GameScreen(game));
        }
        else {
            game.font.draw(game.batch, "Loading..." + (game.assets.getProgress() * 100) + "%", 100, 150);
        }
        game.batch.end();
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
