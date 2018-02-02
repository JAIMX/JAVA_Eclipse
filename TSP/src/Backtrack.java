import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import edu.princeton.cs.algs4.Stopwatch;

public class Backtrack {
	static int n ;
	static double[][] dist ;
	static double distance =Double.MAX_VALUE;
	static boolean[] select ;

	public static void dfs(Double s, int last, int start) {
		boolean k = false;

		for (int i = 0; i < n; i++) {
			if (!select[i]) {
				select[i] = true;
				k = true;
				dfs(s + dist[last][i], i, start);
				select[i] = false;
			}
		}

		if (!k) {
			if (distance > s + dist[start][last]) {
				distance = s + dist[start][last];
			}
			return;
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner in= new Scanner(Paths.get("tsp12.txt"));
		n=in.nextInt();
		select=new boolean[n];
		dist=new double[n][n];

		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				dist[i][j]=in.nextDouble();
			}
		}
        
		in.close();
		Stopwatch timer = new Stopwatch();
  
		for (int i = 0; i < n; i++) {
			select[i] = true;
			dfs(0.0, i, i);
			select[i] = false;
		}

		
		double time = timer.elapsedTime();
		System.out.println("time = " + time);
		System.out.println(distance);

	

	}
}
