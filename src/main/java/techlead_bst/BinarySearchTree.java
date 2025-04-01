import edu.princeton.cs.algs4.Stack;

public class BinarySearchTree {
    private TreeNode root = null;

    private TreeNode add(TreeNode node, int value) {
        if (node == null) {
            // Create leaf here
            return new TreeNode(value);
        } else if (value < root.value) {
            node.left = add(node.left, value);
        } else if (root.value < value) {
            node.right = add(node.right, value);
        } else {
            // Set -> don't add duplicates
            return node;
        }

        // Update height
        node.height = height(node);
        return node;
    }

    public void add(int value) {
        add(root, value);
    }

    private TreeNode search(TreeNode node, int value) {
        if (node == null) {
            // Not found
            return null;
        } else if (node.value == value) {
            return node;
        } else if (value < node.value) {
            return search(value, node.left);
        } else {
            return search(value, node.right);
        }
    }

    public TreeNode search(int value) {
        return search(value, root);
    }

    private void doDelete(TreeNode toDelete, TreeNode parent) {
        if (toDelete == null) {
            throw new IllegalArgumentException("Cannot delete null node");
        } else if (!(toDelete == parent.left || toDelete == parent.right)) {
            throw new IllegalArgumentException("Node to delete is not direct child of input parent");
        } else if (parent == null) {
            // Remove the only leaf node
            root = null;
        } else if (toDelete.isLeaf()) {
            // Remove the leaf node from its parent
            if (toDelete == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (!(toDelete.left == null || toDelete.right == null)) {
            // Node has both children so need to replace it with its successor
            TreeNode successor = getSuccessorNode(toDelete);
            
        } else {
            // Node has a single child -> bring up the grandchild
            TreeNode grandChild = (toDelete.left == null) ? toDelete.right : toDelete.left;
            if (toDelete == parent.left) {
                parent.left = grandChild;
            } else {
                parent.right = grandChild;
            }
        }
    }

    /** Find the successor node in case of deletion in the right subtree.
     * Hop right, then as far as possible to the left. */
    private TreeNode getSuccessorNode(TreeNode node) {
        TreeNode successor = node.right;
        while (successor != null && successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    private void delete(int value, TreeNode next, TreeNode parent) {
        if (next == null) {
            // Not found
            return;
        } else if (value == next.value) {
            // Delete this
            doDelete(next, parent);
        } else if (value < next.value) {
            // Search and delete in left subtree
            delete(value, next.left, next);
        } else {
            // Search and delete in right subtree
            delete(value, next.right, next);
        }

        // Update height
        next.height = height(next);
    }

    public void delete(int value) {
        return delete(value, root, null);
    }

    private void preorder(int[] arr) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        for (int i = 0; i < size && !stack.isEmpty(); ++i) {
            Node next = stack.pop();
            // Visit current -> add value
            arr[i] = next.value;
            // Right child is pushed first -> left is processed first
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    private void postorder(int[] arr) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastVisited = null;
        int i = 0;
        for (TreeNode next = root; i < size && (!stack.isEmpty() || next != null); ) {
            if (next != null) {
                stack.push(node);
                next = next.left;
            } else {
                TreeNode peek = stack.peek();
                // Right child exists and traversing node from left child -> remove right
                if (peek.right != null && lastNodeVisited != peekNode.right) {
                    next = peekNode.right;
                } else {
                    // Visit peeked node
                    arr[i] = peek.value;
                    ++i;
                    lastVisited = stack.pop();
                }
            }
        }
    }

    private void inorder(int arr) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        int i = 0;
        for (TreeNode next = root; i < size && (!stack.isEmpty() || next != null); ) {
            if (node != null) {
                stack.push(node);
                next = next.left;
            } else {
                next = stack.pop();
                // Visit popped node
                arr[i] = next.value;
                ++i;
                next = next.right();
            }
        }
    }

    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int hLeft = height(node.left);
        int hRight = height(node.right);
        return 1 + Math.max(hLeft, hRight);
    }

    public int[] asArray() {
        int size = Math.pow(2, height);
        int[] arr = new int[size];
        preorder(arr);
        return arr;
    }

    public class TreeNode {
        private int value;
        private TreeNode left, right;
        int height;

        public TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.height = 1;
        }

        public TreeNode(int value) {
            this(value, null, null);
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int height() {
            int hLeft = height(node.left);
            int hRight = height(node.right);
            return 1 + Math.max(hLeft, hRight);
        }
    }
}
