# HashMap

A data structure storing key-value pairs with O(1) average time for get/put/containsKey. Critical for fast lookups, frequency counting, and grouping.

---

## When to Reach for HashMap

```
"count frequency"          → Map<element, count>
"find duplicate"           → Map or Set to track seen values
"two elements sum to X"    → Map<value, index>
"group by something"       → Map<key, List<items>>
"have I seen this before"  → containsKey()
```

---

## Essential Methods

```java
map.put(key, value);                                    // insert/update
map.get(key);                                            // null if absent
map.getOrDefault(key, defaultValue);                      // safe get
map.containsKey(key);                                     // check existence
map.computeIfAbsent(key, k -> new ArrayList<>()).add(x);  // for List values
```

`getOrDefault` and `computeIfAbsent` eliminate manual null checks — use them by default.

---

## Common Patterns

### Pattern 1 — Complement lookup (Two Sum style)
```java
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (map.containsKey(complement)) return new int[]{map.get(complement), i};
    map.put(nums[i], i);
}
```
Check complement BEFORE inserting — avoids reusing the same element.

### Pattern 2 — Grouping by signature
```java
Map<String, List<String>> map = new HashMap<>();
for (String s : strs) {
    String key = computeSignature(s);  // sorted chars, or freq count
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
}
```
Used when items need to be grouped by some derived property (anagram signature, etc).

### Pattern 3 — Frequency counting
```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : str.toCharArray())
    freq.put(c, freq.getOrDefault(c, 0) + 1);
```

---

## Key Rules

```
Arrays can't be HashMap keys directly (compared by reference)
  → convert to String via Arrays.toString() or sort + new String()
computeIfAbsent > putIfAbsent + get for List values (one line vs three)
Check existence BEFORE modifying map when avoiding self-pairing
```

---

## Problems

| # | Problem | Pattern | Difficulty |
|---|---------|---------|------------|
| LC 1 | Two Sum | Value → index map, complement lookup | Easy |
| LC 49 | Group Anagrams | Group by sorted/frequency signature | Medium |
