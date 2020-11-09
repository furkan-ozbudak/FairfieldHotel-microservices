package com.samiksha.eapxhistory.config;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private Map<String, Object> config;

    public Config() {
        config = new HashMap<>();
        config.put("pagination.size", 20);
    }

    public Object get(String key) {
        return config.get(key);
    }
}
