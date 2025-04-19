package com.kaldun.cache;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the LRUCache implementation.
 * Verifies the correct behavior of the Least Recently Used cache algorithm.
 */
public class LRUCacheTest {

    /**
     * Tests basic cache operations with the example from LeetCode.
     */
    @Test
    public void testBasicOperations() {
        LRUCache cache = new LRUCache(2);
        
        // Put key 1 with value 1
        cache.put(1, 1);
        // Put key 2 with value 2
        cache.put(2, 2);
        // Get key 1, should return 1
        assertEquals(1, cache.get(1));
        
        // Put key 3 with value 3, which should evict key 2
        cache.put(3, 3);
        // Get key 2, should return -1 (not found)
        assertEquals(-1, cache.get(2));
        
        // Put key 4 with value 4, which should evict key 1
        cache.put(4, 4);
        // Get key 1, should return -1 (not found)
        assertEquals(-1, cache.get(1));
        // Get key 3, should return 3
        assertEquals(3, cache.get(3));
        // Get key 4, should return 4
        assertEquals(4, cache.get(4));
    }
    
    /**
     * Tests updating an existing key in the cache.
     */
    @Test
    public void testUpdateExistingKey() {
        LRUCache cache = new LRUCache(2);
        
        cache.put(1, 1);
        cache.put(2, 2);
        
        // Update key 1 with a new value
        cache.put(1, 10);
        assertEquals(10, cache.get(1));
        
        // Key 2 should still be in the cache
        assertEquals(2, cache.get(2));
    }
    
    /**
     * Tests that accessing a key makes it the most recently used.
     */
    @Test
    public void testLRUEvictionOrder() {
        LRUCache cache = new LRUCache(3);
        
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        
        // Access key 1, making it the most recently used
        assertEquals(1, cache.get(1));
        
        // Add a new key, which should evict key 2 (not 1 or 3)
        cache.put(4, 4);
        
        assertEquals(1, cache.get(1));
        assertEquals(-1, cache.get(2)); // Should be evicted
        assertEquals(3, cache.get(3));
        assertEquals(4, cache.get(4));
    }
    
    /**
     * Tests cache with capacity of 1.
     */
    @Test
    public void testCapacityOne() {
        LRUCache cache = new LRUCache(1);
        
        cache.put(1, 1);
        assertEquals(1, cache.get(1));
        
        cache.put(2, 2);
        assertEquals(-1, cache.get(1)); // Should be evicted
        assertEquals(2, cache.get(2));
    }
    
    /**
     * Tests getting a non-existent key.
     */
    @Test
    public void testGetNonExistentKey() {
        LRUCache cache = new LRUCache(5);
        
        assertEquals(-1, cache.get(1));
        assertEquals(-1, cache.get(100));
        
        cache.put(1, 1);
        assertEquals(1, cache.get(1));
        assertEquals(-1, cache.get(2));
    }
    
    /**
     * Tests a sequence of operations to verify correct LRU behavior.
     */
    @Test
    public void testComplexSequence() {
        LRUCache cache = new LRUCache(3);
        
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        
        assertEquals(3, cache.get(3)); // 3 is most recent, then 2, then 1
        assertEquals(2, cache.get(2)); // 2 is most recent, then 3, then 1
        
        cache.put(4, 4); // Should evict 1
        
        assertEquals(-1, cache.get(1));
        assertEquals(2, cache.get(2));
        
        cache.put(5, 5); // Should evict 3
        
        assertEquals(-1, cache.get(3));
        assertEquals(2, cache.get(2));
        assertEquals(4, cache.get(4));
        assertEquals(5, cache.get(5));
    }
}
