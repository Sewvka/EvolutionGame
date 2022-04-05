package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class Ability extends GameActor {
    private Ability buddy;
    private boolean chosen;
    public Ability(EvolutionGame game, float w, float h, int cardID, boolean firstAbility, Ability buddy) {
        super(w, h);
        this.buddy = buddy;
        chosen = false;
        texture = new TextureRegion(game.getLoader().getTexture("cards/"+Cards.getName(cardID)+".png"));
        if (!firstAbility) texture.flip(true, true);
        addListener(new AbilityInputListener());
    }

    public void setBuddy(Ability buddy) {
        this.buddy = buddy;
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));

        if (buddy != null) buddy.addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        Color color = getColor();
        if (chosen){
            batch.setColor(Color.BLUE);
        } else {
            batch.setColor(color.r, color.g, color.b, parentAlpha);
        }
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1, 1, 0.2f));
    }

    public void updatePosition(int index, int total) {
        float y = (total-index)*getHeight()/5;
        addAction(moveTo(getX(), y, 0.1f));
    }

    private class AbilityInputListener extends InputListener {
        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            chosen = true;
            select();
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            chosen = false;
            deselect();
        }
    }
}
