package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class TableModel {
    private final HashMap<Integer, CreatureModel> creatures;

    public TableModel() {
        creatures = new HashMap<>();
    }

    public void addCreature(int id) {
        creatures.put(id, new CreatureModel());
    }

    public void removeCreature(int id) {
        CreatureModel c = creatures.get(id);
        for (CreatureModel partner : c.getCooperationList()) {
            int i = partner.getCooperationList().indexOf(c);
            partner.getCooperationList().remove(c);
        }
        for (CreatureModel partner : c.getCommunicationList()) {
            int i = partner.getCommunicationList().indexOf(c);
            partner.getCommunicationList().remove(c);
        }
        for (CreatureModel partner : c.getSymbiosisList()) {
            partner.getSymbiosisList().remove(c);
        }
        creatures.remove(id);
    }

    public int getCreatureCount() {
        return creatures.size();
    }

    public CreatureModel get(int id) {
        return creatures.get(id);
    }

    public void addAbility(int id, String ability) {
        creatures.get(id).addAbility(ability);
    }

    public void addCoopAbility(int id1, int id2, String ability) {
        creatures.get(id1).addCoopAbility(ability, creatures.get(id2));
        creatures.get(id2).addCoopAbility(ability, creatures.get(id1));
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
