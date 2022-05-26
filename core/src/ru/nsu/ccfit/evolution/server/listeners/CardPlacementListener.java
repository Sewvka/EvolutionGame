package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class CardPlacementListener extends AbstractListener {
    public CardPlacementListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        JsonValue response = httpResponse.get("response");
        if (response.has("creature")) {
            int creatureID = response.getInt("creature");
            gameWorldState.getHand().remove(gameWorldState.getPlacedCardIndex());
            logger.info("Creature successfully placed. New creature ID: " + creatureID);
        }
        else {
            logger.info("Creature couldn't be placed");
        }
        gameWorldState.setPlacedCardIndex(-1);
    }

}
