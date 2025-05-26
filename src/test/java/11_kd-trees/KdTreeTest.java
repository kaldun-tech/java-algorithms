import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the KdTree class.
 * Tests the functionality of the 2d-tree implementation of a set of points in the unit square.
 */
public class KdTreeTest {

    private Object kdTree;
    private Class<?> kdTreeClass;
    private Point2D p1, p2, p3, p4, p5;
    
    /**
     * Load the KdTree class dynamically.
     */
    private Class<?> loadKdTreeClass() throws Exception {
        // Get the path to the main classes directory
        File mainClassesDir = new File("../../../main/java/11_kd-trees").getCanonicalFile();
        
        // Create a class loader that can load from this directory
        URL[] urls = new URL[] { mainClassesDir.toURI().toURL() };
        URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
        
        // Load the KdTree class
        return classLoader.loadClass("KdTree");
    }

    /**
     * Set up test fixtures before each test.
     */
    @Before
    public void setUp() throws Exception {
        // Load the KdTree class
        kdTreeClass = loadKdTreeClass();
        
        // Create a new instance of KdTree
        Constructor<?> constructor = kdTreeClass.getConstructor();
        kdTree = constructor.newInstance();
        
        // Create test points
        p1 = new Point2D(0.1, 0.1); // bottom-left
        p2 = new Point2D(0.5, 0.5); // middle
        p3 = new Point2D(0.7, 0.3); // right-middle
        p4 = new Point2D(0.9, 0.9); // top-right
        p5 = new Point2D(0.2, 0.8); // top-left
    }

    /**
     * Test the isEmpty method for an empty tree.
     */
    @Test
    public void testIsEmptyOnEmptyTree() throws Exception {
        Method isEmpty = kdTreeClass.getMethod("isEmpty");
        boolean result = (boolean) isEmpty.invoke(kdTree);
        assertTrue("A newly created KdTree should be empty", result);
    }

    /**
     * Test the isEmpty method for a non-empty tree.
     */
    @Test
    public void testIsEmptyOnNonEmptyTree() throws Exception {
        // Insert a point
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        insert.invoke(kdTree, p1);
        
        // Check if it's empty
        Method isEmpty = kdTreeClass.getMethod("isEmpty");
        boolean result = (boolean) isEmpty.invoke(kdTree);
        assertFalse("KdTree with an inserted point should not be empty", result);
    }

    /**
     * Test the size method for an empty tree.
     */
    @Test
    public void testSizeOnEmptyTree() throws Exception {
        Method size = kdTreeClass.getMethod("size");
        int result = (int) size.invoke(kdTree);
        assertEquals("Size of an empty KdTree should be 0", 0, result);
    }

    /**
     * Test the size method after inserting points.
     */
    @Test
    public void testSizeAfterInsertions() throws Exception {
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        Method size = kdTreeClass.getMethod("size");
        
        // Insert one point
        insert.invoke(kdTree, p1);
        int result1 = (int) size.invoke(kdTree);
        assertEquals("Size after one insertion should be 1", 1, result1);
        
        // Insert two more points
        insert.invoke(kdTree, p2);
        insert.invoke(kdTree, p3);
        int result2 = (int) size.invoke(kdTree);
        assertEquals("Size after three insertions should be 3", 3, result2);
        
        // Insert a duplicate point - size should not change
        insert.invoke(kdTree, p1);
        int result3 = (int) size.invoke(kdTree);
        assertEquals("Size should not change after inserting a duplicate point", 3, result3);
    }

    /**
     * Test the contains method.
     */
    @Test
    public void testContains() throws Exception {
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        Method contains = kdTreeClass.getMethod("contains", Point2D.class);
        
        // Check empty tree
        boolean result1 = (boolean) contains.invoke(kdTree, p1);
        assertFalse("Empty tree should not contain any point", result1);
        
        // Insert points and check contains
        insert.invoke(kdTree, p1);
        insert.invoke(kdTree, p2);
        
        boolean result2 = (boolean) contains.invoke(kdTree, p1);
        boolean result3 = (boolean) contains.invoke(kdTree, p2);
        boolean result4 = (boolean) contains.invoke(kdTree, p3);
        
        assertTrue("Tree should contain inserted point p1", result2);
        assertTrue("Tree should contain inserted point p2", result3);
        assertFalse("Tree should not contain point p3 that wasn't inserted", result4);
    }

