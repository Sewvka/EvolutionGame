package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.server.Client;
import ru.nsu.ccfit.evolution.server.LobbyModel;

import java.util.ArrayList;
import java.util.Iterator;

public class LobbyListScreen extends GameScreen {

    // ! not java.util list !
    private List<LobbyModel> lobbiesList;

    private Array<LobbyModel> lobbies;

    public LobbyListScreen(final EvolutionGame game, final Client client) {
        super(game, client);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        lobbies = new Array<>();
        lobbiesList = new List<>(game.getAssets().getSkin());
        lobbiesList.setItems(lobbies);
        lobbiesList.setPosition(0, 0);
        lobbiesList.setSize(W, H);
        lobbiesList.setVisible(true);
        lobbiesList.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    client.stopGamesListChecking();
                    client.joinGame(game.getGameWorldState().getSelfID(), lobbiesList.getSelected().getGameID());
                    return true;
                }

                return false;
            }
        });

        Stage stage = new Stage();
        stage.addActor(lobbiesList);
        addStage(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // add new lobbies in list
        for (LobbyModel lobbyModel : game.getGameWorldState().getGameLobbies()) {
            if (!lobbies.contains(lobbyModel, false)) {
                lobbies.add(lobbyModel);
            }
        }

        // remove unavailable lobbies from list
        Iterator<LobbyModel> iterator = lobbies.iterator();
        java.util.List<Integer> toRemove = new ArrayList<>();
        while (iterator.hasNext()) {
            LobbyModel lobbyModel = iterator.next();
            if (!game.getGameWorldState().getGameLobbies().contains(lobbyModel)) {
                toRemove.add(lobbies.indexOf(lobbyModel, false));
            }
        }

        for (Integer i : toRemove) {
            lobbies.removeIndex(i);
        }

        lobbiesList.setItems(lobbies);

        if (game.getGameWorldState().isInLobby())
            game.setScreen(new LobbyScreen(game, client));
    }

}
