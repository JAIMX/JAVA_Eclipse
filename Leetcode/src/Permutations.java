import java.util.ArrayList;
import java.util.List;

public class Permutations {
	static int[] num;
	static List<List<Integer>> list;
	static List<Integer> temp;
	static boolean[] select;
	static int n;

	public static List<List<Integer>> permute(int[] nums) {
		n = nums.length;
		list = new ArrayList<>();
		temp = new ArrayList<>();
		num = new int[n];
		num = nums;
		select = new boolean[n];
		dfs(1);
		return list;
	}

	public static void dfs(int step) {
		if (step == n + 1) {
			list.add(new ArrayList<>(temp));
			return;
		}
		for (int i = 0; i < n; i++) {
			if (!select[i]) {
				select[i] = true;
				temp.add(num[i]);
				dfs(step + 1);
				select[i] = false;
				temp.remove(temp.size() - 1);
			}
		}
	}
/*
	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4 };
		System.out.println(permute(a));
	}
*/
}
