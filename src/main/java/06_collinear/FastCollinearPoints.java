/**
 * Remarkably, it is possible to solve the problem much faster than the
 * brute-force solution described above. Given a point p, the following method4
 * determines whether p participates in a set of 4 or more collinear points.
 * 1. Think of p as the origin.
 * 2. For each other point q, determine the slope it makes with p.
 * 3. Sort the points according to the slopes they makes with p
 * 4. Check if any 3 (or more) adjacent points in the sorted order have equal
 * slopes with respect to p. If so, these points, together with p, are collinear.
 * Applying this method for each of the n points in turn yields an efficient
 * algorithm to the problem. The algorithm solves the problem because points
 * that have equal slopes with respect to p are collinear, and sorting brings
 * such points together. The algorithm is fast because the bottleneck operation is sorting.
 * <p>
 * Performance requirement. The order of growth of the running time of your program should
 * be O(n^2 log n) in the worst case and it should use space proportional to n plus the
 * number of line segments returned. FastCollinearPoints should work properly even if the
 * input has 5 or more collinear points.
 */
public class FastCollinearPoints {
    /**
     * Finds all line segments containing 4 or more points. Throw an IllegalArgumentException
     * if the argument to the constructor is null, if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     */
    public FastCollinearPoints(Point[] points) {

    }

    /** The number of line segments */
    public int numberOfSegments() {

    }

    /**
     * The method segments() should include each maximal line segment containing
     * 4 (or more) points exactly once. For example, if 5 points appear on a line
     * segment in the order p→q→r→s→t, then do not include the subsegments p→s or q→t.
     */
    public LineSegment[] segments() {

    }

    /**
     * This client program takes the name of an input file as a command-line argument;
     * read the input file (in the format specified below); prints to standard output
     * the line segments that your program discovers, one per line; and draws to
     * standard draw the line segments.
     */
    public static void main(String[] args) {

        // read the n points from a file
        StdIn in = new StdIn(args[0]);
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
