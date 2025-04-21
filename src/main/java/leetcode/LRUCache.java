package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LRUCache implements a Least Recently Used (LRU) cache with fixed capacity.
 * When the cache reaches capacity, the least recently used item is evicted.
 * 
 * Time Complexity:
 * - get(key): O(1)
 * - put(key, value): O(1)
 * 
 * Space Complexity: O(capacity)
 */
public class LRUCache {
    // Node class for doubly linked list
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<Integer, Node> cache;
    private Node head; // Most recently used
    private Node tail; // Least recently used
    
    /**
     * Constructs an LRU Cache with the specified capacity.
     * 
     * @param capacity The maximum number of key-value pairs the cache can hold
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    /**
     * Retrieves the value associated with the specified key.
     * 
     * @param key The key to look up
     * @return The value if present, otherwise -1
     */
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    /**
     * Inserts or updates the value associated with the specified key.
     * 
     * @param key The key to insert or update
     * @param value The value to associate with the key
     */
    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);
            if (cache.size() > capacity) {
                Node tailNode = popTail();
                cache.remove(tailNode.key);
            }
        }
    }

    private void addNode(Node node) {
        node.prev = null;
        node.next = head;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void moveToHead(Node node) {
        if (node == head) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == tail) {
            tail = node.prev;
        }
        node.prev = null;
        node.next = head;
        if (head != null) {
            head.prev = node;
        }
        head = node;
    }

    private Node popTail() {
        if (tail == null) {
            return null;
        }
        Node node = tail;
        if (tail.prev != null) {
            tail.prev.next = null;
        } else {
            head = null;
        }
        tail = tail.prev;
        return node;
    }
}
