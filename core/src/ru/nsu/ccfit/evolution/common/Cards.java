package ru.nsu.ccfit.evolution.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Cards {
    private static final Map<Integer, String> cardIDs;

    static {
        HashMap<Integer, String> aMap = new HashMap<>();
        aMap.put(1, "burrowing-fat");
        aMap.put(2, "camouflage-fat");
        aMap.put(3, "cooperation-fat");
        aMap.put(4, "communication-carnivorous");
        aMap.put(5, "grazing-fat");
        aMap.put(6, "cooperation-carnivorous");
        aMap.put(7, "high_body_weight-fat");
        aMap.put(8, "hibernation_ability-carnivorous");
        aMap.put(9, "high_body_weight-carnivorous");
        aMap.put(10, "mimicry");
        aMap.put(11, "parasite-carnivorous");
        aMap.put(12, "parasite-fat");
        aMap.put(13, "piracy");
        aMap.put(14, "poisonous-carnivorous");
        aMap.put(15, "running");
        aMap.put(16, "scavenger");
        aMap.put(17, "sharp_vision-fat");
        aMap.put(18, "swimmer");
        aMap.put(19, "symbiosis");
        aMap.put(20, "tail_loss");
        cardIDs = Collections.unmodifiableMap(aMap);
    }

    public static String getName(Integer id) {
        return cardIDs.get(id);
    }

    public static String getAbilityFromName(String cardname, boolean firstAbility) {
        String ability1, ability2;
        int i = cardname.indexOf('-');
        if (i >= 0) {
            ability1 = cardname.substring(0, cardname.indexOf('-'));
        } else ability1 = cardname;
        ability2 = cardname.substring(cardname.indexOf('-') + 1);

        if (firstAbility) return ability1;
        else return ability2;
    }
}
