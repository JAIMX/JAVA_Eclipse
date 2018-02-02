import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> sub = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            sub.enqueue(StdIn.readString());
        }

        Iterator<String> i = sub.iterator();
        while (i.hasNext() && k > 0) {
            String s = i.next();
            StdOut.println(s);
            k--;
        }
    }
}
