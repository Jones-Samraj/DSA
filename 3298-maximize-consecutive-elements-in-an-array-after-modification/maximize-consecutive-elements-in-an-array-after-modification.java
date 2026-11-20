
import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        int[] dp = new int[1000002];
        int ans = 0;

        for (int x : nums) {

            int notIncrease = dp[x - 1] + 1;
            int increase = dp[x] + 1;

            dp[x] = Math.max(dp[x], notIncrease);
            dp[x + 1] = Math.max(dp[x + 1], increase);

            ans = Math.max(ans, Math.max(dp[x], dp[x + 1]));
        }

        return ans;
    }
}
