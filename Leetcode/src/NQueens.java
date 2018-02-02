import java.util.ArrayList;
import java.util.List;

public class NQueens {
	static List<List<String>> result;
	static int N;
	static int[] location;
	static boolean[] select;
	static boolean[] select2;
	static boolean[] select3;

	public static List<List<String>> solveNQueens(int n) {
		result = new ArrayList<List<String>>();
		N = n;
		select = new boolean[n + 1];
		select2 = new boolean[n + n + 1];
		select3 = new boolean[n + n];
		location = new int[n + 1];
		dfs(1);
		return result;
	}

	public static void dfs(int row) {
		if (row == N + 1) {
			out();
			return;
		}
		for (int i = 1; i <= N; i++) {
			if (!select[i] && !select2[row + i] && !select3[row - i + N - 1]) {
				select[i] = true;
				select2[row + i] = true;
				select3[row - i + N - 1] = true;
				location[row] = i;
				dfs(row + 1);
				select[i] = false;
				select2[row + i] = false;
				select3[row - i + N - 1] = false;
			}
		}
	}

	public static void out() {
		List<String> ss = new ArrayList<>();
		for (int i = 1; i <= N; i++) {
			String s = "";
			for (int j = 1; j <= N; j++) {
				if (location[i] == j)
					s += "Q";
				else
					s += ".";
			}
			ss.add(s);
		}
		result.add(ss);
	}

	public static void main(String[] args) {
		System.out.println(solveNQueens(4));
	}

}
