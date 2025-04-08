package leetcode;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

/**
 * Unit tests for the CityFlightsSearch class.
 * Tests the functionality of finding all possible flight paths between cities.
 */
public class CityFlightsSearchTest {
    
    private CityFlightsSearch flightSearch;
    
    /**
     * Sets up a flight network for testing.
     * The network consists of flights between:
     * - Atlanta to Boston and Charlotte
     * - Boston to Denver
     * - Charlotte to Denver
     * - Denver to New York
     */
    @Before
    public void setUp() {
        List<String> flights = new ArrayList<>();
        flights.add("Atlanta-Boston");
        flights.add("Atlanta-Charlotte");
        flights.add("Boston-Denver");
        flights.add("Charlotte-Denver");
        flights.add("Denver-New York");
        
        flightSearch = new CityFlightsSearch(flights);
    }
    
    /**
     * Tests finding paths from Atlanta to New York.
     * There should be two paths:
     * 1. Atlanta -> Boston -> Denver -> New York
     * 2. Atlanta -> Charlotte -> Denver -> New York
     */
    @Test
    public void testFindAllPathsAtlantaToNewYork() {
        List<List<String>> paths = flightSearch.findAllPaths("Atlanta", "New York");
        
        // There should be exactly 2 paths
        assertEquals(2, paths.size());
        
        // Check first path: Atlanta -> Boston -> Denver -> New York
        List<String> expectedPath1 = Arrays.asList("Atlanta", "Boston", "Denver", "New York");
        
        // Check second path: Atlanta -> Charlotte -> Denver -> New York
        List<String> expectedPath2 = Arrays.asList("Atlanta", "Charlotte", "Denver", "New York");
        
        // Verify both expected paths are in the result (order might vary)
        assertTrue(paths.contains(expectedPath1));
        assertTrue(paths.contains(expectedPath2));
    }
    
    /**
     * Tests finding paths from Boston to New York.
     * There should be one path: Boston -> Denver -> New York
     */
    @Test
    public void testFindAllPathsBostonToNewYork() {
        List<List<String>> paths = flightSearch.findAllPaths("Boston", "New York");
        
        // There should be exactly 1 path
        assertEquals(1, paths.size());
        
        // Check the path: Boston -> Denver -> New York
        List<String> expectedPath = Arrays.asList("Boston", "Denver", "New York");
        assertEquals(expectedPath, paths.get(0));
    }
    
    /**
     * Tests finding paths from Charlotte to Boston.
     * There should be no paths since there are no flights from Charlotte to Boston.
     */
    @Test
    public void testFindAllPathsNoPath() {
        List<List<String>> paths = flightSearch.findAllPaths("Charlotte", "Boston");
        
        // There should be no paths
        assertTrue(paths.isEmpty());
    }
    
    /**
     * Tests finding paths from a city to itself.
     * Should return a single path containing just that city.
     */
    @Test
    public void testFindAllPathsSameCity() {
        List<List<String>> paths = flightSearch.findAllPaths("Atlanta", "Atlanta");
        
        // There should be exactly 1 path
        assertEquals(1, paths.size());
        
        // The path should just be [Atlanta]
        List<String> expectedPath = Collections.singletonList("Atlanta");
        assertEquals(expectedPath, paths.get(0));
    }
    
    /**
     * Tests that an exception is thrown when the start city doesn't exist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindAllPathsInvalidStartCity() {
        flightSearch.findAllPaths("InvalidCity", "New York");
    }
    
    /**
     * Tests that an exception is thrown when the end city doesn't exist.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindAllPathsInvalidEndCity() {
        flightSearch.findAllPaths("Atlanta", "InvalidCity");
    }
    
    /**
     * Tests that an exception is thrown when null is passed as the start city.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindAllPathsNullStartCity() {
        flightSearch.findAllPaths(null, "New York");
    }
    
    /**
     * Tests that an exception is thrown when null is passed as the end city.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindAllPathsNullEndCity() {
        flightSearch.findAllPaths("Atlanta", null);
    }
    
    /**
     * Tests a more complex flight network with multiple possible paths.
     */
    @Test
    public void testFindAllPathsComplexNetwork() {
        List<String> flights = new ArrayList<>();
        flights.add("A-B");
        flights.add("A-C");
        flights.add("B-D");
        flights.add("C-D");
        flights.add("B-E");
        flights.add("C-E");
        flights.add("D-F");
        flights.add("E-F");
        
        CityFlightsSearch complexSearch = new CityFlightsSearch(flights);
        List<List<String>> paths = complexSearch.findAllPaths("A", "F");
        
        // There should be 4 paths:
        // A -> B -> D -> F
        // A -> B -> E -> F
        // A -> C -> D -> F
        // A -> C -> E -> F
        assertEquals(4, paths.size());
        
        // Check all expected paths are in the result
        List<String> path1 = Arrays.asList("A", "B", "D", "F");
        List<String> path2 = Arrays.asList("A", "B", "E", "F");
        List<String> path3 = Arrays.asList("A", "C", "D", "F");
        List<String> path4 = Arrays.asList("A", "C", "E", "F");
        
        assertTrue(paths.contains(path1));
        assertTrue(paths.contains(path2));
        assertTrue(paths.contains(path3));
        assertTrue(paths.contains(path4));
    }
}
