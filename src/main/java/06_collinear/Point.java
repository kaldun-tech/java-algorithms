import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

/**
 * Create an immutable data type Point that represents a point in the plane.
 * use the data type Point.java, which implements the constructor and the
 * draw(), drawTo(), and toString() methods.
 */
public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Constructs the point (x, y). To avoid potential complications with
     * integer overflow or floating-point precision, you may assume that the
     * constructor arguments x and y are each between 0 and 32,767.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /** Draws this point */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment from this point to that point
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /** String representation. Do not override the equals() or hashCode() methods. */
    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compare two points by y-coordinates, breaking ties by x-coordinates.
     * The compareTo() method should compare points by their y-coordinates,
     * breaking ties by their x-coordinates. Formally, the invoking point
     * (x0, y0) is less than the argument point (x1, y1) if and only if either
     * y0 < y1 or if y0 = y1 and x0 < x1
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (that == null) {
            throw new NullPointerException("compareTo received null input");
        }
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else if (this.y == that.y && this.x == that.x) return 0;
        else return 1;
    }

    /**
     * The slope between this point and that point. The slopeTo() method should
     * return the slope between the invoking point (x0, y0) and the argument point
     * (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). Treat the
     * slope of a horizontal line segment as positive zero; treat the slope of a
     * vertical line segment as positive infinity; treat the slope of a degenerate
     * line segment (between a point and itself) as negative infinity.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException("slopeTo received null input");
        }
        if (compareTo(that) == 0) {
            // Points are equal -> negative infinity
            return Double.NEGATIVE_INFINITY;
        }
        else if (this.y == that.y) {
            // Horizontal line -> 0
            return 0.0;
        }
        else if (this.x == that.x) {
            // Vertical line -> positive infinity
            return Double.POSITIVE_INFINITY;
        }
        else {
            return 1.0 * (that.y - this.y) / (that.x - this.x);
        }
    }

    /**
     * Compare two points by slopes they make with this point. The slopeOrder()
     * method should return a comparator that compares its two argument points
     * by the slopes they make with the invoking point (x0, y0).
     * Formally, the point (x1, y1) is less than the point (x2, y2) if and only
     * if the slope (y1 − y0) / (x1 − x0) is less than the slope
     * (y2 − y0) / (x2 − x0).
     * Treat horizontal, vertical, and degenerate line segments as in the
     * slopeTo() method.
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q, Point r) {
            if (q == null) {
                throw new NullPointerException("SlopeOrder received null first point");
            }
            else if (r == null) {
                throw new NullPointerException("SlopeOrder received null second point");
            }
            double slope1 = slopeTo(q);
            double slope2 = slopeTo(r);
            return Double.compare(slope1, slope2);
        }
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        testCompareTo();
        testSlopeTo();
        testSlopeOrder();
        System.out.println("All tests passed!");
    }

    private static void testCompareTo() {
        Point p = new Point(2, 2);
        // Test null
        try {
            p.compareTo(null);
            assert false;
        }
        catch (NullPointerException e) {
            System.out.println("Caught expected exception for compareTo(null)");
        }

        // Same
        assert p.compareTo(p) == 0;

        // Greater X
        Point right = new Point(4, 2);
        assert p.compareTo(right) < 0;

        // Greater Y
        Point above = new Point(2, 4);
        assert p.compareTo(above) < 0;

        Point lowerLeft = new Point(1, 1);
        assert 0 < p.compareTo(lowerLeft);

        System.out.println("  compareTo tests passed");
    }

    private static void testSlopeTo() {
        Point p = new Point(1, 1);
        // Test null
        try {
            p.slopeTo(null);
            assert false;
        }
        catch (NullPointerException e) {
            System.out.println("Caught expected exception for slopeTo(null)");
        }

        // Same point -> negative infinity
        assert p.slopeTo(p) == Double.NEGATIVE_INFINITY;

        // Horizontal line -> zero
        Point q = new Point(5, 1);
        assert p.slopeTo(q) == 0;
        assert q.slopeTo(p) == 0;

        // Vertical line -> positive infinity
        q = new Point(1, 5);
        assert p.slopeTo(q) == Double.POSITIVE_INFINITY;
        assert q.slopeTo(p) == Double.POSITIVE_INFINITY;

        // Normal
        q = new Point(3, 3);
        assert p.slopeTo(q) == 1;
        assert q.slopeTo(p) == -1;
        System.out.println("  slopeTo tests passed");
    }

    private static void testSlopeOrder() {
        Point p = new Point(2, 2);
        Point q = new Point(3, 3);
        Point r = new Point(4, 4);
        Point s = new Point(2, 5);
        Comparator<Point> order = p.slopeOrder();
        double result;
        try {
            result = order.compare(q, null);
            assert false;
        }
        catch (NullPointerException e) {
            System.out.println("Caught expected exception for SlopeOrder o.compare(q, null)");
        }
        try {
            result = order.compare(null, r);
            assert false;
        }
        catch (NullPointerException e) {
            System.out.println("Caught expected exception for SlopeOrder o.compare(null, r)");
        }

        // Degenerate case
        result = order.compare(p, p);
        assert result == 0;

        // Same line
        result = order.compare(q, r);
        assert result == 0;

        // Zero < positive
        result = order.compare(q, s);
        assert 0 < result;

        result = order.compare(s, r);
        assert result < 0;

        System.out.println("  SlopeOrder tests passed");
    }
}
