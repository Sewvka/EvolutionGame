package ru.nsu.ccfit.evolution.user.actors.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class HoverableListener extends InputListener {
    private final Hoverable parent;

    public HoverableListener(Hoverable parent) {
        this.parent = parent;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if (parent.isHoverable()) {
            parent.hover();
        }
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if (parent.isHoverable()) {
            parent.unhover();
        }
    }
}
