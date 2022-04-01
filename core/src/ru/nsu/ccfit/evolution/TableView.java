package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class TableView extends Group {
    static final float CREATURE_W = 100;
    static final float CREATURE_H = 140;
    private final Array<CreatureView> activeCreatures;
    private int selectedCreature;

    private final Pool<CreatureView> creaturePool;
    private final EvolutionGame game;
    final TextureRegion tableTexture;

    public TableView(final EvolutionGame game, float x, float y, float tableW, float tableH) {
        super();
        this.game = game;
        setPosition(x, y);
        setSize(tableW, tableH);
        setOrigin(tableW/2, tableH/2);
        setBounds(x, y, tableW, tableH);
        tableTexture = new TextureRegion(game.getLoader().getTexture("table.png"));
        activeCreatures = new Array<>(6);
        creaturePool = new Pool<CreatureView>() {
            @Override
            protected CreatureView newObject() {
                return new CreatureView(CREATURE_W, CREATURE_H, game);
            }
        };
        selectedCreature = -1;
    }

    public boolean addCreature(int selectedCard) {
        if (game.getCommunicationManager().requestCreaturePlacement(selectedCard)) {
            CreatureView c = creaturePool.obtain();
            activeCreatures.add(c);
            addActor(c);
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tableTexture, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        super.draw(batch, parentAlpha);
    }

    public boolean creatureSelected() {
        return (selectedCreature != -1);
    }

    public void act(float delta) {
        super.act(delta);

        Vector2 mousepos = parentToLocalCoordinates(game.getMouseCoords());


        Actor hit = hit(mousepos.x, mousepos.y, true);
        if (hit instanceof CreatureView) {
            CreatureView c = (CreatureView) hit;
            if (selectedCreature != activeCreatures.indexOf(c, true)) {
                c.select();
                if (selectedCreature != -1) activeCreatures.get(selectedCreature).deselect();
                selectedCreature = activeCreatures.indexOf(c, true);
            }
        }
        else if (hit == null && selectedCreature != -1) {
            activeCreatures.get(selectedCreature).deselect();
            selectedCreature = -1;
        }
    }
}
