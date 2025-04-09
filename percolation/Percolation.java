public class Percolation {
  private boolean[] states;
  private int openSites;
  private int gridDimension;
  private int siteCount;
  private WeightedQuickUnionUF uf;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Must be a positive number");
    }

    gridDimension = n;
    siteCount = n * n;
    uf = new WeightedQuickUnionUF(n * n + 2);
    states = new boolean[siteCount + 2];
    openSites = 0;

    for (int i = 0; i < siteCount + 2; i++) {
      states[i] = false;
    }
    for (int i = 1; i <= gridDimension; i++) {
      uf.union(0, i);
      uf.union(siteCount + 1, siteCount + 1 - i);
    }
  }

  public void open(int row, int col) {
    validate(row);
    validate(col);
    int idx = idxFromRowCol(row, col);

    if (states[idx])
      return;
    states[idx] = true;
    openSites += 1;

    int right = idxFromRowCol(row, col + 1);
    int bot = idxFromRowCol(row + 1, col);
    int left = idxFromRowCol(row, col - 1);
    int up = idxFromRowCol(row - 1, col);

    int[] targets = { right, bot, left, up };
    for (int i = 0; i < targets.length; i++) {
      if (targets[i] != -1 && isOpenIdx(targets[i])) {
        uf.union(idx, targets[i]);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    validate(row);
    validate(col);

    int idx = idxFromRowCol(row, col);
    return isOpenIdx(idx);
  }

  private boolean isOpenIdx(int idx) {
    return states[idx];

  }

  public boolean isFull(int row, int col) {
    validate(row);
    validate(col);
    int idx = idxFromRowCol(row, col);
    return isFullIdx(idx);
  }

  private boolean isFullIdx(int idx) {
    return isOpenIdx(idx) && (uf.find(idx) == uf.find(0));
  }

  public int numberOfOpenSites() {
    return openSites;
  }

  public boolean percolates() {
    if (!(uf.find(siteCount + 1) == uf.find(0)))
      return false;

    for (int i = 1; i <= gridDimension; i++) {
      boolean st = states[states.length - 1 - i];
      if (st)
        return true;

    }
    return false;
  }

  public static void main(String[] args) {
    Percolation perc = new Percolation(Integer.parseInt(args[0]));

    perc.open(1, 1);

    System.out.println("Open sites: " + perc.numberOfOpenSites());
    System.out.println("Percolates: " + perc.percolates());
  }

  private void validate(int n) {
    if (n <= 0 || n > gridDimension) {
      throw new IllegalArgumentException("Must be a positive number");
    }
  }

  private int idxFromRowCol(int row, int col) {
    try {
      validate(row);
      validate(col);
    } catch (IllegalArgumentException e) {
    }

    return (row - 1) * gridDimension + col;
  }
}
