package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class GameScreen extends StageScreen {
    private final HandView playerHand;
    private final TableManager tables;
    private final Stage tableStage;
    private final Stage uiStage;
    private final TextButton passButton;
    private final FoodTray food;

    public GameScreen(final EvolutionGame game) {
        super(game);

        tables = new TableManager(game);

        playerHand = new HandView(game, EvolutionGame.WORLD_SIZE_X / 2, 50, tables);
        food = new FoodTray(game, tables.getUserTable());
        food.setPosition(EvolutionGame.WORLD_SIZE_X/2-food.getWidth()/2, 500);
        stage.addActor(playerHand);
        tableStage = new Stage(viewport);
        tableStage.addActor(tables);
        uiStage = new Stage(viewport);
        passButton = new TextButton("Pass turn", game.getLoader().getSkin());
        initUI();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(tableStage);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        Deck deck = new Deck();
        //для эксперимента генерим в колоду карты (максимум карт - 6)
        for (int i = 0; i < 6; i++) playerHand.addCard(deck.draw());
    }

    @Override
    public void render(float delta) {
        if (game.getServerEmulator().requestAdvanceStage() == 2) {
            initFeedingPhase();
        }

        super.render(delta);
        uiStage.act();
        tableStage.act();
        stage.act();
        tableStage.draw();
        stage.draw();
        uiStage.draw();
    }

    public void creatureClicked(CreatureView targetCreature) {
        playerHand.resumeCoopCardPlay(targetCreature);
    }

    public Stage getUiStage() {
        return uiStage;
    }

    private void initUI() {
        passButton.setPosition(50, 50);
        passButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getServerEmulator().requestPassTurn(game.getPlayerID());
            }
        });
        uiStage.addActor(passButton);
    }

    private void initFeedingPhase() {
        playerHand.addAction(moveTo(playerHand.getX(), playerHand.getY()-150, 0.3f));
        playerHand.setTouchable(Touchable.disabled);
        passButton.setVisible(false);
        food.init();
        stage.addActor(food);
    }
}
