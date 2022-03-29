package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CreatureTable extends Rectangle {
    static final float CREATURE_W = 100;
    static final float CREATURE_H = 140;
    private final Array<Creature> activeCreatures;
    private int selectedCreature;

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
        selectedCreature = -1;
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

    public boolean isCreatureSelected() {
        return (selectedCreature != -1);
    }

    public void update(Vector2 mousepos) {
        int i = 0;
        int count = activeCreatures.size;
        boolean selectionFlag = false;

        for (Creature c : activeCreatures) {
            float xOffset = (i - (float) (count - 1) / 2) * (CREATURE_W);
            c.x = x+width/2 - CREATURE_W/2 + xOffset;
            c.y = y+height/2 - CREATURE_H/2;

            if (c.contains(mousepos)) {
                //если карта не выбрана, выбираем эту
                if (selectedCreature == -1) {
                    c.setSizeMod(1.1f);
                    selectedCreature = i;
                    selectionFlag = true;
                } else if (selectedCreature == i) { //если это и есть выбранная карта, то ничего не меняется
                    selectionFlag = true;
                }
                //если на карту не навели мышкой, то она рисуется обычного размера
            } else {
                c.setSizeMod(1);
            }
            i++;
        }

        if (!selectionFlag) selectedCreature = -1;
    }
}
