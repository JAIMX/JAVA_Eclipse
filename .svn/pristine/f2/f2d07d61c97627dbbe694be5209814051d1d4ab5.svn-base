import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations2 {
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
		Arrays.sort(nums);
		select = new boolean[n];
		dfs(1);
		return list;
	}


	public static void dfs(int step) {
		if (step == n + 1) {
			list.add(new ArrayList<>(temp));
			return;
		}

		long last = Long.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			if (!select[i] && num[i] != last) {
				select[i] = true;
				temp.add(num[i]);
				dfs(step + 1);
				select[i] = false;
				temp.remove(temp.size() - 1);
				last = num[i];
			}
		}

	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 1 };
		System.out.println(permute(a));
	}

}
