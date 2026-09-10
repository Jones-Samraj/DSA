class Solution {
    public String countAndSay(int n) {
        String current = "1";

        for (int i = 2; i <= n; i++) {

            StringBuilder next = new StringBuilder();

            int j = 0;

            while (j < current.length()) {

                char digit = current.charAt(j);
                int count = 0;

                // Count consecutive same digits
                while (j < current.length() &&
                       current.charAt(j) == digit) {
                    count++;
                    j++;
                }

                // Add count + digit
                next.append(count);
                next.append(digit);
            }

            current = next.toString();
        }

        return current;
    }
}