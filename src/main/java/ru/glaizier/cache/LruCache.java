package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.Map;

public class LruCache<K, V> extends AbstractCache<K, V> {

    public LruCache(KeyValueStorage<K, V> storage, int capacity) {
        super(storage, capacity);
    }

    @Override
    public V get(K key) {
        if (!contains(key))
            return null;
        moveKeyToTail(key);
        return storage.get(key);
    }

    @Override
    public V put(K key, V value) {
        V oldValue = abstractPut(key, value);
        moveKeyToTail(key);
        return oldValue;
    }

    @Override
    public Map.Entry<K, V> putAndGetEvicted(K key, V value) {
        Map.Entry<K, V> evicted = abstractPutAndGetEvicted(key, value);
        moveKeyToTail(key);
        return evicted;
    }

    private void moveKeyToTail(K key) {
        evictQueue.remove(key);
        evictQueue.add(key);
    }
}
