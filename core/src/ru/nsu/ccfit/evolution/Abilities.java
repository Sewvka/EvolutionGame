package ru.nsu.ccfit.evolution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Abilities {
    public static final short LARGE = 0b1000_0000_0000_000,
            FAST = LARGE >> 1,
            SWIMMER = FAST >> 1,
            CAMOUFLAGE = SWIMMER >> 1,
            MIMICRY = CAMOUFLAGE >> 1,
            BURROWER = MIMICRY >> 1,
            ACUTE_VISION = BURROWER >> 1,
            SCAVENGER = ACUTE_VISION >> 1,
            PARASITE = SCAVENGER >> 1,
            PIRACY = PARASITE >> 1,
            STOMPER = PIRACY >> 1,
            SHED_TAIL = STOMPER >> 1,
            PREDATOR = SHED_TAIL >> 1,
            POISONOUS = PREDATOR >> 1;

    private static final Map<String, Short> abilityMap;
    static {
        HashMap<String, Short> aMap = new HashMap<>();
        aMap.put("large", LARGE);
        aMap.put("swimmer", SWIMMER);
        aMap.put("camouflage", CAMOUFLAGE);
        aMap.put("mimicry", MIMICRY);
        aMap.put("burrower", BURROWER);
        aMap.put("acute_vision", ACUTE_VISION);
        aMap.put("scavenger", SCAVENGER);
        aMap.put("parasite", PARASITE);
        aMap.put("piracy", PIRACY);
        aMap.put("stomper", STOMPER);
        aMap.put("shed_tail", SHED_TAIL);
        aMap.put("predator", PREDATOR);
        aMap.put("poisonous", POISONOUS);
        abilityMap = Collections.unmodifiableMap(aMap);
    }

    public static short get(String ability) {
        return abilityMap.get(ability);
    }
}
