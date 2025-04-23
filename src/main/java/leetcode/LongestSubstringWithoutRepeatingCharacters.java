package leetcode;

import java.util.Set;
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
     * O(n) time complexity because of a single pass through the string
     * O(min(m, n)) space complexity because the set size is bounded by the number of unique characters m
     * @param s The input string
     * @return The length of the longest substring without repeating characters
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> seen = new HashSet<>();
        int startIndex = 0;
        int maxLength = 0;

        // Use a sliding window approach
        for (int endIndex = 0; endIndex < s.length(); ++endIndex) {
            char nextChar = s.charAt(endIndex);
            // Slide window right if we have already seen this char
            while (seen.contains(nextChar)) {
                // Slide window to the right
                seen.remove(s.charAt(startIndex));
                ++startIndex;
            }

            seen.add(nextChar);

            // Check if current substring is longer than previous
            int nextLen = endIndex + 1 - startIndex;
            if (maxLength < nextLen) {
                maxLength = nextLen;
            }
        }

        return maxLength;
    }
}
