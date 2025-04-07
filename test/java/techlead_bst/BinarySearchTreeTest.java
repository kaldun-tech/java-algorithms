package techlead_bst;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unit tests for the BinarySearchTree implementation.
 * Tests all major functionality including:
 * - Adding elements
 * - Removing elements
 * - Searching
 * - Tree properties (size, height, etc.)
 * - Traversal methods
 */
public class BinarySearchTreeTest {
    
    private BinarySearchTree bst;
    
    /**
     * Set up a fresh BST before each test
     */
    @Before
    public void setUp() {
        bst = new BinarySearchTree();
    }
    
    /**
     * Test adding elements to the tree
     */
    @Test
    public void testAdd() {
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        
        // Verify size
        assertEquals(5, bst.size());
        assertFalse(bst.isEmpty());
        
        // Verify contains
        assertTrue(bst.contains(50));
        assertTrue(bst.contains(30));
        assertTrue(bst.contains(70));
        assertTrue(bst.contains(20));
        assertTrue(bst.contains(40));
        
        // Verify doesn't contain
        assertFalse(bst.contains(10));
        assertFalse(bst.contains(60));
        
        // Test adding duplicates (should be ignored)
        bst.add(50);
        assertEquals(5, bst.size());
    }
    
    /**
     * Test removing elements from the tree
     */
    @Test
    public void testRemove() {
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        assertEquals(7, bst.size());
        
        // Remove leaf node
        assertTrue(bst.remove(20));
        assertEquals(6, bst.size());
        assertFalse(bst.contains(20));
        
        // Remove node with one child
        assertTrue(bst.remove(30));
        assertEquals(5, bst.size());
        assertFalse(bst.contains(30));
        assertTrue(bst.contains(40)); // Child should still be there
        
        // Remove node with two children
        assertTrue(bst.remove(70));
        assertEquals(4, bst.size());
        assertFalse(bst.contains(70));
        assertTrue(bst.contains(60)); // Children should still be there
        assertTrue(bst.contains(80));
        
        // Remove root
        assertTrue(bst.remove(50));
        assertEquals(3, bst.size());
        assertFalse(bst.contains(50));
        
        // Try to remove non-existent element
        assertFalse(bst.remove(999));
        assertEquals(3, bst.size());
    }
    
    /**
     * Test the min and max methods
     */
    @Test
    public void testMinMax() {
        // Empty tree should throw exception
        try {
            bst.min();
            fail("min() should throw exception on empty tree");
        } catch (NoSuchElementException e) {
            // Expected
        }
        
        try {
            bst.max();
            fail("max() should throw exception on empty tree");
        } catch (NoSuchElementException e) {
            // Expected
        }
        
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        // Test min and max
        assertEquals(20, bst.min());
        assertEquals(80, bst.max());
        
        // Add new min and max
        bst.add(10);
        bst.add(90);
        
        assertEquals(10, bst.min());
        assertEquals(90, bst.max());
    }
    
    /**
     * Test the height method
     */
    @Test
    public void testHeight() {
        assertEquals(0, bst.height());
        
        bst.add(50);
        assertEquals(1, bst.height());
        
        bst.add(30);
        assertEquals(2, bst.height());
        
        bst.add(70);
        assertEquals(2, bst.height());
        
        bst.add(20);
        assertEquals(3, bst.height());
        
        // Add more nodes to right side to make it deeper
        bst.add(60);
        bst.add(80);
        bst.add(90);
        
        assertEquals(4, bst.height());
    }
    
    /**
     * Test the traversal methods (inorder, preorder, postorder, levelorder)
     */
    @Test
    public void testTraversals() {
        // Build a balanced tree
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        // Test inorder traversal
        List<Integer> inorder = new ArrayList<>();
        for (int key : bst.inorder()) {
            inorder.add(key);
        }
        List<Integer> expectedInorder = List.of(20, 30, 40, 50, 60, 70, 80);
        assertEquals(expectedInorder, inorder);
        
        // Test preorder traversal
        List<Integer> preorder = new ArrayList<>();
        for (int key : bst.preorder()) {
            preorder.add(key);
        }
        List<Integer> expectedPreorder = List.of(50, 30, 20, 40, 70, 60, 80);
        assertEquals(expectedPreorder, preorder);
        
        // Test postorder traversal
        List<Integer> postorder = new ArrayList<>();
        for (int key : bst.postorder()) {
            postorder.add(key);
        }
        List<Integer> expectedPostorder = List.of(20, 40, 30, 60, 80, 70, 50);
        assertEquals(expectedPostorder, postorder);
        
        // Test levelorder traversal
        List<Integer> levelorder = new ArrayList<>();
        for (int key : bst.levelorder()) {
            levelorder.add(key);
        }
        List<Integer> expectedLevelorder = List.of(50, 30, 70, 20, 40, 60, 80);
        assertEquals(expectedLevelorder, levelorder);
    }
    
