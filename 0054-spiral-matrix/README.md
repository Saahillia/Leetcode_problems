<h2><a href="https://leetcode.com/problems/spiral-matrix">54. Spiral Matrix</a></h2><h3>Medium</h3><hr><p>Given an <code>m x n</code> <code>matrix</code>, return <em>all elements of the</em> <code>matrix</code> <em>in spiral order</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg" style="width: 242px; height: 242px;" />
<pre>
<strong>Input:</strong> matrix = [[1,2,3],[4,5,6],[7,8,9]]
<strong>Output:</strong> [1,2,3,6,9,8,7,4,5]
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/11/13/spiral.jpg" style="width: 322px; height: 242px;" />
<pre>
<strong>Input:</strong> matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
<strong>Output:</strong> [1,2,3,4,8,12,11,10,9,5,6,7]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>m == matrix.length</code></li>
	<li><code>n == matrix[i].length</code></li>
	<li><code>1 &lt;= m, n &lt;= 10</code></li>
	<li><code>-100 &lt;= matrix[i][j] &lt;= 100</code></li>
</ul>






# 54. Spiral Matrix

## Problem Understanding

You get a grid (rows and columns) of numbers.

Return all numbers in **spiral order**:

1. Move right along the top row.
2. Move down the right column.
3. Move left along the bottom row.
4. Move up along the left column.
5. Repeat the same process for the inner layers until every element has been visited.

### Real-World Analogy

Imagine **peeling an orange in one continuous strip**, starting from the outside and working toward the middle.

> 💡 **Beginner Note**
>
> * A **matrix** is simply a list of lists (rows), so `matrix[row][col]` represents one cell.
> * A common misreading is thinking this requires fancy mathematics. It doesn't. It is mainly careful bookkeeping of where you are allowed to walk.

---

## Pattern Recognition

This problem tests **matrix traversal with boundaries**, also called:

* Layer-by-layer traversal
* Simulation

### Checklist

* ✓ Is the input a 2D grid?
* ✓ Do we visit every cell exactly once?
* ✓ Does the path have a fixed repeating shape: **right → down → left → up**?
* ✓ Is there no searching or optimization, only walking in a specific order?

Since all of these are true, **shrinking four boundaries** is a strong choice.

---

## Concept Explanation

### Four Boundaries

Keep four numbers:

```text
top
bottom
left
right
```

They mark the rectangle of cells that have **not been visited yet**.

### How It Works

Walk one side of the remaining rectangle.

Once that side has been completely processed, move its corresponding boundary inward by `1` because that side is finished.

Repeat this for all four sides, then continue with the smaller inner rectangle.

### Why It's Useful

You never need to remember which cells have already been visited.

The four boundaries already tell you exactly which cells are still unvisited.

### Cost

* Each cell is touched exactly once.
* Each boundary movement takes `O(1)` time.

### Analogy

Think of **mowing a lawn in a spiral**.

After mowing each strip, you put up a fence so you don't mow that same strip again.

> 💡 **Beginner Note**
>
> `O(1)` means **"takes the same tiny amount of time no matter how big the input is."**

---

## Intuition Before Algorithm

### Naive Idea

One possible approach is to:

1. Walk step by step.
2. Maintain a separate `visited` grid.
3. Turn right whenever you hit the edge or a cell that has already been visited.

### Why It's Weaker

This approach:

* Requires an extra grid of the same size.
* Checks whether a cell has been visited on every step.

### Realization

The visited cells always form the **outer ring of the remaining rectangle**.

Therefore, instead of explicitly marking cells as visited, we can simply shrink the rectangle.

### Better Idea

Use four boundaries:

```text
top
bottom
left
right
```

No extra `visited` grid is required.

---

## Why This Algorithm?

* No extra grid, so only `O(1)` extra space is required, not counting the answer list.
* No `"visited?"` check is required for every cell.
* The code reads almost exactly like the spiral itself.

> 💡 **Interview Tip**
>
> For grid problems with a **fixed path shape**, think **"shrinking boundaries"** first.

---

## Why NOT Other Approaches?

