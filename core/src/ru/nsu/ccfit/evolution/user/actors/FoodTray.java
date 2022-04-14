package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.user.actors.FoodToken;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.security.InvalidParameterException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class FoodTray extends Group {
    public static final int TOKEN_SIZE = 30;
    private static final int PADDING = 10;
    private final Array<FoodToken> food;
    private final EvolutionGame game;

    public FoodTray(EvolutionGame game) {
        super();
        setSize((TOKEN_SIZE + PADDING) * 5 + PADDING, (TOKEN_SIZE + PADDING) * 3 + PADDING);
        food = new Array<>();
        this.game = game;
    }

    public void addFood() {
        FoodToken f = new FoodToken(game, TOKEN_SIZE);
        addActor(f);
        food.add(f);
    }

    public void removeToken(FoodToken f) {
        if (!food.contains(f, true))
            throw new InvalidParameterException("Cannot remove food that isn't contained in tray!");
        food.removeIndex(food.indexOf(f, true));
        f.setTouchable(Touchable.disabled);
        removeActor(f);
    }

    public void init(int foodTotal) {
        for (int i = 0; i < foodTotal; i++) addFood();
        updatePositions();
    }

    public void updatePositions() {
        int col = 0;
        int row = 0;
        for (FoodToken f : food) {
            if (col == 5) {
                col = 0;
                row++;
            }
            col++;
            f.addAction(moveTo(PADDING + col * (TOKEN_SIZE + PADDING), PADDING + (3 - row) * (TOKEN_SIZE + PADDING)));
        }
    }
}
