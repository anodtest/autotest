package com.anodtester.Config;

import java.util.HashMap;
import java.util.Map;

public class ButtonLabelProvider {
    public static Map<String, String> createButtonLabels() {
        Map<String, String> labels = new HashMap<>();

        // Add button text for each language
        labels.put("en_yes", "Yes");          // English
        labels.put("en_no", "No");            // English
        labels.put("vi_yes", "Có");           // Vietnamese
        labels.put("vi_no", "Không");         // Vietnamese
        labels.put("bra_yes", "Sim");           // Brazil
        labels.put("bra_no", "Não");            // Brazil

        return labels; // Return the map containing button text
    }
}
