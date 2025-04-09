package leetcode;

/**
 * https://leetcode.com/problems/unique-paths-ii/
 *
 * You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]).
 * The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]).
 * The robot can only move either down or right at any point in time.
 * An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.
 * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * The testcases are generated so that the answer will be less than or equal to 2 * 109.
 *
 * Constraints:
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] is 0 or 1.
 */
public class UniquePaths {
    /**
     * Find unique paths using dynamic programming.
     * The value dp[i][j] represents the number of unique paths to reach cell (i, j).
     *
     * Algorithm:
     * 1. Initialize a DP table `dp` with the same dimensions as `obstacleGrid`.
     * 2. Handle edge cases: empty grid, obstacle at start or end.
     * 3. Initialize the first cell `dp[0][0]`. If `obstacleGrid[0][0]` is 0, `dp[0][0] = 1`, else 0.
     * 4. Fill the first row: `dp[0][j]` depends on `dp[0][j-1]` and `obstacleGrid[0][j]`.
     * 5. Fill the first column: `dp[i][0]` depends on `dp[i-1][0]` and `obstacleGrid[i][0]`.
     * 6. Fill the rest of the table: `dp[i][j] = dp[i-1][j] + dp[i][j-1]` if `obstacleGrid[i][j]` is not an obstacle.
     * 7. Return `dp[m-1][n-1]`.
     *
     * https://en.wikipedia.org/wiki/Dynamic_programming
     * @param obstacleGrid The m x n grid with 0 representing space and 1 representing obstacle.
     * @return The number of unique paths from (0, 0) to (m-1, n-1).
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // Implement edge case checks (null/empty grid, start/end obstacles)
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            System.out.println("Null or empty obstacle grid");
            return 0;
        }
        int mRows = obstacleGrid.length;
        int nCols = obstacleGrid[0].length;

        // If start or end is blocked, no path is possible
        if (obstacleGrid[0][0] == 1 || obstacleGrid[mRows - 1][nCols - 1] == 1) {
            System.out.println("Obstacle grid has invalid obstacle in start or end position");
            return 0;
        }

        // Create the dp table
        int[][] dp = new int[mRows][nCols];

        // Initialize the starting cell. We already know obstacleGrid[0][0] is 0 from the check above
        dp[0][0] = 1;

        // Fill the first row (dp[0][j]). Can only reach from the left
        for (int j = 1; j < nCols; ++j) {
            if (obstacleGrid[0][j] == 0) {
                dp[0][j] = dp[0][j - 1];
            } else {
                dp[0][j] = 0; // Blocked by obstacle
            }
        }

        // Fill the first column (dp[i][0]). Can only reach from above
        for (int i = 1; i < mRows; ++i) {
            if (obstacleGrid[i][0] == 0) {
                dp[i][0] = dp[i - 1][0];
            } else {
                dp[i][0] = 0; // Blocked by obstacle
            }
        }

        // Fill the remaining cells (dp[i][j] for i > 0 and j > 0)
        for (int i = 1; i < mRows; ++i) {
            for (int j = 1; j < nCols; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    // Cell is blocked
                    dp[i][j] = 0;
                } else {
                    // Number of ways is sum of ways from top and left
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        // Return the value in the bottom-right cell (dp[m-1][n-1])
        return dp[mRows - 1][nCols - 1];
    }

    /**
     * Find unique paths using recursion with memoization (Top-Down DP).
     *
     * @param obstacleGrid The m x n grid with 0 representing space and 1 representing obstacle.
     * @return The number of unique paths from (0, 0) to (m-1, n-1).
     */
    public int uniquePathsWithObstaclesMemo(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            System.out.println("Null or empty obstacle grid");
            return 0;
        }
        int mRows = obstacleGrid.length;
        int nCols = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[mRows - 1][nCols - 1] == 1) {
            System.out.println("Obstacle grid has invalid obstacle in start or end position");
            return 0;
        }

        // Use Integer wrapper to allow null for uncomputed
        Integer[][] memo = new Integer[mRows][nCols];
        return dfs(obstacleGrid, 0, 0, memo);
    }

    private int dfs(int[][] obstacleGrid, int row, int col, Integer[][] memo) {
        int mRows = obstacleGrid.length;
        int nCols = obstacleGrid[0].length;

        // Base Case 1: Out of bounds or obstacle
        if (row >= mRows || col >= nCols || obstacleGrid[row][col] == 1) {
            return 0;
        }

        // Base Case 2: Reached destination
        if (row == mRows - 1 && col == nCols - 1) {
            return 1;
        }

        // Base Case 3: Memoization check
        if (memo[row][col] != null) {
            return memo[row][col];
        }

        // Recursive step: Explore down and right
        int pathsDown = dfs(obstacleGrid, row + 1, col, memo);
        int pathsRight = dfs(obstacleGrid, row, col + 1, memo);

        // Store result in memo and return
        memo[row][col] = pathsDown + pathsRight;
        return memo[row][col];
    }
}
