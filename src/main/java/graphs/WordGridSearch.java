package graphs;

import java.util.List;
import java.util.ArrayList;

/**
 * A class that implements word search in a 2D character grid.
 * 
 * The word can be constructed from letters of sequentially adjacent cells,
 * where adjacent cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 */
class WordGridSearch {
    /**
     * Given an m x n grid of characters board and a string word,
     * return true if word exists in the grid.
     *
     * @param board The 2D grid of characters to search in
     * @param word The word to search for
     * @return true if the word exists in the grid, false otherwise
     */
    public boolean exist(char[][] board, String word) {
        // Handle edge cases
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        
        if (word == null || word.isEmpty()) {
            return true;  // Empty word is always found
        }
        
        int rows = board.length;
        int cols = board[0].length;

        // Try starting the search from each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, 0, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Performs a depth-first search from a given position in the grid.
     *
     * @param board The 2D grid of characters
     * @param word The word to search for
     * @param index Current index in the word
     * @param row Current row in the grid
     * @param col Current column in the grid
     * @return true if the word can be found starting at this position
     */
    private boolean dfs(char[][] board, String word, int index, int row, int col) {
        // Base case: we've matched the entire word
        if (index == word.length()) {
            return true;
        }

        // Check if current position is valid and matches the current character
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length ||
                board[row][col] != word.charAt(index)) {
            return false;
        }

        // Mark current cell as visited
        char temp = board[row][col];
        board[row][col] = '#';
        
        // Initialize found flag
        boolean found = false;
        
        // If this is the last character of the word, we're done
        if (index == word.length() - 1) {
            found = true;
        } else {
            // Get all valid neighbors and check them
            for (int[] neighbor : getNeighborPositions(row, col, board.length, board[0].length)) {
                if (dfs(board, word, index + 1, neighbor[0], neighbor[1])) {
                    found = true;
                    break;
                }
            }
        }

        // Backtrack: restore the cell's original value
        board[row][col] = temp;
        return found;
    }
    
    /**
     * Returns the positions of all valid neighbors (up, down, left, right)
     * for a given cell in the grid.
     *
     * @param row Current row
     * @param col Current column
     * @param totalRows Total number of rows in the grid
     * @param totalCols Total number of columns in the grid
     * @return Array of neighbor positions as [row, col] pairs
     */
    private int[][] getNeighborPositions(int row, int col, int totalRows, int totalCols) {
        // Define the 4 possible directions: up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        List<int[]> validNeighbors = new ArrayList<>();
        
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            // Check if the neighbor is within bounds
            if (newRow >= 0 && newRow < totalRows && newCol >= 0 && newCol < totalCols) {
                validNeighbors.add(new int[]{newRow, newCol});
            }
        }
        
        // Convert list to array
        int[][] result = new int[validNeighbors.size()][2];
        for (int i = 0; i < validNeighbors.size(); i++) {
            result[i] = validNeighbors.get(i);
        }
        
        return result;
    }
}
