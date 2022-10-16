package com.github.shortlinks.service.components;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class LruCache<K, V> implements Cache<K, V> {

    private static final int DEFAULT_CACHE_SIZE = 100;
    private final Map<K, V> cacheStorage;
    private final Function<K, V> remappingFunction;

    public LruCache(Function<K, V> remappingFunction, int maxSize) {
        this.remappingFunction = remappingFunction;

        if (maxSize < 0) {
            throw new IllegalArgumentException("Cache size can't be less than zero");
        }

        this.cacheStorage = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    public LruCache(Function<K, V> remappingFunction) {
        this(remappingFunction, DEFAULT_CACHE_SIZE);
    }

    @Override
    public Optional<V> read(K key) {
        return Optional.ofNullable(cacheStorage.get(key));
    }

    @Override
    public void save(K key, V value) {
        cacheStorage.put(key, value);
    }

    @Override
    public V readOrCompute(K key) {
        return cacheStorage.computeIfAbsent(key, remappingFunction);
    }
}
