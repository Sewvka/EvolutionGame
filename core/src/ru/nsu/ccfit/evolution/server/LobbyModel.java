package ru.nsu.ccfit.evolution.server;

import java.util.Objects;

// TODO: update LobbyModel
public class LobbyModel {

    private int gameID;
    // private int players;
    // private String hostName;


    public LobbyModel(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
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
        return "LobbyModel{" +
                "gameID=" + gameID +
                '}';
    }
}
