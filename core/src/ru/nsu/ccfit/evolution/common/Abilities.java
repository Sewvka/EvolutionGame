package ru.nsu.ccfit.evolution.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class Abilities {

    private static final Map<String, Integer> abilityMap;
    private static final Map<Integer, String> abilityNameMap;

    static {
        HashMap<String, Integer> aMap = new HashMap<>();
        HashMap<Integer, String> bMap = new HashMap<>();
        aMap.put("burrowing", 1);
        aMap.put("camouflage", 2);
        aMap.put("carnivorous", 3);
        aMap.put("communication", 4);
        aMap.put("cooperation", 5);
        aMap.put("fat", 6);
        aMap.put("grazing", 7);
        aMap.put("hibernation_ability", 8);
        aMap.put("high_body_weight", 9);
        aMap.put("mimicry", 10);
        aMap.put("parasite", 11);
        aMap.put("piracy", 12);
        aMap.put("poisonous", 13);
        aMap.put("running", 14);
        aMap.put("scavenger", 15);
        aMap.put("sharp_vision", 16);
        aMap.put("swimmer", 17);
        aMap.put("symbiosis", 18);
        aMap.put("tail_loss", 19);

        bMap.put(1, "burrowing");
        bMap.put(2, "camouflage");
        bMap.put(3, "carnivorous");
        bMap.put(4, "communication");
        bMap.put(5, "cooperation");
        bMap.put(6, "fat");
        bMap.put(7, "grazing");
        bMap.put(8, "hibernation_ability");
        bMap.put(9, "high_body_weight");
        bMap.put(10, "mimicry");
        bMap.put(11, "parasite");
        bMap.put(12, "piracy");
        bMap.put(13, "poisonous");
        bMap.put(14, "running");
        bMap.put(15, "scavenger");
        bMap.put(16, "sharp_vision");
        bMap.put(17, "swimmer");
        bMap.put(18, "symbiosis");
        bMap.put(19, "tail_loss");
        abilityMap = Collections.unmodifiableMap(aMap);
        abilityNameMap = Collections.unmodifiableMap(bMap);
    }

    public static int getAbilityID(String ability) {
        return abilityMap.get(ability);
    }

    public static String getAbilityName(int id) {
        return abilityNameMap.get(id);
    }

    public static boolean isCooperative(String ability) {
        return ability.equals("communication") || ability.equals("cooperation") || ability.equals("symbiosis");
    }
}
