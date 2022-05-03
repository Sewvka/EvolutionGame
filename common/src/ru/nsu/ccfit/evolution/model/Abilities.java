package ru.nsu.ccfit.evolution.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Abilities {
    public static final short BURROWING = 0b1000_0000_0000_000,
            CAMOUFLAGE = BURROWING >> 1,
            CARNIVOROUS = CAMOUFLAGE >> 1,
            GRAZING = CARNIVOROUS >> 1,
            HIBERNATION_ABILITY = GRAZING >> 1,
            HIGH_BODY_WEIGHT = HIBERNATION_ABILITY >> 1,
            MIMICRY = HIGH_BODY_WEIGHT >> 1,
            PARASITE = MIMICRY >> 1,
            PIRACY = PARASITE >> 1,
            POISONOUS = PIRACY >> 1,
            RUNNING = POISONOUS >> 1,
            SCAVENGER = RUNNING >> 1,
            SHARP_VISION = SCAVENGER >> 1,
            SWIMMER = SHARP_VISION >> 1,
            TAIL_LOSS = SWIMMER >> 1;

    private static final Map<String, Short> abilityMap;

    static {
        HashMap<String, Short> aMap = new HashMap<>();
        aMap.put("burrowing", BURROWING);
        aMap.put("camouflage", CAMOUFLAGE);
        aMap.put("carnivorous", CARNIVOROUS);
        aMap.put("grazing", GRAZING);
        aMap.put("hibernation_ability", HIBERNATION_ABILITY);
        aMap.put("high_body_weight", HIGH_BODY_WEIGHT);
        aMap.put("mimicry", MIMICRY);
        aMap.put("parasite", PARASITE);
        aMap.put("piracy", PIRACY);
        aMap.put("poisonous", POISONOUS);
        aMap.put("running", RUNNING);
        aMap.put("scavenger", SCAVENGER);
        aMap.put("sharp_vision", SHARP_VISION);
        aMap.put("swimmer", SWIMMER);
        aMap.put("tail_loss", TAIL_LOSS);
        abilityMap = Collections.unmodifiableMap(aMap);
    }

    public static short get(String ability) {
        return abilityMap.get(ability);
    }

    public static boolean isCooperative(String ability) {
        return ability.equals("communication") || ability.equals("cooperation") || ability.equals("symbiosis");
    }
}
