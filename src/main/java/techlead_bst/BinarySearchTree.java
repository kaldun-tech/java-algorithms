package techlead_bst;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Binary Search Tree implementation with standard operations.
 * Supports various traversals and operations like rank, select, floor, ceiling.
 */
public class BinarySearchTree {
    private Node root = null;

    private Node add(int key, Node n) {
        if (n == null) {
            // Create leaf here
            return new Node(key);
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp < 0) {
            n.left = add(key, n.left);
        } else if (0 < cmp) {
            n.right = add(key, n.right);
        } else {
            // Set -> don't add duplicates
            return n;
        }
        return n;
    }

    /**
     * Adds a key to the tree. Skips duplicates.
     * @param key
     */
    public void add(int key) {
        root = add(key, root);
    }

    private Node search(int key, Node n) {
        if (n == null) {
            // Not found
            return null;
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp == 0) {
            return n;
        } else if (cmp < 0) {
            return search(key, n.left);
        } else {
            // 0 < cmp
            return search(key, n.right);
        }
    }

    /**
     * Search for a key
     * @param key the key to search for
     * @return the node containing the key, or null if not found
     */
    public Node search(int key) {
        return search(key, root);
    }

    /**
     * Check if the tree contains a key
     * @param key the key to check for
     * @return true if the key is in the tree, false otherwise
     */
    public boolean contains(int key) {
        return search(key) != null;
    }

    private Node min(Node n) {
        Node less;
        for (less = n; less != null && less.left != null; less = less.left) {}
        return less;
    }

