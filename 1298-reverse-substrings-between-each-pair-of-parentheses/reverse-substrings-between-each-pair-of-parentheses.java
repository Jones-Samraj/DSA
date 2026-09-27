import java.util.*;

class Solution {
    public String reverseParentheses(String s) {

        Stack<String> stack = new Stack<>();
        StringBuilder curr = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Save current string
                stack.push(curr.toString());
                curr.setLength(0);

            } else if (ch == ')') {
                // Reverse the current substring
                curr.reverse();

                // Restore previous string
                curr.insert(0, stack.pop());

            } else {
                // Normal character
                curr.append(ch);
            }
        }

        return curr.toString();
    }
}