package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

public class MruCache<K, V> extends AbstractCache<K, V> {

    public MruCache(KeyValueStorage<K, V> storage, int maxSize) {
        super(storage, maxSize);
    }


    @Override
    public V get(K key) {
        if (!contains(key))
            return null;
        removeCandidatesQueue.remove(key);
        removeCandidatesQueue.addToHead(key);
        return storage.get(key);
    }

    @Override
    public V put(K key, V value) {
        V oldValue = abstractStoragePut(key, value);
        removeCandidatesQueue.remove(key);
        removeCandidatesQueue.addToHead(key);
        return oldValue;
    }
}
