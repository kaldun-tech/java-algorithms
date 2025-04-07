import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

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
        add(key, root);
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
     * @param key
     * @return
     */
    public Node search(int key) {
        return search(key, root);
    }

    private Node min(Node n) {
        Node less;
        for (less = n; less != null && less.left != null; less = less.left) {}
        return less;
    }

    /**
     * Get minimum key or zero on failure
     * @return
     */
    public int min() {
        Node n = min(root);
        return (n == null) ? 0 : n.key;
    }

    private Node max(Node n) {
        Node gtr;
        for (gtr = n; gtr != null && gtr.right != null; gtr = gtr.right) {}
        return gtr;
    }

    /**
     * Get maximum key or zero on failure
     * @return
     */
    public int max() {
        Node n = max(root);
        return (n == null) ? 0 : n.key;
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
     * Get the greatest key less than or equal to the input key or zero on failure
     * @param key
     * @return
     */
    public int ceiling(int key) {
        Node c = ceiling(key, root);
        return (c == null) ? 0 : c.key;
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
     * Get the least key greater or equal to the input key or zero on failure
     * @param key
     * @return
     */
    public int floor(int key) {
        Node f = floor(key, root);
        return (f == null) ? 0 : f.key;
    }

    /** Get the size of the tree */
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        return (n == null) ? 0 : n.size();
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
            // Search left
            return rank(key, n.left);
        } else if (0 < cmp) {
            // Size of this plus left subtree plus search right
            return 1 + size(n.left) + rank(key, n.right);
        } else {
            // Found the ranked node -> return size of left subtree
            return size(n.left);
        }
    }

    /** Hibbard deletion: use the predecessor or successor node with equal probability */
    private Node doDelete(Node n) {
        double probability = 0.5;
        boolean useSuccessor = StdRandom.bernoulli(probability);
        if (useSuccessor) {
            Node suc = getSuccessor(n);
            n.right = deleteMin(suc.right);
            n.left = suc.left;
        } else { // use predecessor
            Node pred = getPredecessor(n);
            n.left = deleteMax(pred.left);
            n.right = pred.right;
        }

        return n;
    }

    /** Predecessor is one hop left then all the way right */
    private Node getPredecessor(Node n) {
        return max(n.left);
    }

    private Node deleteMax(Node n) {
        if (n.right == null) {
            return n.left;
        }
        n.right = deleteMax(n.right);
        return n;
    }

    /**
     * Delete the node with highest key
     */
    public void deleteMax() {
        deleteMax(root);
    }

    /** Successor is one hop right then all the way left */
    private Node getSuccessor(Node n) {
        return min(n.right);
    }

    private Node deleteMin(Node n) {
        if (n.left == null) {
            return n.right;
        }
        n.left = deleteMin(n.left);
        return n;
    }

    /**
     * Delete the node with least key
     */
    public void deleteMin() {
        deleteMin(root);
    }

    private Node delete(Node n, int key) {
        if (n == null) {
            // Not found
            return null;
        }
        int cmp = Integer.compare(key, n.key);
        if (cmp < 0) {
            // Search left
            delete(n.left, key);
        } else if (0 < cmp) {
            // Search right
            delete(n.right, key);
        } else {
            // Found matching node
            if (n.isLeaf()) {
                n = doDelete(n);
            } else if (n.right == null) {
                // No right child -> return left
                return n.left;
            } else if (n.left == null) {
                // No left child -> return right
                return n.right;
            }
        }

        return n;
    }

    public void delete(int key) {
        delete(root, key);
    }

    /**
     * Get keys in-order as Iterable
     * @return
     */
    public Iterable<Integer> keys() {
        Queue<Integer> q = new Queue<>();
        inorder(root, q);
        return q;
    }

    /** In-order traversal: left -> this -> right */
    private void inorder(Node n, Queue<Integer> q) {
        if (n == null) {
            return;
        }
        inorder(n.left, q);
        q.enqueue(n.key);
        inorder(n.right, q);
    }

    /** Pre-order traversal: this -> left -> right */
    private void preorder(Node n, Queue<Integer> q) {
        if (n == null) {
            return;
        }
        q.enqueue(n.key);
        preorder(n.left, q);
        preorder(n.right, q);
    }

    /** Post-order traversal: left -> right -> this */
    private void postorder(Node n, Queue<Integer> q) {
        if (n == null) {
            return;
        }
        postorder(n.left, q);
        postorder(n.right, q);
        q.enqueue(n.key);
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
