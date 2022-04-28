package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Deck {
    private static final int DECK_SIZE = 84;
    private static final Map<Integer, Integer> cardAmountMap;

    static {
        HashMap<Integer, Integer> aMap = new HashMap<>();
        aMap.put(1, 4);
        aMap.put(2, 4);
        aMap.put(3, 4);
        aMap.put(4, 4);
        aMap.put(5, 4);
        aMap.put(6, 4);
        aMap.put(7, 4);
        aMap.put(8, 4);
        aMap.put(9, 4);
        aMap.put(10, 4);
        aMap.put(11, 4);
        aMap.put(12, 4);
        aMap.put(13, 4);
        aMap.put(14, 4);
        aMap.put(15, 4);
        aMap.put(16, 4);
        aMap.put(17, 4);
        aMap.put(18, 4);
        aMap.put(19, 8);
        aMap.put(20, 4);
        cardAmountMap = Collections.unmodifiableMap(aMap);
    }

    private final Array<Integer> cards;

    public Deck() {
        cards = new Array<>(DECK_SIZE);

        for (int i = 1; i <= 20; i++) {
            for (int j = 0; j < cardAmountMap.get(i); j++) cards.add(i);
        }

        cards.shuffle();
    }

    public int draw() {
        return cards.removeIndex(cards.size - 1);
    }
}
