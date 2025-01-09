package com.thientester.Config;
import java.util.HashSet;
import java.util.Set;

// Class name used compare 'team input' from keyboard vs key map in here
public class PlayerKeys {
    private static final Set<String> playerNames = new HashSet<>();

    // Key of Team
    static {
        playerNames.add("Cristiano Ronaldo");
        playerNames.add("Lionel Messi");
        playerNames.add("Kylian Mbappé");
        playerNames.add("Erling Braut Haaland");
        playerNames.add("Lamine Yamal");
    }

    // Return the set of team names
    public static Set<String> getPlayerNames() {
        return playerNames;
    }

    // Check if a team name is valid
    public static boolean isValidTeam(String teamName) {
        return playerNames.contains(teamName);
    }
}
