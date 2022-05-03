package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class StageMultiplexer {
    private final InputMultiplexer multiplexer;
    private final ArrayList<Stage> stages;

    public StageMultiplexer() {
        multiplexer = new InputMultiplexer();
        stages = new ArrayList<>();
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void addStage(Stage stage) {
        multiplexer.addProcessor(stage);
        stages.add(stage);
    }

    public void removeStage(Stage stage) {
        multiplexer.removeProcessor(stage);
        stages.remove(stage);
    }

    public void update() {
        for (Stage s : stages) {
            s.act();
            s.draw();
        }
    }

    public void dispose() {
        for (Stage s : stages) {
            s.dispose();
        }
    }
}
