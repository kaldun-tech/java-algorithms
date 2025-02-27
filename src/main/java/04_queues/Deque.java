import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Item[] q;
    private int head = 0;
    private int tail = 0;
    private int maxSize = 2;

    private static Item[] createArray(int capacity) {
        return (Item[]) new Object[capacity];
    }

    /** construct an empty deque */
    public Deque() {
        q = createArray(maxSize);
    }

    /** is the deque empty? */
    public boolean isEmpty() {
        return head == tail;
    }

    /** return the number of items on the deque */
    public int size() {
        return tail - head;
    }

    private void resize() {
        Item[] newItems = createArray(maxSize)
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

    /** add the item to the front */
    public void addFirst(Item item) {
        validateItem(item);
        growIfNeeded();
        q[head] = item;
        if (0 < head) --head;
    }

    /** add the item to the back */
    public void addLast(Item item) {
        validateItem(item);
        growIfNeeded();
        q[tail] = item;
        if (tail < maxSize) ++tail;
    }

    /** remove and return the item from the front */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("No first element");
        }
        Item t = q[head];
        q[head] = null;
        ++head;
        shrinkIfNeeded();
        return t;
    }

    /** remove and return the item from the back */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("No last element");
        }
        Item t = q[tail];
        q[tail] = null;
        --tail;
        shrinkIfNeeded();
        return t;
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = head;

        public boolean hasNext() {
            return i <= tail;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No such next element");

            Item t = q[i];
            ++i;
            return t;
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    /** return an iterator over items in order from front to back */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /** unit testing (required) */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println("Initial deque state is empty with size zero");
        assert deque.isEmpty();
        assert deque.size() == 0;

        StdOut.println("Test exception handling by removing from empty");
        try {
            deque.removeFirst();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception for removeFirst on empty");
        }
        try {
            deque.removeLast();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception for removeLast on empty");
        }

        StdOut.println("Test exception handling by rdd null elements");
        try {
            deque.addFirst(null);
            assert false;
        } catch (IllegalArgumentException e) {
            StdOut.println("Caught expected exception for addFirst of null");
        }
        try {
            deque.addLast(null);
            assert false;
        } catch (IllegalArgumentException e) {
            StdOut.println("Caught expected exception for addLast of null");
        }

        StdOut.println("Add elements to front and back");
        for (int i = 0; i < 5; ++i) {
            deque.addFirst(i);
            deque.addLast(i);
            StringBuilder sb = new StringBuilder("Added ")
                    .append(i)
                    .append(" to front and end of deque");
            StdOut.println(sb.toString());
        }
        assert deque.size() == 10;
        assert !deque.isEmpty();

        StdOut.println("Test iterating over elements");
        Iterator<Integer> it = deque.iterator();
        boolean triedRemove = false;
        while (it.hasNext()) {
            int next = it.next();
            StdOut.println("Next element: " + next);
            if (triedRemove) continue;
            try {
                it.remove();
                assert false;
            } catch (UnsupportedOperationException e) {
                triedRemove = true;
                StdOut.println("Remove not allowed on iterator");
            }
        }

        StdOut.println("Test iterator's next method when there are no more items");
        try {
            it.next();
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }

        int currentSize = deque.size();
        assert currentSize == 10;
        assert !deque.isEmpty();

        StdOut.println("Removing elements");
        for (int i = 4; 0 <= i; --i) {
            int first = deque.removeFirst();
            int last = deque.removeLast();
            assert first == last;
            assert first == i;
            StdOut.println("Removed " + i);
            currentSize -= 2;
            assert deque.size() == currentSize;
        }

        StdOut.println("Deque is now empty");
        assert deque.isEmpty();
        try {
            deque.removeFirst();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception for removeFirst on empty");
        }
        try {
            deque.removeLast();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception for removeLast on empty");
        }
    }

}
