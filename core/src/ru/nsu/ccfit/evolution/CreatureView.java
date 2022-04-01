package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CreatureView extends GameActor {
    private short abilities;

    public CreatureView(float w, float h, EvolutionGame game) {
        super(w, h);
        this.abilities = 0;
        this.texture = new TextureRegion(game.getLoader().getTexture("cards/cover.png"));
    }

    public void select() {
        addAction(scaleTo(1.1f, 0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1, 0.2f));
    }

    @Override
    public void reset() {
        super.reset();
        this.abilities = 0;
    }

    public void move(float x, float y, float t) {
        addAction(moveTo(x, y, t));
    }

    public void addAbility(String ability) {
        //временное условие, до того, как добавлю реализацию особых свойств
        if (!Objects.equals(ability, "fat")) abilities |= Abilities.get(ability);
    }

    public boolean hasAbility(String ability) {
        return (abilities & Abilities.get(ability)) == 0;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }
}
