package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.server.LobbyModel;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class GamesListListener extends AbstractListener {

    public GamesListListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        httpResponse = httpResponse.get("response");
        logger.info("Game lobbies list request received");
        JsonValue gamesListJson = httpResponse.get("games");
        for (JsonValue gameJson = gamesListJson.child; gameJson != null; gameJson = gameJson.next) {
            int gameID = gameJson.getInt("id");
            logger.info("Game lobby found, gameID: " + gameID);
            if (gameWorldState.getGameLobbies().contains(gameID)) {
                // TODO: check if amount of players in lobby needs to be changed
            } else {
                gameWorldState.getGameLobbies().add(new LobbyModel(gameID));
            }
        }
    }

}
