package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Pool;

import java.util.Objects;

public class CreatureModel implements Pool.Poolable {
    private short abilities;

    public CreatureModel() {
        this.abilities = 0;
    }

    public void addAbility(String ability) {
        //временное условие, до того, как добавлю реализацию особых свойств
        if (!Objects.equals(ability, "fat")) abilities |= Abilities.get(ability);
    }

    public int foodRequired() {
        int res = 1;
        if (hasAbility("high_body_weight")) res+=1;
        if (hasAbility("parasite")) res+=2;
        if (hasAbility("carnivorous")) res+=1;
        return res;
    }

    public boolean hasAbility(String ability) {
        return (abilities & Abilities.get(ability)) == 0;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }

    @Override
    public void reset() {
        this.abilities = 0;
    }
}
