package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class QuitGameListener extends AbstractListener {

    public QuitGameListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        JsonValue jsonResponse = httpResponse.get("response");
        if (jsonResponse.isBoolean()) {
            if (jsonResponse.asBoolean()) {
                logger.info("User successfully quited game, userID: " + gameWorldState.getSelfID());
                gameWorldState.clear();
            } else {
                logger.info("User couldn't quit game, userID: " + gameWorldState.getGameID());
            }
        } else {
            logger.severe(
                    "Unexpected error in query to DB when quitting game, gameID: "
                            + gameWorldState.getSelfID());
        }
    }

}
