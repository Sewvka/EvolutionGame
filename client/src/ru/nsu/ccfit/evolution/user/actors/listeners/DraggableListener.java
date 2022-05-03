package ru.nsu.ccfit.evolution.user.actors.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class DraggableListener extends InputListener {
    private final Draggable parent;
    private final int button;

    public DraggableListener(Draggable parent, int button) {
        this.parent = parent;
        this.button = button;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (parent.isDraggable() && button == this.button) {
            parent.startDragging();
            return true;
        }
        return false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        parent.drag(x, y);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (button == this.button) parent.release();
    }
}
