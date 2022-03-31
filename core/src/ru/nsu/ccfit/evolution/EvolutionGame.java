package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Game;

public class EvolutionGame extends Game {
    private AssetLoader loader;
    private SpriteDrawer drawer;
    private Controller controller;
    private CommunicationManager communicationManager;
    public static final float WORLD_SIZE_X = 1360;
    public static final float WORLD_SIZE_Y = 720;

    @Override
    public void create() {
        load();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        loader.dispose();
        drawer.dispose();
    }

    private void load() {
        controller = new Controller(this);
        loader = new AssetLoader();
        loader.loadAll();
        drawer = new SpriteDrawer(loader);
        communicationManager = new CommunicationManager(this);
        this.setScreen(new LoadingScreen(this));
    }

    public SpriteDrawer getDrawer() {
        return drawer;
    }

    public AssetLoader getLoader() {
        return loader;
    }

    public Controller getController() {
        return controller;
    }

    public CommunicationManager getCommunicationManager() {
        return communicationManager;
    }
}
