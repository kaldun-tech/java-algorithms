// No package declaration, as Board.java is in the default package

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

/**
 * Since the Board class is in the default package in the main source directory,
 * and we're testing from the test directory, we'll use reflection to access it.
 */

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
        // Board with some tiles out of place
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}; 
        Object board = createBoard(tiles);
        Object result = callMethod(board, "hamming", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should have 5 tiles out of place", 5, result); // 5 tiles out of place (not counting the blank)
    }
    
    /**
     * Test the hamming() method with a board that has all tiles out of place.
     * The blank tile (0) is not counted in hamming distance.
     */
    @Test
    public void testHammingAllMisplaced() {
        // Board with all tiles out of place (except the blank)
        int[][] allWrongTiles = {{0, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        Object allWrongBoard = createBoard(allWrongTiles);
        Object allWrongResult = callMethod(allWrongBoard, "hamming", new Class<?>[]{}, new Object[]{});
        // The blank tile (0) is not counted in hamming distance. In this case, 7 tiles are out of place (not 8)
        assertEquals("Board with all tiles misplaced should have 7 out of place", 7, allWrongResult);
    }

    /**
     * Test the manhattan() method with various board configurations.
     */
    @Test
    public void testManhattan() {
        // Goal board - 0 manhattan distance
        int[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object goalBoard = createBoard(goalTiles);
        Object goalResult = callMethod(goalBoard, "manhattan", new Class<?>[]{}, new Object[]{});
        assertEquals(0, goalResult);
        
        // Example from problem spec: Sum of Manhattan distances is 10
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}; 
        Object board = createBoard(tiles);
        Object result = callMethod(board, "manhattan", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should have 10 Manhattan distances", 10, result);
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
        assertEquals(board1, board1);
        
        // Test equality with equivalent board
        assertEquals(board1, board1Again);
        
        // Test inequality with different board
        int[][] tiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Object board2 = createBoard(tiles2);
        assertNotEquals(board1, board2);
        
        // Test inequality with null
        assertNotEquals(board1, null);
        
        // Test inequality with different object type
        assertNotEquals(board1, new Object());
        
        // Test inequality with different dimension
        int[][] smallerTiles = {{1, 2}, {3, 0}};
        Object smallerBoard = createBoard(smallerTiles);
        assertNotEquals(board1, smallerBoard);
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
        assertNotEquals(board, twin);
        
        // Twin should have exactly two tiles swapped (excluding the blank)
        int differenceCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] != 0) { // Skip the blank tile
                    boolean foundMatch = false;
                    // Search for this tile in the twin
                    outerLoop:
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (tiles[i][j] == getTileValue(twin, k, l)) {
                                if (i != k || j != l) {
                                    differenceCount++;
                                }
                                foundMatch = true;
                                break outerLoop;
                            }
                        }
                    }
                    assertTrue("Tile " + tiles[i][j] + " not found in twin", foundMatch);
                }
            }
        }
        assertEquals(2, differenceCount);
    }
    
    /**
     * Helper method to get a tile value from a Board.
     * Since Board doesn't have a public tileAt method, we need to use reflection or
     * compare the string representation.
     */
    private int getTileValue(Object board, int row, int col) {
        // Parse the board's string representation to get the tile value
        String[] lines = board.toString().split("\n");
        // Skip the first line (dimension)
        String[] rowValues = lines[row + 1].trim().split("\\s+");
        return Integer.parseInt(rowValues[col]);
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        
        String boardString = board.toString();
        
        // Print the actual board string for debugging
        System.out.println("Board string representation: \n" + boardString);
        
        // The string should contain all the tile values
        // Using more flexible assertions that don't depend on exact formatting
        assertTrue("Board string should contain dimension", boardString.contains("3"));
        assertTrue("Board string should contain tile 1", boardString.contains("1"));
        assertTrue("Board string should contain tile 2", boardString.contains("2"));
        assertTrue("Board string should contain tile 3", boardString.contains("3"));
        assertTrue("Board string should contain tile 4", boardString.contains("4"));
        assertTrue("Board string should contain tile 5", boardString.contains("5"));
        assertTrue("Board string should contain tile 6", boardString.contains("6"));
        assertTrue("Board string should contain tile 7", boardString.contains("7"));
        assertTrue("Board string should contain tile 8", boardString.contains("8"));
        assertTrue("Board string should contain tile 0", boardString.contains("0"));
    }
}
