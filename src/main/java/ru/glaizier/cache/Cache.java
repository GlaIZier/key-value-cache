package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.Map;

public interface Cache<K, V> extends KeyValueStorage<K, V> {

    /**
     * Removes first candidate to remove from cache
     *
     * @return
     */
    Map.Entry<K, V> removeCandidate();

    int getMaxSize();

}
