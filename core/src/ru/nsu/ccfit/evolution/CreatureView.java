package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.Objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CreatureView extends Group implements Pool.Poolable {
    private short abilities;
    private final EvolutionGame game;
    private final Cover cover;

    public CreatureView(float w, float h, EvolutionGame game) {
        setSize(w, h);
        this.game = game;
        setOrigin(w / 2, h / 2);
        setPosition(0, 0);
        updateBounds();
        this.abilities = 0;
        cover = new Cover(game, w, h);
        addActor(cover);
        addListener(new CreatureInputListener(this));
        //addCaptureListener(new CreatureInputListener(this));
    }

    public void select() {
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(this);
    }

    public void deselect() {
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(null);
    }

    public Ability addAbility(int cardID, boolean firstAbility) {
        Ability ability = new Ability(game, getWidth(), getHeight(), cardID, firstAbility, null);
        addActorBefore(cover, ability);
        return ability;
    }

    public void addAbility(Ability ability) {
        addActorBefore(cover, ability);
    }

    public boolean hasAbility(String ability) {
        return (abilities & Abilities.get(ability)) == 0;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }

    public void updateTablePosition(int count, int total) {
        float x = (count - (float) (total - 1) / 2) * (getWidth() + 20) - getWidth() / 2 + getParent().getOriginX();
        addAction(moveTo(x, getY(), 0.1f));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateBounds();
        SnapshotArray<Actor> children = getChildren();
        int i = 0;
        for (Actor a : children) {
            if (i != children.size - 1) {
                Ability ability = (Ability) a;
                ability.updatePosition(i, children.size - 1);
            }
            i++;
        }
    }

    @Override
    public void reset() {
        setScale(1);
        setRotation(0);
        this.abilities = 0;
    }

    private void updateBounds() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    private class CreatureInputListener extends InputListener {
        CreatureView parentCreature;

        private CreatureInputListener(CreatureView parent) {
            this.parentCreature = parent;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            select();
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            deselect();
        }
    }
}
