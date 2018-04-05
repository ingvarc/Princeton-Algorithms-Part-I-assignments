import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * A class to perform a series of computational experiments.
 */
public class PercolationStats {

    /**
     * The array to store the results of all trials.
     */
    private double[] threshold;

    /**
     * Performs trials independent experiments on an n-by-n grid.
     *
     * @param gridDimension     the dimension of the grid side
     * @param experimentsNumber the number of trials
     */
    public PercolationStats(int gridDimension, int experimentsNumber) {
        if (gridDimension <= 0 || experimentsNumber <= 0) {
            throw new IllegalArgumentException("Arguments are out of bound.");
        }

        threshold = new double[experimentsNumber];

        for (int i = 0; i < experimentsNumber; i++) {
            int openedSites = 0;
            Percolation percolation = new Percolation(gridDimension);
            do {
                int row = StdRandom.uniform(1, gridDimension + 1);
                int col = StdRandom.uniform(1, gridDimension + 1);
                if (percolation.isOpen(row, col)) {
                    continue;
                }
                percolation.open(row, col);
                openedSites++;
            } while (!percolation.percolates());

            threshold[i] = (double) openedSites / (gridDimension * gridDimension);
        }
    }

    /**
     * Starting point.
     *
     * @param args an array of two arguments: the dimension of the grid side and the number of trials
     */
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean = %f\n", percolationStats.mean());
        StdOut.printf("stddev = %f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }

    /**
     * Returns the mean of percolation threshold.
     *
     * @return the mean of percolation threshold; {@code Double.NaN} if there is no values
     */
    public double mean() {
        return StdStats.mean(threshold);
    }

    /**
     * Returns the standard deviation of percolation threshold.
     *
     * @return the standard deviation; {@code Double.NaN} if there is no values
     */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /**
     * Returns the half of the interval of the percolation threshold.
     *
     * @return the half of the interval
     */
    private double halfInterval() {
        return 1.96 * stddev() / Math.sqrt(threshold.length);
    }

    /**
     * Returns the low endpoint of 95% confidence interval for the percolation threshold.
     *
     * @return the low endpoint
     */
    public double confidenceLo() {
        return mean() - halfInterval();
    }


    /**
     * Returns the high endpoint of 95% confidence interval for the percolation threshold.
     *
     * @return the high endpoint
     */
    public double confidenceHi() {
        return mean() + halfInterval();
    }
}
