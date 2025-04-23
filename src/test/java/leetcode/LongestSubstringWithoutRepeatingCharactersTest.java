package leetcode;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the LongestSubstringWithoutRepeatingCharacters implementation.
 * Tests various scenarios for finding the longest substring without repeating characters.
 */
public class LongestSubstringWithoutRepeatingCharactersTest {

    private final LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

    /**
     * Test examples from the problem description.
     */
    @Test
    public void testExamples() {
        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    }

    /**
     * Test edge cases with empty and single character strings.
     */
    @Test
    public void testEdgeCases() {
        assertEquals(0, solution.lengthOfLongestSubstring(""));
        assertEquals(1, solution.lengthOfLongestSubstring("a"));
    }

    /**
     * Test cases with special characters and spaces.
     */
    @Test
    public void testSpecialCharacters() {
        assertEquals(4, solution.lengthOfLongestSubstring("ab c"));
        assertEquals(5, solution.lengthOfLongestSubstring("a1b2c"));
        assertEquals(5, solution.lengthOfLongestSubstring("!@#$%"));
    }

    /**
     * Test cases where the longest substring is at the beginning, middle, or end.
     */
    @Test
    public void testSubstringPositions() {
        // Longest at beginning
        assertEquals(3, solution.lengthOfLongestSubstring("abcbb"));
        
        // Longest in middle
        assertEquals(3, solution.lengthOfLongestSubstring("bbabcbb"));
        
        // Longest at end
        assertEquals(3, solution.lengthOfLongestSubstring("bbbabc"));
    }

    /**
     * Test case with all unique characters.
     */
    @Test
    public void testAllUniqueCharacters() {
        assertEquals(10, solution.lengthOfLongestSubstring("abcdefghij"));
    }
}
