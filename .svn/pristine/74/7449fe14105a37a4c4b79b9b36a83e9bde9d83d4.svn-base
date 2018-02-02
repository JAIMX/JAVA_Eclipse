import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> pointset;

    // construct an empty set of points
    public PointSET() {

        pointset = new TreeSet<Point2D>();

    }

    // is the set empty?
    public boolean isEmpty() {

        return pointset.size() == 0;
    }

    public int size() {
        return pointset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null)
            throw new java.lang.NullPointerException();
        pointset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        return pointset.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

        for (Point2D i : pointset) {
            i.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException();

        TreeSet<Point2D> insidepoints = new TreeSet<Point2D>();
        for (Point2D i : pointset) {
            if (rect.contains(i))
                insidepoints.add(i);
        }
        return insidepoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();

        if (pointset.isEmpty())
            return null;
        Point2D record = null;
        double distance = Integer.MAX_VALUE;

        for (Point2D i : pointset) {
            if (i.distanceTo(p) < distance) {
                distance = i.distanceTo(p);
                record = i;
            }
        }

        return record;

    }

}
