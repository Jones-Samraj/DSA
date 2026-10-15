class Solution {
    public int sumCounts(List<Integer> nums) {
        int n=nums.size();
        int sum=0;
        int count=0;
        for(int i=0; i<n; i++){
            Set<Integer> st = new HashSet<>();
            for(int j=i; j<n; j++){
                st.add(nums.get(j));

                count=st.size();
                System.out.println(count);
                sum+=count*count;
            }
        }
        return sum;
    }
}