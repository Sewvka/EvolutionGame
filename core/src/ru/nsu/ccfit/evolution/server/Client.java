package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import ru.nsu.ccfit.evolution.server.listeners.CardAllocationListener;
import ru.nsu.ccfit.evolution.server.listeners.CreateGameListener;
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

    private String baseURL = "http://37.230.115.215:5000/";

    public Client() {
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

    public void createGame(int userID) {
        logger.info("Sending request to server for creating a game lobby, user id: " + userID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "newgame");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CreateGameListener());
    }

    public void login(String username) {
        logger.info("Sending request to server for logging in, username: " + username);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", String.valueOf(username));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "login");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CreateGameListener());
    }

    public void cardAllocation(int userID) {
        logger.info("Sending request to server for card allocation, userID: " + userID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "cardallocation");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CardAllocationListener());
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.login("ymimsr");
        client.createGame(2);
        client.cardAllocation(2);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
