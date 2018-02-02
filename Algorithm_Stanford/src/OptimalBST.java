
public class OptimalBST {

	public static void main(String[] args) {
		int n = 7;
		double[] frequecy = { 0.2, 0.05, 0.17, 0.1, 0.2, 0.03, 0.25 };
		double[][] f = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i > j) {
					f[i][j] = 0;
				} else
					f[i][j] = Double.MAX_VALUE;
			}
		}

		for (int s = 0; s < n; s++) {
			for (int i = 0; i < n; i++) {
				if (i + s < n) {

					double p = 0;
					for (int r = i; r <= i + s; r++) {
						p += frequecy[r];
					}

					for (int r = i; r <= i + s; r++) {
						if (r - 1 < 0) {
							f[i][i + s] = Math.min(f[i][i + s], p + f[r + 1][i + s]);
						} else {
							if (r + 1 >= n) {
								f[i][i + s] = Math.min(f[i][i + s], p + f[i][r - 1]);
							} else {
								f[i][i + s] = Math.min(f[i][i + s], p + f[i][r - 1] + f[r + 1][i + s]);
							}

						}

					}

				}
			}
		}

		System.out.println(f[0][n - 1]);
	}
}
