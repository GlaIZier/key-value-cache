package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class LruCache<K, V> extends AbstractCache<K, V> {

    private LinkedHashSet<K> removeCandidatesQueue = new LinkedHashSet<K>(getMaxSize());

    private LinkedHashMap<K, K> l = new LinkedHashMap<K, K>(getMaxSize());

    public LruCache(KeyValueStorage<K, V> storage, int maxSize) {
        super(storage, maxSize);
    }

    @Override
    public V removeCandidate(K key) {
        return null;
    }
}
