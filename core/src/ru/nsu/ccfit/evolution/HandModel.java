package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import javax.smartcardio.Card;

public class HandModel {
    private final Array<CardModel> activeCards;

    public HandModel() {
        activeCards = new Array<>(6);
    }

    public int getCardCount() {
        return activeCards.size;
    }

    public boolean containsAbility(String ability, int i) {
        CardModel c = activeCards.get(i);
        return c.getAbility1().equals(ability) || c.getAbility2().equals(ability);
    }

    public void addCard(int id) {
        CardModel c = new CardModel(id);
        activeCards.add(c);
    }

    public void removeCard(int i) {
        activeCards.removeIndex(i);
    }

    public int getCardIndex(CardModel card) {
        return activeCards.indexOf(card, true);
    }
}
