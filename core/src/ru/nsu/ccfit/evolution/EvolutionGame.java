package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class EvolutionGame extends Game {
    private AssetLoader loader;
    private OrthographicCamera camera;
    private ServerEmulator serverEmulator;
    public static final float WORLD_SIZE_X = 1360;
    public static final float WORLD_SIZE_Y = 720;

    //в конечной версии у программы пользователя не будет доступа к своему номеру пользователя.
    private int playerID;

    @Override
    public void create() {
        //опять же, временно
        playerID = 0;
        loader = new AssetLoader();
        loader.loadAll();
        serverEmulator = new ServerEmulator();
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

    public ServerEmulator getServerEmulator() {
        return serverEmulator;
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

    //в конечной версии у программы пользователя не будет доступа к своему номеру пользователя.
    public int getPlayerID() {
        return playerID;
    }
}
