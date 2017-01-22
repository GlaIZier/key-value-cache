package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

public abstract class AbstractCache<K, V> implements Cache<K, V> {

    private final KeyValueStorage<K, V> storage;

    private final int maxSize;

    AbstractCache(KeyValueStorage<K, V> storage, int maxSize) {
        this.storage = storage;
        this.maxSize = maxSize;
    }


    @Override
    public V get(K key) {
        return storage.get(key);
    }

    @Override
    public void put(K key, V value) {
        storage.put(key, value);
    }

    @Override
    public V delete(K key) {
        return storage.delete(key);
    }


    @Override
    public int getSize() {
        return storage.getSize();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

}
