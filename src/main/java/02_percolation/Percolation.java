import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

import java.lang.IllegalArgumentException;
import java.lang.NumberFormatException;

/**
 * If sites are independently set to be open with probability p and blocked
 * with probability 1-p, what is the probability that the system percolates?
 * When n is sufficiently large there is a threshold value p* such that when
 * p < p* a random n by n grid almost never percolates. When p > p* the random
 * grid almost always percolates. This program estimates p*.
 * <p>
 * Corner cases: By convention the row and column indices are integers in range
 * [1, n] where (1,1) is the upper-left site. Throw IllegalArgumentException
 * if any argument to open(), isOpen() or isFull() is outside its prescribed
 * range.
 * <p>
 * Performance requirements: The constructor must take time proportional to
 * n^2; all instance methods must take constant time plus a constant number
 * of calls to union() and find().
 * <p>
 * Run a Monte Carlo simulation: To estimate the percolation threshold, consider
 * the following experiment:
 * 1. Initialize all sites to be blocked
 * 2. Repeat until the system percolates:
 * a. Choose a site uniformly at random among all blocked sites
 * b. Open the site
 * 3. The fraction of sites that are opened when the system percolates provides
 * an estimate of the percolation threshold.
 */
public class Percolation {
    public static final int DEFAULT_N = 20;

    // n is number of rows and columns
    private final int n;
    // Connectivity is modeled using union-find with weighting and path compression
    private final WeightedQuickUnionUF uf;
    // The grid of sites. A site is open (true) or closed (false)
    private final boolean[][] open;
    // Virtual node at the top of the grid
    private final int TOP;
    // Virtual node at the bottom of the grid
    private final int BOTTOM;
    // Count of open sites
    private int openSites = 0;

    /**
     * Creates n-by-n grid, with all sites initially blocked
     * Must take time proportional to n * n
     *
     * @param n Number of rows and columns in the grid
     */
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid size n must be positive");
        }
        this.n = n;
        TOP = n * n;
        BOTTOM = TOP + 1;
        uf = new WeightedQuickUnionUF(BOTTOM + 1);
        open = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndex(row, col);
        if (!open[row - 1][col - 1]) {
            doOpen(row, col, index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return open[row - 1][col - 1];
    }

    /**
     * Is the site (row, col) full?
     *
     * @param row Row indexed starting at 1
     * @param col Column indexed starting at 1
     * @return True if and only if the site is open and connected to
     * an open site in the top row.
     */
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        return isOpen(row, col) && isConnected(index, TOP);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Does the system percolate? True if and only if there is a full site
     * in the bottom row. This means the open site in the bottom connects to
     * a full site at the top.
     */
    public boolean percolates() {
        return isConnected(TOP, BOTTOM);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = DEFAULT_N;
        if (0 < args.length) {
            try {
                n = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                StdOut.println("Failed to parse input grid size " + args[0]);
            }
        }
        Percolation perc = new Percolation(n);
        StdOut.println("Created Percolation of size " + n);
    }

    // Helpers

    /** Check whether nodes with specified indices are connected in union find */
    private boolean isConnected(int p, int q) {
        int setP = uf.find(p);
        int setQ = uf.find(q);
        return setP == setQ;
    }

    /** Validates that a row-column pair is in range [1, n] */
    private void validateRowCol(int row, int col) {
        if (row < 1 || row < n) {
            throw new IllegalArgumentException("Row be positive and less than n");
        }
        else if (col < 1 || col < n) {
            throw new IllegalArgumentException("Column be positive and less than n");
        }
    }

    /** Maps a row-column pair to a scalar index */
    private int getIndex(int row, int col) {
        validateRowCol(row, col);
        return row * n + col;
    }

    /** Does the work of opening a site */
    private void doOpen(int row, int col, int index) {
        open[row][col] = true;
        ++openSites;
        connectToOpenNeighbors(row, col, index);
        connectToVirtualTopBottom(row, index);
    }

    /** Connects a site to its neighbors who are open */
    private void connectToOpenNeighbors(int row, int col, int index) {
        if (0 < row && isOpen(row - 1, col)) {
            int neighborBelow = getIndex(row - 1, col);
            connectToNeighbor(index, neighborBelow);
        }
        if (row < n - 1 && isOpen(row + 1, col)) {
            int neighborAbove = getIndex(row + 1, col);
            connectToNeighbor(index, neighborAbove);
        }
        if (0 < col && isOpen(row, col - 1)) {
            int neighborToLeft = getIndex(row, col - 1);
            connectToNeighbor(index, neighborToLeft);
        }
        if (col < n - 1 && isOpen(row, col + 1)) {
            int neighborToRight = getIndex(row, col + 1);
            connectToNeighbor(index, neighborToRight);
        }
    }

    /** Connects open sites in the top and bottom rows to the virtual nodes */
    private void connectToVirtualTopBottom(int row, int index) {
        if (row == 0) {
            connectToNeighbor(index, TOP);
        }
        if (row == n - 1) {
            connectToNeighbor(index, BOTTOM);
        }
    }

    /** Connects a site to its neighbor */
    private void connectToNeighbor(int p, int q) {
        uf.union(p, q);
    }
}
