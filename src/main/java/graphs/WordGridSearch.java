import java.util.List;
import java.util.ArrayList;

class Solution {
    /**
     * Given an m x n grid of characters board and a string word,
     * return true if word exists in the grid.
     *
     * The word can be constructed from letters of sequentially adjacent cells,
     * where adjacent cells are horizontally or vertically neighboring.
     * The same letter cell may not be used more than once.
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, 0, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word, int index, int row, int col) {
        if (index == word.length()) {
            return true;
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length ||
                board[row][col] != word.charAt(index)) {
            return false;
        }

        char temp = board[row][col];
        board[row][col] = '#'; // Mark as visited (you can also use a separate visited matrix)

        boolean found = dfs(board, word, index + 1, row + 1, col) ||
                dfs(board, word, index + 1, row - 1, col) ||
                dfs(board, word, index + 1, row, col + 1) ||
                dfs(board, word, index + 1, row, col - 1);

        board[row][col] = temp; // Backtrack (unmark)
        return found;
    }

    /*public boolean exist(char[][] board, String word) {
        Board model = new Board(board);
        char start = word.charAt(0);

        for (int i = 0; i < model.mRows; ++i) {
            for (int j = 0; j < model.nCols; ++j) {
                if (model.cells[i][j].c == start) {
                    if (model.dfs(word, 0, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    class Cell {
        char c;
        int index;
        boolean visited;

        public Cell(char c, int index) {
            this.c = c;
            this.index = index;
            visited = false;
        }
    }

    class Board {
        int mRows;
        int nCols;
        Cell[][] cells;

        public Board(char[][] board) {
            mRows = board.length;
            nCols = board[0].length;
            cells = new Cell[mRows][];
            for (int i = 0; i < mRows; ++i) {
                cells[i] = new Cell[nCols];
                for (int j = 0; j < nCols; ++j) {
                    cells[i][j] = new Cell(board[i][j], scalarIndex(i, j));
                }
            }
        }

        public int scalarIndex(int row, int col) {
            if (row < 0 || mRows <= row || col < 0 || nCols < col) {
                // Invalid value
                return -1;
            }
            return (nCols * row) + col;
        }

        public int getRow(int index) {
            return index / nCols;
        }

        public int getCol(int index) {
            return index % nCols;
        }

        public List<Cell> getNeighbors(int row, int col) {
            List<Cell> neighbors = new ArrayList<>();
            if (0 < row) {
                // Prev row
                neighbors.add(cells[row - 1][col]);
            }
            if (row < mRows - 1) {
                // Next row
                neighbors.add(cells[row + 1][col]);
            }
            if (0 < col) {
                // Prev col
                neighbors.add(cells[row][col - 1]);
            }
            if (col < nCols - 1) {
                // Next col
                neighbors.add(cells[row][col + 1]);
            }
            return neighbors;
        }

        boolean dfs(String word, int wIndex, int row, int col) {
            if (wIndex == word.length()) {
                return true;
            }

            char nextChar = word.charAt(wIndex);
            if (row < 0 || mRows <= row  || col < 0 || nCols <= col ||
                cells[row][col].visited || cells[row][col].c != nextChar) {
                return false;
            }

            cells[row][col].visited = true;
            // Recursion time
            List<Cell> neighbors = getNeighbors(row, col);
            boolean found = false;
            for (Cell n : neighbors) {
                int nextRow = getRow(n.index);
                int nextCol = getCol(n.index);
                found = (found || dfs(word, wIndex + 1, nextRow, nextCol));
                // Backtrack (unmark visited)
                n.visited = false;
            }
            return found;
        }
    }*/
}
