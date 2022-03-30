package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    final EvolutionGame game;
    private final HandView handView;
    private final TableView table;

    public GameScreen(final EvolutionGame game) {
        this.game = game;
        table = new TableView(game, EvolutionGame.WORLD_SIZE_X /2 - 350, 300, 700, 160);
        handView = new HandView(game, table);

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
        game.getDrawer().begin();
        game.getDrawer().drawGame();
        game.getDrawer().end();

        Vector2 mousePos = game.getController().getMouseCoords();

        handView.update(mousePos);
        table.update(mousePos);
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
