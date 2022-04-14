package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.server.CardModel;

public class HandModel {
    private final Array<CardModel> activeCards;
    private final Array<Integer> drawnCards;

    public HandModel() {
        activeCards = new Array<>(6);
        drawnCards = new Array<>(6);
    }

    public int getCardCount() {
        return activeCards.size;
    }

    public boolean containsAbility(String ability, int i) {
        CardModel c = activeCards.get(i);
        return c.getAbility1().equals(ability) || c.getAbility2().equals(ability);
    }

    public Array<Integer> getDrawnCards() {
        return drawnCards;
    }

    public void commitDrawnCards() {
        for (Integer id : drawnCards) {
            activeCards.add(new CardModel(id));
        }
        drawnCards.clear();
    }

    public void drawCard(int id) {
        drawnCards.add(id);
    }

    public void removeCard(int i) {
        activeCards.removeIndex(i);
    }

    public int getCardIndex(CardModel card) {
        return activeCards.indexOf(card, true);
    }
}
