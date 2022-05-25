package ru.nsu.ccfit.evolution.server;

import ru.nsu.ccfit.evolution.common.Abilities;

public class AbilityModel {
    public AbilityModel(int id, int creature1, int creature2) {
        this.id = id;
        appointedCreatureID1 = creature1;
        appointedCreatureID2 = creature2;
        name = Abilities.getAbilityName(id);
    }

    private final int id;
    private final int appointedCreatureID1;
    private final int appointedCreatureID2;
    private final String name;

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
