import java.util.ArrayList;
import java.util.List;

public class Combinations {
	static List<List<Integer>> result;
	static List<Integer> record;
	static int N;
	static int K;
	static boolean[] select;

	public static List<List<Integer>> combine(int n, int k) {
		N = n;
		K = k;
		result = new ArrayList<List<Integer>>();
		record = new ArrayList<>();
		select = new boolean[n + 1];
		dfs(1, 0);
		return result;
	}

	public static void dfs(int step, int last) {
		if (step == K + 1) {
			result.add(new ArrayList<Integer>(record));
			return;
		}

		for (int i = last + 1; i <= N-K+step; i++) {
			if (!select[i]) {
				select[i] = true;
				record.add(i);
				dfs(step + 1, i);
				select[i] = false;
				record.remove(record.size() - 1);
			}
		}
	}

	public static void main(String[] args) {
		combine(4, 2);
		System.out.println(result);
	}

}
