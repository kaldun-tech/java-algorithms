package techlead_bst;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

/**
 * Unit tests for the KthLargest class.
 * Tests the functionality of finding the k-th largest element in a stream.
 */
public class KthLargestTest {
    
    /**
     * Test basic functionality with a simple stream of numbers
     */
    @Test
    public void testBasicFunctionality() {
        KthLargest kthLargest = new KthLargest(3);
        
        // Empty stream
        try {
            kthLargest.getKthLargest();
            fail("Should throw NoSuchElementException when the stream is empty");
        } catch (NoSuchElementException e) {
            // Expected
        }
        
        // Add elements and check the 3rd largest
        assertEquals(4, kthLargest.add(4));  // [4], not enough elements, return smallest
        assertEquals(4, kthLargest.getKthLargest());
        
        assertEquals(4, kthLargest.add(5));  // [4, 5], not enough elements, return smallest
        assertEquals(4, kthLargest.getKthLargest());
        
        assertEquals(4, kthLargest.add(8));  // [4, 5, 8], 3rd largest is 4
        assertEquals(4, kthLargest.getKthLargest());
        
        assertEquals(4, kthLargest.add(2));  // After adding 2, BST becomes [2, 4, 5, 8], then min 2 is removed, leaving [4, 5, 8]
        assertEquals(4, kthLargest.getKthLargest());
        
        assertEquals(4, kthLargest.add(3));  // After adding 3, BST becomes [3, 4, 5, 8], then min 3 is removed, leaving [4, 5, 8]
        assertEquals(4, kthLargest.getKthLargest());
        
        assertEquals(4, kthLargest.add(5));  // After adding another 5, BST remains [4, 5, 8] since duplicates are skipped
        assertEquals(4, kthLargest.getKthLargest());
        
        assertEquals(5, kthLargest.add(10)); // After adding 10, BST becomes [4, 5, 8, 10], then min 4 is removed, leaving [5, 8, 10]
        assertEquals(5, kthLargest.getKthLargest());
        
        assertEquals(8, kthLargest.add(9));  // After adding 9, BST becomes [5, 8, 9, 10], then min 5 is removed, leaving [8, 9, 10]
        assertEquals(8, kthLargest.getKthLargest());
        
        assertEquals(8, kthLargest.add(4));  // After adding 4, BST becomes [4, 8, 9, 10], then min 4 is removed, leaving [8, 9, 10]
        assertEquals(8, kthLargest.getKthLargest());
    }
    
    /**
     * Test with duplicate values in the stream
     */
    @Test
    public void testWithDuplicates() {
        KthLargest kthLargest = new KthLargest(2);
        
        assertEquals(5, kthLargest.add(5));  // [5], not enough elements, return smallest
        assertEquals(5, kthLargest.getKthLargest());
        
        assertEquals(5, kthLargest.add(5));  // [5], duplicate is skipped
        assertEquals(5, kthLargest.getKthLargest());
        
        assertEquals(5, kthLargest.add(10)); // [5, 10], 2nd largest is 5
        assertEquals(5, kthLargest.getKthLargest());
        
        assertEquals(7, kthLargest.add(7));  // [7, 10], 2nd largest is 7
        assertEquals(7, kthLargest.getKthLargest());
    }
    
    /**
     * Test with invalid k value
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidK() {
        new KthLargest(0);
    }
    
    /**
     * Test with negative k value
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeK() {
        new KthLargest(-1);
    }
    
    /**
     * Test with a larger k value
     */
    @Test
    public void testLargerK() {
        KthLargest kthLargest = new KthLargest(5);
        
        assertEquals(1, kthLargest.add(1));  // [1], not enough elements, return smallest
        assertEquals(1, kthLargest.getKthLargest());
        
        assertEquals(1, kthLargest.add(2));  // [1, 2], not enough elements, return smallest
        assertEquals(1, kthLargest.getKthLargest());
        
        assertEquals(1, kthLargest.add(3));  // [1, 2, 3], not enough elements, return smallest
        assertEquals(1, kthLargest.getKthLargest());
        
        assertEquals(1, kthLargest.add(4));  // [1, 2, 3, 4], not enough elements, return smallest
        assertEquals(1, kthLargest.getKthLargest());
        
        assertEquals(1, kthLargest.add(5));  // [1, 2, 3, 4, 5], 5th largest is 1
        assertEquals(1, kthLargest.getKthLargest());
        
        assertEquals(2, kthLargest.add(6));  // [2, 3, 4, 5, 6], 5th largest is 2
        assertEquals(2, kthLargest.getKthLargest());
        
        assertEquals(3, kthLargest.add(7));  // [3, 4, 5, 6, 7], 5th largest is 3
        assertEquals(3, kthLargest.getKthLargest());
    }
}
