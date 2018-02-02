import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;

public class GA_TSP {

    private int n;
    private double[][] distance;
    private int size;
    private LinkedList<int[]> population;
    final double goodpercent=0.8;
    final double badpercent=0.4;
    final int stopNum=50;
    final double mutationLevel=0.1;
    final double repeatLimitPercent=0.1;
    final int repeatLimit;

    public GA_TSP(double[][] distance,int size) {
        this.distance = distance;
        n = distance.length;
        this.size=size;
        population=new LinkedList<int[]>();
        repeatLimit=(int) (size*repeatLimitPercent);
    }

    // randomly generate one route
    public int[] initialize() {
        int[] route = new int[n + 2];
        boolean[] chosen=new boolean[n];
       

        route[0] = 0;
        route[n] = 0;
        chosen[0]=true;
        int count = 1;
        Random random = new Random();

        while (count < n ) {
            ArrayList<Integer> set = new ArrayList<Integer>();
            
            for(int i=1;i<n;i++){
                if(!chosen[i]&&distance[route[count-1]][i]<Double.MAX_VALUE){
                    set.add(i);
                }
            }
            if(set.size()==0||(set.size()>0&&count==n-1&&distance[set.get(0)][0]>=Double.MAX_VALUE)){
                route[0] = 0;
                route[n] = 0;
                chosen=new boolean[n];
                chosen[0]=true;
                count = 1;
            }else{
                int nextcity=random.nextInt(set.size());
                route[count]=set.get(nextcity);
                chosen[route[count]]=true;
                count++;
            }
            

        }

        route[n + 1] = totalCost(route);
        return route;

    }

    // calculate one route's total cost
    public int totalCost(int[] route) {
        int cost = 0;
        for (int i = 0; i < n; i++) {
            cost += distance[route[i]][route[i + 1]];
        }
        return cost;
    }

    // procedure for generating a child
    public int[] born(int[] parent1, int[] parent2) {
        int[] child = new int[n + 2];
        child[0] = 0;
        child[n] = 0;
        boolean[] chosen = new boolean[n]; // default false
        chosen[0] = true;
        int current = 0; // index of child

        while (current < n) {
            if (current == n - 1 && distance[child[n - 1]][0] < Double.MAX_VALUE) {
                current++;
            } else {
                ArrayList<Integer> pair = new ArrayList<Integer>();

                // find all the pairs
                for (int i = 0; i < n; i++) {
                    if (parent1[i] == child[current] || parent1[i + 1] == child[current]) {
                        if (parent1[i] == child[current] && !chosen[parent1[i + 1]]
                                && distance[current][parent1[i + 1]] < Double.MAX_VALUE) {
                            pair.add(parent1[i + 1]);
                        } else if (!chosen[parent1[i]] && distance[current][parent1[i]] < Double.MAX_VALUE)
                            pair.add(parent1[i]);
                    }

                    if (parent2[i] == child[current] || parent2[i + 1] == child[current]) {
                        if (parent2[i] == child[current] && !chosen[parent2[i + 1]]
                                && distance[current][parent2[i + 1]] < Double.MAX_VALUE) {
                            pair.add(parent2[i + 1]);
                        } else if (!chosen[parent2[i]] && distance[current][parent2[i]] < Double.MAX_VALUE)
                            pair.add(parent2[i]);
                    }
                }

                // consider sub-tour reversal that the child's tour is making in
                // a portion of a parent's tour
                // parent1
                if (current != 0) {
                    int childnode = current;
                    int record = 0;
                    for (int i = 0; i < n; i++) {
                        if (parent1[i] == child[current]) {
                            record = i;
                            break;
                        }
                    }

                    // detect the sub-tour
                    if (record < n && parent1[record + 1] == child[childnode - 1]) {
                        boolean exit = false;

                        while (!exit) {

                            if (parent1[record] == child[childnode]) {
                                if (childnode > 0)
                                    childnode--;
                                else
                                    exit = true;
                                if (record + 1 > 0 && record + 1 < n)
                                    record += 1;
                                else
                                    exit = true;
                            } else {
                                exit = true;
                                if (!chosen[parent1[record]]
                                        && distance[parent1[record]][child[current]] < Double.MAX_VALUE) {
                                    pair.add(parent1[record]);

                                }
                            }
                        } // end while
                    }

                }

                // parent2
                if (current != 0) {
                    int childnode = current;
                    int record = 0;
                    for (int i = 0; i < n; i++) {
                        if (parent2[i] == child[current]) {
                            record = i;
                            break;
                        }
                    }

                    // detect the sub-tour
                    if (record < n && parent2[record + 1] == child[childnode - 1]) {
                        boolean exit = false;

                        while (!exit) {

                            if (parent2[record] == child[childnode]) {
                                if (childnode > 0)
                                    childnode--;
                                else
                                    exit = true;
                                if (record + 1 > 0 && record + 1 < n)
                                    record += 1;
                                else
                                    exit = true;
                            } else {
                                exit = true;
                                if (!chosen[parent2[record]]
                                        && distance[parent2[record]][child[current]] < Double.MAX_VALUE) {
                                    pair.add(parent2[record]);

                                }
                            }
                        } // end while
                    }

                } // end sub-tour process
                  // find all the pairs


                // step3: selection of next link
                // step4: check for a mutation
                double mutation = Math.random();
                boolean ifhasmutation = true;
                if (mutation < mutationLevel) {
                    // search a connected city with city[current] which hasn't
                    // been chosen
                    ArrayList<Integer> mutationCity = new ArrayList<Integer>();
                    for (int i = 1; i < n; i++) {
                        if (distance[i][child[current]] < Double.MAX_VALUE && !pair.contains(i) && !chosen[i]) {
                            mutationCity.add(i);
                        }
                    }

                    if (mutationCity.size() > 0) {
                        Random random = new Random();
                        child[current + 1] = mutationCity.get(random.nextInt(mutationCity.size()));
                        current++;
                        chosen[child[current]] = true;
                    } else { // no mutation
                        ifhasmutation = false;
                    }

                }

                if (mutation >= mutationLevel || !ifhasmutation) {
                    if (pair.size() == 0) { // restart the child search
                        child[0] = 0;
                        child[n] = 0;
                        chosen = new boolean[n]; // default false
                        chosen[0] = true;
                        current = 0; // index of child

                    } else {
                        Random random = new Random();
                        child[current + 1] = pair.get(random.nextInt(pair.size()));
                        current++;
                        chosen[child[current]] = true;

                    }
                }

                // System.out.println(Arrays.toString(child));

            }
        }

        child[n + 1] = totalCost(child);
        return child;

    }
    
