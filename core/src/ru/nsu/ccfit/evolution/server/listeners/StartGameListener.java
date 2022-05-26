package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class StartGameListener extends AbstractListener {
    public StartGameListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        boolean result = httpResponse.getBoolean("response");
        logger.info("Game start result: " + result);

        gameWorldState.setInLobby(false);
    }
}
