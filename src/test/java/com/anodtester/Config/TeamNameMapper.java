package com.thientester.Config;

import java.util.HashMap;
import java.util.Map;

// Class name used ánh xạ compare MatchName together between out and in (because matchName at MatchDetail is short name)
public class TeamNameMapper {
    private static final Map<String, String> teamShortToFullMap = new HashMap<>();
    static {
        teamShortToFullMap.put("Man Utd", "Manchester United");
        teamShortToFullMap.put("Man City", "Manchester City");
        teamShortToFullMap.put("PSG", "Paris Saint-Germain");
    }

    public static String getFullName(String shortName) {
        return teamShortToFullMap.get(shortName);
    }
}
