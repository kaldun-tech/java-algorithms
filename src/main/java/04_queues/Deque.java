import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
        public Node(Item val) {
            this.item = val;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /** Construct an empty deque. Head and tail are initialized as dummy pointers */
    public Deque() {
        size = 0;
        head = new Node(null);
        tail = new Node(null);
    }

    /** is the deque empty? */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return the number of items on the deque */
    public int size() {
        return size;
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");
    }

    /** add the item to the front */
    public void addFirst(Item item) {
        validateItem(item);
        Node first = new Node(item);
        if (isEmpty()) {
            // Set initial
            head.next = tail.next = first;
        } else {
            // Link to existing
            head.next.prev = first;
            first.next = head.next;
            head.next = first;
        }
        ++size;
    }

    /** add the item to the back */
    public void addLast(Item item) {
        validateItem(item);
        Node last = new Node(item);
        if (isEmpty()) {
            head.next = tail.next = last;
        } else {
            tail.next.next = last;
            last.prev = tail.next;
            tail.next = last;
        }
        ++size;
    }

    /** remove and return the item from the front */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("No first element");
        }
        Node first = head.next;
        if (size == 1) {
            // Becomes empty
            head.next = tail.next = null;
        } else {
            head.next = first.next;
            first.next.prev = head;
        }
        --size;
        return first.item;
    }

    /** remove and return the item from the back */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("No last element");
        }
        Node last = tail.next;
        if (size == 1) {
            // Becomes empty
            head.next = tail.next = null;
        } else {
            last.prev.next = null;
            tail.next = last.prev;
        }
        return last.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node node;

        public DequeIterator() {
            node = head.next;
        }

        public boolean hasNext() {
            return node != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No such next element");

            Item t = node.item;
            node = node.next;
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
