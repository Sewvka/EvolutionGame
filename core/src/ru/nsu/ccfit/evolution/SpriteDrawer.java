package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class SpriteDrawer {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final AssetLoader assets;
    private final ArrayList<Drawable> drawables;

    public SpriteDrawer(AssetLoader assets) {
        this.assets = assets;
        drawables = new ArrayList<>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, EvolutionGame.WORLD_SIZE_X, EvolutionGame.WORLD_SIZE_Y);
        batch = new SpriteBatch();
    }

    public void addDrawable(Drawable d) {
        drawables.add(d);
    }

    public Camera getCamera() {
        return camera;
    }

    public void dispose() {
        batch.dispose();
    }

    public void begin() {
        ScreenUtils.clear(220, 220, 220, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void drawLoading() {
        assets.getFont().draw(batch, "Loading..." + (assets.progress() * 100) + "%", 100, 150);
    }

    public void drawGame() {
        for (Drawable d: drawables) d.draw(batch);
    }
}
