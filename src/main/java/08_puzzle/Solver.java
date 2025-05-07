import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Implements A* search. Efficacy of the approach hinges on the choice of
 * priority function:
 * Hamming priority function - Hamming distance of a board plus the number of
 * moves made so far to get to the search node. Intuitively, a node with a small
 * number of tiles in the wrong position is close to the goal, and we prefer a
 * lesser number of moves.
 * Manhattan priority function - Manhattan distance of a board plus the number
 * of moves made so far to get to the search node.
 * To solve the puzzle from a given search node on a PQ, the total number of moves
 * needed is at least its priority using either Hamming or Manhattan PF.
 * Consequently, whent he goal board is dequeued we have discovered both a sequence
 * of moves from the initial board to the goal, and also one with the fewest moves.
 * Challenge: Prove it
 * 
 * Runtime and Memory Complexity:
 * - Constructor/solve(): O(n⁴ * 2^(n²)) time in the worst case for A* search, where n is the board dimension.
 *   This is because:
 *   1. There are n² tiles and each can be in n² positions, giving (n²)! possible board states
 *   2. The branching factor is at most 4 (max neighbors per board)
 *   3. The A* algorithm with Manhattan heuristic dramatically prunes the search space
 *   4. Each board operation is O(n²)
 * 
 * - Space complexity: O(n⁴ * 2^(n²)) in the worst case to store the priority queue
 * 
 * - isSolvable(): O(1) time and space after solving
 * - moves(): O(1) time and space after solving
 * - solution(): O(d) time and O(d) space, where d is the solution depth (number of moves)
 * 
 * Note: While the theoretical worst-case complexity is high, in practice the A* algorithm
 * with Manhattan distance heuristic is very efficient for solving the 8-puzzle and can
 * typically solve random instances in milliseconds.
 */
public class Solver {

    private boolean solutionFound = false;    // Flag to indicate if a solution was found
    private SearchNode goalNode = null;

    /**
     * Find a solution to the initial board (using the A* algorithm)
     * First, insert the initial search node into a priority queue.
     * Then, delete from the PQ the search node with minimum priority, and insert
     * onto the PQ all neighboring search nodes that can be reached in one move
     * from the dequeued search node. Repeat until search node dequeued
     * corresponds to the goal board.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null initial board");
        }

        // Create a search node for the initial board
        SearchNode initialNode = new SearchNode(initial);
        
        /**
         * Twin board logic for determining solvability:
         * 
         * For the 8-puzzle (and n-puzzle in general), a fundamental mathematical property exists:
         * - If a board is solvable, its twin (created by swapping two adjacent non-blank tiles) is unsolvable
         * - If a board is unsolvable, its twin is solvable
         * 
         * By running A* searches on both boards simultaneously, we can definitively determine solvability:
         * - If we find a solution for the original board, it's solvable
         * - If we find a solution for the twin board, the original is unsolvable
         * 
         * This approach is more efficient than counting inversions and doesn't require accessing
         * the board's internal state.
         */
        Board twin = initial.twin();
        SearchNode twinNode = new SearchNode(twin);
        
        // Initialize priority queues for both the original and twin boards
        MinPQ<SearchNode> pq = new MinPQ<>(new ManhattanOrder());
        MinPQ<SearchNode> twinPq = new MinPQ<>(new ManhattanOrder());
        
        // Insert initial nodes
        pq.insert(initialNode);
        twinPq.insert(twinNode);
        
        // Run the A* algorithm on both boards simultaneously
        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            // Process a step for the original board
            SearchNode min = pq.delMin();
            if (min.board.isGoal()) {
                goalNode = min;
                solutionFound = true;
                break;
            }
            
            // Process a step for the twin board
            SearchNode twinMin = twinPq.delMin();
            if (twinMin.board.isGoal()) {
                // If twin is solvable, original is not
                break;
            }
            
            // If neither reached the goal, expand both search trees
            enqueueNeighbors(pq, min);
            enqueueNeighbors(twinPq, twinMin);
        }
    }
    
    /**
     * Helper method to enqueue all neighbors of a search node.
     * Implements the critical optimization: don't enqueue a neighbor if its board is the same as
     * the board of the previous search node in the game tree.
     */
    private void enqueueNeighbors(MinPQ<SearchNode> pq, SearchNode node) {
        for (Board neighbor : node.board.neighbors()) {
            if (node.prev != null && neighbor.equals(node.prev.board)) {
                continue;
            }
            
            // Create a new search node and enqueue it
            SearchNode neighborNode = new SearchNode(neighbor, node.moves + 1, node);
            pq.insert(neighborNode);
        }
    }

    /**
     * Define a search node of the game to be a board, the number of moves made
     * to reach the board, and the previous search node. Also cache the priority
     * values of priority functions to avoid recomputation. Like dynamic programming.
     */
    private class SearchNode {
        final Board board;
        final int moves;
        final SearchNode prev;
        final int manhattanPriority;

        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            moves = m;
            prev = p;
            // Cache priority values to avoid recomputation
            manhattanPriority = board.manhattan() + moves;
        }

        public SearchNode(Board b) {
            this(b, 0, null);
        }
    }

    /**
     * Manhattan priority function - Manhattan distance of a board plus the
     * number of moves made so far to get to the search node.
     */
    private class ManhattanOrder implements Comparator<SearchNode> {
        public int compare(SearchNode q, SearchNode r) {
            if (q == null) {
                throw new NullPointerException("Null first node");
            }
            else if (r == null) {
                throw new NullPointerException("Null second node");
            }
            return Integer.compare(q.manhattanPriority, r.manhattanPriority);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solutionFound;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return goalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        // Create a queue to store boards in correct order (initial to goal)
        Queue<Board> solutionPath = new Queue<Board>();
        
        // Count the number of moves to allocate an array of the right size
        int numMoves = goalNode.moves;
        Board[] boardsInReverse = new Board[numMoves + 1]; // +1 for initial board
        
        // Fill the array in reverse order
        SearchNode n = goalNode;
        int i = numMoves;
        while (n != null) {
            boardsInReverse[i--] = n.board;
            n = n.prev;
        }
        
        // Enqueue boards in correct order
        for (Board board : boardsInReverse) {
            solutionPath.enqueue(board);
        }
        
        return solutionPath;
    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (solver.isSolvable()) {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        else {
            StdOut.println("No solution possible");
        }
    }

}
