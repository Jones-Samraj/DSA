class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), ans);
        return ans;
    }
    public void backtrack(int[] candidates, int target, int index, List<Integer> current, List<List<Integer>> ans){
        // Target reached
        if (target == 0) {
            ans.add(new ArrayList<>(current));
            return;
        }

        // Try all possible candidates
        for (int i = index; i < candidates.length; i++) {
            // Cannot choose this number
            if (candidates[i] > target) {
                continue;
            }

            // Choose
            current.add(candidates[i]);

            // Reuse the same number -> i
            backtrack(
                candidates,
                target - candidates[i],
                i,
                current,
                ans
            );

            // Undo choice
            current.remove(current.size() - 1);
        }
    }
}