package ru.glaizier.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class DiskKeyValueStorage<K, V> implements KeyValueStorage<K, V> {

    private final Map<K, String> keyToFilePath = new HashMap<>();

    ObjectMapper mapper = new ObjectMapper();

    private final String basePath;

    private int lastFileIndex = 1;

    public DiskKeyValueStorage(String basePath) {
        if (basePath.charAt(basePath.length() - 1) != File.separatorChar)
            basePath += File.separator;
        this.basePath = basePath;
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
        AbstractMap.SimpleEntry<K, V> entry = null;
//        try {
//            entry = mapper.readValue(file, AbstractMap.SimpleEntry());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(entry.getKey());
        System.out.println(entry.getValue());
        // deserialization
        // return Map.Entry implementation
        return null;
    }

    @Override
    public V put(K key, V value) {
        V result = remove(key);


        Map.Entry<K, V> entry = new AbstractMap.SimpleEntry<>(key, value);
        String filePath = basePath + lastFileIndex++;
        File file = new File(filePath);
        try {
            file.createNewFile();
//            Files.createFile(Paths.get(filePath));
            System.out.println(mapper.writeValueAsString(entry));
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
}
