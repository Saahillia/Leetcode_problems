<h2><a href="https://leetcode.com/problems/reformat-date">1283. Reformat Date</a></h2><h3>Easy</h3><hr><p>Given a <code>date</code> string in the form&nbsp;<code>Day Month Year</code>, where:</p>

<ul>
	<li><code>Day</code>&nbsp;is in the set <code>{&quot;1st&quot;, &quot;2nd&quot;, &quot;3rd&quot;, &quot;4th&quot;, ..., &quot;30th&quot;, &quot;31st&quot;}</code>.</li>
	<li><code>Month</code>&nbsp;is in the set <code>{&quot;Jan&quot;, &quot;Feb&quot;, &quot;Mar&quot;, &quot;Apr&quot;, &quot;May&quot;, &quot;Jun&quot;, &quot;Jul&quot;, &quot;Aug&quot;, &quot;Sep&quot;, &quot;Oct&quot;, &quot;Nov&quot;, &quot;Dec&quot;}</code>.</li>
	<li><code>Year</code>&nbsp;is in the range <code>[1900, 2100]</code>.</li>
</ul>

<p>Convert the date string to the format <code>YYYY-MM-DD</code>, where:</p>

<ul>
	<li><code>YYYY</code> denotes the 4 digit year.</li>
	<li><code>MM</code> denotes the 2 digit month.</li>
	<li><code>DD</code> denotes the 2 digit day.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> date = &quot;20th Oct 2052&quot;
<strong>Output:</strong> &quot;2052-10-20&quot;
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> date = &quot;6th Jun 1933&quot;
<strong>Output:</strong> &quot;1933-06-06&quot;
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> date = &quot;26th May 1960&quot;
<strong>Output:</strong> &quot;1960-05-26&quot;
</pre>

<p>&nbsp;</p>



# Reformat Date

## Problem Understanding

You get a date written like:

```text
20th Oct 2052
```

The goal is to rewrite it as:

```text
2052-10-20
```

In other words, convert the date from **day-month-year with text** into **year-month-day with numbers**.

### Real-World Analogy

It's like converting a handwritten date on a birthday card into the standardized format a computer database expects.

> 💡 **Beginner Note:** **Parsing** simply means reading a string and pulling out the pieces you need from it.

---

## Pattern Recognition

This is a **string parsing + lookup table** problem, rather than a classic algorithm pattern like Dynamic Programming or Graphs.

### Checklist

* ✅ Fixed, predictable input format: `Day Month Year`
* ✅ Need to convert a small set of text labels (month names) into numbers
* ✅ Need to strip non-numeric suffixes (`st`, `nd`, `rd`, `th`) from the day

Since these clues are present, **split the string + lookup** is the right approach.

---

## Concept Explanation

### Lookup Table

A lookup table stores key-value relationships so that a value can be retrieved from a known key.

For example:

```text
Jan → 01
Feb → 02
Mar → 03
...
Dec → 12
```

A `HashMap` is one way to implement this:

```java
Map<String, String>
```

However, because the months are a small, fixed list in a known order, we don't actually need a `HashMap`. An array is simpler.

### Array-Based Lookup

We can store the months in calendar order:

```java
String[] months = {
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
};
```

Then:

```text
"Jan" → index 0 → month 1
"Feb" → index 1 → month 2
...
"Oct" → index 9 → month 10
```

Because arrays start at index `0`, we add `1` to the index to get the actual month number.

> 💡 **Beginner Note:** A lookup table is like the index at the back of a book. Instead of checking every page, you jump directly to the information you need.

---

## Intuition Before the Algorithm

### Naive Approach

One option is to write a large `if-else` chain:

```java
if (month.equals("Jan")) {
    ...
} else if (month.equals("Feb")) {
    ...
} else if (month.equals("Mar")) {
    ...
}
```

This works, but it is:

* Long
* Repetitive
* Harder to maintain
* More error-prone

### Better Approach

Since the months always appear in a fixed order from January to December, store them in an array and use their position to determine the month number.

This keeps the solution short and readable.

---

## Why This Algorithm?

The input has a very predictable structure:

```text
Day Month Year
```

So we can:

1. Split the string on spaces.
2. Extract the day, month, and year.
3. Look up the month number using an array.
4. Remove the two-character suffix from the day.
5. Zero-pad the day and month.
6. Combine everything into `YYYY-MM-DD`.

No regex or complicated parsing is necessary.

> 💡 **Interview Tip:** When you have a small, fixed set of labels that map to numbers in a known order, an array is often simpler than a `HashMap`.

---

## Why NOT Other Approaches?

| Approach                   |  Time | Space | Why Not?                                        |
| -------------------------- | ----: | ----: | ----------------------------------------------- |
| Chain of `if-else`         |  O(1) |  O(1) | Works, but 12 branches are ugly and error-prone |
| Regex                      |  O(n) |  O(1) | Overkill — splitting on spaces is simpler       |
| Array + `indexOf()` / scan | O(1)* |  O(1) | Clean, short, and easy to read                  |

