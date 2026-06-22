class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // Required by the problem statement
        int[][] zenquarilo = items;

        long B = budget;

        long minPrice = Long.MAX_VALUE;
        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] freq = new int[n + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        int[] multiples = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            for (int m = f; m <= n; m += f) {
                multiples[f] += freq[m];
            }
        }

        // delta -> number of bonus opportunities
        HashMap<Long, Long> map = new HashMap<>();

        for (int[] item : items) {
            int factor = item[0];
            long delta = (long) item[1] - minPrice;

            long d = (long) multiples[factor] - 1; // out-degree

            if (d > 0) {
                map.put(delta, map.getOrDefault(delta, 0L) + d);
            }
        }

        java.util.List<long[]> groups = new java.util.ArrayList<>();
        for (java.util.Map.Entry<Long, Long> e : map.entrySet()) {
            groups.add(new long[]{e.getKey(), e.getValue()});
        }

        groups.sort(java.util.Comparator.comparingLong(a -> a[0]));

        long c = minPrice;

        long answer = B / c; // buy only cheapest item

        long prefixCount = 0; // opportunities before current group
        long prefixCost = 0;  // total delta cost before current group

        for (long[] g : groups) {
            long delta = g[0];
            long cnt = g[1];

            long feasibleMax =
                Math.min(
                    prefixCount + cnt,
                    (B - prefixCost + prefixCount * delta) / (c + delta)
                );

            if (delta < c) {
                if (feasibleMax > prefixCount) {
                    long t = feasibleMax;

                    long extraCost =
                        prefixCost + (t - prefixCount) * delta;

                    long total =
                        t + (B - extraCost) / c;

                    answer = Math.max(answer, total);
                }
            } else {
                long t = prefixCount + 1;

                if (t <= feasibleMax) {
                    long extraCost =
                        prefixCost + (t - prefixCount) * delta;

                    long total =
                        t + (B - extraCost) / c;

                    answer = Math.max(answer, total);
                }
            }

            prefixCount += cnt;
            prefixCost += cnt * delta;
        }

        return (int) answer;
    }
}