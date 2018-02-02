import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

	private int n;
	private boolean[] blocked;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uff;

	public Percolation(int n) { // create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new java.lang.IllegalArgumentException();
		this.n = n;
		blocked = new boolean[n * n + 2];
		for (int i = 0; i < n * n + 2; i++) {
			blocked[i] = false;
		}
		blocked[0] = true;
		blocked[n * n + 1] = true;
		uf = new WeightedQuickUnionUF(n * n + 2);
		uff = new WeightedQuickUnionUF(n * n + 1);
	}

	public void open(int i, int j) { // open site (row i, column j) if it is not
										// open already
		if (i <= 0 || i > n || j <= 0 || j > n)
			throw new java.lang.IndexOutOfBoundsException();

		int x = i * n - n + j;
		if (blocked[x])
			return;
		blocked[x] = true;

		if (i == 1) {
			uf.union(0, x);
			uff.union(0, x);
		}

		if (i == n)
			uf.union(n * n + 1, x);

		int leftx = 0, rightx = 0, upx = -1, downx = 1;
		int lefty = -1, righty = 1, upy = 0, downy = 0;

		int xx = i + leftx, yy = j + lefty;
		if (xx > 0 && xx <= n && yy > 0 && yy <= n) {
			if (isOpen(xx, yy)) {
				if (isFull(xx, yy) || isFull(i, j)) {
					if (!isFull(xx, yy)) {
						uf.union(0, x - 1);
						uff.union(0, x - 1);
					}
					if (!isFull(i, j)) {
						uf.union(0, x);
						uff.union(0, x);
					}
				} else {
					uf.union(x, x - 1);
					uff.union(x, x - 1);
				}
			}
		}

		xx = i + rightx;
		yy = j + righty;
		if (xx > 0 && xx <= n && yy > 0 && yy <= n) {
			if (isOpen(xx, yy)) {
				if (isFull(xx, yy) || isFull(i, j)) {
					if (!isFull(xx, yy)) {
						uf.union(0, x + 1);
						uff.union(0, x + 1);
					}
					if (!isFull(i, j)) {
						uf.union(0, x);
						uff.union(0, x);
					}
				} else {
					uf.union(x, x + 1);
					uff.union(x, x + 1);
				}
			}
		}

		xx = i + upx;
		yy = j + upy;
		if (xx > 0 && xx <= n && yy > 0 && yy <= n) {
			if (isOpen(xx, yy)) {
				if (isFull(xx, yy) || isFull(i, j)) {
					if (!isFull(xx, yy)) {
						uf.union(0, x - n);
						uff.union(0, x - n);
					}
					if (!isFull(i, j)) {
						uf.union(0, x);
						uff.union(0, x);
					}
				} else {
					uf.union(x, x - n);
					uff.union(x, x - n);
				}
			}
		}

		xx = i + downx;
		yy = j + downy;
		if (xx > 0 && xx <= n && yy > 0 && yy <= n) {
			if (isOpen(xx, yy)) {
				if (isFull(xx, yy) || isFull(i, j)) {
					if (!isFull(xx, yy)) {
						uf.union(0, x + n);
						uff.union(0, x + n);
					}
					if (!isFull(i, j)) {
						uf.union(0, x);
						uff.union(0, x);
					}
				} else {
					uf.union(x, x + n);
					uff.union(x, x + n);
				}
			}
		}

	}

	public boolean isOpen(int i, int j) { // is site (row i, column j) open?
		if (i <= 0 || i > n || j <= 0 || j > n)
			throw new java.lang.IndexOutOfBoundsException();
		return blocked[i * n - n + j];
	}

	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		if (i <= 0 || i > n || j <= 0 || j > n)
			throw new java.lang.IndexOutOfBoundsException();
		int x = i * n - n + j;
		return blocked[x] && uff.connected(x, 0);
	}

	public boolean percolates() { // does the system percolate?
		return uf.connected(0, n * n + 1);
	}

	public static void main(String[] args) {
		int testn = 20;
		int count = 0;
		Percolation pc = new Percolation(testn);
		while (!pc.percolates() && count <= testn * testn) {
			int testx = StdRandom.uniform(1, testn * testn);
			while (pc.blocked[testx])
				testx = StdRandom.uniform(1, testn * testn);
			count += 1;
			pc.open((testx - 1) / testn + 1, (testx - 1) % testn + 1);
		}
		double percent = (double) count / (testn * testn);
		System.out.println(percent);
		System.out.println(count);

	}

}
