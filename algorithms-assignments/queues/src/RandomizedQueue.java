import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, except that the item
 * removed is chosen uniformly at random from items in the data structure.
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;

    /**
     * Randomized queue size.
     */
    private int size;

    /**
     * Constructs an empty randomized queue.
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
    }

    /**
     * Checks whether the randomized queue is empty.
     *
     * @return true if the randomized queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return (size <= 0);
    }

    /**
     * Returns the number of items on the randomized queue.
     *
     * @return the number of items
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item.
     * @param item the item to be added
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == a.length) {
            resize(2 * a.length);
        }
        a[size++] = item;
    }

    /**
     * Resizes the randomized queue up to the given capacity.
     * @param capacity the capacity
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++){
            copy[i] = a[i];
        }
        a = copy;
    }

    /**
     * Removes and returns a random item.
     *
     * @return a randomly selected item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item ans = a[index];
        if (index != size - 1) {
            a[index] = a[size - 1];
        }
        a[--size] = null;
        if (size >= 1 && size == a.length / 4) {
            resize(a.length / 2);
        }
        return ans;
    }

    /**
     * Returns a random item (but doesn't remove it).
     *
     * @return a randomly selected item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return a[StdRandom.uniform(size)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Represents an iterator of a randomized queue.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] copy;
        private int subSize = size;

        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[subSize];
            for (int i = 0; i < subSize; i++) {
                copy[i] = a[i];
            }
        }

        @Override
        public boolean hasNext() {
            return subSize > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int index = StdRandom.uniform(subSize);
            Item item = copy[index];
            if (index != subSize - 1) {
                copy[index] = copy[subSize - 1];
            }
            copy[--subSize] = null;
            return item;
        }
    }
}