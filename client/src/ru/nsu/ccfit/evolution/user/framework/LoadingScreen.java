package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import ru.nsu.ccfit.evolution.EvolutionGame;

public class LoadingScreen extends GameScreen {
    ProgressBar bar;

    public LoadingScreen(final EvolutionGame game) {
        super(game);
        bar = new ProgressBar(0, 100, 1, false, game.getAssets().getSkin(), "default-horizontal");
        bar.setSize(getViewport().getWorldWidth() / 3, 20);
        bar.setPosition((getViewport().getWorldWidth() - bar.getWidth()) / 2, (getViewport().getWorldHeight() - bar.getHeight()) / 2);
        bar.setAnimateDuration(0.1f);
        Stage stage = new Stage(getViewport());
        stage.addActor(bar);
        bar.setValue(0);
        addStage(stage);
    }

    @Override
    public void render(float delta) {
        if (game.getAssets().isLoaded()) {
            game.setScreen(new SessionScreen(game));
        } else {
            super.render(delta);
            bar.setValue(game.getAssets().progress() * 100);
            bar.updateVisualValue();
        }
    }
}
