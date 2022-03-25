package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CreatureTable extends Rectangle {
    final float CREATURE_W = 100;
    final float CREATURE_H = 140;
    private final Array<Creature> activeCreatures;

    private final Pool<Creature> creaturePool;
    private final EvolutionGame game;
    final Texture tableTexture;

    public CreatureTable(EvolutionGame game, float tableW, float tableH) {
        super(game.getWorldSizeX()/2 - tableW/2, 500, tableW, tableH);
        this.game = game;
        x = game.getWorldSizeX()/2 - tableW/2;
        y = game.getWorldSizeY()/2 - tableH/2;
        width = tableW;
        height = tableH;
        tableTexture = game.assets.get("table.png", Texture.class);
        activeCreatures = new Array<>(6);
        creaturePool = new Pool<Creature>() {
            @Override
            protected Creature newObject() {
                return new Creature(CREATURE_W, CREATURE_H);
            }
        };
    }

    public void addCreature() {
        Creature c = creaturePool.obtain();
        c.init(game);
        activeCreatures.add(c);
    }

    public void drawAll(SpriteBatch batch) {
        for (Creature c : activeCreatures) {
            c.draw(batch);
        }
    }

    public void update() {
        int i = 0;
        int count = activeCreatures.size;
        for (Creature c : activeCreatures) {
            float xOffset = (i - (float) (count - 1) / 2) * (CREATURE_W);
            c.x = x+width/2 - CREATURE_W/2 + xOffset;
            c.y = y+height/2 - CREATURE_H/2;
            i++;
        }
    }
}
