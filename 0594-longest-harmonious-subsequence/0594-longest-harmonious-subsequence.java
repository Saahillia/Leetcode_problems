class Solution {
    public int findLHS(int[] nums) {
        HashMap<Integer, Integer> count = new HashMap<>();

        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        int maxLength = 0;

        for (int key : count.keySet()) {
            if (count.containsKey(key + 1)) {
                maxLength = Math.max(maxLength, count.get(key) + count.get(key + 1));
            }
        }

        return maxLength;
    }
}