package ru.glaizier.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DiskKeyValueStorageTest extends Assert {

    //TODO base path for test conditional to OS
    private static final String BASE_PATH = "/tmp/key-value-cache/";

    private KeyValueStorage<Integer, String> s1 = new DiskKeyValueStorage<>(BASE_PATH, Integer.class, String.class);

    @Before
    public void init() {
        s1.put(1, "1");
        s1.put(2, "2");
        s1.put(3, "three");
        s1.put(null, "4");
        s1.put(5, null);
        s1.put(6, "null");
    }

    @Test
    public void get() throws Exception {
        assertEquals("1", s1.get(1));
        assertEquals("2", s1.get(2));
        assertEquals("three", s1.get(3));
        assertEquals("4", s1.get(null));
        assertEquals(null, s1.get(5));
        assertEquals("null", s1.get(6));
    }

    @Test
    public void put() throws Exception {
        assertEquals(null, s1.put(7, "7"));
        assertEquals(null, s1.put(8, "8"));

        assertEquals("1", s1.put(1, "one"));
        assertEquals("three", s1.put(3, "3"));
        assertEquals("4", s1.put(null, "four"));
        assertEquals(null, s1.put(5, "5"));
        assertEquals("null", s1.put(6, "6"));

        assertTrue(s1.contains(1));
        assertTrue(s1.contains(2));
        assertTrue(s1.contains(3));
        assertTrue(s1.contains(null));
        assertTrue(s1.contains(5));
        assertTrue(s1.contains(6));
        assertTrue(s1.contains(7));
        assertTrue(s1.contains(8));
    }

    @Test
    public void remove() throws Exception {
        assertEquals("1", s1.remove(1));
        assertEquals("2", s1.remove(2));
        assertEquals("three", s1.remove(3));
        assertEquals("4", s1.remove(null));
        assertEquals(null, s1.remove(5));
        assertEquals("null", s1.remove(6));

        assertFalse(s1.contains(1));
        assertFalse(s1.contains(2));
        assertFalse(s1.contains(3));
        assertFalse(s1.contains(null));
        assertFalse(s1.contains(5));
        assertFalse(s1.contains(6));

        assertEquals(null, s1.remove(1));
        assertEquals(null, s1.remove(2));
        assertEquals(null, s1.remove(3));
        assertEquals(null, s1.remove(null));
        assertEquals(null, s1.remove(5));
        assertEquals(null, s1.remove(6));
    }

    @Test
    public void contains() throws Exception {
        assertTrue(s1.contains(1));
        assertTrue(s1.contains(2));
        assertTrue(s1.contains(3));
        assertTrue(s1.contains(null));
        assertTrue(s1.contains(5));
        assertTrue(s1.contains(6));

        assertFalse(s1.contains(7));

        s1.remove(1);
        assertFalse(s1.contains(1));
        s1.put(1, "one");
        assertTrue(s1.contains(1));
    }

    @Test
    public void getSize() throws Exception {
        assertEquals(6, s1.getSize());
        s1.remove(1);
        assertEquals(5, s1.getSize());
        s1.remove(1);
        assertEquals(5, s1.getSize());
        s1.put(1, "oooone");
        assertEquals(6, s1.getSize());
    }

}