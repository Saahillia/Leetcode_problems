class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int maxV = 100000;
        int[] cnt = new int[maxV + 2];
        for (int num : nums) {
            cnt[num]++;
        }

        int[] prefix = new int[maxV + 2];
        for (int i = 1; i <= maxV; i++) {
            prefix[i] = prefix[i - 1] + cnt[i];
        }

        int ans = 0;
        for (int t = 1; t <= maxV; t++) {
            int lo = Math.max(1, t - k);
            int hi = Math.min(maxV, t + k);
            int windowSum = prefix[hi] - prefix[lo - 1];
            int reachableExtra = windowSum - cnt[t];
            int achievable = cnt[t] + Math.min(numOperations, reachableExtra);
            ans = Math.max(ans, achievable);
        }

        return ans;
    }
}