package ru.nsu.ccfit.evolution.user.actors.listeners;

public interface Displayable {
    boolean isDisplayable();

    boolean isDisplayed();

    void display();

    void undisplay();
}
