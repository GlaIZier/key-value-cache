package ru.glaizier.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.glaizier.storage.MemoryKeyValueStorage;

import java.util.AbstractMap;
import java.util.Arrays;

public class MultiLevelCacheTest extends Assert {

    private MultiLevelCache<Integer, String> c;

    @Before
    public void init() {
        Cache<Integer, String> l1 = new LruCache<>(new MemoryKeyValueStorage<>(), 1);
        Cache<Integer, String> l2 = new LruCache<>(new MemoryKeyValueStorage<>(), 2);
        Cache<Integer, String> l3 = new LruCache<>(new MemoryKeyValueStorage<>(), 3);

        l1.put(1, "1");
        l2.put(null, "2");
        l2.put(3, null);
        l3.put(4, "4");
        l3.put(5, "5");
        l3.put(6, "6");

        c = new MultiLevelCache<>(Arrays.asList(l1, l2, l3));
    }

    @Test
    public void get() throws Exception {
        assertEquals("1", c.get(1));
        assertEquals("2", c.get(null));
        assertEquals(null, c.get(3));
        assertEquals("4", c.get(4));
        assertEquals("5", c.get(5));
        assertEquals("6", c.get(6));
        assertEquals(new AbstractMap.SimpleEntry<>(1, "1"), c.putAndGetEvicted(7, "7"));
        assertEquals(new AbstractMap.SimpleEntry<>(null, "2"), c.putAndGetEvicted(8, "8"));
        assertEquals(new AbstractMap.SimpleEntry<>(3, null), c.putAndGetEvicted(9, "9"));
        assertEquals(new AbstractMap.SimpleEntry<>(4, "4"), c.putAndGetEvicted(10, "10"));
        assertEquals(new AbstractMap.SimpleEntry<>(5, "5"), c.putAndGetEvicted(11, "11"));
        assertEquals(new AbstractMap.SimpleEntry<>(6, "6"), c.putAndGetEvicted(12, "12"));

        assertEquals(null, c.get(100));
        assertEquals(null, c.putAndGetEvicted(7, "-7"));
        assertEquals(new AbstractMap.SimpleEntry<>(8, "8"), c.putAndGetEvicted(13, "13"));
        // 13 7 12 11 10 9
        assertEquals(null, c.putAndGetEvicted(12, "-12"));
        // 12  13 7 11 10 9
        assertEquals(new AbstractMap.SimpleEntry<>(9, "9"), c.putAndGetEvicted(14, "14"));
        assertEquals(new AbstractMap.SimpleEntry<>(10, "10"), c.putAndGetEvicted(15, "15"));
        assertEquals(new AbstractMap.SimpleEntry<>(11, "11"), c.putAndGetEvicted(16, "16"));
        assertEquals(new AbstractMap.SimpleEntry<>(7, "-7"), c.putAndGetEvicted(17, "17"));
    }

    @Test
    public void put() throws Exception {
        assertEquals(null, c.put(7, "7"));
        assertEquals("7", c.put(7, "-7"));
        assertEquals(null, c.put(8, "8"));
        assertEquals(null, c.put(9, "9"));
        assertEquals(null, c.put(10, "10"));
        assertEquals(null, c.put(11, "11"));
        assertEquals(null, c.put(12, "12"));

        assertFalse(c.contains(1));
        assertFalse(c.contains(null));
        assertFalse(c.contains(3));
        assertFalse(c.contains(4));
        assertFalse(c.contains(5));
        assertFalse(c.contains(6));

        assertTrue(c.contains(7));
        assertTrue(c.contains(8));
        assertTrue(c.contains(9));
        assertTrue(c.contains(10));
        assertTrue(c.contains(11));
        assertTrue(c.contains(12));

        assertEquals(6, c.getSize());
        assertEquals(6, c.getCapacity());

        assertEquals(null, c.put(13, "13"));
        assertFalse(c.contains(7));
        assertTrue(c.contains(13));
        assertTrue(c.contains(10));
        assertEquals("10", c.put(10, "-10"));
        assertEquals("-10", c.get(10));
        assertEquals(6, c.getSize());
    }

