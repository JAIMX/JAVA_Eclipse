import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Knapsack {

	private int items;
	private long capacity;
	private int[] values;
	private int[] weights;
	private long value;
	private boolean[] taken;
	private boolean[] takenRecord;
	private int[] sortedIndex;
	private double[] sortedDensity;
	private List<density> templist;
	
	
	public void readData(String filename) throws IOException {
		Scanner in=new Scanner(Paths.get(filename));
		capacity=in.nextLong();
		items=in.nextInt();
		values=new int[items];
		weights=new int[items];
		
		for(int i=0;i<items;i++) {
			values[i]=in.nextInt();
			weights[i]=in.nextInt();
		}
		
		
	}
	
	public void opt() {
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
        System.out.println(value);

        // prepare the solution in the specified output format
//        System.out.println(value + " 1");
//
//        boolean[] realtaken = new boolean[items];
//        for (int i = 0; i < items; i++) {
//            if (takenRecord[i]) {
//                realtaken[sortedIndex[i]] = true;
//            }
//        }
//        
//        //System.out.println(realtaken[9756]);
//
//        
//        for (int i = 0; i < items; i++) {
//
//            if (realtaken[i]) {
//                System.out.print(1 + " ");
//            } else {
//                System.out.print(0 + " ");
//            }
//
//        }
//        System.out.println("");

	}

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
	
	
	public static void main(String[] args) throws IOException {
		Knapsack test=new Knapsack();
		test.readData("./data/knapsack1.txt");
//		test.readData("./data/knapsack_big.txt");
		test.opt();
	}
	
	
	
}
