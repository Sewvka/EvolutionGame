package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class EatListener extends AbstractListener {
    public EatListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        if (httpResponse.get("response").has("creature")) {
            logger.info("Predator successfully ate creature.");
        }
        else {
            logger.info("Predator couldn't eat creature.");
        }
    }
}
