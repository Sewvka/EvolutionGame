package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.security.InvalidParameterException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class HandView extends Group {
    final float CARD_W = 120;
    final float CARD_H = 168;
    private final Array<CardView> activeCards;
    private final Pool<CardView> cardPool;
    private final EvolutionGame game;
    private final TableManager tables;
    private CardView queuedCard;
    private CreatureView queuedCreature;

    public HandView(final EvolutionGame game, float x, float y, final TableManager tables) {
        setPosition(x, y);
        this.game = game;
        this.tables = tables;
        activeCards = new Array<>(6);
        cardPool = new Pool<CardView>() {
            @Override
            protected CardView newObject() {
                return new CardView(game, tables.getUserTable(), CARD_W, CARD_H);
            }
        };
    }

    public void addCard(int id) {
        if (game.getServerEmulator().requestCardAddition(id, game.getPlayerID())) {
            CardView c = cardPool.obtain();
            c.init(id);
            activeCards.add(c);
            addActor(c);
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
        if (!activeCards.contains(c, true)) throw new InvalidParameterException("Cannot remove card that isn't contained in hand!");
        cardPool.free(c);
        activeCards.removeIndex(activeCards.indexOf(c, true));
        removeActor(c);
    }

    @Override
    public void act(float delta) {
        int i = 0;
        for (CardView c : activeCards) {
            if (c.isInDeck() && !c.isDisplayed()) {
                float x = (i - (float) (activeCards.size - 1) / 2) * c.getWidth() / 2 - c.getWidth() / 2;
                float y = -Math.abs(i - (float) (activeCards.size - 1) / 2) * c.getHeight() / 8;
                c.addAction(parallel(moveTo(x, y, 0.1f), rotateTo(-(i - (float) (activeCards.size - 1) / 2) * 8, 0.1f)));
            }
            i++;
        }
        super.act(delta);
    }

    public TableManager getTables() {
        return tables;
    }

    public int getCardIndex(CardView card) {
        return activeCards.indexOf(card, true);
    }

    public void queueCard(CardView queuedCard, CreatureView queuedCreature) {
        this.queuedCard = queuedCard;
        this.queuedCreature = queuedCreature;
    }

    public void resumeCoopCardPlay(CreatureView targetCreature) {
        if (queuedCard != null && !queuedCreature.equals(targetCreature)) {
            setTouchable(Touchable.enabled);
            queuedCard.resumeCoopCardPlay(targetCreature, queuedCreature);
            queuedCard = null;
        }
    }
}
