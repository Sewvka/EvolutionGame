package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends StageScreen {
    private final HandView handView;
    private final TableView table;
    private final Stage tableStage;

    public GameScreen(final EvolutionGame game) {
        super(game);
        table = new TableView(game, EvolutionGame.WORLD_SIZE_X / 2 - 350, 300, 700, 160);
        handView = new HandView(game, EvolutionGame.WORLD_SIZE_X / 2, 50, table);
        stage.addActor(handView);
        tableStage = new Stage(viewport);
        tableStage.addActor(table);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(tableStage);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        Deck deck = new Deck();
        //для эксперимента генерим в колоду карты (максимум карт - 6)
        for (int i = 0; i < 6; i++) handView.addCard(deck.draw());
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        tableStage.act();
        stage.act();
        tableStage.draw();
        stage.draw();
    }
}
