import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private int n, count;
    private LineSegment[] linesegment;
    private List<LineSegment> record;

    public BruteCollinearPoints(Point[] points) { // finds all line segments
                                                  // containing 4 points
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

        record = new ArrayList<>();

        count = 0;

        Point[] compare = new Point[4];

        for (int a = 0; a < n; a++) {
            for (int b = a + 1; b < n; b++) {
                for (int c = b + 1; c < n; c++) {
                    for (int d = c + 1; d < n; d++) {
                        if (points[a].compareTo(points[b]) == 0
                                || points[a].compareTo(points[c]) == 0
                                || points[a].compareTo(points[d]) == 0
                                || points[b].compareTo(points[c]) == 0
                                || points[b].compareTo(points[d]) == 0
                                || points[c].compareTo(points[d]) == 0)
                            throw new java.lang.IllegalArgumentException();
                        if (points[a].slopeTo(points[b]) == points[a]
                                .slopeTo(points[c])
                                && points[a].slopeTo(points[b]) == points[a]
                                        .slopeTo(points[d])) {
                            count++;
                            compare[0] = points[a];
                            compare[1] = points[b];
                            compare[2] = points[c];
                            compare[3] = points[d];
                            Arrays.sort(compare);

                            LineSegment temp = new LineSegment(compare[0],
                                    compare[3]);
                            record.add(temp);

                        }

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

    public static void main(String[] args) {
        Point[] point = new Point[6];
        point[0] = new Point(0, 0);
        point[1] = new Point(1, 1);
        point[2] = new Point(2, 2);
        point[3] = new Point(3, 3);
        point[4] = new Point(4, 4);
        point[5] = new Point(7, 6);

        FastCollinearPoints test = new FastCollinearPoints(point);
        System.out.println(test.numberOfSegments());
        System.out.println(test.segments()[0]);

    }

}
