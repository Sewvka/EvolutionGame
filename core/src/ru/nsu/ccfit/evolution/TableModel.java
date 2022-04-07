package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class TableModel {
    private final Array<CreatureModel> activeCreatures;
    private final Pool<CreatureModel> creaturePool;

    public TableModel() {
        activeCreatures = new Array<>(6);
        creaturePool = new Pool<CreatureModel>() {
            @Override
            protected CreatureModel newObject() {
                return new CreatureModel();
            }
        };
    }

    public void addCreature() {
        CreatureModel c = creaturePool.obtain();
        activeCreatures.add(c);
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

    public void clearAllFood() {
        for (CreatureModel c : activeCreatures) {
            c.resetFood();
        }
    }
}
