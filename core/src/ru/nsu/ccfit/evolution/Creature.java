package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Creature extends Rectangle implements Pool.Poolable {
    private Texture image;

    public Creature(float w, float h) {
        this.width = w;
        this.height = h;
        this.x = 0;
        this.y = 0;
    }

    public void init(EvolutionGame game) {
        this.image = game.assets.get("oldcards/cover.png", Texture.class);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x, y, width,height);
    }

    @Override
    public void reset() {

    }
}
