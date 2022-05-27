package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Gdx;
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

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        loginInput = new TextField("your login", evolutionGame.getAssets().getSkin());
        loginInput.setSize(W / 4, H / 16);
        loginInput.setPosition(3 * W / 8, 15 * H / 32);

        submitButton = new TextButton("Submit", evolutionGame.getAssets().getSkin());
        submitButton.setSize(W / 8, H / 16);
        submitButton.setPosition(7 * W / 16, H / 8);
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
        if (game.getGameWorldState().isLoggedIn()) {
            game.setScreen(new MainScreen(game, client));
        }
    }

}
