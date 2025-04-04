/**
 * Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm
 *
 * The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8,
 * plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as
 * few moves as possible. You are permitted to slide tiles either horizontally or vertically into the blank
 * square. The diagram shows a sequence of moves from an initial board (left) to the goal board (right).
 *
 * To begin, create an immutable data type that models an n-by-n board with sliding tiles.
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

    // Board dimension n
    public int dimension() {
        return tiles.length();
    }

    // Number of tiles out of place
    public int hamming() {
        for (int i = 0; i < dimension(); ++i) {
            for (int j = 0; j < dimension(); ++j) {

            }
        }
    }

    /** Sum of Manhattan distances between tiles and goal. Theis is defined as the
     *  sum of the vertical and horizontal distance from the tiles to their goal position */
    public int manhattan() {

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
     * @return
     */
    public Iterable<Board> neighbors() {

    }

    // A board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
