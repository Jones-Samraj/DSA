class Solution {

    int n, k;
    int[] prod;
    int[][] cnt;

    // Merge two segments
    void pull(int node) {
        int left = node * 2;
        int right = node * 2 + 1;

        // Product of the complete segment
        prod[node] = (prod[left] * prod[right]) % k;

        // Clear current counts
        for (int r = 0; r < k; r++) {
            cnt[node][r] = 0;
        }

        // Prefixes completely inside left segment
        for (int r = 0; r < k; r++) {
            cnt[node][r] += cnt[left][r];
        }

        // Prefixes containing all of left + prefix of right
        for (int r = 0; r < k; r++) {
            int newRem = (prod[left] * r) % k;
            cnt[node][newRem] += cnt[right][r];
        }
    }

    // Build segment tree
    void build(int node, int l, int r, int[] nums) {

        if (l == r) {
            prod[node] = nums[l] % k;

            cnt[node][prod[node]] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        pull(node);
    }

    // Point update
    void update(int node, int l, int r, int index, int value) {

        if (l == r) {
            prod[node] = value % k;

            for (int i = 0; i < k; i++) {
                cnt[node][i] = 0;
            }

            cnt[node][prod[node]] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        pull(node);
    }

    // Query result for [ql, qr]
    Node query(int node, int l, int r, int ql, int qr) {

        if (qr < l || r < ql) {
            return null;
        }

        if (ql <= l && r <= qr) {

            Node res = new Node(k);

            res.prod = prod[node];

            for (int i = 0; i < k; i++) {
                res.cnt[i] = cnt[node][i];
            }

            return res;
        }

        int mid = (l + r) / 2;

        Node left = query(
            node * 2,
            l,
            mid,
            ql,
            qr
        );

        Node right = query(
            node * 2 + 1,
            mid + 1,
            r,
            ql,
            qr
        );

        return merge(left, right);
    }

    // Merge query nodes
    Node merge(Node left, Node right) {

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        Node res = new Node(k);

        // Complete product
        res.prod = (left.prod * right.prod) % k;

        // Prefixes completely inside left
        for (int r = 0; r < k; r++) {
            res.cnt[r] += left.cnt[r];
        }

        // Prefixes using all of left + prefix of right
        for (int r = 0; r < k; r++) {

            int newRem = (left.prod * r) % k;

            res.cnt[newRem] += right.cnt[r];
        }

        return res;
    }

    static class Node {

        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.n = nums.length;
        this.k = k;

        // Segment tree
        prod = new int[4 * n];

        // cnt[node][remainder]
        cnt = new int[4 * n][k];

        // Build initial tree
        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Apply update
            update(
                1,
                0,
                n - 1,
                index,
                value
            );

            // Query range [start, n-1]
            Node ans = query(
                1,
                0,
                n - 1,
                start,
                n - 1
            );

            result[i] = ans.cnt[x];
        }

        return result;
    }
}