import edu.princeton.cs.algs4.Queue;

/**
 * Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm
 *
 * The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8,
 * plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as
 * few moves as possible. You are permitted to slide tiles either horizontally or vertically into the blank
 * square. The diagram shows a sequence of moves from an initial board (left) to the goal board (right).
 *
 * To begin, create an immutable data type that models an n-by-n board with sliding tiles.
 * 
 * Runtime and Memory Complexity:
 * - Constructor: O(n²) time to initialize and find the blank square, O(n²) space for the tiles array
 * - dimension(): O(1) time and space
 * - hamming(): O(n²) time to check each tile, O(1) extra space
 * - manhattan(): O(n²) time to calculate distances for each tile, O(1) extra space
 * - isGoal(): O(n²) time (calls hamming()), O(1) extra space
 * - equals(): O(n²) time to compare all tiles, O(1) extra space
 * - neighbors(): O(n²) time to create up to 4 neighbor boards, O(n²) space for each neighbor
 * - twin(): O(n²) time to create a new board with swapped tiles, O(n²) space for the new board
 * 
 * where n is the dimension of the board
 */
public class Board {

    private int[][] tiles;
    private int blankRow;
    private int blankCol;


    /** create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
     *  You may assume that the constructor receives an n-by-n array containing the n^2 integers
     *  between 0 and n^2 − 1, where 0 represents the blank square.
     *  You may also assume that 2 ≤ n < 128.*/
    public Board(int[][] tiles) {
        this.tiles = tiles;
        // Find the blank square
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (tiles[i][j] == 0) {
                    // Blank square
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    /** String representation of this board. Returns a string composed of n + 1 lines.
     * The first line contains the board size n; the remaining n lines contains the n-by-n grid
     * of tiles in row-major order, using 0 to designate the blank square.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(dimension());
        for (int[] row : tiles) {
            sb.append("\n");
            for (int entry : row) {
                sb.append(" ").append(entry);
            }
        }
        return sb.toString();
    }

    /**
     * Board dimension n
     * @return the dimension of the board
     */
    public int dimension() {
        return tiles.length;
    }

    /**
     * Number of tiles out of place
     * @return the number of tiles out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                int value = tiles[i][j];
                int goal = i * dimension() + j + 1;
                if (value != 0 && value != goal) {
                    count++;
                }
            }
        }
        return count;
    }

    /** Sum of Manhattan distances between tiles and goal. This is defined as the
     *  sum of the vertical and horizontal distance from the tiles to their goal position
     *  @return the sum of Manhattan distances between tiles and goal
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int value = tiles[i][j];
                // Skip the blank tile
                if (value != 0) {
                    int goalRow = (value - 1) / dimension();
                    int goalCol = (value - 1) % dimension();
                    sum += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return sum;
    }

    /** Is this board the goal board? */
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        } else if (!(y instanceof Board)) {
            return false;
        }

        Board that = (Board) y;
        if (this.dimension() != that.dimension()) {
            return false;
        }
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Get all neighboring boards. Depending on the location of the blank
     * square, a board can have 2, 3, or 4 neighbors
     * @return an iterable of all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();

        // Try moving blank in all four directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            // Apply direction vector to find neighbor
            int newRow = blankRow + dir[0];
            int newCol = blankCol + dir[1];

            // Check if the new position is in bounds
            if (0 <= newRow && newRow < dimension() && 0 <= newCol && newCol < dimension()) {
                // Create a copy of the tiles
                int[][] newTiles = copyTiles();

                // Swap the blank with the adjacent tile
                newTiles[blankRow][blankCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;

                neighbors.enqueue(new Board(newTiles));
            }
        }

        return neighbors;
    }

    /** Creates a copy of the existing tiles */
    private int[][] copyTiles() {
        int[][] copy = new int[dimension()][dimension()];

        // Copy the tiles
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     * @return a board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        int[][] twinTiles = copyTiles();

        // Find two non-blank tiles to swap
        int row1 = 0, col1 = 0;
        int row2 = 0, col2 = 1;

        // If one of these is the blank, move to the next row
        if (twinTiles[row1][col1] == 0 || twinTiles[row2][col2] == 0) {
            row1 = 1;
            row2 = 1;
        }

        // Swap the tiles
        int temp = twinTiles[row1][col1];
        twinTiles[row1][col1] = twinTiles[row2][col2];
        twinTiles[row2][col2] = temp;

        return new Board(twinTiles);
    }
}
