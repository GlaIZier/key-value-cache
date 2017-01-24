package ru.glaizier.util;

import org.junit.Assert;
import org.junit.Test;

public class LinkedHashSetTest extends Assert {

    private LinkedHashSet<Integer> l = new LinkedHashSet<>();

    @Test
    public void add() throws Exception {
        l.add(1);
        l.add(null);
        l.add(-1);
        assertTrue(l.contains(1));
        assertTrue(l.contains(null));
        assertTrue(l.contains(-1));
        assertEquals(Integer.valueOf(-1), l.getTail());
    }

    @Test
    public void addToHead() throws Exception {
        l.addToHead(null);
        l.addToHead(2);
        l.addToHead(1);
        assertTrue(l.contains(null));
        assertTrue(l.contains(2));
        assertTrue(l.contains(1));
        assertEquals(Integer.valueOf(1), l.getHead());
        assertEquals(null, l.getTail());
    }

    @Test
    public void contains() throws Exception {
        l.add(Integer.MAX_VALUE);
        l.add(1);
        l.add(null);
        assertTrue(l.contains(Integer.MAX_VALUE));
        assertTrue(l.contains(1));
        assertTrue(l.contains(null));
        assertFalse(l.contains(Integer.MIN_VALUE));
        l.remove(Integer.MAX_VALUE);
        l.remove(null);
        assertTrue(l.contains(1));
        assertFalse(l.contains(Integer.MAX_VALUE));
        assertFalse(l.contains(null));
        assertFalse(l.contains(Integer.MIN_VALUE));
    }

    @Test
    public void removeWhenOneElementPresent() throws Exception {
        l.add(100);
        assertTrue(l.contains(100));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(100), l.getTail());
        l.remove(100);
        assertFalse(l.contains(100));
        assertEquals(null, l.getHead());
        assertEquals(null, l.getTail());
    }

    @Test
    public void removeHead() throws Exception {
        l.add(100);
        l.add(101);
        l.add(-1);
        l.add(102);
        assertTrue(l.contains(100));
        assertTrue(l.contains(101));
        assertTrue(l.contains(-1));
        assertTrue(l.contains(102));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(102), l.getTail());
        l.remove(100);
        assertFalse(l.contains(100));
        assertTrue(l.contains(101));
        assertTrue(l.contains(-1));
        assertTrue(l.contains(102));
        assertEquals(Integer.valueOf(101), l.getHead());
        assertEquals(Integer.valueOf(102), l.getTail());
    }

    @Test
    public void removeTail() throws Exception {
        l.add(100);
        l.add(-1);
        l.add(101);
        l.add(102);
        assertTrue(l.contains(100));
        assertTrue(l.contains(-1));
        assertTrue(l.contains(101));
        assertTrue(l.contains(102));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(102), l.getTail());
        l.remove(102);
        assertFalse(l.contains(102));
        assertTrue(l.contains(100));
        assertTrue(l.contains(-1));
        assertTrue(l.contains(101));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(101), l.getTail());
    }

    @Test
    public void removeMiddle() throws Exception {
        l.add(100);
        l.add(null);
        l.add(102);
        assertTrue(l.contains(100));
        assertTrue(l.contains(null));
        assertTrue(l.contains(102));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(102), l.getTail());
        l.remove(null);
        assertFalse(l.contains(null));
        assertTrue(l.contains(100));
        assertTrue(l.contains(102));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(102), l.getTail());
    }

    @Test
    public void getHead() throws Exception {
        l.add(Integer.MAX_VALUE);
        l.add(1);
        l.add(100);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), l.getHead());
    }

    @Test
    public void getTail() throws Exception {
        l.add(89);
        l.add(3);
        l.add(100);
        assertEquals(Integer.valueOf(100), l.getTail());
    }

    @Test
    public void notPresent() throws Exception {
        assertFalse(l.contains(-1));
        assertFalse(l.contains(null));
        assertFalse(l.remove(-1));
        assertFalse(l.remove(null));
        assertEquals(null, l.getHead());
        assertEquals(null, l.getTail());
        l.add(-1);
        assertTrue(l.contains(-1));
        assertFalse(l.contains(null));
        assertTrue(l.remove(-1));
        assertFalse(l.remove(null));
        l.add(null);
        assertFalse(l.add(null));
        assertFalse(l.addToHead(null));
    }

    @Test
    public void dealNulls() throws Exception {
        assertEquals(null, l.getHead());
        assertEquals(null, l.getTail());
        assertFalse(l.contains(null));
        assertFalse(l.remove(null));

        assertTrue(l.add(null));
        assertEquals(null, l.getHead());
        assertEquals(null, l.getTail());
        assertTrue(l.contains(null));

        assertTrue(l.remove(null));
        assertEquals(null, l.getHead());
        assertEquals(null, l.getTail());
        assertFalse(l.contains(null));
    }

}