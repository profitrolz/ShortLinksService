package com.github.shortlinks.service.components;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class LruCache implements Cache<String, String> {

    private static final int DEFAULT_CACHE_SIZE = 100;
    private final Map<String, String> cacheStorage;
    private final Function<String, String> remappingFunction;

    public LruCache(Function<String, String> remappingFunction, int maxSize) {
        this.remappingFunction = remappingFunction;

        if (maxSize < 0) {
            throw new IllegalArgumentException("Cache size can't be less than zero");
        }

        this.cacheStorage = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > maxSize;
            }
        };
    }

    public LruCache(Function<String, String> remappingFunction) {
        this(remappingFunction, DEFAULT_CACHE_SIZE);
    }

    @Override
    public Optional<String> read(String key) {
        return Optional.ofNullable(cacheStorage.get(key));
    }

    @Override
    public void save(String key, String value) {
        cacheStorage.put(key, value);
    }

    @Override
    public String readOrCompute(String key) {
        return cacheStorage.computeIfAbsent(key, remappingFunction);
    }
}
