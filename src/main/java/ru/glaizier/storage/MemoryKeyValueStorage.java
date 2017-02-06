package ru.glaizier.storage;

import java.util.HashMap;
import java.util.Map;

public class MemoryKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    private final Map<K, V> map = new HashMap<>();

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    @Override
    public int getSize() {
        return map.size();
    }

}
