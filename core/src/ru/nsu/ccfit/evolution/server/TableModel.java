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
        CreatureModel c = activeCreatures.get(index);
        for (CreatureModel partner : new Array.ArrayIterator<>(c.getCooperationList())) {
            int i = partner.getCooperationList().indexOf(c, true);
            partner.getCooperationList().removeValue(c, true);
            partner.getCooperationUsed().removeIndex(i);
        }
        for (CreatureModel partner : new Array.ArrayIterator<>(c.getCommunicationList())) {
            int i = partner.getCommunicationList().indexOf(c, true);
            partner.getCommunicationList().removeValue(c, true);
            partner.getCommunicationUsed().removeIndex(i);
        }
        for (CreatureModel partner : new Array.ArrayIterator<>(c.getSymbiosisList())) {
            partner.getSymbiosisList().removeValue(c, true);
        }
        activeCreatures.removeIndex(index);
    }

    public int getCreatureCount() {
        return activeCreatures.size;
    }

    public CreatureModel getCreature(int index) {
        return activeCreatures.get(index);
    }

    public int indexOf(CreatureModel c) {
        return activeCreatures.indexOf(c, true);
    }

    public Array<CreatureModel> getActiveCreatures() {
        return activeCreatures;
    }

    public void addAbility(int index, String ability) {
        activeCreatures.get(index).addAbility(ability);
    }

    public void addCoopAbility(int index1, int index2, String ability) {
        activeCreatures.get(index1).addCoopAbility(ability, activeCreatures.get(index2));
        activeCreatures.get(index2).addCoopAbility(ability, activeCreatures.get(index1));
    }

    public void resetPerRoundAbilities() {
        for (CreatureModel c : new Array.ArrayIterator<>(activeCreatures)) {
            c.resetPerRoundAbilities();
        }
    }

    public void resetPerTurnAbilities() {
        for (CreatureModel c : new Array.ArrayIterator<>(activeCreatures)) {
            c.resetPerTurnAbilities();
        }
    }

    public void clearAllFood() {
        for (CreatureModel c : new Array.ArrayIterator<>(activeCreatures)) {
            c.resetFood();
        }
    }
}
