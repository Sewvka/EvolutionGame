package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class CheckChangesListener extends AbstractListener {

    public CheckChangesListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        if (httpResponse.has("game_stage")) {

        } else {
            logger.severe(
                    "Unexpected error in query to DB when checking a game, gameID: "
                            + gameWorldState.getGameID() + " userID: " + gameWorldState.getSelfID());
        }
    }

}
