package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class EvolutionServerLauncher {

    public static void main(String[] args) {
        new HeadlessApplication(new EvolutionServerGame());
    }

}
