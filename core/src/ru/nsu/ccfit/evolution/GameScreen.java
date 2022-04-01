package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.math.Vector2;

public class GameScreen extends StageScreen {
    private final HandView handView;
    private final TableView table;

    public GameScreen(final EvolutionGame game) {
        super(game);
        table = new TableView(game, EvolutionGame.WORLD_SIZE_X /2 - 350, 300, 700, 160);
        handView = new HandView(game, EvolutionGame.WORLD_SIZE_X/2, 50, table);
        stage.addActor(table);
        stage.addActor(handView);

        //для эксперимента генерим в колоду карты (максимум карт - 6)
        handView.addCard(1);
        handView.addCard(2);
        handView.addCard(1);
        handView.addCard(1);
        handView.addCard(1);
        handView.addCard(2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Vector2 mousePos = game.getMouseCoords();

        stage.act();
        stage.draw();
    }
}
