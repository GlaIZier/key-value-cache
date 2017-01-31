package ru.glaizier.storage;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DiskKeyValueStorageTest extends Assert {

    private static final String BASE_PATH = "/tmp/key-value-storage/";

    private KeyValueStorage<Set<Integer>, List<String>> storage =
            new DiskKeyValueStorage<>(BASE_PATH);

    private KeyValueStorage<Object, Object> storage1 = new DiskKeyValueStorage<>(BASE_PATH);

    @Test
    public void get() throws Exception {

    }

    @Test
    public void put() throws Exception {
        Set<Integer> s = new HashSet<>();
        s.add(1);
        s.add(2);
        List<String> l = new ArrayList<>();
        l.add("1");
        l.add("2");
        storage.put(s, l);
    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void contains() throws Exception {

    }

    @Test
    public void getSize() throws Exception {

    }

}