*Technically, scanning 12 months takes `O(12)`, but since the number of months never changes, this is effectively **O(1)**.

---

## Dry Thought Process

Consider:

```text
20th Oct 2052
```

### Step 1: Split the String

```text
["20th", "Oct", "2052"]
```

### Step 2: Process the Day

```text
"20th"
```

Remove the last two characters:

```text
"20"
```

The suffix `th` is gone.

### Step 3: Process the Month

```text
"Oct"
```

October is at index `9`.

Since arrays are zero-indexed:

```text
9 + 1 = 10
```

Format it as two digits:

```text
10
```

### Step 4: Process the Year

```text
"2052"
```

The year is already four digits, so no changes are needed.

### Step 5: Combine

```text
2052-10-20
```

---

## Approach

1. Split the input by spaces into day, month, and year.
2. Convert the month name to a number using a fixed array.
3. Remove the last two characters from the day to remove `st`, `nd`, `rd`, or `th`.
4. Zero-pad the day and month to ensure two digits.
5. Combine everything as:

```text
year-month-day
```

---

## Algorithm

### Step 1: Split the Date

```java
String[] parts = date.split(" ");
```

This produces:

```text
["20th", "Oct", "2052"]
```

---

### Step 2: Extract the Parts

```java
String dayPart = parts[0];
String monthPart = parts[1];
String yearPart = parts[2];
```

Now we have:

```text
dayPart   = "20th"
monthPart = "Oct"
yearPart  = "2052"
```

---

### Step 3: Find the Month Number

Store all months in an array:

```java
String[] months = {
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
};
```

Search for the matching month:

```java
int monthIndex = 0;

for (int i = 0; i < months.length; i++) {
    if (months[i].equals(monthPart)) {
        monthIndex = i + 1;
        break;
    }
}
```

For `"Oct"`:

```text
index = 9
month = 9 + 1 = 10
```

---

### Step 4: Format the Month

```java
String month = String.format("%02d", monthIndex);
```

Examples:

```text
5  → "05"
10 → "10"
12 → "12"
```

---

### Step 5: Remove the Day Suffix

```java
String dayDigits = dayPart.substring(0, dayPart.length() - 2);
```

Examples:

```text
"6th"  → "6"
"20th" → "20"
"31st" → "31"
```

The last two characters are always the suffix:

```text
st
nd
rd
th
```

---

### Step 6: Zero-Pad the Day

```java
String day = String.format("%02d", Integer.parseInt(dayDigits));
```

Examples:

```text
"6"  → "06"
"20" → "20"
```

---

### Step 7: Combine Everything

```java
return yearPart + "-" + month + "-" + day;
```

Result:

```text
2052-10-20
```

---

## Visualization

Input:

```text
6th Jun 1933
```

| Step         | Value                                      |
| ------------ | ------------------------------------------ |
| Split        | `["6th", "Jun", "1933"]`                   |
| Month lookup | `"Jun"` → index `5` → `5 + 1 = 6` → `"06"` |
| Day strip    | `"6th"` → remove `"th"` → `"6"` → `"06"`   |
| Year         | `"1933"`                                   |
| Combine      | `"1933-06-06"`                             |

---

## Code

```java
class Solution {
    public String reformatDate(String date) {
        String[] months = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        String[] parts = date.split(" ");

        String dayPart = parts[0];
        String monthPart = parts[1];
        String yearPart = parts[2];

        // Find month number (1-based)
        int monthIndex = 0;

        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(monthPart)) {
                monthIndex = i + 1;
                break;
            }
        }

        String month = String.format("%02d", monthIndex);

        // Strip "st", "nd", "rd", or "th" suffix from day
        String dayDigits = dayPart.substring(0, dayPart.length() - 2);
        String day = String.format("%02d", Integer.parseInt(dayDigits));

        return yearPart + "-" + month + "-" + day;
    }
}
```

---

## Line-by-Line Explanation

### 1. Create the Month Array

```java
String[] months = {
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
};
```

**What it does:**
Creates a fixed list of month abbreviations in calendar order.

**Why we need it:**
The position of each month tells us its number.

**Example:**

```text
months[9] = "Oct"
```

Therefore:

```text
October = 9 + 1 = 10
```

---

### 2. Split the Date

```java
String[] parts = date.split(" ");
```

**What it does:**
Breaks the input into pieces wherever there is a space.

**Why we need it:**
It separates the day, month, and year.

**Example:**

```text
"20th Oct 2052"
```

becomes:

```text
["20th", "Oct", "2052"]
```

---

### 3. Find the Month

```java
if (months[i].equals(monthPart)) {
    monthIndex = i + 1;
    break;
}
```

**What it does:**
Searches for the matching month and stores its calendar number.

**Why `+1`?**

Arrays start at index `0`:

```text
Jan → 0
Feb → 1
Mar → 2
...
Oct → 9
```

But calendar months start at `1`:

```text
Jan → 1
Feb → 2
Mar → 3
...
Oct → 10
```

