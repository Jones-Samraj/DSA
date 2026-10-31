
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1_000_000_007;

        // right[i][j]: paths entering (i,j) from left
        // down[i][j]: paths entering (i,j) from top
        long[][] right = new long[m][n];
        long[][] down = new long[m][n];

        // Starting cell: count exactly one initial path
        right[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 0) {

                    // Empty cell: robot can move right or down
                    long ways = (right[i][j] + down[i][j]) % MOD;

                    // Move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + ways) % MOD;
                    }

                    // Move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + ways) % MOD;
                    }

                } else {

                    // Mirror entered from left:
                    // Turn down and move below the mirror
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + right[i][j]) % MOD;
                    }

                    // Mirror entered from top:
                    // Turn right and move right of the mirror
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + down[i][j]) % MOD;
                    }
                }
            }
        }

        return (int)((right[m - 1][n - 1]
                    + down[m - 1][n - 1]) % MOD);
    }
}
