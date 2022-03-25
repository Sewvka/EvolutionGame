package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final EvolutionGame game;
    OrthographicCamera camera;
    private CardManager cManager;

    public GameScreen(final EvolutionGame game) {
        this.game = game;
        cManager = new CardManager(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWorldSizeX(), game.getWorldSizeY());

        cManager.newCard(1, 200, 50);
        cManager.newCard(2, 500, 50);
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
        cManager.drawAll(game.batch);
        game.batch.end();

        Vector3 mousePos = new Vector3();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        cManager.updateMovement(new Vector2(mousePos.x, mousePos.y));

        if (Gdx.input.isTouched()) {

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
