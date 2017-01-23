package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.List;
import java.util.Map;

public class MultiLevelCache<K, V> implements Cache<K, V> {

    private final List<Cache<K, V>> levels;

    public MultiLevelCache(List<Cache<K, V>> levels) {
        this.levels = levels;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public V delete(K key) {
        return null;
    }

    @Override
    public int getSize() {
        return levels.stream().mapToInt(KeyValueStorage::getSize).sum();
    }

    @Override
    public Map.Entry<K, V> removeCandidate() {
        return null;
    }

    @Override
    public int getMaxSize() {
        return levels.stream().mapToInt(Cache::getMaxSize).sum();
    }
}
