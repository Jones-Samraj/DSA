import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        // Sorted version of the array
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        /*
         * Give every value a unique hash.
         * Since nums[i] <= 100000, this is enough.
         */
        long[] valueHash = new long[100001];

        for (int i = 1; i <= 100000; i++) {
            valueHash[i] = splitmix64(i);
        }

        // Prefix hash of original and sorted arrays
        long[] pref1 = new long[n + 1];
        long[] pref2 = new long[n + 1];

        for (int i = 0; i < n; i++) {
            pref1[i + 1] = pref1[i] + valueHash[nums[i]];
            pref2[i + 1] = pref2[i] + valueHash[sorted[i]];
        }

        /*
         * bad[i] = 1 when nums[i] > nums[i + 1]
         */
        int[] prefBad = new int[n];

        for (int i = 0; i < n - 1; i++) {
            prefBad[i + 1] = prefBad[i];

            if (nums[i] > nums[i + 1]) {
                prefBad[i + 1]++;
            }
        }

        int answer = 0;

        // k must divide n
        for (int k = 1; k <= n; k++) {

            if (n % k != 0) {
                continue;
            }

            boolean possible = true;

            // Check every block
            for (int start = 0; start < n; start += k) {

                /*
                 * Condition 1:
                 * Original block and sorted block
                 * must contain the same elements.
                 */
                long originalHash =
                        pref1[start + k] - pref1[start];

                long sortedHash =
                        pref2[start + k] - pref2[start];

                if (originalHash != sortedHash) {
                    possible = false;
                    break;
                }

                /*
                 * Condition 2:
                 * The block must be cyclically sorted.
                 *
                 * A cyclically sorted array has at most
                 * one place where a[i] > a[i+1].
                 */

                int descending =
                        prefBad[start + k - 1] - prefBad[start];

                // Check circular connection
                if (nums[start + k - 1] > nums[start]) {
                    descending++;
                }

                if (descending > 1) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                answer += k;
            }
        }

        return answer;
    }

    // Deterministic 64-bit hash
    private long splitmix64(long x) {
        x += 0x9E3779B97F4A7C15L;
        x = (x ^ (x >>> 30)) * 0xBF58476D1CE4E5B9L;
        x = (x ^ (x >>> 27)) * 0x94D049BB133111EBL;
        return x ^ (x >>> 31);
    }
}