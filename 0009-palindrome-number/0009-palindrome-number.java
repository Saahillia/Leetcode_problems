class Solution {
    public boolean isPalindrome(int x) {
        // Inside your isPalindrome method
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int og = x; // Store the original value of x
        int reversedNum = 0;

        while (x > 0) {
            int digit = x % 10;
            x /= 10;
            reversedNum = reversedNum * 10 + digit;
        }
        return og == reversedNum;
    }
}