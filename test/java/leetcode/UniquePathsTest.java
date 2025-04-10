package leetcode;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UniquePathsTest {

    private UniquePaths uniquePaths;

    @Before
    public void setUp() {
        uniquePaths = new UniquePaths();
    }

    @Test
    public void testExample1() {
        int[][] obstacleGrid = {{0,0,0},{0,1,0},{0,0,0}};
        assertEquals("Test Case 1: Standard 3x3 with center obstacle", 2, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testExample2() {
        int[][] obstacleGrid = {{0,1},{0,0}};
        assertEquals("Test Case 2: 2x2 with obstacle", 1, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testNoObstacles() {
        int[][] obstacleGrid = {{0,0,0},{0,0,0},{0,0,0}};
        assertEquals("Test Case 3: 3x3 with no obstacles", 6, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testObstacleAtStart() {
        int[][] obstacleGrid = {{1,0},{0,0}};
        assertEquals("Test Case 4: Obstacle at start", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testObstacleAtEnd() {
        int[][] obstacleGrid = {{0,0},{0,1}};
        assertEquals("Test Case 5: Obstacle at end", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testBlockedPath() {
        int[][] obstacleGrid = {{0,0},{1,0}}; // Obstacle completely blocks path
        assertEquals("Test Case 6: Path completely blocked", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
        
        int[][] obstacleGrid2 = {{0,1},{0,0}}; 
         assertEquals("Test Case 6b: Path blocked variant", 1, uniquePaths.uniquePathsWithObstacles(obstacleGrid2)); // From Example 2

         int[][] obstacleGrid3 = {{0,0,0},{0,1,0},{0,0,0}};
         assertEquals("Test Case 6c: Path blocked variant 2", 2, uniquePaths.uniquePathsWithObstacles(obstacleGrid3)); // From Example 1
    }

    @Test
    public void testFirstRowBlocked() {
        int[][] obstacleGrid = {{0,1,0},{0,0,0},{0,0,0}};
        assertEquals("Test Case 7: First row blocked", 3, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }
    
    @Test
    public void testFirstColBlocked() {
         int[][] obstacleGrid = {{0,0,0},{1,0,0},{0,0,0}};
         assertEquals("Test Case 8: First column blocked", 3, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testSingleCellNoObstacle() {
        int[][] obstacleGrid = {{0}};
        assertEquals("Test Case 9: Single cell, no obstacle", 1, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testSingleCellObstacle() {
        int[][] obstacleGrid = {{1}};
        assertEquals("Test Case 10: Single cell, with obstacle", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }
    
    @Test
    public void testEmptyGrid() {
        int[][] obstacleGrid1 = {};
        assertEquals("Test Case 11a: Empty grid (0 rows)", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid1));
        
        int[][] obstacleGrid2 = {{}}; // Represents a grid with 1 row and 0 columns effectively
        // Depending on how 0-column grids are handled, this might need adjustment
        // The current code checks obstacleGrid[0].length == 0, so it should return 0
         assertEquals("Test Case 11b: Empty grid (1 row, 0 columns)", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid2));
    }

     @Test
    public void testNullGrid() {
        assertEquals("Test Case 12: Null grid", 0, uniquePaths.uniquePathsWithObstacles(null));
    }

    @Test
    public void testLargeGridNoObstacles() {
        // Create a 5x5 grid with no obstacles
        int[][] obstacleGrid = new int[5][5]; 
        // Expected paths for 5x5 grid is C(5+5-2, 5-1) = C(8, 4) = 70
        assertEquals("Test Case 13: Larger 5x5 grid, no obstacles", 70, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }

    @Test
    public void testLargeGridWithObstacles() {
        int[][] obstacleGrid = {
            {0,0,0,0,0},
            {0,1,0,1,0},
            {0,0,0,0,0},
            {0,1,0,0,0},
            {0,0,0,0,0}
        };
        // Manually calculate or derive expected value. Let's trace:
        // dp[0] = [1, 1, 1, 1, 1]
        // dp[1] = [1, 0, 1, 0, 1]
        // dp[2] = [1, 1, 2, 2, 3]
        // dp[3] = [1, 0, 2, 4, 7]
        // dp[4] = [1, 1, 3, 7, 14]
        assertEquals("Test Case 14: Larger 5x5 grid with obstacles", 14, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
    }
     @Test
    public void testRowVector() {
        int[][] obstacleGrid = {{0, 0, 0, 1, 0}};
        assertEquals("Test Case 15: Row vector with obstacle", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
         int[][] obstacleGrid2 = {{0, 0, 0, 0, 0}};
        assertEquals("Test Case 15b: Row vector no obstacle", 1, uniquePaths.uniquePathsWithObstacles(obstacleGrid2));
    }

     @Test
    public void testColumnVector() {
         int[][] obstacleGrid = {{0}, {0}, {1}, {0}, {0}};
         assertEquals("Test Case 16: Column vector with obstacle", 0, uniquePaths.uniquePathsWithObstacles(obstacleGrid));
         int[][] obstacleGrid2 = {{0}, {0}, {0}, {0}, {0}};
        assertEquals("Test Case 16b: Column vector no obstacle", 1, uniquePaths.uniquePathsWithObstacles(obstacleGrid2));
    }
}
