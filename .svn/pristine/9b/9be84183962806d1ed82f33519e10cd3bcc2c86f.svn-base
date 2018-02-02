import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Week3_2 {

    public static void main(String[] args) throws IOException {

        Comparator<Integer> inverseSort;
        inverseSort = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return o2 - o1;
            }
        };

        PriorityQueue<Integer> qh = new PriorityQueue<Integer>();
        PriorityQueue<Integer> ql = new PriorityQueue<Integer>(1, inverseSort);

        int result = 0;
         Scanner in=new Scanner(Paths.get("./data/Median.txt"));
//        Scanner in = new Scanner(Paths.get("./data/test.txt"));

        for (int i = 0; i < 10000; i++) {
            int currentNum = in.nextInt();
            if (qh.size() == 0 && ql.size() == 0) {
                qh.add(currentNum);
            } else {
                int cut = qh.peek();
                if (currentNum <= cut) {
                    ql.add(currentNum);
                } else
                    qh.add(currentNum);
            }

            if (qh.size() - ql.size() >= 2) {
                int temp = qh.poll();
                ql.add(temp);
            } else {
                if (ql.size() - qh.size() >= 2) {
                    int temp = ql.poll();
                    qh.add(temp);
                }
            }

            // pick median
            int median = 0;
            if (ql.size() == qh.size()) {
                median = ql.peek();
            } else {
                if (ql.size() > qh.size()) {
                    median = ql.peek();
                } else
                    median = qh.peek();
            }
//            System.out.println(median);

            result += median;
            result = result % 10000;
        }
        System.out.println("result="+result);

    }
}
