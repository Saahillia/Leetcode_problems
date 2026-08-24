class Solution {
    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        Set<Integer> kDistantIndicesSet = new HashSet<>();
        int n = nums.length;

        // Iterate through the array to find all occurrences of 'key'
        for (int j = 0; j < n; j++) {
            if (nums[j] == key) {
                // For each occurrence of 'key' at index 'j',
                // add all indices 'i' such that |i - j| <= k to the set.
                // This means 'i' can range from max(0, j - k) to min(n - 1, j + k).
                int start = Math.max(0, j - k);
                int end = Math.min(n - 1, j + k);

                for (int i = start; i <= end; i++) {
                    kDistantIndicesSet.add(i);
                }
            }
        }

        // Convert the set to a list
        List<Integer> result = new ArrayList<>(kDistantIndicesSet);

        // Sort the list in increasing order
        Collections.sort(result);

        return result;
    }
}