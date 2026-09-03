<h2><a href="https://leetcode.com/problems/excel-sheet-column-number">171. Excel Sheet Column Number</a></h2><h3>Easy</h3><hr><p>Given a string <code>columnTitle</code> that represents the column title as appears in an Excel sheet, return <em>its corresponding column number</em>.</p>

<p>For example:</p>

<pre>
A -&gt; 1
B -&gt; 2
C -&gt; 3
...
Z -&gt; 26
AA -&gt; 27
AB -&gt; 28 
...
</pre>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> columnTitle = &quot;A&quot;
<strong>Output:</strong> 1
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> columnTitle = &quot;AB&quot;
<strong>Output:</strong> 28
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> columnTitle = &quot;ZY&quot;
<strong>Output:</strong> 701
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= columnTitle.length &lt;= 7</code></li>
	<li><code>columnTitle</code> consists only of uppercase English letters.</li>
	<li><code>columnTitle</code> is in the range <code>[&quot;A&quot;, &quot;FXSHRXW&quot;]</code>.</li>
</ul>










# Excel Sheet Column Number (LeetCode 171)

## Problem Understanding

You are given a spreadsheet column title (such as `"A"`, `"Z"`, `"AA"`, `"AB"`) and need to return its corresponding column number.

Think of Excel columns: `A, B, C ... Z`, then `AA, AB ...`  
This is analogous to counting in **base 26**, but with a crucial difference: there is no `"0"` digit. The digits range from **1 to 26**, rather than standard **0 to 25**.

> 💡 **Beginner Note:** Base 26 means each position represents a power of 26 (just as base 10 uses powers of 10). While standard base-26 digits range from 0–25, column titles use 1–26 ($A = 1$, not $A = 0$).

---

## Pattern Recognition

This is a **base conversion** problem with non-standard digit indexing.

### Checklist
- [x] **Fixed alphabet (26 letters):** Points directly to base-26 arithmetic.
- [x] **Positional value depends on place:** Just like units, tens, hundreds, each position signifies a power of 26.
- [x] **No leading zero digit ($A = 1$, not $0$):** Confirms it is a **bijective base-26 numeral system**, rather than a standard zero-indexed base-26 system.

---

## Concept Explanation

### Bijective Base-26 Numeral System

- **What it is:** A positional numeral system with base 26 where digits range from $1$ to $26$ instead of $0$ to $25$.
- **How it works:** Each letter's value corresponds to its 1-indexed alphabetical position ($A = 1, B = 2, \dots, Z = 26$). Processing left to right, each step multiplies the accumulated total by 26 and adds the current letter's value.
- **Why it is useful:** It allows spreadsheets (e.g., Excel, Google Sheets) to label infinite columns sequentially without ever needing an explicit "zero" column placeholder.
- **Time Complexity:** Each character is processed once in $O(1)$ operations per character.

> 💡 **Beginner Note:** Compare this to standard decimal numbers:  
> - `"23"` $= 2 \times 10 + 3$  
> - `"AB"` $= 1 \times 26 + 2 = 28$ ($A = 1, B = 2$)

---

## Intuition Before Algorithm

If you treat this as standard base 26 ($A = 0, B = 1, \dots, Z = 25$):
- Single letters would give offset answers unless adjusted.
- Multi-letter columns would fail because there is no zero placeholder — `"AA"` does not represent zero in the units or tens place.

**The Solution:**  
At each step, treat the running total as a number accumulated digit by digit:
$$\text{total} = \text{total} \times 26 + \text{digitValue}$$
where $\text{digitValue} \in [1, 26]$. This naturally handles the bijective nature because we carry the accumulated positional value forward without needing a zero representation.

---

## Why This Algorithm?

A single left-to-right pass, multiplying by 26 and adding the current digit value, requires the theoretical minimum amount of work:
- Every character is visited exactly once.
- Only two arithmetic operations per character.
- $O(1)$ auxiliary space (a single accumulator integer).

> 💡 **Interview Tip:** Any problem requiring string-to-number conversion in an arbitrary base (e.g., binary string to integer, Roman numerals, Excel columns) typically reduces to:
> ```text
> running_total = running_total * base + current_digit_value
> ```

---

## Comparison of Approaches

| Approach | Time Complexity | Space Complexity | Trade-offs & Drawbacks |
| :--- | :---: | :---: | :--- |
| **Right-to-left with powers** | $O(n)$ | $O(1)$ | Works, but `Math.pow` calls introduce unnecessary overhead and floating-point inaccuracies compared to simple integer multiplication. |
| **Reverse string, then loop** | $O(n)$ | $O(n)$ | Wastes memory and execution time creating a reversed string/array when it can be processed directly forward. |
| **Left-to-right running total (Optimal)** | $O(n)$ | $O(1)$ | Minimal operations, zero extra allocations, clean and cache-friendly. |

---

## Dry Thought Process

Take `"AB"`:
1. Initialize `total = 0`.
2. See `'A'` (value 1):  
   $$\text{total} = 0 \times 26 + 1 = 1$$
3. See `'B'` (value 2):  
   $$\text{total} = 1 \times 26 + 2 = 28$$

**Verification:**  
$A = 1, \dots, Z = 26, AA = 27, AB = 28$. Matches expected output.

---

## Algorithm

1. Initialize `result = 0` to hold the cumulative column number.
2. Iterate through each character `c` in `columnTitle` from left to right.
3. Compute `value = c - 'A' + 1` (maps `'A' \to 1, \dots, 'Z' \to 26`).
4. Update `result = result * 26 + value`.
5. Return `result` after completing the loop.

---

## Visual Walkthrough

### Example: Input `"ZY"`

| Step | Char | Value (`c - 'A' + 1`) | Result Before | Calculation | Result After |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **1** | `'Z'` | $26$ | $0$ | $0 \times 26 + 26$ | **26** |
| **2** | `'Y'` | $25$ | $26$ | $26 \times 26 + 25$ | **701** |

---

## Implementation

```java
class Solution {
    public int titleToNumber(String columnTitle) {
        int result = 0;
        for (int i = 0; i < columnTitle.length(); i++) {
            int value = columnTitle.charAt(i) - 'A' + 1;
            result = result * 26 + value;
        }
        return result;
    }
}
