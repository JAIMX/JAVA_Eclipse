

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Stopwatch;

public class DynamicProgrammingTSPbyMA {
    static final int maxInt = 10000;
    static int nbCities;
    static int dist[][]; // index of cities start from 0
    static boolean[] chosen;
    static ArrayList<TreeSet<Integer>> subsets;
    static TreeSet<Integer> tempSet;
    static Map<String, Integer> strToIdx = new HashMap<String, Integer>();

    public static void generateSubset(int nbCities, int size, int level, int startFrom) {
	if (level >= size) {
	    // System.out.println(tempSet);
	    subsets.add(new TreeSet<Integer>(tempSet));
	    // System.out.println(subsets);
	    String tempStr = tempSet.toString();
	    strToIdx.put(tempStr, subsets.size() - 1);
	    return;
	} else {
	    for (int i = startFrom; i < nbCities; i++) {
		if (!chosen[i]) {
		    chosen[i] = true;
		    tempSet.add(i);
		    generateSubset(nbCities, size, level + 1, i + 1);
		    chosen[i] = false;
		    tempSet.remove(i);
		}
	    }
	}
    }

    public static void print2DArray(int[][] g) {
	for (int[] temp : g)
	    System.out.println(Arrays.toString(temp));
    }

    public static void main(String[] args) throws IOException {
	for (String arg : args) {
	    Scanner in = new Scanner(Paths.get(arg));
	    nbCities = in.nextInt();
	    dist = new int[nbCities][nbCities];

	    for (int i = 0; i < nbCities; i++) {
		for (int j = 0; j < nbCities; j++) {
		    dist[i][j] = in.nextInt();
		    if (i == j) dist[i][j] = 0;
		}
	    }
	    in.close();

	    Stopwatch timer = new Stopwatch();
	    // for (int[] tmp : dist)
	    // System.out.println(Arrays.toString(tmp));

	    subsets = new ArrayList<TreeSet<Integer>>(); // ((int)Math.pow(nbCities, 2));
	    subsets.add(new TreeSet<Integer>());
	    strToIdx.put((new TreeSet<Integer>()).toString(), 0);

	    for (int j = 1; j <= nbCities - 1; j++) {
		chosen = new boolean[nbCities];
		tempSet = new TreeSet<Integer>();
		generateSubset(nbCities, j, 0, 1);
	    }
	    // System.out.println(subsets);
	    // System.out.println(strToIdx);

	    int[][] g = new int[nbCities][subsets.size()];
	    for (int i = 0; i < nbCities; i++)
		for (int j = 0; j < subsets.size(); j++)
		    g[i][j] = maxInt;

	    for (int i = 1; i < nbCities; i++)
		g[i][0] = dist[i][0];
	    // print2DArray(g);
	    // System.out.println(" ---- empty subset done. ----");

	    int col = 1;
	    for (int i = 1; i < nbCities; i++) {
		for (int j = 1;; j++) {
		    if (subsets.get(j).size() > 1) {
			col = j;
			break;
		    }
		    TreeSet<Integer> currentSet = subsets.get(j);
		    // System.out.print(currentSet.toString() + "\n");
		    if (!currentSet.contains(i)) {
			for (Integer tmpInt : currentSet) {
			    g[i][j] = dist[i][tmpInt] + g[strToIdx.get(currentSet.toString())][0];
			}
		    }
		}
	    }
	    // print2DArray(g);
	    // System.out.println(" ---- subset.size == 1 done. ---- ");
	    int endCol = 0;

	    for (int setSize = 2; setSize <= nbCities - 2; setSize++) {
		for (int i = 1; i < nbCities; i++) {
		    for (int j = col;; j++) {
			if (subsets.get(j).size() > setSize) {
			    endCol = j;
			    break;
			}
			TreeSet<Integer> currentSet = new TreeSet<Integer>(subsets.get(j));
			// System.out.print("\n" + currentSet.toString() + "\t");
			if (!currentSet.contains(i)) {
			    for (Integer tmpInt : subsets.get(j)) {
				int l = dist[i][tmpInt];
				// System.out.print("L(" + i + "," + tmpInt + ") = " + l + "\t");
				currentSet.remove(tmpInt);
				// System.out.printf("g(%d, %s) = ", tmpInt, currentSet.toString());
				int gVal = g[tmpInt][strToIdx.get(currentSet.toString())];
				// System.out.println(gVal);
				g[i][j] = Math.min(g[i][j], l + gVal);
				currentSet.add(tmpInt);
			    }
			}
		    }
		}
	    }

	    // calculate last cell of the DP table g[0][col]
	    col = endCol;
	    
	    // col must point at the last row of the DP table
	    assert col == subsets.size() - 1 : "wrong col value: " + col;
	    
	    TreeSet<Integer> currentSet = new TreeSet<Integer>(subsets.get(col));
	    // System.out.print("\n" + currentSet.toString() + "\t");
	    assert !currentSet.contains(0) : "why the last SUBSET contains 0?";
	    
	    for (Integer tmpInt : subsets.get(col)) {
		int l = dist[0][tmpInt];
		// System.out.print("L(" + i + "," + tmpInt + ") = " + l + "\t");
		currentSet.remove(tmpInt);
		// System.out.printf("g(%d, %s) = ", tmpInt, currentSet.toString());
		int gVal = g[tmpInt][strToIdx.get(currentSet.toString())];
		// System.out.println(gVal);
		g[0][col] = Math.min(g[0][col], l + gVal);
		currentSet.add(tmpInt);
	    }

	    // print2DArray(g);
	    // print OPT
	    int opt = g[0][col];
	    System.out.printf("%s: %d\n", arg, opt);

	    // extract tour[] out from the DP table
	    int count = 0, pos = 1, i = 0;
	    int tour[] = new int[nbCities + 1]; // first 0, last 0, so length + 1
	    for (int j = col; j > 0;) {
		currentSet = new TreeSet<Integer>(subsets.get(j));
		for (Integer tmpInt : subsets.get(j)) {
		    int l = dist[i][tmpInt];
		    // System.out.print("L(" + i + "," + tmpInt + ") = " + l + "\t");
		    currentSet.remove(tmpInt);
		    // System.out.printf("g(%d, %s) = ", tmpInt, currentSet.toString());
		    int gVal = g[tmpInt][strToIdx.get(currentSet.toString())];
		    // System.out.println(gVal);
		    if (opt == l + gVal) {
			// System.out.printf(" - %d \n", tmpInt);
			tour[pos++] = tmpInt;
			opt -= l;
			i = tmpInt;
			j = strToIdx.get(currentSet.toString());
			// System.out.println(j + "\t" + subsets.get(j) + "\t" + opt);
			count++;
			break;
		    }
		    currentSet.add(tmpInt);
		}
	    }
	    assert count == nbCities - 1 : "tour length error" + count;

	    System.out.println(Arrays.toString(tour));
	    
	    double time = timer.elapsedTime();
	    System.out.printf("time = %.2f sec\n\n", time);
	}
    }
}
