import java.util.*;

class Solution {
    List<Integer>[] adj;
    int[][] up;
    int[] tin, tout, depth, parent;
    int[] baseMask, bit;
    char[] chars;
    int timer, LOG, n;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                        String s, String[] queries) {
        this.n = n;
        chars = s.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[LOG][n];
        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        baseMask = new int[n];
        bit = new int[n + 2];

        buildTree();

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        List<Boolean> ans = new ArrayList<>();

        for (String query : queries) {
            String[] p = query.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                if (chars[u] != c) {
                    int delta = (1 << (chars[u] - 'a'))
                              ^ (1 << (c - 'a'));

                    rangeUpdate(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int lca = getLCA(u, v);

                int mask = getPrefixMask(u)
                         ^ getPrefixMask(v)
                         ^ (1 << (chars[lca] - 'a'));

                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    void buildTree() {
        int[] stack = new int[n];
        int[] next = new int[n];
        Arrays.fill(parent, -1);

        int top = 0;
        stack[0] = 0;
        parent[0] = 0;
        up[0][0] = 0;

        tin[0] = timer++;
        baseMask[0] = 1 << (chars[0] - 'a');

        while (top >= 0) {
            int u = stack[top];

            if (next[u] < adj[u].size()) {
                int v = adj[u].get(next[u]++);

                if (v == parent[u]) continue;

                parent[v] = u;
                up[0][v] = u;
                depth[v] = depth[u] + 1;
                baseMask[v] = baseMask[u]
                            ^ (1 << (chars[v] - 'a'));

                tin[v] = timer++;
                stack[++top] = v;
            } else {
                tout[u] = timer;
                top--;
            }
        }
    }

    void rangeUpdate(int left, int right, int delta) {
        add(left + 1, delta);

        if (right < n) {
            add(right + 1, delta);
        }
    }

    void add(int index, int mask) {
        while (index <= n) {
            bit[index] ^= mask;
            index += index & -index;
        }
    }

    int pointQuery(int index) {
        int mask = 0;

        while (index > 0) {
            mask ^= bit[index];
            index -= index & -index;
        }

        return mask;
    }

    int getPrefixMask(int u) {
        return baseMask[u] ^ pointQuery(tin[u] + 1);
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }
}