Therefore:

```java
monthIndex = i + 1;
```

---

### 4. Zero-Pad the Month

```java
String.format("%02d", monthIndex);
```

**What it does:**
Formats the number as exactly two digits.

Examples:

```text
1  → "01"
5  → "05"
10 → "10"
12 → "12"
```

---

### 5. Remove the Day Suffix

```java
dayPart.substring(0, dayPart.length() - 2);
```

**What it does:**
Removes the final two characters from the day.

Examples:

```text
"20th" → "20"
"6th"  → "6"
"31st" → "31"
```

> 💡 **Beginner Note:** `substring(start, end)` grabs characters starting at `start` and ending just before `end`.

So:

```java
dayPart.length() - 2
```

means:

> "Stop two characters before the end."

---

### 6. Parse and Format the Day

```java
String.format("%02d", Integer.parseInt(dayDigits));
```

**What it does:**

1. Converts the day string into an integer.
2. Formats it as two digits.

Examples:

```text
"6"  → 6  → "06"
"20" → 20 → "20"
```

---

## Dry Run

Input:

```text
26th May 1960
```

### Split

```text
["26th", "May", "1960"]
```

### Find Month

`May` is at index `4`.

```text
monthIndex = 4 + 1 = 5
month = "05"
```

### Extract Day

```text
"26th"
```

Remove `th`:

```text
"26"
```

Format:

```text
"26"
```

### Year

```text
"1960"
```

### Final Result

```text
1960-05-26
```

---

## Complexity

### Time Complexity

**O(1)**

The month array contains exactly 12 elements, so scanning it takes at most 12 comparisons.

The input strings are also very small and bounded by the problem constraints.

### Space Complexity

**O(1)**

We only create a fixed number of strings and a fixed-size month array.

---

## Edge Cases

### Single-Digit Day

Input:

```text
6th Jun 1933
```

After removing the suffix:

```text
6
```

Formatting gives:

```text
06
```

Result:

```text
1933-06-06
```

---

### Two-Digit Day

Input:

```text
20th Oct 2052
```

The suffix is removed:

```text
20th → 20
```

No additional padding is required.

Result:

```text
2052-10-20
```

---

### Different Suffixes

The algorithm doesn't need to know whether the suffix is:

```text
st
nd
rd
th
```

It simply removes the final two characters.

Examples:

```text
1st  → 1
2nd  → 2
3rd  → 3
4th  → 4
```

---

### January and December

The loop checks the entire array, so both ends work correctly:

```text
Jan → index 0 → month 01
Dec → index 11 → month 12
```

---

### Four-Digit Year

The year is already provided as four digits according to the constraints, so no additional formatting is necessary.

---

## Key Takeaways

* ✅ Use `split(" ")` when the input has a clean, space-separated format.
* ✅ For a small fixed set of labels with a known order, an array can be simpler than a `HashMap`.
* ✅ Use array position + `1` to convert zero-based indexes into calendar month numbers.
* ✅ `substring()` can remove predictable suffixes.
* ✅ `String.format("%02d", n)` is useful for zero-padding numbers.
* ✅ Always check whether the output requires a fixed number of digits.

---

## Similar Problems

1. **ZigZag Conversion** — String manipulation
2. **String to Integer (atoi)** — String parsing
3. **Projection Area of 3D Shapes** — Simple formatting/lookup-style logic

---

## Interview Insights

This problem is useful in interviews because it tests whether you can recognize a simple data-transformation problem instead of overengineering it.

### What Interviewers May Look For

* Can you identify the fixed structure of the input?
* Can you avoid a large `if-else` chain?
* Can you choose a simple lookup structure?
* Do you remember to zero-pad the day and month?
* Can you explain the complexity clearly?

### Common Follow-Up

> **"What if the date format could vary?"**

For example:

```text
Month Day Year
Year Month Day
Day/Month/Year
```

A more general parser would need to identify the position and format of each component rather than assuming:

```text
Day Month Year
```

### Common Mistakes

* ❌ Forgetting to remove the two-character suffix.
* ❌ Forgetting that arrays are zero-indexed.
* ❌ Forgetting to pad single-digit months.
* ❌ Forgetting to pad single-digit days.
* ❌ Using a large `if-else` chain when a simple lookup structure is sufficient.

---

## General Pattern

The technique used here appears in many problems where you need to convert **text labels into standardized codes**.

Examples include:

```text
"Monday" → 1
"Tuesday" → 2

"Jan" → 01
"Feb" → 02

"USD" → currency code/value
"EUR" → currency code/value
```

The general pattern is:

```text
Input text
    ↓
Split / parse
    ↓
Lookup fixed value
    ↓
Format
    ↓
Combine into required output
```

The main lesson is simple:

> **When the input is structured and the possible labels are fixed, look for a simple parsing + lookup solution before reaching for more complicated techniques.**

<p><strong>Constraints:</strong></p>

<ul>
	<li>The given dates are guaranteed to be valid, so no error handling is necessary.</li>
</ul>
