package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;

import java.util.logging.Logger;

public class CreateGameListener extends AbstractListener {

    @Override
    public void handle(JsonValue response) {
        if (response.has("game")) {
            int gameID = response.getInt("game");
            logger.fine("Game lobby is created, game id: " + gameID);

            // TODO: do something
        } else {
            // TODO: add user id
            logger.info("User is already in game, user id: ");
        }
    }

}
