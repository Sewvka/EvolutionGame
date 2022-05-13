package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractListener implements Net.HttpResponseListener {

    protected static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        int statusCode = httpResponse.getStatus().getStatusCode();
        if (statusCode == 200) {
            logger.fine("HTTP request successfully received");
            JsonReader jsonReader = new JsonReader();
            handle(jsonReader.parse(httpResponse.getResultAsString()).get("response"));
        } else {
            logger.severe("HTTP request failed, status code: " + statusCode);
        }
    }

    @Override
    public void failed(Throwable t) {
        logger.severe("Procession of HTTP request failed due to exception \n" + t.getMessage());
    }

    @Override
    public void cancelled() {
        logger.info("Response listener cancelled");
    }

    public abstract void handle(JsonValue httpResponse);
}
