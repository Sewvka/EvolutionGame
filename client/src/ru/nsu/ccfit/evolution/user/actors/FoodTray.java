package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.nsu.ccfit.evolution.EvolutionGame;


public class FoodTray extends Group {
    public static final int TOKEN_SIZE = 30;
    private static final int PADDING = 10;
    private int foodTotal;
    private int foodCurrent;
    private final EvolutionGame game;
    private final Label label;

    public FoodTray(EvolutionGame game) {
        super();
        setSize(TOKEN_SIZE + PADDING * 2, TOKEN_SIZE + PADDING * 2);
        label = new Label("", game.getAssets().getSkin());
        label.setPosition(0, TOKEN_SIZE + PADDING * 2);
        label.setColor(Color.GREEN);
        addActor(label);
        this.game = game;
        FoodToken f = new FoodToken(game, TOKEN_SIZE, true);
        f.setTrueParent(this);
        addActor(f);
    }

    public void init(int foodTotal) {
        this.foodTotal = foodTotal;
        foodCurrent = foodTotal;
        updateText();
    }

    public void removeFood() {
        if (foodCurrent <= 0) return;
        foodCurrent--;
        if (foodCurrent > 0) {
            addActor(new FoodToken(game, TOKEN_SIZE, true));
        }
        updateText();
        if (foodCurrent <= 0) label.setColor(Color.RED);
    }

    private void updateText() {
        label.setText(foodCurrent + "/" + foodTotal);
    }
}
