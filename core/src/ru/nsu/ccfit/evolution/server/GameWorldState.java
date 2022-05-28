package ru.nsu.ccfit.evolution.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorldState {
    // initialized after login
    private boolean isLoggedIn = false;
    private int selfID = -1;
    private String selfUsername;

    // info for game lobbies
    // TODO: create LobbyModel which contains amount of players in lobby, etc; Use List<LobbyModel> after
    private List<LobbyModel> gameLobbies = new ArrayList<>();

    // initialized after joining/creating game
    private boolean isInLobby = false;
    private boolean isHost = false;
    private int gameID = -1;
    private int foodAvailable = 0;

    private Map<Integer, String> players = new HashMap<>();
    private final ArrayList<Integer> hand = new ArrayList<>();
    private final Map<Integer, TableModel> tables = new HashMap<>();

    private boolean isGameStarted = false;
    private GameStage currentGameStage;
    private int currentTurn = -1;

    //stuff to remember when playing cards/creatures.
    private int placedCardIndex = -1;

    //'flags' for turn and stage changes.
    private int currentTurnFlag = -1;
    private GameStage gameStageFlag = null;

    public int getFoodAvailable() {
        return foodAvailable;
    }

    public void setFoodAvailable(int foodAvailable) {
        this.foodAvailable = foodAvailable;
    }

    public GameStage getGameStageFlag() {
        return gameStageFlag;
    }

    public void setGameStageFlag(GameStage gameStageFlag) {
        this.gameStageFlag = gameStageFlag;
    }

    public void setCurrentGameStage(GameStage currentGameStage) {
        this.currentGameStage = currentGameStage;
    }

    public GameStage getCurrentGameStage() {
        return currentGameStage;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getCurrentTurnFlag() {
        return currentTurnFlag;
    }

    public void setCurrentTurnFlag(int currentTurnFlag) {
        this.currentTurnFlag = currentTurnFlag;
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

    public boolean notHost() {
        return !isHost;
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

    public List<LobbyModel> getGameLobbies() {
        return gameLobbies;
    }

    public void setGameLobbies(List<LobbyModel> gameLobbies) {
        this.gameLobbies = gameLobbies;
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
        currentTurnFlag = -1;
        gameStageFlag = null;
    }

}