    /**
     * Test the floor and ceiling methods
     */
    @Test
    public void testFloorCeiling() {
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        // Test floor
        assertEquals(50, bst.floor(50));
        assertEquals(50, bst.floor(55));
        assertEquals(40, bst.floor(45));
        assertEquals(80, bst.floor(85));
        assertEquals(20, bst.floor(25));
        
        // Test ceiling
        assertEquals(50, bst.ceiling(50));
        assertEquals(60, bst.ceiling(55));
        assertEquals(50, bst.ceiling(45));
        assertEquals(20, bst.ceiling(15));
        assertEquals(30, bst.ceiling(25));
        
        // Test edge cases
        try {
            bst.floor(10);
            fail("floor(10) should throw exception when no floor exists");
        } catch (NoSuchElementException e) {
            // Expected
        }
        
        try {
            bst.ceiling(90);
            fail("ceiling(90) should throw exception when no ceiling exists");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }
    
    /**
     * Test the rank and select methods
     */
    @Test
    public void testRankSelect() {
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        // Test rank
        assertEquals(0, bst.rank(20));
        assertEquals(1, bst.rank(30));
        assertEquals(2, bst.rank(40));
        assertEquals(3, bst.rank(50));
        assertEquals(4, bst.rank(60));
        assertEquals(5, bst.rank(70));
        assertEquals(6, bst.rank(80));
        
        // Test rank of elements not in the tree
        assertEquals(0, bst.rank(10));
        assertEquals(3, bst.rank(45));
        assertEquals(7, bst.rank(90));
        
        // Test select
        assertEquals(20, bst.select(0));
        assertEquals(30, bst.select(1));
        assertEquals(40, bst.select(2));
        assertEquals(50, bst.select(3));
        assertEquals(60, bst.select(4));
        assertEquals(70, bst.select(5));
        assertEquals(80, bst.select(6));
        
        // Test invalid select
        try {
            bst.select(-1);
            fail("select(-1) should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        
        try {
            bst.select(7);
            fail("select(7) should throw exception when tree size is 7");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
    
    /**
     * Test the range methods
     */
    @Test
    public void testRange() {
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        // Test range
        List<Integer> range = new ArrayList<>();
        for (int key : bst.range(30, 70)) {
            range.add(key);
        }
        List<Integer> expectedRange = List.of(30, 40, 50, 60, 70);
        assertEquals(expectedRange, range);
        
        // Test smaller range
        List<Integer> smallRange = new ArrayList<>();
        for (int key : bst.range(35, 55)) {
            smallRange.add(key);
        }
        List<Integer> expectedSmallRange = List.of(40, 50);
        assertEquals(expectedSmallRange, smallRange);
        
        // Test empty range
        List<Integer> emptyRange = new ArrayList<>();
        for (int key : bst.range(51, 59)) {
            emptyRange.add(key);
        }
        assertTrue(emptyRange.isEmpty());
    }
    
    /**
     * Test the deleteMin and deleteMax methods
     */
    @Test
    public void testDeleteMinMax() {
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        assertEquals(7, bst.size());
        
        // Test deleteMin
        bst.deleteMin();
        assertEquals(6, bst.size());
        assertFalse(bst.contains(20));
        assertEquals(30, bst.min());
        
        // Test deleteMax
        bst.deleteMax();
        assertEquals(5, bst.size());
        assertFalse(bst.contains(80));
        assertEquals(70, bst.max());
        
        // Delete all elements using deleteMin
        bst.deleteMin(); // 30
        bst.deleteMin(); // 40
        bst.deleteMin(); // 50
        bst.deleteMin(); // 60
        bst.deleteMin(); // 70
        
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        
        // Test deleteMin/Max on empty tree
        try {
            bst.deleteMin();
            fail("deleteMin() should throw exception on empty tree");
        } catch (NoSuchElementException e) {
            // Expected
        }
        
        try {
            bst.deleteMax();
            fail("deleteMax() should throw exception on empty tree");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }
}
