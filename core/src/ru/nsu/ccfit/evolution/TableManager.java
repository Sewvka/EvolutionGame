package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class TableManager extends Group {
    private final Array<TableView> tables;
    private final TableView userTable;

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
}
