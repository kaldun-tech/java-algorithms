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
                if (grid[i][j] == '1') {
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
     *
     * @param grid The 2D grid representing the map
     * @param row The starting row position
     * @param col The starting column position
     */
    private void bfs(char[][] grid, int row, int col) {
        // Create a queue for BFS
        Queue<int[]> queue = new LinkedList<>();

        // Add the starting cell to the queue and mark it as visited
        queue.add(new int[]{row, col});
        grid[row][col] = '#';  // Mark as visited

        // Define directions: up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        // Process the queue until empty
        while (!queue.isEmpty()) {
            // Get the current cell coordinates
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            // Check all four adjacent cells
            for (int[] dir : directions) {
                int newRow = r + dir[0];
                int newCol = c + dir[1];

                // Check if the new position is valid and is land ('1')
                if (newRow >= 0 && newRow < mRows &&
                    newCol >= 0 && newCol < grid[newRow].length &&
                    grid[newRow][newCol] == '1') {

                    // Mark as visited and add to queue
                    grid[newRow][newCol] = '#';
                    queue.add(new int[]{newRow, newCol});
                }
            }
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
     * Time Complexity: O(m*n*α(m*n)) where α is the inverse Ackermann function
     * Space Complexity: O(m*n) for the Union-Find data structure
     *
     * @param grid The m x n 2D binary grid representing the map.
     * @return The number of islands in the grid.
     */
    public int numIslandsUnionFind(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Create our own Union-Find data structure
        DisjointSet uf = new DisjointSet(rows * cols);
        
        // Map to track land cells (we only care about land cells for islands)
        boolean[] isLand = new boolean[rows * cols];
        
        // First pass: mark land cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    isLand[i * cols + j] = true;
                }
            }
        }
        
        // Define directions: right and down (no need for all 4 directions to avoid double counting)
        int[][] directions = {{1, 0}, {0, 1}};
        
        // Second pass: union adjacent land cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    int index = i * cols + j;
                    
                    // Check adjacent cells (right and down)
                    for (int[] dir : directions) {
                        int newRow = i + dir[0];
                        int newCol = j + dir[1];
                        
                        // If adjacent cell is within bounds and is land, union them
                        if (newRow >= 0 && newRow < rows && 
                            newCol >= 0 && newCol < cols && 
                            grid[newRow][newCol] == '1') {
                            
                            int newIndex = newRow * cols + newCol;
                            uf.union(index, newIndex);
                        }
                    }
                }
            }
        }
        
        // Count distinct roots among land cells
        int numIslands = 0;
        boolean[] rootCounted = new boolean[rows * cols];
        
        for (int i = 0; i < rows * cols; i++) {
            if (isLand[i]) {
                int root = uf.find(i);
                if (!rootCounted[root]) {
                    numIslands++;
                    rootCounted[root] = true;
                }
            }
        }
        
        return numIslands;
    }
    
    /**
     * A simple implementation of Disjoint Set (Union-Find) data structure
     * with path compression and union by rank optimizations.
     */
    private static class DisjointSet {
        private final int[] parent;
        private final int[] rank;
        
        /**
         * Initialize a disjoint set with n elements, each in its own set.
         * 
         * @param n Number of elements
         */
        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            
            // Initialize each element as its own parent
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        /**
         * Find the representative (root) of the set containing element x.
         * Uses path compression for efficiency.
         * 
         * @param x Element to find
         * @return Root of the set containing x
         */
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        /**
         * Union the sets containing elements x and y.
         * Uses union by rank for efficiency.
         * 
         * @param x First element
         * @param y Second element
         */
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) {
                return; // Already in the same set
            }
            
            // Union by rank: attach smaller rank tree under root of higher rank tree
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                // If ranks are the same, make one the root and increment its rank
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
