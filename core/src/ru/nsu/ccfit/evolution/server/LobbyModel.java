package ru.nsu.ccfit.evolution.server;

import java.util.Objects;

// TODO: update LobbyModel
public class LobbyModel {

    private int gameID;
    private int playersCount;
    private String hostName;

    public LobbyModel(int gameID, int playersCount, String hostName) {
        this.gameID = gameID;
        this.playersCount = playersCount;
        this.hostName = hostName;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyModel that = (LobbyModel) o;
        return gameID == that.gameID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID);
    }

    @Override
    public String toString() {
        return "Host: " + hostName + "; Players: " + playersCount + "\\4";
    }
}
