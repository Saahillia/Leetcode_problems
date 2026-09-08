<h2><a href="https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-i">3622. Maximum Frequency of an Element After Performing Operations I</a></h2><h3>Medium</h3><hr><p>You are given an integer array <code>nums</code> and two integers <code>k</code> and <code>numOperations</code>.</p>

<p>You must perform an <strong>operation</strong> <code>numOperations</code> times on <code>nums</code>, where in each operation you:</p>

<ul>
	<li>Select an index <code>i</code> that was <strong>not</strong> selected in any previous operations.</li>
	<li>Add an integer in the range <code>[-k, k]</code> to <code>nums[i]</code>.</li>
</ul>

<p>Return the <strong>maximum</strong> possible <span data-keyword="frequency-array">frequency</span> of any element in <code>nums</code> after performing the <strong>operations</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,4,5], k = 1, numOperations = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>We can achieve a maximum frequency of two by:</p>

<ul>
	<li>Adding 0 to <code>nums[1]</code>. <code>nums</code> becomes <code>[1, 4, 5]</code>.</li>
	<li>Adding -1 to <code>nums[2]</code>. <code>nums</code> becomes <code>[1, 4, 4]</code>.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [5,11,20,20], k = 5, numOperations = 1</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>We can achieve a maximum frequency of two by:</p>

<ul>
	<li>Adding 0 to <code>nums[1]</code>.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= k &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= numOperations &lt;= nums.length</code></li>
</ul>






# Maximum Frequency of an Element After Performing Operations

## Problem Understanding

You get an array of numbers. You're allowed `numOperations` operations — each operation picks one index (each index usable only once) and changes its value by any amount between `-k` and `+k`.

After all operations, you want as many array elements as possible to hold the **same value**.

Return that maximum count.

### Real-World Analogy

Imagine `numOperations` coupons, where each coupon lets you nudge one product's price up or down by at most `k` rupees.

You want to end up with the largest possible group of products priced identically.

### Common Beginner Misreading

People often assume you must use all `numOperations`.

You **don't**.

You can use fewer operations, or even zero operations, if that gives a better result.

> 💡 **Beginner Note:** "Frequency" simply means **how many times a value appears in the array**.

---

## Pattern Recognition

This is a **counting + sliding window** problem.

It can also be solved using sorting and two pointers, but the counting approach is particularly effective because the values are small and bounded.

### Checklist

* ✓ Values are small and bounded (up to `100,000`) — a red flag that says **"just count directly, don't overthink with binary search."**
* ✓ We need, for every possible target value, how many array elements are close enough to reach it — that's a **range-sum** question, solved with prefix sums.
* ✓ We're optimizing over a bounded space of **target values**, not the array itself.

Since these clues are present, **counting array + prefix sum sliding window** is the strongest choice here.

---

## Concept Explanation

### Prefix Sum Array

A **prefix sum array** is a helper array where `prefix[i]` stores the running total of a count array from index `0` up to `i`.

#### What It Is

```text
prefix[i] = cnt[1] + cnt[2] + ... + cnt[i]
```

#### How It Works

Build it once in `O(maxValue)` time.

Then any range sum:

```text
cnt[lo] + ... + cnt[hi]
```

can be calculated as:

```text
prefix[hi] - prefix[lo - 1]
```

in `O(1)` time.

#### Why It's Useful

Without prefix sums, checking how many numbers fall in `[lo, hi]` for every target would cost `O(range)` each time.

That adds up quickly.

With prefix sums, every range query becomes `O(1)`.

#### Complexity

* **Building the prefix sum:** `O(maxValue)`
* **Each range query:** `O(1)`

#### Analogy

It's like a bank statement that already shows your running balance after each day.

Instead of adding up all transactions between two dates, you simply subtract two balances.

> 💡 **Beginner Note:** "Prefix" means **everything before and including this point**.

---

## Intuition Before Algorithm

### Naive Idea

For every possible target value `T`:

1. Scan the whole array.
2. Count how many elements are already equal to `T`.
3. Count how many other elements are within `[T-k, T+k]`.

This would take:

```text
O(maxValue × n)
```

which is too slow if done carelessly.

### Realization

If we build a count array:

```text
cnt[v] = how many times value v appears
```

then:

> How many elements lie in `[T-k, T+k]`?

becomes a simple **range-sum query** over `cnt`.

And range sums can be calculated instantly using a prefix sum array.

Therefore, instead of rescanning the entire array for every `T`, we can use the prefix sum to calculate the number of reachable elements efficiently.

---

