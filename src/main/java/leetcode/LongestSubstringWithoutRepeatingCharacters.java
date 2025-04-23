package leetcode;

import java.util.HashSet;

/**
 * Solution for LeetCode problem: Longest Substring Without Repeating Characters
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Problem Description:
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class LongestSubstringWithoutRepeatingCharacters {

    /**
     * Finds the length of the longest substring without repeating characters.
     *
     * @param s The input string
     * @return The length of the longest substring without repeating characters
     */
    public int lengthOfLongestSubstring(String s) {
        int longest = 0;
        // Loop through the string O(n) where n is length -> O(n^2)
        for (int i = 0; i < s.length(); ++i) {
            int nextLength = lengthOfLongestSubstringAtIndex(s, i);
            if (longest < nextLength) {
                longest = nextLength;
            }
        }

        return longest;
    }

    /**
     * Gets the longest substring without repeating characters that starts at a particular index
     * @param s
     * @param start
     * @return
     */
    private int lengthOfLongestSubstringAtIndex(String s, int start) {
        // Stores the frequency of characters encountered in a substring
        // Should be less than O(n) where n is string length memory usage, closer to constant
        HashSet<Character> charsSeen = new HashSet<>();
        int length = 0;

        // O(n) iteration over string
        for (int i = start; i < s.length(); ++i) {
            char c = s.charAt(i);
            // O(1) hash lookup
            if (charsSeen.contains(c)) {
                // This character is non-unique so return
                return length;
            } else {
                charsSeen.add(c);
                ++length;
            }
        }

        // All characters are unique!
        return length;
    }
}
