package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import sun.net.NetworkClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: create Net.HttpResponseListener() for each post request
public class Client {

    private String serverUrl = "http://37.230.115.215:5000/";
    private final Json json = new Json();
    private final JsonReader jsonReader = new JsonReader();

    // нужен класс Game(?), который хранит в себе состояние игры, все видимое игроком окружение
    // ResponseListener'ы должны обращаться к классу Game и менять состояние после получения запроса с сервера
    public Client() {
        json.setTypeName(null);
        json.setOutputType(JsonWriter.OutputType.json);
        json.setUsePrototypes(false);
        Gdx.net = new Net() {
            private NetJavaImpl netJavaImpl = new NetJavaImpl();

            @Override
            public void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener) {
                netJavaImpl.sendHttpRequest(httpRequest, httpResponseListener);
            }

            @Override
            public void cancelHttpRequest(HttpRequest httpRequest) {
                netJavaImpl.cancelHttpRequest(httpRequest);
            }

            @Override
            public ServerSocket newServerSocket(Protocol protocol, String hostname, int port, ServerSocketHints hints) {
                return null;
            }

            @Override
            public ServerSocket newServerSocket(Protocol protocol, int port, ServerSocketHints hints) {
                return null;
            }

            @Override
            public Socket newClientSocket(Protocol protocol, String host, int port, SocketHints hints) {
                return null;
            }

            @Override
            public boolean openURI(String URI) {
                return false;
            }
        };
    }

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    public int createGame(int userID) {
        logger.info("Trying to create json for request to create game");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        String response = sendRequest(parameters, "newgame");
        JsonValue jsonResponse = jsonReader.parse(response).get("response");

        int gameID = jsonResponse.getInt("game");
        logger.fine("Game is created, gameID: " + gameID);

        return gameID;
    }

    public int login(String userName) {
        logger.info("Trying to create json for request to login for " + userName);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", String.valueOf(userName));

        String response = sendRequest(parameters, "login");
        JsonValue jsonResponse = jsonReader.parse(response).get("response");

        int userID = jsonResponse.getInt("user");
        logger.fine("User" + userName + " is logged in, userID: " + userID);

        return userID;
    }

    private String sendRequest(final Map<String, String> parameters, final String request) {
        final String url = serverUrl + request;
        logger.info(
                "Trying to send request to " + url + "\n" +
                        "Content: " + HttpParametersUtils.convertHttpParameters(parameters)
        );

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(url);
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        final String[] response = {""};
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus httpStatus = httpResponse.getStatus();
                if (httpStatus.getStatusCode() == 200) {
                    response[0] = httpResponse.getResultAsString();
                    logger.fine("HttpResponse from " + url + " " + response[0]);
                } else {
                    response[0] = "failed";
                    logger.severe(
                            "HttpResponse from " + url + " failed, status code is " + httpStatus.getStatusCode() + "\n" +
                                    "response:\n" + httpResponse.getResultAsString()
                    );
                }
            }

            @Override
            public void failed(Throwable t) {
                response[0] = "failed";
                logger.log(Level.SEVERE, "HttpResponse from " + url + " failed", t);
            }

            @Override
            public void cancelled() {
                response[0] = "cancelled";
                logger.warning("HttpResponse from " + url + " has been cancelled");
            }
        });

        while (Objects.equals(response[0], "")) {
        }

        return response[0];
    }

    // for tests only
    public static void main(String[] args) {
        Client client = new Client();
    }

}
