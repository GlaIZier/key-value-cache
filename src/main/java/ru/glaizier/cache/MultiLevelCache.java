package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class MultiLevelCache<K, V> implements Cache<K, V> {

    private final List<Cache<K, V>> levels;

    public MultiLevelCache(List<Cache<K, V>> levels) {
        assert levels != null;
        assert !levels.isEmpty();

        this.levels = levels;
    }

    @Override
    public V get(K key) {
        if (!contains(key))
            return null;
        // remove key from it's place in cache
        V value = remove(key);
        // add to the 1st level
        put(key, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        if (contains(key))
            oldValue = remove(key);
        putToFirstLevelAndGetEvicted(key, value);
        return oldValue;
    }

    @Override
    public Map.Entry<K, V> putAndGetEvicted(K key, V value) {
        //if it is already in cache on some level we remove it and put to first level
        if (contains(key))
            remove(key);
        return putToFirstLevelAndGetEvicted(key, value);
    }

    private Map.Entry<K, V> putToFirstLevelAndGetEvicted(K key, V value) {
        Map.Entry<K, V> evicted = new AbstractMap.SimpleEntry<>(key, value);
        for (Cache<K, V> level : levels) {
            evicted = level.putAndGetEvicted(evicted.getKey(), evicted.getValue());
            if (evicted == null)
                return null;
        }
        return evicted;
    }

    @Override
    public V remove(K key) {
        if (!contains(key))
            return null;
        return levels.stream().filter(c -> c.contains(key)).findFirst()
                .orElseThrow(() -> new IllegalStateException("Multilevel cache contains value but couldn't remove one!"))
                .remove(key);
    }

    @Override
    public Map.Entry<K, V> evict() {
        for (int levelIndex = levels.size() - 1; levelIndex >= 0; levelIndex--) {
            Map.Entry<K, V> evicted = levels.get(levelIndex).evict();
            if (evicted != null)
                return evicted;
        }
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
    public int getCapacity() {
        return levels.stream().mapToInt(Cache::getCapacity).sum();
    }
}
