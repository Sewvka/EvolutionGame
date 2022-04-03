package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

public class GameActor extends Actor implements Pool.Poolable {
    protected TextureRegion texture;

    public GameActor(float w, float h) {
        setSize(w, h);
        setOrigin(w / 2, h / 2);
        setPosition(0, 0);
        updateBounds();
        this.texture = null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateBounds();
    }

    @Override
    public void reset() {
        setScale(1);
        setRotation(0);
    }

    private void updateBounds() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
}

