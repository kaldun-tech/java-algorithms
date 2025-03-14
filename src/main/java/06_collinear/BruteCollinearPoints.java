import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Write a program BruteCollinearPoints.java that examines 4 points at a time
 * and checks whether they all lie on the same line segment, returning all such
 * line segments. To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q, between p and r, and between
 * p and s are all equal.
 * Performance Requirement: The order of growth of the running time of your
 * program should be O(n^4) in the worst case, and it should use space
 * proportional to n plus the number of line segments returned.
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numSegments = 0;
    private int maxSegments;
    private SET<Point> pointSet;

    /**
     * Finds all line segments containing 4 points. Throw an IllegalArgumentException
     * if the argument to the constructor is null, if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Constructor received null input");
        }
        maxSegments = 4;
        segments = new LineSegment[maxSegments];
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

    /** Builds LineSegment array for Point array O(n^4) */
    private void buildSegmentsForPoints(Point[] points) {
        for (int i = 0; i < points.length - 3; ++i) {
            Point p = points[i];
            for (int j = i + 1; j < points.length - 2; ++j) {
                Point q = points[j];
                for (int k = j + 1; k < points.length - 1; ++k) {
                    Point r = points[k];
                    for (int m = k + 1; m < points.length; ++m) {
                        Point s = points[m];
                        LineSegment[] newSegments = getSegmentsForPoints(p, q, r, s);
                        addSegments(newSegments);
                    }
                }
            }
        }
    }

    /** Grows the segments array */
    private void resizeSegments() {
        int prevMax = maxSegments;
        maxSegments *= 2;
        LineSegment[] newSegments = new LineSegment[maxSegments];
        for (int i = 0; i < prevMax; ++i) {
            newSegments[i] = segments[i];
        }
        segments = newSegments;
    }

    /**
     * Adds segments in an array to the end of existing array O(n)
     * Points are unique so a segment should not be collinear to a previous
     */
    private void addSegments(LineSegment[] newSegments) {
        for (LineSegment next : newSegments) {
            if (next == null) {
                System.out.println("Unexpected null segment");
                continue;
            }
            if (numSegments + 1 == maxSegments) resizeSegments();
            segments[numSegments] = next;
            ++numSegments;
        }
    }

    private void debugPoints(Point[] points) {
        StringBuilder sb = new StringBuilder("Points: [ ");
        for (Point p : points) {
            sb.append(p).append(", ");
        }
        sb.append(" ]");
        System.out.println(sb.toString());
    }

    private LineSegment[] getSegmentsForPoints(Point p, Point q, Point r, Point s) {
        double slopePQ = p.slopeTo(q);
        double slopePR = p.slopeTo(r);
        double slopePS = p.slopeTo(s);
        Comparator<Point> order = p.slopeOrder();
        int orderPQR = order.compare(q, r);
        int orderPQS = order.compare(q, s);
        int orderPRS = order.compare(r, s);
        if (orderPQR == orderPRS) {
            // All points are collinear
            return new LineSegment[] { new LineSegment(p, s) };
        }
        else if (orderPQR == 0 || orderPQS == 0) {
            // PQR or PQS is one segment
            return new LineSegment[] {
                    new LineSegment(p, r), new LineSegment(p, s)
            };
        }
        else if (orderPRS == 0) {
            // PRS is one segment
            return new LineSegment[] {
                    new LineSegment(p, q), new LineSegment(p, s)
            };
        }
        else {
            // All independent segments
            return new LineSegment[] {
                    new LineSegment(p, q), new LineSegment(p, r), new LineSegment(p, s)
            };
        }
    }

    /** Get the number of line segments */
    public int numberOfSegments() {
        return numSegments;
    }

    /**
     * Get the line segments. The method segments() should include each line segment
     * containing 4 points exactly once. If 4 points appear on a line segment in
     * the order p→q→r→s, then you should include either the line segment p→s or
     * s→p (but not both) and you should not include subsegments such as p→r or q→r.
     * For simplicity, we will not supply any input to BruteCollinearPoints
     * that has 5 or more collinear points.
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numSegments);
    }

    /** Sample client for brute force */
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
