class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int pos = 0;

        // Find the position of 0
        while (nums[pos] != 0) {
            pos++;
        }

        boolean normal = true;
        boolean reverse = true;

        for (int i = 0; i < n; i++) {
            // Check circular ascending order
            if (nums[(pos + i) % n] != i) {
                normal = false;
            }

            // Check circular descending order from 0
            if (nums[(pos - i + n) % n] != i) {
                reverse = false;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (normal) {
            // Rotate left pos times, or use reversals
            ans = Math.min(pos, n - pos + 2);
        }

        if (reverse) {
            // Reverse plus the minimum rotations
            int k = n - 1 - pos;
            ans = Math.min(ans, Math.min(k, n - k) + 1);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}