| Approach                                  |         Time |                      Space | Why Not?                                                         |
| ----------------------------------------- | -----------: | -------------------------: | ---------------------------------------------------------------- |
| Visited grid + turn at walls              |     `O(m·n)` |                   `O(m·n)` | Requires an extra grid and a check on every cell.                |
| Direction array (`dx`, `dy`) + mark cells |     `O(m·n)` | `O(m·n)` or modifies input | Works, but you must mark cells, and changing the input is risky. |
| **Boundaries (chosen)**                   | **`O(m·n)`** |           **`O(1)` extra** | Same speed, less memory, and no per-cell visited checks.         |

> **Note:** The direction-array approach is a competitive alternative. It is **not wrong**, just less efficient in terms of extra space.

---

## Dry Thought Process

Consider a `3 × 3` matrix.

Initial boundaries:

```text
top = 0
bottom = 2
left = 0
right = 2
```

### Step 1 — Top Row

Walk the top row:

```text
1 → 2 → 3
```

That row is finished, so:

```text
top = 1
```

### Step 2 — Right Column

Walk the right column from the **new top**:

```text
6 → 9
```

Then:

```text
right = 1
```

### Step 3 — Bottom Row

Before walking the bottom row, ask:

> Is there still a bottom row left?

Yes.

Walk:

```text
8 → 7
```

Then:

```text
bottom = 1
```

### Step 4 — Left Column

Ask the same question.

Yes.

Walk:

```text
4
```

Then:

```text
left = 1
```

### Step 5 — Inner Layer

Now only the middle remains:

```text
5
```

Repeat the process.

---

## Approach

Use **four boundaries**.

Each loop iteration processes one complete ring:

1. Walk the top row.
2. Walk the right column.
3. Walk the bottom row.
4. Walk the left column.
5. Shrink the corresponding boundary after each side.

### Important Checks

After processing the top row and right column, **check that rows and columns still exist** before walking back along the bottom and left.

These checks are the most important part of the problem and are explained further in the **Edge Cases** section.

---

## Algorithm

1. Set:

   ```text
   top = 0
   bottom = m - 1
   left = 0
   right = n - 1
   ```

   **Reason:** The whole grid is unvisited at the start.

2. While:

   ```text
   top <= bottom && left <= right
   ```

   process one ring.

   **Reason:** This means there is still at least one cell left.

3. Walk from `left → right` on row `top`, then:

   ```text
   top++
   ```

   **Reason:** The top row is finished.

4. Walk from `top → bottom` on column `right`, then:

   ```text
   right--
   ```

   **Reason:** The right column is finished.

5. If:

   ```text
   top <= bottom
   ```

   walk from `right → left` on row `bottom`, then:

   ```text
   bottom--
   ```

   **Reason:** The bottom row may no longer exist, such as when only a single row remains.

6. If:

   ```text
   left <= right
   ```

   walk from `bottom → top` on column `left`, then:

   ```text
   left++
   ```

   **Reason:** The left column may no longer exist, such as when only a single column remains.

7. Return the result list.

---

## Visualization

### Example 1

Input:

```text
[[1,2,3],
 [4,5,6],
 [7,8,9]]
```

|       Step | Side Walked                       | Added     | Boundaries After `(top, bottom, left, right)` |
| ---------: | --------------------------------- | --------- | --------------------------------------------- |
|      Start | –                                 | –         | `(0, 2, 0, 2)`                                |
|          1 | Top row                           | `1, 2, 3` | `(1, 2, 0, 2)`                                |
|          2 | Right column                      | `6, 9`    | `(1, 2, 0, 1)`                                |
|          3 | Bottom row                        | `8, 7`    | `(1, 1, 0, 1)`                                |
|          4 | Left column                       | `4`       | `(1, 1, 1, 1)`                                |
| 5 (Ring 2) | Top row                           | `5`       | `(2, 1, 1, 1)`                                |
|        End | Loop stops because `top > bottom` | –         | –                                             |

### Result

```text
[1,2,3,6,9,8,7,4,5]
```

✅ Correct.

---

