import java.io.IOException;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {
	private int n;
	private PriorityQueue<subTree> queue;

	private class subTree implements Comparable<subTree>{
		private int max;
		private int min;
		private int weight;
		
		public int compareTo(subTree that) {
			return this.weight-that.weight;
		}
	}
	
	
	public void readData(String filename) throws IOException {
		Scanner in=new Scanner(Paths.get(filename));
		n=in.nextInt();
		subTree subtree=new subTree();
		queue=new PriorityQueue<subTree>();
		for(int i=0;i<n;i++) {
			subtree=new subTree();
			subtree.max=0;
			subtree.min=0;
			subtree.weight=in.nextInt();
			queue.add(subtree);
		}
	}
	
	
	public void opt() {
		while(queue.size()>1) {
			subTree tree1=queue.poll();
			subTree tree2=queue.poll();
			int max=Math.max(tree1.max, tree2.max);
			int min=Math.min(tree1.min, tree2.min);
			subTree newtree=new subTree();
			newtree.max=max+1;
			newtree.min=min+1;
			newtree.weight=tree1.weight+tree2.weight;
			queue.add(newtree);
		}
		
		System.out.println("maximum length of a codeword= "+queue.peek().max);
		System.out.println("minimum length of a codeword= "+queue.peek().min);
	}
	
	public static void main(String[] args) throws IOException {
		Huffman test=new Huffman();
		test.readData("./data/huffman.txt");
		test.opt();
	}
}
