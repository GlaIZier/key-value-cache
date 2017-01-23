package ru.glaizier.cache;

import ru.glaizier.storage.KeyValueStorage;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LruCache<K, V> extends AbstractCache<K, V> {

    public LruCache(KeyValueStorage<K, V> storage, int maxSize) {
        super(storage, maxSize);
    }

    @Override
    public Map.Entry<K, V> removeCandidate() {
        return null;
    }
}
