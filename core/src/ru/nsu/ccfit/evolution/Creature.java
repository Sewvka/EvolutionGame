package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class Creature extends Card {
    private short abilities;
    private float sizeMod;

    public Creature(float w, float h) {
        super(w, h);
        this.abilities = 0;
    }

    public void init(EvolutionGame game) {
        super.init();
        this.image = game.assets.get("cards/cover.png", Texture.class);
    }

    @Override
    public void reset() {
        super.reset();
        this.abilities = 0;
    }

    public boolean hasAbility(String ability) {
        return (abilities & Abilities.get(ability)) == 0;
    }

    public void addAbility(String ability) {
        abilities |= Abilities.get(ability);
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }
}
