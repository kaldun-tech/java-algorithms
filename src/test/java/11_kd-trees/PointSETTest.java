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
 * Unit tests for the PointSET class.
 * Tests the functionality of the brute-force implementation of a set of points in the unit square.
 */
public class PointSETTest {

    private Object pointSet;
    private Class<?> pointSetClass;
    private Point2D p1, p2, p3, p4;
    
    /**
     * Load the PointSET class dynamically.
     */
    private Class<?> loadPointSETClass() throws Exception {
        // Get the path to the main classes directory
        File mainClassesDir = new File("../../../main/java/11_kd-trees").getCanonicalFile();
        
        // Create a class loader that can load from this directory
        URL[] urls = new URL[] { mainClassesDir.toURI().toURL() };
        URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
        
        // Load the PointSET class
        return classLoader.loadClass("PointSET");
    }

    /**
     * Set up test fixtures before each test.
     */
    @Before
    public void setUp() throws Exception {
        // Load the PointSET class
        pointSetClass = loadPointSETClass();
        
        // Create a new instance of PointSET
        Constructor<?> constructor = pointSetClass.getConstructor();
        pointSet = constructor.newInstance();
        
        // Create test points
        p1 = new Point2D(0.1, 0.1);
        p2 = new Point2D(0.5, 0.5);
        p3 = new Point2D(0.7, 0.3);
        p4 = new Point2D(0.9, 0.9);
    }

    /**
     * Test the isEmpty method for an empty set.
     */
    @Test
    public void testIsEmptyOnEmptySet() throws Exception {
        Method isEmpty = pointSetClass.getMethod("isEmpty");
        boolean result = (boolean) isEmpty.invoke(pointSet);
        assertTrue("A newly created PointSET should be empty", result);
    }

    /**
     * Test the isEmpty method for a non-empty set.
     */
    @Test
    public void testIsEmptyOnNonEmptySet() throws Exception {
        // Insert a point
        Method insert = pointSetClass.getMethod("insert", Point2D.class);
        insert.invoke(pointSet, p1);
        
        // Check if it's empty
        Method isEmpty = pointSetClass.getMethod("isEmpty");
        boolean result = (boolean) isEmpty.invoke(pointSet);
        assertFalse("PointSET with an inserted point should not be empty", result);
    }

    /**
     * Test the size method for an empty set.
     */
    @Test
    public void testSizeOnEmptySet() throws Exception {
        Method size = pointSetClass.getMethod("size");
        int result = (int) size.invoke(pointSet);
        assertEquals("Size of an empty PointSET should be 0", 0, result);
    }

    /**
     * Test the size method after inserting points.
     */
    @Test
    public void testSizeAfterInsertions() throws Exception {
        Method insert = pointSetClass.getMethod("insert", Point2D.class);
        Method size = pointSetClass.getMethod("size");
        
        // Insert one point
        insert.invoke(pointSet, p1);
        int result1 = (int) size.invoke(pointSet);
        assertEquals("Size after one insertion should be 1", 1, result1);
        
        // Insert two more points
        insert.invoke(pointSet, p2);
        insert.invoke(pointSet, p3);
        int result2 = (int) size.invoke(pointSet);
        assertEquals("Size after three insertions should be 3", 3, result2);
        
        // Insert a duplicate point - size should not change
        insert.invoke(pointSet, p1);
        int result3 = (int) size.invoke(pointSet);
        assertEquals("Size should not change after inserting a duplicate point", 3, result3);
    }

    /**
     * Test the contains method.
     */
    @Test
    public void testContains() throws Exception {
        Method insert = pointSetClass.getMethod("insert", Point2D.class);
        Method contains = pointSetClass.getMethod("contains", Point2D.class);
        
        // Check empty set
        boolean result1 = (boolean) contains.invoke(pointSet, p1);
        assertFalse("Empty set should not contain any point", result1);
        
        // Insert points and check contains
        insert.invoke(pointSet, p1);
        insert.invoke(pointSet, p2);
        
        boolean result2 = (boolean) contains.invoke(pointSet, p1);
        boolean result3 = (boolean) contains.invoke(pointSet, p2);
        boolean result4 = (boolean) contains.invoke(pointSet, p3);
        
        assertTrue("Set should contain inserted point p1", result2);
        assertTrue("Set should contain inserted point p2", result3);
        assertFalse("Set should not contain point p3 that wasn't inserted", result4);
    }

