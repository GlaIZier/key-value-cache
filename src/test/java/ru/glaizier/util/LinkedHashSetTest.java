package ru.glaizier.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedHashSetTest extends Assert {

    private LinkedHashSet<Integer> l = new LinkedHashSet<>();

    @Test
    public void add() throws Exception {
        l.add(1);
        l.add(200);
        l.add(-1);
        assertTrue(l.contains(1));
        assertTrue(l.contains(200));
        assertTrue(l.contains(-1));
        assertEquals(Integer.valueOf(-1), l.getTail());
    }

    @Test
    public void addToHead() throws Exception {
        l.add(3);
        l.add(2);
        l.add(1);
        assertTrue(l.contains(3));
        assertTrue(l.contains(2));
        assertTrue(l.contains(1));
        assertEquals(Integer.valueOf(3), l.getHead());
    }

    @Test
    public void contains() throws Exception {
        l.add(Integer.MAX_VALUE);
        l.add(1);
        assertTrue(l.contains(Integer.MAX_VALUE));
        assertTrue(l.contains(1));
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
        l.add(101);
        l.add(102);
        assertTrue(l.contains(100));
        assertTrue(l.contains(101));
        assertTrue(l.contains(102));
        assertEquals(Integer.valueOf(100), l.getHead());
        assertEquals(Integer.valueOf(102), l.getTail());
        l.remove(101);
        assertFalse(l.contains(101));
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

}