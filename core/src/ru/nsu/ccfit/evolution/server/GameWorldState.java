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

    private Map<Integer, String> players;
    private final ArrayList<Integer> hand = new ArrayList<>();
    private final Map<Integer, TableModel> tables = new HashMap<>();

    private boolean isGameStarted = false;

    //stuff to remember when playing card/creatures.
    private int placedCardIndex = -1;
    private int targetedCreatureID1 = -1;
    private int targetedCreatureID2 = -1;
    private String playedAbility = null;

    public int getTargetedCreatureID1() {
        return targetedCreatureID1;
    }

    public int getTargetedCreatureID2() {
        return targetedCreatureID2;
    }

    public void setTargetedCreatureID1(int targetedCreatureID1) {
        this.targetedCreatureID1 = targetedCreatureID1;
    }

    public void setTargetedCreatureID2(int targetedCreatureID2) {
        this.targetedCreatureID2 = targetedCreatureID2;
    }

    public String getPlayedAbility() {
        return playedAbility;
    }

    public void setPlayedAbility(String playedAbility) {
        this.playedAbility = playedAbility;
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
}
