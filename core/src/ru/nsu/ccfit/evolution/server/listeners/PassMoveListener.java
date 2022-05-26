package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class PassMoveListener extends AbstractListener {
    public PassMoveListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        JsonValue response = httpResponse.get("response");
        if (response.has("move")) {
            logger.info("Turn successfully passed. Next player ID: " + response.getInt("move"));
        }
        else {
            logger.info("Turn couldn't be passed.");
        }
    }
}
