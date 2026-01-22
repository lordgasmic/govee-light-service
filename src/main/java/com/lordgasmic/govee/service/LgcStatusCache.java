package com.lordgasmic.govee.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LgcStatusCache {

    private final Map<String, Boolean> statusCache;

    public LgcStatusCache() {
        statusCache = new ConcurrentHashMap<>();
    }

    public Map<String, Boolean> getStatusCache() {
        return statusCache;
    }

    public void put(final String key, final Boolean value) {
        statusCache.put(key, value);
    }
}
