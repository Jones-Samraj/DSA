class Solution {
    public int totalNumbers(int[] digits) {
        int[] count = new int[10];

        // Store frequency of every digit
        for (int digit : digits) {
            count[digit]++;
        }

        int ans = 0;

        // Hundreds digit
        for (int h = 1; h <= 9; h++) {

            // Tens digit
            for (int t = 0; t <= 9; t++) {

                // Ones digit must be even
                for (int o = 0; o <= 8; o += 2) {

                    // Check whether digits are available
                    if (count[h] == 0 ||
                        count[t] == 0 ||
                        count[o] == 0) {
                        continue;
                    }

                    // If same digit is used multiple times,
                    // we need multiple copies.
                    if (h == t && count[h] < 2) {
                        continue;
                    }

                    if (h == o && count[h] < 2) {
                        continue;
                    }

                    if (t == o && count[t] < 2) {
                        continue;
                    }

                    if (h == t && t == o && count[h] < 3) {
                        continue;
                    }

                    ans++;
                }
            }
        }

        return ans;
    }
}