package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Pool;
import ru.nsu.ccfit.evolution.server.CreatureModel;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class TableView extends Group {
    static final float CREATURE_W = 80;
    static final float CREATURE_H = 112;
    private final Map<Integer, CreatureView> creatures;
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
        creatures = new HashMap<>();
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
        return creatures.get(index);
    }

    public boolean isCreatureSelected() {
        return (selectedCreature != null);
    }

    public Map<Integer, CreatureView> getCreatures() {
        return creatures;
    }

    public int creatureCount() {
        return creatures.size();
    }

    public boolean isSelected() {
        return tableSelected;
    }

    public void addCreature(int id) {
        CreatureView c = creaturePool.obtain();
        c.setID(id);
        creatures.put(id, c);
        addActor(c);
    }

    public void removeCreature(CreatureView c) {
        removeCreature(c.getID());
    }

    public void removeCreature(int id) {
        CreatureView c = creatures.get(id);
        c.removeBuddies();
        creatures.remove(id);
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
        for (CreatureView c : creatures.values()) {
            float x = (i - (float) (creatures.size() - 1) / 2) * (c.getWidth() + 2) - c.getWidth() / 2 + c.getParent().getOriginX();
            c.addAction(moveTo(x, c.getY(), 0.1f));
            i++;
        }
        super.act(delta);
    }

    public void clearAllFood() {
        for (CreatureView c : creatures.values()) {
            c.clearFood();
        }
    }
}