## Why This Algorithm?

* Values are capped at `100,000` according to the constraint, so a counting array of that size is cheap.
* We need repeated range-sum queries — one for each candidate target.
* Prefix sums are specifically designed to make repeated range-sum queries fast.
* No sorting is required.

> 💡 **Interview Tip:** Whenever a problem's **values**, rather than its array length, are small and bounded, check whether you can count by value instead of manipulating the array directly.

---

## Why NOT Other Approaches?

| Approach                                                          |                  Time |             Space | Why Not?                                                                                                                                                   |
| ----------------------------------------------------------------- | --------------------: | ----------------: | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Brute force — try every target and scan the whole array each time |     `O(n × maxValue)` |            `O(1)` | Too slow — `100,000 × 100,000` is **10 billion operations**.                                                                                               |
| Sort + two-pointer sliding window on the array                    |          `O(n log n)` |            `O(n)` | Also correct and works here, but requires more bookkeeping, such as tracking duplicate runs inside a window, for no real speed benefit at this input size. |
| **Counting array + prefix sum (chosen)**                          | **`O(maxValue + n)`** | **`O(maxValue)`** | Simple, direct, and fast — no sorting needed, and the value range is small enough to just count over.                                                      |

---

## Dry Thought Process

Take:

```text
nums = [1, 4, 5]
k = 1
numOperations = 2
```

If we try target:

```text
T = 4
```

* Value `4` is already there, so it is **free** and requires no operation.
* Values `3` and `5` could reach `4` with one operation each.
* Value `5` exists in the array, so using `1` of our `2` operations on it gets us a second `4`.
* Therefore, the frequency becomes `2`.

Now try:

```text
T = 1
```

Nothing else is close enough.

We would need values `0` or `2`, but neither exists.

So target `4` (or `5`, symmetrically) wins over target `1`.

---

## Approach

1. Count how many times each value from `1` to `100,000` appears using a `cnt` array.
2. Build a prefix sum over `cnt`.
3. For every possible target value `T` from `1` to `100,000`:

   * Find how many array elements fall in `[T-k, T+k]` using the prefix sum. This is `windowSum`.
   * Subtract the elements already equal to `T`. These are stored in `reachableExtra` and represent the elements that would need an operation.
   * The achievable frequency at `T` is:

     * Elements already at `T`
     * Plus as many reachable extra elements as `numOperations` allows.
4. Take the best value across all possible targets.

---

## Algorithm

### Step 1: Build the Count Array

Build:

```text
cnt[v]
```

for every value `v` from `1` to `100,000`.

**Reason:** We need to know how many elements sit at each exact value.

---

### Step 2: Build the Prefix Sum

Build:

```text
prefix[v] = prefix[v - 1] + cnt[v]
```

**Reason:** This enables `O(1)` range-sum lookups.

---

### Step 3: Calculate the Reachable Range

For each target `T`, calculate:

```text
lo = max(1, T - k)
hi = min(100000, T + k)
```

**Reason:** We clip the search window to valid value bounds.

---

### Step 4: Calculate the Window Sum

Calculate:

```text
windowSum = prefix[hi] - prefix[lo - 1]
```

**Reason:** This counts all elements that **could become `T`**.

---

### Step 5: Remove Elements Already Equal to the Target

Calculate:

```text
reachableExtra = windowSum - cnt[T]
```

**Reason:** Elements already equal to `T` don't need an operation, so we exclude them from the elements that require an operation.

---

### Step 6: Calculate the Achievable Frequency

Calculate:

```text
achievable = cnt[T] + min(numOperations, reachableExtra)
```

**Reason:**

* You can't spend more operations than you have.
* You can't convert more elements than are actually reachable.

---

### Step 7: Track the Maximum

Track the maximum `achievable` across all possible target values `T`.

---

## Visualization

Consider:

```text
nums = [1, 4, 5]
k = 1
numOperations = 2
```

The count array contains:

```text
cnt[1] = 1
cnt[4] = 1
cnt[5] = 1
```

All other values have frequency `0`.

| Target `T` | Window `[lo, hi]` | `windowSum` | `cnt[T]` | `reachableExtra` | `achievable` |
| ---------: | ----------------: | ----------: | -------: | ---------------: | -----------: |
|        `1` |          `[1, 2]` |         `1` |      `1` |              `0` |          `1` |
|        `4` |          `[3, 5]` |         `2` |      `1` |              `1` |      **`2`** |
|        `5` |          `[4, 6]` |         `2` |      `1` |              `1` |      **`2`** |

### Result

