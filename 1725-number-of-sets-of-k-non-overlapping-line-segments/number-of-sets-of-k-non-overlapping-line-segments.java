class Solution {
    static final long MOD = 1_000_000_007;
    public int numberOfSets(int n, int k) {
        int N = n + k - 1;
        int R = 2 * k;

        long numerator = 1;
        long denominator = 1;

        for (int i = 1; i <= R; i++) {

            numerator = numerator * (N - R + i) % MOD;

            denominator = denominator * i % MOD;
        }

        long ans = numerator * modInverse(denominator) % MOD;

        return (int) ans;
    }

    static long modInverse(long x) {
        return power(x, MOD - 2);
    }

    static long power(long a, long b) {

        long result = 1;

        while (b > 0) {

            if ((b & 1) == 1) {
                result = result * a % MOD;
            }

            a = a * a % MOD;
            b >>= 1;
        }

        return result;
    }
}