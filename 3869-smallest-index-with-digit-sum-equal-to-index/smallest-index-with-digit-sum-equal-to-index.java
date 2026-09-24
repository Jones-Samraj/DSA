class Solution {
    public int smallestIndex(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            int num = nums[i];
            int sum = 0;

            // Find digit sum
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }

            // Check digit sum == index
            if (sum == i) {
                return i;
            }
        }

        return -1;
    }
}