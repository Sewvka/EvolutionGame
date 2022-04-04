package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class Ability extends GameActor {
    public Ability(EvolutionGame game, float w, float h, int cardID, boolean firstAbility) {
        super(w, h);
        texture = new TextureRegion(game.getLoader().getTexture("cards/"+Cards.getName(cardID)+".png"));
        if (!firstAbility) texture.flip(true, true);
        addListener(new AbilityInputListener());
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    public void updatePosition(int index, int total) {
        float y = (total-index)*getHeight()/5;
        addAction(moveTo(getX(), y, 0.1f));
    }

    private class AbilityInputListener extends InputListener {
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
