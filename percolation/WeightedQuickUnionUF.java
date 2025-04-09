public class WeightedQuickUnionUF {
  private int[] sites;
  private int[] size;

  public WeightedQuickUnionUF(int n) {
    sites = new int[n];
    size = new int[n];
    for (int i = 0; i < n; i++) {
      sites[i] = i;
      size[i] = 1;
    }
  }

  public int find(int p) {
    validate(p);
    while (p != sites[p])
      p = sites[p];
    return p;
  }

  private void validate(int p) {
    int n = sites.length;
    if (p < 0 || p >= n) {
      throw new IllegalArgumentException("p:  " + p + "is beyond the allowed range.");
    }
  }

  public void union(int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);
    if (rootP == rootQ)
      return;

    if (size[rootP] < size[rootQ]) {
      sites[rootP] = rootQ;
      size[rootQ] += size[rootP];
    } else {
      sites[rootQ] = rootP;
      size[rootP] += size[rootQ];
    }
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);

    System.out.println(uf.size.length);
  }
}
