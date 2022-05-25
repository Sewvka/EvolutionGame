package ru.nsu.ccfit.evolution.server;

import java.util.HashMap;

public class TableModel {
    private final HashMap<Integer, CreatureModel> creatures;

    public TableModel() {
        creatures = new HashMap<>();
    }

    public void addCreature(int id) {
        creatures.put(id, new CreatureModel(id));
    }

    public void removeCreature(int id) {
        CreatureModel c = creatures.get(id);
        for (AbilityModel a : c.getAbilities()) {
            int partnerID = a.getCreatureID1() == id ? a.getCreatureID2() : a.getCreatureID1();
            if (a.getName().equals("cooperation")) {
                creatures.get(partnerID).removeCoopAbility("cooperation", id);
            }
            if (a.getName().equals("communication")) {
                creatures.get(partnerID).removeCoopAbility("communication", id);
            }
            if (a.getName().equals("symbiosis")) {
                creatures.get(partnerID).removeCoopAbility("symbiosis", id);
            }
        }
        creatures.remove(id);
    }

    public void clearAllFood() {
        for (CreatureModel c : creatures.values()) {
            c.resetFood();
        }
    }

    public HashMap<Integer, CreatureModel> getCreatures() {
        return creatures;
    }
}
