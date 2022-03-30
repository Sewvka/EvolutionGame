package ru.nsu.ccfit.evolution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Cards {
    private static final Map<Integer, String> cardIDs;
    static {
        HashMap<Integer, String> aMap = new HashMap<>();
        aMap.put(0, "burrowing-fat");
        aMap.put(1, "piracy");
        aMap.put(2, "poisonous-predator");
        aMap.put(3, "grazing-fat");
        aMap.put(4, "mimicry");
        aMap.put(5, "scavenger");
        aMap.put(6, "swimmer");
        aMap.put(7, "hibernation_ability-predator");
        aMap.put(8, "running");
        aMap.put(9, "shed_tail");
        aMap.put(10, "camouflage-fat");
        aMap.put(11, "high_body_weight-predator");
        aMap.put(12, "high_body_weight-fat");
        aMap.put(13, "parasite-predator");
        aMap.put(14, "parasite-fat");
        aMap.put(15, "sharp_vision-fat");
        aMap.put(16, "symbiosis");
        aMap.put(17, "communication-predator");
        aMap.put(18, "cooperation-predator");
        aMap.put(19, "cooperation-fat");
        cardIDs = Collections.unmodifiableMap(aMap);
    }

    public static String getName(Integer id) {
        return cardIDs.get(id);
    }
}
