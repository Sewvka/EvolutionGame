package ru.nsu.ccfit.evolution.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameWorldState {
    // initialized after login
    private boolean isLoggedIn = false;
    private int selfID = -1;
    private String selfUsername;

    // initialized after joining/creating game
    private boolean isInLobby = false;
    private boolean isHost = false;
    private int gameID = -1;
    private int foodAvailable = 0;

    private Map<Integer, String> players = new HashMap<>();
    private final ArrayList<Integer> hand = new ArrayList<>();
    private final Map<Integer, TableModel> tables = new HashMap<>();

    private boolean isGameStarted = false;

    //stuff to remember when playing cards/creatures.
    private int placedCardIndex = -1;

    //'flags' for turn and stage changes.
    private int activePlayerID = -1;
    private GameStage gameStage = null;

    public int getFoodAvailable() {
        return foodAvailable;
    }

    public void setFoodAvailable(int foodAvailable) {
        this.foodAvailable = foodAvailable;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public int getActivePlayerID() {
        return activePlayerID;
    }

    public void setActivePlayerID(int activePlayerID) {
        this.activePlayerID = activePlayerID;
    }

    public ArrayList<Integer> getHand() {
        return hand;
    }

    public void initTables() {
        for (int id : players.keySet()) {
            tables.put(id, new TableModel());
        }
    }

    public Map<Integer, TableModel> getTables() {
        return tables;
    }

    public int getPlacedCardIndex() {
        return placedCardIndex;
    }

    public void setPlacedCardIndex(int placedCardIndex) {
        this.placedCardIndex = placedCardIndex;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public int getSelfID() {
        return selfID;
    }

    public void setSelfID(int selfID) {
        this.selfID = selfID;
    }

    public String getSelfUsername() {
        return selfUsername;
    }

    public void setSelfUsername(String selfUsername) {
        this.selfUsername = selfUsername;
    }

    public boolean isInLobby() {
        return isInLobby;
    }

    public void setInLobby(boolean inLobby) {
        isInLobby = inLobby;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Map<Integer, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, String> players) {
        this.players = players;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public void clear() {
        isInLobby = false;
        isHost = false;
        gameID = -1;

        players.clear();
        hand.clear();
        tables.clear();

        isGameStarted = false;

        placedCardIndex = -1;
        activePlayerID = -1;
        gameStage = null;
    }

}
