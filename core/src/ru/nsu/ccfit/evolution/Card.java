package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class Card extends Rectangle implements Pool.Poolable {
    public Texture image;
    private float sizeMod;
    private float rotation;

    public Card(float w, float h) {
        this.width = w;
        this.height = h;
        this.x = 0;
        this.y = 0;
        this.image = null;
        this.sizeMod = 1;
        this.rotation = 0;
    }

    public void init() {
        this.x = 0;
        this.y = 0;
        this.sizeMod = 1;
        this.rotation = 0;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x, y, width/2,height/2, width, height, sizeMod, sizeMod, rotation, 0, 0, 440, 620, false, false);
    }

    @Override
    public void reset() {
        this.sizeMod = 1;
        this.rotation = 0;
    }

    public void setSizeMod(float sizeMod) {
        this.sizeMod = sizeMod;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}

