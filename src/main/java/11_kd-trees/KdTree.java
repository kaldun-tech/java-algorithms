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
     * Gets the rectangle to add to a node which splits the parent's rectangle based on level
     * @param toAdd
     * @param parentRect
     * @param nextVertical is the node to add vertical based on its level
     * @param cmp comparison result of toAdd to parent's point
     * @return
     */
    private RectHV getSplitRect(Point2D toAdd, RectHV parentRect, boolean nextVertical, int cmp) {
        double xmin, xmax, ymin, ymax, splitVal;
        if (nextVertical) {
            // For vertical rectangle inherit y values from parent
            ymin = parentRect.ymin();
            ymax = parentRect.ymax();
            // Subdivide x values by splitting parent width
            splitVal = parentRect.xmin() + parentRect.width() / 2;
            if (cmp < 0) {
                // Left rectangle
                xmin = parentRect.xmin();
                xmax = splitVal;
            } else {
                // Right rectangle
                xmin = splitVal;
                xmax = parentRect.xmax();
            }
        } else {
            // For horizontal inherit x values from parent
            xmin = parentRect.xmin();
            xmax = parentRect.ymax();
            // Subdivide y values by parent height
            splitVal = parentRect.ymin() + parentRect.height() / 2;
            if (cmp < 0) {
                // Lower rectangle
                ymin = parentRect.xmin();
                ymax = splitVal;
            } else {
                // Upper rectangle
                ymin = splitVal;
                ymax = parentRect.xmax();
            }
        }

        return new RectHV(xmin, ymin, xmax, ymax);
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
        RectHV r = getSplitRect(p, n.rect, nextVertical, cmp);

        if (cmp < 0 && n.left == null) {
            ++size;
            n.left = new Node(p, r, nextVertical);
        } else if (cmp < 0) {
            // n.left != null -> go left
            doInsert(p, n.left);
        } else if (n.right == null) {
            ++size;
            n.right = new Node(p, r, nextVertical);
        } else {
            // Go right
            doInsert(p, n.right);
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
        } else if (n == null || !rect.intersects(n.rect)) {
            // No reason to explore a node with non-intersecting rectangle
            return;
        }

        Point2D p = n.point;
        if (rect.contains(p)) {
            result.add(p);
        }

        // Explore children if relevant
        if (n.left != null && rect.intersects(n.left.rect)) {
            doRange(rect, n.left, result);
        }
        if (n.right != null && rect.intersects(n.right.rect)) {
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
        } else  if (isEmpty()) {
            return null;
        }

        return nearestPruning(p, root.point, root);
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
     * @param p
     * @param bestSoFar
     * @param n
     * @return
     */
    private Point2D nearestPruning(Point2D p, Point2D bestSoFar, Node n) {
        if (p == null || n == null) {
            // Should not happen, bad inputs
            return null;
        }

        if (bestSoFar == null) {
            // Base case for root node
            bestSoFar = n.point;
        } else {
            Comparator<Point2D> distanceOrder = p.distanceToOrder();
            int cmp = distanceOrder.compare(bestSoFar, n.point);
            // Greater than zero indicates n.point is closer than best so far
            if (0 < cmp) {
                bestSoFar = n.point;
            }
        }

        /* Always choose subtree on the same side of splitting line as the query
         * point as first subtree to explore */
        if (n.left != null && n.right != null) {
            Node firstSubtree = getSameSideSubtree(p, n);
            Node secondSubtree = (firstSubtree == n.left) ? n.right : n.left;

            bestSoFar = nearestPruning(p, bestSoFar, firstSubtree);
            bestSoFar = nearestPruning(p, bestSoFar, secondSubtree);
        } else if (n.left != null) {
            bestSoFar = nearestPruning(p, bestSoFar, n.left);
        } else if (n.right != null) {
            bestSoFar = nearestPruning(p, bestSoFar, n.right);
        }

        return bestSoFar;
    }

    private Node getSameSideSubtree(Point2D p, Node n) {
        if (p == null || n == null || n.left == null || n.right == null) {
            // Should not happen, bad inputs
            return null;
        }

        return (n.left.rect.contains(p)) ? n.left : n.right;
    }
}
