package ru.nsu.ccfit.evolution.server;

public class AbilityModel {
    public AbilityModel(int id, int creature1, int creature2) {
        this.id = id;
        appointedCreatureID1 = creature1;
        appointedCreatureID2 = creature2;
    }

    private final int id;
    private final int appointedCreatureID1;
    private final int appointedCreatureID2;
    private int property;

    public int getId() {
        return id;
    }

    public int getCreatureID1() {
        return appointedCreatureID1;
    }

    public int getCreatureID2() {
        return appointedCreatureID2;
    }

    public int getProperty() {
        return property;
    }
}