    /**
     * Common setup for range tests.
     * Inserts test points into the PointSET and returns the range method.
     */
    private Method setupRangeTest() throws Exception {
        Method insert = pointSetClass.getMethod("insert", Point2D.class);
        Method range = pointSetClass.getMethod("range", RectHV.class);
        
        // Insert several points
        insert.invoke(pointSet, p1); // (0.1, 0.1)
        insert.invoke(pointSet, p2); // (0.5, 0.5)
        insert.invoke(pointSet, p3); // (0.7, 0.3)
        insert.invoke(pointSet, p4); // (0.9, 0.9)
        
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
        Iterable<?> rangeResult = (Iterable<?>) range.invoke(pointSet, rect);
        List<Point2D> points = toList(rangeResult);
        
        assertEquals("Rectangle covering the unit square should contain all 4 points", 4, points.size());
        assertTrue(points.contains(p1));
        assertTrue(points.contains(p2));
        assertTrue(points.contains(p3));
        assertTrue(points.contains(p4));
    }
    
    /**
     * Test the range method with a rectangle that contains some points.
     */
    @Test
    public void testRangePartialRectangle() throws Exception {
        Method range = setupRangeTest();
        
        // Test with a rectangle that contains some points
        RectHV rect = new RectHV(0.0, 0.0, 0.6, 0.6);
        Iterable<?> rangeResult = (Iterable<?>) range.invoke(pointSet, rect);
        List<Point2D> points = toList(rangeResult);
        
        assertEquals("Rectangle should contain 2 points", 2, points.size());
        assertTrue(points.contains(p1));
        assertTrue(points.contains(p2));
        assertFalse(points.contains(p3));
        assertFalse(points.contains(p4));
    }
    
    /**
     * Test the range method with a rectangle that contains no points.
     */
    @Test
    public void testRangeEmptyRectangle() throws Exception {
        Method range = setupRangeTest();
        
        // Test with a rectangle that contains no points
        RectHV rect = new RectHV(0.2, 0.6, 0.4, 0.8);
        Iterable<?> rangeResult = (Iterable<?>) range.invoke(pointSet, rect);
        List<Point2D> points = toList(rangeResult);
        
        assertEquals("Rectangle should contain no points", 0, points.size());
    }

    /**
     * Test the nearest method.
     */
    @Test
    public void testNearest() throws Exception {
        Method insert = pointSetClass.getMethod("insert", Point2D.class);
        Method nearest = pointSetClass.getMethod("nearest", Point2D.class);
        
        // Test with an empty set
        Point2D result1 = (Point2D) nearest.invoke(pointSet, new Point2D(0.5, 0.5));
        assertNull("Nearest on empty set should return null", result1);
        
        // Insert several points
        insert.invoke(pointSet, p1); // (0.1, 0.1)
        insert.invoke(pointSet, p2); // (0.5, 0.5)
        insert.invoke(pointSet, p3); // (0.7, 0.3)
        insert.invoke(pointSet, p4); // (0.9, 0.9)
        
        // Test nearest to various query points
        Point2D result2 = (Point2D) nearest.invoke(pointSet, new Point2D(0.1, 0.1));
        Point2D result3 = (Point2D) nearest.invoke(pointSet, new Point2D(0.4, 0.4));
        Point2D result4 = (Point2D) nearest.invoke(pointSet, new Point2D(0.8, 0.3));
        Point2D result5 = (Point2D) nearest.invoke(pointSet, new Point2D(0.8, 0.8));
        
        assertEquals("Nearest to (0.1, 0.1) should be p1", p1, result2);
        assertEquals("Nearest to (0.4, 0.4) should be p2", p2, result3);
        assertEquals("Nearest to (0.8, 0.3) should be p3", p3, result4);
        assertEquals("Nearest to (0.8, 0.8) should be p4", p4, result5);
    }

    /**
     * Test for null argument handling.
     */
    @Test(expected = Exception.class)
    public void testInsertNullPoint() throws Exception {
        Method insert = pointSetClass.getMethod("insert", Point2D.class);
        try {
            insert.invoke(pointSet, (Point2D) null);
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
        Method contains = pointSetClass.getMethod("contains", Point2D.class);
        try {
            contains.invoke(pointSet, (Point2D) null);
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
        Method range = pointSetClass.getMethod("range", RectHV.class);
        try {
            range.invoke(pointSet, (RectHV) null);
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
        Method nearest = pointSetClass.getMethod("nearest", Point2D.class);
        try {
            nearest.invoke(pointSet, (Point2D) null);
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
