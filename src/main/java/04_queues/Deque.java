import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] q;
    private int size = 0;
    private int maxSize = 2;

    /** construct an empty deque */
    public Deque() {
        q = new Item[maxSize];
    }

    /** is the deque empty? */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return the number of items on the deque */
    public int size() {
        return size;
    }

    private void resize() {
        Item[] newItems = new Item[maxSize];
        for (int i = 0; i < size; ++i) {
            newItems[i] = q[i];
        }
        q = newItems;
    }

    private void grow() {
        maxSize *= 2;
        resize();
    }

    private void growIfNeeded() {
        if (size + 1 == maxSize) grow();
    }

    private void shrink() {
        maxSize /= 2;
        resize();
    }

    private void shrinkIfNeeded() {
        if (size - 1 < maxSize / 4) shrink();
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");
    }

    private void shiftLeft() {
        for (int i = 0; i < size - 1; ++i) {
            q[i] = q[i + 1];
        }
    }

    private void shiftRight() {
        for (int i = size; 0 < i; --i) {
            q[i] = q[i - 1];
        }
    }

    /** add the item to the front */
    public void addFirst(Item item) {
        validateItem();
        growIfNeeded();
        shiftRight();
        q[0] = item;
        ++size;
    }

    /** add the item to the back */
    public void addLast(Item item) {
        validateItem();
        growIfNeeded();
        q[size] = item;
        ++size;
    }

    /** remove and return the item from the front */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("No first element");
        }
        shiftLeft();
        --size;
    }

    /** remove and return the item from the back */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("No last element");
        }
        --size;
        q[size] = null;
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            Item n = q[i];
            ++i;
            return n;
        }

        public void remove() {
            return UnsupportedOperationException("Cannot remove from queue");
        }
    }

    /** return an iterator over items in order from front to back */
    public Iterator<Item> iterator() {
        return new DequeIterator<>();
    }

    /** unit testing (required) */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println("Initial deque state is empty with size zero");
        assert deque.isEmpty();
        assert deque.size() == 0;

        System.out.println("Test exception handling by removing from empty");
        try {
            deque.removeFirst();
            assert false;
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception for removeFirst on empty");
        }
        try {
            deque.removeLast();
            assert false;
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception for removeLast on empty");
        }

        System.out.println("Test exception handling by rdd null elements");
        try {
            deque.addFirst(null);
            assert false;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for addFirst of null");
        }
        try {
            deque.addLast(null);
            assert false;
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for addLast of null");
        }

        System.out.println("Add elements to front and back");
        for (int i = 0; i < 5; ++i) {
            deque.addFirst(i);
            deque.addLast(i);
            StringBuilder sb = new StringBuilder("Added ")
                    .append(i)
                    .append(" to front and end of deque");
            System.out.println(sb.toString());
        }
        assert deque.size() == 10;
        assert !deque.isEmpty();

        System.out.println("Test iterating over elements")
        Iterator<Integer> it = deque.iterator();
        boolean triedRemove = false;
        while (it.hasNext()) {
            int next = it.next();
            System.out.println("Next element: " + next);
            if (triedRemove) continue;
            try {
                it.remove();
                assert false;
            } catch (UnsupportedOperationException e) {
                triedRemove = true;
                System.out.println("Remove not allowed on iterator");
            }
        }

        System.out.println("Test iterator's next method when there are no more items");
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        int currentSize = deque.size();
        assert currentSize == 10;
        assert !deque.isEmpty();

        System.out.println("Removing elements");
        for (int i = 4; 0 <= i; --i) {
            int first = deque.removeFirst();
            int last = deque.removeLast();
            assert first == last;
            assert first == i;
            System.out.println("Removed " + i);
            currentSize -= 2;
            assert deque.size() == currentSize;
        }

        System.out.println("Deque is now empty");
        assert deque.isEmpty();
        try {
            deque.removeFirst();
            assert false;
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception for removeFirst on empty");
        }
        try {
            deque.removeLast();
            assert false;
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception for removeLast on empty");
        }
    }

}
