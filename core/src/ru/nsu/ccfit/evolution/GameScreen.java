package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends StageScreen {
    private final HandView hand;
    private final TableView table;
    private final Stage tableStage;
    private final Stage uiStage;

    public GameScreen(final EvolutionGame game) {
        super(game);
        table = new TableView(game, EvolutionGame.WORLD_SIZE_X / 2 - 350, 300, 700, 160);
        hand = new HandView(game, EvolutionGame.WORLD_SIZE_X / 2, 50, table);
        stage.addActor(hand);
        tableStage = new Stage(viewport);
        tableStage.addActor(table);
        uiStage = new Stage(viewport);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(tableStage);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        Deck deck = new Deck();
        //для эксперимента генерим в колоду карты (максимум карт - 6)
        for (int i = 0; i < 6; i++) hand.addCard(deck.draw());
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        uiStage.act();
        tableStage.act();
        stage.act();
        tableStage.draw();
        stage.draw();
        uiStage.draw();
    }

    public void creatureClicked(CreatureView targetCreature) {
        hand.resumeCoopCardPlay(targetCreature);
    }

    public Stage getUiStage() {
        return uiStage;
    }
}
