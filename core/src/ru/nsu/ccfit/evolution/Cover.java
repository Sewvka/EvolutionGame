package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class Cover extends GameActor {
    public Cover(EvolutionGame game, float w, float h) {
        super(w, h);
        texture = new TextureRegion(game.getLoader().getTexture("cards/cover.png"));
        addListener(new CoverInputListener());
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    private class CoverInputListener extends InputListener {
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
