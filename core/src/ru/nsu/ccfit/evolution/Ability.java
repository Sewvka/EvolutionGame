package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Ability extends GameActor {
    private Ability buddy;
    private final String name;
    private final EvolutionGame game;

    public Ability(EvolutionGame game, float w, float h, int cardID, boolean firstAbility, Ability buddy) {
        super(w, h);
        this.game = game;
        this.buddy = buddy;
        texture = new TextureRegion(game.getLoader().getTexture("cards/" + Cards.getName(cardID) + ".png"));
        name = Cards.getAbilityFromName(Cards.getName(cardID), firstAbility);
        if (!firstAbility) texture.flip(true, true);
        addListener(new AbilityInputListener());
    }

    public String getName() {
        return name;
    }

    public void setBuddy(Ability buddy) {
        this.buddy = buddy;
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
        if (buddy != null) buddy.addAction(scaleTo(1, 1, 0.2f));
    }

    private void activate() {
        GameScreen screen = (GameScreen) game.getScreen();
        CreatureView parentCreature = (CreatureView) getParent();

        if (name.equals("carnivorous")) {
            screen.getTables().queueAbility(this, parentCreature);
        }
    }

    public void resumeActivation(CreatureView targetCreature, CreatureView queuedCreature) {
        GameScreen screen = (GameScreen) game.getScreen();
        TableManager tableManager = screen.getTables();
        TableView targetTable = (TableView) targetCreature.getParent();
        TableView parentTable = (TableView) queuedCreature.getParent();
        if (name.equals("carnivorous")) {
            if (game.getServerEmulator().requestPredation(parentTable.getCreatureIndex(queuedCreature), targetTable.getCreatureIndex(targetCreature), tableManager.indexOf(parentTable), tableManager.indexOf(targetTable))) {
                targetTable.removeCreature(targetCreature);
                queuedCreature.addFood(new FoodToken(game, FoodTray.TOKEN_SIZE));
                queuedCreature.addFood(new FoodToken(game, FoodTray.TOKEN_SIZE));
            } else {
                screen.getTables().cancelAbilityUsage();
            }
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
            GameScreen screen = (GameScreen) game.getScreen();
            if (button == Input.Buttons.LEFT && !screen.getTables().isAbilityQueued()) {
                activate();
                return true;
            }
            if (button == Input.Buttons.RIGHT && screen.getTables().isAbilityQueued()) {
                screen.getTables().cancelAbilityUsage();
                return true;
            }
            return false;
        }
    }
}
