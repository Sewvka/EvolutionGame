package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class LoginListener extends AbstractListener {

    public LoginListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        int userID = httpResponse.get("response").getInt("user");
        logger.info("User is successfully logged in, user id: " + userID);
        gameWorldState.setSelfID(userID);
        gameWorldState.setLoggedIn(true);
        // TODO: do something
    }

}
