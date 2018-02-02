import java.util.Stack;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final boolean USEX = true;
    private static final boolean USEY = false;
    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);

    private int size;
    private Kdnode root;
    private Stack<Point2D> record;
    private double mindis;
    private Point2D nearrecord;

    private class Kdnode {
        private Point2D point;
        private Kdnode leftchild, rightchild;
        private boolean usexory;

        public Kdnode(Point2D point, Kdnode leftchild, Kdnode rightchild, boolean usexory) {
            this.point = point;
            this.leftchild = leftchild;
            this.rightchild = rightchild;
            this.usexory = usexory;
        }

    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p))
            return;

        root = insert(root, p, USEX);
        size++;
    }

    private Kdnode insert(Kdnode node, Point2D point, boolean usexory) {
        if (node == null)
            return new Kdnode(point, null, null, usexory);

        if (usexory == USEX) {
            int cmp = Point2D.X_ORDER.compare(point, node.point);
            if (cmp < 0)
                node.leftchild = insert(node.leftchild, point, USEY);
            else if (cmp >= 0)
                node.rightchild = insert(node.rightchild, point, USEY);
        }

        if (usexory == USEY) {
            int cmp = Point2D.Y_ORDER.compare(point, node.point);
            if (cmp < 0)
                node.leftchild = insert(node.leftchild, point, USEX);
            else if (cmp >= 0)
                node.rightchild = insert(node.rightchild, point, USEX);
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return contains(root, p);
    }

    private boolean contains(Kdnode node, Point2D point) {
        if (node == null)
            return false;
        if (node.point.x() == point.x() && node.point.y() == point.y())
            return true;
        else {
            if (node.usexory == USEX && point.x() < node.point.x()
                    || (node.usexory == USEY && point.y() < node.point.y())) {
                return (contains(node.leftchild, point));
            } else
                return (contains(node.rightchild, point));
        }
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        CONTAINER.draw();

        draw(root, CONTAINER);
    }

    private void draw(Kdnode node, RectHV rect) {
        if (node == null) {
            return;
        }

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();

        // get the min and max points of division line
        Point2D min, max;
        if (node.usexory == USEX) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.point.x(), rect.ymin());
            max = new Point2D(node.point.x(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.point.y());
            max = new Point2D(rect.xmax(), node.point.y());
        }

        // draw that division line
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children
        draw(node.leftchild, leftRect(node, rect));
        draw(node.rightchild, rightRect(node, rect));
    }

    private RectHV leftRect(Kdnode node, RectHV rect) {
        if (node.usexory == USEX) {
            return new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
        }
    }

    private RectHV rightRect(Kdnode node, RectHV rect) {
        if (node.usexory == USEX) {
            return new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        record = new Stack<Point2D>();
        if (rect.intersects(CONTAINER))
            rangesearch(root, CONTAINER, rect);
        return record;
    }

    private void rangesearch(Kdnode node, RectHV noderect, RectHV rect) {
        if (node == null)
            return;
        if (rect.contains(node.point))
            record.add(node.point);

        RectHV leftrect = leftRect(node, noderect);
        RectHV rightrect = rightRect(node, noderect);
        if (rect.intersects(leftrect))
            rangesearch(node.leftchild, leftrect, rect);
        if (rect.intersects(rightrect))
            rangesearch(node.rightchild, rightrect, rect);

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        mindis = Double.MAX_VALUE;
        nearrecord = null;
        nearsearch(root, CONTAINER, p.x(), p.y());
        return nearrecord;
    }

    private void nearsearch(Kdnode node, RectHV noderect, double x, double y) {
        if (node == null)
            return;
        Point2D point = new Point2D(x, y);
        if (node.point.distanceSquaredTo(point) < mindis) {
            mindis = node.point.distanceSquaredTo(point);
            nearrecord = node.point;
        }

        RectHV leftrect = leftRect(node, noderect);
        RectHV rightrect = rightRect(node, noderect);
        if (leftrect.distanceSquaredTo(point) < mindis && rightrect.distanceSquaredTo(point) < mindis) {
            if (node.usexory == USEX) {
                if (x <= node.point.x()) {
                    nearsearch(node.leftchild, leftrect, x, y);
                    nearsearch(node.rightchild, rightrect, x, y);
                } else {
                    nearsearch(node.rightchild, rightrect, x, y);
                    nearsearch(node.leftchild, leftrect, x, y);
                }
            }

            if (node.usexory == USEY) {
                if (y <= node.point.y()) {
                    nearsearch(node.leftchild, leftrect, x, y);
                    nearsearch(node.rightchild, rightrect, x, y);
                } else {
                    nearsearch(node.rightchild, rightrect, x, y);
                    nearsearch(node.leftchild, leftrect, x, y);
                }
            }
        } else {
            if (leftrect.distanceSquaredTo(point) < mindis)
                nearsearch(node.leftchild, leftrect, x, y);
            if (rightrect.distanceSquaredTo(point) < mindis)
                nearsearch(node.rightchild, rightrect, x, y);
        }

    }

    public static void main(String[] args) {
        KdTree test = new KdTree();
        test.insert(new Point2D(0, 0.5));
        test.insert(new Point2D(0.5, 1));
        test.insert(new Point2D(0.5, 0));
        test.insert(new Point2D(1, 0.5));
        RectHV rect = new RectHV(0.1, 0.8, 0.9, 1.1);
        System.out.println(test.range(rect).toString());

    }

}
