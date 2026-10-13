class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n=nums.length;
        for(int i=0; i<n; i++){
            nums[i]=nums[i]*nums[i];
        }
        Arrays.sort(nums);
        long ans=0;
        for(int i=0; i<n; i++){
            long num=nums[i];
            if(i<(n/2)) ans-=num;
            else ans+=num;
        }
        for(int x:nums){
            System.out.println(x);
        }
        return ans;
    }
}