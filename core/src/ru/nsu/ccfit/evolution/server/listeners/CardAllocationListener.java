package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import com.sun.tools.javac.util.StringUtils;
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
        JsonValue jsonCards = httpResponse.get("cards");
        for (JsonValue jsonCard = jsonCards.child; jsonCard != null; jsonCard = jsonCard.next) {
            int cardID = jsonCard.asInt();
            cards.add(cardID);
        }
        gameWorldState.getHand().addAll(cards);
        logger.info("Card allocation, cards: " + jsonCards.toString());
    }

}
