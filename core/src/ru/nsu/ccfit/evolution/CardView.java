package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CardView extends GameActor {
    private String ability1;
    private String ability2;
    private boolean inDeck;
    private boolean isDisplayed;

    public boolean isInDeck() {
        return inDeck;
    }

    public void putInDeck() {
        this.inDeck = true;
    }

    public void takeFromDeck() {
        this.inDeck = false;
    }

    public CardView(float w, float h) {
        super(w, h);
        inDeck = false;
        isDisplayed = false;
        final CardView t = this;
        addListener(new CardInputListener(this));
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    public void display() {
        toFront();
        addAction(parallel(scaleTo(3, 3, 0.2f), moveTo(-getWidth()/2, getStage().getHeight()/2 - getParent().getY() - getHeight()/2, 0.2f), rotateTo(0, 0.2f)));
        isDisplayed = true;
    }

    public void undisplay() {
        HandView parentHand = (HandView) getParent();
        setZIndex(parentHand.getCardIndex(this));
        addAction(scaleTo(1, 1, 0.2f));
        isDisplayed = false;
    }

    public void init(EvolutionGame game, Integer id) {
        String cardname = Cards.getName(id);
        this.texture = new TextureRegion(game.getLoader().getCardTexture(cardname));
        this.inDeck = true;

        //несколько костыльно, но делим название ф1айла на название свойств
        int i = cardname.indexOf('-');
        if (i >= 0) {
            ability1 = cardname.substring(0, cardname.indexOf('-'));
        } else ability1 = cardname;
        this.ability2 = cardname.substring(cardname.indexOf('-') + 1);
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

    private void playAbility(String ability, int selectedCreature) {
        HandView parentHand = (HandView) getParent();
        if (parentHand.getTable().addAbility(ability, parentHand.getCardIndex(this), selectedCreature)) {
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
                        playAbility(ability1, selectedCreature);
                        getParent().removeActor(this);
                        break;
                    case 2:
                        playAbility(ability2, selectedCreature);
                        getParent().removeActor(this);
                        break;
                    case 3:
                        putInDeck();
                        getParent().removeActor(this);
                }
            }
        };
        dialog.text("Select which ability to play");
        dialog.button(ability1, 1);
        dialog.button(ability2, 2);
        dialog.button("Cancel", 3);
        dialog.show(getParent().getStage(), null);
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
            }
            else return false;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (button == Input.Buttons.RIGHT) return;

            HandView parentHand = (HandView) getParent();
            int selectedCreature = parentHand.getTable().getSelectedCreatureIndex();
            if (parentHand.getTable().isCreatureSelected()) {
                if (!ability1.equals(ability2)) showAbilityDialog(selectedCreature);
                else playAbility(ability1, selectedCreature);
            } else if (parentHand.getTable().isSelected()) {
                playCreature();
            } else {
                putInDeck();
            }
        }
    }
}
