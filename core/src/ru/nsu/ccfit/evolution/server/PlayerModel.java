package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.user.actors.Ability;
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

    public void addExtinctCreature(int index) {
        extinctCreatures.add(index);
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

    public int getVictoryPoints() {
        float result = 0;
        result += table.getCreatureCount() * 2;
        for (int i = 0; i < table.getCreatureCount(); i++) {
            result += table.getCreature(i).foodRequired() - 1;
            result += table.getCreature(i).getAbilityCount();
        }

        return (int) result;
    }
}
