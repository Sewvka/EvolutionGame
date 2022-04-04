package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CardView extends GameActor {
    private String ability1;
    private String ability2;
    private boolean inDeck;
    private boolean isDisplayed;
    private int id;
    private boolean queuedAbility;
    private final EvolutionGame game;

    public boolean isInDeck() {
        return inDeck;
    }

    public void putInDeck() {
        this.inDeck = true;
    }

    public void takeFromDeck() {
        this.inDeck = false;
    }

    public CardView(EvolutionGame game, float w, float h) {
        super(w, h);
        this.game = game;
        inDeck = false;
        isDisplayed = false;
        final CardView t = this;
        addListener(new CardInputListener(this));
    }

    private void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    private void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    private void display() {
        toFront();
        addAction(parallel(scaleTo(3, 3, 0.2f), moveTo(-getWidth() / 2, getStage().getHeight() / 2 - getParent().getY() - getHeight() / 2, 0.2f), rotateTo(0, 0.2f)));
        isDisplayed = true;
    }

    private void undisplay() {
        HandView parentHand = (HandView) getParent();
        setZIndex(parentHand.getCardIndex(this));
        addAction(scaleTo(1, 1, 0.2f));
        isDisplayed = false;
    }

    public void init(Integer id) {
        String cardname = Cards.getName(id);
        texture = new TextureRegion(game.getLoader().getCardTexture(cardname));
        inDeck = true;
        this.id = id;

        ability1 = Cards.getAbilityFromName(cardname, true);
        ability2 = Cards.getAbilityFromName(cardname, false);
    }

    public void move(float x, float y, float t) {
        addAction(parallel(rotateTo(0, t), moveTo(x, y, t)));
    }

    public String getAbility1() {
        return ability1;
    }

    public String getAbility2() {
        return ability2;
    }

    @Override
    public void reset() {
        super.reset();
        this.inDeck = false;
        this.ability1 = null;
        this.ability2 = null;
    }

    public void updateDeckPosition(int count, int total) {
        if (!isDisplayed) {
            float x = (count - (float) (total - 1) / 2) * getWidth() / 2 - getWidth() / 2;
            float y = -Math.abs(count - (float) (total - 1) / 2) * getHeight() / 8;
            addAction(parallel(moveTo(x, y, 0.1f), rotateTo(-(count - (float) (total - 1) / 2) * 8, 0.1f)));
        }
    }

    private void playAbility(boolean firstAbility, int selectedCreature) {
        HandView parentHand = (HandView) getParent();

        String ability = firstAbility ? ability1 : ability2;
        if (Abilities.isCooperative(ability)) {
            if (parentHand.getTable().creatureCount() > 1) {
                parentHand.setTouchable(Touchable.disabled);
                queuedAbility = firstAbility;
                parentHand.queueCard(this, parentHand.getTable().get(selectedCreature));
                return;
            } else {
                putInDeck();
                return;
            }
        }

        if (parentHand.getTable().addAbility(id, firstAbility, parentHand.getCardIndex(this), selectedCreature)) {
            parentHand.removeCard(this);
        } else putInDeck();
    }

    public void resumeCoopCardPlay(CreatureView targetCreature1, CreatureView targetCreature2) {
        HandView parentHand = (HandView) getParent();
        int selectedCreature1 = parentHand.getTable().getCreatureIndex(targetCreature1);
        int selectedCreature2 = parentHand.getTable().getCreatureIndex(targetCreature2);
        if (parentHand.getTable().addCoopAbility(id, queuedAbility, parentHand.getCardIndex(this), selectedCreature1, selectedCreature2)) {
            parentHand.removeCard(this);
        } else putInDeck();
    }

    private void playCreature() {
        HandView parentHand = (HandView) getParent();
        if (parentHand.getTable().addCreature(parentHand.getCardIndex(this))) {
            parentHand.removeCard(this);
        } else putInDeck();
    }

    private void showAbilityDialog(final int selectedCreature) {
        Skin skin = new Skin(Gdx.files.internal("styles/uiskin.json"));
        Dialog dialog = new Dialog("Ability Selection", skin) {
            @Override
            protected void result(Object object) {
                switch ((Integer) object) {
                    case 1:
                        playAbility(true, selectedCreature);
                        getParent().removeActor(this);
                        break;
                    case 2:
                        playAbility(false, selectedCreature);
                        getParent().removeActor(this);
                        break;
                    case 3:
                        putInDeck();
                        getParent().removeActor(this);
                }
            }
        };
        GameScreen screen = (GameScreen) game.getScreen();

        dialog.text("Select which ability to play");
        dialog.button(ability1, 1);
        dialog.button(ability2, 2);
        dialog.button("Cancel", 3);
        dialog.show(screen.getUiStage(), null);
        dialog.setPosition((getStage().getWidth() - dialog.getWidth()) / 2, (getStage().getHeight() - dialog.getHeight()) / 2);
        dialog.setMovable(false);
    }

    private class CardInputListener extends InputListener {
        CardView parentCard;

        private CardInputListener(CardView parent) {
            this.parentCard = parent;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            if (inDeck && !isDisplayed) select();
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            if (inDeck && !isDisplayed) deselect();
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Vector2 v = localToParentCoordinates(new Vector2(x, y));
            move(v.x - getWidth() / 2, v.y - getHeight() / 2, 0.1f);
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (button == Input.Buttons.LEFT) {
                if (isDisplayed) return false;
                takeFromDeck();
                return true;
            } else if (button == Input.Buttons.RIGHT) {
                if (inDeck && !isDisplayed) display();
                else if (isDisplayed) undisplay();
                return false;
            } else return false;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (button == Input.Buttons.RIGHT) return;

            HandView parentHand = (HandView) getParent();
            int selectedCreature = parentHand.getTable().getSelectedCreatureIndex();
            if (parentHand.getTable().isCreatureSelected()) {
                if (!ability1.equals(ability2)) showAbilityDialog(selectedCreature);
                else playAbility(true, selectedCreature);
            } else if (parentHand.getTable().isSelected()) {
                playCreature();
            } else {
                putInDeck();
            }
        }
    }
}
