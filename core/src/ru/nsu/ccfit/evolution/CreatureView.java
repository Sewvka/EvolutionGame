package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;

import java.util.Objects;

public class CreatureView extends GameSprite {
    private short abilities;
    private float sizeMod;

    public CreatureView(float w, float h) {
        super(w, h);
        this.abilities = 0;
    }

    public void init(EvolutionGame game) {
        super.init();
        this.image = game.getLoader().getTexture("cover.png");
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
        //временное условие, до того, как добавлю реализацию особых свойств
        if (!Objects.equals(ability, "fat")) abilities |= Abilities.get(ability);
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }
}
