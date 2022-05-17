package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.server.Client;

public class JoinLobbyScreen extends GameScreen {

    private TextField gameIDTextField;
    private TextButton submitButton;


    public JoinLobbyScreen(final EvolutionGame game, final Client client) {
        super(game, client);

        gameIDTextField = new TextField("Game ID", game.getAssets().getSkin());
        gameIDTextField.setSize(GameScreen.WORLD_SIZE_X / 4, GameScreen.WORLD_SIZE_Y / 16);
        gameIDTextField.setPosition(3 * GameScreen.WORLD_SIZE_X / 8, 15 * GameScreen.WORLD_SIZE_Y / 32);
        gameIDTextField.setTextFieldFilter(new DigitFilter());

        submitButton = new TextButton("Submit", game.getAssets().getSkin());
        submitButton.setSize(GameScreen.WORLD_SIZE_X / 8, GameScreen.WORLD_SIZE_Y / 16);
        submitButton.setPosition(7 * GameScreen.WORLD_SIZE_X / 16, GameScreen.WORLD_SIZE_Y / 8);
        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int gameID = Integer.parseInt(gameIDTextField.getText());
                client.joinGame(game.getGameWorldState().getSelfID(), gameID);
            }
        });

        Stage stage = new Stage(getViewport());
        stage.addActor(gameIDTextField);
        stage.addActor(submitButton);
        addStage(stage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (game.getGameWorldState().getGameID() != -1)
            game.setScreen(new LobbyScreen(game, client));
    }

    public class DigitFilter implements TextField.TextFieldFilter {

        private char[] accepted;

        public DigitFilter() {
            accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        }

        @Override
        public boolean acceptChar(TextField textField, char c) {
            for (char a : accepted)
                if (a == c) return true;
            return false;
        }
    }

}