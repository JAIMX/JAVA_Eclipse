import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class Week1_2 {
	private ArrayList<ArrayList<Integer>> adjList,adjList2;
	private int n;
	private int[] index1,index2;
	private LinkedList<Integer> scc;

	
	
	public Week1_2(String filename,int n) throws IOException{
		scc=new LinkedList<Integer>();
		adjList=new ArrayList<ArrayList<Integer>>();
		adjList2=new ArrayList<ArrayList<Integer>>();
		this.n=n;
		
		for(int i=0;i<n;i++){
			ArrayList<Integer> temp=new ArrayList<Integer>();
			adjList.add(temp);
			temp=new ArrayList<Integer>();
			adjList2.add(temp);
		}
		
		//read data
		Scanner in=new Scanner(Paths.get(filename));
		while(in.hasNextInt()){
			int a=in.nextInt()-1;
			int b=in.nextInt()-1;
//			System.out.println("a= "+a);
//			System.out.println("b= "+b);
//			System.out.println();
			adjList.get(a).add(b);
			adjList2.get(b).add(a);
		}
		
		index1=new int[n];
		index2=new int[n];
		for(int i=0;i<n;i++){
			index1[i]=n-1-i;
		}
		
		
	}
	
	public void DFS_Loop(int numLoop){
		int t=0;
		Stack<Integer> stack=new Stack<Integer>();
		boolean[] explored=new boolean[n];
		
		
		for(int i=0;i<n;i++){
			int node;
			if(numLoop==1){
				node=index1[i];
			}else{
				node=index2[n-1-i];
			}
			
			if(!explored[node]){
				int count=0;
				stack.add(node);
				explored[node]=true;
				while(!stack.isEmpty()){
					
					int currentNode=stack.get(stack.size()-1);
					boolean allexplored=true;
					if(numLoop==1){
						for(int j=0;j<adjList2.get(currentNode).size();j++){
							int nextNode=adjList2.get(currentNode).get(j);
							if(!explored[nextNode]){
								allexplored=false;
								explored[nextNode]=true;
								stack.push(nextNode);
							}
						}
					}else{
						if(numLoop==2){
							for(int j=0;j<adjList.get(currentNode).size();j++){
								int nextNode=adjList.get(currentNode).get(j);
								if(!explored[nextNode]){
									allexplored=false;
									explored[nextNode]=true;
									stack.push(nextNode);
								}
							}
						}
					}
					
					if(allexplored){
						if(numLoop==1){
							t++;
							index2[t-1]=currentNode;
						}else{
							if(numLoop==2){
								count++;
							}
						}
						stack.pop();
					}
					
					
				}
				if(numLoop==2){
					if(scc.size()==0){
						scc.add(count);
					}else{
						boolean ifAdd=false;
						for(int index=0;index<scc.size();index++){
							if(count<scc.get(index)){
								scc.add(index, count);
								ifAdd=true;
								break;
							}
						}
						
						if(!ifAdd){
							scc.add(count);
						}
						
						if(scc.size()>=6){
							scc.remove(0);
						}
					}

				}

			}
		}
		
		
		
		
	}
	
	
	public LinkedList<Integer> getscc(){
		return scc;
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		Week1_2 test=new Week1_2("./data/SCC.txt",875714);
//		Week1_2 test=new Week1_2("./data/test.txt",9);
		long endTime = System.currentTimeMillis();
        System.out.println("time= " + (endTime - startTime) + "ms");
        
		test.DFS_Loop(1);

		System.out.println("Phase 1 finished");
        endTime = System.currentTimeMillis();
        System.out.println("time= " + (endTime - startTime) + "ms");
        
		test.DFS_Loop(2);
		LinkedList<Integer> scc=test.getscc();
		System.out.println(scc.toString());
        endTime = System.currentTimeMillis();
        System.out.println("time= " + (endTime - startTime) + "ms");
		
	}
}