## Code

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {

            // 1. left -> right along the top row
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }

            top++;

            // 2. top -> bottom along the right column
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }

            right--;

            // 3. right -> left along the bottom row
            //    only if a row remains
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }

                bottom--;
            }

            // 4. bottom -> top along the left column
            //    only if a column remains
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }

                left++;
            }
        }

        return result;
    }
}
```

> **Note:** LeetCode already imports `java.util.*`, so `List` and `ArrayList` work without an explicit import line.

---

## Line-by-Line Explanation

### 1. Create the Result List

```java
List<Integer> result = new ArrayList<>();
```

**What it does:**
Creates an empty list to collect the answers in spiral order.

**Why we need it:**
The problem requires us to return a list.

**Example:**

After processing the first row of Example 1:

```text
result = [1, 2, 3]
```

---

### 2. Initialize the Four Boundaries

```java
int top = 0;
int bottom = matrix.length - 1;
int left = 0;
int right = matrix[0].length - 1;
```

**What it does:**
Sets the four boundaries around the entire grid.

**Why we need it:**
They mark which part of the matrix is still unvisited.

**Example:**

For a `3 × 4` grid:

```text
top = 0
bottom = 2
left = 0
right = 3
```

---

### 3. Check Whether Cells Remain

```java
while (top <= bottom && left <= right)
```

**What it does:**
Continues while at least one cell is still unvisited.

**Why we need it:**
Once the boundaries cross, every cell has already been processed.

**Example:**

```text
top = 2
bottom = 1
```

Since:

```text
top > bottom
```

the traversal stops.

---

### 4. Walk Across the Top Row

```java
for (int col = left; col <= right; col++) {
    result.add(matrix[top][col]);
}
```

**What it does:**
Walks across the top row from left to right.

**Why we need it:**
This is the first side of the current spiral ring.

**Example:**

If:

```text
top = 0
left = 0
right = 2
```

it adds:

```text
1, 2, 3
```

---

### 5. Move the Top Boundary

```java
top++;
```

**What it does:**
Moves the top boundary down by `1`.

**Why we need it:**
The top row is finished and must not be visited again.

**Example:**

```text
top: 0 → 1
```

---

### 6. Walk Down the Right Column

```java
for (int row = top; row <= bottom; row++) {
    result.add(matrix[row][right]);
}
```

**What it does:**
Walks down the right column, starting below the corner that was already processed.

**Why we need it:**
This is the second side of the current ring.

Starting at the **new `top`** avoids adding the top-right corner twice.

**Example:**

If:

```text
top = 1
bottom = 2
right = 2
```

it adds:

```text
6, 9
```

---

### 7. Move the Right Boundary

```java
right--;
```

**What it does:**
Moves the right boundary one position to the left.

**Why we need it:**
The right column is finished.

**Example:**

```text
right: 2 → 1
```

---

### 8. Process the Bottom Row

```java
if (top <= bottom) {
    for (int col = right; col >= left; col--) {
        result.add(matrix[bottom][col]);
    }

    bottom--;
}
```

**What it does:**
Walks the bottom row from right to left, but only if a row is still available.

**Why we need it:**
Without this check, a single-row matrix could be walked twice.

**Example:**

For:

```text
[[1,2,3]]
```

after processing the top row:

```text
top = 1
bottom = 0
```

Therefore:

```text
top <= bottom
```

is false, so the bottom row is skipped.

---

### 9. Process the Left Column

```java
if (left <= right) {
    for (int row = bottom; row >= top; row--) {
        result.add(matrix[row][left]);
    }

    left++;
}
```

**What it does:**
Walks the left column from bottom to top, but only if a column is still available.

**Why we need it:**
Without this check, a single-column matrix could be walked twice.

**Example:**

For:

```text
[[1],
 [2],
 [3]]
```

after processing the right column:

```text
right = -1
left = 0
```

Therefore:

```text
left <= right
```

is false, so the left column is skipped.

> 💡 **Beginner Note**
>
> * `result.add(x)` puts `x` at the end of the list.
> * `col--` means **"go one step left."**

---

## Dry Run

### Input

```text
[[1,2,3,4],
 [5,6,7,8],
 [9,10,11,12]]
