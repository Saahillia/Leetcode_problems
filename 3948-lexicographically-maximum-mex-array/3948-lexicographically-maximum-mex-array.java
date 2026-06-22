class Solution {
    public int[] maximumMEX(int[] nums) {
        int[] dralunetic = nums;

        int n = nums.length;
        int MAX = 100005;

        int[] freq = new int[MAX + 2];
        for (int x : nums) {
            if (x <= MAX) freq[x]++;
        }

        List<Integer> ans = new ArrayList<>();

        int[] seen = new int[MAX + 2];
        int token = 1;

        int i = 0;

        while (i < n) {
            int mex = 0;
            while (freq[mex] > 0) mex++;

            if (mex == 0) {
                ans.add(0);
                freq[nums[i]]--;
                i++;
                continue;
            }

            int need = mex;
            int j = i;

            while (j < n) {
                int x = nums[j];

                freq[x]--;

                if (x < mex && seen[x] != token) {
                    seen[x] = token;
                    need--;
                }

                j++;

                if (need == 0) break;
            }

            ans.add(mex);
            token++;
            i = j;
        }

        int[] res = new int[ans.size()];
        for (int k = 0; k < ans.size(); k++) {
            res[k] = ans.get(k);
        }

        return res;
    }
}