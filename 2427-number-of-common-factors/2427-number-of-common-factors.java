class Solution {
    public int commonFactors(int a, int b) {
        int count = 0;
        // The maximum possible common factor is the minimum of a and b.
        // We only need to check numbers up to this limit.
        int limit = Math.min(a, b); 

        // Iterate from 1 up to the determined limit
        for (int i = 1; i <= limit; i++) {
            // Check if 'i' divides 'a' and 'i' divides 'b'
            if (a % i == 0 && b % i == 0) {
                count++; // If both conditions are true, 'i' is a common factor
            }
        }
        return count; // Return the total number of common factors found
    }
}