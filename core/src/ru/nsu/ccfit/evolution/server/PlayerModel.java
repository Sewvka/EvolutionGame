package ru.nsu.ccfit.evolution.server;

public class PlayerModel {
    public boolean passedTurn;
    private final TableModel table;
    private final HandModel hand;
    private final int playerID;

    public PlayerModel(int playerID) {
        this.playerID = playerID;
        passedTurn = false;
        table = new TableModel();
        hand = new HandModel();
    }


    public int getID() {
        return playerID;
    }

    public HandModel getHand() {
        return hand;
    }

    public TableModel getTable() {
        return table;
    }
}
