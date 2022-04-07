package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Ability extends GameActor {
    private Ability buddy;
    private final String name;
    //private final Array<FoodToken> food;

    public Ability(EvolutionGame game, float w, float h, int cardID, boolean firstAbility, Ability buddy) {
        super(w, h);
        this.buddy = buddy;
        texture = new TextureRegion(game.getLoader().getTexture("cards/"+Cards.getName(cardID)+".png"));
        name = Cards.getAbilityFromName(Cards.getName(cardID), firstAbility);
        if (!firstAbility) texture.flip(true, true);
        addListener(new AbilityInputListener());
    }

    public String getName() {
        return name;
    }

    public void setBuddy(Ability buddy) {
        this.buddy = buddy;
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1, 1, 0.2f));
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
