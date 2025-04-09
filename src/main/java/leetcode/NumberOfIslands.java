package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/number-of-islands/
 *
 * Given an m x n 2D binary grid 'grid' which represents a map of '1's (land) and '0's (water),
 * return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 */
public class NumberOfIslands {
    int mRows;

    public int numIslands(char[][] grid) {
        mRows = grid.length;
        return numIslandsDFS(grid);
    }

    /**
     * Find the number of islands in the grid using depth-first search (DFS).
     * Algorithm:
     * 1. Iterate through each cell in the grid.
     * 2. If a cell contains '1' (land), increment the island count and perform DFS to mark all connected land cells as visited.
     * 3. During DFS, explore all four directions (up, down, left, right) and mark visited cells to avoid counting them again.
     * 4. Return the total count of islands found.
     *
     * Time Complexity: O(m*n) where m is the number of rows and n is the number of columns.
     * Space Complexity: O(m*n) in the worst case for the recursion stack.
     *
     * @param grid The m x n 2D binary grid representing the map.
     * @return The number of islands in the grid.
     */
    public int numIslandsDFS(char[][] grid) {
        int numIslands = 0;

        // O(m*n) iteration
        for (int i = 0; i < mRows; ++i) {
            int nCols = grid[i].length;
            for (int j = 0; j < nCols; ++j) {
                if (grid[i][j] == '1') {
                    // Land ho! Increment island count and use dfs to mark visited
                    ++numIslands;
                    dfs(grid, i, j);
                }
            }
        }
        return numIslands;
    }

    /**
     * Helper method to perform depth-first search from a land cell.
     * Marks all connected land cells as visited by changing '1' to '0' or another marker.
     *
     * @param grid The 2D grid representing the map.
     * @param row The current row position.
     * @param col The current column position.
     */
    private void dfs(char[][] grid, int row, int col) {
        // Implement DFS to mark all connected land cells
        if (grid[row][col] != '1') {
            // Already marked with sentinel # or water 0
            return;
        }
        // Get the size
        int nCols = grid[row].length;

        // Mark this as a connected land cell with # sentinel value
        grid[row][col] = '#';

        // Recurse for the four neighbors
        if (0 < row) {
            // Check neighbor above
            dfs(grid, row - 1, col);
        }
        if (0 < col) {
            // Check neighbor to left
            dfs(grid, row, col - 1);
        }
        if (row < mRows - 1) {
            // Check neighbor below
            dfs(grid, row + 1, col);
        }
        if (col < nCols - 1) {
            // Neighbor to right
            dfs(grid, row, col + 1);
        }
    }

    /**
     * Alternative implementation using breadth-first search (BFS).
     *
     * Algorithm:
     * 1. Iterate through each cell in the grid.
     * 2. If a cell contains '1' (land), increment the island count and perform BFS to mark all connected land cells as visited.
     * 3. During BFS, use a queue to explore all four directions and mark visited cells.
     * 4. Return the total count of islands found.
     *
     * @param grid The m x n 2D binary grid representing the map.
     * @return The number of islands in the grid.
     */
    public int numIslandsBFS(char[][] grid) {
        int numIslands = 0;

        for (int i = 0; i < mRows; ++i) {
            int nCols = grid[i].length;
            for (int j = 0; j < nCols; ++j) {
                if (grid[i][j] == 1) {
                    ++numIslands;
                    bfs(grid, i, j);
                }
            }
        }

        return numIslands;
    }

    /**
     * Helper method to perform breadth-first search from a land cell.
     * Marks all connected land cells as visited by changing '1' to '0' or another marker.
     * During BFS, use a queue to explore all four directions and mark visited cells.
     *
     * Instead of using recursion (like in DFS), BFS uses a queue data structure
     * We add cells to the queue and process them in a first-in-first-out (FIFO) order
     * This creates a "level by level" exploration pattern, exploring all immediate neighbors before moving to their neighbors
     *
     * When we find a land cell ('1'), we add its coordinates (row, col) to the queue
     * We mark this cell as visited (using '#' in your implementation)
     * Then we process the queue by:
     * Removing the front element (coordinates)
     * Checking all four adjacent cells (up, down, left, right)
     * For each adjacent cell that is land ('1'), we mark it as visited and add its coordinates to the queue
     * @param grid
     * @param row
     * @param col
     */
    private void bfs(char[][] grid, int row, int col) {
        if (grid[row][col] != '1') {
            // Already marked with sentinel # or water 0
            return;
        }
        // Get the size
        int nCols = grid[row].length;
        Queue<int[]> q = new LinkedList<>();

        // Enqueue this coordinate
        q.add(new int[]{row, col});

        // Mark this as a connected land cell with # sentinel value
        grid[row][col] = '#';

        while (!q.isEmpty()) {
            // Next row in position 0, col in 1
            int[] next = q.remove();
            int i = next[0];
            int j = next[1];

            // Enqueue the four neighbors
            if (0 < i) {
                // Check neighbor above
                bfsVisit(grid, i - 1, j, q);
            }
            if (i < mRows - 1) {
                // Check neighbor below
                bfsVisit(grid, i + 1, j, q);
            }
            if (0 < j) {
                // Check neighbor to left
                bfsVisit(grid, i, j - 1, q);
            }
            if (j < nCols - 1) {
                // Neighbor to right
                bfsVisit(grid, i, j + 1, q);
            }
        }
    }

    private void bfsVisit(char[][] grid, int row, int col, Queue<int[]> q) {
        if (grid[row][col] == '1') {
            bfs(grid, row, col);
            q.add(new int[]{ row, col });
        }
    }

    /**
     * Alternative implementation using Union-Find (Disjoint Set) data structure.
     *
     * Algorithm:
     * 1. Initialize a Union-Find data structure.
     * 2. Iterate through the grid and union adjacent land cells.
     * 3. Count the number of distinct sets (islands).
     *
     * @param grid The m x n 2D binary grid representing the map.
     * @return The number of islands in the grid.
     */
    public int numIslandsUnionFind(char[][] grid) {
        int numIslands = 0;
        return numIslands;
    }
}
