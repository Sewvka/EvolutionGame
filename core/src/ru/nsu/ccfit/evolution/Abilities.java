package ru.nsu.ccfit.evolution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Abilities {
    public static final short HIGH_BODY_WEIGHT = 0b1000_0000_0000_000,
            RUNNING = HIGH_BODY_WEIGHT >> 1,
            SWIMMER = RUNNING >> 1,
            CAMOUFLAGE = SWIMMER >> 1,
            MIMICRY = CAMOUFLAGE >> 1,
            BURROWER = MIMICRY >> 1,
            ACUTE_VISION = BURROWER >> 1,
            SCAVENGER = ACUTE_VISION >> 1,
            PARASITE = SCAVENGER >> 1,
            PIRACY = PARASITE >> 1,
            GRAZING = PIRACY >> 1,
            SHED_TAIL = GRAZING >> 1,
            PREDATOR = SHED_TAIL >> 1,
            POISONOUS = PREDATOR >> 1,
            HIBERNATION_ABILITY = POISONOUS >> 1;

    private static final Map<String, Short> abilityMap;
    static {
        HashMap<String, Short> aMap = new HashMap<>();
        aMap.put("high_body_weight", HIGH_BODY_WEIGHT);
        aMap.put("swimmer", SWIMMER);
        aMap.put("camouflage", CAMOUFLAGE);
        aMap.put("mimicry", MIMICRY);
        aMap.put("burrowing", BURROWER);
        aMap.put("sharp_vision", ACUTE_VISION);
        aMap.put("scavenger", SCAVENGER);
        aMap.put("parasite", PARASITE);
        aMap.put("piracy", PIRACY);
        aMap.put("grazing", GRAZING);
        aMap.put("tail_loss", SHED_TAIL);
        aMap.put("predator", PREDATOR);
        aMap.put("poisonous", POISONOUS);
        aMap.put("hibernation_ability", HIBERNATION_ABILITY);
        aMap.put("running", RUNNING);
        abilityMap = Collections.unmodifiableMap(aMap);
    }

    public static short get(String ability) {
        return abilityMap.get(ability);
    }
}
