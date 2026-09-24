<h2><a href="https://leetcode.com/problems/matrix-diagonal-sum">1677. Matrix Diagonal Sum</a></h2><h3>Easy</h3><hr><p>Given a&nbsp;square&nbsp;matrix&nbsp;<code>mat</code>, return the sum of the matrix diagonals.</p>

<p>Only include the sum of all the elements on the primary diagonal and all the elements on the secondary diagonal that are not part of the primary diagonal.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/08/14/sample_1911.png" style="width: 336px; height: 174px;" />
<pre>
<strong>Input:</strong> mat = [[<strong>1</strong>,2,<strong>3</strong>],
&nbsp;             [4,<strong>5</strong>,6],
&nbsp;             [<strong>7</strong>,8,<strong>9</strong>]]
<strong>Output:</strong> 25
<strong>Explanation: </strong>Diagonals sum: 1 + 5 + 9 + 3 + 7 = 25
Notice that element mat[1][1] = 5 is counted only once.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> mat = [[<strong>1</strong>,1,1,<strong>1</strong>],
&nbsp;             [1,<strong>1</strong>,<strong>1</strong>,1],
&nbsp;             [1,<strong>1</strong>,<strong>1</strong>,1],
&nbsp;             [<strong>1</strong>,1,1,<strong>1</strong>]]
<strong>Output:</strong> 8
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> mat = [[<strong>5</strong>]]
<strong>Output:</strong> 5
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == mat.length == mat[i].length</code></li>
	<li><code>1 &lt;= n &lt;= 100</code></li>
	<li><code>1 &lt;= mat[i][j] &lt;= 100</code></li>
</ul>





# 1572. Matrix Diagonal Sum

## Problem Understanding

You get a square grid of numbers (`mat`). You need to add up two diagonals:

* **Primary diagonal:** top-left to bottom-right.
* **Secondary diagonal:** top-right to bottom-left.

If a cell sits on both diagonals — which only happens at the exact center of an odd-sized grid — **count it once, not twice**.

### Real-World Analogy

Imagine an **X** drawn across a square photo.

You're adding up the pixel values along that X, but the center pixel, where the two lines of the X cross, is counted only once.

### Common Beginner Misreading

People often sum all diagonal cells without checking for the overlap at the center.

This produces a slightly-too-high answer on odd-sized grids.

> 💡 **Beginner Note**
>
> A "diagonal" here means a straight line of cells at 45 degrees.
>
> In an `n × n` grid, the primary diagonal cells are where:
>
> ```text
> row index = column index
> ```

---

## Pattern Recognition

This is a **direct index-math** problem.

No data structure is needed — just a loop and coordinate arithmetic.

### Checklist

* ✓ The grid is square: `n == number of rows == number of columns`.
* ✓ We need position-based access: `(row i, column i)` and `(row i, column n-1-i)`, rather than scanning for a value.
* ✓ There is a known formula relating the row and column index for each diagonal.

Since these clues are present, a **single pass using index arithmetic** is the right choice.

No hash maps, no sorting, and no extra data structures are required.

---

## Concept Explanation

The key idea is:

For every row `i`:

* The primary diagonal cell is:

  ```text
  mat[i][i]
  ```

* The secondary diagonal cell is:

  ```text
  mat[i][n - 1 - i]
  ```

This works because as you move down a row on the secondary diagonal, the column index counts down from `n-1` to `0`, while the row index counts up from `0` to `n-1`.

Therefore:

```text
row + column = n - 1
```

for every secondary diagonal cell.

### The Formula

**What it is:**
A formula that finds the matching column for any row on the secondary diagonal.

```text
column = n - 1 - row
```

**How it works:**
For each row, calculate the mirrored column using `n - 1 - row`.

**Why it's useful:**
It lets us find both diagonal values in one loop without needing a second pass or a search.

**Time complexity:**
`O(1)` to compute the position for each cell because it only requires arithmetic.

### Analogy

Imagine you're walking down the left staircase (primary diagonal) while someone else walks down the right staircase (secondary diagonal) at the same pace.

