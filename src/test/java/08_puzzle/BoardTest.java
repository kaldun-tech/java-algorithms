// No package declaration, as Board.java is in the default package

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

/**
 * Unit tests for the Board class.
 * 
 * Tests all public methods of the Board class including:
 * - dimension()
 * - hamming()
 * - manhattan()
 * - isGoal()
 * - equals()
 * - neighbors()
 * - twin()
 * - toString()
 */
public class BoardTest {
    
    // Class reference for Board obtained via reflection
    private Class<?> boardClass;
    
    /**
     * Set up the test by loading the Board class via reflection.
     */
    @Before
    public void setUp() throws Exception {
        // Load the Board class using reflection
        boardClass = Class.forName("Board");
    }
    
    /**
     * Helper method to create a Board instance using reflection.
     * 
     * @param tiles The 2D array of tiles to create the board with
     * @return A Board instance
     */
    private Object createBoard(int[][] tiles) {
        try {
            Constructor<?> constructor = boardClass.getConstructor(int[][].class);
            return constructor.newInstance((Object) tiles);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Board instance", e);
        }
    }
    
    /**
     * Helper method to call a method on a Board instance using reflection.
     * 
     * @param board The Board instance
     * @param methodName The name of the method to call
     * @param paramTypes The parameter types of the method
     * @param params The parameters to pass to the method
     * @return The result of the method call
     */
    private Object callMethod(Object board, String methodName, Class<?>[] paramTypes, Object[] params) {
        try {
            Method method = board.getClass().getMethod(methodName, paramTypes);
            return method.invoke(board, params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call method " + methodName, e);
        }
    }
    
    /**
     * Helper method to get a tile value from a Board instance.
     */
    private int getTileValue(Object board, int row, int col) {
        try {
            // We'll use toString and parse the result to get the tile value
            String boardString = (String) callMethod(board, "toString", new Class<?>[]{}, new Object[]{});
            String[] lines = boardString.trim().split("\n");
            String[] values = lines[row].trim().split("\\s+");
            return Integer.parseInt(values[col]);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get tile value", e);
        }
    }

    /**
     * Test the dimension() method.
     */
    @Test
    public void testDimension() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object result = callMethod(board, "dimension", new Class<?>[]{}, new Object[]{});
        assertEquals(3, result);
    }

