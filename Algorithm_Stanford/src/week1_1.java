import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

// multiple two numbers
public class week1_1 {

    public LinkedList<Integer> multiple(LinkedList<Integer> n1, LinkedList<Integer> n2) {

        check(n1);
        check(n2);

        int size1 = n1.size();
        int size2 = n2.size();
        LinkedList<Integer> sum = new LinkedList<Integer>();

        if (size1 == 1 && size2 == 1) {
            int temp = n1.get(0) * n2.get(0);
            if (temp < 10) {
                sum.add(temp);
            } else {
                sum.add(temp % 10);
                sum.add(temp / 10);
            }
            // System.out.println("n1="+n1.toString());
            // System.out.println("n2="+n2.toString());
            // System.out.println("sum="+sum.toString());
            return sum;
        }

        LinkedList<Integer> a = new LinkedList<Integer>();
        LinkedList<Integer> b = new LinkedList<Integer>();
        LinkedList<Integer> c = new LinkedList<Integer>();
        LinkedList<Integer> d = new LinkedList<Integer>();

        int index1 = size1 / 2;
        int index2 = size2 / 2;

        for (int i = 0; i < size1; i++) {
            if (i < index1) {
                b.add(n1.get(i));
            } else
                a.add(n1.get(i));
        }

        for (int i = 0; i < size2; i++) {
            if (i < index2) {
                d.add(n2.get(i));
            } else
                c.add(n2.get(i));
        }
        // System.out.println(a.toString());
        // System.out.println(b.toString());
        // System.out.println(c.toString());
        // System.out.println(d.toString());
        // multiple10(a, 3);

        // size1=1, size2!=1
        if (size1 == 1) {
            sum = multiple10(multiple(n1, c), index2);
            sum = add(sum, multiple(n1, d));

            // System.out.println("n1="+n1.toString());
            // System.out.println("n2="+n2.toString());
            // System.out.println("sum="+sum.toString());

            return sum;
        } else {
            if (size2 == 1) {
                sum = multiple10(multiple(n2, a), index1);
                sum = add(sum, multiple(n2, b));

                // System.out.println("n1="+n1.toString());
                // System.out.println("n2="+n2.toString());
                // System.out.println("sum="+sum.toString());

                return sum;
            }
        }

        sum = multiple10(multiple(a, c), index1 + index2);
//        if (size1 == 3 && size2 == 2) {
//            System.out.println("n1 is " + n1.toString());
//            System.out.println("n2 is " + n2.toString());
//            System.out.println(sum.toString());
//        }

        sum = add(sum, multiple10(multiple(a, d), index1));
//        if (size1 == 3 && size2 == 2)
//            System.out.println(sum.toString());
        sum = add(sum, multiple10(multiple(b, c), index2));
//        if (size1 == 3 && size2 == 2)
//            System.out.println(sum.toString());
        sum = add(sum, multiple(b, d));
//        if (size1 == 3 && size2 == 2) {
//            // System.out.println("b is"+b.toString());
//            // System.out.println("d is"+d.toString());
//            System.out.println(sum.toString());
//            System.out.println();
//        }

        // System.out.println("n1="+n1.toString());
        // System.out.println("n2="+n2.toString());
        // System.out.println("sum="+sum.toString());

        return sum;

    }

    public void check(LinkedList<Integer> x) {

        // delete 0 at the highest bit
        int index = x.size() - 1;
        while (x.get(index) == 0 && x.size() > 1) {
            x.remove(index);
            index--;
        }

        // add 1 if >=10
        for (int i = 0; i < x.size(); i++) {

            if (x.get(i) >= 10) {
                if (i <= x.size() - 2) {
                    x.set(i, x.get(i) % 10);
                    x.set(i + 1, x.get(i + 1) + 1);
                } else {
                    x.set(i, x.get(i) % 10);
                    x.add(1);
                }
            }
        }

    }

    public LinkedList<Integer> add(LinkedList<Integer> n1, LinkedList<Integer> n2) {
        LinkedList<Integer> x1, x2;
        if (n1.size() >= n2.size()) {
            x1 = n1;
            x2 = n2;
        } else {
            x1 = n2;
            x2 = n1;
        }

        for (int i = 0; i < x2.size(); i++) {
            int temp = x1.get(i) + x2.get(i);
            if (temp < 10) {
                x1.set(i, temp);
            } else {
                x1.set(i, temp % 10);
                if (i + 1 > x1.size() - 1) {
                    x1.add(1);
                } else {
                    x1.set(i + 1, x1.get(i + 1) + 1);

                }
            }
        }

        check(x1);
        return x1;
    }

    public LinkedList<Integer> multiple10(LinkedList<Integer> ele, int pow) {
        for (int i = 0; i < pow; i++) {
            ele.add(0, 0);
        }

        // System.out.println(ele.toString());

        return ele;
    }

    public static void main(String[] args) {
        // Scanner in=new Scanner(System.in);
        // String x1=in.nextLine();
        // String x2=in.nextLine();

        String x1 = "3141592653589793238462643383279502884197169399375105820974944592";
        String x2 = "2718281828459045235360287471352662497757247093699959574966967627";
//         String x1 = "20974944592";
//         String x2 = "966967627";
//        String x1 = "445";
//        String x2 = "27";
        // String x1 = "11980";
        // String x2 = "35";
        // String x1 = "92";
        // String x2 = "27";
        LinkedList<Integer> n1 = new LinkedList<Integer>();
        LinkedList<Integer> n2 = new LinkedList<Integer>();

        for (int i = x1.length() - 1; i >= 0; i--) {
            n1.add((int) (x1.charAt(i)) - 48);
        }

        for (int i = x2.length() - 1; i >= 0; i--) {
            n2.add((int) (x2.charAt(i)) - 48);
        }

        // System.out.println(n1.toString());
        // System.out.println(n2.toString());

        week1_1 test = new week1_1();

        LinkedList<Integer> result, temp;
         result = new LinkedList<Integer>();
         temp = test.multiple(n1, n2);
         String result1 = "";
         for (int i = temp.size() - 1; i >= 0; i--) {
         result.add(temp.get(i));
         result1 += temp.get(i);
         }
        
         System.out.println(result.toString());
         System.out.println(result1.toString());
        
         System.out.println(result1.length());

//        temp = new LinkedList<Integer>();
//        temp.add(5);
//        temp.add(1);
//        temp.add(10);
//        System.out.println(temp.toString());
//        test.check(temp);
//        System.out.println(temp.toString());

        // test.add(n1, n2);

    }
}
