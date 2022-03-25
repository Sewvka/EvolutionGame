package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card extends Rectangle implements Pool.Poolable {
    private static Map<Integer, String> cardIDs;
    static {
        HashMap<Integer, String> aMap = new HashMap<>();
        aMap.put(1, "burrower-fat");
        aMap.put(2, "large-fat");
        cardIDs = Collections.unmodifiableMap(aMap);
    }

    private Texture image;
    private float sizeMod;

    public Card() {
        this.width = 200;
        this.height = 280;
        this.x = 0;
        this.y = 0;
        this.image = null;
        this.sizeMod = 1;
    }

    public void init(EvolutionGame game, Integer id, float x, float y) {
        this.image = game.assets.get("cards/" + cardIDs.get(id) + ".png", Texture.class);
        this.x = x;
        this.y = y;
        this.sizeMod = 1;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x-(sizeMod-1)/2*width, y-(sizeMod-1)/2*height, width*sizeMod, height*sizeMod);
    }

    @Override
    public void reset() {
        this.sizeMod = 1;
    }

    public void setSizeMod(float sizeMod) {
        this.sizeMod = sizeMod;
    }
}
