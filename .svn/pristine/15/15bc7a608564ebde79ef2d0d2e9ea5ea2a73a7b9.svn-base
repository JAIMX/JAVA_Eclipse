import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class k_means {

	private int[] parent, rank;
	private int numNode;
	private PriorityQueue<Edge> queue;

	private class Edge implements Comparable<Edge> {
		private int u;
		private int v;
		private int distance;

		public int compareTo(Edge that) {
			return this.distance - that.distance;
		}
	}

	public int find(int node) {
		int root = -1;
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] check = new boolean[numNode];
		stack.add(node);
		while (!stack.isEmpty()) {
			int currentNode = stack.peek();

			if (check[currentNode]) {
				parent[currentNode] = root;
				stack.pop();
			} else {
				if (parent[currentNode] == currentNode) {
					root = currentNode;
					stack.pop();
				} else {
					stack.add(parent[currentNode]);
					check[currentNode] = true;
				}
			}

		}

		return root;
	}

	public void union(int node1, int node2) {
		int root1 = find(node1);
		int root2 = find(node2);
		if (root1 != root2) {

			if (rank[root1] < rank[root2]) {
				parent[root1] = root2;
			} else {
				if (rank[root1] > rank[root2]) {
					parent[root2] = root1;
				} else {
					parent[root2] = root1;
					rank[root1]++;
				}
			}

		}

	}

	public int clustering1(String filename, int k) throws IOException {
		// read data
		Scanner in = new Scanner(Paths.get(filename));
		numNode = in.nextInt();

		parent = new int[numNode];
		rank = new int[numNode];

		for (int i = 0; i < numNode; i++) {
			parent[i] = i;
		}

		queue = new PriorityQueue<Edge>();

		while (in.hasNextInt()) {
			Edge edge = new Edge();
			edge.u = in.nextInt() - 1;
			edge.v = in.nextInt() - 1;
			edge.distance = in.nextInt();
			queue.add(edge);
		}

		// we need merge n-k times
		int merge = 0;
		while (merge < numNode - k) {
			Edge edge = queue.poll();
			int node1 = edge.u;
			int node2 = edge.v;
			if (find(node1) != find(node2)) {
				union(node1, node2);
				merge++;
			}
		}

		Edge edge = queue.poll();
		int node1 = edge.u;
		int node2 = edge.v;

		while (find(node1) == find(node2)) {
			edge = queue.poll();
			node1 = edge.u;
			node2 = edge.v;
		}

		System.out.println("The spacing is " + edge.distance);
		return edge.distance;

	}

	public char transfer(char tempChar) {
		if (tempChar == '0')
			return '1';
		return '0';
	}

	public void clustering2(String filename) throws IOException {
		Scanner in = new Scanner(Paths.get(filename));
		numNode = in.nextInt();
		int bit = in.nextInt();
		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		parent = new int[numNode];
		rank = new int[numNode];
		int repeatNode=0;

		for (int i = 0; i < numNode; i++) {
			parent[i] = i;
		}

		String string = in.nextLine();
		// read data
		for (int i = 0; i < numNode; i++) {
			string = in.nextLine();
			String tempString = "";
			for (int j = 0; j < string.length(); j++) {
				if (string.charAt(j) != ' ') {
					tempString += string.charAt(j);
				}
			}

//			if(i<5) {
//				System.out.println(tempString.toString() + " " + i);
//			}
			

			if(hashmap.containsKey(tempString)) repeatNode++;
			hashmap.put(tempString, i);
		}
		
		System.out.println("repeatNode= "+repeatNode);

		int merge = 0;
		 int count=0;
		// merge all edges whose length is 1 or 2
		Iterator iter = hashmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();

			String keyy = (String) key;
			int vall = (int) val;

			// System.out.println(keyy.toString());
			// System.out.println(vall);

			int node1 = vall;

			 count++;
//			 if(count==1)System.out.println(keyy.toString());
			// System.out.println(count);

			// merge the edge of length 1
			for (int i = 0; i < bit; i++) {
				String tempString = "";
				tempString += keyy.substring(0, i);
				// System.out.println("i= "+i);
				// System.out.println(keyy);
				tempString += transfer(keyy.charAt(i));
				tempString += keyy.substring(i + 1, keyy.length());
//				if(count==1)System.out.println(tempString.toString());

				if (hashmap.containsKey(tempString)) {
					int node2 = hashmap.get(tempString);
					if (find(node1) != find(node2)) {
						union(node1, node2);
						merge++;
					}
				}
			}

			// merge the edge of length 2
			for (int i = 0; i < bit; i++) {
				for (int j = i + 1; j < bit; j++) {

					String tempString = "";
					tempString += keyy.substring(0, i);
					tempString += transfer(keyy.charAt(i));
					tempString += keyy.substring(i + 1, j);
					tempString += transfer(keyy.charAt(j));
					tempString += keyy.substring(j + 1, keyy.length());
					
//					if(count==1)System.out.println(tempString.toString());

					if (hashmap.containsKey(tempString)) {
						int node2 = hashmap.get(tempString);
						if (find(node1) != find(node2)) {
							union(node1, node2);
							merge++;
						}
					}

				}

			}

		}

//		System.out.println("repeatNode= "+repeatNode);
		System.out.println(numNode - merge-repeatNode);

	}

	public static void main(String[] args) throws IOException {
		k_means test = new k_means();
		 test.clustering1("./data/clustering1.txt", 4);
//		test.clustering2("./data/clustering_big.txt");
//		 test.clustering2("./data/test.txt");
//		test.clustering2("./data/input_random_80_65536_22.txt");
		

	}
}
