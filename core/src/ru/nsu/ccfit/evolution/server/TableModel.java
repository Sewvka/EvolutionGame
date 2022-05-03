package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.server.CreatureModel;

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
        for (int id : new Array.ArrayIterator<>(c.getCooperationList())) {
            activeCreatures.get(id).getCooperationList().removeValue(index, false);
        }
        for (int id : new Array.ArrayIterator<>(c.getCommunicationList())) {
            activeCreatures.get(id).getCommunicationList().removeValue(index, false);
        }
        for (int id : new Array.ArrayIterator<>(c.getSymbiosisList())) {
            activeCreatures.get(id).getSymbiosisList().removeValue(index, false);
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
        activeCreatures.get(index1).addCoopAbility(ability, index2);
        activeCreatures.get(index2).addCoopAbility(ability, index1);
    }

    public void resetActivations() {
        for (CreatureModel c : new Array.ArrayIterator<>(activeCreatures)) {
            c.resetAbilities();
        }
    }

    public void clearAllFood() {
        for (CreatureModel c : new Array.ArrayIterator<>(activeCreatures)) {
            c.resetFood();
        }
    }
}
