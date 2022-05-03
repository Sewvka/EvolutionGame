package ru.nsu.ccfit.evolution.user.actors.listeners;

public interface Draggable {
    boolean isDraggable();

    void startDragging();

    void drag(float x, float y);

    void release();
}
