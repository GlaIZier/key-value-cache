package ru.glaizier.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.glaizier.storage.MemoryKeyValueStorage;

import java.util.AbstractMap;

public class AbstractCacheTest extends Assert {

    private Cache<Integer, String> c = new LruCache<>(new MemoryKeyValueStorage<>(), 3);

    @Before
    public void init() {
        c.put(1, "1");
        c.put(null, "2");
        c.put(3, null);
    }

    @Test
    public void remove() throws Exception {
        assertTrue(c.contains(1));
        assertEquals("1", c.remove(1));
        assertFalse(c.contains(1));

        assertFalse(c.contains(100500));
        assertEquals(null, c.remove(100500));
        assertFalse(c.contains(100500));

        assertTrue(c.contains(null));
        assertEquals("2", c.remove(null));
        assertFalse(c.contains(null));

        assertTrue(c.contains(3));
        assertEquals(null, c.remove(3));
        assertFalse(c.contains(3));

        assertEquals(null, c.remove(1));
        assertEquals(null, c.remove(null));
        assertEquals(null, c.remove(3));

    }

    @Test
    public void evict() throws Exception {
        assertEquals(new AbstractMap.SimpleEntry<>(1, "1"), c.evict());
        assertEquals(new AbstractMap.SimpleEntry<>(null, "2"), c.evict());
        c.put(4, "4");
        assertEquals(new AbstractMap.SimpleEntry<>(3, null), c.evict());
        assertEquals(new AbstractMap.SimpleEntry<>(4, "4"), c.evict());
        assertEquals(null, c.evict());
        assertEquals(null, c.evict());
    }

    @Test
    public void contains() throws Exception {
        assertTrue(c.contains(1));
        assertTrue(c.contains(null));
        assertTrue(c.contains(3));
        c.evict();
        assertFalse(c.contains(1));
        assertFalse(c.contains(-1));
        assertFalse(c.contains(Integer.MAX_VALUE));

    }

    @Test
    public void getSize() throws Exception {
        assertEquals(3, c.getSize());
        c.evict();
        assertEquals(2, c.getSize());
        c.remove(3);
        assertEquals(1, c.getSize());
        c.put(4, "4");
        assertEquals(2, c.getSize());
        c.put(4, "5");
        assertEquals(2, c.getSize());
        c.put(5, "5");
        assertEquals(3, c.getSize());
        c.put(6, "6");
        assertEquals(3, c.getSize());
        c.put(7, "7");
        assertEquals(3, c.getSize());

    }

    @Test
    public void getCapacity() throws Exception {
        assertEquals(3, c.getCapacity());
        c.put(4, "4");
        assertEquals(3, c.getCapacity());
        c.evict();
        assertEquals(3, c.getCapacity());
    }

}