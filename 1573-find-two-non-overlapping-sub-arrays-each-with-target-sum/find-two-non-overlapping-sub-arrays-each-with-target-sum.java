class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;

        int[] best = new int[n];

        int INF = n + 1;

        // Initially, no valid subarray exists
        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }

        int left = 0;
        int sum = 0;

        int ans = INF;
        int minLength = INF;

        for (int right = 0; right < n; right++) {

            sum += arr[right];

            // Shrink window if sum becomes greater than target
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // If current window has target sum
            if (sum == target) {

                int length = right - left + 1;

                // Previous non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(ans, length + best[left - 1]);
                }

                // Store shortest subarray seen so far
                minLength = Math.min(minLength, length);
            }

            // Carry forward previous best
            if (right > 0) {
                best[right] = minLength;
            } else {
                best[right] = minLength;
            }
        }

        return ans == INF ? -1 : ans;
    }
}