/** Union find with weighting and path compression */
public class UnionFindWPC {
    // IDs form a tree where connected nodes point to their root
    public final int[] id;
    // Size is the weight of the tree
    public final int[] size;

    /** Initialize an unconnected graph */
    public UnionFindWPC(int n) {
        id = new int[n];
        size = new int[n];
        // Set id of each object to itself: O(N)
        for (int i = 0; i < n; ++i) {
            id[i] = i;
            size[i] = 1;
        }
    }

    /** Connects two nodes using a weighted tree structure: O(lg N) */
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        // Link the root of the smaller tree to the root of the larger and update size
        if (size[i] < size[j]) {
            id[i] = j;
            size[j] += size[i];
        }
        else {
            id[j] = i;
            size[i] += size[j];
        }
    }

    /** Check if p and q have the same root O(lg N) */
    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    /**
     * Get the root of a node O(lg N). Every node in the path points to its
     * grandparent. This halves the path length over regular union find.
     */
    private int root(int i) {
        while (i != id[i]) {
            int grandparent = id[id[i]];
            id[i] = grandparent;
            i = grandparent;
        }
        return i;
    }
}
