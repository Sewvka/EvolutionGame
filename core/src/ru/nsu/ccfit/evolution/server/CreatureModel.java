package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.common.Abilities;

import java.security.InvalidParameterException;
import java.util.Objects;

public class CreatureModel {
    private short abilities;

    private final Array<CreatureModel> cooperationList;
    private final Array<Boolean> cooperationUsed;
    private final Array<CreatureModel> symbiosisList;
    private final Array<CreatureModel> communicationList;
    private final Array<Boolean> communicationUsed;
    private int food;
    private int fatMax;
    private int fatStored;
    public boolean preyedThisRound;
    public boolean grazedThisRound;
    public boolean piracyUsed;
    public boolean isPoisoned;
    public int turnsSinceHibernation;

    public CreatureModel() {
        abilities = 0;
        fatMax = 0;
        fatStored = 0;
        communicationList = new Array<>();
        communicationUsed = new Array<>();
        symbiosisList = new Array<>();
        cooperationList = new Array<>();
        cooperationUsed = new Array<>();
        preyedThisRound = false;
        grazedThisRound = false;
        isPoisoned = false;
        piracyUsed = false;
        turnsSinceHibernation = -1;
    }

    public Array<CreatureModel> getCooperationList() {
        return cooperationList;
    }

    public Array<CreatureModel> getSymbiosisList() {
        return symbiosisList;
    }

    public Array<CreatureModel> getCommunicationList() {
        return communicationList;
    }

    public void resetFood() {
        food = 0;
    }

    public int getFood() {
        return food;
    }

    public boolean isFed() {
        return food >= foodRequired() || turnsSinceHibernation == 0;
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

    public boolean addFood() {
        if (food < foodRequired()) {
            food++;
            return true;
        } else if (canEatMore()) {
            fatStored++;
            return true;
        } else return false;
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
                cooperationUsed.add(Boolean.FALSE);
                break;
            case "communication":
                communicationList.add(partner);
                communicationUsed.add(Boolean.FALSE);
                break;
            case "symbiosis":
                symbiosisList.add(partner);
                break;
        }
    }

    public boolean hasAbility(String ability) {
        if (ability.equals("fat")) return (fatMax > 0);
        if (ability.equals("cooperation")) return cooperationList.size > 0;
        if (ability.equals("communication")) return communicationList.size > 0;
        if (ability.equals("symbiosis")) return symbiosisList.size > 0;
        return (abilities & Abilities.get(ability)) != 0;
    }

    public boolean hasCoopAbilityLink(String ability, CreatureModel partner) {
        if (!Abilities.isCooperative(ability)) return false;
        switch (ability) {
            case "cooperation":
                return cooperationList.contains(partner, true);
            case "communication":
                return communicationList.contains(partner, true);
            case "symbiosis":
                return symbiosisList.contains(partner, true);
        }
        return false;
    }

    public void removeAbility(String ability) {
        abilities -= Abilities.get(ability);
    }

    public Array<Boolean> getCooperationUsed() {
        return cooperationUsed;
    }

    public Array<Boolean> getCommunicationUsed() {
        return communicationUsed;
    }

    public void resetPerRoundAbilities() {
        preyedThisRound = false;
        grazedThisRound = false;
        for (int i = 0; i < communicationUsed.size; i++) {
            communicationUsed.set(i, false);
        }
        for (int i = 0; i < cooperationUsed.size; i++) {
            cooperationUsed.set(i, false);
        }
    }

    public void resetPerTurnAbilities() {
        resetPerRoundAbilities();
        piracyUsed = false;
        if (turnsSinceHibernation >= 0) turnsSinceHibernation++;
        if (turnsSinceHibernation >= 2) turnsSinceHibernation = -1;
    }
}
