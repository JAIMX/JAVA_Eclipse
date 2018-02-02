import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Week4_4 {
	
	private ArrayList<ArrayList<Integer>> adjList,adjList2;
	private int n,N;
	private int[] index1,index2;
//	private LinkedList<Integer> scc;
	
	
	// qu fei zhuan huan
	public int transfer(int index) {
		if(index>n) {
			return index-n;
		}else return index+n;
	}
	public void readData(String filename) throws IOException   {
		Scanner in=new Scanner(Paths.get(filename));
		n=in.nextInt();
		N=2*n+1;
		
		//initialize
		adjList=new ArrayList<ArrayList<Integer>>();
		adjList2=new ArrayList<ArrayList<Integer>>();	
		for(int i=1;i<=N;i++){
			ArrayList<Integer> temp=new ArrayList<Integer>();
			adjList.add(temp);
			temp=new ArrayList<Integer>();
			adjList2.add(temp);
		}
		
		int a,b;
		for(int i=0;i<n;i++) {
			a=in.nextInt();
			b=in.nextInt();
			if(a<0) {
				a=a*(-1)+n;
			}
			if(b<0) {
				b=b*(-1)+n;
			}
			
			adjList.get(transfer(a)).add(b);
			adjList2.get(b).add(transfer(a));
			
			adjList.get(transfer(b)).add(a);
			adjList2.get(a).add(transfer(b));
		}
		
		index1=new int[N];
		index2=new int[N];
		for(int i=1;i<=N-1;i++){
			index1[i]=N-i;
		}
	}

	public boolean scc(int numLoop) {
		int t = 0;
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] explored = new boolean[N];

		for (int i = 1; i < N; i++) {
			int node;
			if (numLoop == 1) {
				node = index1[i];
			} else {
				node = index2[N- i];
			}

			boolean[] expanded=new boolean[N];
			if (!explored[node]) {
				boolean[] checkFeasible=new boolean[N];
				int count = 0;
				stack.add(node);
				explored[node] = true;
				while (!stack.isEmpty()) {
					
					int currentNode = stack.get(stack.size() - 1);
					

					if(!expanded[currentNode]) {
//						boolean allexplored = true;
						if (numLoop == 1) {
							for (int j = 0; j < adjList2.get(currentNode).size(); j++) {
								int nextNode = adjList2.get(currentNode).get(j);
								if (!explored[nextNode]) {
//									allexplored = false;
									explored[nextNode] = true;
									stack.push(nextNode);
								}
							}
						} else {
							if (numLoop == 2) {
								for (int j = 0; j < adjList.get(currentNode).size(); j++) {
									int nextNode = adjList.get(currentNode).get(j);
									if (!explored[nextNode]) {
//										allexplored = false;
										explored[nextNode] = true;
										stack.push(nextNode);
									}
								}
							}
						}
						
						expanded[currentNode]=true;
					}else {
						if (numLoop == 1) {
							t++;
							index2[t] = currentNode;
						} else {
							if (numLoop == 2) {
//								count++;
								checkFeasible[currentNode]=true;
								if(checkFeasible[transfer(currentNode)]) return false;
							}
						}
						stack.pop();
					}
					

				}
				

			}
		}
		
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		Week4_4 test=new Week4_4();
		test.readData("./data/2sat4.txt");
		test.scc(1);
		System.out.println(test.scc(2));
	}
	
	
}
