package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.Map;

public interface Cache<K, V> extends KeyValueStorage<K, V> {

    Map.Entry<K, V> putAndGetEvicted(K key, V value);

    /**
     * Removes first candidate to remove from cache
     *
     * @return key-value of removed candidate
     */
    Map.Entry<K, V> evict();

    int getCapacity();

}
