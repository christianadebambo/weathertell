package com.example.adebambo_christian_s2111002;

/* Christian Adebambo - S2111002 */

import java.util.HashMap;
import java.util.Map;

public class LocationUtil {
    private static final Map<String, String> LOCATION_MAP = new HashMap<>();

    static {
        // location IDs and names
        LOCATION_MAP.put("2648579", "Glasgow");
        LOCATION_MAP.put("2643743", "London");
        LOCATION_MAP.put("5128581", "New York");
        LOCATION_MAP.put("287286", "Oman");
        LOCATION_MAP.put("934154", "Mauritius");
        LOCATION_MAP.put("1185241", "Bangladesh");
    }

    // Returns the name of the location given its ID
    public static String getLocationNameById(String id) {
        return LOCATION_MAP.getOrDefault(id, "Unknown Location");
    }
}
