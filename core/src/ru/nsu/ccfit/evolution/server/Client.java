package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import ru.nsu.ccfit.evolution.server.listeners.*;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;
import sun.net.NetworkClient;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: create Net.HttpResponseListener() for each post request
public class Client {

    private String baseURL = "http://37.230.115.215:5000/";

    private GameWorldState gameWorldState;
    private EvolutionGame evolutionGame;

    private static final Logger logger;

    private Timer lobbyCheckTimer = new Timer(true);

    static {
        logger = Logger.getLogger(Client.class.getName());
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
    }

    public Client(EvolutionGame evolutionGame, GameWorldState gameWorldState) {
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
        this.evolutionGame = evolutionGame;
        this.gameWorldState = gameWorldState;
    }

    public void createGame(int userID) {
        logger.info("Sending request to server for creating a game lobby, user id: " + userID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "newgame");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CreateGameListener(gameWorldState, evolutionGame));
    }

    public void login(String username) {
        logger.info("Sending request to server for logging in, username: " + username);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", String.valueOf(username));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "login");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        gameWorldState.setSelfUsername(username);
        Gdx.net.sendHttpRequest(httpRequest, new LoginListener(gameWorldState, evolutionGame));
    }

    public void cardPlacement(int userID, int cardID) {
        logger.info("Sending request to server for card placement, userID: " + userID + ", cardID: " + cardID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("selected_card", String.valueOf(cardID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "cardplacement/check");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CardPlacementListener(gameWorldState, evolutionGame));
    }

    public void cardAllocation(int userID) {
        logger.info("Sending request to server for card allocation, userID: " + userID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "cardallocation");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CardAllocationListener(gameWorldState, evolutionGame));
    }

    public void startGame(int userID, int gameID) {
        logger.info("Sending request to server for starting game, userID: " + userID + " gameID: " + gameID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("game", String.valueOf(gameID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "startgame");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new JoinGameListener(gameWorldState, evolutionGame));
    }

    public void joinGame(int userID, int gameID) {
        logger.info("Sending request to server for joining to game, userID: " + userID + " gameID: " + gameID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("game", String.valueOf(gameID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "joingame");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        gameWorldState.setGameID(gameID);
        Gdx.net.sendHttpRequest(httpRequest, new JoinGameListener(gameWorldState, evolutionGame));
    }

    public void checkLobby(int userID, int gameID) {
        logger.info("Sending request to server for checking possible changes in lobby: " + userID + " gameID: " + gameID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("game", String.valueOf(gameID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "checklobby");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CheckLobbyListener(gameWorldState, evolutionGame));
    }

    public void startLobbyChecking() {
        logger.info("Starting timer for lobby checking");
        lobbyCheckTimer.scheduleAtFixedRate(new LobbyChecker(), 0, 300);
    }

    public void stopLobbyChecking() {
        lobbyCheckTimer.cancel();
    }

    class LobbyChecker extends TimerTask {
        @Override
        public void run() {
            checkLobby(gameWorldState.getSelfID(), gameWorldState.getGameID());
        }
    }

    public static void main(String[] args) {
        //Client client = new Client();
    }

}
