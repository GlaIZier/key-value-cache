package ru.glaizier.util;

import java.util.HashMap;

/**
 * Set with O(1) time for:
 * get by key
 * get tail
 * get head
 * remove by key
 * add to tail
 * add to head
 */
public class LinkedHashSet<E> {

    /**
     * head <-> e1 <-> ... <-> e2 <-> tail
     * <- previous
     * -> next
     */
    private class Node<T> {
        Node<T> previous;
        Node<T> next;
        T element;
        private Node(Node<T> previous, Node<T> next, T element){
            this.previous = previous;
            this.next = next;
            this.element = element;
        }
    }

    private final HashMap<E, Node<E>> map = new HashMap<>();
    private Node<E> head;
    private Node<E> tail;

    public LinkedHashSet() {}

    /**
     * @return true if this set did not already contain the specified
     * element
     */
    public boolean add(E e) {
        if (contains(e))
            return false;
        Node<E> node = new Node<>(null, null, e);
        // first element
        if (tail == null) {
            tail = node;
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
            tail = node;
        }
        map.put(e, node);
        return true;
    }

    public boolean addToHead(E e) {
        if (contains(e))
            return false;
        Node<E> node = new Node<>(null, null, e);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.previous = node;
            node.next = head;
            head = node;
        }
        map.put(e, node);
        return true;
    }

    public boolean contains(E e) {
        return map.containsKey(e);
    }

    /**
     * @return true if the set contained the specified element
     */
    public boolean remove(E e) {
        if (!contains(e))
            return false;
        Node<E> node = map.get(e);
        Node<E> prev = node.previous;
        Node<E> next = node.next;

        if (prev == null)
            head = next;
        else
            prev.next = next;
        if (next == null)
            tail = prev;
        else
            next.previous = prev;

        node.next = null;
        node.previous = null;
        map.remove(e);
        return true;
    }

    public E getHead() {
        return (head != null) ? head.element : null;
    }

    public E getTail() {
        return (tail != null) ? tail.element : null;
    }

}