    //randomly select m elements from 0 to t-1
    public int[] randomSelect(int t,int m){
        
//        System.out.println("t= "+t);
//        System.out.println("m= "+m);
        ArrayList<Integer> set=new ArrayList<Integer>();
        int[] select=new int[m];
        for(int i=0;i<t;i++){
            set.add(i);
        }
        Random random=new Random();
        for(int i=0;i<m;i++){
            int temp=random.nextInt(set.size());
            select[i]=set.get(temp);
            set.remove(temp);
        }
        return select;
    }
    
    //add one route to population
    public void addroute(int[] route){
        
       // int index=-1;
        int node=0;
        int repeat=0;
        
        while(route[n+1]>=population.get(node)[n+1]){
            if(route[n+1]==population.get(node)[n+1]){
                repeat++;
            }
            node++;
            if(node==population.size())break;
            
        }
        
        if(repeat<repeatLimit){
            if(node==population.size()){
                population.add(route);
            }else{
                population.add(node,route);
            }
        }
        
        
        
//        for(int j=0;j<population.size();j++){
//            if(population.get(j)[n+1]>=route[n+1]){
//                if(population.get(j)[n+1]==route[n+1]) {
//                    index=-2;
//                    break;
//                }else{
//                    index=j;
//                    break;
//                }
//
//            }
//        }
//        if(index==-1){
//            population.add(route);
//        }else if(index>=0) population.add(index, route);
        
    }
    
    //add initial routes to population
    public void addroute0(int[] route){
        
        int index=-1;
        
        for(int j=0;j<population.size();j++){
            if(population.get(j)[n+1]>=route[n+1]){
                    index=j;
                    break;
            }
        }
        if(index==-1){
            population.add(route);
        }else population.add(index, route);
        
    }
    
