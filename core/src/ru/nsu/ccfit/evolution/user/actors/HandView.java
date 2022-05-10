package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.security.InvalidParameterException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class HandView extends Group {
    final float CARD_W = 120;
    final float CARD_H = 168;
    private final Array<CardView> activeCards;
    private final Pool<CardView> cardPool;

    public HandView(final EvolutionGame game, final boolean isUser, float x, float y) {
        setPosition(x, y);
        activeCards = new Array<>();
        cardPool = new Pool<CardView>() {
            @Override
            protected CardView newObject() {
                return new CardView(game, CARD_W, CARD_H, isUser);
            }
        };
    }

    public void addCard(int id) {
        CardView c = cardPool.obtain();
        c.init(id);
        activeCards.add(c);
        addActor(c);
        c.setTrueParent(this);
    }

    public void addAll(Array<Integer> ids) {
        for (Integer id : new Array.ArrayIterator<>(ids)) {
            addCard(id);
        }
    }

    public void removeCardAt(int index) {
        if (index < 0) throw new InvalidParameterException("Card index must be positive");
        CardView c = activeCards.get(index);
        cardPool.free(c);
        activeCards.removeIndex(index);
        removeActor(c);
    }

    public void removeCard(CardView c) {
        if (!activeCards.contains(c, true))
            throw new InvalidParameterException("Cannot remove card that isn't contained in hand!");
        cardPool.free(c);
        activeCards.removeIndex(activeCards.indexOf(c, true));
        c.remove();
    }

    @Override
    public void act(float delta) {
        int i = 0;
        SnapshotArray<Actor> children = getChildren();
        for (Actor a : children) {
            CardView c = (CardView) a;
            if (c.isInDeck() && !c.isDisplayed()) {
                float x = (i - (float) (activeCards.size - 1) / 2) * c.getWidth() / 2 - c.getWidth() / 2;
                float y = -Math.abs(i - (float) (activeCards.size - 1) / 2) * c.getHeight() / 8;
                c.addAction(parallel(moveTo(x, y, 0.1f), rotateTo(-(i - (float) (activeCards.size - 1) / 2) * 8, 0.1f)));
            }
            i++;
        }
        super.act(delta);
    }

    public int getCardIndex(CardView card) {
        return activeCards.indexOf(card, true);
    }

    public boolean noCardDisplayed() {
        for (CardView c : new Array.ArrayIterator<>(activeCards)) {
            if (c.isDisplayed()) return false;
        }
        return true;
    }
}
