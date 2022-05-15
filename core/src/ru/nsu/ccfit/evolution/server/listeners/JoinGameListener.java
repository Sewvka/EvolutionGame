package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class JoinGameListener extends AbstractListener {

    public JoinGameListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        try {
            httpResponse.getBoolean("response");
            logger.info("User successfully joined the lobby");
            gameWorldState.setInLobby(true);
            evolutionGame.getClient().checkLobby(gameWorldState.getSelfID(), gameWorldState.getGameID());
        } catch (IllegalArgumentException ex) {
            logger.severe(httpResponse.getString("response"));
            gameWorldState.setGameID(-1);
        }
    }

}
