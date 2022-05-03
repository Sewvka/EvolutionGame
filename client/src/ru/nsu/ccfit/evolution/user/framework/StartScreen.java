package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.EvolutionGame;

public class StartScreen extends GameScreen {

    private final TextButton createSession;
    private final TextButton connectToSession;

    public StartScreen(final EvolutionGame game) {
        super(game);

        this.createSession = new TextButton("Create lobby", game.getAssets().getSkin());
        this.connectToSession = new TextButton("Join lobby", game.getAssets().getSkin());
        initActors();

        Stage stage = new Stage(getViewport());
        stage.addActor(createSession);
        stage.addActor(connectToSession);
        addStage(stage);
    }

    private void initActors() {
        this.createSession.setSize(getViewport().getWorldWidth() / 4, getViewport().getWorldHeight() / 16);
        this.createSession.setPosition(3 * getViewport().getWorldWidth() / 8, 14 * getViewport().getWorldHeight() / 32);
        this.createSession.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadingScreen(game));
            }
        });

        this.connectToSession.setSize(getViewport().getWorldWidth() / 4, getViewport().getWorldHeight() / 16);
        this.connectToSession.setPosition(3 * getViewport().getWorldWidth() / 8, 18 * getViewport().getWorldHeight() / 32);
        this.connectToSession.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadingScreen(game));
            }
        });
    }


}