```

This is a `3 × 4` matrix:

```text
Rows = 3
Columns = 4
```

|  Step | Action                        | Added        | `top` | `bottom` | `left` | `right` |
| ----: | ----------------------------- | ------------ | ----: | -------: | -----: | ------: |
| Start | –                             | –            |   `0` |      `2` |    `0` |     `3` |
|     1 | Top row                       | `1, 2, 3, 4` |   `1` |      `2` |    `0` |     `3` |
|     2 | Right column                  | `8, 12`      |   `1` |      `2` |    `0` |     `2` |
|     3 | Bottom row (`1 ≤ 2` ✓)        | `11, 10, 9`  |   `1` |      `1` |    `0` |     `2` |
|     4 | Left column (`0 ≤ 2` ✓)       | `5`          |   `1` |      `1` |    `1` |     `2` |
|     5 | Top row                       | `6, 7`       |   `2` |      `1` |    `1` |     `2` |
|     6 | Right column (empty)          | –            |   `2` |      `1` |    `1` |     `1` |
|     7 | Bottom row: `2 ≤ 1` ✗ skipped | –            |   `2` |      `1` |    `1` |     `1` |
|     8 | Left column: empty range      | –            |   `2` |      `1` |    `2` |     `1` |
|   End | Loop check fails              | –            |     – |        – |      – |       – |

### Result

```text
[1,2,3,4,8,12,11,10,9,5,6,7]
```

✅ Matches the expected output.

---

## Complexity

### Time Complexity

```text
O(m · n)
```

Every cell is added exactly once.

Where:

* `m` = number of rows
* `n` = number of columns

### Space Complexity

```text
O(1) extra
```

Only four boundary integers are used, not counting the output list.

The output list itself must contain `m · n` elements, so that required output storage is not considered extra space.

---

## Edge Cases

### Single Row

```text
[[1,2,3]]
```

The bottom-row check:

```java
top <= bottom
```

prevents a second walk over the same row.

---

### Single Column

```text
[[1],
 [2],
 [3]]
```

The left-column check:

```java
left <= right
```

prevents a second walk over the same column.

---

### Single Cell

```text
[[5]]
```

The top row adds `5`.

All later walks are empty.

Result:

```text
[5]
```

---

### Non-Square Grid

For example, a `3 × 4` matrix.

The two boundary checks handle cases where rows or columns run out at different times.

---

### Negative Numbers

Negative numbers are simply values inside the matrix.

The logic uses indexes for traversal, so negative values do not affect the algorithm.

---

## Key Takeaways

* ✔ **Shrinking boundaries beat a visited grid:** less memory and less work.
* ✔ Start the second side at the **new `top`**, otherwise corners can be added twice.
* ✔ The two `if` checks are what make **single rows and single columns** work correctly.

> **Remember:** For grid walks with a fixed shape, use four boundaries and move each one inward after its corresponding side is completed.

---

## Similar Problems

* **Spiral Matrix II (#59):** Fill a grid in spiral order — same boundaries, reversed job.
* **Spiral Matrix IV (#2326):** Spiral fill with a linked list.
* **Rotate Image (#48):** Layer-by-layer thinking.
* **Set Matrix Zeroes (#73)**
* **Diagonal Traverse (#498)**

---

## Interview Insights

### How to Reason Out Loud

Start by mentioning the visited-grid idea:

> "I could use a visited grid and turn whenever I hit a wall or visited cell."

Then explain the optimization:

> "But the visited cells always form the outer ring, so I can skip the visited grid by shrinking four boundaries."

Interviewers want to hear that **trade-off**.

---

### Follow-Up Questions

#### "Can you do it without extra space?"

→ Yes. This boundary-based solution does that.

#### "What if the matrix is empty?"

→ LeetCode guarantees at least a `1 × 1` matrix, but in real code you could add a check for an empty array.

#### "Can you go counter-clockwise?"

→ Yes. Swap the order in which the four sides are processed.

---

### Common Mistakes

* Forgetting one of the two `if` checks.

  * This is the **#1 bug**.
  * It causes repeated numbers on single-row or single-column inputs.
* Starting the right column at the **old `top`**, which duplicates the corner.
* Mixing up:

  ```text
  matrix.length
  ```

  which represents **rows**, with:

  ```text
  matrix[0].length
  ```

  which represents **columns**.

---

### Where It Shows Up Again

This technique appears in:

* Ring-by-ring grid problems
* Edge-by-edge grid problems
* Layer-based matrix traversal

---

## Blind Spot to Watch

Most people memorize this code but cannot explain **why the two `if` checks exist**.

If an interviewer removes them and asks:

> "What breaks?"

You should immediately think of:

```text
[[1,2,3]]
```

and:

```text
[[1],
 [2],
 [3]]
```

These are the cases that expose the bug.

**Practice explaining those cases, not just memorizing the code.**
