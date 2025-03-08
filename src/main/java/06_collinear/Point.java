import java.util.Comparator;

/** Create an immutable data type Point that represents a point in the plane.
 * use the data type Point.java, which implements the constructor and the
 * draw(), drawTo(), and toString() methods. */
public class Point implements Comparable<Point> {
    /** Constructs the point (x, y). To avoid potential complications with
     * integer overflow or floating-point precision, you may assume that the
     * constructor arguments x and y are each between 0 and 32,767.*/
    public Point(int x, int y) {

    }

    /** Draws this point */
    public void draw() {

    }

    /** Draws the line segment from this point to that point */
    public void drawTo(Point that) {

    }

    /** String representation. Do not override the equals() or hashCode() methods. */
    @Override
    public String toString() {

    }

    /** Compare two points by y-coordinates, breaking ties by x-coordinates.
     * The compareTo() method should compare points by their y-coordinates,
     * breaking ties by their x-coordinates. Formally, the invoking point
     * (x0, y0) is less than the argument point (x1, y1) if and only if either
     * y0 < y1 or if y0 = y1 and x0 < x1 */
    public int compareTo(Point that) {

    }

    /** The slope between this point and that point. The slopeTo() method should
     * return the slope between the invoking point (x0, y0) and the argument point
     * (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). Treat the
     * slope of a horizontal line segment as positive zero; treat the slope of a
     * vertical line segment as positive infinity; treat the slope of a degenerate
     * line segment (between a point and itself) as negative infinity. */
    public double slopeTo(Point that) {

    }

    /** Compare two points by slopes they make with this point. The slopeOrder()
     * method should return a comparator that compares its two argument points
     * by the slopes they make with the invoking point (x0, y0). Formally, the
     * point (x1, y1) is less than the point (x2, y2) if and only if the slope
     * (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
     * Treat horizontal, vertical, and degenerate line segments as in the
     * slopeTo() method. */
    public Comparator<Point> slopeOrder() {

    }
}
