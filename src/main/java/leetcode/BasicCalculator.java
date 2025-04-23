package leetcode;

import java.util.Stack;

/**
 * Implements a basic calculator to evaluate a simple expression string.
 * The expression string contains non-negative integers, '+', '-', '*', '/' operators,
 * and empty spaces. The integer division should truncate toward zero.
 *
 * LeetCode Problem 227: https://leetcode.com/problems/basic-calculator-ii/
 */
public class BasicCalculator {

    /**
     * Evaluates the given expression string.
     *
     * @param s The expression string.
     * @return The result of the evaluation.
     */
    public int calculate(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // Trim whitespace and append a dummy operator to ensure last number is processed
        s = s.trim() + "+"; 
        int len = s.length();
        Stack<Integer> stack = new Stack<>();
        int currentNumber = 0;
        char lastOperator = '+';

        for (int i = 0; i < len; ++i) {
            char c = s.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c)) {
                // Build the current number
                currentNumber = currentNumber * 10 + (c - '0');
            } else {
                // It's an operator -> Process the previous number based on the lastOperator
                if (lastOperator == '+') {
                    stack.push(currentNumber);
                } else if (lastOperator == '-') {
                    stack.push(-currentNumber);
                } else if (lastOperator == '*') {
                    stack.push(stack.pop() * currentNumber);
                } else if (lastOperator == '/') {
                    // Check for division by zero, although problem constraints might prevent it
                    if (currentNumber == 0) { 
                        throw new ArithmeticException("Division by zero");
                    }
                    stack.push(stack.pop() / currentNumber);
                }
                // Update the last operator and reset the current number for the next segment
                lastOperator = c;
                currentNumber = 0;
            }
        }

        // Sum up all values left in the stack for the final result
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }
}
