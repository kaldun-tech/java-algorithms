import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * If sites are independently set to be open with probability p and blocked
 * with probability 1-p, what is the probability that the system percolates?
 * When n is sufficiently large there is a threshold value p* such that when
 * p < p* a random n by n grid almost never percolates. When p > p* the random
 * grid almost always percolates. This program estimates p*.
 * 
 * Corner cases: By convention the row and column indices are integers in range
 * [1, n] where (1,1) is the upper-left site. Throw IllegalArgumentException
 * if any argument to open(), isOpen() or isFull() is outside its prescribed
 * range.
 *
 * Performance requirements: The constructor must take time proportional to
 * n^2; all instance methods must take constant time plus a constant number
 * of calls to union() and find().
 *
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

    // n is size of grid
    final int n;
    // grid is composed of sites which are flagged as open (true) or blocked (false)
    final boolean grid[][];
    int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid size n must be positive")
        }
        this.n = n;
        grid = new boolean[n][];
        for (int i = 0; i < n; ++i) {
            grid[i] = new boolean[n];
        }
    }

    // Helper checks input
    private void validateRowCol(int row, int col) {
        if (row < 1 || row < n) {
            throw new IllegalArgumentException("Row be positive and less than n")
        } else if (col < 1 || col < n) {
            throw new IllegalArgumentException("Column be positive and less than n")
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol();
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            ++openSites;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol();
        return grid[row - 1][col - 1];
    }

    /** Is the site (row, col) full?
     * @param row Row indexed starting at 1
     * @param col Column indexed starting at 1
     * @return True if and only if the site is open and connected to
     *         an open site in the top row.
     */
    public boolean isFull(int row, int col) {
        validateRowCol();
        // TODO union find
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    /** Does the system percolate? True if and only if there is a full site
     * in the bottom row. This means the open site in the bottom connects to
     * a full site at the top. */
    public boolean percolates() {
        int bottomRow = grid[n - 1];
        // TODO Can this be quicker using dynamic connectivity?
        for (int i = 0; i < n; ++i) {
            if (isFull(n, i)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
