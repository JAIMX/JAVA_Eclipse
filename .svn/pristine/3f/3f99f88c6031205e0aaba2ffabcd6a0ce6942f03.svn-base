import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private int n, count;
    private LineSegment[] linesegment;
    private List<LineSegment> record;

    private class Node {
        private double slope;
        private Point inter;
        private Node next;
    }

    public FastCollinearPoints(Point[] points) {
        n = points.length;
        if (n == 0)
            throw new java.lang.NullPointerException();

        if (n < 4 && n > 1) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (points[i].compareTo(points[j]) == 0)
                        throw new java.lang.IllegalArgumentException();
                }
            }
        }

        count = 0;
        record = new ArrayList<>();
        Node start = null;

        Point[] temp = new Point[n];
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++)
                temp[j] = points[j];

            Arrays.sort(temp, points[i].slopeOrder());

            double current = points[i].slopeTo(temp[0]);
            int amount = 1;
            for (int j = 1; j <= n; j++) {
                if (j < n && points[i].slopeTo(temp[j]) == current) {
                    amount++;
                } else {
                    if (current == Double.NEGATIVE_INFINITY && amount > 1)
                        throw new java.lang.IllegalArgumentException();
                    if (amount > 2) {

                        boolean found = false;
                        if (start != null) {
                            Node temp4 = start;

                            while (temp4 != null) {
                                if (temp4.slope == current && temp4.inter.slopeTo(points[i]) == current) {
                                    found = true;
                                    break;
                                }
                                temp4 = temp4.next;
                            }
                        }

                        if (!found) {
                            count++;
                            Point[] temp2 = new Point[amount + 1];
                            for (int k = 0; k < amount; k++)
                                temp2[k] = temp[j - amount + k];
                            temp2[amount] = points[i];

                            Arrays.sort(temp2);

                            LineSegment temp3 = new LineSegment(temp2[0], temp2[amount]);

                            if (start == null) {
                                Node temp4 = new Node();
                                temp4.slope = current;
                                temp4.inter = points[i];
                                temp4.next = null;
                                start = temp4;
                                record.add(temp3);
                            } else {
                                Node temp4 = new Node();
                                temp4.slope = current;
                                temp4.inter = points[i];
                                temp4.next = start;
                                start = temp4;
                                record.add(temp3);
                            }

                        }

                    }

                    if (j < n) {
                        current = points[i].slopeTo(temp[j]);
                        amount = 1;
                    }
                }
            }
        }

    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {

        linesegment = new LineSegment[count];

        for (int i = 0; i < count; i++) {
            linesegment[i] = record.get(i);
        }
        return linesegment;
    }

    public static void main(String[] args) throws IOException {
       /*
        // TODO Auto-generated method stub
        Point[] point = new Point[8];
        point[0] = new Point(10000, 0);
        point[1] = new Point(0, 10000);
        point[2] = new Point(3000, 7000);
        point[3] = new Point(7000, 3000);
        point[4] = new Point(20000, 21000);
        point[5] = new Point(3000, 4000);
        point[6] = new Point(14000, 15000);
        point[7] = new Point(6000, 7000);

        FastCollinearPoints test = new FastCollinearPoints(point);
        System.out.println(test.count);
        for (int i = 0; i < test.count; i++) {
            System.out.println(test.segments()[i]);
        }
       */
        // read the n points from a file
        //In in = new In("collinear-testing/input8.txt");
        Scanner in= new Scanner(Paths.get("collinear-testing/input10000.txt"));
        int n = in.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.count);
    
    }

}
