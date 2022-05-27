package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.nsu.ccfit.evolution.user.actors.listeners.Draggable;
import ru.nsu.ccfit.evolution.user.actors.listeners.DraggableListener;
import ru.nsu.ccfit.evolution.user.actors.listeners.Hoverable;
import ru.nsu.ccfit.evolution.user.actors.listeners.HoverableListener;
import ru.nsu.ccfit.evolution.user.framework.SessionStage;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class FoodToken extends GameActor implements Hoverable, Draggable {
    private final boolean isActive;

    public FoodToken(EvolutionGame game, int size, boolean isActive, String color) {
        super(null, size, size);
        TextureRegion texture;
        switch (color) {
            case "yellow":
                texture = new TextureRegion(game.getAssets().getTexture("food/food_yellow.png"));
                break;
            case "blue":
                texture = new TextureRegion(game.getAssets().getTexture("food/food_blue.png"));
            default:
                texture = new TextureRegion(game.getAssets().getTexture("food/food_red.png"));
        }
        setTexture(texture);
        addListener(new HoverableListener(this));
        addListener(new DraggableListener(this, Input.Buttons.LEFT));
        this.isActive = isActive;
    }

    @Override
    public boolean isDraggable() {
        return isActive;
    }

    @Override
    public void startDragging() {
        SessionStage sessionStage = (SessionStage) getStage();
        sessionStage.getSessionScreen().moveActorToFront(this);
    }

    @Override
    public void drag(float x, float y) {
        Vector2 v = localToParentCoordinates(new Vector2(x, y));
        addAction(moveTo(v.x - getWidth() / 2, v.y - getHeight() / 2, 0.1f));
    }

    @Override
    public void release() {
        SessionStage sessionStage = (SessionStage) getTrueParent().getStage();
        sessionStage.getSessionScreen().feedToken(this);
    }

    @Override
    public boolean isHoverable() {
        return isActive;
    }

    @Override
    public void hover() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    @Override
    public void unhover() {
        addAction(scaleTo(1, 1, 0.2f));
    }
}
