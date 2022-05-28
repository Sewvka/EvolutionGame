package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.*;
import ru.nsu.ccfit.evolution.server.listeners.*;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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
    private Timer checkChangesTimer = new Timer(true);
    private Timer gamesListTimer = new Timer(true);

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

    public void addProperty(int userID, int cardID, int creature1ID, int creature2ID, String selectedProperty) {
        logger.info("Sending request to server for property addition, userID: " + userID + ", cardID: " + cardID +
                ", creatureID: " + creature1ID + "creature2ID: " + creature2ID + ", ability name: " + selectedProperty);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("initial_card", String.valueOf(cardID));
        parameters.put("creature_card", String.valueOf(creature1ID));
        if (creature1ID != creature2ID) parameters.put("coop_creature", String.valueOf(creature2ID));
        parameters.put("selected_property", selectedProperty);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "addproperty");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new AddPropertyListener(gameWorldState, evolutionGame));
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

        Gdx.net.sendHttpRequest(httpRequest, new StartGameListener(gameWorldState, evolutionGame));
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

    public void quitGame(int userID) {
        logger.info("Sending request to server to quit game, userID: " + userID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "quitgame");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new QuitGameListener(gameWorldState, evolutionGame));
    }

    public void checkChanges(int userID, int gameID) {
        logger.info("Sending request to server for checking possible changes in game. User: " + userID + " gameID: " + gameID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("game", String.valueOf(gameID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "checkchanges");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new CheckChangesListener(gameWorldState, evolutionGame));
    }

    public void startChangeChecking() {
        logger.info("Starting timer for change checking");
        checkChangesTimer.scheduleAtFixedRate(new ChangeChecker(), 0, 500);
    }

    public void startLobbyChecking() {
        logger.info("Starting timer for lobby checking");
        lobbyCheckTimer.scheduleAtFixedRate(new LobbyChecker(), 0, 500);
    }

    public void passMove(int userID) {
        logger.info("Sending request to server for passing turn: " + userID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "passmove");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new PassMoveListener(gameWorldState, evolutionGame));
    }

    public void gamesList() {
        logger.info("Sending request to server for game lobbies list");

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "gameslist");

        Gdx.net.sendHttpRequest(httpRequest, new GamesListListener(gameWorldState, evolutionGame));
    }

    public void startGamesListChecking() {
        logger.info("Starting timer for games list checking");
        gamesListTimer.scheduleAtFixedRate(new GamesListChecker(), 0, 500);
    }

    public void feed(int userID, int creatureID) {
        logger.info("Sending request to feed creature. CreatureID: " + creatureID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("creature_card", String.valueOf(creatureID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "feed");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new FeedListener(gameWorldState, evolutionGame));
    }

    public void eat(int userID, int creatureID, int targetID) {
        logger.info("Sending request to eat creature. Predator ID: " + creatureID + ", prey ID: " + targetID);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(userID));
        parameters.put("creature_card", String.valueOf(creatureID));
        parameters.put("target_id", String.valueOf(targetID));

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(baseURL + "eat");
        httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        Gdx.net.sendHttpRequest(httpRequest, new EatListener(gameWorldState, evolutionGame));
    }

    public void stopLobbyChecking() {
        lobbyCheckTimer.cancel();
    }

    public void stopChangeChecking() {
        checkChangesTimer.cancel();
    }

    public void stopGamesListChecking() {
        gamesListTimer.cancel();
    }

    class LobbyChecker extends TimerTask {
        @Override
        public void run() {
            checkLobby(gameWorldState.getSelfID(), gameWorldState.getGameID());
        }
    }

    class ChangeChecker extends TimerTask {
        @Override
        public void run() {
            checkChanges(gameWorldState.getSelfID(), gameWorldState.getGameID());
        }
    }

    class GamesListChecker extends TimerTask {
        @Override
        public void run() {
            gamesList();
        }
    }

    public static void main(String[] args) {
    }

}
