package ru.glaizier.storage;

public interface KeyValueStorage<K, V> {

    V get(K key);

    void put(K key, V value);

    V delete(K key);

    int getSize();

}
