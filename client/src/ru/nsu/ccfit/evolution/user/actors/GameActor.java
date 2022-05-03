package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool;

public class GameActor extends Group implements Pool.Poolable {
    private TextureRegion texture;
    private Group trueParent;

    public GameActor() {
        this.texture = null;
    }

    public GameActor(TextureRegion texture, float w, float h) {
        this.texture = texture;
        setSize(w, h);
        setOrigin(w / 2, h / 2);
        setPosition(0, 0);
        updateBounds();
    }

    public void setTrueParent(Group trueParent) {
        this.trueParent = trueParent;
    }

    public Group getTrueParent() {
        return trueParent;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (texture != null) {
            batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        super.draw(batch, parentAlpha);
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

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}

