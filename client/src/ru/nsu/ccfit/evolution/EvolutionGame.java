package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Game;
import ru.nsu.ccfit.evolution.network.MyClient;
import ru.nsu.ccfit.evolution.server.ServerEmulator;
import ru.nsu.ccfit.evolution.user.framework.AssetLoader;
import ru.nsu.ccfit.evolution.user.framework.LoadingScreen;
import ru.nsu.ccfit.evolution.user.framework.StartScreen;

public class EvolutionGame extends Game {

    private AssetLoader assets;
    private ServerEmulator serverEmulator;
    private MyClient client;
    private int playerID;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.loadAll();
        playerID = 0;
        serverEmulator = new ServerEmulator();
        this.setScreen(new StartScreen(this));
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

    public ServerEmulator getServerEmulator() {
        return serverEmulator;
    }

    // ================================ init ================================ //

    //private void init

}
