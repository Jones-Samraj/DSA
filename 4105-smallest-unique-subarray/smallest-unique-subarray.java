
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        // Build suffix array using a sentinel (0)
        int[] a = new int[N];
        for (int i = 0; i < n; i++) {
            a[i] = nums[i];
        }

        int[] sa = new int[N];
        int[] rank = new int[N];
        int[] temp = new int[N];

        // Initial counting sort
        int maxVal = 100001;
        int[] cnt = new int[Math.max(N, maxVal + 1)];

        for (int x : a) {
            cnt[x]++;
        }

        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        for (int i = N - 1; i >= 0; i--) {
            sa[--cnt[a[i]]] = i;
        }

        int classes = 1;
        rank[sa[0]] = 0;

        for (int i = 1; i < N; i++) {
            if (a[sa[i]] != a[sa[i - 1]]) {
                classes++;
            }
            rank[sa[i]] = classes - 1;
        }

        // Build suffix array using doubling
        for (int len = 1; len < N; len <<= 1) {

            for (int i = 0; i < N; i++) {
                temp[i] = sa[i] - len;
                if (temp[i] < 0) {
                    temp[i] += N;
                }
            }

            Arrays.fill(cnt, 0);

            for (int i = 0; i < N; i++) {
                cnt[rank[temp[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                int x = temp[i];
                sa[--cnt[rank[x]]] = x;
            }

            int[] newRank = new int[N];
            int newClasses = 1;

            newRank[sa[0]] = 0;

            for (int i = 1; i < N; i++) {
                int cur = sa[i];
                int prev = sa[i - 1];

                if (rank[cur] != rank[prev] ||
                    rank[(cur + len) % N] !=
                    rank[(prev + len) % N]) {
                    newClasses++;
                }

                newRank[cur] = newClasses - 1;
            }

            rank = newRank;
            classes = newClasses;

            if (classes == N) {
                break;
            }
        }

        // Build inverse suffix-array positions
        int[] pos = new int[N];

        for (int i = 0; i < N; i++) {
            pos[sa[i]] = i;
        }

        // Kasai's algorithm: calculate LCP
        int[] lcp = new int[N];
        int k = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == 0) {
                k = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + k < n && j + k < n &&
                   nums[i + k] == nums[j + k]) {
                k++;
            }

            lcp[r] = k;

            if (k > 0) {
                k--;
            }
        }

        // Find minimum unique subarray length
        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            int maxLCP = lcp[r];

            if (r + 1 < N) {
                maxLCP = Math.max(maxLCP, lcp[r + 1]);
            }

            int uniqueLength = maxLCP + 1;

            if (uniqueLength <= n - i) {
                ans = Math.min(ans, uniqueLength);
            }
        }

        return ans;
    }
}
