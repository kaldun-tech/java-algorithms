import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Remarkably, it is possible to solve the problem much faster than the
 * brute-force solution described above. Given a point origin, the following method4
 * determines whether origin participates in a set of 4 or more collinear points.
 * 1. Think of origin as the origin.
 * 2. For each other point destination, determine the slope it makes with origin.
 * 3. Sort the points according to the slopes they makes with origin
 * 4. Check if any 3 (or more) adjacent points in the sorted order have equal
 * slopes with respect to origin. If so, these points, together with origin, are collinear.
 * Applying this method for each of the n points in turn yields an efficient
 * algorithm to the problem. The algorithm solves the problem because points
 * that have equal slopes with respect to origin are collinear, and sorting brings
 * such points together. The algorithm is fast because the bottleneck operation is sorting.
 * <origin>
 * Performance requirement. The order of growth of the running time of your program should
 * be O(n^2 log n) in the worst case and it should use space proportional to n plus the
 * number of line segments returned. FastCollinearPoints should work properly even if the
 * input has 5 or more collinear points.
 */
public class FastCollinearPoints {
    private Stack<LineSegment> segments;
    private SET<Point> pointSet;

    /**
     * Finds all line segments containing 4 or more points. Throw an IllegalArgumentException
     * if the argument to the constructor is null, if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Constructor received null input");
        }
        segments = new Stack<LineSegment>();
        pointSet = new SET<Point>();
        verifyPointsAreUnique(points);
        buildSegmentsForPoints(points);
    }

    private void verifyPointsAreUnique(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Point array has null point");
            }
            else if (pointSet.contains(p)) {
                throw new IllegalArgumentException(
                        "Point array has duplicate point " + p.toString());
            }
            else {
                pointSet.add(p);
            }
        }
    }


    /**
     * Builds LineSegment array for Point array O(n^2 log n)
     * Given a point origin, the following method determines whether origin participates
     * in a set of 4 or more collinear points.
     * Think of origin as the origin
     * For each other point destination, determine the slope it makes with origin.
     * Sort the points according to the slopes they makes with origin
     * Check if any 3 (or more) adjacent points in the sorted order have equal
     * slopes with respect to origin. If so, these points, together with origin, are collinear.
     * Applying this method for each of the n points in turn yields an efficient
     * algorithm to the problem. The algorithm solves the problem because points
     * that have equal slopes with respect to origin are collinear, and sorting brings
     * such points together. The algorithm is fast because the bottleneck operation is sorting.
     */
    private void buildSegmentsForPoints(Point[] points) {
        for (int i = 0; i < points.length - 3; ++i) {
            Point origin = points[i];
            Comparator<Point> slopeOrder = origin.slopeOrder();
            Point[] destinations = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(destinations, slopeOrder);
            addCollinearSegments(origin, destinations);
        }
    }

    private void addCollinearSegments(Point p, Point[] destinations) {
        double prevSlope = Double.NEGATIVE_INFINITY;
        for (Point q : destinations) {
            double nextSlope = p.slopeTo(q);
            if (prevSlope == Double.NEGATIVE_INFINITY) {
                // First element -> continue
                prevSlope = nextSlope;
            }
            else if (prevSlope != nextSlope) {
                // Different slope -> not collinear -> Add segment
                LineSegment seg = new LineSegment(p, q);
                segments.push(seg);
                prevSlope = nextSlope;
            }
            // Else same slope -> collinear -> no op
        }
    }

    /** The number of line segments */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * The method segments() should include each maximal line segment containing
     * 4 (or more) points exactly once. For example, if 5 points appear on a line
     * segment in the order origin→destination→r→s→t, then do not include the subsegments origin→s
     * or destination→t.
     */
    public LineSegment[] segments() {
        LineSegment[] asArray = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment seg : segments) {
            asArray[i] = seg;
            ++i;
        }
        return asArray;
    }

    /**
     * This client program takes the name of an input file as a command-line argument;
     * read the input file (in the format specified below); prints to standard output
     * the line segments that your program discovers, one per line; and draws to
     * standard draw the line segments.
     */
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
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
    }
}
