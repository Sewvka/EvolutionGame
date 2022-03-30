package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class SpriteDrawer {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final AssetLoader assets;

    public SpriteDrawer(AssetLoader assets) {
        this.assets = assets;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, EvolutionGame.WORLD_SIZE_X, EvolutionGame.WORLD_SIZE_Y);
        batch = new SpriteBatch();
    }

    public void dispose() {
        batch.dispose();
    }

    public void unproject(Vector3 v) {
        camera.unproject(v);
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
        /*batch.draw(table.tableTexture, table.x, table.y, table.width, table.height);
        table.drawAll(batch);
        hand.drawAll(batch);*/
    }
}
