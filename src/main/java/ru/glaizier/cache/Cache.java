package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

public interface Cache<K, V> extends KeyValueStorage<K, V> {

    V removeCandidate(K key);

    int getMaxSize();

}
