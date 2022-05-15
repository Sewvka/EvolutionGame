package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.util.ArrayList;
import java.util.List;

public class CardAllocationListener extends AbstractListener {

    public CardAllocationListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        List<Integer> cards = new ArrayList<>();
        String cardIterator = httpResponse.getString("cards");
        logger.info("Card allocation, cards: " + cardIterator);
    }

}
