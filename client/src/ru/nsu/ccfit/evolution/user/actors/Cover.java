package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.nsu.ccfit.evolution.user.actors.listeners.Hoverable;
import ru.nsu.ccfit.evolution.user.actors.listeners.HoverableListener;
import ru.nsu.ccfit.evolution.EvolutionGame;
import ru.nsu.ccfit.evolution.user.framework.SessionScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class Cover extends GameActor implements Hoverable {
    EvolutionGame game;

    public Cover(EvolutionGame game, float w, float h) {
        super(new TextureRegion(game.getAssets().getTexture("cards/cover.png")), w, h);
        this.game = game;
        addListener(new HoverableListener(this));
        addListener(new CoverInputListener());
    }

    @Override
    public void hover() {
        addAction(scaleTo(1.05f, 1.05f, 0.2f));
    }

    @Override
    public void unhover() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    @Override
    public boolean isHoverable() {
        return true;
    }

    private class CoverInputListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (button == Input.Buttons.LEFT) {
                SessionScreen s = (SessionScreen) game.getScreen();
                s.creatureClicked((CreatureView) getParent());
                return true;
            } else return false;
        }
    }
}
