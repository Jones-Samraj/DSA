
class Solution {
    int[] answer;
    int[] newParent;
    List<Integer>[] children;
    int[] lastSeen;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        answer = new int[n];
        newParent = parent.clone();
        lastSeen = new int[26];

        Arrays.fill(lastSeen, -1);

        // Build original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Find the closest same-character ancestor
        dfs(0, tree, s);

        // Build the modified tree
        children = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            children[newParent[i]].add(i);
        }

        // Calculate final subtree sizes
        calculateSize(0);

        return answer;
    }

    void dfs(int node, List<Integer>[] tree, String s) {
        int c = s.charAt(node) - 'a';

        int previous = lastSeen[c];

        if (previous != -1) {
            newParent[node] = previous;
        }

        lastSeen[c] = node;

        for (int child : tree[node]) {
            dfs(child, tree, s);
        }

        // Restore ancestor information for other branches
        lastSeen[c] = previous;
    }

    int calculateSize(int node) {
        int size = 1;

        for (int child : children[node]) {
            size += calculateSize(child);
        }

        answer[node] = size;

        return size;
    }
}
