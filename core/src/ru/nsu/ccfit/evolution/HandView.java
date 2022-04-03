package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.security.InvalidParameterException;

public class HandView extends Group {
    final float CARD_W = 150;
    final float CARD_H = 210;
    private final Array<CardView> activeCards;
    private final Pool<CardView> cardPool;
    private final EvolutionGame game;
    private final TableView table;

    public HandView(EvolutionGame game, float x, float y, TableView table) {
        setPosition(x, y);
        this.game = game;
        this.table = table;
        activeCards = new Array<>(6);
        cardPool = new Pool<CardView>() {
            @Override
            protected CardView newObject() {
                return new CardView(CARD_W, CARD_H);
            }
        };
    }

    public void addCard(int id) {
        if (game.getCommunicationManager().requestCardAddition(id)) {
            CardView c = cardPool.obtain();
            c.init(game, id);
            activeCards.add(c);
            addActor(c);
        }
    }

    public void removeCardAt(int index) {
        if (index < 0) throw new InvalidParameterException("Card index must be positive");
        CardView c = activeCards.get(index);
        cardPool.free(c);
        activeCards.removeIndex(index);
        removeActor(c);
    }

    public void removeCard(CardView c) {
        cardPool.free(c);
        activeCards.removeIndex(activeCards.indexOf(c, true));
        removeActor(c);
    }

    @Override
    public void act(float delta) {
        int i = 0;
        for (CardView c : activeCards) {
            if (c.isInDeck()) c.updateDeckPosition(i, activeCards.size);
            i++;
        }
        super.act(delta);
        Vector2 mousepos = parentToLocalCoordinates(game.getMouseCoords());
    }

    public TableView getTable() {
        return table;
    }

    public int getCardIndex(CardView card) {
        return activeCards.indexOf(card, true);
    }
}
