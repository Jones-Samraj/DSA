
class Solution {

    public int minimumChanges(String s, int k) {

        int n = s.length();

        int[][] cost = new int[n][n];

        // Precompute cost of every substring
        for (int l = 0; l < n; l++) {

            for (int r = l + 1; r < n; r++) {

                int len = r - l + 1;
                int best = Integer.MAX_VALUE;

                // Try every proper divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) continue;

                    int changes = 0;
                    int groups = len / d;

                    // Check each group
                    for (int i = 0; i < d; i++) {

                        for (int t = 0; t < groups / 2; t++) {

                            int left = l + i + t * d;

                            int right = l + i
                                + (groups - 1 - t) * d;

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[l][r] = best;
            }
        }

        // Dynamic Programming
        int INF = 1000000;

        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {

            for (int i = 2 * parts; i <= n; i++) {

                for (int j = 2 * (parts - 1); j <= i - 2; j++) {

                    dp[parts][i] = Math.min(
                        dp[parts][i],
                        dp[parts - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
