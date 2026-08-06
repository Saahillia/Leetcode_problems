class Solution {

    private int getDigitProduct(int number) {
        if (number == 0) {
            return 0; 
        }
        int product = 1;
        int temp = number;
        while (temp > 0) {
            int digit = temp % 10;
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
        while (true) {
            int digitProduct = getDigitProduct(num);
            if (digitProduct % t == 0) {
                return num;
            }
            num++;
        }
    }
}