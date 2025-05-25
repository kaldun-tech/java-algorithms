import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

/**
 * Write a mutable data type that uses a 2d-tree to implement the same API
 * (but replace PointSET with KdTree). A 2d-tree is a generalization of a BST to
 * two-dimensional keys. The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 *
 * Search and insert: The algorithms for search and insert are similar to those
 * for BSTs, but at the root we use the x-coordinate (if the point to be inserted
 * has a smaller x-coordinate than the point at the root, go left; otherwise go right);
 * then at the next level, we use the y-coordinate (if the point to be inserted
 * has a smaller y-coordinate than the point in the node, go left; otherwise go right);
 * then at the next level the x-coordinate, and so forth.
 *
 * The prime advantage of a 2d-tree over a BST is that it supports efficient
 * implementation of range search and nearest-neighbor search. Each node corresponds
 * to an axis-aligned rectangle in the unit square, which encloses all of the points
 * in its subtree. The root corresponds to the unit square; the left and right
 * children of the root corresponds to the two rectangles split by the x-coordinate
 * of the point at the root; and so forth.
 */
public class KdTree {
    /**
     * Node in the KdTree
     */
    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;        // the axis-aligned rectangle corresponding to this node
        private Node left;          // the left/bottom subtree
        private Node right;         // the right/top subtree
        private boolean vertical;   // is this a vertical dividing line (true) or horizontal (false)
    }
    
    private Node root;      // root of the KdTree
    private int size;       // number of nodes in the KdTree
    
    /**
     * Construct an empty set of points
     */
    public KdTree() {
        // Initialize an empty KdTree
    }

    /**
     * Is the set empty?
     * @return true if the set contains no points, false otherwise
     */
    public boolean isEmpty() {
        // Check if the KdTree is empty
        return true; // placeholder return
    }

    /**
     * Number of points in the set
     * @return the number of points in the set
     */
    public int size() {
        // Return the number of points in the KdTree
        return 0; // placeholder return
    }

    /**
     * Add the point to the set (if it is not already in the set)
     * @param p the point to add
     * @throws IllegalArgumentException if the point is null
     */
    public void insert(Point2D p) {
        // Insert a point into the KdTree
    }
    
    /**
     * Does the set contain point p?
     * @param p the point to check
     * @return true if the set contains the point, false otherwise
     * @throws IllegalArgumentException if the point is null
     */
    public boolean contains(Point2D p) {
        // Check if the KdTree contains the point
        return false; // placeholder return
    }

    /**
     * Draw all points to standard draw. A 2d-tree divides the unit square in a
     * simple way: all the points to the left of the root go in the left subtree;
     * all those to the right go in the right subtree; and so forth, recursively.
     * Your draw() method should draw all of the points to standard draw in black
     * and the subdivisions in red (for vertical splits) and blue (for horizontal splits).
     * This method need not be efficient—it is primarily for debugging.
     */
    public void draw() {
        // Draw all points and splitting lines
    }

    /**
     * All points that are inside the rectangle (or on the boundary)
     * To find all points contained in a given query rectangle, start at the root
     * and recursively search for points in both subtrees using the following
     * pruning rule: if the query rectangle does not intersect the rectangle
     * corresponding to a node, there is no need to explore that node (or its subtrees).
     * A subtree is searched only if it might contain a point contained in the query rectangle.
     * @param rect the query rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     * @throws IllegalArgumentException if the rectangle is null
     */
    public Iterable<Point2D> range(RectHV rect) {
        // Find all points contained in the query rectangle
        return new ArrayList<Point2D>(); // placeholder return
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     * Nearest-neighbor search. To find a closest point to a given query point,
     * start at the root and recursively search in both subtrees using the following
     * pruning rule: if the closest point discovered so far is closer than the
     * distance between the query point and the rectangle corresponding to a node,
     * there is no need to explore that node (or its subtrees). That is, search
     * a node only only if it might contain a point that is closer than the best
     * one found so far. The effectiveness of the pruning rule depends on quickly
     * finding a nearby point. To do this, organize the recursive method so that
     * when there are two possible subtrees to go down, you always choose the
     * subtree that is on the same side of the splitting line as the query point
     * as the first subtree to explore—the closest point found while exploring the
     * first subtree may enable pruning of the second subtree.
     * @param p the query point
     * @return a nearest neighbor in the set to point p, null if the set is empty
     * @throws IllegalArgumentException if the point is null
     */
    public Point2D nearest(Point2D p) {
        // Find the nearest neighbor to the query point
        return null; // placeholder return
    }

    /**
     * Unit testing of the methods (optional)
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Unit testing
    }
}
