package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.server.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyScreen extends GameScreen {

    private Label gameIDLabel;
    private Label selfPlayerLabel;
    private List<Label> playerLabels = new ArrayList<>();
    private TextButton startLobbyButton;

    public LobbyScreen(final EvolutionGame game, final Client client) {
        super(game, client);

        gameIDLabel = new Label("Game ID: " + game.getGameWorldState(), game.getAssets().getSkin());
        gameIDLabel.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        gameIDLabel.setPosition(getViewport().getWorldWidth() / 16, 14 * getViewport().getWorldHeight() / 16);

        selfPlayerLabel = new Label(game.getGameWorldState().getSelfUsername(), game.getAssets().getSkin());
        selfPlayerLabel.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        selfPlayerLabel.setPosition(13 * getViewport().getWorldWidth() / 16, 14 * getViewport().getWorldHeight() / 16);

        Label playerLabel2 = new Label("", game.getAssets().getSkin());
        playerLabel2.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        playerLabel2.setPosition(13 * getViewport().getWorldWidth() / 16, 12 * getViewport().getWorldHeight() / 16);

        Label playerLabel3 = new Label("", game.getAssets().getSkin());
        playerLabel3.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        playerLabel3.setPosition(13 * getViewport().getWorldWidth() / 16, 10 * getViewport().getWorldHeight() / 16);

        Label playerLabel4 = new Label("", game.getAssets().getSkin());
        playerLabel3.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        playerLabel3.setPosition(13 * getViewport().getWorldWidth() / 16, 8 * getViewport().getWorldHeight() / 16);

        playerLabels.add(playerLabel2);
        playerLabels.add(playerLabel3);
        playerLabels.add(playerLabel4);

        startLobbyButton = new TextButton("Submit", game.getAssets().getSkin());
        startLobbyButton.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        startLobbyButton.setPosition(7 * getViewport().getWorldWidth() / 16, getViewport().getWorldHeight() / 8);
        startLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadingScreen(game, client));
            }
        });

        Stage stage = new Stage();
        stage.addActor(gameIDLabel);
        stage.addActor(selfPlayerLabel);
        stage.addActor(playerLabel2);
        stage.addActor(playerLabel3);
        stage.addActor(playerLabel4);
        stage.addActor(startLobbyButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        for (Label playerLabel : playerLabels) {
            playerLabel.setVisible(false);
        }

        if (game.getGameWorldState().getPlayers() != null) {
            int index = 0;
            for (Map.Entry<Integer, String> player : game.getGameWorldState().getPlayers().entrySet()) {
                Label playerLabel = playerLabels.get(index++);
                player.setValue(player.getValue());
                playerLabel.setVisible(true);
            }
        }

        if (!game.getGameWorldState().isHost())
            startLobbyButton.setTouchable(Touchable.disabled);
    }
}
