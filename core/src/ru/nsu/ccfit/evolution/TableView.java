package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class TableView extends Rectangle implements Drawable {
    static final float CREATURE_W = 100;
    static final float CREATURE_H = 140;
    final int TEXTURE_W = 740;
    final int TEXTURE_H = 1036;
    private final Array<CreatureView> activeCreatures;
    private int selectedCreature;

    private final Pool<CreatureView> creaturePool;
    private final EvolutionGame game;
    final Texture tableTexture;

    public TableView(EvolutionGame game, float x, float y, float tableW, float tableH) {
        super(x, y, tableW, tableH);
        this.game = game;
        tableTexture = game.getLoader().getTexture("table.png");
        activeCreatures = new Array<>(6);
        creaturePool = new Pool<CreatureView>() {
            @Override
            protected CreatureView newObject() {
                return new CreatureView(CREATURE_W, CREATURE_H);
            }
        };
        selectedCreature = -1;
    }

    public boolean addCreature(int selectedCard) {
        if (game.getCommunicationManager().requestCreaturePlacement(selectedCard)) {
            CreatureView c = creaturePool.obtain();
            c.init(game);
            activeCreatures.add(c);
            return true;
        }
        return false;
    }

    public boolean addAbility(String ability, int selectedCard) {
        if (game.getCommunicationManager().requestAbilityPlacement(ability, selectedCard, selectedCreature)) {
            activeCreatures.get(selectedCreature).addAbility(ability);
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tableTexture, x, y, width, height);
        for (CreatureView c : activeCreatures) {
            c.draw(batch, TEXTURE_W, TEXTURE_H);
        }
    }

    public boolean creatureSelected() {
        return (selectedCreature != -1);
    }

    public void updateLogic(Vector2 mousepos) {
        int i = 0;
        int count = activeCreatures.size;
        boolean selectionFlag = false;
        for (CreatureView c : activeCreatures) {
            float xOffset = (i - (float) (count - 1) / 2) * (CREATURE_W);
            c.x = x + width / 2 - CREATURE_W / 2 + xOffset;
            c.y = y + height / 2 - CREATURE_H / 2;

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
