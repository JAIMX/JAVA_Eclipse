
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javax.swing.plaf.synth.SynthSeparatorUI;

import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to
 * solve the knapsack problem.
 *
 */
public class Solver {
    private int items;
    private long capacity;
    private long[] values;
    private long[] weights;
    private long value;
    private boolean[] taken;
    private boolean[] takenRecord;
    private int[] sortedIndex;
    private double[] sortedDensity;
    private List<density> templist;

    private class density implements Comparable<density> {
        double denValue;
        int index;

        public density(double denValue, int index) {
            this.denValue = denValue;
            this.index = index;
        }

        public int compareTo(density other) {
            double temp = denValue - other.denValue;
            if (temp < 0)
                return 1;
            if (temp > 0)
                return -1;
            return 0;
        }
    }

    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            Solver test = new Solver();
            test.solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the instance, solve it, and print the solution in the standard
     * output
     */
    public void solve(String[] args) throws IOException {
        String fileName = null;

        // get the temp file name
        for (String arg : args) {
            if (arg.startsWith("-file=")) {
                fileName = arg.substring(6);
            }
        }
        if (fileName == null)
            return;

        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input = new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while ((line = input.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            input.close();
        }

        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        items = Integer.parseInt(firstLine[0]);
        capacity = Integer.parseInt(firstLine[1]);

        values = new long[items];
        weights = new long[items];

        for (int i = 1; i < items + 1; i++) {
            String line = lines.get(i);
            String[] parts = line.split("\\s+");

            values[i - 1] = Integer.parseInt(parts[0]);
            weights[i - 1] = Integer.parseInt(parts[1]);
        }

        /***
         * // a trivial greedy algorithm for filling the knapsack // it takes
         * items in-order until the knapsack is full int value = 0; int weight =
         * 0; int[] taken = new int[items];
         * 
         * for(int i=0; i < items; i++){ if(weight + weights[i] <= capacity){
         * taken[i] = 1; value += values[i]; weight += weights[i]; } else {
         * taken[i] = 0; } }
         * 
         * // prepare the solution in the specified output format
         * System.out.println(value+" 0"); for(int i=0; i < items; i++){
         * System.out.print(taken[i]+" "); } System.out.println("");
         ***/

        // DFS branch and bound based on the new greedy sort of items, using
        // maximal possible value to bound
        // know items,capacity,values[items],weights[items]

        templist = new ArrayList<density>();

        for (int i = 0; i < items; i++) {
            double temp = (double) values[i] / (double) weights[i];
            density tempDensity = new density(temp, i);
            templist.add(tempDensity);
        }

        Collections.sort(templist);

        sortedIndex = new int[items];
        sortedDensity = new double[items];

        for (int i = 0; i < items; i++) {
            sortedDensity[i] = templist.get(i).denValue;
            sortedIndex[i] = templist.get(i).index;
        }

        // System.out.println(Arrays.toString(sortedDensity));
        // System.out.println(Arrays.toString(sortedIndex));

        value = Integer.MIN_VALUE;
        taken = new boolean[items];
        takenRecord = new boolean[items];

        DFS(0, 0, sortedDensity[0] * capacity, 0);

        // prepare the solution in the specified output format
        System.out.println(value + " 1");

        boolean[] realtaken = new boolean[items];
        for (int i = 0; i < items; i++) {
            if (takenRecord[i]) {
                realtaken[sortedIndex[i]] = true;
            }
        }
        
        //System.out.println(realtaken[9756]);

        
        for (int i = 0; i < items; i++) {

            if (realtaken[i]) {
                System.out.print(1 + " ");
            } else {
                System.out.print(0 + " ");
            }

        }
        System.out.println("");


    }

    public void DFS(long currentValue, long space, double possibleValue, int index) {
        if (index == items) {
            if (currentValue > value) {
                value = currentValue;
                takenRecord = Arrays.copyOf(taken, (int) items);
            }
        } else {

            // check whether can add item[sortedIndex[index]]into bag
            if (space + weights[sortedIndex[index]] <= capacity && possibleValue > value) {
                taken[index] = true;
                double update = Double.MAX_VALUE;
                if (index < items - 1) {
                    update = currentValue + values[sortedIndex[index]]
                            + sortedDensity[index + 1] * (capacity - space - weights[sortedIndex[index]]);
                }
                DFS(currentValue + values[sortedIndex[index]], space + weights[sortedIndex[index]], update, index + 1);
                taken[index] = false;
            }

            // check whether can not add item[index]into bag(bound)
            double update2 = Double.MAX_VALUE;
            if (index < items - 1) {
                update2 = currentValue + sortedDensity[index + 1] * (capacity - space);
            }
            if (update2 > value) {
                DFS(currentValue, space, update2, index + 1);
            }
        }
    }
}