    /**
     * Test the hamming() method with a goal board configuration.
     * A goal board should have 0 tiles out of place.
     */
    @Test
    public void testHammingGoalBoard() {
        // Goal board - 0 tiles out of place
        int[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object goalBoard = createBoard(goalTiles);
        Object goalResult = callMethod(goalBoard, "hamming", new Class<?>[]{}, new Object[]{});
        assertEquals("Goal board should have 0 tiles out of place", 0, goalResult);
    }
    
    /**
     * Test the hamming() method with a board that has some tiles out of place.
     */
    @Test
    public void testHammingPartiallyMisplaced() {
        // Some tiles out of place
        int[][] partialTiles = {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        Object partialBoard = createBoard(partialTiles);
        Object partialResult = callMethod(partialBoard, "hamming", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should have 2 tiles out of place", 2, partialResult);
    }
    
    /**
     * Test the hamming() method with a board that has all tiles out of place.
     */
    @Test
    public void testHammingAllMisplaced() {
        // All tiles out of place
        int[][] allMisplacedTiles = {{8, 7, 6}, {5, 4, 3}, {2, 1, 0}};
        Object allMisplacedBoard = createBoard(allMisplacedTiles);
        Object allMisplacedResult = callMethod(allMisplacedBoard, "hamming", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should have 8 tiles out of place", 8, allMisplacedResult);
    }
    
    /**
     * Test the manhattan() method with a goal board configuration.
     * A goal board should have 0 Manhattan distance.
     */
    @Test
    public void testManhattanGoalBoard() {
        // Goal board - 0 Manhattan distance
        int[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object goalBoard = createBoard(goalTiles);
        Object goalResult = callMethod(goalBoard, "manhattan", new Class<?>[]{}, new Object[]{});
        assertEquals("Goal board should have 0 Manhattan distance", 0, goalResult);
    }
    
    /**
     * Test the manhattan() method with a board that has some Manhattan distance.
     */
    @Test
    public void testManhattanSomeDistance() {
        // Some Manhattan distance
        int[][] someTiles = {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        Object someBoard = createBoard(someTiles);
        Object someResult = callMethod(someBoard, "manhattan", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should have 2 Manhattan distances", 2, someResult);
    }
    
    /**
     * Test the manhattan() method with a board that has large Manhattan distance.
     */
    @Test
    public void testManhattanLargeDistance() {
        // Large Manhattan distance
        int[][] largeTiles = {{8, 7, 6}, {5, 4, 3}, {2, 1, 0}};
        Object largeBoard = createBoard(largeTiles);
        Object largeResult = callMethod(largeBoard, "manhattan", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should have 16 Manhattan distances", 16, largeResult);
    }

    /**
     * Test the isGoal() method.
     */
    @Test
    public void testIsGoal() {
        int[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object goalBoard = createBoard(goalTiles);
        Object goalResult = callMethod(goalBoard, "isGoal", new Class<?>[]{}, new Object[]{});
        assertTrue((Boolean) goalResult);

        int[][] notGoalTiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Object notGoalBoard = createBoard(notGoalTiles);
        Object notGoalResult = callMethod(notGoalBoard, "isGoal", new Class<?>[]{}, new Object[]{});
        assertFalse((Boolean) notGoalResult);
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        int[][] tiles1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board1 = createBoard(tiles1);
        Object board1Again = createBoard(tiles1);
        
        // Test equality with same board
        Object equalsSame = callMethod(board1, "equals", new Class<?>[]{Object.class}, new Object[]{board1});
        assertTrue("Board should equal itself", (Boolean) equalsSame);
        
        // Test equality with equivalent board
        Object equalsEquivalent = callMethod(board1, "equals", new Class<?>[]{Object.class}, new Object[]{board1Again});
        assertTrue("Board should equal equivalent board", (Boolean) equalsEquivalent);
        
        // Test inequality with different board
        int[][] tiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Object board2 = createBoard(tiles2);
        Object equalsDifferent = callMethod(board1, "equals", new Class<?>[]{Object.class}, new Object[]{board2});
        assertFalse("Board should not equal different board", (Boolean) equalsDifferent);
        
        // Test inequality with null
        Object equalsNull = callMethod(board1, "equals", new Class<?>[]{Object.class}, new Object[]{null});
        assertFalse("Board should not equal null", (Boolean) equalsNull);
        
        // Test inequality with different object type
        Object equalsDifferentType = callMethod(board1, "equals", new Class<?>[]{Object.class}, new Object[]{new Object()});
        assertFalse("Board should not equal different type", (Boolean) equalsDifferentType);
    }

    /**
     * Test the neighbors() method when blank is in the middle.
     * A blank in the middle should have 4 neighbors (can move in all 4 directions).
     */
    @Test
    public void testNeighborsBlankInMiddle() {
        // Blank in middle - should have 4 neighbors
        int[][] tilesMiddle = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Object boardMiddle = createBoard(tilesMiddle);
        
        Object neighborsMiddle = callMethod(boardMiddle, "neighbors", new Class<?>[]{}, new Object[]{});
        int countMiddle = 0;
        for (@SuppressWarnings("unused") Object neighbor : (Iterable<?>) neighborsMiddle) {
            countMiddle++;
        }
        assertEquals("Board with blank in middle should have 4 neighbors", 4, countMiddle);
    }

    /**
     * Test the neighbors() method when blank is in the corner.
     * A blank in the corner should have 2 neighbors (can only move in 2 directions).
     */
    @Test
    public void testNeighborsBlankInCorner() {
        // Blank in corner - should have 2 neighbors
        int[][] tilesCorner = {{0, 2, 3}, {4, 5, 6}, {7, 8, 1}};
        Object boardCorner = createBoard(tilesCorner);
        
        Object neighborsCorner = callMethod(boardCorner, "neighbors", new Class<?>[]{}, new Object[]{});
        int countCorner = 0;
        for (@SuppressWarnings("unused") Object neighbor : (Iterable<?>) neighborsCorner) {
            countCorner++;
        }
        assertEquals("Board with blank in corner should have 2 neighbors", 2, countCorner);
    }

    /**
     * Test the neighbors() method when blank is on the edge (not corner).
     * A blank on the edge should have 3 neighbors (can move in 3 directions).
     */
    @Test
    public void testNeighborsBlankOnEdge() {
        // Blank on edge - should have 3 neighbors
        int[][] tilesEdge = {{1, 0, 3}, {4, 5, 6}, {7, 8, 2}};
        Object boardEdge = createBoard(tilesEdge);
        
        Object neighborsEdge = callMethod(boardEdge, "neighbors", new Class<?>[]{}, new Object[]{});
        int countEdge = 0;
        for (@SuppressWarnings("unused") Object neighbor : (Iterable<?>) neighborsEdge) {
            countEdge++;
        }
        assertEquals("Board with blank on edge should have 3 neighbors", 3, countEdge);
    }

    /**
     * Test the twin() method.
     */
    @Test
    public void testTwin() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object twin = callMethod(board, "twin", new Class<?>[]{}, new Object[]{});
        
        // Twin should not be equal to original
        Object equals = callMethod(board, "equals", new Class<?>[]{Object.class}, new Object[]{twin});
        assertFalse("Twin should not equal original board", (Boolean) equals);
        
        // Twin should have exactly two tiles swapped (excluding the blank)
        int differenceCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] != 0) { // Skip the blank tile
                    boolean foundMatch = false;
                    // Search for this tile in the twin
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (tiles[i][j] == getTileValue(twin, k, l)) {
                                if (i != k || j != l) {
                                    differenceCount++;
                                }
                                foundMatch = true;
                                break;
                            }
                        }
                        if (foundMatch) break;
                    }
                }
            }
        }
        assertEquals("Twin should have exactly 2 tiles swapped", 2, differenceCount);
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        
        Object result = callMethod(board, "toString", new Class<?>[]{}, new Object[]{});
        assertNotNull("toString() should not return null", result);
        assertTrue("toString() should return a String", result instanceof String);
        
        // The exact format might vary, but it should contain all the numbers
        String boardString = (String) result;
        for (int i = 0; i <= 8; i++) {
            assertTrue("Board string should contain " + i, boardString.contains(Integer.toString(i)));
        }
    }
    
    /**
     * Test the equals() method with a board of different size.
     */
    @Test
    public void testEqualsWithDifferentSize() {
        int[][] tiles1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board1 = createBoard(tiles1);
        
        int[][] tiles2 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        Object board2 = createBoard(tiles2);
        
        Object result = callMethod(board1, "equals", new Class<?>[]{Object.class}, new Object[]{board2});
        assertFalse("equals() with different size should return false", (Boolean) result);
    }
    
    /**
     * Test the Board constructor with a minimum sized board (2x2).
     */
    @Test
    public void testMinimumBoardSize() {
        int[][] tiles = {{1, 2}, {3, 0}};
        Object board = createBoard(tiles);
        
        Object dimension = callMethod(board, "dimension", new Class<?>[]{}, new Object[]{});
        assertEquals("Minimum board size should be 2", 2, dimension);
        
        Object isGoal = callMethod(board, "isGoal", new Class<?>[]{}, new Object[]{});
        assertTrue("2x2 board should be in goal state", (Boolean) isGoal);
    }
    
    /**
     * Test hamming and manhattan distances with a more complex board.
     */
    @Test
    public void testComplexDistances() {
        // A more scrambled board
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Object board = createBoard(tiles);
        
        Object hamming = callMethod(board, "hamming", new Class<?>[]{}, new Object[]{});
        assertTrue("Hamming distance should be positive for scrambled board", (Integer) hamming > 0);
        
        Object manhattan = callMethod(board, "manhattan", new Class<?>[]{}, new Object[]{});
        assertTrue("Manhattan distance should be positive for scrambled board", (Integer) manhattan > 0);
        
        // Manhattan is typically larger than or equal to hamming
        assertTrue("Manhattan should be >= Hamming", (Integer) manhattan >= (Integer) hamming);
    }
}
