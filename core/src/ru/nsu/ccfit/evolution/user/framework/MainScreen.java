package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.ietf.jgss.GSSManager;
import ru.nsu.ccfit.evolution.server.Client;

public class MainScreen extends GameScreen {

    private TextButton joinLobby;
    private TextButton createLobby;

    public MainScreen(final EvolutionGame game, final Client client) {
        super(game, client);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        joinLobby = new TextButton("Join lobby", game.getAssets().getSkin());
        joinLobby.setSize(W / 8, H / 16);
        joinLobby.setPosition(7 * W / 16, 6 * H / 16);
        joinLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                client.startGamesListChecking();
                game.setScreen(new LobbyListScreen(game, client));
            }
        });

        createLobby = new TextButton("Create lobby", game.getAssets().getSkin());
        createLobby.setSize(W / 8, H / 16);
        createLobby.setPosition(7 * W / 16, 9 * H / 16);
        createLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                client.createGame(game.getGameWorldState().getSelfID());
            }
        });

        Stage stage = new Stage();
        stage.addActor(joinLobby);
        stage.addActor(createLobby);
        addStage(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (game.getGameWorldState().isInLobby())
            game.setScreen(new LobbyScreen(game, client));
    }
}
