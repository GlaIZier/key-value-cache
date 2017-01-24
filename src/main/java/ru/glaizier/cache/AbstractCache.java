package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;
import ru.glaizier.util.LinkedHashSet;

import java.util.AbstractMap;
import java.util.Map;

// TODO write tests for this
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    protected final KeyValueStorage<K, V> storage;

    protected final LinkedHashSet<K> removeCandidatesQueue = new LinkedHashSet<>();

    private final int maxSize;

    AbstractCache(KeyValueStorage<K, V> storage, int maxSize) {
        assert maxSize > 0;
        assert storage != null;

        this.storage = storage;
        this.maxSize = maxSize;
    }

    @Override
    public V remove(K key) {
        removeCandidatesQueue.remove(key);
        return storage.remove(key);
    }

    @Override
    public Map.Entry<K, V> removeCandidate() {
        K key = removeCandidatesQueue.getHead();
        // if we get null we need to check if we don't have such key in queue or a key == null
        if (!removeCandidatesQueue.contains(key))
            // queue is empty. Don't need to do anything
            return null;
        V value = remove(key);
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @Override
    public boolean contains(K key) {
        return removeCandidatesQueue.contains(key);
    }

    @Override
    public int getSize() {
        return storage.getSize();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    protected V abstractStoragePut(K key, V value) {
        if (getSize() == getMaxSize())
            removeCandidate();
        return storage.put(key, value);
    }

}
