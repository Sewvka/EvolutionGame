package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class GameSprite extends Rectangle implements Pool.Poolable {
    public Texture image;
    protected float sizeMod;
    protected float rotation;

    public GameSprite(float w, float h) {
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

    public void draw(SpriteBatch batch, int srcW, int srcH) {
        batch.draw(image, x, y, width/2,height/2, width, height, sizeMod, sizeMod, rotation, 0, 0, srcW, srcH, false, false);
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