    //optimal process
    public int[] geneticProcess(){
        
        
        // initialize routes in population
        for(int i=0;i<size;i++){
            int[] tempnode=initialize();
            addroute0(tempnode);
            }
        

        int goodnum=(int) ((size/2)*goodpercent);
        if (goodnum % 2!=0) goodnum=goodnum-1;
        
        int badnum=(int) ((size-(size/2))*badpercent);
        if (badnum % 2!=0) badnum=badnum-1;   
        LinkedList<int[]> parent;
        
        int stopCount=0;
        double bestcost=population.get(0)[n+1];
        int[] bestroute=population.get(0);
        while(stopCount<stopNum){
            
            parent=new LinkedList<int[]>();
            int[] parentIndex1=randomSelect(size/2,goodnum);
            int[] parentIndex2=randomSelect(size-size/2,badnum);
            
            for(int i=0;i<parentIndex1.length;i++){
                parent.add(population.get(parentIndex1[i]));
            }
            
            for(int i=0;i<parentIndex2.length;i++){
                parent.add(population.get(parentIndex2[i]+size/2));
            }
            
            int[] bornindex=randomSelect(parent.size(), parent.size());
            for(int i=0;i<=bornindex.length-2;i=i+2){
                addroute(born(parent.get(i),parent.get(i+1)));
            }
            
            while(population.size()>size){
                population.removeLast();
            }
            
            if(population.get(0)[n+1]<bestcost){
                bestcost=population.get(0)[n+1];
                bestroute=population.get(0);
                stopCount=0;

                System.out.println("A new best route has been found, which is "+Arrays.toString(bestroute));
                System.out.println();

            }else stopCount++;
            
        }
        
//        System.out.println("Now the first 100 routes are ");
//        for(int i=0;i<size;i++) System.out.println(i+":"+Arrays.toString(population.get(i)));

        return bestroute;
    }

    public static void main(String[] args) throws IOException {
        Stopwatch timer = new Stopwatch();
        //ReadData data=new ReadData("att48.tsp", 48,"ATT");
        //ReadData data=new ReadData("st70.tsp", 70,"EUC");
        ReadData data=new ReadData("tsp29.txt", 29,"NORMAL");
        //ReadData data=new ReadData("bier127.tsp",127,"EUC");
        // double[][] distance={{0,3,4,7},{3,0,5,2},{4,5,0,1},{7,2,1,0}};
        GA_TSP test=new GA_TSP(data.read(),500);
        
        System.out.println(Arrays.toString(test.geneticProcess()));
        System.out.println("goodpercent= "+test.goodpercent);
        System.out.println("badpercent= "+test.badpercent);
        System.out.println("mutationLevel= "+test.mutationLevel);
        System.out.println("population size= "+test.size);
        System.out.println("repeatLimit= "+test.repeatLimit);


        


//        double[][] distance = { { 0, 12, 10, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, 12 },
//                { 12, 0, 8, 12, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE }, 
//                 { 10, 8, 0, 11, 3, Double.MAX_VALUE, 9 },
//                { Double.MAX_VALUE, 12, 11, 0, 11, 10, Double.MAX_VALUE },
//                { Double.MAX_VALUE, Double.MAX_VALUE, 3, 11, 0, 6, 7 },
//                { Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, 10, 6, 0, 9 },
//                { 12, Double.MAX_VALUE, 9, Double.MAX_VALUE, 7, 9, 0 } };
//        GA_TSP test=new GA_TSP(distance,10);
//        System.out.println(Arrays.toString(test.geneticProcess()));
        
        int[] parent1 = { 0, 1, 2, 3, 4, 5, 6, 0, 0 };
        int[] parent2 = { 0, 1, 3, 5, 4, 6, 2, 0, 0 };

        //GA_TSP test = new GA_TSP(distance,10);
//        for(int i=0;i<50;i++){
//            System.out.println(Arrays.toString(test.geneticProcess()));
//        }
        
//        int[] route={1,8,38,31,44,18,7,28,6,37,19,27,17,43,
//                30,36,46,33,20,47,21,32,39,48,5,42,24,10,45,35,4,26,2,29,34,41
//                ,16,22,3,23,14,25,13,11,12,15,40,9,1,0};
//        for(int i=0;i<route.length;i++){
//            route[i]-=1;
//        }
//
//        System.out.println(test.totalCost(route));
        
        
        double time = timer.elapsedTime();
        System.out.println("time = " + time);

    }
}
