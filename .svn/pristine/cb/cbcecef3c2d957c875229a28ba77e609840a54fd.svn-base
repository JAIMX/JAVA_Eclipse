public class NQueens2 {
	static int count;
	static int N;
	static boolean[] select;
	static boolean[] select2;
	static boolean[] select3;

	public static int totalNQueens(int n) {
		N = n;
		count = 0;
		select = new boolean[n + 1];
		select2 = new boolean[n + n + 1];
		select3 = new boolean[n + n];
		dfs(1);
		return count;
	}

	public static void dfs(int row) {
		if (row == N + 1) {
			count++;
			return;
		}
		for (int i = 1; i <= N; i++) {
			if (!select[i] && !select2[row + i] && !select3[row - i + N - 1]) {
				select[i] = true;
				select2[row + i] = true;
				select3[row - i + N - 1] = true;
				dfs(row + 1);
				select[i] = false;
				select2[row + i] = false;
				select3[row - i + N - 1] = false;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(totalNQueens(4));
	}
}
