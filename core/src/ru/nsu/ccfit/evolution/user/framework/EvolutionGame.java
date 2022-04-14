package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Game;
import ru.nsu.ccfit.evolution.server.ServerEmulator;

public class EvolutionGame extends Game {
    private AssetLoader assets;
    private ServerEmulator serverEmulator;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.loadAll();
        serverEmulator = new ServerEmulator();
        this.setScreen(new LoadingScreen(this));
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

    public ServerEmulator getServerEmulator() {
        return serverEmulator;
    }
}
