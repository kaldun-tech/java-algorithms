package com.kaldun.leetcode;

/**
 * LeetCode Problem 12: Integer to Roman
 * 
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * 
 * Symbol       Value
 * I            1
 * V            5
 * X            10
 * L            50
 * C            100
 * D            500
 * M            1000
 * 
 * For example, 2 is written as II in Roman numeral, just two ones added together.
 * 12 is written as XII, which is simply X + II.
 * The number 27 is written as XXVII, which is XX + V + II.
 * 
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is written as IV.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * 
 * There are six instances where subtraction is used:
 * - I can be placed before V (5) and X (10) to make 4 and 9.
 * - X can be placed before L (50) and C (100) to make 40 and 90.
 * - C can be placed before D (500) and M (1000) to make 400 and 900.
 * 
 * Constraints:
 * - 1 <= num <= 3999
 */
public class IntegerToRoman {
    
    /**
     * Converts an integer to a Roman numeral string.
     *
     * @param num The integer to convert (1 <= num <= 3999)
     * @return The Roman numeral representation of the input integer
     * @throws IllegalArgumentException if the input is outside the valid range
     */
    public String intToRoman(int num) {
        // TODO: Implement the conversion from integer to Roman numeral
        
        // Validate input
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Input must be between 1 and 3999");
        }
        
        // Placeholder return
        return "";
    }
}
