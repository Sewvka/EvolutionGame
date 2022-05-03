package ru.nsu.ccfit.evolution.user.actors.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DisplayableListener extends InputListener {
    private final Displayable parent;
    private final int button;

    public DisplayableListener(Displayable parent, int button) {
        this.parent = parent;
        this.button = button;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == this.button) {
            if (parent.isDisplayable()) {
                parent.display();
                return true;
            } else if (parent.isDisplayed()) {
                parent.undisplay();
                return true;
            }
        }
        return false;
    }
}
