package leetcode;

/**
 * https://leetcode.com/problems/palindromic-substrings/
 *
 * Given a string s, return the number of palindromic substrings in it.
 * A string is a palindrome when it reads the same backward as forward.
 * A substring is a contiguous sequence of characters within the string.
 *
 * Example 1:
 * Input: s = "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c"
 *
 * Example 2:
 * Input: s = "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa"
 * 
 * Constraints:
 * - 1 <= s.length <= 1000
 * - s consists of lowercase English letters.
 */
public class PalindromicSubstrings {
    
    /**
     * Count all palindromic substrings in the given string using the expand around center approach.
     * 
     * Time Complexity: O(n²) where n is the length of the string
     * Space Complexity: O(1)
     *
     * @param s The input string
     * @return The count of palindromic substrings
     */
    public int countSubstrings(String s) {
        int count = 0;
        // TODO: Implement this method
        return 0;
    }
    
    /**
     * Expands around a center and counts palindromes.
     * 
     * @param s The input string
     * @param left The left index of the center
     * @param right The right index of the center
     * @return The count of palindromes with this center
     */
    private int expandAroundCenter(String s, int left, int right) {
        // TODO: Implement this method
        return 0;
    }
    
    /**
     * Alternative implementation using dynamic programming.
     * 
     * Time Complexity: O(n²) where n is the length of the string
     * Space Complexity: O(n²) for the dp array
     *
     * @param s The input string
     * @return The count of palindromic substrings
     */
    public int countSubstringsDP(String s) {
        // TODO: Implement this method
        return 0;
    }
}
