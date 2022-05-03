package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Game;
public class EvolutionGame extends Game {
    private AssetLoader assets;
    private int playerID;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.loadAll();
        playerID = 0;
        this.setScreen(new LoadingScreen(this));
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    public AssetLoader getAssets() {
        return assets;
    }
}
