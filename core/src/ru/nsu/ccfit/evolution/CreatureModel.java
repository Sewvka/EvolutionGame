package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.security.InvalidParameterException;
import java.util.Objects;

public class CreatureModel implements Pool.Poolable {
    private short abilities;
    private final Array<Integer> cooperation;
    private final Array<Integer> symbiosis;
    private final Array<Integer> communication;
    private int food;
    private int fat;

    public CreatureModel() {
        abilities = 0;
        fat = 0;
        communication = new Array<>();
        symbiosis = new Array<>();
        cooperation = new Array<>();
    }

    public void addFood() {
        food++;
    }

    public void resetFood() {
        food = 0;
    }

    public int getFood() {
        return food;
    }

    public int getFat() {
        return fat;
    }

    public void addAbility(String ability) {
        //временное условие, до того, как добавлю реализацию особых свойств
        if (Abilities.isCooperative(ability)) {
            throw new InvalidParameterException("Use addCoopAbility method to add coop abilities!");
        }
        if (!Objects.equals(ability, "fat")) abilities |= Abilities.get(ability);
        else {
            fat++;
        }
    }

    public void addCoopAbility(String ability, int partherID) {
        if (!Abilities.isCooperative(ability)) {
            throw new InvalidParameterException("Use addAbility method to add non-coop abilities!");
        }
        if (!Objects.equals(ability, "cooperation")) cooperation.add(partherID);
        else if (!Objects.equals(ability, "communication")) communication.add(partherID);
        else if (!Objects.equals(ability, "symbiosis")) symbiosis.add(partherID);
    }

    public int foodRequired() {
        int res = 1;
        if (hasAbility("high_body_weight")) res += 1;
        if (hasAbility("parasite")) res += 2;
        if (hasAbility("carnivorous")) res += 1;
        return res;
    }

    public boolean hasAbility(String ability) {
        if (ability.equals("fat")) return (fat > 0);
        if (ability.equals("cooperation")) return cooperation.size > 0;
        if (ability.equals("communication")) return communication.size > 0;
        if (ability.equals("symbiosis")) return symbiosis.size > 0;
        return (abilities & Abilities.get(ability)) != 0;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }

    @Override
    public void reset() {
        abilities = 0;
        fat = 0;
        communication.clear();
        symbiosis.clear();
        cooperation.clear();
    }
}
