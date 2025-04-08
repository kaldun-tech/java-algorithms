package leetcode;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for the WordGridSearch class.
 * Tests the functionality of finding words in a character grid.
 */
public class WordGridSearchTest {
    
    private WordGridSearch wordSearch;
    
    /**
     * Sets up a WordGridSearch instance for testing.
     */
    @Before
    public void setUp() {
        wordSearch = new WordGridSearch();
    }
    
    /**
     * Tests finding a word that exists horizontally in the grid.
     */
    @Test
    public void testHorizontalWord() {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        
        assertTrue(wordSearch.exist(board, "ABCE"));
    }
    
    /**
     * Tests finding a word that exists vertically in the grid.
     */
    @Test
    public void testVerticalWord() {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        
        assertTrue(wordSearch.exist(board, "ASA"));
    }
    
    /**
     * Tests finding a word that exists in a zig-zag pattern in the grid.
     */
    @Test
    public void testZigzagWord() {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        
        assertTrue(wordSearch.exist(board, "ABCCED"));
    }
    
    /**
     * Tests that a word not present in the grid returns false.
     */
    @Test
    public void testWordNotPresent() {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        
        assertFalse(wordSearch.exist(board, "ABCB"));
    }
    
    /**
     * Tests finding a word that requires backtracking during the search.
     */
    @Test
    public void testBacktrackingRequired() {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'E', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        
        assertTrue(wordSearch.exist(board, "ABCESEEEFS"));
    }
    
    /**
     * Tests finding a single character word in the grid.
     */
    @Test
    public void testSingleCharacterWord() {
        char[][] board = {
            {'A', 'B', 'C'},
            {'D', 'E', 'F'},
            {'G', 'H', 'I'}
        };
        
        assertTrue(wordSearch.exist(board, "E"));
    }
    
    /**
     * Tests that the same cell cannot be used twice in a word.
     */
    @Test
    public void testCellCannotBeUsedTwice() {
        char[][] board = {
            {'A', 'A', 'A'},
            {'A', 'A', 'A'},
            {'A', 'A', 'A'}
        };
        
        // In a 3x3 grid, we can have at most 9 cells
        // So a word with 9 A's should be possible, but 10 A's should not
        assertTrue(wordSearch.exist(board, "AAAAAAAAA"));
        assertFalse(wordSearch.exist(board, "AAAAAAAAAA"));
    }
    
    /**
     * Tests a word that is longer than the total number of cells in the grid.
     */
    @Test
    public void testWordLongerThanGrid() {
        char[][] board = {
            {'A', 'B'},
            {'C', 'D'}
        };
        
        // Grid has only 4 cells, so a 5-letter word can't exist
        assertFalse(wordSearch.exist(board, "ABCDE"));
    }
    
    /**
     * Tests finding a word in a 1x1 grid.
     */
    @Test
    public void testSingleCellGrid() {
        char[][] board = {{'A'}};
        
        assertTrue(wordSearch.exist(board, "A"));
        assertFalse(wordSearch.exist(board, "B"));
        assertFalse(wordSearch.exist(board, "AA"));
    }
    
    /**
     * Tests finding a word in a grid with repeated characters.
     */
    @Test
    public void testGridWithRepeatedCharacters() {
        char[][] board = {
            {'A', 'A', 'A', 'A'},
            {'A', 'B', 'B', 'A'},
            {'A', 'B', 'B', 'A'},
            {'A', 'A', 'A', 'A'}
        };
        
        assertTrue(wordSearch.exist(board, "AAAAABBBBAAA"));
    }
    
    /**
     * Tests an empty word, which should always return true.
     */
    @Test
    public void testEmptyWord() {
        char[][] board = {
            {'A', 'B'},
            {'C', 'D'}
        };
        
        // An empty word should be found in any grid
        assertTrue(wordSearch.exist(board, ""));
    }
    
    /**
     * Tests a complex grid with a long, winding word.
     */
    @Test
    public void testComplexGridWithLongWord() {
        char[][] board = {
            {'C', 'A', 'A', 'T', 'S', 'E', 'A', 'R', 'C', 'H'},
            {'R', 'L', 'D', 'O', 'L', 'G', 'O', 'R', 'I', 'T'},
            {'A', 'G', 'V', 'F', 'I', 'N', 'D', 'P', 'A', 'H'},
            {'P', 'O', 'A', 'S', 'D', 'E', 'P', 'T', 'H', 'M'},
            {'H', 'R', 'N', 'E', 'I', 'G', 'H', 'B', 'O', 'R'},
            {'S', 'I', 'C', 'A', 'R', 'E', 'F', 'U', 'L', 'L'},
            {'E', 'T', 'E', 'R', 'E', 'C', 'T', 'I', 'O', 'N'},
            {'A', 'H', 'S', 'C', 'C', 'T', 'R', 'A', 'V', 'E'},
            {'R', 'M', 'A', 'H', 'T', 'I', 'A', 'L', 'E', 'L'},
            {'C', 'P', 'A', 'T', 'H', 'O', 'N', 'W', 'A', 'Y'}
        };
        
        assertTrue(wordSearch.exist(board, "ALGORITHM"));
        assertTrue(wordSearch.exist(board, "SEARCH"));
        assertTrue(wordSearch.exist(board, "DEPTH"));
        assertTrue(wordSearch.exist(board, "PATH"));
        assertFalse(wordSearch.exist(board, "JAVA"));
    }
}
