class Solution {
    public int longestPalindrome(String s) {
        int[] freq = new int[128];

        // Count frequency of every character
        for (char ch : s.toCharArray()) {
            freq[ch]++;
        }

        int ans = 0;
        boolean hasOdd = false;

        for (int count : freq) {

            // Take the largest even part
            ans += (count / 2) * 2;

            // Check if frequency is odd
            if (count % 2 == 1) {
                hasOdd = true;
            }
        }

        // One odd character can be placed in the center
        if (hasOdd) {
            ans++;
        }

        return ans;
    }
}