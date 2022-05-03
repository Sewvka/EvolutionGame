package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.user.actors.CreatureView;

public class PlayerModel {
    public boolean passedTurn;
    private final TableModel table;
    private final HandModel hand;
    private final int playerID;
    private final Array<Integer> extinctCreatures;

    public PlayerModel(int playerID) {
        this.playerID = playerID;
        passedTurn = false;
        extinctCreatures = new Array<>();
        table = new TableModel();
        hand = new HandModel();
    }

    public void addExtinctCreature(CreatureModel c) {
        extinctCreatures.add(table.indexOf(c));
    }

    public Array<Integer> getExtinctCreatures() {
        return extinctCreatures;
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
