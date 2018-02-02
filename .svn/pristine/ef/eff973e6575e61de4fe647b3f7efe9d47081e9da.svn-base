import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import edu.princeton.cs.algs4.Stopwatch;

public class Knapsack {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(Paths.get("data/ks_1000_0.txt"));
        int n = in.nextInt();
        int k = in.nextInt();
        int[] bag = new int[k + 1];
        boolean[][] track = new boolean[k + 1][n];
        int[] values = new int[n];
        int[] spaces = new int[n];
        
        Stopwatch timer = new Stopwatch();

        for (int i = 0; i < n; i++) {
            int value = in.nextInt();
            values[i] = value;
            int weight = in.nextInt();
            spaces[i] = weight;

            for (int space = k; space >= weight; space--) {
                if (bag[space] < bag[space - weight] + value) {
                    bag[space] = bag[space - weight] + value;
                    track[space][i] = true;
                }
            }
        }
        System.out.println(bag[k]);


        int space = k;
        int item = n - 1;
        while (space > 0 && item >= 0) {
            while (item >= 0 && track[space][item] == false) {
                item--;
            }
            if (item >= 0) {
                System.out.println("taken item #" + (item + 1) + " with value " + values[item]);
                space = space - spaces[item];
                item--;
            }

        }
      
        double time = timer.elapsedTime();
        System.out.println("time                    = " + time);

    }

}
