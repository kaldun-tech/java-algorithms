package techlead_bst;

import java.util.NoSuchElementException;

/**
 * A class to find the k-th largest element in a stream of integers.
 * Uses a BinarySearchTree to maintain the k largest elements seen so far.
 * After adding a new element that would increase the size greater than k,
 * we delete the minimum element. The k-th largest element is then the minimum
 * element in the tree.
 */
public class KthLargest {
    private final int k;
    private final BinarySearchTree bst;
    
    /**
     * Initialize the KthLargest class with the given k value
     * 
     * @param k the k value for finding the k-th largest element
     * @throws IllegalArgumentException if k is less than or equal to 0
     */
    public KthLargest(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be greater than 0");
        }
        this.k = k;
        this.bst = new BinarySearchTree();
    }
    
    /**
     * Add a new element to the stream and return the k-th largest element
     * 
     * @param val the value to add to the stream
     * @return the k-th largest element after adding the new value, or the smallest element
     *         if there are fewer than k elements
     */
    public int add(int val) {
        System.out.println("DEBUG - Adding: " + val);
        // Add the new value to the BST
        bst.add(val);
        System.out.println("DEBUG - After adding, BST: " + bst.inorder());
        
        // If the BST size exceeds k, remove the minimum element
        while (!bst.isEmpty() && bst.size() > k) {
            int minVal = bst.min();
            System.out.println("DEBUG - Removing min: " + minVal);
            bst.deleteMin();
            System.out.println("DEBUG - After removing min, BST: " + bst.inorder());
        }
        
        // Return the minimum element in the BST (which is the k-th largest overall)
        // If we have fewer than k elements, this will still return the smallest element
        int result = bst.min();
        System.out.println("DEBUG - Returning: " + result);
        return result;
    }
    
    /**
     * Get the current k-th largest element without adding a new value
     * 
     * @return the k-th largest element, or the smallest element if there are fewer than k elements
     * @throws NoSuchElementException if the stream is empty
     */
    public int getKthLargest() {
        if (bst.isEmpty()) {
            throw new NoSuchElementException("Stream is empty");
        }
        
        return bst.min();
    }
    
    /**
     * Get the number of elements currently in the stream
     * 
     * @return the number of elements in the stream, capped at k
     */
    public int size() {
        return bst.size();
    }
    
    /**
     * Check if the stream is empty
     * 
     * @return true if the stream is empty, false otherwise
     */
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
