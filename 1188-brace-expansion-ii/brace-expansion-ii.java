import java.util.*;

class Solution {
    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> result = parse();

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Parse an expression
    private Set<String> parse() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length() && s.charAt(index) != '}') {

            Set<String> current;

            if (s.charAt(index) == '{') {
                index++; // skip '{'
                current = parse();
                index++; // skip '}'
            } 
            else if (s.charAt(index) == ',') {
                index++; // skip ','
                result.addAll(parse());
                return result;
            } 
            else {
                // lowercase letter
                current = new HashSet<>();
                current.add(String.valueOf(s.charAt(index)));
                index++;
            }

            // Concatenation
            Set<String> temp = new HashSet<>();

            for (String a : result) {
                for (String b : current) {
                    temp.add(a + b);
                }
            }

            result = temp;
        }

        return result;
    }
}