At step `i`, they are always exactly:

```text
n - 1 - i
```

steps from the right wall.

> 💡 **Beginner Note**
>
> `n - 1` is used because array indices start at `0`.
>
> In a `3 × 3` grid, the valid indices are:
>
> ```text
> 0, 1, 2
> ```
>
> So the last index is:
>
> ```text
> n - 1 = 2
> ```
>
> not `n = 3`.

---

## Intuition Before Algorithm

### Naive Idea

Loop through the entire matrix — every row and every column — and for each cell check:

> "Is this cell on the primary diagonal or the secondary diagonal?"

That means checking:

```text
n²
```

cells when we only actually need `n` cells per diagonal.

### Realization

You don't need to check every cell.

You already know exactly which cell contains each diagonal value by using the row index alone.

So instead of searching, jump directly to the required cells.

### Double-Counting Problem

When `n` is odd, such as a `3 × 3` matrix, the middle cell:

```text
row = column = n / 2
```

belongs to both diagonals.

Its value should only be added **once**.

Therefore, if the loop naturally adds it twice, we must prevent the second addition.

---

## Why This Algorithm?

A single loop from `0` to `n-1` can calculate both diagonal cells directly using formulas.

This performs the entire job:

* In one pass
* Without wasted checks
* Without extra memory

> 💡 **Interview Tip**
>
> Whenever a problem talks about **diagonals of a grid**, look for a formula relating row and column instead of writing nested loops to scan the whole grid.

---

## Why NOT Other Approaches?

| Approach                                               |       Time |      Space | Why Not?                                                                                            |
| ------------------------------------------------------ | ---------: | ---------: | --------------------------------------------------------------------------------------------------- |
| Scan every cell and check if it is on either diagonal  |    `O(n²)` |     `O(1)` | Checks `n²` cells when only `2n - 1` are ever relevant — wasted work.                               |
| Two separate loops, one for each diagonal              |     `O(n)` |     `O(1)` | Works, but requires an extra "if odd, subtract center" step and performs two passes instead of one. |
| **Single loop, both diagonals per iteration (chosen)** | **`O(n)`** | **`O(1)`** | Same total work as two loops, but done in one pass and cleanest to read.                            |

---

## Dry Thought Process

Take:

```text
mat = [
    [1,2,3],
    [4,5,6],
    [7,8,9]
]

n = 3
```

### Row 0

Primary:

```text
mat[0][0] = 1
```

Secondary:

```text
mat[0][2] = 3
```

They are different cells, so add both.

### Row 1

Primary:

```text
mat[1][1] = 5
```

Secondary:

```text
mat[1][1] = 5
```

They are the **same cell**.

Only add it once.

### Row 2

Primary:

```text
mat[2][2] = 9
```

Secondary:

```text
mat[2][0] = 7
```

They are different cells, so add both.

### Total

```text
1 + 3 + 5 + 9 + 7 = 25
```

Matches the expected output.

---

## Approach

Loop through rows from `0` to `n-1`.

For every row `i`:

1. Add:

   ```text
   mat[i][i]
   ```

   which belongs to the primary diagonal.

2. Check whether:

   ```text
   i == n - 1 - i
   ```

   If they are equal, this is the center cell shared by both diagonals, so do **not** add it again.

3. Otherwise, add:

   ```text
   mat[i][n - 1 - i]
   ```

   which belongs to the secondary diagonal.

---

## Algorithm

1. Get:

   ```text
   n = mat.length
   ```

   **Reason:** We need `n` to calculate the mirrored column index.

2. Initialize:

   ```text
   sum = 0
   ```

   **Reason:** This stores the running total of all diagonal values.

3. Loop `i` from `0` to `n-1`.

   **Reason:** This covers every row exactly once.

4. Add:

   ```text
   mat[i][i]
   ```

   to `sum`.

   **Reason:** This is the primary diagonal cell for row `i`.

5. If:

   ```text
   i != n - 1 - i
   ```

   add:

   ```text
   mat[i][n - 1 - i]
   ```

   **Reason:** This is the secondary diagonal cell, but we skip it when it is the same cell as the primary diagonal cell — the center of an odd-sized grid.

