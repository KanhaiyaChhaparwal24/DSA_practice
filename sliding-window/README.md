# Sliding Window

Sliding window uses two pointers (left and right) to maintain a window over a contiguous subarray/substring, expanding and shrinking it efficiently — avoiding recomputation from scratch.

---

## Core Concept

```
l = 0
for r in range(n):
    add nums[r] to window

    while window is invalid:
        remove nums[l] from window
        l++

    update answer
```

Each element is added once (right pointer) and removed once (left pointer) → O(n) total.

---

## Two Types

### Type 1 — Fixed Size Window
Window size `k` is given upfront. Slide it across the array.

```java
// build first window
int sum = 0;
for (int i = 0; i < k; i++) sum += nums[i];
int best = sum;

// slide
for (int i = k; i < n; i++) {
    sum += nums[i];        // add new (right)
    sum -= nums[i - k];    // remove old (left)
    best = Math.max(best, sum);
}
```

### Type 2 — Variable Size Window
No fixed size. Expand until invalid/valid, shrink to optimize.

```java
int l = 0;
for (int r = 0; r < n; r++) {
    // expand: add nums[r] to window state

    while (window is invalid or valid-but-shrinkable) {
        // shrink: remove nums[l], l++
    }

    // update answer
}
```

---

## When to use Sliding Window vs Prefix Sum

```
All positive numbers + subarray sum condition  → Sliding Window (O(n))
Has negative numbers + count subarrays         → Prefix Sum + HashMap (O(n))
Range sum queries (multiple, static array)     → Prefix Sum array (O(1) per query)
```

---

## Key Rules

```
Expand right always, every iteration
Shrink left with WHILE (not if) → finds minimum valid window
Each element added/removed at most once → O(n) total
For "contains all chars" problems → use a need/satisfied counter
  instead of comparing two maps directly (O(1) vs O(k) check)
```

---

## The "need counter" trick (LC 76 pattern)

When checking "does window contain all required chars/counts":

```java
// only decrement need if char is USEFUL (not excess)
if (needMap.containsKey(c) && window.get(c) <= needMap.get(c)) need--;

// only increment need if removal causes REAL deficit
if (needMap.containsKey(c) && window.get(c) < needMap.get(c)) need++;
```

This avoids comparing entire maps on every step — O(1) check instead of O(k).

---

## Problems

| # | Problem | Pattern | Difficulty |
|---|---------|---------|------------|
| LC 643 | Maximum Average Subarray I | Fixed window, track max sum | Easy |
| LC 1343 | Subarrays of Size K Above Threshold | Fixed window, sum >= threshold*k | Medium |
| LC 209 | Minimum Size Subarray Sum | Variable window, sum >= target | Medium |
| LC 3 | Longest Substring Without Repeating Chars | Variable window, frequency array | Medium |
| LC 76 | Minimum Window Substring | Variable window, two HashMaps, need counter | Hard |
