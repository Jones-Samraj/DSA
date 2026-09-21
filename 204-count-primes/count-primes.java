class Solution {
    public int countPrimes(int n) {
        int count=0;
        int[] prime=new int[n+1];
        Arrays.fill(prime, 1);
        if(n==0||n==1) return count;
        for(int x=2; x<Math.sqrt(n); x++){
            if(prime[x]==1){
                for(int y=x*x; y<=n; y+=x) prime[y]=0;
            }
        }
        for(int i=2; i<n; i++){
            count+=prime[i];
        }
        return count;
    }
}