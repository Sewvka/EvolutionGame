package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.EvolutionGame;

public class LobbyCreationScreen extends GameScreen {

    private final TextField nickName;
    private final TextButton textButton;

    public LobbyCreationScreen(EvolutionGame game) {
        super(game);

        this.nickName = new TextField("Your nickname", game.getAssets().getSkin());
        this.textButton = new TextButton("Create new lobby", game.getAssets().getSkin());
        initActors();

        Stage stage = new Stage(getViewport());
        stage.addActor(nickName);
        stage.addActor(textButton);
    }

    public void initActors() {
        this.nickName.setSize(getViewport().getWorldWidth() / 4, getViewport().getWorldHeight() / 16);
        this.nickName.setPosition(3 * getViewport().getWorldWidth() / 8, 14 * getViewport().getWorldHeight() / 32);

        this.textButton.setSize(getViewport().getWorldWidth() / 4, getViewport().getWorldHeight() / 16);
        this.textButton.setPosition(3 * getViewport().getWorldWidth() / 8, 18 * getViewport().getWorldHeight() / 32);
        this.textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO:
            }
        });
    }
}
