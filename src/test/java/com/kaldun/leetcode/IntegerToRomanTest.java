package com.kaldun.leetcode;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the IntegerToRoman implementation.
 * Tests various scenarios for converting integers to Roman numerals.
 */
public class IntegerToRomanTest {

    private final IntegerToRoman converter = new IntegerToRoman();

    /**
     * Test basic Roman numeral conversions.
     */
    @Test
    public void testBasicConversions() {
        assertEquals("I", converter.intToRoman(1));
        assertEquals("III", converter.intToRoman(3));
        assertEquals("IV", converter.intToRoman(4));
        assertEquals("V", converter.intToRoman(5));
        assertEquals("IX", converter.intToRoman(9));
        assertEquals("X", converter.intToRoman(10));
        assertEquals("L", converter.intToRoman(50));
        assertEquals("C", converter.intToRoman(100));
        assertEquals("D", converter.intToRoman(500));
        assertEquals("M", converter.intToRoman(1000));
    }

    /**
     * Test compound Roman numeral conversions.
     */
    @Test
    public void testCompoundConversions() {
        assertEquals("VI", converter.intToRoman(6));
        assertEquals("VII", converter.intToRoman(7));
        assertEquals("VIII", converter.intToRoman(8));
        assertEquals("XII", converter.intToRoman(12));
        assertEquals("XIV", converter.intToRoman(14));
        assertEquals("XV", converter.intToRoman(15));
        assertEquals("XIX", converter.intToRoman(19));
        assertEquals("XX", converter.intToRoman(20));
        assertEquals("XXVII", converter.intToRoman(27));
        assertEquals("LVIII", converter.intToRoman(58));
    }

    /**
     * Test complex Roman numeral conversions with subtractive notation.
     */
    @Test
    public void testComplexConversions() {
        assertEquals("XL", converter.intToRoman(40));
        assertEquals("XC", converter.intToRoman(90));
        assertEquals("CD", converter.intToRoman(400));
        assertEquals("CM", converter.intToRoman(900));
        assertEquals("MCMXCIV", converter.intToRoman(1994));
        assertEquals("MMMCMXCIX", converter.intToRoman(3999));
    }

    /**
     * Test boundary values for Roman numeral conversions.
     */
    @Test
    public void testBoundaryValues() {
        assertEquals("I", converter.intToRoman(1));
        assertEquals("MMMCMXCIX", converter.intToRoman(3999));
    }

    /**
     * Test invalid input handling.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputLowerBound() {
        converter.intToRoman(0);
    }

    /**
     * Test invalid input handling.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputUpperBound() {
        converter.intToRoman(4000);
    }
}
