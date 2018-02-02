import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// import edu.princeton.cs.algs4.Stopwatch;
public class PercolationStats {
    private double[] a;
    private int t;

    public PercolationStats(int n, int trials) { // perform trials independent
                                                 // experiments on an n-by-n
                                                 // grid
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        a = new double[trials];
        t = trials;

        for (int i = 0; i < trials; i++) {
            int count = 0;
            Percolation pc = new Percolation(n);
            while (!pc.percolates() && count <= n * n) {
                int testx = StdRandom.uniform(1, n * n + 1);
                while (pc.isOpen((testx - 1) / n + 1, (testx - 1) % n + 1))
                    testx = StdRandom.uniform(1, n * n + 1);
                count += 1;
                pc.open((testx - 1) / n + 1, (testx - 1) % n + 1);
            }
            a[i] = (double) count / (n * n);
        }
    }

    public double mean() {
        return (StdStats.mean(a));
    }

    public double stddev() {
        return (StdStats.stddev(a));
    }

    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(t));
    }

    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(t));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        // Stopwatch timer = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, t);
        // double time = timer.elapsedTime();
        // System.out.println("time                    = " + time);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo()
                + ", " + ps.confidenceHi());
    }

}
