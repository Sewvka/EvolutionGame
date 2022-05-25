package ru.nsu.ccfit.evolution.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Abilities {

    private static final Map<String, Integer> abilityMap;

    static {
        HashMap<String, Integer> aMap = new HashMap<>();
        aMap.put("burrowing", 1);
        aMap.put("camouflage", 2);
        aMap.put("carnivorous", 3);
        aMap.put("communication");
        aMap.put("cooperation");
        aMap.put("fat");
        aMap.put("grazing");
        aMap.put("hibernation_ability");
        aMap.put("high_body_weight");
        aMap.put("mimicry");
        aMap.put("parasite");
        aMap.put("piracy");
        aMap.put("poisonous");
        aMap.put("running");
        aMap.put("scavenger");
        aMap.put("sharp_vision");
        aMap.put("swimmer");
        aMap.put("symbiosis");
        aMap.put("tail_loss", 1);
        abilityMap = Collections.unmodifiableMap(aMap);
    }

    public static int getAbilityID(String ability) {
        return abilityMap.get(ability);
    }

    public static boolean isCooperative(String ability) {
        return ability.equals("communication") || ability.equals("cooperation") || ability.equals("symbiosis");
    }
}
