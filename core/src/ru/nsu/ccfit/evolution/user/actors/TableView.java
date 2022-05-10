package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class TableView extends Group {
    static final float CREATURE_W = 80;
    static final float CREATURE_H = 112;
    private final Array<CreatureView> activeCreatures;
    private CreatureView selectedCreature;
    private boolean tableSelected;
    private final Pool<CreatureView> creaturePool;
    final TextureRegion tableTexture;

    public TableView(final EvolutionGame game, float x, float y, float tableW, float tableH) {
        super();
        tableSelected = false;
        setPosition(x, y);
        setSize(tableW, tableH);
        setOrigin(tableW / 2, tableH / 2);
        setBounds(x, y, tableW, tableH);
        tableTexture = new TextureRegion(game.getAssets().getTexture("table.png"));
        activeCreatures = new Array<>();
        creaturePool = new Pool<CreatureView>() {
            @Override
            protected CreatureView newObject() {
                return new CreatureView(game, CREATURE_W, CREATURE_H);
            }
        };
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tableSelected = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tableSelected = false;
            }
        });
    }

    public CreatureView getSelectedCreature() {
        return selectedCreature;
    }

    public void setSelectedCreature(CreatureView selectedCreature) {
        this.selectedCreature = selectedCreature;
    }

    public CreatureView get(int index) {
        return activeCreatures.get(index);
    }

    public int getSelectedCreatureIndex() {
        return activeCreatures.indexOf(selectedCreature, true);
    }

    public int getCreatureIndex(CreatureView c) {
        return activeCreatures.indexOf(c, true);
    }

    public boolean isCreatureSelected() {
        return (selectedCreature != null);
    }

    public int creatureCount() {
        return activeCreatures.size;
    }

    public boolean isSelected() {
        return tableSelected;
    }

    public void addCreature() {
        CreatureView c = creaturePool.obtain();
        activeCreatures.add(c);
        addActor(c);
    }

    public void removeCreature(CreatureView c) {
        int index = activeCreatures.indexOf(c, true);
        removeCreature(index);
    }

    public void removeCreature(int index) {
        CreatureView c = activeCreatures.get(index);
        c.removeBuddies();
        activeCreatures.removeIndex(index);
        creaturePool.free(c);
        removeActor(c);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tableTexture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        super.draw(batch, parentAlpha);
    }

    public void act(float delta) {
        int i = 0;
        for (CreatureView c : new Array.ArrayIterator<>(activeCreatures)) {
            float x = (i - (float) (activeCreatures.size - 1) / 2) * (c.getWidth() + 2) - c.getWidth() / 2 + c.getParent().getOriginX();
            c.addAction(moveTo(x, c.getY(), 0.1f));
            i++;
        }
        super.act(delta);
    }

    public void clearAllFood() {
        for (CreatureView c : new Array.ArrayIterator<>(activeCreatures)) {
            c.clearFood();
        }
    }
}
