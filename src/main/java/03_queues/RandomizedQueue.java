import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size = 0;
    private int maxSize = 2;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new Item[maxSize];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void grow() {
        maxSize *= 2;
        Item[] newItems = new Item[maxSize];
        for (int i = 0; i < size; ++i) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    private void growIfNeeded() {
        if (size + 1 == maxSize) grow();
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");
    }

    private void shiftLeft(int start) {
        for (int i = start; i < size - 1; ++i) {
            items[i] = items[i + 1];
        }
    }

    /* Add the item. Since the order of removal is random, the position where
     * the item is added is irrelevant. I choose to add to the end like a stack */
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");

        growIfNeeded();
        items[size] = item;
        ++size;
    }

    private void checkEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
    }

    private int getRandomPosition() {
        double probability = 1.0 / size;
        for (int i = 0; i < size; ++i) {
            if (StdRandom.bernoulli(probability))
                return i;
        }
        System.out.println("getRandomPosition unexpectedly completed loop")
        return size - 1;
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmpty();
        int position = getRandomPosition();
        Item item = items[position];
        shiftLeft(position);
        return item;
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        int i = 0;
        int[] positions;

        public RandomizedQueueIterator<Item>() {
            positions = new int[size];
            for (int j = 0; j < size; ++j) {
                positions[j] = getRandomPosition();
            }
        }

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            Item n = items[positions[i]];
            ++i;
            return n;
        }

        public void remove() {
            return UnsupportedOperationException("Cannot remove from queue");
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkEmpty();
        int position = getRandomPosition();
        return items[position];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    
        // Test initialization
        System.out.println("Initial queue state is empty: " + queue.isEmpty()); // Expected: true
        System.out.println("Initial size: " + queue.size()); // Expected: 0
    
        // Test adding items
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Size after adding 3 items: " + queue.size()); // Expected: 3
    
        // Test sampling items
        System.out.println("Sampled item: " + queue.sample()); // Expected: Random item (1, 2, or 3)
    
        // Test removing items
        System.out.println("Removed item: " + queue.dequeue()); // Expected: Random item (1, 2, or 3)
        System.out.println("Size after removing an item: " + queue.size()); // Expected: 2
    
        // Test exception handling for dequeue on empty queue
        queue.dequeue(); // Remove two more items
        queue.dequeue();
        try {
            queue.dequeue(); // Should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    
        // Test exception handling for sample on empty queue
        try {
            queue.sample(); // Should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    
        // Test exception handling for adding null
        try {
            queue.enqueue(null); // Should throw IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    
        // Test iterator functionality
        queue.enqueue(4);
        queue.enqueue(5);
        System.out.println("Items in random order:");
        for (Integer item : queue) {
            System.out.println(item); // Expected: Random order of items (4, 5)
        }
    }

}
