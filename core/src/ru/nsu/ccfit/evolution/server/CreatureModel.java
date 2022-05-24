package ru.nsu.ccfit.evolution.server;

import ru.nsu.ccfit.evolution.common.Abilities;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;

public class CreatureModel {
    private short abilities = 0;
    private final ArrayList<CreatureModel> cooperationList = new ArrayList<>();
    private final ArrayList<CreatureModel> symbiosisList = new ArrayList<>();
    private final ArrayList<CreatureModel> communicationList = new ArrayList<>();
    private int food = 0;
    private int fatMax = 0;
    private int fatStored = 0;

    public CreatureModel() {
    }

    public ArrayList<CreatureModel> getCooperationList() {
        return cooperationList;
    }

    public ArrayList<CreatureModel> getSymbiosisList() {
        return symbiosisList;
    }

    public ArrayList<CreatureModel> getCommunicationList() {
        return communicationList;
    }

    public void resetFood() {
        food = 0;
    }

    public int getFood() {
        return food;
    }

    public void removeFat(int fatCount) {
        fatStored -= fatCount;
    }

    public boolean canEatMore() {
        return food + fatStored < foodRequired() + fatMax;
    }

    public void addFood(int foodCount) {
        for (int i = 0; i < foodCount; i++) {
            addFood();
        }
    }

    public void addFood() {
        if (food < foodRequired()) {
            food++;
        } else if (canEatMore()) {
            fatStored++;
        }
    }

    public void removeFood() {
        if (food > 0) food--;
    }

    public int getFatMax() {
        return fatMax;
    }

    public int getFatStored() {
        return fatStored;
    }

    public int foodRequired() {
        int res = 1;
        if (hasAbility("high_body_weight")) res += 1;
        if (hasAbility("parasite")) res += 2;
        if (hasAbility("carnivorous")) res += 1;
        return res;
    }

    public float getAbilityCount() {
        return Integer.bitCount(abilities) + communicationList.size() + (float) symbiosisList.size() / 2 + (float) cooperationList.size() / 2 + fatMax;
    }

    public void addAbility(String ability) {
        if (Abilities.isCooperative(ability)) {
            throw new InvalidParameterException("Use addCoopAbility method to add coop abilities!");
        }
        if (!Objects.equals(ability, "fat")) abilities |= Abilities.get(ability);
        else {
            fatMax++;
        }
    }

    public void addCoopAbility(String ability, CreatureModel partner) {
        if (!Abilities.isCooperative(ability)) {
            throw new InvalidParameterException("Use addAbility method to add non-coop abilities!");
        }
        switch (ability) {
            case "cooperation":
                cooperationList.add(partner);
                break;
            case "communication":
                communicationList.add(partner);
                break;
            case "symbiosis":
                symbiosisList.add(partner);
                break;
        }
    }

    public boolean hasAbility(String ability) {
        if (ability.equals("fat")) return (fatMax > 0);
        if (ability.equals("cooperation")) return cooperationList.size() > 0;
        if (ability.equals("communication")) return communicationList.size() > 0;
        if (ability.equals("symbiosis")) return symbiosisList.size() > 0;
        return (abilities & Abilities.get(ability)) != 0;
    }

    public boolean hasCoopAbilityLink(String ability, CreatureModel partner) {
        if (!Abilities.isCooperative(ability)) return false;
        switch (ability) {
            case "cooperation":
                return cooperationList.contains(partner);
            case "communication":
                return communicationList.contains(partner);
            case "symbiosis":
                return symbiosisList.contains(partner);
        }
        return false;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }
}
