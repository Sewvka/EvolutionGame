package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class TableManager extends Group {
    private final Array<TableView> tables;
    private final TableView userTable;
    private Ability queuedAbility;
    private CreatureView queuedCreature;

    public TableManager(EvolutionGame game) {
        super();
        tables = new Array<>();
        userTable = new TableView(game, EvolutionGame.WORLD_SIZE_X / 2 - 350, 300, 700, 160);
        tables.add(userTable);
        addActor(userTable);
    }

    public TableView getSelectedTable() {
        for (TableView t : tables) {
            if (t.isSelected()) return t;
        }
        return null;
    }

    public TableView getUserTable() {
        return userTable;
    }

    public int indexOf(TableView table) {
        return tables.indexOf(table, true);
    }

    public int getSelectedCreatureIndex() {
        for (TableView t : tables) {
            if (t.isCreatureSelected()) {
                return t.getSelectedCreatureIndex();
            }
        }
        return -1;
    }

    public boolean isCreatureSelected() {
        return getSelectedCreatureIndex() > -1;
    }

    public void queueAbility(Ability queuedAbility, CreatureView queuedCreature) {
        this.queuedAbility = queuedAbility;
        this.queuedCreature = queuedCreature;
    }

    public boolean isAbilityQueued() {
        return queuedAbility != null;
    }

    public void resumeAbilityActivation(CreatureView targetCreature) {
        if (queuedAbility != null && !queuedCreature.equals(targetCreature)) {
            queuedAbility.resumeActivation(targetCreature, queuedCreature);
            queuedAbility = null;
        }
    }

    public void cancelAbilityUsage() {
        queuedAbility = null;
    }
}
