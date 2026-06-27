# Sliding Window

Sliding window uses two pointers (left and right) to maintain a window over a subarray, expanding and shrinking it efficiently.

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

One pointer expands (right), one shrinks (left). Each element is added and removed at most once → O(n).

---

## Two Types

**Fixed size window** — window size k is given
```java
// build first window of size k
for (int i = 0; i < k; i++) sum += nums[i];
// slide
for (int i = k; i < n; i++) {
    sum += nums[i] - nums[i - k];  // add new, remove old
    ans = Math.max(ans, sum);
}
```

**Variable size window** — find min/max window satisfying condition
```java
int l = 0;
for (int r = 0; r < n; r++) {
    // expand: add nums[r]
    while (window is invalid) {
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
Range sum queries (multiple)                   → Prefix Sum array (O(1) per query)
```

---

## Key Rules

```
Expand right always
Shrink left when window becomes invalid
while loop (not if) when shrinking → find minimum valid window
Each element added/removed at most once → O(n)
```

---

## Problems

| # | Problem | Pattern | Difficulty |
|---|---------|---------|------------|
| LC 209 | Minimum Size Subarray Sum | Variable window, sum >= target | Medium |
