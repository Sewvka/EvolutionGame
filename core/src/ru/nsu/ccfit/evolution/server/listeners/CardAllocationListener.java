package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class CardAllocationListener extends AbstractListener {

    @Override
    public void handle(JsonValue httpResponse) {
        List<Integer> cards = new ArrayList<>();
        String cardIterator = httpResponse.getString("cards");
        logger.info("Card allocation, cards: " + cardIterator);
    }

}
