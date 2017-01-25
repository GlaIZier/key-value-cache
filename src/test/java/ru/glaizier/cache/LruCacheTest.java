package ru.glaizier.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.glaizier.storage.MemoryKeyValueStorage;

import java.util.AbstractMap;

public class LruCacheTest extends Assert {

    private Cache<Integer, String> c = new LruCache<>(new MemoryKeyValueStorage<>(), 3);

    @Before
    public void init() {
        c.put(1, "1");
        c.put(null, "2");
        c.put(3, null);
    }

    @Test
    public void get() throws Exception {
        assertEquals("1", c.get(1));
        assertEquals("2", c.get(null));
        assertEquals(null, c.get(3));
        assertEquals(null, c.get(4));
        c.evict();
        assertEquals(null, c.get(1));
        c.put(4, "4");
        c.put(5, "5");
        assertEquals(null, c.get(null));
        assertEquals("4", c.get(4));
        assertEquals("5", c.get(5));
    }

    @Test
    public void put() throws Exception {
        assertEquals("1", c.get(1));
        assertEquals("2", c.get(null));
        assertEquals(null, c.get(3));

        assertEquals(null, c.put(4, "4"));
        assertEquals("4", c.get(4));
        assertEquals(null, c.get(1));
        assertFalse(c.contains(1));
        assertTrue(c.contains(4));
        assertTrue(c.contains(null));
        assertTrue(c.contains(3));

        assertEquals("4", c.put(4, "-4"));
        assertEquals("-4", c.get(4));
        assertEquals(null, c.get(1));
        assertFalse(c.contains(1));
        assertTrue(c.contains(4));
        assertTrue(c.contains(null));
        assertTrue(c.contains(3));

        assertEquals(null, c.put(5, "5"));
        assertEquals("5", c.get(5));
        assertEquals(null, c.get(null));
        assertFalse(c.contains(1));
        assertFalse(c.contains(null));
        assertTrue(c.contains(5));
        assertTrue(c.contains(4));
        assertTrue(c.contains(3));

        assertEquals(3, c.getSize());
        c.evict();
        c.evict();
        c.evict();
        assertEquals(0, c.getSize());
        assertEquals(null, c.put(6, "6"));
        assertEquals(null, c.put(7, "7"));
        assertEquals(null, c.put(8, "8"));
        assertEquals("8", c.put(8, "8"));
        assertTrue(c.contains(6));
        assertTrue(c.contains(7));
        assertTrue(c.contains(8));
        assertEquals(3, c.getSize());
    }

    @Test
    public void putAndGetEvicted() {
        assertEquals("1", c.get(1));
        assertEquals("2", c.get(null));
        assertEquals(null, c.get(3));

        assertEquals(new AbstractMap.SimpleEntry<>(1, "1"), c.putAndGetEvicted(4, "4"));
        assertEquals("4", c.get(4));
        assertEquals(null, c.get(1));
        assertFalse(c.contains(1));
        assertTrue(c.contains(4));
        assertTrue(c.contains(null));
        assertTrue(c.contains(3));

        assertEquals(null, c.putAndGetEvicted(4, "-4"));
        assertEquals("-4", c.get(4));
        assertEquals(null, c.get(1));
        assertFalse(c.contains(1));
        assertTrue(c.contains(4));
        assertTrue(c.contains(null));
        assertTrue(c.contains(3));

        assertEquals(new AbstractMap.SimpleEntry<>(null, "2"), c.putAndGetEvicted(5, "5"));
        assertEquals("5", c.get(5));
        assertEquals(null, c.get(null));
        assertFalse(c.contains(1));
        assertFalse(c.contains(null));
        assertTrue(c.contains(5));
        assertTrue(c.contains(4));
        assertTrue(c.contains(3));

        assertEquals(3, c.getSize());
        assertEquals(new AbstractMap.SimpleEntry<>(3, null), c.putAndGetEvicted(6, "6"));
        assertEquals(new AbstractMap.SimpleEntry<>(4, "-4"), c.putAndGetEvicted(7, "7"));
        c.evict();
        c.evict();
        c.evict();
        assertEquals(0, c.getSize());
        assertEquals(null, c.putAndGetEvicted(6, "6"));
        assertEquals(null, c.putAndGetEvicted(7, "7"));
        assertEquals(null, c.putAndGetEvicted(8, "8"));
        assertEquals(null, c.putAndGetEvicted(8, "8"));
        assertTrue(c.contains(6));
        assertTrue(c.contains(7));
        assertTrue(c.contains(8));
        assertEquals(3, c.getSize());
    }

}