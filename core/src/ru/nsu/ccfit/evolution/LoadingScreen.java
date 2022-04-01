package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen extends StageScreen {
    ProgressBar bar;

    public LoadingScreen(final EvolutionGame game) {
        super(game);
        bar = new ProgressBar(0, 100, 1, false, game.getSkin(), "default-horizontal");
        bar.setPosition(EvolutionGame.WORLD_SIZE_X/2 - 200, 150);
        bar.setSize(400, 20);
        bar.setAnimateDuration(0.5f);
        stage.addActor(bar);
        bar.setValue(0);
    }

    @Override
    public void render(float delta) {
        if (game.getLoader().isLoaded()) {
            game.setScreen(new GameScreen(game));
        }
        else {
            ScreenUtils.clear(220, 220, 220, 220);
            game.getCamera().update();
            bar.setValue(game.getLoader().progress() * 100);
            bar.updateVisualValue();
            stage.act();
            stage.draw();
        }
    }
}
