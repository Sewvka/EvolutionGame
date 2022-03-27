package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card extends Rectangle implements Pool.Poolable {
    private static final Map<Integer, String> cardIDs;
    static {
        HashMap<Integer, String> aMap = new HashMap<>();
        aMap.put(1, "burrower-fat");
        aMap.put(2, "large-fat");
        cardIDs = Collections.unmodifiableMap(aMap);
    }

    private Texture image;
    private float sizeMod;
    float rotation;
    boolean inDeck;

    public Card(float w, float h) {
        this.width = w;
        this.height = h;
        this.x = 0;
        this.y = 0;
        this.image = null;
        this.sizeMod = 1;
        this.rotation = 0;
        this.inDeck = false;
    }

    public void init(EvolutionGame game, Integer id, float x, float y) {
        this.image = game.assets.get("cards/" + cardIDs.get(id) + ".png", Texture.class);
        this.x = x;
        this.y = y;
        this.sizeMod = 1;
        this.rotation = 0;
        this.inDeck = true;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x, y, width/2,height/2, width, height, sizeMod, sizeMod, rotation, 0, 0, 743, 1038, false, false);
    }

    @Override
    public void reset() {
        this.sizeMod = 1;
        this.rotation = 0;
        this.inDeck = false;
    }

    public void setSizeMod(float sizeMod) {
        this.sizeMod = sizeMod;
    }
}