6. Return:

   ```text
   sum
   ```

   **Reason:** This is the final answer.

---

## Visualization

For:

```text
mat = [
    [1,2,3],
    [4,5,6],
    [7,8,9]
]
```

| `i` | `mat[i][i]` — Primary | `mat[i][n-1-i]` — Secondary | Same Cell?     |      Running Sum |
| --: | --------------------: | --------------------------: | -------------- | ---------------: |
| `0` |                   `1` |                         `3` | No             |      `1 + 3 = 4` |
| `1` |                   `5` |                         `5` | Yes → add once |      `4 + 5 = 9` |
| `2` |                   `9` |                         `7` | No             | `9 + 9 + 7 = 25` |

### Result

```text
25
```

---

## Code

```java
class Solution {
    public int diagonalSum(int[][] mat) {
        int n = mat.length;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += mat[i][i];              // primary diagonal

            if (i != n - 1 - i) {          // avoid double-counting the center
                sum += mat[i][n - 1 - i];  // secondary diagonal
            }
        }

        return sum;
    }
}
```

---

## Line-by-Line Explanation

### 1. Get the Matrix Size

```java
int n = mat.length;
```

**What it does:**
Stores the size of the square matrix.

**Why we need it:**
It is needed to compute the mirrored column index:

```text
n - 1 - i
```

and to control the loop.

**Example:**

For a `3 × 3` matrix:

```text
n = 3
```

---

### 2. Initialize the Sum

```java
int sum = 0;
```

**What it does:**
Creates a variable to hold the running total.

**Why we need it:**
We add each diagonal value to this variable as we go through the loop.

**Example:**

It starts at:

```text
0
```

and ends at:

```text
25
```

for the example matrix.

---

### 3. Loop Through Every Row

```java
for (int i = 0; i < n; i++)
```

**What it does:**
Repeats the loop body once for each row.

**Why we need it:**
Every row contributes exactly:

* One primary diagonal cell
* One secondary diagonal cell

unless both are the same center cell.

**Example:**

For:

```text
n = 3
```

`i` takes the values:

```text
0, 1, 2
```

---

### 4. Add the Primary Diagonal

```java
sum += mat[i][i];
```

**What it does:**
Adds the primary diagonal value for row `i`.

**Why we need it:**
The primary diagonal always has matching row and column indexes.

```text
row = column
```

**Example:**

At:

```text
i = 1
```

we add:

```text
mat[1][1] = 5
```

---

### 5. Check for the Center Cell

```java
if (i != n - 1 - i)
```

**What it does:**
Checks whether the secondary diagonal cell in this row is different from the primary diagonal cell.

**Why we need it:**
At the center of an odd-sized matrix, both diagonals meet at the same cell.

This condition prevents that cell from being added twice.

**Example:**

For:

```text
i = 1
n = 3
```

we get:

```text
n - 1 - i
= 3 - 1 - 1
= 1
```

Therefore:

```text
i == n - 1 - i
```

The condition is false, so the secondary diagonal value is skipped.

---

### 6. Add the Secondary Diagonal

```java
sum += mat[i][n - 1 - i];
```

**What it does:**
Adds the secondary diagonal value for row `i`.

**Why we need it:**
This formula gives the mirrored column for the secondary diagonal.

**Example:**

For:

```text
i = 0
n = 3
```

we get:

```text
n - 1 - i
= 3 - 1 - 0
= 2
```

Therefore:

```text
mat[0][2] = 3
```

is added.

---

### 7. Return the Sum

```java
return sum;
```

**What it does:**
Returns the final diagonal sum.

**Why we need it:**
This is the answer LeetCode checks.

**Example:**

For the example matrix:

```text
return 25;
```

> 💡 **Beginner Note**
>
> `+=` means **"add this value to the existing variable."**
>
> For example:
>
> ```java
> sum += 5;
> ```
>
> is shorthand for:
>
> ```java
> sum = sum + 5;
> ```

---

## Dry Run

### Input

