import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.Stopwatch;

public class balancedMTSP {
    static int m=2;
    static int n ;
    static int[][] dist ;
    static long distance = Long.MAX_VALUE;
    static boolean[] select ;
    static ArrayList<Integer> path;
    static int[]temp;


    public static void dfs(long s, int last, int start) {
        boolean k = false;

        for (int i = 0; i < n+m-1; i++) {
            if (!select[i]) {
                select[i] = true;
                k = true;
                path.add(i);
                dfs(s + dist[last][i], i, start);
                path.remove(path.size()-1);
                select[i] = false;
            }
        }

        if (!k) {
            if (distance > s + dist[start][last]) {
                long subdistance=0;
                int i=1;
                while(path.get(i)!=n){                //only process m=2
                    subdistance+=dist[path.get(i-1)][path.get(i-1)];
                    i++;
                }
                subdistance+=dist[i-1][i];
                if(Math.abs(s+dist[start][last]-subdistance*2)/Math.max(s+dist[start][last]-subdistance,subdistance )<0.1){
                    distance = s + dist[start][last];
                    for(int j=0;j<path.size();j++) temp[j]=path.get(j);
                }

            }
            return;
        }
    }

    public static void main(String[] args) throws IOException  {
       
        Scanner in= new Scanner(Paths.get("tsp11.txt"));
        n=in.nextInt();
        select=new boolean[n+m-1];
        dist=new int[n+m-1][n+m-1];
        path=new ArrayList<Integer>();
        temp=new int[n+m-1];
        

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                dist[i][j]=in.nextInt();
            }
        }
        

        
        
        
        in.close();
        Stopwatch timer = new Stopwatch();
  
        for (int k = 0; k < n; k++) {
            
            for(int i=n;i<n+m-1;i++){
                for(int j=1;j<n;j++){
                    dist[i][j]=dist[i][k];
                    dist[j][i]=dist[i][j];
                }
            }
            
            for(int i=n;i<n+m-1;i++){
                for(int j=n;j<n+m-1;j++){
                    dist[i][j]=Integer.MAX_VALUE;
                }
                dist[i][k]=Integer.MAX_VALUE;
                dist[k][i]=Integer.MAX_VALUE;
            }
            
            
            select[k] = true;
            path.add(k);
            dfs(0, k, k);
            path.remove(path.size()-1);
            select[k] = false;
        }
        
   

        
        double time = timer.elapsedTime();
        System.out.println("time = " + time);
        System.out.println(distance);
        for(int i=0;i<n+m-1;i++){System.out.print(temp[i]+" ");}

    

    }

}
