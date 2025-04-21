package leetcode;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;

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

    HashMap<Integer, String> symbolMap = new HashMap<>();
    int[] keys;

    public IntegerToRoman() {
        // Standard forms
        symbolMap.put(1, "I");
        symbolMap.put(5, "V");
        symbolMap.put(10, "X");
        symbolMap.put(50, "L");
        symbolMap.put(100, "C");
        symbolMap.put(500, "D");
        symbolMap.put(1000, "M");

        // Subtractive forms
        symbolMap.put(4, "IV");
        symbolMap.put(9, "IX");
        symbolMap.put(40, "XL");
        symbolMap.put(90, "XC");
        symbolMap.put(400, "CD");
        symbolMap.put(900, "CM");

        // Create a sorted array of keys
        Set<Integer> keySet = symbolMap.keySet();
        keys = new int[keySet.size()];
        int i = 0;
        for (int k : keys) {
            keys[i++] = k;
        }
        Arrays.sort(keys);
    }

    /**
     * Converts an integer to a Roman numeral string.
     *
     * @param num The integer to convert (1 <= num <= 3999)
     * @return The Roman numeral representation of the input integer
     * @throws IllegalArgumentException if the input is outside the valid range
     */
    public String intToRoman(int num) {
        // Validate input
        if (num < 1 || 3999 < num) {
            throw new IllegalArgumentException("Input must be between 1 and 3999");
        }

        StringBuilder sb = new StringBuilder();
        // Loop through keys in reverse order to build Roman numeral: O(n) where n is length of num
        for (int i = keys.length - 1; 0 <= i; --i) {
            num = intToRoman(num, i, sb);
        }

        return sb.toString();
    }

    /**
     * Gets the Roman numeral for a particular number and index, appends to the
     * StringBuilder, decrements and returns the input number
     * @param num
     * @param i
     * @param sb
     * @return
     */
    private int intToRoman(int num, int i, StringBuilder sb) {
        int nextKey = keys[i];
        String nextVal = symbolMap.get(nextKey);
        while (nextKey <= num) {
            sb.append(nextVal);
            num -= nextKey;
        }
        return num;
    }
}
