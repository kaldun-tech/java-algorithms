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

    static final char[] operators = {'+', '-', '*', '/'};

    private static boolean isOperator(char c) {
        for (char op : operators) {
            if (op == c) {
                return true;
            }
        }
        return false;
    }

    private static void pushOperator(Stack<Character> s, char c) {
        s.push(c);
    }

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

        // TODO: Implement the calculation logic using a stack
        Stack<Integer> sumStack = new Stack<>();
        int currentNumber = 0;
        int len = s.length();
        char lastOperator = '+';

        for (int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) {
                // Nothing to see here
                continue;
            } else if (isOperator(c) || i == len - 1) {
                // Handle multiplication and division immediately as they have higher precedence
                if (lastOperator == '+') {
                    // Push the number onto the stack
                    stack.push(currentNumber);
                } else if (lastOperator == '-') {
                    // Push the negative
                    stack.push(-1 * currentNumber);
                } if (lastOperator == '*') {
                    // Pop the top, multiply and push back
                    int product = currentNumber * sumStack.pop();
                    sumStack.push(product);
                } else {
                    // Division -> pop, divide by current num, and push back
                    int div = sumStack.pop() / currentNumber;
                    sumStack.push(div);
                }
                // Update last op and reset current number
                lastOperator = c;
                currentNumber = 0;
            } else {
                // Update current number
                int digit = (c - '0');
                currentNumber = currentNumber * 10 + digit;
            }
        }

        // Result is the sum of remaining numbers on the stack
        int sum = 0;
        for (Integer num : sumStack) {
            sum += num;
        }

        return sum;
    }
}
