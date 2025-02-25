import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] q;
    private int head = 0;
    private int tail = 0;
    private int maxSize = 2;

    /** construct an empty deque */
    public Deque() {
        q = new Item[maxSize];
    }

    /** is the deque empty? */
    public boolean isEmpty() {
        return head == tail;
    }

    /** return the number of items on the deque */
    public int size() {
        return tail - head;
    }

    private void rebuildArray() {
        Item[] newItems = new Item[maxSize];
        int newHead = maxSize / 4;
        int newTail = newHead;
        for (int i = head; i < tail; ++i, ++newTail) {
            newItems[newTail] = q[i];
        }
        q = newItems;
        head = newHead;
        tail = newTail;
    }

    private void growIfNeeded() {
        if (head == 0 || tail + 1 == maxSize) {
            if (size() + 1 == maxSize) maxSize *= 2;
            rebuildArray();
        }
    }

    private void shrinkIfNeeded() {
        if (size() - 1 < maxSize / 4) {
            maxSize /= 2;
            rebuildArray();
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
        // TODO problem if we add before index 0
        if (0 < head) {
            --head;
            q[head] = item;
        }
    }

    /** add the item to the back */
    public void addLast(Item item) {
        validateItem(item);
        growIfNeeded();
        q[tail] = item;
        ++tail;
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

    private class DequeIterator<Item> implements Iterator<Item> {
        private int i = head;

        public boolean hasNext() {
            return i <= tail;
        }

        public Item next() {
            Item t = q[i];
            ++i;
            return t;
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
