class Solution {
    public int fib(int n) {
        int a=0;
        int b=1;
        int c=a+b;
        if(n==0) return 0;
        for(int i=2; i<n; i++){
            a=b;
            b=c;
            c=a+b;
        }
        return c;
    }
}