import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int head = 0;
    private int tail = 0;
    private int maxSize = 2;

    private static Item[] createArray(int capacity) {
        return (Item[]) new Object[capacity];
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = createArray(maxSize);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == tail;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail - head;
    }

    private void resize() {
        Item[] newItems = createArray(maxSize);
        int newHead = maxSize / 4;
        int newTail = newHead;
        for (int i = head; i <= tail; ++i, ++newTail) {
            newItems[newTail] = q[i];
        }
        q = newItems;
        head = newHead;
        tail = newTail;
    }

    private void growIfNeeded() {
        if (head == 0 || tail == maxSize) {
            if (size() + 1 == maxSize) maxSize *= 2;
            resize();
        }
    }

    private void shrinkIfNeeded() {
        if (size() < maxSize / 4) {
            maxSize /= 2;
            resize();
        }
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");
    }

    /** Enqueue the item. Since order is irrelevant it may be added to the front or end. */
    public void enqueue(Item item) {
        validateItem(item);
        growIfNeeded();
        if (0 < head) {
            q[head] = item;
            --head;
        } else {
            q[tail] = item;
            ++tail;
        }
    }

    private void checkEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
    }

    private int getRandomPosition() {
        double probability = 1.0 / size();
        for (int i = head; i <= tail; ++i) {
            if (StdRandom.bernoulli(probability))
                return i;
        }
        throw new UnsupportedOperationException("Unexpected failure computing random position");
    }

    private void shiftTailLeft(int position) {
        if (position != tail) {
            // Copy existing tail to removed position
            q[position] = q[tail];
        }
        q[tail] = null;
        --tail;
    }

    /** Remove and return a random item */
    public Item dequeue() {
        checkEmpty();
        int random = getRandomPosition();
        Item item = q[random];
        shiftTailLeft(random);
        shrinkIfNeeded();
        return item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] samples;
        int i = 0;

        public RandomizedQueueIterator() {
            samples = createArray(size());
            for (int j = 0; j < samples.length; ++j) {
                samples[j] = sample();
            }
        }

        public boolean hasNext() {
            return i < samples.length;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No such next element");

            Item n = samples[i];
            ++i;
            return n;
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    /** return a random item (but do not remove it) */
    public Item sample() {
        checkEmpty();
        int position = getRandomPosition();
        return q[position];
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
