import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class Week2_2 {
    
    private ArrayList<ArrayList<edge>> adjList;
//    private TreeMap<Integer,Integer> heap;
    private PriorityQueue<nodeDistance> queue;
    private nodeDistance[] recordNode;
//    private int[] minDistance;
    private int n;
    private boolean[] x;


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
    
    public Week2_2(int n) {
        this.n=n;
//        minDistance=new int[n];
//        minDistance[0]=0;
        recordNode=new nodeDistance[n];
        x=new boolean[n];
        queue= new PriorityQueue<nodeDistance>();
        adjList=new ArrayList<ArrayList<edge>>();
    }
    
    public void readData(String filename) throws IOException{
        

        Scanner in=new Scanner(Paths.get(filename));
        
        for(int i=0;i<n;i++){
            String temp=in.nextLine();
            String[] list=temp.split("\\s+");
//            System.out.println(list[list.length-1]);
            ArrayList<edge> adj=new ArrayList<edge>();
            
            for(int j=1;j<list.length;j++){
                edge tempEdge=new edge();
                int index=list[j].indexOf(",", 0);
                tempEdge.pointTo=Integer.parseInt(list[j].substring(0, index))-1;
                tempEdge.distance=Integer.parseInt(list[j].substring(index+1, list[j].length()));
                adj.add(tempEdge);
            }
            
            adjList.add(adj);
        }
    }
    
    
    
    public void dijkstraSP(){
        //initialize
        for(int i=0;i<n;i++){
            nodeDistance dis=new nodeDistance();
            dis.node=i;
            dis.distance=1000000;
            recordNode[i]=dis;
            queue.add(dis);
        }      
        recordNode[0].distance=0;
        
        
        //start the algorithm
        while(!queue.isEmpty()){
            
            nodeDistance nodedistance=queue.poll();
            int node=nodedistance.node;
            x[node]=true;
            
            ArrayList<edge> adjNode=adjList.get(node);
            
            for(int i=0;i<adjNode.size();i++){
                int pointToNode=adjNode.get(i).pointTo;
                if(!x[pointToNode]){
                    queue.remove(recordNode[pointToNode]);
                    recordNode[pointToNode].distance=Math.min(recordNode[pointToNode].distance, nodedistance.distance+adjNode.get(i).distance);
                    queue.add(recordNode[pointToNode]);
                }
            }
            
            
        }
    }
    
    public void OutPut(){
        int[] set={7,37,59,82,99,115,133,165,188,197};
        HashSet<Integer> requireSet=new HashSet<Integer>();
        for(int i=0;i<set.length;i++){
            requireSet.add(set[i]-1);
        }
        for(int i=0;i<n;i++){
            if(requireSet.contains(i)){
//                System.out.println("Node "+i+"'s distance= "+recordNode[i].distance);
                System.out.print(recordNode[i].distance+",");
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        Week2_2 test=new Week2_2(200);
        test.readData("./data/dijkstraData.txt");
//        test.readData("./data/test.txt");
        test.dijkstraSP();

        test.OutPut();
    }
}
