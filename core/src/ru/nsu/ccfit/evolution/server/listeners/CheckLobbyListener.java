package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.server.TableModel;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckLobbyListener extends AbstractListener {

    public CheckLobbyListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        JsonValue response = httpResponse.get("response");
        if (response.has("players")) {
            logger.info("Check lobby request successfully received, gameID: " + gameWorldState.getGameID());
            JsonValue jsonPlayers = response.get("players");
            Map<Integer, String> players = new HashMap<>();
            for (JsonValue jsonPlayer = jsonPlayers.child; jsonPlayer != null; jsonPlayer = jsonPlayer.next) {
                int playerID = jsonPlayer.getInt(0);
                String playerName = jsonPlayer.getString(2);
                players.put(playerID, playerName);
                logger.info("Username: " + playerName + ", userID: " + playerID);
            }
            gameWorldState.setPlayers(players);
            for (int id : players.keySet()) {
                if (!gameWorldState.getTables().containsKey(id)) {
                    gameWorldState.getTables().put(id, new TableModel());
                }
            }

            if (response.has("status")) {
                String status = response.getString("status");
                if (status.toLowerCase(Locale.ROOT).contains("started")) {
                    logger.info("Game is started");
                    gameWorldState.setGameStarted(true);
                    evolutionGame.getClient().stopLobbyChecking();
                    evolutionGame.getClient().startChangeChecking();
                }
            }
        } else {
            logger.severe(
                    "Unexpected error in query to DB when checking a lobby, gameID: "
                            + gameWorldState.getGameID() + " userID: " + gameWorldState.getSelfID());
        }
    }

}
