class Solution {
    public boolean isPossible(int[] nums, int k, long mid) {
        int count = 1;
        long sum = 0;

        for (int num : nums) {
            if (sum + num > mid) {
                count++;
                sum = num;
            } else {
                sum += num;
            }
        }

        return count <= k;
    }

    public int splitArray(int[] nums, int k) {
        long low = 0;
        long high = 0;

        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        while (low < high) {
            long mid = low + (high - low) / 2;
            if (isPossible(nums, k, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return (int) low;
    }
}