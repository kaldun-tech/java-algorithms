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

    }

}
