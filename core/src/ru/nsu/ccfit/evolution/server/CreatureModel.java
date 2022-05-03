package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.common.Abilities;

import java.security.InvalidParameterException;
import java.util.Objects;

public class CreatureModel {
    private short abilities;

    private final Array<Integer> cooperationList;
    private final Array<Integer> symbiosisList;
    private final Array<Integer> communicationList;
    private int food;
    private int fat;
    public boolean preyedThisRound;
    public CreatureModel() {
        abilities = 0;
        fat = 0;
        communicationList = new Array<>();
        symbiosisList = new Array<>();
        cooperationList = new Array<>();
    }

    public void addFood() {
        if (food < foodRequired())
            food++;
    }

    public Array<Integer> getCooperationList() {
        return cooperationList;
    }

    public Array<Integer> getSymbiosisList() {
        return symbiosisList;
    }

    public Array<Integer> getCommunicationList() {
        return communicationList;
    }

    public void resetFood() {
        food = 0;
    }

    public int getFood() {
        return food;
    }

    public boolean isFed() {
        return food >= foodRequired();
    }

    public int getFat() {
        return fat;
    }

    public int foodRequired() {
        int res = 1;
        if (hasAbility("high_body_weight")) res += 1;
        if (hasAbility("parasite")) res += 2;
        if (hasAbility("carnivorous")) res += 1;
        return res;
    }

    public void addAbility(String ability) {
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
        if (!Objects.equals(ability, "cooperation")) cooperationList.add(partherID);
        else if (!Objects.equals(ability, "communication")) communicationList.add(partherID);
        else if (!Objects.equals(ability, "symbiosis")) symbiosisList.add(partherID);
    }

    public boolean hasAbility(String ability) {
        if (ability.equals("fat")) return (fat > 0);
        if (ability.equals("cooperation")) return cooperationList.size > 0;
        if (ability.equals("communication")) return communicationList.size > 0;
        if (ability.equals("symbiosis")) return symbiosisList.size > 0;
        return (abilities & Abilities.get(ability)) != 0;
    }

    public boolean hasCoopAbility(String ability, int partnerID) {
        if (!Abilities.isCooperative(ability)) return false;
        switch (ability) {
            case "cooperation":
                return cooperationList.contains(partnerID, false);
            case "communication":
                return communicationList.contains(partnerID, false);
            case "symbiosis":
                return symbiosisList.contains(partnerID, false);
        }
        return false;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }

    public void resetAbilities() {
        preyedThisRound = false;
    }
}
