import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] items;
    private int size = 0;
    private int maxSize = 2;

    // construct an empty deque
    public Deque() {
        items = new Item[maxSize];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
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

    private void shiftLeft() {
        for (int i = 0; i < size - 1; ++i) {
            items[i] = items[i + 1];
        }
    }

    private void shiftRight() {
        for (int i = size; 0 < i; --i) {
            items[i] = items[i - 1];
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem();
        growIfNeeded();
        shiftRight();
        items[0] = item;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem();
        growIfNeeded();
        items[size] = item;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("No first element");
        }
        shiftLeft();
        --size;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("No last element");
        }
        --size;
        items[size] = null;
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            Item n = items[i];
            ++i;
            return n;
        }

        public void remove() {
            return UnsupportedOperationException("Cannot remove from queue");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
