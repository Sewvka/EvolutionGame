package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;

public class TableModel {
    private final Array<CreatureModel> activeCreatures;

    public TableModel() {
        activeCreatures = new Array<>(6);
    }

    public void addCreature() {
        activeCreatures.add(new CreatureModel());
    }

    public void removeCreature(int index) {
        activeCreatures.removeIndex(index);
    }

    public int getCreatureCount() {
        return activeCreatures.size;
    }

    public CreatureModel getCreature(int index) {
        return activeCreatures.get(index);
    }

    public void addAbility(int index, String ability) {
        activeCreatures.get(index).addAbility(ability);
    }

    public void addCoopAbility(int index1, int index2, String ability) {
        activeCreatures.get(index1).addCoopAbility(ability, index2);
        activeCreatures.get(index2).addCoopAbility(ability, index1);
    }

    public void resetActivations() {
        for (CreatureModel c : activeCreatures) {
            c.resetAbilities();
        }
    }

    public void clearAllFood() {
        for (CreatureModel c : activeCreatures) {
            c.resetFood();
        }
    }
}
