package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    final EvolutionGame game;
    private final HandView handView;
    private final TableView table;
    private final Viewport viewport;

    public GameScreen(final EvolutionGame game) {
        viewport = new ExtendViewport(EvolutionGame.WORLD_SIZE_X, EvolutionGame.WORLD_SIZE_Y, game.getDrawer().getCamera());
        this.game = game;
        table = new TableView(game, EvolutionGame.WORLD_SIZE_X /2 - 350, 300, 700, 160);
        handView = new HandView(game, table);
        game.getDrawer().addDrawable(table);
        game.getDrawer().addDrawable(handView);

        //для эксперимента генерим в колоду карты (максимум карт - 6)
        handView.addCard(1);
        handView.addCard(2);
        handView.addCard(1);
        handView.addCard(1);
        handView.addCard(1);
        handView.addCard(2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Vector2 mousePos = game.getController().getMouseCoords();

        handView.updateLogic(mousePos);
        table.updateLogic(mousePos);

        game.getDrawer().begin();
        game.getDrawer().drawGame();
        game.getDrawer().end();
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
