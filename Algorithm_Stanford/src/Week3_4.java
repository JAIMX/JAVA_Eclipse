import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Week3_4 {
	
	private int n;
	private boolean[] chosen;
	private double minDistance;
	private int[] record;
	
	private double[] x;
	private double[] y;
	
	
	public void readData(String filename) throws IOException {
		Scanner in=new Scanner(Paths.get(filename));
		n=in.nextInt();
		x=new double[n];
		y=new double[n];
		int temp;
		
		for(int i=0;i<n;i++) {
			temp=in.nextInt();
			x[i]=in.nextDouble();
			y[i]=in.nextDouble();
		}
		
//		System.out.println(Arrays.toString(x));
//		System.out.println(Arrays.toString(y));
	}
	
	public void opt() {
		chosen=new boolean[n];
		chosen[0]=true;
		int currentNode=0;
		minDistance=0;
		record=new int[n];
		record[0]=0;
		
		for(int i=1;i<=n-1;i++) {
			System.out.println("i = "+i);
			double currentMin=Double.MAX_VALUE;
			int nextNode=-1;;
			
			for(int j=1;j<n;j++) {
				if(!chosen[j]) {
					double distance=Math.pow(x[currentNode]-x[j], 2)+Math.pow(y[currentNode]-y[j], 2);
					
//					System.out.println(currentNode+" -> "+j+"'s distance is "+distance);
//					System.out.println("currentMin= "+currentMin);
//					System.out.println();
					
					if(currentMin>distance) {
						currentMin=distance;
						nextNode=j;
					}
				}
			}
			
			minDistance+=Math.sqrt(currentMin);
			chosen[nextNode]=true;
			record[i]=nextNode;
			currentNode=nextNode;
//			System.out.println(Arrays.toString(record));
		}
		
		minDistance+=Math.sqrt(Math.pow(x[currentNode]-x[0], 2)+Math.pow(y[currentNode]-y[0], 2));
		
		System.out.println("Greedy mininal distance is "+ minDistance);
//		System.out.println(Arrays.toString(record));
		
		
		
		
		
	}

	public static void main(String[] args) throws IOException {
		Week3_4 test=new Week3_4();
//		test.readData("./data/test.txt");
		test.readData("./data/nn.txt");
		test.opt();
	}
}
