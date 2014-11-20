import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Deque class is a generalization of a stack and queue that supports
 * inserting and removing items from either the front or the back of the data
 * structure. (Using doubly linked list)
 * 
 * @author Shuai Wang
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first; // reference to the beginning of deque
    private Node last; // reference to the end of deque
    private int size; // number of elements on deque

    // helper linked list class
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    /**
     * Constructs an empty Deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Is this deque empty?
     * 
     * @return true if this deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items on this deque
     * 
     * @return the number of items on this deque
     */
    public int size() {
        return size;
    }

    /**
     * Inserts item at the front of the deque
     * 
     * @item the item to insert
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("Null item added");
        if (size == 0) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            first.prev = null;
            oldFirst.prev = first;
        }
        size++;
        assert check();
    }

    /**
     * Inserts the item at the end of the deque
     * 
     * @item the item to insert
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("Null item added");
        if (size == 0) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = null;
            first = last;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldLast;
            oldLast.next = last;
        }
        size++;
        assert check();
    }

    /**
     * Removes and returns the item at the front of the deque
     * 
     * @return the item at the front of the deque
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        Item item = first.item; // item to be removed
        if (size == 1) {
            first = null;
            last = first;
        } else {
            first = first.next;
            first.prev = null;
        }
        size--;
        assert check();
        return item;
    }

    /**
     * Removes and returns the item at the end of the deque
     * 
     * @return the item at the end of the deque
     */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        if (size == 1) {
            last = null;
            first = last;
        } else {
            last = last.prev;
            last.next = null;
        }
        size--;
        assert check();
        return item;
    }

    // check internal invariants
    private boolean check() {
        if (size == 0) {
            if (first != null)
                return false;
            if (last != null)
                return false;
        } else if (size == 1) {
            if (first == null || last == null)
                return false;
            if (first != last)
                return false;
            if (first.next != null || first.prev != null)
                return false;
        } else {
            if (first == last)
                return false;
            if (first.next == null || last.prev == null)
                return false;
            if (last.next != null || first.prev != null)
                return false;

            // check internal consistency of instance variable size
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != size)
                return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode)
                return false;
        }
        return true;
    }

    /**
     * Returns an iterator that iterates over items from front to end in the
     * deque
     * 
     * @return an iterator that iterates over items from front to end in the
     *         deque
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // an iterator
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
