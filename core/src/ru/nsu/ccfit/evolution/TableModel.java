package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class TableModel {
    private EvolutionGame game;
    private final Array<CreatureModel> activeCreatures;
    private final Pool<CreatureModel> creaturePool;

    public TableModel(EvolutionGame game) {
        this.game = game;
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

    public void addAbility(int selectedCreature, String ability) {
        activeCreatures.get(selectedCreature).addAbility(ability);
    }
}
