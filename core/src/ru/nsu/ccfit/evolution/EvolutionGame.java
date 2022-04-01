package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class EvolutionGame extends Game {
    private AssetLoader loader;
    private OrthographicCamera camera;
    private Skin skin;
    private CommunicationManager communicationManager;
    public static final float WORLD_SIZE_X = 1360;
    public static final float WORLD_SIZE_Y = 720;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("styles/uiskin.json"), new TextureAtlas(Gdx.files.internal("styles/uiskin.atlas")));
        loader = new AssetLoader();
        loader.loadAll();
        communicationManager = new CommunicationManager(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, EvolutionGame.WORLD_SIZE_X, EvolutionGame.WORLD_SIZE_Y);
        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        loader.dispose();
    }

    public AssetLoader getLoader() {
        return loader;
    }

    public CommunicationManager getCommunicationManager() {
        return communicationManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public Vector2 getMouseCoords() {
        Vector3 mousePos = new Vector3();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        return (new Vector2(mousePos.x, mousePos.y));
    }

    public Skin getSkin() {
        return skin;
    }
}
