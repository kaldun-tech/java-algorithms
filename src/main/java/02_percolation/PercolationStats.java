import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Throw an IllegalArgumentException in the constructor if either n <= 0
 * or trials <= 0. The main method takes two command-line arguments n and T.
 * This performs T independent experiments on an n by n grid and prints the
 * sample mean, standard deviation, and the 95% confidence interval for the
 * percolation threshold. Use StdRandom to generate numbers, StdStats
 * to compute the sample mean and standard deviation.
 */
public class PercolationStats {
    /** Default size of grid */
    private static final int DEFAULT_N = 20;
    /** Default number of trials */
    private static final int DEFAULT_TRIALS = 30;

    /** Number of rows and columns in percolation grid */
    private final int n;
    /** Number of trials to conduct in simulation */
    private final int trials;
    /** Observed percolation thresholds of trials */
    private final double[] observedThresholds;
    /** Track closed sites during a trial */
    private int[][] closedSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid size n must be positive");
        }
        else if (trials < 1) {
            throw new IllegalArgumentException("Number of trials must be positive");
        }
        this.n = n;
        this.trials = trials;
        this.observedThresholds = new double[trials];
        runMonteCarloSimulation();
    }

    private void runMonteCarloSimulation() {
        for (int i = 0; i < trials; ++i) {
            observedThresholds[i] = runTrial();
        }
    }

    /**
     * Runs a trial of the Monte Carlo simulation on the Percolation
     *
     * @return Estimate of percolation threshold given by the number of open
     * sites divided by the number of total sites as a double.
     */
    private double runTrial() {
        Percolation percolation = new Percolation(n);
        initializeClosedSites();
        for (int i = 0; i < n * n && !percolation.percolates(); ++i) {
            openClosedSite(percolation, i);
        }
        int openSites = percolation.numberOfOpenSites();
        return (double) openSites / (n * n);
    }

    // Initialize a uniformly random table of closed sites to open
    private void initializeClosedSites() {
        closedSites = new int[n * n][2];
        int i = 0;
        for (int row = 1; row <= n; ++row) {
            for (int col = 1; col <= n; ++col) {
                closedSites[i] = new int[] { row, col };
                ++i;
            }
        }
        // Shuffle the sites to open them in uniform random order
        StdRandom.shuffle(closedSites);
    }

    /** Chooses and opens the next closed site */
    private void openClosedSite(Percolation percolation, int i) {
        // Select and open
        int row = closedSites[i][0];
        int col = closedSites[i][1];
        if (!percolation.isOpen(row, col)) {
            percolation.open(row, col);
        }
    }

    /** Sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(observedThresholds);
    }

    /** Sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(observedThresholds);
    }

    /** Get the confidence interval */
    private double confidenceInterval() {
        return 1.96 * stddev() / Math.sqrt(trials);
    }

    /** Low endpoint of 95% confidence interval */
    public double confidenceLo() {
        return mean() - confidenceInterval();
    }

    /** High endpoint of 95% confidence interval */
    public double confidenceHi() {
        return mean() + confidenceInterval();
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = DEFAULT_N;
        int trials = DEFAULT_TRIALS;
        if (0 < args.length) {
            try {
                n = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                StdOut.println("Failed to parse size of grid from " + args[0]);
            }
        }
        if (1 < args.length) {
            try {
                trials = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                StdOut.println("Failed to parse trials from " + args[1]);
            }
        }

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean\t\t\t= " + stats.mean());
        StdOut.println("stddev\t\t\t= " + stats.stddev());

        StringBuilder sb = new StringBuilder("95% confidence interval\t= [")
                .append(stats.confidenceLo())
                .append(", ")
                .append(stats.confidenceHi())
                .append("]");
        StdOut.println(sb.toString());
    }

}
