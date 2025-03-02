import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int MIN_SIZE = 2;

    private Item[] q;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[MIN_SIZE];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int newSize) {
        newSize = Math.max(newSize, MIN_SIZE);
        Item[] newQ = (Item[]) new Object[newSize];
        for (int i = 0; i < size; ++i) {
            newQ[i] = q[i];
        }
        q = newQ;
    }

    private void growIfNeeded() {
        if (q.length <= size) {
            resize(2 * q.length);
        }
    }

    private void shrinkIfNeeded() {
        if (size <= q.length / 4) {
            resize(q.length / 2);
        }
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");
    }

    /** Enqueue the item. I choose to add to the end like a stack */
    public void enqueue(Item item) {
        validateItem(item);
        growIfNeeded();
        q[size] = item;
        ++size;
    }

    private void checkEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("Randomized queue is empty");
    }

    private int getRandom() {
        return StdRandom.uniformInt(size);
    }

    private Item pop(int pos) {
        Item popped = q[pos];
        --size;
        q[pos] = q[size];
        q[size] = null;
        return popped;
    }

    /** Remove and return a random item */
    public Item dequeue() {
        checkEmpty();
        int random = getRandom();
        Item item = pop(random);
        shrinkIfNeeded();
        return item;
    }

    /** Return a random item (but do not remove it) */
    public Item sample() {
        checkEmpty();
        int random = getRandom();
        return q[random];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] samples;
        int i = 0;

        public RandomizedQueueIterator() {
            samples = (Item[]) new Object[size()];
            for (int j = 0; j < size(); ++j) {
                samples[i] = q[i];
            }
            StdRandom.shuffle(samples);
        }

        public boolean hasNext() {
            return i < samples.length;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No such next element");

            Item next = samples[i];
            ++i;
            return next;
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        // Test initialization
        StdOut.println("Initial queue state is empty: " + queue.isEmpty()); // Expected: true
        StdOut.println("Initial size: " + queue.size()); // Expected: 0

        // Test adding items
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        StdOut.println("Size after adding 3 items: " + queue.size()); // Expected: 3

        // Test sampling items
        StdOut.println("Sampled item: " + queue.sample()); // Expected: Random item (1, 2, or 3)

        // Test removing items
        StdOut.println("Removed item: " + queue.dequeue()); // Expected: Random item (1, 2, or 3)
        StdOut.println("Size after removing an item: " + queue.size()); // Expected: 2

        // Test exception handling for dequeue on empty queue
        queue.dequeue(); // Remove two more items
        queue.dequeue();
        try {
            queue.dequeue(); // Should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }

        // Test exception handling for sample on empty queue
        try {
            queue.sample(); // Should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }

        // Test exception handling for adding null
        try {
            queue.enqueue(null); // Should throw IllegalArgumentException
        } catch (IllegalArgumentException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }

        // Test iterator functionality
        queue.enqueue(4);
        queue.enqueue(5);
        StdOut.println("Items in random order:");
        for (Integer item : queue) {
            StdOut.println(item); // Expected: Random order of items (4, 5)
        }
    }

}