```text
mat = [
    [1,2,3],
    [4,5,6],
    [7,8,9]
]

n = 3
```

| `i` | `mat[i][i]` | `n-1-i` | `mat[i][n-1-i]` | Same Cell?            | Sum After This Row |
| --: | ----------: | ------: | --------------: | --------------------- | -----------------: |
| `0` |         `1` |     `2` |             `3` | No                    |    `0 + 1 + 3 = 4` |
| `1` |         `5` |     `1` |             `5` | Yes — skip second add |        `4 + 5 = 9` |
| `2` |         `9` |     `0` |             `7` | No                    |   `9 + 9 + 7 = 25` |

### Final Output

**25** ✓ Matches the expected output.

---

## Complexity

### Time Complexity

```text
O(n)
```

The loop runs once per row, and each iteration performs constant-time work.

### Space Complexity

```text
O(1)
```

Only a few variables are used.

No extra data structures are required.

---

## Edge Cases

### 1. 1 × 1 Matrix

```text
[[5]]
```

Here:

```text
i = 0
n - 1 - i = 0
```

Both diagonals point to the same cell.

The cell is added only once.

Result:

```text
5
```

Correct.

---

### 2. Even-Sized Matrix

For example:

```text
4 × 4
```

No cell is shared between the two diagonals.

Therefore, the `if` condition never skips a secondary diagonal value.

Every diagonal cell is added exactly once.

---

### 3. Odd-Sized Matrix

For example:

```text
3 × 3
5 × 5
```

Exactly one cell — the dead center — is shared by both diagonals.

The `if` check correctly prevents double-counting it.

---

### 4. All Values Equal

For example, consider Example 2 where all values are `1`.

For:

```text
n = 4
```

the diagonals have no shared cell.

Therefore, the total number of diagonal cells is:

```text
2n = 8
```

Since every value is `1`:

```text
sum = 8
```

The expected output is `8`.

Correct.

---

## Key Takeaways

* ✔ For diagonal problems, use the formula:

  ```text
  column = n - 1 - row
  ```

  instead of scanning the whole grid.

* ✔ Always watch for double-counting at the center cell of odd-sized square grids.

* ✔ One pass, `O(n)` time, `O(1)` space — this is about as efficient as it gets.

---

## Similar Problems

* **Toeplitz Matrix (#766)**
* **Transpose Matrix (#867)**
* **Rotate Image (#48)**

---

## Interview Insights

### 1. Notice the Double-Counting Risk

Interviewers expect you to notice the double-counting risk yourself without being prompted.

That's the actual test here — not the loop itself.

You should recognize that the center cell of an odd-sized matrix belongs to both diagonals.

---

### 2. Common Follow-Up

**Question:**

> What if the matrix isn't square?

**Answer:**

The problem guarantees that the matrix is square.

For a non-square matrix, primary and secondary diagonals would need to be handled differently because they would not align using the same formula.

---

### 3. Common Mistake

Forgetting the `if` check entirely.

This can pass even-sized test cases because there is no shared center cell.

However, it fails on odd-sized matrices because the center cell gets counted twice.

---

### 4. General Pattern

The same:

```text
row + column = n - 1
```

trick appears anywhere anti-diagonal traversal is needed.

Examples include:

* Diagonal traversal of a matrix
* Checking anti-diagonal wins in tic-tac-toe
* Other grid problems involving mirrored columns

---

## Final Pattern to Remember

For every row `i`:

```text
Primary diagonal:
mat[i][i]

Secondary diagonal:
mat[i][n - 1 - i]
```

Then handle the shared center:

```text
if (i != n - 1 - i)
```

The complete idea is:

```text
For each row
     ↓
Take primary diagonal → mat[i][i]
     ↓
Calculate secondary column → n - 1 - i
     ↓
Check whether both point to the same cell
     ↓
If different → add secondary
     ↓
Continue
```

### Core Formula

```text
Primary   = mat[i][i]
Secondary = mat[i][n - 1 - i]
```

### Core Condition

```text
i != n - 1 - i
```

This prevents the center cell of an odd-sized matrix from being counted twice.
