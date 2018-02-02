

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.Stopwatch;

public class DPtsp2 {

    private double g[][];
    private int record[][];
    private int numcity;
    private double distance[][]; // index of cities start from 0
    private double minLength;
    private int pathindex[];
    private final int max_num_condition;
    
    boolean[] chosen;

    public DPtsp2(double distances[][]) {
        numcity = distances.length;
        assert(numcity>=3): "number of cities is less than 3!";
        max_num_condition = (int) Math.pow(2, numcity);
        distance=new double[numcity][numcity];

        for(int i=0;i<numcity;i++){
            for(int j=0;j<numcity;j++){
                distance[i][j]=distances[i][j];
            }
        }
        
        g = new double[numcity][max_num_condition];
        record = new int[numcity][max_num_condition];
        
        
        //initialize g[][] and record[][]
        for(int i=1;i<numcity;i++){
            g[i][0]=distance[i][0];
            record[i][0]=0;
        }
        
        //update g[][] and record[][]
        chosen=new boolean[numcity];
        for(int numofset=1;numofset<numcity-1;numofset++){    //number of elements in set
            
            for(int startpoint=1;startpoint<numcity;startpoint++){    //the startpoint of g
                
                for(int i=0;i<numcity;i++){
                    chosen[i]=false;
                }
                searchallsets(0,numofset,startpoint,0);
                
            }
        }
        
        //calculate g[0][max_num_condition-2]
        g[0][max_num_condition-2]=Double.MAX_VALUE;
        for(int i=1;i<numcity;i++){
            if(g[0][max_num_condition-2]>distance[0][i]+g[i][(int) (max_num_condition-2-Math.pow(2, i))]){
                record[0][max_num_condition-2]=i;
                g[0][max_num_condition-2]=distance[0][i]+g[i][(int) (max_num_condition-2-Math.pow(2, i))];
            }
        }
        
        minLength=g[0][max_num_condition-2];
        pathindex=new int[numcity];
        pathindex[0]=0;
        int tempindex2=max_num_condition-2;
        int tempstartpoint=0;
        for(int i=1;i<numcity;i++){
            pathindex[i]=record[tempstartpoint][tempindex2];
            tempstartpoint=pathindex[i];
            tempindex2=(int) (tempindex2-Math.pow(2, pathindex[i]));
        }
        
        
    }

    public void searchallsets(int chosencity, int goalcity, int startpoint, int lastpoint){
        if(chosencity==goalcity){       // Calculate g[][] of current set
            int tempindex=0;            //setindex
            for(int i=numcity-1;i>=0;i--){
                tempindex=tempindex*2+(chosen[i]==true?1:0);
            }
            
            g[startpoint][tempindex]=Double.MAX_VALUE;
            for(int i=1;i<numcity;i++){
                if(chosen[i]){
                    if(g[startpoint][tempindex]>distance[startpoint][i]+g[i][(int) (tempindex-Math.pow(2, i))]){
                        record[startpoint][tempindex]=i;
                        g[startpoint][tempindex]=distance[startpoint][i]+g[i][(int) (tempindex-Math.pow(2, i))];
                    }
                }
            }
            
            return;
        }
        
        for(int i=lastpoint+1;i<numcity;i++){                 
            if(i!=startpoint&&!chosen[i]){
                chosen[i]=true;
                searchallsets(chosencity+1,goalcity,startpoint,i);
                chosen[i]=false;
            }
        }
    }
        
        
    public static void main(String[] args) throws IOException {
//        double[][] dic={{100,1,4},{1,100,2},{4,2,100}};

//        Scanner in= new Scanner(Paths.get("tsp17.txt"));
//        int n=in.nextInt();
//        double[][]dist=new double[n][n];
//
//        for(int i=0;i<n;i++){
//            for(int j=0;j<n;j++){
//                dist[i][j]=in.nextDouble();
//            }
//        }
//        
//        in.close();
        
		Scanner in = new Scanner(Paths.get("tsp.txt"));
		// Scanner in = new Scanner(Paths.get("./data/test.txt"));
		int n = in.nextInt();
		double[] x = new double[n];
		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			x[i] = in.nextDouble();
			y[i] = in.nextDouble();
		}

		double[][] distance = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				distance[i][j] = Math.sqrt(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
			}
		}
        
        Stopwatch timer = new Stopwatch();
        
//        DPtsp2 test=new DPtsp2(dist);
        DPtsp2 test=new DPtsp2(distance);
        System.out.println(test.minLength);
        System.out.println(Arrays.toString(test.pathindex));
        double time = timer.elapsedTime();
        System.out.println("time = " + time);
        
    }
    
    
    
}
