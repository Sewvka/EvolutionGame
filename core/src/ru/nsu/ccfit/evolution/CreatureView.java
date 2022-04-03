package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.Objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CreatureView extends GameActor {
    private short abilities;

    public CreatureView(float w, float h, EvolutionGame game) {
        super(w, h);
        this.abilities = 0;
        this.texture = new TextureRegion(game.getLoader().getTexture("cards/cover.png"));
        addListener(new CreatureInputListener(this));
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(this);
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(null);
    }

    @Override
    public void reset() {
        super.reset();
        this.abilities = 0;
    }

    public void addAbility(String ability) {
        //временное условие, до того, как добавлю реализацию особых свойств
        if (!Objects.equals(ability, "fat")) abilities |= Abilities.get(ability);
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
