package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class CreateGameListener extends AbstractListener {

    public CreateGameListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue response) {
        if (response.get("response").has("game")) {
            int gameID = response.get("response").getInt("game");
            logger.info("Game lobby is created, game id: " + gameID);
            gameWorldState.setGameID(gameID);
            gameWorldState.setHost(true);
            gameWorldState.setInLobby(true);
            evolutionGame.getClient().startLobbyChecking();
        } else {
            logger.info("User is already in game, user id: " + gameWorldState.getSelfID());
        }
    }

}
