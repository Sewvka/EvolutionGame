package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.CreatureModel;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.server.TableModel;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class CheckChangesListener extends AbstractListener {

    public CheckChangesListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        if (httpResponse.has("game_stage")) {
            httpResponse = httpResponse.get("game_stage");
            if (httpResponse.has("creatures")) {
                JsonValue creaturesJson = httpResponse.get("creatures");
                for (JsonValue creatureJson = creaturesJson.child; creatureJson != null; creatureJson = creaturesJson.next) {
                    int userID = creatureJson.get("player").getInt("id");
                    int creatureID = creatureJson.getInt("id");
                    TableModel userTable = gameWorldState.getTables().get(userID);
                    if (userTable.getCreatures().containsKey(creatureID)) {
                        CreatureModel creature = userTable.getCreatures().get(creatureID);
                    }
                }
            }
        } else {
            logger.severe(
                    "Unexpected error in query to DB when checking a game, gameID: "
                            + gameWorldState.getGameID() + " userID: " + gameWorldState.getSelfID());
        }
    }

}
