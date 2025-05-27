import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Comparator;

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
        /** the point */
        private Point2D point;
        /** the axis-aligned rectangle corresponding to this node */
        private RectHV rect;
        /** the left/bottom subtree */
        private Node left;
        /** the right/top subtree */
        private Node right;
        /** is this a vertical dividing line (true) or horizontal (false) */
        private boolean vertical;

        public Node(Point2D p, RectHV r, boolean isVertical) {
            point = p;
            rect = r;
            vertical = isVertical;
        }
    }

    /** root of the KdTree */
    private Node root;
    /** number of nodes in the KdTree */
    private int size;
    /** Initial rectangle of max size: x and y coordinates in range [0, 1] */
    private static final RectHV MAX_RECT = new RectHV(0, 0, 1, 1);

    /**
     * Construct an empty set of points O(1)
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * Is the set empty? O(1)
     * @return true if the set contains no points, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Number of points in the set O(1)
     * @return the number of points in the set
     */
    public int size() {
        return size;
    }

    /**
     * Add the point to the set (if it is not already in the set)
     * O(log N) because of logarithmic tree operations
     * @param p the point to add
     * @throws IllegalArgumentException if the point is null
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        } else if (isEmpty()) {
            // Base case: root is vertical with maximally bounded rectangle
            root = new Node(p, MAX_RECT, true);
            size = 1;
            return;
        } else if (contains(p)) {
            // Nothing to do
            return;
        }

        doInsert(p, root);
    }

    /**
     * Search and insert: The algorithms for search and insert are similar to those
     * for BSTs, but at the root we use the x-coordinate (if the point to be inserted
     * has a smaller x-coordinate than the point at the root, go left; otherwise go right);
     * then at the next level, we use the y-coordinate (if the point to be inserted
     * has a smaller y-coordinate than the point in the node, go left; otherwise go right);
     * then at the next level the x-coordinate, and so forth.
     * @param p
     * @param n
     */
    private void doInsert(Point2D p, Node n) {
        int cmp = comparePointToNode(p, n);
        // Flip the vertical flag for the next level
        boolean nextVertical = !n.vertical;
        // Create a new rectangle for the child
        RectHV childRect;
        
        if (cmp < 0) {
            // Go left
            if (n.left == null) {
                if (n.vertical) {
                    // Vertical split - divide by x-coordinate
                    childRect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.point.x(), n.rect.ymax());
                } else {
                    // Horizontal split - divide by y-coordinate
                    childRect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.point.y());
                }
                ++size;
                n.left = new Node(p, childRect, nextVertical);
            } else {
                doInsert(p, n.left);
            }
        } else {
            // Go right
            if (n.right == null) {
                if (n.vertical) {
                    // Vertical split - divide by x-coordinate
                    childRect = new RectHV(n.point.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
                } else {
                    // Horizontal split - divide by y-coordinate
                    childRect = new RectHV(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.rect.ymax());
                }
                ++size;
                n.right = new Node(p, childRect, nextVertical);
            } else {
                doInsert(p, n.right);
            }
        }
    }

    /**
     * Does the set contain point p? O(log N) because of logarithmic tree operations
     * @param p the point to check
     * @return true if the set contains the point, false otherwise
     * @throws IllegalArgumentException if the point is null
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        } else if (isEmpty()) {
            return false;
        }

        return doContains(p, root);
    }

    private int comparePointToNode(Point2D p, Node n) {
        Comparator<Point2D> comp = n.vertical ? Point2D.X_ORDER : Point2D.Y_ORDER;
        return comp.compare(p, n.point);
    }

    /**
     * Search and insert: The algorithms for search and insert are similar to those
     * for BSTs, but at the root we use the x-coordinate (if the point to be inserted
     * has a smaller x-coordinate than the point at the root, go left; otherwise go right);
     * then at the next level, we use the y-coordinate (if the point to be inserted
     * has a smaller y-coordinate than the point in the node, go left; otherwise go right);
     * then at the next level the x-coordinate, and so forth.
     * @param p
     * @param n
     * @return
     */
    private boolean doContains(Point2D p, Node n) {
        if (n == null) {
            return false;
        }

        Point2D q = n.point;
        if (p.equals(q)) {
            return true;
        }

        int cmp = comparePointToNode(p, n);
        boolean leftContains = (n.left != null && cmp < 0 && doContains(p, n.left));
        boolean rightContains = (n.right != null && 0 <= cmp && doContains(p, n.right));
        return leftContains || rightContains;
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
        StdDraw.setPenRadius(0.01);
        doDraw(root);
    }

    /** Draws the tree recursively O(n) pre-order traversal */
    private void doDraw(Node n) {
        if (n == null) {
            return;
        }

        drawPoint(n.point);
        drawRect(n.rect, n.vertical);
        if (n.left != null) {
            doDraw(n.left);
        }
        if (n.right != null) {
            doDraw(n.right);
        }
    }

    private void drawPoint(Point2D p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        p.draw();
    }

    private void drawRect(RectHV r, boolean isVertical) {
        StdDraw.setPenColor(isVertical ? StdDraw.RED : StdDraw.BLUE);
        r.draw();
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
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }

        ArrayList<Point2D> result = new ArrayList<>();
        doRange(rect, root, result);
        return result;
    }

    /** Does the range search using BST O(lg N) */
    private void doRange(RectHV rect, Node n, ArrayList<Point2D> result) {
        if (result == null) {
            throw new IllegalArgumentException("Result list cannot be null");
        } else if (n == null) {
            return;
        }
        
        /* If the query rectangle doesn't intersect this node's rectangle,
         * we can skip this entire subtree */
        if (!rect.intersects(n.rect)) {
            return;
        }

        // Check if the current point is in the query rectangle
        Point2D p = n.point;
        if (rect.contains(p)) {
            result.add(p);
        }

        // Recursively search both subtrees
        if (n.left != null) {
            doRange(rect, n.left, result);
        }
        if (n.right != null) {
            doRange(rect, n.right, result);
        }
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     * Nearest-neighbor search. To find a closest point to a given query point,
     * start at the root and recursively search in both subtrees using the
     * pruning rule
     * @param p the query point
     * @return a nearest neighbor in the set to point p, null if the set is empty
     * @throws IllegalArgumentException if the point is null
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        } else if (isEmpty()) {
            return null;
        }

        /* Start with null as the best point so far, which will be updated to the root
         * in the first call to nearestPruning */
        return nearestPruning(p, null, root);
    }

    /**
     * Does the nearest neighbor search using the pruning rule:
     * if the closest point discovered so far is closer than the
     * distance between the query point and the rectangle corresponding to a node,
     * there is no need to explore that node (or its subtrees). That is, search
     * a node only only if it might contain a point that is closer than the best
     * one found so far. The effectiveness of the pruning rule depends on quickly
     * finding a nearby point. To do this, organize the recursive method so that
     * when there are two possible subtrees to go down, you always choose the
     * subtree that is on the same side of the splitting line as the query point
     * as the first subtree to explore—the closest point found while exploring the
     * first subtree may enable pruning of the second subtree.
     * @param p query point
     * @param bestSoFar best point found so far
     * @param n current node
     * @return nearest point
     */
    private Point2D nearestPruning(Point2D p, Point2D bestSoFar, Node n) {
        if (p == null || n == null) {
            return bestSoFar;
        }
        
        // Check if current point is closer than best so far
        double currentDistSq = p.distanceSquaredTo(n.point);
        double bestDistSq = bestSoFar == null ? Double.POSITIVE_INFINITY : p.distanceSquaredTo(bestSoFar);
        
        if (currentDistSq < bestDistSq) {
            bestSoFar = n.point;
        }
        
        /* If this node's rectangle cannot contain a closer point than what we've found,
         * we can prune this entire subtree */
        if (bestSoFar != null) {
            bestDistSq = p.distanceSquaredTo(bestSoFar);
        }
        
        // Determine which subtree to search first based on the splitting dimension
        Node first = null;
        Node second = null;
        
        if (n.vertical) {
            // For vertical splits, compare x-coordinates
            if (p.x() < n.point.x()) {
                first = n.left;
                second = n.right;
            } else {
                first = n.right;
                second = n.left;
            }
        } else {
            // For horizontal splits, compare y-coordinates
            if (p.y() < n.point.y()) {
                first = n.left;
                second = n.right;
            } else {
                first = n.right;
                second = n.left;
            }
        }
        
        // Search the first subtree if it exists and could contain a closer point
        if (first != null) {
            // Only search if the rectangle could contain a closer point
            double bestDistSqFirst = p.distanceSquaredTo(bestSoFar);
            double distToFirstRectSq = first.rect.distanceSquaredTo(p);
            
            if (distToFirstRectSq <= bestDistSqFirst) {
                bestSoFar = nearestPruning(p, bestSoFar, first);
            }
        }
        
        // Only search the second subtree if it could contain a closer point
        if (second != null) {
            double bestDistSqSecond = p.distanceSquaredTo(bestSoFar);
            double distToSecondRectSq = second.rect.distanceSquaredTo(p);
            
            if (distToSecondRectSq <= bestDistSqSecond) {
                bestSoFar = nearestPruning(p, bestSoFar, second);
            }
        }
        
        return bestSoFar;
    }
}
