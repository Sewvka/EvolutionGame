package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CardManager {
    private final Array<Card> activeCards;

    private final Pool<Card> cardPool;
    private final EvolutionGame game;

    private boolean cardSelected;

    public CardManager(EvolutionGame game) {
        this.game = game;
        activeCards = new Array<>();
        cardPool = new Pool<Card>() {
            @Override
            protected Card newObject() {
                return new Card();
            }
        };
        cardSelected = false;
    }

    public Card newCard(int id, int x, int y) {
        Card c = cardPool.obtain();
        activeCards.add(c);
        c.init(game, id, x, y);
        return c;
    }

    public void drawAll(SpriteBatch batch) {
        for (Card c : activeCards) {
            c.draw(batch);
        }
    }

    public void updateMovement(Vector2 mousepos) {
        for (Card c : activeCards) {
            if (c.contains(mousepos) && !cardSelected) {
                c.setSizeMod(1.2f);
                cardSelected = true;
            }
            else {
                c.setSizeMod(1);
                cardSelected = false;
            }
        }
    }
}
