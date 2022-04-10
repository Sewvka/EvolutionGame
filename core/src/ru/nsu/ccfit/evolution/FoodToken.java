package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class FoodToken extends GameActor {

    public FoodToken(EvolutionGame game, int size) {
        super(size, size);
        texture = new TextureRegion(game.getLoader().getTexture("food/food_red.png"));
        addListener(new FoodEventListener(this));
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    public void placeInTray() {
        FoodTray parentTray = (FoodTray) getParent();
        clearActions();
        if (parentTray != null) {
            parentTray.updatePositions();
        }
    }

    private class FoodEventListener extends InputListener {
        private final FoodToken parentToken;

        public FoodEventListener(FoodToken parentToken) {
            this.parentToken = parentToken;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            select();
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            deselect();
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Vector2 v = localToParentCoordinates(new Vector2(x, y));
            addAction(moveTo(v.x - getWidth() / 2, v.y - getHeight() / 2, 0.1f));
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            FoodTray parentTray = (FoodTray) getParent();

            if (parentTray.getUserTable().isCreatureSelected()) {
                parentTray.feed(parentToken);
                parentTray.updatePositions();
            }
            else {
                placeInTray();
            }
        }
    }
}
