package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import java.security.InvalidParameterException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class FoodTray extends Group {
    public static final int TOKEN_SIZE = 30;
    private static final int PADDING = 10;
    private final Array<FoodToken> food;
    private final EvolutionGame game;
    private final TableView userTable;

    public FoodTray(EvolutionGame game, TableView userTable) {
        super();
        this.userTable = userTable;
        setSize((TOKEN_SIZE+PADDING)*5+PADDING, (TOKEN_SIZE+PADDING)*3+PADDING);
        food = new Array<>();
        this.game = game;
    }

    public TableView getUserTable() {
        return userTable;
    }

    public void feed(FoodToken f) {
        if (!food.contains(f, true)) throw new InvalidParameterException("Cannot remove food that isn't contained in tray!");
        if (game.getServerEmulator().requestFeed(userTable.getSelectedCreatureIndex(), game.getPlayerID())) {
            food.removeIndex(food.indexOf(f, true));
            f.setTouchable(Touchable.disabled);
            userTable.getSelectedCreature().addFood(f);
            removeActor(f);
        }
        else f.placeInTray();
    }

    public void addFood() {
        FoodToken f = new FoodToken(game, TOKEN_SIZE);
        addActor(f);
        food.add(f);
    }

    public void init() {
        int foodTotal = game.getServerEmulator().getFoodTotal();
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
            f.addAction(moveTo(PADDING+col*(TOKEN_SIZE+PADDING), PADDING+(3-row)*(TOKEN_SIZE+PADDING)));
        }
    }
}
