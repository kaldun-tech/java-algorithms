import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Brute-force implementation. Write a mutable data type that represents a set of
 * points in the unit square. Implement the following API by using a red–black BST.
 *
 * You must use either SET or java.util.TreeSet; do not implement your own red–black BST.
 *
 * Corner cases: Throw an IllegalArgumentException if any argument is null.
 *
 * Performance requirements: Your implementation should support insert() and contains()
 * in time proportional to the logarithm of the number of points in the set in
 * the worst case; it should support nearest() and range() in time proportional
 * to the number of points in the set.
 */
public class PointSET {
    
    /**
     * The set of points stored in a red-black BST
     */
    private final TreeSet<Point2D> points;
    
    /**
     * Construct an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * Is the set empty?
     * @return true if the set contains no points, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Implement this method
        return points.isEmpty();
    }

    /**
     * Number of points in the set
     * @return the number of points in the set
     */
    public int size() {
        // TODO: Implement this method
        return points.size();
    }

    /**
     * Add the point to the set (if it is not already in the set)
     * @param p the point to add
     * @throws IllegalArgumentException if the point is null
     */
    public void insert(Point2D p) {
        // TODO: Implement this method
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        points.add(p);
    }

    /**
     * Does the set contain point p?
     * @param p the point to check
     * @return true if the set contains the point, false otherwise
     * @throws IllegalArgumentException if the point is null
     */
    public boolean contains(Point2D p) {
        // TODO: Implement this method
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        return points.contains(p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        // TODO: Implement this method
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : points) {
            p.draw();
        }
    }

    /**
     * All points that are inside the rectangle (or on the boundary)
     * @param rect the query rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     * @throws IllegalArgumentException if the rectangle is null
     */
    public Iterable<Point2D> range(RectHV rect) {
        // TODO: Implement this method
        if (rect == null) throw new IllegalArgumentException("Rectangle cannot be null");
        
        ArrayList<Point2D> result = new ArrayList<>();
        
        // Brute force approach: check each point in the set
        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        
        return result;
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     * @param p the query point
     * @return a nearest neighbor in the set to point p, null if the set is empty
     * @throws IllegalArgumentException if the point is null
     */
    public Point2D nearest(Point2D p) {
        // TODO: Implement this method
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        if (isEmpty()) return null;
        
        Point2D nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        
        // Brute force approach: check each point in the set
        for (Point2D point : points) {
            double distance = p.distanceSquaredTo(point);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = point;
            }
        }
        
        return nearest;
    }

    /**
     * Unit testing of the methods (optional)
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // TODO: Implement unit tests
    }
}
