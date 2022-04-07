package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class HandModel {
    private final Array<CardModel> activeCards;
    private final Pool<CardModel> cardPool;

    public HandModel() {
        activeCards = new Array<>(6);
        cardPool = new Pool<CardModel>() {
            @Override
            protected CardModel newObject() {
                return new CardModel();
            }
        };
    }

    public int getCardCount() {
        return activeCards.size;
    }

    public boolean containsAbility(String ability, int i) {
        CardModel c = activeCards.get(i);
        return c.getAbility1().equals(ability) || c.getAbility2().equals(ability);
    }

    public void addCard(int id) {
        CardModel c = cardPool.obtain();
        c.init(id);
        activeCards.add(c);
    }

    public void removeCard(int i) {
        cardPool.free(activeCards.get(i));
        activeCards.removeIndex(i);
    }

    public int getCardIndex(CardModel card) {
        return activeCards.indexOf(card, true);
    }
}
