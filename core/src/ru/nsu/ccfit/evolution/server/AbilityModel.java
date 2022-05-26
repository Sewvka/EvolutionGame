package ru.nsu.ccfit.evolution.server;

import ru.nsu.ccfit.evolution.common.Abilities;

public class AbilityModel {
    private final int id;
    private final int appointedCreatureID1;
    private final int appointedCreatureID2;
    private final String name;

    public AbilityModel(String name, int creature1, int creature2) {
        this.id = Abilities.getAbilityID(name);
        appointedCreatureID1 = creature1;
        appointedCreatureID2 = creature2;
        this.name = name;
    }

    public boolean isCooperative() {
        return appointedCreatureID1 != appointedCreatureID2;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCreatureID1() {
        return appointedCreatureID1;
    }

    public int getCreatureID2() {
        return appointedCreatureID2;
    }
}
