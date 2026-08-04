class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] counts = new int[101];
        for (int num : nums) {
            counts[num]++;
        }
        for (int i = 1; i < 101; i++) {
            counts[i] += counts[i - 1];
        }
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                result[i] = 0;
            } else {
                result[i] = counts[nums[i] - 1];
            }
        }

        return result;
    }
}