import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (a generalization of a stack and a queue that supports
 * adding and removing items from either the front or the back of the data structure
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * First Node of the deque.
     */
    private Node<Item> first;

    /**
     * Last node of the deque.
     */
    private Node<Item> last;

    /**
     * Deque size.
     */
    private int size = 0;

    /**
     * Constructs an empty deque.
     */
    public Deque() {
        first = null;
        last = null;
    }

    /**
     * Checks whether the deque is empty.
     *
     * @return true if the deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return (size <= 0);
    }

    /**
     * Returns the number of items on the deque.
     *
     * @return the number of items
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to the front of the deque.
     *
     * @param item the item to be added
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> oldfirst = first;
        first = new Node<>(item);
        if (isEmpty()) {
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        size++;
    }

    /**
     * Add the item to the end of the deque.
     *
     * @param item the item to be added
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> oldlast = last;
        last = new Node<>(item);
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        size++;
    }

    /**
     * Removes and returns the item from the front.
     *
     * @return the front item
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (first.next != null) {
            first = first.next;
        }
        first.previous = null;
        size--;
        if (isEmpty()) {
            last = null;
            first = null;
        }
        return item;
    }

    /**
     * Removes and returns the item from the end.
     *
     * @return the end item
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (last.previous != null) {
            last = last.previous;
        }
        last.next = null;
        size--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return item;
    }

    /**
     * Returns an iterator over items in order from front to end.
     *
     * @return the iterator instance
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Represents an iterator of a deque.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Represents a node of a deque.
     *
     * @param <Item>
     */
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;

        private Node(Item item) {
            this.item = item;
            next = null;
            previous = null;
        }
    }
}