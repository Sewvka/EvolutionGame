package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Controller {
    private final EvolutionGame game;

    public Controller(EvolutionGame game) {
        this.game = game;
    }

    public Vector2 getMouseCoords() {
        Vector3 mousePos = new Vector3();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.getDrawer().unproject(mousePos);
        return (new Vector2(mousePos.x, mousePos.y));
    }
}