    /**
     * Common setup for range tests.
     * Inserts test points into the KdTree and returns the range method.
     */
    private Method setupRangeTest() throws Exception {
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        Method range = kdTreeClass.getMethod("range", RectHV.class);
        
        // Insert several points
        insert.invoke(kdTree, p1); // (0.1, 0.1)
        insert.invoke(kdTree, p2); // (0.5, 0.5)
        insert.invoke(kdTree, p3); // (0.7, 0.3)
        insert.invoke(kdTree, p4); // (0.9, 0.9)
        insert.invoke(kdTree, p5); // (0.2, 0.8)
        
        return range;
    }
    
    /**
     * Test the range method with a rectangle that contains all points.
     */
    @Test
    public void testRangeFullRectangle() throws Exception {
        Method range = setupRangeTest();
        
        // Test with a rectangle that contains all points
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        Iterable<?> rangeResult = (Iterable<?>) range.invoke(kdTree, rect);
        List<Point2D> points = toList(rangeResult);
        
        assertEquals("Rectangle covering the unit square should contain all 5 points", 5, points.size());
        assertTrue(points.contains(p1));
        assertTrue(points.contains(p2));
        assertTrue(points.contains(p3));
        assertTrue(points.contains(p4));
        assertTrue(points.contains(p5));
    }
    
    /**
     * Test the range method with a rectangle that contains some points.
     */
    @Test
    public void testRangePartialRectangle() throws Exception {
        Method range = setupRangeTest();
        
        // Test with a rectangle that contains some points
        RectHV rect = new RectHV(0.0, 0.0, 0.6, 0.6);
        Iterable<?> rangeResult = (Iterable<?>) range.invoke(kdTree, rect);
        List<Point2D> points = toList(rangeResult);
        
        assertEquals("Rectangle should contain 2 points", 2, points.size());
        assertTrue(points.contains(p1));
        assertTrue(points.contains(p2));
        assertFalse(points.contains(p3));
        assertFalse(points.contains(p4));
        assertFalse(points.contains(p5));
        
        // Test with a rectangle in the top-left quadrant
        RectHV rect2 = new RectHV(0.0, 0.7, 0.3, 1.0);
        Iterable<?> rangeResult2 = (Iterable<?>) range.invoke(kdTree, rect2);
        List<Point2D> points2 = toList(rangeResult2);
        
        assertEquals("Rectangle should contain 1 point", 1, points2.size());
        assertTrue(points2.contains(p5));
        assertFalse(points2.contains(p2));
    }
    
    /**
     * Test the range method with a rectangle that contains no points.
     */
    @Test
    public void testRangeEmptyRectangle() throws Exception {
        Method range = setupRangeTest();
        
        // Test with a rectangle that contains no points
        RectHV rect = new RectHV(0.3, 0.6, 0.4, 0.7);
        Iterable<?> rangeResult = (Iterable<?>) range.invoke(kdTree, rect);
        List<Point2D> points = toList(rangeResult);
        
        assertEquals("Rectangle should contain no points", 0, points.size());
    }

    /**
     * Test the nearest method with various query points.
     */
    @Test
    public void testNearest() throws Exception {
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        Method nearest = kdTreeClass.getMethod("nearest", Point2D.class);
        
        // Test with an empty tree
        Point2D result1 = (Point2D) nearest.invoke(kdTree, new Point2D(0.5, 0.5));
        assertNull("Nearest on empty tree should return null", result1);
        
        // Insert several points
        insert.invoke(kdTree, p1); // (0.1, 0.1)
        insert.invoke(kdTree, p2); // (0.5, 0.5)
        insert.invoke(kdTree, p3); // (0.7, 0.3)
        insert.invoke(kdTree, p4); // (0.9, 0.9)
        insert.invoke(kdTree, p5); // (0.2, 0.8)
        
        // Test nearest to various query points
        Point2D result2 = (Point2D) nearest.invoke(kdTree, new Point2D(0.1, 0.1));
        Point2D result3 = (Point2D) nearest.invoke(kdTree, new Point2D(0.4, 0.4));
        Point2D result4 = (Point2D) nearest.invoke(kdTree, new Point2D(0.8, 0.3));
        Point2D result5 = (Point2D) nearest.invoke(kdTree, new Point2D(0.8, 0.8));
        Point2D result6 = (Point2D) nearest.invoke(kdTree, new Point2D(0.2, 0.9));
        
        assertEquals("Nearest to (0.1, 0.1) should be p1", p1, result2);
        assertEquals("Nearest to (0.4, 0.4) should be p2", p2, result3);
        assertEquals("Nearest to (0.8, 0.3) should be p3", p3, result4);
        assertEquals("Nearest to (0.8, 0.8) should be p4", p4, result5);
        assertEquals("Nearest to (0.2, 0.9) should be p5", p5, result6);
    }

