class Solution {

    // Helper method to calculate the product of digits of a given number
    private int getDigitProduct(int number) {
        if (number == 0) {
            return 0; // The product of digits for 0 is 0
        }
        int product = 1;
        int temp = number;
        while (temp > 0) {
            int digit = temp % 10;
            // If any digit is 0, the entire product will be 0.
            // This is important because 0 is divisible by any non-zero t.
            if (digit == 0) {
                return 0;
            }
            product *= digit;
            temp /= 10;
        }
        return product;
    }

    public int smallestNumber(int n, int t) {
        int num = n;
        // Loop indefinitely, incrementing num, until the condition is met
        while (true) {
            int digitProduct = getDigitProduct(num);
            if (digitProduct % t == 0) {
                return num; // Found the smallest number satisfying the condition
            }
            num++; // Move to the next number
        }
    }
}