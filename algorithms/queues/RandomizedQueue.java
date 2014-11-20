import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The RandomizedQueue is similar to a stack or queue, except that the item
 * removed is chosen uniformly at random. (Using resizing array)
 * 
 * @author Shuai Wang
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items; // items of items
    private int size; // number of elements in RandomizedQueue

    /**
     * Initializes an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    /**
     * Is the queue empty?
     * 
     * @return true if the queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items on the queue
     * 
     * @return the number of items on the queue
     */
    public int size() {
        return size;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            temp[i] = items[i];
        items = temp;
    }

    /**
     * Add an item to the queue
     * 
     * @param item
     *            the item to add
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Null item added");
        if (size == items.length)
            resize(2 * items.length); // double size of array if necessary
        items[size++] = item;
    }

    /**
     * Removes and returns a random item
     * 
     * @return a random item from the queue
     */
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException("Randomized Queue underflow");
        int pos = StdRandom.uniform(size);
        Item item = items[pos];
        items[pos] = items[--size];
        items[size] = null;
        // shrink size of items if necessary
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    /**
     * Returns (but not deletes) a random item
     * 
     * @return a random item
     */
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException("Randomized Queue underflow");
        int pos = StdRandom.uniform(size);
        return items[pos];
    }

    /**
     * Returns and independent iterator over the items in random order
     * 
     * @return an iterator over the items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    // an iterator
    private class RandomIterator implements Iterator<Item> {
        private int i = size;
        private Item[] shuffledItems;

        public RandomIterator() {
            shuffledItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, shuffledItems, 0, size);
            StdRandom.shuffle(shuffledItems);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return shuffledItems[--i];
        }
    }
}
