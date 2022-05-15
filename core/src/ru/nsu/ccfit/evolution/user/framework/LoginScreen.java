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
        loginInput.setSize(getViewport().getWorldWidth() / 4, getViewport().getWorldHeight() / 16);
        loginInput.setPosition(3 * getViewport().getWorldWidth() / 8, 15 * getViewport().getWorldHeight() / 32);

        submitButton = new TextButton("Submit", evolutionGame.getAssets().getSkin());
        submitButton.setSize(getViewport().getWorldWidth() / 8, getViewport().getWorldHeight() / 16);
        submitButton.setPosition(7 * getViewport().getWorldWidth() / 16, getViewport().getWorldHeight() / 8);
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
