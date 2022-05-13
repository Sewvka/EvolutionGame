package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;

public class LoginListener extends AbstractListener {

    @Override
    public void handle(JsonValue httpResponse) {
        int userID = httpResponse.getInt("user");
        logger.fine("User is successfully logged in, user id: " + userID);
        // TODO: do something
    }

}
