# Prefix Sum

Prefix sum is a technique to answer range sum queries in O(1) after O(n) preprocessing.

---

## Core Concept

```
arr    = [1, 2, 3, 4, 5]
prefix = [0, 1, 3, 6, 10, 15]  ← size n+1, prefix[0]=0

sum(left, right) = prefix[right+1] - prefix[left]
```

Build once in O(n), answer every query in O(1).

---

## Template 1 — Basic Prefix Sum

```java
int[] prefix = new int[n + 1];
prefix[0] = 0;
for (int i = 0; i < n; i++)
    prefix[i + 1] = prefix[i] + nums[i];

// query
int sum = prefix[right + 1] - prefix[left];
```

---

## Template 2 — Prefix Sum + HashMap (subarray count)

```java
Map<Integer, Integer> map = new HashMap<>();
map.put(0, 1);  // ALWAYS start with this

int prefixSum = 0, count = 0;
for (int num : nums) {
    prefixSum += num;                              // or num%2, or custom
    count += map.getOrDefault(prefixSum - k, 0);  // subarrays ending here with sum k
    map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
}
```

---

## Key Rules

```
Always use size n+1 prefix array → prefix[0]=0, no edge cases
map.put(0,1) always → handles subarrays starting at index 0
sum(left,right) = prefix[right+1] - prefix[left]
rightSum at pivot = totalSum - leftSum - nums[i]
num%2 trick → converts "count odds" to "subarray sum = k"
```

---

## How to Identify Prefix Sum Problems

```
"sum of subarray" or "range sum"     → basic prefix sum
"number of subarrays with sum = k"   → prefix sum + HashMap
"pivot index / equal partition"      → prefix sum from both sides
"k odd numbers in subarray"          → num%2 + prefix sum + HashMap
"divisible by k"                     → prefix sum + modulo + HashMap
```

---

## Problems

| # | Problem | Pattern | Difficulty |
|---|---------|---------|------------|
| LC 303 | Range Sum Query Immutable | Basic prefix sum | Easy |
| LC 724 | Find Pivot Index | totalSum - leftSum - nums[i] | Easy |
| LC 560 | Subarray Sum Equals K | Prefix sum + HashMap | Medium |
| LC 1248 | Count Number of Nice Subarrays | num%2 + LC560 pattern | Medium |
