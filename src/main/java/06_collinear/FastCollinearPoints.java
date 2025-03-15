import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * FastCollinearPoints implements a fast algorithm for finding all line segments
 * containing 4 or more collinear points in a set of points.
 * 
 * Algorithm overview:
 * 1. For each point p as origin:
 *    a. Calculate the slope between p and all other points
 *    b. Sort the points by slope
 *    c. Find groups of points with the same slope (collinear points)
 *    d. If a group has 3 or more points (plus origin = 4+ points), create a line segment
 * 
 * Time complexity: O(n^2 log n) where n is the number of points
 * Space complexity: O(n) plus space for the output segments
 * 
 * The algorithm ensures that:
 * - Each maximal line segment is reported exactly once
 * - Subsegments are not included in the output
 * - The implementation handles 5 or more collinear points correctly
 */
public class FastCollinearPoints {
    private final LineSegment[] segmentsArray;

    /**
     * Finds all line segments containing 4 or more points.
     * 
     * @param points Array of points to analyze
     * @throws IllegalArgumentException if the argument is null, contains null points,
     *                                  or contains repeated points
     */
    public FastCollinearPoints(Point[] points) {
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
     * Finds all line segments containing 4 or more collinear points.
     * 
     * @param points Array of points to analyze
     * @return Stack of LineSegment objects
     */
    private Stack<LineSegment> findAllLineSegments(Point[] points) {
        Stack<LineSegment> segments = new Stack<>();
        int n = points.length;
        
        // For each point as origin
        for (int i = 0; i < n; i++) {
            Point origin = points[i];
            
            // Create and sort array of other points by slope to origin
            Point[] otherPoints = getOtherPointsSortedBySlope(origin, points, i);
            
            // Find collinear points and add segments
            findCollinearPointsForOrigin(origin, otherPoints, segments);
        }
        
        return segments;
    }
    
    /**
     * Creates an array of all points except the origin point and sorts them by slope to origin.
     * 
     * @param origin The origin point
     * @param points All points
     * @param originIndex Index of the origin point in the points array
     * @return Array of all other points sorted by slope to origin
     */
    private Point[] getOtherPointsSortedBySlope(Point origin, Point[] points, int originIndex) {
        int n = points.length;
        
        // Create an array of all other points with their slopes to origin
        Point[] otherPoints = new Point[n - 1];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (i != originIndex) {
                otherPoints[idx++] = points[i];
            }
        }
        
        // Sort by slope relative to origin
        Arrays.sort(otherPoints, origin.slopeOrder());
        
        return otherPoints;
    }
    
    /**
     * Finds groups of collinear points for a given origin and adds line segments
     * for groups with 4 or more collinear points (including origin).
     * 
     * @param origin The origin point
     * @param otherPoints Array of points sorted by slope to origin
     * @param segments Stack to add line segments to
     */
    private void findCollinearPointsForOrigin(Point origin, Point[] otherPoints, Stack<LineSegment> segments) {
        int n = otherPoints.length;
        int count = 1;

        for (int i = 0; i < n; i += count) {
            // Find all points with the same slope
            double currentSlope = origin.slopeTo(otherPoints[i]);
            // Count includes the current point
            count = 1; 
            
            // Count points with the same slope
            while (i + count < n && origin.slopeTo(otherPoints[i + count]) == currentSlope) {
                count++;
            }
            
            // If we have at least 3 points with the same slope (plus origin = 4 points)
            if (count >= 3) {
                addMaximalSegmentIfNeeded(origin, otherPoints, i, count, segments);
            }
        }
    }
    
    /**
     * Creates and adds a maximal line segment if the origin is the minimum point
     * in the collinear set.
     * 
     * @param origin The origin point
     * @param otherPoints Array of points sorted by slope to origin
     * @param startIndex Start index of collinear points in otherPoints
     * @param count Number of collinear points
     * @param segments Stack to add line segments to
     */
    private void addMaximalSegmentIfNeeded(Point origin, Point[] otherPoints, int startIndex, 
                                         int count, Stack<LineSegment> segments) {
        // Create array with origin and all collinear points
        Point[] collinearPoints = new Point[count + 1];
        collinearPoints[0] = origin;
        for (int k = 0; k < count; k++) {
            collinearPoints[k + 1] = otherPoints[startIndex + k];
        }
        
        // Sort by natural ordering
        Arrays.sort(collinearPoints);
        
        // Only add the segment if origin is the minimum point. This ensures we only add each maximal segment once
        if (collinearPoints[0].compareTo(origin) == 0) {
            segments.push(new LineSegment(collinearPoints[0], collinearPoints[count]));
        }
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
     * Each maximal line segment containing 4 or more points is included exactly once.
     * 
     * @return Array of line segments
     */
    public LineSegment[] segments() {
        // Return a defensive copy to maintain immutability
        return segmentsArray.clone();
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
