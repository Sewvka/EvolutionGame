package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class AddPropertyListener extends AbstractListener {
    public AddPropertyListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        try {
            if (httpResponse.getBoolean("response")) {
                logger.info("Ability successfully placed.");
                gameWorldState.getHand().remove(gameWorldState.getPlacedCardIndex());
            } else {
                logger.info("Ability couldn't be placed.");
            }
            gameWorldState.setPlacedCardIndex(-1);
        } catch (IllegalArgumentException ex) {
            logger.severe(httpResponse.getString("response"));
            gameWorldState.setGameID(-1);
        }
    }
}
