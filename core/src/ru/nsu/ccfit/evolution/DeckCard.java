package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeckCard extends Card {
    private static final Map<Integer, String> cardIDs;
    static {
        HashMap<Integer, String> aMap = new HashMap<>();
        aMap.put(1, "burrower-fat");
        aMap.put(2, "large-fat");
        cardIDs = Collections.unmodifiableMap(aMap);
    }

    public boolean inDeck;

    public DeckCard(float w, float h) {
        super(w, h);
        this.inDeck = false;
    }

    public void init(EvolutionGame game, Integer id) {
        super.init();
        this.image = game.assets.get("cards/" + cardIDs.get(id) + ".png", Texture.class);
        this.inDeck = true;
    }

    @Override
    public void reset() {
        super.reset();
        this.inDeck = false;
    }
}
