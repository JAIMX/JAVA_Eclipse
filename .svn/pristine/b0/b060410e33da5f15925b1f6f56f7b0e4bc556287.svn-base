import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.Stopwatch;

public class Test {
    ArrayList<ArrayList<Integer>> record;

    public boolean track(int space, int item) {
        if (record.get(item).size() == 0)
            return false;
        for (int i = 1; i < record.get(item).size() - 1; i++) {
            if (space <= record.get(item).get(i) && space > record.get(item).get(i + 1)) {
                if (record.get(item).get(0) == 0) {
                    if (i % 2 == 0)
                        return true;
                    else
                        return false;
                } else {
                    if (i % 2 == 0)
                        return false;
                    else
                        return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Scanner in = new Scanner(Paths.get("data/ks_10000_0.txt"));
        int n = in.nextInt();
        int k = in.nextInt();
        int[] bag = new int[k + 1];
        int[] values = new int[n];
        int[] spaces = new int[n];
        int count = 0;
        long count2 = 0;
        Test test = new Test();
        test.record = new ArrayList<ArrayList<Integer>>();

        Stopwatch timer = new Stopwatch();

        for (int i = 0; i < n; i++) {
            int value = in.nextInt();
            values[i] = value;
            int weight = in.nextInt();
            spaces[i] = weight;

            ArrayList<Integer> temp = new ArrayList<>();
            int initial = 0;
            if (k >= weight) {
                if (bag[k] < bag[k - weight] + value) {
                    bag[k] = bag[k - weight] + value;
                    initial = 1;
                }
                temp.add(initial);
                temp.add(k);
                count = count + 2;
            }

            for (int space = k - 1; space >= weight; space--) {
                if (bag[space] < bag[space - weight] + value) {
                    bag[space] = bag[space - weight] + value;
                    if (initial == 0) {
                        initial = 1;
                        temp.add(space);
                        count++;
                    }
                } else {
                    if (initial == 1) {
                        initial = 0;
                        temp.add(space);
                        count++;
                    }
                }
            }

            if (temp.size() > 0) {
                if (temp.get(temp.size() - 1) != weight && initial == 1) {
                    temp.add(weight - 1);
                    count++;
                } else {
                    if (temp.get(temp.size() - 1) == weight && initial == 1) {
                        temp.add(weight - 1);
                        count++;
                    }
                }
            }

            test.record.add(temp);
        }

        int space = k;
        int item = n - 1;
        while (space > 0 && item >= 0) {
            while (item >= 0 && test.track(space, item) == false) {
                item--;
            }
            if (item >= 0) {
                System.out.println("taken item #" + (item + 1) + " with value " + values[item]);
                count2 += values[item];
                space = space - spaces[item];
                item--;
            }

        }

        double time = timer.elapsedTime();
        System.out.println("time                    = " + time);
    }

}
