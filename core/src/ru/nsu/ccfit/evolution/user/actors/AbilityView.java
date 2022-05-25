package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.nsu.ccfit.evolution.common.Cards;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;
import ru.nsu.ccfit.evolution.user.framework.SessionScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class AbilityView extends GameActor {
    private AbilityView buddy;
    private final String name;
    private final EvolutionGame game;

    public AbilityView(EvolutionGame game, float w, float h, String name) {
        super(null, w, h);
        setTexture(new TextureRegion(game.getAssets().getAbilityTexture(name)));
        this.game = game;
        buddy = null;
        this.name = name;
        addListener(new AbilityInputListener());
    }

    public String getName() {
        return name;
    }

    public void setBuddy(AbilityView buddy) {
        this.buddy = buddy;
    }

    public AbilityView getBuddy() {
        return buddy;
    }

    public void select() {
        addAction(scaleTo(1.05f, 1.05f, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1.05f, 1.05f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1, 1, 0.2f));
    }

    private boolean activate() {
        SessionScreen screen = (SessionScreen) game.getScreen();
        return screen.activateAbility(this);
    }

    public void resumeActivation(CreatureView targetCreature) {
        SessionScreen screen = (SessionScreen) game.getScreen();
        TableView targetTable = (TableView) targetCreature.getParent();
        CreatureView parentCreature = (CreatureView) getParent();
        switch (name) {
            case "carnivorous":
                targetTable.removeCreature(targetCreature);
                parentCreature.addFood();
                parentCreature.addFood();
                screen.cancelAbilityUsage();
                break;
            case "piracy":
                parentCreature.addFood();
                targetCreature.removeFood();
                screen.cancelAbilityUsage();
        }
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

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            SessionScreen screen = (SessionScreen) game.getScreen();
            if (button == Input.Buttons.LEFT && !screen.isAbilityQueued()) {
                return activate();
            }
            if (button == Input.Buttons.RIGHT && screen.isAbilityQueued()) {
                screen.cancelAbilityUsage();
                return true;
            }
            return false;
        }
    }
}