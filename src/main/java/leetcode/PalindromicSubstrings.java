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
     * For each position in the string, we consider it as a potential center of a palindrome
     * and expand outwards to find all possible palindromes.
     *
     * Time Complexity: O(n²) where n is the length of the string
     * Space Complexity: O(1)
     *
     * @param s The input string
     * @return The count of palindromic substrings
     */
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int count = 0;
        int n = s.length();

        // For each center position, try to expand outwards
        for (int i = 0; i < n; i++) {
            // Odd length palindromes (single character center)
            count += expandAroundCenter(s, i, i);

            // Even length palindromes (between two characters)
            count += expandAroundCenter(s, i, i + 1);
        }

        return count;
    }

    /**
     * Expands around a center and counts palindromes.
     * This method expands outward from the given center indices as long as characters match.
     * For each valid expansion, we count one more palindrome.
     *
     * @param s The input string
     * @param left The left index of the center
     * @param right The right index of the center
     * @return The count of palindromes with this center
     */
    private int expandAroundCenter(String s, int left, int right) {
        int count = 0;
        int n = s.length();

        // Expand outward as long as characters match and indices are valid
        while (0 <= left && right < n && s.charAt(left) == s.charAt(right)) {
            // Found a palindrome
            count++;

            // Expand outward
            left--;
            right++;
        }

        return count;
    }

    /**
     * Alternative implementation using dynamic programming.
     *
     * This method uses a 2D boolean array dp[i][j] where:
     * - dp[i][j] = true if substring s[i...j] is a palindrome
     * - dp[i][j] = false otherwise
     *
     * The recurrence relation is:
     * - dp[i][j] = true if s[i] == s[j] AND (j-i <= 2 OR dp[i+1][j-1] is true)
     *
     * Base cases:
     * - All single characters are palindromes: dp[i][i] = true for all i
     * - Two adjacent identical characters form palindromes: dp[i][i+1] = true if s[i] == s[i+1]
     *
     * For each palindromic substring found, we increment our counter.
     *
     * Time Complexity: O(n²) where n is the length of the string
     * Space Complexity: O(n²) for the dp array
     *
     * @param s The input string
     * @return The count of palindromic substrings
     */
    public int countSubstringsDP(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int count = 0;
        int len = s.length();
        // Uses additional O(n²) memory to initialize dynamic program
        boolean dp[][] = new boolean[len][];
        for (int i = 0; i < len; ++i) {
            dp[i] = new boolean[len];
            // Base case: all single characters are palindromes -> dp[i][i] = true
            dp[i][i] = true;
            // Two adjacent characters form palindromes
            if (i + 1 < len && s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
            }
        }

        // Count the base cases we've already set
        for (int i = 0; i < len; ++i) {
            if (dp[i][i]) {
                count++; // Single character palindromes
            }
            if (i < len - 1 && dp[i][i+1]) {
                count++; // Two-character palindromes
            }
        }
        
        // Check for palindromes of length 3 or more
        // We need to process by length to ensure dp[i+1][j-1] is calculated before dp[i][j]
        for (int length = 3; length <= len; length++) {
            for (int i = 0; i <= len - length; i++) {
                int j = i + length - 1; // End index of substring
                
                // Check if substring from i to j is a palindrome
                if (s.charAt(i) == s.charAt(j) && dp[i+1][j-1]) {
                    dp[i][j] = true;
                    count++;
                }
            }
        }

        return count;
    }
}
