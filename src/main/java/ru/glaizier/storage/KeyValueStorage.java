package ru.glaizier.storage;

public interface KeyValueStorage<K, V> {

    V get(K key);

    /**
     * @return previous value or null if there was no such value
     */
    V put(K key, V value);

    /**
     * @return previous value or null if there was no such value. Or return null when no such key.
     * Use contains to handle this situation
     */
    V remove(K key);

    boolean contains(K key);

    int getSize();

}
