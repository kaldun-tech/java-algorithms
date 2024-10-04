import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

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
    /** Default size of grid */
    private static final int DEFAULT_N = 20;

    // n is number of rows and columns
    private final int n;
    // Connectivity is modeled using union-find with weighting and path compression
    private final WeightedQuickUnionUF ufPercolation;
    // For checking if a site is full
    private final WeightedQuickUnionUF ufFull;
    // The grid of sites. A site is open (true) or closed (false)
    private final boolean[][] open;
    // Virtual node at the top of the grid
    private final int top;
    // Virtual node at the bottom of the grid
    private final int bottom;
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
        top = n * n;
        bottom = top + 1;
        ufFull = new WeightedQuickUnionUF(bottom);
        ufPercolation = new WeightedQuickUnionUF(bottom + 1);
        open = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            doOpen(row, col);
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
        return isOpen(row, col) && ufFull.find(index) == ufFull.find(top);
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
        return ufPercolation.find(top) == ufPercolation.find(bottom);
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

    /** Validates that a row-column pair is in range [1, n] */
    private void validateRowCol(int row, int col) {
        if (row < 1 || n < row) {
            StringBuilder sb = new StringBuilder("Row ")
                    .append(row)
                    .append(" must be positive and less than n");
            throw new IllegalArgumentException(sb.toString());
        }
        else if (col < 1 || n < col) {
            StringBuilder sb = new StringBuilder("Column ")
                    .append(col)
                    .append(" must be positive and less than n");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /** Maps a row-column pair to a scalar index starting from zero */
    private int getIndex(int row, int col) {
        validateRowCol(row, col);
        return n * (row - 1) + col - 1;
    }

    /** Does the work of opening a site */
    private void doOpen(int row, int col) {
        int index = getIndex(row, col);
        open[row - 1][col - 1] = true;
        ++openSites;
        connectToOpenNeighbors(row, col, index);
        connectToVirtualTopBottom(row, index);
    }

    /** Connects a site to its neighbors who are open */
    private void connectToOpenNeighbors(int row, int col, int index) {
        if (1 < row) {
            // Connect below
            connectIfOpen(index, row - 1, col);
        }
        if (row < n) {
            // Connect above
            connectIfOpen(index, row + 1, col);
        }
        if (1 < col) {
            // Connect to left
            connectIfOpen(index, row, col - 1);
        }
        if (col < n) {
            // Connect to right
            connectIfOpen(index, row, col + 1);
        }
    }

    /** Connects open sites in the top and bottom rows to the virtual nodes */
    private void connectToVirtualTopBottom(int row, int index) {
        if (row == 1) {
            // Connect to virtual top if in first row
            ufPercolation.union(top, index);
            ufFull.union(top, index);
        }
        if (row == n) {
            // Connect to virtual bottom in last row for only percolation check
            ufPercolation.union(bottom, index);
        }
    }

    private void connectIfOpen(int index, int otherRow, int otherCol) {
        if (isOpen(otherRow, otherCol)) {
            int neighbor = getIndex(otherRow, otherCol);
            connectToNeighbor(index, neighbor);
        }
    }

    /** Connects a site to its neighbor */
    private void connectToNeighbor(int index, int neighbor) {
        ufPercolation.union(index, neighbor);
        ufFull.union(index, neighbor);
    }
}
