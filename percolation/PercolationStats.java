import java.util.Random;

public class PercolationStats {
  private double[] thresholds;
  private double factor = 1.96;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("n or trials cannot be zero or less");
    }

    thresholds = new double[trials];

    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);

      while (!perc.percolates()) {
        int row = genRandomInt(1, n);
        int col = genRandomInt(1, n);
        perc.open(row, col);
      }

      thresholds[i] = (double) perc.numberOfOpenSites() / (n * n);
    }
  }

  public double mean() {
    double sum = 0;
    for (int i = 0; i < thresholds.length; i++) {
      sum += thresholds[i];
    }

    double avg = sum / thresholds.length;
    return avg;
  }

  public double stddev() {
    double avg = mean();
    double total = 0;
    for (int i = 0; i < thresholds.length; i++) {
      double diff = thresholds[i] - avg;
      total += Math.pow(diff, 2);
    }

    double sampleStdDev = Math.sqrt(total / thresholds.length - 1);
    return sampleStdDev;
  }

  public double confidenceLo() {
    return mean() - (factor * stddev() / Math.sqrt(thresholds.length));
  }

  public double confidenceHi() {
    return mean() + (factor * stddev() / Math.sqrt(thresholds.length));
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percStats = new PercolationStats(n, trials);

    System.out.println(String.format("%-24s = %s", "mean", percStats.mean()));
    System.out.println(String.format("%-24s = %s", "stddev", percStats.stddev()));
    System.out.println(String.format("%-24s = [%s, %s]", "95% confidence interval", percStats.confidenceLo(),
        percStats.confidenceHi()));
  }

  private int genRandomInt(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min) + min;
  }
}
