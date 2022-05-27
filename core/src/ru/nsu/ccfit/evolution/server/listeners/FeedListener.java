package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class FeedListener extends AbstractListener {
    public FeedListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        try {
            if (httpResponse.getBoolean("response")) {
                logger.info("Creature successfully fed.");
            }
            else {
                logger.info("Creature couldn't be fed.");
            }
        } catch (IllegalArgumentException ex) {
            logger.severe(httpResponse.getString("response"));
        }
    }
}