```text
Best answer = 2
```

---

## Code

```java
class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int maxV = 100000;

        int[] cnt = new int[maxV + 2];

        for (int num : nums) {
            cnt[num]++;
        }

        int[] prefix = new int[maxV + 2];

        for (int i = 1; i <= maxV; i++) {
            prefix[i] = prefix[i - 1] + cnt[i];
        }

        int ans = 0;

        for (int t = 1; t <= maxV; t++) {
            int lo = Math.max(1, t - k);
            int hi = Math.min(maxV, t + k);

            int windowSum = prefix[hi] - prefix[lo - 1];

            int reachableExtra = windowSum - cnt[t];

            int achievable =
                cnt[t] + Math.min(numOperations, reachableExtra);

            ans = Math.max(ans, achievable);
        }

        return ans;
    }
}
```

---

## Line-by-Line Explanation

### 1. Create the Count Array

```java
int[] cnt = new int[maxV + 2];
```

**What it does:**
Creates an array to count occurrences of each value.

**Why we need it:**
We need to know, for any value, exactly how many times it appears.

**Example:**

For:

```text
nums = [1, 4, 5]
```

we get:

```text
cnt[1] = 1
cnt[4] = 1
cnt[5] = 1
```

Everything else is `0`.

---

### 2. Fill the Count Array

```java
for (int num : nums) {
    cnt[num]++;
}
```

**What it does:**
Fills the count array from the input.

**Why we need it:**
This is the raw data our whole solution depends on.

**Example:**

Looping over:

```text
[1, 4, 5]
```

sets those three positions to `1` each.

---

### 3. Build the Prefix Sum

```java
prefix[i] = prefix[i - 1] + cnt[i];
```

**What it does:**
Builds a running total.

**Why we need it:**
It lets us compute any range sum in `O(1)` instead of re-adding every value every time.

**Example:**

```text
prefix[5] = prefix[4] + cnt[5]
          = 2 + 1
          = 3
```

This represents the running total through value `5`.

---

### 4. Calculate the Valid Range

```java
int lo = Math.max(1, t - k);
int hi = Math.min(maxV, t + k);
```

**What it does:**
Clips the reachable value range to valid array bounds.

**Why we need it:**
It prevents looking outside the count array, since values below `1` or above `100,000` don't exist.

**Example:**

If:

```text
t = 1
k = 5
```

then:

```text
t - k = -4
```

After clipping:

```text
lo = 1
```

---

### 5. Calculate the Window Sum

```java
int windowSum = prefix[hi] - prefix[lo - 1];
```

**What it does:**
Counts all elements whose value lies in `[lo, hi]`.

**Why we need it:**
This tells us how many elements **could be turned into `t`**.

**Example:**

For:

```text
t = 4
k = 1
```

we get:

```text
lo = 3
hi = 5
```

Therefore:

```text
windowSum = prefix[5] - prefix[2]
```

This counts the values in the range `[3, 5]`.

For:

```text
nums = [1, 4, 5]
```

the values `4` and `5` count, giving:

```text
windowSum = 2
```

---

### 6. Calculate Reachable Extra Elements

```java
int reachableExtra = windowSum - cnt[t];
```

**What it does:**
Removes the elements already equal to `t` from the count.

**Why we need it:**
Only the **other** elements need an operation to become `t`.

**Example:**

```text
windowSum = 2
cnt[4] = 1
```

Therefore:

```text
reachableExtra = 2 - 1 = 1
```

Only value `5` needs to be converted.

---

### 7. Calculate the Achievable Frequency

```java
int achievable = cnt[t] + Math.min(numOperations, reachableExtra);
```

**What it does:**
Adds as many convertible elements as our available operations allow.

**Why we need it:**
This is the actual maximum frequency reachable for this specific target.

**Example:**

```text
cnt[4] = 1
numOperations = 2
reachableExtra = 1
```

Therefore:

```text
achievable = 1 + min(2, 1)
           = 1 + 1
           = 2
```

> 💡 **Beginner Note:** `Math.min` and `Math.max` simply pick the smaller or larger of two numbers. Here, they are used to enforce limits — don't exceed the available operations and don't go outside the valid array bounds.

---

## Dry Run

Consider:

```text
nums = [5, 11, 20, 20]
k = 5
numOperations = 1
```

The count array contains:

```text
cnt[5] = 1
cnt[11] = 1
cnt[20] = 2
```

