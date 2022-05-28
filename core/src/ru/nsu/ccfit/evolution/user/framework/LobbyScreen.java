package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Gdx;
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

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        gameIDLabel = new Label("Game ID: " + game.getGameWorldState().getGameID(), game.getAssets().getSkin());
        gameIDLabel.setSize(W / 8, H / 16);
        gameIDLabel.setPosition(W / 16, 14 * H / 16);

        selfPlayerLabel = new Label(game.getGameWorldState().getSelfUsername(), game.getAssets().getSkin());
        selfPlayerLabel.setSize(W / 8, H / 16);
        selfPlayerLabel.setPosition(13 * W / 16, 14 * H / 16);

        Label playerLabel2 = new Label("", game.getAssets().getSkin());
        playerLabel2.setSize(W / 8, H / 16);
        playerLabel2.setPosition(13 * W / 16, 12 * H / 16);

        Label playerLabel3 = new Label("", game.getAssets().getSkin());
        playerLabel3.setSize(W / 8, H / 16);
        playerLabel3.setPosition(13 * W / 16, 10 * H / 16);

        Label playerLabel4 = new Label("", game.getAssets().getSkin());
        playerLabel3.setSize(W / 8, H / 16);
        playerLabel3.setPosition(13 * W / 16, 8 * H / 16);

        playerLabels.add(selfPlayerLabel);
        playerLabels.add(playerLabel2);
        playerLabels.add(playerLabel3);
        playerLabels.add(playerLabel4);

        startLobbyButton = new TextButton("Submit", game.getAssets().getSkin());
        startLobbyButton.setSize(W / 8, H / 16);
        startLobbyButton.setPosition(7 * W / 16, H / 8);
        startLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                client.startGame(game.getGameWorldState().getSelfID(), game.getGameWorldState().getGameID());
            }
        });

        Stage stage = new Stage();
        stage.addActor(gameIDLabel);
        stage.addActor(selfPlayerLabel);
        stage.addActor(playerLabel2);
        stage.addActor(playerLabel3);
        stage.addActor(playerLabel4);
        stage.addActor(startLobbyButton);
        addStage(stage);

        for (Label playerLabel : playerLabels) {
            playerLabel.setVisible(false);
        }
        if (game.getGameWorldState().notHost()) {
            startLobbyButton.setTouchable(Touchable.disabled);
            startLobbyButton.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        for (Label label : playerLabels) {
            label.setVisible(false);
        }

        if (game.getGameWorldState().getPlayers() != null) {
            int index = 0;
            for (Map.Entry<Integer, String> player : game.getGameWorldState().getPlayers().entrySet()) {
                Label playerLabel = playerLabels.get(index++);
                playerLabel.setText(player.getValue());
                playerLabel.setVisible(true);
            }
        }

        if (game.getGameWorldState().notHost()) {
            startLobbyButton.setVisible(false);
            startLobbyButton.setTouchable(Touchable.disabled);
        } else {
            startLobbyButton.setVisible(true);
            startLobbyButton.setTouchable(Touchable.enabled);
        }

        if (game.getGameWorldState().isGameStarted()) {
            game.getClient().stopLobbyChecking();
            game.setScreen(new LoadingScreen(game, client));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (!game.getGameWorldState().isGameStarted())
            client.quitGame(game.getGameWorldState().getSelfID());
    }
}
