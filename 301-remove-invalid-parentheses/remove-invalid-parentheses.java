import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {

        List<String> ans = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        Queue<String> q = new LinkedList<>();

        q.add(s);
        visited.add(s);

        boolean found = false;

        while (!q.isEmpty()) {

            String str = q.poll();

            // Check if current string is valid
            if (isValid(str)) {
                ans.add(str);
                found = true;
            }

            // If valid strings are found,
            // don't remove more characters.
            if (found) {
                continue;
            }

            // Try removing each character
            for (int i = 0; i < str.length(); i++) {

                // Only remove parentheses
                if (str.charAt(i) != '(' &&
                    str.charAt(i) != ')') {
                    continue;
                }

                String next = str.substring(0, i)
                            + str.substring(i + 1);

                if (!visited.contains(next)) {
                    visited.add(next);
                    q.add(next);
                }
            }
        }

        return ans;
    }

    // Check whether parentheses are valid
    private boolean isValid(String s) {

        int count = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                count++;
            }
            else if (c == ')') {
                count--;

                // More ')' than '('
                if (count < 0) {
                    return false;
                }
            }
        }

        return count == 0;
    }
}