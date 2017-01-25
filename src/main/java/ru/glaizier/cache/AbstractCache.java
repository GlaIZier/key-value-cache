package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;
import ru.glaizier.util.LinkedHashSet;

import java.util.AbstractMap;
import java.util.Map;

public abstract class AbstractCache<K, V> implements Cache<K, V> {

    protected final KeyValueStorage<K, V> storage;

    protected final LinkedHashSet<K> evictQueue = new LinkedHashSet<>();

    private final int capacity;

    AbstractCache(KeyValueStorage<K, V> storage, int capacity) {
        assert capacity > 0;
        assert storage != null;

        this.storage = storage;
        this.capacity = capacity;
    }

    @Override
    public V remove(K key) {
        evictQueue.remove(key);
        return storage.remove(key);
    }

    @Override
    public Map.Entry<K, V> evict() {
        K key = evictQueue.getHead();
        // if we get null we need to check if we don't have such key in queue or a key == null
        if (!evictQueue.contains(key))
            // queue is empty. Don't need to do anything
            return null;
        V value = remove(key);
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @Override
    public boolean contains(K key) {
        return evictQueue.contains(key);
    }

    @Override
    public int getSize() {
        return storage.getSize();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    protected V abstractPut(K key, V value) {
        if (!contains(key) && getSize() == getCapacity())
            evict();
        return storage.put(key, value);
    }

    protected Map.Entry<K, V> abstractPutAndGetEvicted(K key, V value) {
        Map.Entry<K, V> evicted = null;
        if (!contains(key) && getSize() == getCapacity())
            evicted = evict();
        storage.put(key, value);
        return evicted;
    }

}
