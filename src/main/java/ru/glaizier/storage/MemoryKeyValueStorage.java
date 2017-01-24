package ru.glaizier.storage;

public class MemoryKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
