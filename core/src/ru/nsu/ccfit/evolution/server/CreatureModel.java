package ru.nsu.ccfit.evolution.server;

import java.util.ArrayList;

public class CreatureModel {
    private ArrayList<AbilityModel> abilities = new ArrayList<>();
    private int food = 0;
    private int fatMax = 0;
    private int fatStored = 0;
    private final int id;

    public CreatureModel(int id) {
        this.id = id;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void removeFood() {
        if (food > 0) food--;
    }

    public int getFatStored() {
        return fatStored;
    }

    public void setFatStored(int fatStored) {
        this.fatStored = fatStored;
    }

    public int getFatMax() {
        return fatMax;
    }

    public void setFatMax(int fatMax) {
        this.fatMax = fatMax;
    }

    public void addAbility(String ability, int targetID1, int targetID2) {
        if (!ability.equals("fat")) abilities.add(new AbilityModel(ability, targetID1, targetID2));
        else {
            fatMax++;
        }
    }

    public boolean hasAbility(String ability) {
        if (ability.equals("fat")) return (fatMax > 0);
        for (AbilityModel a : abilities) {
            if (a.getName().equals(ability)) return true;
        }
        return false;
    }

    public void removeAbility(String ability) {
        for (AbilityModel a : abilities) {
            if (a.getName().equals(ability)) {
                abilities.remove(a);
                break;
            }
        }
    }

    public void removeCoopAbility(String ability, int partnerID) {
        for (AbilityModel a : abilities) {
            if (a.getName().equals(ability) && (a.getCreatureID1() == partnerID || a.getCreatureID2() == partnerID))
                abilities.remove(a);
        }
    }

    public ArrayList<AbilityModel> getAbilities() {
        return abilities;
    }
}
