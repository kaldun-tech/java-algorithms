package com.kaldun.cache;

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
        this.cache = new HashMap<>(capacity);
        // Initialize dummy head and tail nodes
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Retrieves the value for the given key if it exists in the cache.
     * Also marks the key as most recently used.
     * 
     * @param key The key to look up
     * @return The value associated with the key, or -1 if not found
     */
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        
        // Move to front (most recently used position)
        moveToHead(node);
        return node.value;
    }
    
    /**
     * Inserts or updates a key-value pair in the cache.
     * If the key already exists, updates its value and marks it as most recently used.
     * If the cache is at capacity, removes the least recently used item.
     * 
     * @param key The key to insert or update
     * @param value The value to associate with the key
     */
    public void put(int key, int value) {
        Node node = cache.get(key);
        
        if (node != null) {
            // Update existing node
            node.value = value;
            moveToHead(node);
        } else {
            // Create new node
            Node newNode = new Node(key, value);
            
            // Check if cache is at capacity
            if (cache.size() >= capacity) {
                // Remove least recently used (from tail)
                Node lru = tail.prev;
                removeNode(lru);
                cache.remove(lru.key);
            }
            
            // Add new node to head (most recently used position)
            addToHead(newNode);
            cache.put(key, newNode);
        }
    }
    
    /**
     * Helper method to remove a node from the doubly linked list.
     * 
     * @param node The node to remove from the list
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * Helper method to add a node to the head of the doubly linked list.
     * 
     * @param node The node to add to the head of the list
     */
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Helper method to move a node to the head of the doubly linked list.
     * 
     * @param node The node to move to the head of the list
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
}
