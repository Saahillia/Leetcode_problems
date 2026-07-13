import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> result = new ArrayList<>();
        // Iterate through all possible starting digits (1 to 9)
        for (int startDigit = 1; startDigit <= 9; startDigit++) {
            long currentNum = startDigit; // Use long to handle potential intermediate values
            int nextDigit = startDigit + 1;

            // Build sequential numbers by appending next digits
            while (nextDigit <= 9) {
                currentNum = currentNum * 10 + nextDigit;

                // 1. Check if the generated number exceeds the high limit.
                // If it does, all subsequent numbers for this startDigit will also be too large.
                if (currentNum > high) {
                    break; // Exit the inner loop for this startDigit
                }

                // 2. Check if the generated number is within the [low, high] range.
                if (currentNum >= low) {
                    result.add((int) currentNum);
                }

                nextDigit++;
            }
        }
        // 3. Ensure the list is sorted as required by the problem.
        // The generation approach naturally produces numbers in increasing order,
        // so a final sort might not always be strictly necessary for correctness,
        // but it's good practice to ensure the requirement is met.
        Collections.sort(result);
        return result;
    }
}