| Target `T` | `[lo, hi]` | `windowSum` | `cnt[T]` | `reachableExtra` | `achievable` |
| ---------: | ---------: | ----------: | -------: | ---------------: | -----------: |
|        `5` |  `[1, 10]` |         `1` |      `1` |              `0` |          `1` |
|       `11` |  `[6, 16]` |         `1` |      `1` |              `0` |          `1` |
|       `20` | `[15, 25]` |         `2` |      `2` |              `0` |          `2` |

### Result

```text
Best = 2
```

This matches the expected output.

---

## Complexity Analysis

### Time Complexity

```text
O(maxValue + n)
```

Why?

* One pass over the array to build the count array: `O(n)`
* One pass to build the prefix sum: `O(maxValue)`
* One pass to scan all possible targets: `O(maxValue)`

Therefore:

```text
O(n + maxValue)
```

Since `maxValue` is fixed at `100,000`, this is effectively linear.

### Space Complexity

```text
O(maxValue)
```

The solution uses:

* `cnt` array
* `prefix` array

Both depend on the maximum possible value.

---

## Edge Cases

### 1. `numOperations = 0`

No conversions are allowed.

Therefore, the answer becomes the maximum frequency already present in the array.

The code handles this because:

```java
Math.min(0, reachableExtra) = 0
```

---

### 2. `k = 0`

Only exact matches count because the reachable window shrinks to just `T`.

Therefore:

```text
reachableExtra = 0
```

automatically.

---

### 3. All Elements Are Identical

If all elements have the same value:

```text
cnt[T] = n
```

The answer is `n` regardless of the number of operations.

---

### 4. Single Element Array

The answer is always:

```text
1
```

because there is nothing else to combine with.

---

### 5. Large Values Near `100,000` With Large `k`

The code uses:

```java
hi = Math.min(maxV, t + k);
```

This correctly clips the upper bound instead of going out of bounds.

---

## Key Takeaways

* ✔ When **values**, rather than array length, are small and bounded, count by value instead of manipulating the array directly.
* ✔ Prefix sums turn repeated **"how many things are in this range?"** questions into `O(1)` lookups.
* ✔ Reframe **"which elements do I convert?"** as **"how many extra elements are within reach of each candidate target?"**

---

## Similar Problems

### LeetCode 3347 — Maximum Frequency of an Element After Performing Operations II

Same basic idea, but values can go up to `10^9`.

Because of that, we can no longer allocate a raw counting array based on the value.

The solution therefore requires:

* Coordinate compression
* A difference-array technique

---

### LeetCode 1838 — Frequency of the Most Frequent Element

Has the same general **"how many operations are needed to make elements equal?"** flavor.

It is solved using:

* Sorting
* Sliding window

---

### LeetCode 2009 — Minimum Number of Operations to Make Array Continuous

Another problem involving the same general pattern of counting elements within reachable ranges.

---

## Interview Insights

### 1. Notice the Value Range

Interviewers expect you to notice the value range constraint:

```text
100,000
```

If you jump straight to sorting and two pointers without noticing the bound, that's still fine because that approach works.

However, the counting approach is faster and can be simpler to explain given this input size.

---

### 2. Common Follow-Up

**Question:**

> What if `nums[i]` could be up to `10^9`?

That's essentially the **"II" version** of this problem.

You can no longer allocate a counting array of size `10^9`.

The solution then requires:

* Coordinate compression
* Difference arrays

---

### 3. Common Mistake

A common mistake is forgetting that **unused operations are allowed**.

Some candidates incorrectly assume that all `numOperations` operations must be used.

They don't.

You can use fewer operations, or even zero, if that produces the best answer.

---

### 4. General Pattern

This **range-counting-with-prefix-sums** pattern appears anywhere you're asked:

> **"For every possible X, how many elements are within some distance of X?"**

Examples include:

* Meeting scheduling
* Coverage problems
* Closest-pair-within-range problems

Recognizing this pattern can help you quickly identify when a counting array and prefix sum can replace repeated scanning.

---

## Final Pattern to Remember

The core idea can be summarized as:

```text
Count frequencies
       ↓
Build prefix sums
       ↓
Try every target T
       ↓
Find reachable range [T-k, T+k]
       ↓
Count reachable elements
       ↓
Remove elements already equal to T
       ↓
Use at most numOperations conversions
       ↓
Track maximum frequency
```

**Key Formula:**

```text
windowSum       = prefix[hi] - prefix[lo - 1]

reachableExtra  = windowSum - cnt[T]

achievable      = cnt[T] + min(numOperations, reachableExtra)
```

This turns the problem from repeatedly scanning the entire array into a simple bounded-value range-counting problem.