    /**
     * Test a more complex nearest neighbor scenario.
     */
    @Test
    public void testNearestWithComplexTree() throws Exception {
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        Method nearest = kdTreeClass.getMethod("nearest", Point2D.class);
        
        // Create a more complex tree with more points
        for (double x = 0.1; x < 1.0; x += 0.2) {
            for (double y = 0.1; y < 1.0; y += 0.2) {
                insert.invoke(kdTree, new Point2D(x, y));
            }
        }
        
        // Test a point that should be closest to (0.3, 0.3)
        Point2D expected = new Point2D(0.3, 0.3);
        Point2D query = new Point2D(0.31, 0.29);
        Point2D nearest1 = (Point2D) nearest.invoke(kdTree, query);
        
        // Use a small epsilon to account for floating-point precision issues
        double epsilon = 1e-10;
        double xDiff = Math.abs(expected.x() - nearest1.x());
        double yDiff = Math.abs(expected.y() - nearest1.y());
        assertTrue("Nearest point to (0.31, 0.29) should be close to (0.3, 0.3)", 
                  xDiff < epsilon && yDiff < epsilon);
        
        // Verify that the nearest point is indeed closer than any other point in the tree
        double nearestDist = query.distanceSquaredTo(nearest1);
        for (double x = 0.1; x < 1.0; x += 0.2) {
            for (double y = 0.1; y < 1.0; y += 0.2) {
                Point2D p = new Point2D(x, y);
                if (!p.equals(nearest1)) {
                    double dist = query.distanceSquaredTo(p);
                    assertTrue("Found a point closer than the reported nearest", nearestDist <= dist);
                }
            }
        }
    }

    /**
     * Test for null argument handling.
     */
    @Test(expected = Exception.class)
    public void testInsertNullPoint() throws Exception {
        Method insert = kdTreeClass.getMethod("insert", Point2D.class);
        try {
            insert.invoke(kdTree, (Point2D) null);
        } catch (Exception e) {
            // We expect an InvocationTargetException wrapping an IllegalArgumentException
            if (e.getCause() instanceof IllegalArgumentException) {
                throw new IllegalArgumentException();
            }
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void testContainsNullPoint() throws Exception {
        Method contains = kdTreeClass.getMethod("contains", Point2D.class);
        try {
            contains.invoke(kdTree, (Point2D) null);
        } catch (Exception e) {
            // We expect an InvocationTargetException wrapping an IllegalArgumentException
            if (e.getCause() instanceof IllegalArgumentException) {
                throw new IllegalArgumentException();
            }
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void testRangeNullRectangle() throws Exception {
        Method range = kdTreeClass.getMethod("range", RectHV.class);
        try {
            range.invoke(kdTree, (RectHV) null);
        } catch (Exception e) {
            // We expect an InvocationTargetException wrapping an IllegalArgumentException
            if (e.getCause() instanceof IllegalArgumentException) {
                throw new IllegalArgumentException();
            }
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void testNearestNullPoint() throws Exception {
        Method nearest = kdTreeClass.getMethod("nearest", Point2D.class);
        try {
            nearest.invoke(kdTree, (Point2D) null);
        } catch (Exception e) {
            // We expect an InvocationTargetException wrapping an IllegalArgumentException
            if (e.getCause() instanceof IllegalArgumentException) {
                throw new IllegalArgumentException();
            }
            throw e;
        }
    }

    /**
     * Helper method to convert an Iterable to a List for easier testing.
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> toList(Iterable<?> iterable) {
        List<T> list = new ArrayList<>();
        for (Object item : iterable) {
            list.add((T) item);
        }
        return list;
    }
}
