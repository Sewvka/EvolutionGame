package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.server.Client;

public class LoginScreen extends GameScreen {

    private TextField loginInput;
    private TextButton submitButton;

    public LoginScreen(final EvolutionGame evolutionGame, final Client client) {
        super(evolutionGame, client);

        loginInput = new TextField("your login", evolutionGame.getAssets().getSkin());
        loginInput.setSize(GameScreen.WORLD_SIZE_X / 4, GameScreen.WORLD_SIZE_Y / 16);
        loginInput.setPosition(3 * GameScreen.WORLD_SIZE_X / 8, 15 * GameScreen.WORLD_SIZE_Y / 32);

        submitButton = new TextButton("Submit", evolutionGame.getAssets().getSkin());
        submitButton.setSize(GameScreen.WORLD_SIZE_X / 8, GameScreen.WORLD_SIZE_Y / 16);
        submitButton.setPosition(7 * GameScreen.WORLD_SIZE_X / 16, GameScreen.WORLD_SIZE_Y / 8);
        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = loginInput.getText();
                if (!username.equals("your login")) {
                    client.login(username);
                } else {
                    game.setScreen(new LoginScreen(evolutionGame, client));
                }
            }
        });

        Stage stage = new Stage(getViewport());
        stage.addActor(loginInput);
        stage.addActor(submitButton);
        addStage(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (game.getGameWorldState().isLoggedIn())
            game.setScreen(new MainScreen(game, client));
    }

}
