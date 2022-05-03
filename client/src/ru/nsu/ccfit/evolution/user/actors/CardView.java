package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.nsu.ccfit.evolution.model.Cards;
import ru.nsu.ccfit.evolution.user.actors.listeners.*;
import ru.nsu.ccfit.evolution.EvolutionGame;
import ru.nsu.ccfit.evolution.user.framework.SessionStage;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CardView extends GameActor implements Draggable, Hoverable, Displayable {
    private String ability1;
    private String ability2;
    private boolean inDeck;
    private boolean isDisplayed;
    private int id;
    private final EvolutionGame game;
    private final boolean isUser;


    public CardView(EvolutionGame game, float w, float h, boolean isUser) {
        super(null, w, h);
        this.isUser = isUser;
        this.game = game;
        inDeck = false;
        isDisplayed = false;
        addListener(new DraggableListener(this, Input.Buttons.LEFT));
        addListener(new HoverableListener(this));
        addListener(new DisplayableListener(this, Input.Buttons.RIGHT));
    }

    public boolean isInDeck() {
        return inDeck;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isDisplayable() {
        HandView parentHand = (HandView) getTrueParent();
        return (isUser && inDeck && !parentHand.isCardDisplayed() && !isDisplayed);
    }

    @Override
    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public void display() {
        isDisplayed = true;
        SessionStage sessionStage = (SessionStage) getStage();
        sessionStage.getSessionScreen().moveActorToFront(this);
        addAction(parallel(scaleTo(3, 3, 0.2f), moveTo((getStage().getWidth() - getWidth()) / 2, (getStage().getHeight() - getHeight()) / 2, 0.2f), rotateTo(0, 0.2f)));
    }

    @Override
    public void undisplay() {
        isDisplayed = false;
        putInDeck();
        addAction(scaleTo(1, 1, 0.2f));
    }

    @Override
    public boolean isHoverable() {
        return (inDeck && !isDisplayed && isUser);
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
    public boolean isDraggable() {
        HandView parentHand = (HandView) getTrueParent();
        return (!isDisplayed && isUser && inDeck && !parentHand.isCardDisplayed());
    }

    @Override
    public void startDragging() {
        inDeck = false;
        SessionStage sessionStage = (SessionStage) getStage();
        sessionStage.getSessionScreen().moveActorToFront(this);
    }

    @Override
    public void drag(float x, float y) {
        Vector2 v = localToParentCoordinates(new Vector2(x, y));
        addAction(parallel(rotateTo(0, 0.1f), moveTo(v.x - getWidth() / 2, v.y - getHeight() / 2, 0.1f)));
    }

    @Override
    public void release() {
        SessionStage sessionStage = (SessionStage) getTrueParent().getStage();
        sessionStage.getSessionScreen().playCard(this);
    }

    public void putInDeck() {
        inDeck = true;
        SessionStage sessionStage = (SessionStage) getTrueParent().getStage();
        sessionStage.getSessionScreen().moveActorToBack(this);
    }

    public void init(int id) {
        String cardname = Cards.getName(id);
        setTexture(new TextureRegion(game.getAssets().getCardTexture(cardname)));
        inDeck = true;
        this.id = id;

        ability1 = Cards.getAbilityFromName(cardname, true);
        ability2 = Cards.getAbilityFromName(cardname, false);
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
        this.isDisplayed = false;
        this.ability1 = null;
        this.ability2 = null;
    }
}
