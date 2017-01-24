package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

public class LruCache<K, V> extends AbstractCache<K, V> {

    public LruCache(KeyValueStorage<K, V> storage, int maxSize) {
        super(storage, maxSize);
    }

    @Override
    public V get(K key) {
        if (!contains(key))
            return null;
        evictQueue.remove(key);
        evictQueue.add(key);
        return storage.get(key);
    }

    @Override
    public V put(K key, V value) {
        V oldValue = abstractStoragePut(key, value);
        evictQueue.remove(key);
        evictQueue.add(key);
        return oldValue;
    }
}
