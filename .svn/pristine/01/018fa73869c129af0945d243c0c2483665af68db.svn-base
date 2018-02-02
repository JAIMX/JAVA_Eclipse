import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;

public class ReverseHybrid {

    private int n;
    private double[][] distance;
    final int size = 20;
    final double p = 1;
    final int exitLimit = 50000;
    private ArrayList<int[]> population;

    public ReverseHybrid(double[][] distance) {
        this.distance = distance;
        n = distance.length;
        population = new ArrayList<int[]>();
    }

    // randomly generate one route
    public int[] initialize() {
        int[] route = new int[n + 2];
        boolean[] chosen = new boolean[n];

        route[0] = 0;
        route[n] = 0;
        chosen[0] = true;
        int count = 1;
        Random random = new Random();

        while (count < n) {
            ArrayList<Integer> set = new ArrayList<Integer>();

            for (int i = 1; i < n; i++) {
                if (!chosen[i] && distance[route[count - 1]][i] < Double.MAX_VALUE) {
                    set.add(i);
                }
            }
            if (set.size() == 0 || (set.size() > 0 && count == n - 1 && distance[set.get(0)][0] >= Double.MAX_VALUE)) {
                route[0] = 0;
                route[n] = 0;
                chosen = new boolean[n];
                chosen[0] = true;
                count = 1;
            } else {
                int nextcity = random.nextInt(set.size());
                route[count] = set.get(nextcity);
                chosen[route[count]] = true;
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

    public int[] geneticProcess() {

        int[] bestRoute = new int[n + 2];
        double bestCost = Double.MAX_VALUE;

        // produce initial population randomly
        for (int i = 0; i < size; i++) {
            int[] temp = initialize();
            if (bestCost > temp[n + 1]) {
                bestRoute = temp;
                bestCost = temp[n + 1];
            }
            population.add(temp);
        }

        // for(int i=0;i<size;i++){
        // System.out.println(Arrays.toString(population.get(i)));
        // }

        int exitCount = 0;
        int[] newRoute;
        Random random = new Random();
        while (exitCount < exitLimit) {

            boolean improve = false;

            // for every unit in the population
            for (int i = 0; i < size; i++) {

                // randomly select one adjacent pair to improve
                int[] tempRoute = Arrays.copyOf(population.get(i), n + 2);
                int mostDecreasingIndex = 0;
                double valueRecord = Double.MAX_VALUE;
                for (int j = 1; j < n - 1; j++) {
                    double difference = distance[tempRoute[j - 1]][tempRoute[j + 1]]
                            + distance[tempRoute[j]][tempRoute[j + 2]] - distance[tempRoute[j - 1]][tempRoute[j]]
                            - distance[tempRoute[j + 1]][tempRoute[j + 2]];
                    // distance[j][j+2]-distance[j-1][j]-distance[j+1][j+2];
                    if (difference < valueRecord) {
                        valueRecord = difference;
                        mostDecreasingIndex = j;
                    }
                }
                if (mostDecreasingIndex != 0) {
                    int temp = tempRoute[mostDecreasingIndex];
                    tempRoute[mostDecreasingIndex]=tempRoute[mostDecreasingIndex+1];
                    tempRoute[mostDecreasingIndex+1]=temp;
                }
                population.set(i, tempRoute);
                
                
                
                
                newRoute = Arrays.copyOf(population.get(i), n + 2);
                int index0 = random.nextInt(n - 1) + 1; // between 1 and n-1
                int index1 = -1;

                do {

                    double rand = random.nextDouble();
                    if (rand <= p) { // choose another city from newRoute
                        index1 = random.nextInt(n - 1) + 1;
                        while (index1 == index0) {
                            index1 = random.nextInt(n - 1) + 1;
                        }
                    } else {
                        int[] randomRoute = Arrays.copyOf(population.get(random.nextInt(size)), n + 2);
                        ;
                        int cityRecord = -1;
                        for (int j = 0; j < n + 1; j++) {
                            if (randomRoute[j] == newRoute[index0]) {
                                cityRecord = j;
                                break;
                            }
                        }

                        int anotherCity = -1;
                        if (cityRecord == n - 1) {
                            anotherCity = randomRoute[cityRecord - 1];
                        } else {
                            anotherCity = randomRoute[cityRecord + 1];
                        }

                        for (int j = 0; j < n + 1; j++) {
                            if (newRoute[j] == anotherCity) {
                                index1 = j;
                                break;
                            }
                        }
                    }
                    
                    
                    
                    if (Math.abs(index0 - index1) == 1) {
                        break;
                    }

                    int start = Math.min(index0, index1);
                    int end = Math.max(index0, index1);
                    // reverse between start and end in newRoute
                    int temp = end - start + 1;
                    int[] part = new int[temp];
                    for (int j = 0; j < temp; j++) {
                        part[j] = newRoute[j + start];
                    }

                    for (int j = end; j >= start; j--) {
                        newRoute[j] = part[end - j];
                    }
                    
                    
                } while (exitCount < exitLimit);

                newRoute[n + 1] = totalCost(newRoute);
                if (newRoute[n + 1] < population.get(i)[n + 1]) {
                    population.set(i, newRoute);
                    if (bestCost > newRoute[n + 1]) {
                        bestRoute = Arrays.copyOf(newRoute, n + 2);
                        bestCost = bestRoute[n + 1];
                        improve = true;

                        System.out.println("A new best route is found, which is " + Arrays.toString(bestRoute));
                        System.out.println();
                    }
                }
            }

            if (!improve) {
                exitCount++;
            } else
                exitCount = 0;
        }

        return bestRoute;
    }

    public static void main(String[] args) throws IOException {
        Stopwatch timer = new Stopwatch();

        // double[][] distance = { { 0, 12, 10, Double.MAX_VALUE,
        // Double.MAX_VALUE, Double.MAX_VALUE, 12 },
        // { 12, 0, 8, 12, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE
        // },
        // { 10, 8, 0, 11, 3, Double.MAX_VALUE, 9 },
        // { Double.MAX_VALUE, 12, 11, 0, 11, 10, Double.MAX_VALUE },
        // { Double.MAX_VALUE, Double.MAX_VALUE, 3, 11, 0, 6, 7 },
        // { Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, 10, 6, 0, 9
        // },
        // { 12, Double.MAX_VALUE, 9, Double.MAX_VALUE, 7, 9, 0 } };

        // ReadData data=new ReadData("tsp29.txt", 29,"NORMAL");
        //ReadData data = new ReadData("att48.tsp", 48, "ATT");
        //ReadData data=new ReadData("st70.tsp", 70,"EUC");
        ReadData data=new ReadData("bier127.tsp",127,"EUC");
        ReverseHybrid test = new ReverseHybrid(data.read());

        //for(int i=0;i<10;i++){
        //   System.out.println(Arrays.toString(test.geneticProcess()));
        //   double time = timer.elapsedTime();
        //   System.out.println("time = " + time);
        //   timer = new Stopwatch();
        //}

        System.out.println(Arrays.toString(test.geneticProcess()));
        System.out.println("size= "+test.size);
        System.out.println("p= "+test.p);
        System.out.println("exitLimit= "+test.exitLimit);
        double time = timer.elapsedTime();
        System.out.println("time = " + time);

    }
}
