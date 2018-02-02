import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Week1_4 {
	private int n,numEdge;
	private int[][] distance;
	private long[][] dpFunction1,dpFunction2;
	private long min;
	

	public Week1_4(String filename) throws IOException{
		// read data
		Scanner in=new Scanner(Paths.get(filename));
		n=in.nextInt();
		numEdge=in.nextInt();
		distance=new int[n][n];
		dpFunction1=new long[n][n];
		dpFunction2=new long[n][n];
		
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(i!=j){
					distance[i][j]=Integer.MAX_VALUE;
					dpFunction1[i][j]=distance[i][j];
				}
			}
		}
		
		for(int i=0;i<numEdge;i++){
			int s=in.nextInt()-1;
			int t=in.nextInt()-1;
			distance[s][t]=in.nextInt();
			dpFunction1[s][t]=distance[s][t];
		}
		
	}
	
	public void opt(){
		for(int k=0;k<n;k++){
//			System.out.println("k= "+k);
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					dpFunction2[i][j]=Math.min(dpFunction1[i][j], dpFunction1[i][k]+dpFunction1[k][j]);
				}
			}
			
			System.arraycopy(dpFunction2, 0, dpFunction1, 0, n);
		}
		
		boolean hasNegativeCycle=false;
		for(int i=0;i<n;i++){
			if(dpFunction1[i][i]<0){
				hasNegativeCycle=true;
				System.out.println("There is negative cycle in the graph!");
				break;
			}
		}
		
		
		if(!hasNegativeCycle){
			min=Long.MAX_VALUE;
			
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(i!=j&&min>dpFunction1[i][j]){
						min=dpFunction1[i][j];
					}
				}
			}
			
			System.out.println("the shortest shortest path'length = "+min);
			
			
		}
	}
	
	public static void main(String[] args) throws IOException {
//		Week1_4 test=new Week1_4("./data/g1.txt");
		Week1_4 test=new Week1_4("./data/g3.txt");
		test.opt();
//		Week1_4 test=new Week1_4("./data/test.txt");
	}
}
