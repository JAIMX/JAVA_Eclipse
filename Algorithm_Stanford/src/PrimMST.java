import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrimMST{
    
    private ArrayList<ArrayList<edge>> adjList;
    private PriorityQueue<nodeDistance> queue;
    private nodeDistance[] recordNode;
    private int n;
    private boolean[] x;
    private long minDistance;


    private class edge{
        private int pointTo;
        private int distance;
    }
    
    private class nodeDistance implements Comparable<nodeDistance>{
        int node;
        int distance;
        
        public int compareTo(nodeDistance that){
            if(this.distance<that.distance)return -1;
            if(this.distance>that.distance)return 1;
            return 0;
        }
    }
    
    public PrimMST(String filename) throws IOException {
    	Scanner in=new Scanner(Paths.get(filename));
        this.n=in.nextInt();
        recordNode=new nodeDistance[n];
        x=new boolean[n];
        queue= new PriorityQueue<nodeDistance>();
        adjList=new ArrayList<ArrayList<edge>>();
        
        for(int i=0;i<n;i++) {
        	ArrayList<edge> list=new ArrayList<edge>();
        	adjList.add(list);
        }
        
        
        //read data
        int numEdge=in.nextInt();
        edge tempEdge;
        
        for(int i=0;i<numEdge;i++) {
        	int node1=in.nextInt()-1;
        	int node2=in.nextInt()-1;
        	int distance=in.nextInt();
        	
        	tempEdge=new edge();
        	tempEdge.pointTo=node2;
        	tempEdge.distance=distance;
        	adjList.get(node1).add(tempEdge);
        	
        	tempEdge=new edge();
        	tempEdge.pointTo=node1;
        	tempEdge.distance=distance;
        	adjList.get(node2).add(tempEdge);
        }
    }
    
    public void prim(){
        //initialize
        for(int i=0;i<n;i++){
            nodeDistance dis=new nodeDistance();
            dis.node=i;
            dis.distance=1000000;
            recordNode[i]=dis;
            queue.add(dis);
        }      
        recordNode[0].distance=0;
        minDistance=0;
        
        
        //start the algorithm
        while(!queue.isEmpty()){
            
            nodeDistance nodedistance=queue.poll();
            minDistance+=nodedistance.distance;
            		
            int node=nodedistance.node;
            x[node]=true;
            
            ArrayList<edge> adjNode=adjList.get(node);
            
            for(int i=0;i<adjNode.size();i++){
                int pointToNode=adjNode.get(i).pointTo;
                if(!x[pointToNode]){
                    queue.remove(recordNode[pointToNode]);
                    recordNode[pointToNode].distance=Math.min(recordNode[pointToNode].distance, adjNode.get(i).distance);
                    queue.add(recordNode[pointToNode]);
                }
            }
            
            
        }
        
        System.out.println("MST is "+minDistance);
    }
    
    
    public static void main(String[] args) throws IOException {
    	PrimMST test=new PrimMST("./data/edges.txt");
//    	PrimMST test=new PrimMST("./data/test.txt");
        test.prim();
    }
}
