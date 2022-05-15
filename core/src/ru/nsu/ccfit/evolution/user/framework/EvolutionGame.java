package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Game;
import ru.nsu.ccfit.evolution.server.Client;
import ru.nsu.ccfit.evolution.server.GameWorldState;

public class EvolutionGame extends Game {
    private AssetLoader assets;
    private Client client;
    private GameWorldState gameWorldState;
    private int playerID;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.loadAll();
        this.gameWorldState = new GameWorldState();
        this.client = new Client(this, gameWorldState);
        playerID = 0;
        this.setScreen(new LoginScreen(this, client));
    }

    public GameWorldState getGameWorldState() {
        return gameWorldState;
    }

    public int getPlayerID() {
        return playerID;
    }

    public Client getClient() {
        return client;
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

    public void test() {
        this.setScreen(new LoadingScreen(this, client));
    }
}
