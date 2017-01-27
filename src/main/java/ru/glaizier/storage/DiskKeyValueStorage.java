package ru.glaizier.storage;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * serialize to json
 * store in file serialized Map.Entry<K, V>
 */

public class DiskKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    private final Map<K, String> keyToFilePath = new HashMap<>();

    private final String basePath;

    private int lastFileIndex = 1;

    public DiskKeyValueStorage(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public V get(K key) {
        if (!contains(key))
            return null;

        String path = keyToFilePath.get(key);
        // deserialization
        // return Map.Entry implementation
        return null;
    }

    @Override
    public V put(K key, V value) {
        V result = get(key);
        Map.Entry<K, V> entry = new AbstractMap.SimpleEntry<>(key, value);
        // create filename
        // serialization
        // save
        return result;
    }

    @Override
    public V remove(K key) {
        if (!contains(key))
            return null;
        V result = get(key);
        String path = keyToFilePath.remove(key);
        // file remove
        return result;
    }

    @Override
    public boolean contains(K key) {
        return keyToFilePath.containsKey(key);
    }

    @Override
    public int getSize() {
        return keyToFilePath.size();
    }
}
