package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.server.Client;

public class MainScreen extends GameScreen {

    private TextButton joinLobby;
    private TextButton createLobby;

    public MainScreen(final EvolutionGame game, final Client client) {
        super(game, client);

        joinLobby = new TextButton("Join lobby", game.getAssets().getSkin());
        joinLobby.setSize(GameScreen.WORLD_SIZE_X / 8, getViewport().getWorldHeight() / 16);
        joinLobby.setPosition(7 * GameScreen.WORLD_SIZE_X / 16, 6 * getViewport().getWorldHeight() / 16);
        joinLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new JoinLobbyScreen(game, client));
            }
        });

        createLobby = new TextButton("Create lobby", game.getAssets().getSkin());
        createLobby.setSize(GameScreen.WORLD_SIZE_X / 8, GameScreen.WORLD_SIZE_Y / 16);
        createLobby.setPosition(7 * GameScreen.WORLD_SIZE_X / 16, 9 * GameScreen.WORLD_SIZE_Y / 16);
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
