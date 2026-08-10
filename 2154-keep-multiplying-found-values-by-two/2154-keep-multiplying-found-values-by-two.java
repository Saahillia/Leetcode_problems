class Solution {
    public int findFinalValue(int[] nums, int original) {
        // Convert nums to a HashSet for O(1) average time complexity lookups
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        // Repeatedly double original as long as it exists in the set
        while (numSet.contains(original)) {
            original *= 2;
        }

        return original;
    }
}