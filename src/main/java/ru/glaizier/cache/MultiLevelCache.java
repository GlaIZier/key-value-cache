package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.List;
import java.util.Map;

public class MultiLevelCache<K, V> implements Cache<K, V> {

    private final List<Cache<K, V>> levels;

    // TODO think about transfer from levels algorithm
    public MultiLevelCache(List<Cache<K, V>> levels) {
        assert levels != null;
        assert !levels.isEmpty();

        this.levels = levels;
    }

    @Override
    public V get(K key) {
        if (!contains(key))
            return null;
        return levels.stream().filter(c -> contains(key)).findFirst().
                orElseThrow(() -> new IllegalStateException("Multilevel cache contains value but couldn't get one!"))
                .get(key);
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public boolean contains(K key) {
        return levels.stream().anyMatch(c -> c.contains(key));
    }

    @Override
    public int getSize() {
        return levels.stream().mapToInt(KeyValueStorage::getSize).sum();
    }

    @Override
    public Map.Entry<K, V> putAndGetEvicted(K key, V value) {
        return null;
    }

    @Override
    public Map.Entry<K, V> evict() {
        return null;
    }

    @Override
    public int getMaxSize() {
        return levels.stream().mapToInt(Cache::getMaxSize).sum();
    }
}
