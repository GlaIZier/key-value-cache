package ru.glaizier.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DiskKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    private final Map<K, String> keyToFilePath = new HashMap<>();

    private final ObjectMapper mapper = new ObjectMapper();

    private int lastFileIndex = 1;

    private final String basePath;

    private final Class<K> keyType;

    private final Class<V> valueType;

    public DiskKeyValueStorage(String basePath, Class<K> keyType, Class<V> valueType) {
        if (basePath == null || "".equals(basePath) || keyType == null || valueType == null)
            throw new IllegalArgumentException("Illegal argument in DiskKeyValueStorage constructor! " +
                    "basePath must be not empty, keyType not null, valueType not null");

        if (basePath.charAt(basePath.length() - 1) != File.separatorChar)
            basePath += File.separator;
        this.basePath = basePath;
        this.keyType = keyType;
        this.valueType = valueType;
        try {
            Files.createDirectories(Paths.get(this.basePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public V get(K key) {
        if (!contains(key))
            return null;

        String filePath = keyToFilePath.get(key);
        File file = new File(filePath);
        try {
            Pair<K, V> entry = mapper.readValue(file, mapper.getTypeFactory().constructParametricType(Pair.class,
                    keyType, valueType));
            return entry.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        V result = remove(key);

        Pair<K, V> entry = new Pair<>(key, value);
        String filePath = basePath + lastFileIndex++;
        File file = new File(filePath);
        try {
            file.createNewFile();
            mapper.writeValue(file, entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyToFilePath.put(key, filePath);
        return result;
    }

    @Override
    public V remove(K key) {
        if (!contains(key))
            return null;
        V result = get(key);
        String filePath = keyToFilePath.remove(key);
        File file = new File(filePath);
        file.delete();
        return result;
    }

    @Override
    public boolean contains(K key) {
        return keyToFilePath.containsKey(key);
    }

    @Override
    public int getSize() {
        return keyToFilePath.size();
    }

    private static class Pair<K, V> {

        private K key;

        private V value;

        @JsonCreator
        private Pair(@JsonProperty("key") K key, @JsonProperty("value") V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

}
