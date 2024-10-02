import java.lang.IllegalArgumentException;

/**
 * Throw an IllegalArgumentException in the constructor if either n <= 0
 * or trials <= 0. The main method takes two command-line arguments n and T.
 * This performs T independent experiments on an n by n grid and prints the
 * sample mean, standard deviation, and the 95% confidence interval for the
 * percolation threshold. Use StdRandom to generate numbers, StdStats
 * to compute the sample mean and standard deviation.
 */
public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid size n must be positive");
        }
        else if (trials < 1) {
            throw new IllegalArgumentException("Number of trials must be positive");
        }
    }

    // sample mean of percolation threshold
    public double mean() {

    }

    // sample standard deviation of percolation threshold
    public double stddev() {

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

    }

    // test client (see below)
    public static void main(String[] args) {

    }

}
