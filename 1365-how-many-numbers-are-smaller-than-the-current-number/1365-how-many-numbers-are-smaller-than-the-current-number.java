class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        // Step 1: Create a frequency array to count occurrences of each number.
        // The constraints state 0 <= nums[i] <= 100, so an array of size 101 is sufficient.
        int[] counts = new int[101];
        for (int num : nums) {
            counts[num]++;
        }

        // Step 2: Transform the frequency array into a cumulative count array.
        // After this loop, counts[i] will store the total number of elements
        // in the original `nums` array that are less than or equal to `i`.
        for (int i = 1; i < 101; i++) {
            counts[i] += counts[i - 1];
        }

        // Step 3: Build the result array.
        // For each number in the original `nums` array, look up its count.
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            // If nums[i] is 0, there are no numbers smaller than it.
            // Otherwise, counts[nums[i] - 1] gives the total count of numbers
            // less than or equal to nums[i] - 1, which is equivalent to
            // numbers strictly smaller than nums[i].
            if (nums[i] == 0) {
                result[i] = 0;
            } else {
                result[i] = counts[nums[i] - 1];
            }
        }

        return result;
    }
}