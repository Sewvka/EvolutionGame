package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import ru.nsu.ccfit.evolution.server.GameStage;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class FoodTray extends Group {
    public static final int TOKEN_SIZE = 30;
    private static final int PADDING = 10;
    private int foodTotal;
    private int foodCurrent;
    private final EvolutionGame game;
    private final Label label;
    private FoodToken token;

    public FoodTray(EvolutionGame game) {
        super();
        setSize(TOKEN_SIZE + PADDING * 2, TOKEN_SIZE + PADDING * 2);
        label = new Label("", game.getAssets().getSkin());
        label.setPosition(0, TOKEN_SIZE + PADDING * 2);
        label.setColor(Color.GREEN);
        addActor(label);
        this.game = game;
    }

    public void init(int foodTotal) {
        clearChildren();
        this.foodTotal = foodTotal;
        foodCurrent = foodTotal;
        updateText();
        if (foodCurrent > 0) addToken();
    }

    private void addToken() {
        token = new FoodToken(game, TOKEN_SIZE, true, "red");
        token.setTrueParent(this);
        addActor(token);
    }

    public void update(int foodCurrent) {
        this.foodCurrent = foodCurrent;
        if (foodCurrent <= 0 && token != null) token.remove();
        updateText();
    }

    private void updateText() {
        label.setText(foodCurrent + "/" + foodTotal);
        if (foodCurrent <= 0) label.setColor(Color.RED);
        else label.setColor(Color.GREEN);
    }
}