    @Test
    public void putAndGetEvicted() throws Exception {
        assertEquals(new AbstractMap.SimpleEntry<>(4, "4"), c.putAndGetEvicted(7, "7"));
        assertEquals(null, c.putAndGetEvicted(7, "7"));
        assertEquals(new AbstractMap.SimpleEntry<>(5, "5"), c.putAndGetEvicted(8, "8"));
        assertEquals(new AbstractMap.SimpleEntry<>(6, "6"), c.putAndGetEvicted(9, "9"));
        assertEquals(new AbstractMap.SimpleEntry<>(null, "2"), c.putAndGetEvicted(10, "10"));
        assertEquals(new AbstractMap.SimpleEntry<>(3, null), c.putAndGetEvicted(11, "11"));
        assertEquals(new AbstractMap.SimpleEntry<>(1, "1"), c.putAndGetEvicted(12, "12"));

        assertFalse(c.contains(1));
        assertFalse(c.contains(null));
        assertFalse(c.contains(3));
        assertFalse(c.contains(4));
        assertFalse(c.contains(5));
        assertFalse(c.contains(6));

        assertTrue(c.contains(7));
        assertTrue(c.contains(8));
        assertTrue(c.contains(9));
        assertTrue(c.contains(10));
        assertTrue(c.contains(11));
        assertTrue(c.contains(12));

        assertEquals(6, c.getSize());
        assertEquals(6, c.getCapacity());

        assertEquals(new AbstractMap.SimpleEntry<>(7, "7"), c.putAndGetEvicted(13, "13"));
        assertFalse(c.contains(7));
        assertTrue(c.contains(13));
        assertTrue(c.contains(10));
        assertEquals(null, c.putAndGetEvicted(10, "-10"));
        assertEquals("-10", c.get(10));
    }

    @Test
    public void putAndGetEvictedTheSameOnDifferentLevels() throws Exception {
        assertEquals(null, c.putAndGetEvicted(6, "-6"));
        assertEquals(null, c.putAndGetEvicted(3, "3"));
        assertEquals(null, c.putAndGetEvicted(1, "-1"));
        assertEquals(null, c.putAndGetEvicted(5, "-5"));
        assertEquals(null, c.putAndGetEvicted(4, "-4"));
        assertEquals(null, c.putAndGetEvicted(null, "-2"));
    }

    @Test
    public void remove() throws Exception {
        assertEquals(null, c.remove(0));
        assertEquals(null, c.remove(Integer.MAX_VALUE));
        assertEquals(null, c.remove(Integer.MIN_VALUE));

        assertEquals("5", c.remove(5));
        assertFalse(c.contains(5));
        assertTrue(c.contains(6));
        assertTrue(c.contains(3));
        assertTrue(c.contains(1));

        assertEquals("4", c.remove(4));
        assertFalse(c.contains(4));
        assertTrue(c.contains(6));
        assertTrue(c.contains(3));
        assertTrue(c.contains(1));

        assertEquals("1", c.remove(1));
        assertFalse(c.contains(1));
        assertTrue(c.contains(6));
        assertTrue(c.contains(3));

        assertEquals("2", c.remove(null));
        assertFalse(c.contains(null));
        assertTrue(c.contains(6));
        assertTrue(c.contains(3));

        assertEquals("6", c.remove(6));
        assertFalse(c.contains(6));
        assertTrue(c.contains(3));

        assertEquals(null, c.remove(1000));
        assertFalse(c.contains(1000));
        assertTrue(c.contains(3));

        assertTrue(c.contains(3));
        assertEquals(null, c.remove(3));
        assertFalse(c.contains(3));
    }

    @Test
    public void evict() throws Exception {
        assertEquals(new AbstractMap.SimpleEntry<>(4, "4"), c.evict());
        assertFalse(c.contains(4));
        assertTrue(c.contains(1));
        assertTrue(c.contains(3));
        assertTrue(c.contains(5));

        assertEquals(new AbstractMap.SimpleEntry<>(5, "5"), c.evict());
        assertFalse(c.contains(5));
        assertTrue(c.contains(1));
        assertTrue(c.contains(3));
        assertTrue(c.contains(6));

        assertEquals(new AbstractMap.SimpleEntry<>(6, "6"), c.evict());
        assertFalse(c.contains(6));
        assertTrue(c.contains(1));
        assertTrue(c.contains(3));

        assertEquals(new AbstractMap.SimpleEntry<>(null, "2"), c.evict());
        assertFalse(c.contains(null));
        assertTrue(c.contains(1));
        assertTrue(c.contains(3));

        assertEquals(new AbstractMap.SimpleEntry<>(3, null), c.evict());
        assertFalse(c.contains(3));
        assertTrue(c.contains(1));
        assertEquals(new AbstractMap.SimpleEntry<>(1, "1"), c.evict());
        assertFalse(c.contains(1));

        assertEquals(null, c.evict());
    }

    @Test
    public void contains() throws Exception {
        assertFalse(c.contains(0));
        assertFalse(c.contains(Integer.MAX_VALUE));
        assertFalse(c.contains(Integer.MIN_VALUE));

        assertTrue(c.contains(1));
        assertTrue(c.contains(null));
        assertTrue(c.contains(3));
        assertTrue(c.contains(4));
        assertTrue(c.contains(5));
        assertTrue(c.contains(6));
    }

    @Test
    public void getSize() throws Exception {
        assertEquals(6, c.getSize());
    }

    @Test
    public void getCapacity() throws Exception {
        assertEquals(6, c.getCapacity());
    }

}