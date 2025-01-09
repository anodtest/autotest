package com.anodtester.Config;
import java.util.HashSet;
import java.util.Set;

// Class name used compare 'team input' from keyboard vs key map in here
public class TeamKeys {
    private static final Set<String> teamNames = new HashSet<>();

    // Key of Team
    static {
        teamNames.add("Manchester City");
        teamNames.add("Liverpool");
        teamNames.add("Chelsea");
        teamNames.add("Arsenal");
        teamNames.add("Manchester United");
    }

    // Check valid
    public static boolean isValidTeam(String teamName) {
        return teamNames.contains(teamName);
    }
}
