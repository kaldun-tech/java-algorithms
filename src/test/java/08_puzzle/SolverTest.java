// No package declaration, as Solver.java is in the default package

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

/**
 * Unit tests for the Solver class from the 8-puzzle problem.
 * 
 * Since the Solver class is in the default package in the main source directory,
 * and we're testing from the test directory, we use reflection to access it.
 */
public class SolverTest {
    
    // Class references obtained via reflection
    private Class<?> solverClass;
    private Class<?> boardClass;
    
    /**
     * Set up the test by loading the Solver and Board classes via reflection.
     */
    @Before
    public void setUp() throws Exception {
        // Load the classes using reflection
        solverClass = Class.forName("Solver");
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
     * Helper method to create a Solver instance using reflection.
     * 
     * @param board The Board instance to solve
     * @return A Solver instance
     */
    private Object createSolver(Object board) {
        try {
            Constructor<?> constructor = solverClass.getConstructor(boardClass);
            return constructor.newInstance(board);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Solver instance", e);
        }
    }
    
    /**
     * Helper method to call a method on an object using reflection.
     * 
     * @param object The object to call the method on
     * @param methodName The name of the method to call
     * @param paramTypes The parameter types of the method
     * @param params The parameters to pass to the method
     * @return The result of the method call
     */
    private Object callMethod(Object object, String methodName, Class<?>[] paramTypes, Object[] params) {
        try {
            Method method = object.getClass().getMethod(methodName, paramTypes);
            return method.invoke(object, params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call method " + methodName, e);
        }
    }
    
    /**
     * Test the isSolvable() method with a solvable board.
     */
    @Test
    public void testIsSolvableSolvableBoard() {
        // Create a solvable board (goal state)
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object result = callMethod(solver, "isSolvable", new Class<?>[]{}, new Object[]{});
        assertTrue("Goal board should be solvable", (Boolean) result);
    }
    
    /**
     * Test the isSolvable() method with an unsolvable board.
     * Note: In the 8-puzzle, a board is unsolvable if the number of inversions is odd.
     * An inversion is when a tile precedes another tile with a lower number.
     */
    @Test
    public void testIsSolvableUnsolvableBoard() {
        // Create an unsolvable board (swap 1 and 2 from goal state)
        int[][] tiles = {{2, 1, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object result = callMethod(solver, "isSolvable", new Class<?>[]{}, new Object[]{});
        assertFalse("Board with odd number of inversions should not be solvable", (Boolean) result);
    }
    
    /**
     * Test the moves() method with a board that requires 0 moves (already solved).
     */
    @Test
    public void testMovesZeroMoves() {
        // Create a solved board (goal state)
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object result = callMethod(solver, "moves", new Class<?>[]{}, new Object[]{});
        assertEquals("Solved board should require 0 moves", 0, result);
    }
    
    /**
     * Test the moves() method with a board that requires some moves.
     */
    @Test
    public void testMovesSomeMoves() {
        // Create a board that requires some moves
        // This board requires 4 moves to solve
        int[][] tiles = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object result = callMethod(solver, "moves", new Class<?>[]{}, new Object[]{});
        assertEquals("Board should require 4 moves", 4, result);
    }
    
    /**
     * Test the moves() method with an unsolvable board.
     */
    @Test
    public void testMovesUnsolvableBoard() {
        // Create an unsolvable board
        int[][] tiles = {{2, 1, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object result = callMethod(solver, "moves", new Class<?>[]{}, new Object[]{});
        assertEquals("Unsolvable board should return -1 for moves", -1, result);
    }
    
    /**
     * Test the solution() method with a solved board.
     */
    @Test
    public void testSolutionSolvedBoard() {
        // Create a solved board (goal state)
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object solution = callMethod(solver, "solution", new Class<?>[]{}, new Object[]{});
        
        // Solution should not be null
        assertNotNull("Solution should not be null for solvable board", solution);
        
        // Count the number of boards in the solution
        int count = 0;
        for (@SuppressWarnings("unused") Object b : (Iterable<?>) solution) {
            count++;
        }
        
        // For a solved board, the solution should contain just the initial board
        assertEquals("Solution for solved board should contain 1 board", 1, count);
    }
    
    /**
     * Test the solution() method with a board that requires some moves.
     */
    @Test
    public void testSolutionMultipleSteps() {
        // Create a board that requires some moves
        int[][] tiles = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object solution = callMethod(solver, "solution", new Class<?>[]{}, new Object[]{});
        
        // Solution should not be null
        assertNotNull("Solution should not be null for solvable board", solution);
        
        // Count the number of boards in the solution
        int count = 0;
        for (@SuppressWarnings("unused") Object b : (Iterable<?>) solution) {
            count++;
        }
        
        // For this board, the solution should contain 5 boards (initial + 4 moves)
        assertEquals("Solution should contain 5 boards (initial + 4 moves)", 5, count);
    }
    
    /**
     * Test the solution() method with an unsolvable board.
     */
    @Test
    public void testSolutionUnsolvableBoard() {
        // Create an unsolvable board
        int[][] tiles = {{2, 1, 3}, {4, 5, 6}, {7, 8, 0}};
        Object board = createBoard(tiles);
        Object solver = createSolver(board);
        
        Object solution = callMethod(solver, "solution", new Class<?>[]{}, new Object[]{});
        
        // Solution should be null for an unsolvable board
        assertNull("Solution should be null for unsolvable board", solution);
    }
}
