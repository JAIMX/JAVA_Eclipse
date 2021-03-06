import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

//randomized contraction algorithm
public class Week4_1 {

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();

        // read data to adjList
        Scanner in = new Scanner(Paths.get("./data/kargerMinCut.txt"));
//        Scanner in = new Scanner(Paths.get("./data/test.txt"));

        while (in.hasNextLine()) {
            String line = in.nextLine();
            ArrayList<Integer> temp = new ArrayList<Integer>();
            String[] list = line.split("\\s+");

            for (int i = 1; i < list.length; i++) {
                temp.add(Integer.parseInt(list[i]) - 1);
            }
            adjList.add(temp);
        }

        // connected
        int n = adjList.size();
        boolean[][] connected = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> temp = adjList.get(i);
            for (int j = 0; j < temp.size(); j++) {
                connected[i][temp.get(j)] = true;
            }
        }

        // start the algorithm
        long N = (long) ((Math.log1p(n - 1) + 1) * Math.pow(n, 2));
//        System.out.println("N="+N);

        long startTime = System.currentTimeMillis();
        
        long minCut = Long.MAX_VALUE;
        for (int i = 0; i < N; i++) {

//            System.out.println("i= "+i);
            // copy
            ArrayList<ArrayList<Integer>> adjList2 = new ArrayList<ArrayList<Integer>>();
            for (int a = 0; a < adjList.size(); a++) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                for (int b = 0; b < adjList.get(a).size(); b++) {
                    temp.add(adjList.get(a).get(b));
//                    
//                    if(temp.get(temp.size()-1)==a){
//                        System.out.println("Wrong!!!");
//                    }
                }
                adjList2.add(temp);
//                System.out.println(adjList2.get(adjList2.size()-1).toString());
            }
            

            // if Empty
            boolean[] ifEmpty = new boolean[n];
            for (int j = 0; j < n; j++) {
                if (adjList.get(j).size() == 0) {
                    ifEmpty[j] = true;
                }
            }
            

            // operate to adjList2
            for (int j = n; j > 2; j--) {
                
//                System.out.println("j= "+j);
                
                int vertice1 = (int)(Math.random() * n);
                while (ifEmpty[vertice1] ) {
                    vertice1 = (int)(Math.random() * n);
                }
                
//                System.out.println("vertice1= "+vertice1);
//                System.out.println(adjList2.get(vertice1).toString());
                
                int index = (int)(Math.random() * adjList2.get(vertice1).size());
                int vertice2=adjList2.get(vertice1).get(index);
                
                
//                System.out.println("vertice1= " + vertice1);
//                System.out.println("vertice2= " + vertice2);
//                System.out.println(adjList2.get(vertice2).toString());
//                System.out.println();
                
//                while (ifEmpty[vertice1] ) {
//                    index = (int)(Math.random() * adjList2.get(vertice1).size());
//                    vertice2=adjList2.get(vertice1).get(index);
//                }
                
                //delete edges between v1 and v2
                ArrayList<Integer> temp =adjList2.get(vertice1);
                int index2=0;
                while(index2<temp.size()){
                    if(temp.get(index2)==vertice2){
                        temp.remove(index2);
                    }else index2++;
                }
                
                temp =adjList2.get(vertice2);
                index2=0;
                while(index2<temp.size()){
                    if(temp.get(index2)==vertice1){
                        temp.remove(index2);
                    }else index2++;
                }

                
                //change the adj edges of v2 to v1
                for(int a=0;a<temp.size();a++){
                    int adjVertice=temp.get(a);
                    
                    ArrayList<Integer> temp2 =adjList2.get(adjVertice);
                    for(int b=0;b<temp2.size();b++){
                        if(temp2.get(b)==vertice2){
                            temp2.set(b, vertice1);
                            adjList2.get(vertice1).add(adjVertice);
                        }
                    }
                }
                temp.clear();
                
                if(adjList2.get(vertice2).size()==0){
                    ifEmpty[vertice2]=true;
                }
                
                if(adjList2.get(vertice1).size()==0){
                    ifEmpty[vertice1]=true;
                }
                
            }
            
            
            for(int a=0;a<n;a++){
                
                if(adjList2.get(a).size()>0){
                    System.out.println(i+" minCut="+adjList2.get(a).size());
                    if(minCut>adjList2.get(a).size()){
                        minCut=adjList2.get(a).size();
                    }
                    break;
                }
            }
        }
        
        System.out.println("minCut= "+minCut);
        long endTime = System.currentTimeMillis();
        System.out.println("time= " + (endTime - startTime) + "ms");
        

    }

}
