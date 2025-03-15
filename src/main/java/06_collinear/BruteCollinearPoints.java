import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * BruteCollinearPoints implements a brute-force algorithm for finding all line segments
 * containing exactly 4 collinear points in a set of points.
 * 
 * Algorithm overview:
 * 1. Examine every combination of 4 points
 * 2. Check if all 4 points are collinear by comparing slopes
 * 3. If collinear, add a line segment between the endpoints
 * 
 * Time complexity: O(n^4) where n is the number of points
 * Space complexity: O(n) plus space for the output segments
 * 
 * The algorithm ensures that:
 * - Each line segment containing exactly 4 collinear points is reported once
 * - Subsegments are not included in the output
 */
public class BruteCollinearPoints {
    private final LineSegment[] segmentsArray;

    /**
     * Finds all line segments containing exactly 4 points.
     * 
     * @param points Array of points to analyze
     * @throws IllegalArgumentException if the argument is null, contains null points,
     *                                  or contains repeated points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Constructor received null input");
        }
        
        // Check for null points
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Point array has null point");
            }
        }
        
        // Make a proper defensive copy of the input array
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsCopy[i] = points[i];
        }
        
        // Check for duplicate points
        Arrays.sort(pointsCopy);
        checkForDuplicatePoints(pointsCopy);
        
        // Find all line segments
        Stack<LineSegment> segments = findAllLineSegments(pointsCopy);
        
        // Convert stack to array for immutability
        segmentsArray = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment seg : segments) {
            segmentsArray[i++] = seg;
        }
    }

    /**
     * Checks for duplicate points in a sorted array of points.
     * 
     * @param points Sorted array of points
     * @throws IllegalArgumentException if duplicate points are found
     */
    private void checkForDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate point " + points[i].toString());
            }
        }
    }
    
    /**
     * Finds all line segments containing exactly 4 collinear points using brute force.
     * 
     * @param points Array of points to analyze
     * @return Stack of LineSegment objects
     */
    private Stack<LineSegment> findAllLineSegments(Point[] points) {
        Stack<LineSegment> segments = new Stack<>();
        int n = points.length;
        
        // Check all combinations of 4 points
        for (int i = 0; i < n - 3; i++) {
            Point p = points[i];
            
            for (int j = i + 1; j < n - 2; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);
                
                for (int k = j + 1; k < n - 1; k++) {
                    Point r = points[k];
                    double slopePR = p.slopeTo(r);
                    
                    // Skip if slopes don't match
                    if (slopePQ != slopePR) continue;
                    
                    for (int m = k + 1; m < n; m++) {
                        Point s = points[m];
                        double slopePS = p.slopeTo(s);
                        
                        // If all slopes are equal, the 4 points are collinear
                        if (slopePQ == slopePS) {
                            // Since points are sorted, p is the minimum point and s is the maximum
                            segments.push(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
        
        return segments;
    }

    /**
     * Returns the number of line segments found.
     * 
     * @return Number of line segments
     */
    public int numberOfSegments() {
        return segmentsArray.length;
    }

    /**
     * Returns an array of all line segments found.
     * Each line segment containing exactly 4 collinear points is included once.
     * 
     * @return Array of line segments
     */
    public LineSegment[] segments() {
        // Return a defensive copy to maintain immutability
        return segmentsArray.clone();
    }

    /**
     * Sample client for brute force algorithm.
     * Takes an input file, reads points, and displays line segments.
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
