package ru.glaizier.storage;

public class MemoryKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public V delete(K key) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