    /**
     * Get minimum key
     * @return the minimum key in the tree
     * @throws NoSuchElementException if the tree is empty
     */
    public int min() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot get minimum of empty tree");
        }
        return min(root).key;
    }

    private Node max(Node n) {
        Node gtr;
        for (gtr = n; gtr != null && gtr.right != null; gtr = gtr.right) {}
        return gtr;
    }

    /**
     * Get maximum key
     * @return the maximum key in the tree
     * @throws NoSuchElementException if the tree is empty
     */
    public int max() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot get maximum of empty tree");
        }
        return max(root).key;
    }

    private Node ceiling(int key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp == 0) {
            return n;
        } else if (cmp < 0) {
            Node ceilLeft = ceiling(key, n.left);
            return (ceilLeft == null) ? n : ceilLeft;
        } else {
            return ceiling(key, n.right);
        }
    }

    /**
     * Get the greatest key less than or equal to the input key
     * @param key the key to find the ceiling for
     * @return the ceiling key
     * @throws NoSuchElementException if no ceiling exists
     */
    public int ceiling(int key) {
        Node c = ceiling(key, root);
        if (c == null) {
            throw new NoSuchElementException("No ceiling exists for key " + key);
        }
        return c.key;
    }

    private Node floor(int key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp == 0) {
            return n;
        } else if (cmp < 0) {
            return floor(key, n.left);
        } else {
            Node floorRight = floor(key, n.right);
            return (floorRight == null) ? n : floorRight;
        }
    }

    /**
     * Get the least key greater or equal to the input key
     * @param key the key to find the floor for
     * @return the floor key
     * @throws NoSuchElementException if no floor exists
     */
    public int floor(int key) {
        Node f = floor(key, root);
        if (f == null) {
            throw new NoSuchElementException("No floor exists for key " + key);
        }
        return f.key;
    }

    /** Get the size of the tree */
    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size();
    }
    
    /**
     * Check if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Get how many keys are less than the input key
     * @param key
     * @return
     */
    public int rank(int key) {
        return rank(key, root);
    }

    private int rank(int key, Node n) {
        if (n == null) {
            return 0;
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp < 0) {
            return rank(key, n.left);
        } else if (cmp > 0) {
            return 1 + (n.left != null ? n.left.size() : 0) + rank(key, n.right);
        } else {
            return (n.left != null ? n.left.size() : 0);
        }
    }

    /** Hibbard deletion: use the predecessor or successor node with equal probability */
    private Node doDelete(Node n) {
        Random random = new Random();
        if (random.nextBoolean()) {
            // Use successor if available
            if (n.right != null) {
                Node succ = getSuccessor(n);
                n.key = succ.key;
                n.right = delete(n.right, succ.key);
                return n;
            } else if (n.left != null) {
                // Fall back to predecessor if no right child
                Node pred = getPredecessor(n);
                n.key = pred.key;
                n.left = delete(n.left, pred.key);
                return n;
            } else {
                // Leaf node
                return null;
            }
        } else {
            // Use predecessor if available
            if (n.left != null) {
                Node pred = getPredecessor(n);
                n.key = pred.key;
                n.left = delete(n.left, pred.key);
                return n;
            } else if (n.right != null) {
                // Fall back to successor if no left child
                Node succ = getSuccessor(n);
                n.key = succ.key;
                n.right = delete(n.right, succ.key);
                return n;
            } else {
                // Leaf node
                return null;
            }
        }
    }

    /** Predecessor is one hop left then all the way right */
    private Node getPredecessor(Node n) {
        return max(n.left);
    }

    /** Successor is one hop right then all the way left */
    private Node getSuccessor(Node n) {
        return min(n.right);
    }
    
    /**
     * Delete the node with least key from the subtree rooted at n
     * @param n the root of the subtree
     * @return the updated subtree
     */
    private Node deleteMin(Node n) {
        if (n == null) {
            return null;
        }
        if (n.left == null) {
            return n.right;
        }
        n.left = deleteMin(n.left);
        return n;
    }
    
    /**
     * Delete the node with least key
     * @throws NoSuchElementException if the tree is empty
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot delete minimum from empty tree");
        }
        root = deleteMin(root);
    }
    
    /**
     * Delete the node with highest key from the subtree rooted at n
     * @param n the root of the subtree
     * @return the updated subtree
     */
    private Node deleteMax(Node n) {
        if (n == null) {
            return null;
        }
        if (n.right == null) {
            return n.left;
        }
        n.right = deleteMax(n.right);
        return n;
    }
    
    /**
     * Delete the node with highest key
     * @throws NoSuchElementException if the tree is empty
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot delete maximum from empty tree");
        }
        root = deleteMax(root);
    }

    /**
     * Delete a node with the given key from the subtree rooted at n
     * @param n the root of the subtree
     * @param key the key to delete
     * @return the updated subtree
     */
    private Node delete(Node n, int key) {
        if (n == null) {
            // Not found
            return null;
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp < 0) {
            // Search left
            n.left = delete(n.left, key);
        } else if (0 < cmp) {
            // Search right
            n.right = delete(n.right, key);
        } else {
            // Found matching node
            if (n.isLeaf()) {
                // Just remove leaf nodes
                return null;
            } else if (n.right == null) {
                // No right child -> return left
                return n.left;
            } else if (n.left == null) {
                // No left child -> return right
                return n.right;
            } else {
                // Node with two children -> Use Hibbard deletion to handle replacement with predecessor or successor
                return doDelete(n);
            }
        }
        return n;
    }

    /**
     * Delete a key from the tree
     * @param key the key to delete
     */
    public void delete(int key) {
        root = delete(root, key);
    }
    
    /**
     * Remove a key from the tree
     * @param key the key to remove
     * @return true if the key was found and removed, false otherwise
     */
    public boolean remove(int key) {
        if (!contains(key)) {
            return false;
        }
        delete(key);
        return true;
    }

    /**
     * Get keys in-order as Iterable
     * @return an iterable of all keys in sorted order
     */
    public Iterable<Integer> keys() {
        List<Integer> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }
    
    /**
     * Get keys in-order as Iterable
     * @return an iterable of all keys in sorted order
     */
    public Iterable<Integer> inorder() {
        List<Integer> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }
    
    /**
     * Get keys in pre-order as Iterable
     * @return an iterable of all keys in pre-order
     */
    public Iterable<Integer> preorder() {
        List<Integer> list = new LinkedList<>();
        preorder(root, list);
        return list;
    }
    
    /**
     * Get keys in post-order as Iterable
     * @return an iterable of all keys in post-order
     */
    public Iterable<Integer> postorder() {
        List<Integer> list = new LinkedList<>();
        postorder(root, list);
        return list;
    }
    
    /**
     * Get keys in level-order as Iterable
     * @return an iterable of all keys in level-order
     */
    public Iterable<Integer> levelorder() {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        
        java.util.Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            result.add(node.key);
            
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        
        return result;
    }
    
    /**
     * Get keys in the given range [lo, hi] as Iterable
     * @param lo the lower bound (inclusive)
     * @param hi the upper bound (inclusive)
     * @return an iterable of all keys in the given range
     */
    public Iterable<Integer> range(int lo, int hi) {
        List<Integer> list = new LinkedList<>();
        range(root, list, lo, hi);
        return list;
    }
    
    private void range(Node node, List<Integer> list, int lo, int hi) {
        if (node == null) {
            return;
        }
        
        if (lo < node.key) {
            range(node.left, list, lo, hi);
        }
        if (lo <= node.key && node.key <= hi) {
            list.add(node.key);
        }
        if (node.key < hi) {
            range(node.right, list, lo, hi);
        }
    }
    
    /**
     * Get the key of rank k (the kth smallest key)
     * @param k the rank
     * @return the key of rank k
     * @throws IllegalArgumentException if k is out of range
     */
    public int select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("Rank out of range");
        }
        return select(root, k);
    }
    
    private int select(Node node, int k) {
        if (node == null) return -1;
        
        int leftSize = (node.left == null) ? 0 : node.left.size();
        
        if (leftSize > k) {
            return select(node.left, k);
        } else if (leftSize < k) {
            return select(node.right, k - leftSize - 1);
        } else {
            return node.key;
        }
    }

    /** In-order traversal: left -> this -> right */
    private void inorder(Node n, List<Integer> list) {
        if (n == null) {
            return;
        }
        inorder(n.left, list);
        list.add(n.key);
        inorder(n.right, list);
    }

    /** Pre-order traversal: this -> left -> right */
    private void preorder(Node n, List<Integer> list) {
        if (n == null) {
            return;
        }
        list.add(n.key);
        preorder(n.left, list);
        preorder(n.right, list);
    }

    /** Post-order traversal: left -> right -> this */
    private void postorder(Node n, List<Integer> list) {
        if (n == null) {
            return;
        }
        postorder(n.left, list);
        postorder(n.right, list);
        list.add(n.key);
    }

    /** Gets the height of the tree */
    public int height() {
        return (root == null) ? 0 : root.height();
    }

    /**
     * Builds array of keys using in-order traversal
     * @return
     */
    public int[] asArray() {
        int len = (int) Math.pow(2, height());
        int[] arr = new int[len];
        Iterator<Integer> t = keys().iterator();
        for (int i = 0; i < len && t.hasNext(); ++i) {
            arr[i] = t.next();
        }
        return arr;
    }

    private class Node {
        private int key;
        private Node left, right;

        public Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }

        public Node(int key) {
            this(key, null, null);
        }

        /** Is this a leaf node with no children */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /** Count of nodes in this sub-tree */
        public int size() {
            if (isLeaf()) {
                return 1;
            }
            int lSize = (left == null) ? 0 : left.size();
            int rSize = (right == null) ? 0 : right.size();
            return 1 + lSize + rSize;
        }

        /** Height of this sub-tree */
        public int height() {
            int hLeft = (left == null) ? 0 : left.height();
            int hRight = (right == null) ? 0 : right.height();
            return 1 + Math.max(hLeft, hRight);
        }
    }
}
