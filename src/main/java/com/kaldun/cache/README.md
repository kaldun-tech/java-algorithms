# LRU Cache Implementation

## Overview
This package contains an implementation of a Least Recently Used (LRU) Cache, a data structure that maintains a fixed-size collection of key-value pairs. When the cache reaches its capacity and a new item needs to be added, the least recently used item is removed.

## Algorithm Details

The LRU Cache is implemented using a combination of:
- A **HashMap** for O(1) lookups by key
- A **Doubly Linked List** to track usage order (most recent at head, least recent at tail)

### Time Complexity
- `get(key)`: O(1)
- `put(key, value)`: O(1)

### Space Complexity
- O(capacity)

## Implementation

The implementation consists of:

1. An inner `Node` class representing elements in the doubly linked list
2. A `HashMap` to store key-to-node mappings
3. Dummy head and tail nodes to simplify list operations
4. Helper methods to manage the linked list:
   - `removeNode(node)`: Removes a node from the list
   - `addToHead(node)`: Adds a node to the head (most recently used position)
   - `moveToHead(node)`: Moves a node to the head

## Usage Example

```java
// Create a cache with capacity of 2
LRUCache cache = new LRUCache(2);

// Add key-value pairs
cache.put(1, 1);
cache.put(2, 2);

// Retrieve a value (also marks it as recently used)
int value = cache.get(1);  // Returns 1

// Add another key-value pair, which will evict the least recently used item (key 2)
cache.put(3, 3);

// Try to retrieve the evicted key
value = cache.get(2);  // Returns -1 (not found)
```

## Testing

The `LRUCacheTest` class provides comprehensive test cases to verify the correct behavior of the LRU Cache implementation, including:
- Basic operations
- Key updates
- Eviction order
- Edge cases (capacity of 1, non-existent keys)
- Complex operation sequences
