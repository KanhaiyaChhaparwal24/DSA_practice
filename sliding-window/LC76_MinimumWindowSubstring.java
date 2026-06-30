// ============================================================
// LC 76 - Minimum Window Substring
// https://leetcode.com/problems/minimum-window-substring/
// ============================================================
// PROBLEM:
//   Given strings s and t, return the minimum window substring
//   of s that contains all characters of t (including duplicates).
//   Return "" if no such window exists.
//
// APPROACH: Variable Size Sliding Window + Two HashMaps
//   needMap → frequency of each char required by t
//   window  → frequency of each char in current window
//   need    → count of characters still unsatisfied
//             starts at t.length(), reaches 0 when window is valid
//
//   1. Expand right — add char to window
//      if char is needed AND not yet excess → need--
//   2. When need==0 (window has all of t):
//      - record window if smallest so far
//      - shrink left:
//          remove char from window
//          if removal causes deficit → need++ (stop shrinking)
//   3. Repeat until r reaches end of s
//
// KEY CONDITION EXPLAINED:
//   window.get(c) <= needMap.get(c)  → only count chars actually needed
//     (extra duplicates beyond what's needed don't help satisfy 'need')
//   window.get(c) < needMap.get(c)   → only break when removal causes
//     real deficit (removing an excess duplicate doesn't break validity)
//
// Time:  O(n) — each char added once (right) and removed once (left)
// Space: O(k) — k = number of distinct characters in t
// ============================================================

import java.util.HashMap;
import java.util.Map;

class LC76_MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        // Step 1: build needMap — frequency of each char in t
        Map<Character, Integer> needMap = new HashMap<>();
        for (char c : t.toCharArray())
            needMap.put(c, needMap.getOrDefault(c, 0) + 1);

        int need = t.length();              // chars still unsatisfied
        int start = -1, minLen = s.length() + 1;

        Map<Character, Integer> window = new HashMap<>();

        for (int l = 0, r = 0; r < s.length(); r++) {
            // Step 2: expand right
            char rightChar = s.charAt(r);
            window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);

            // only decrement need if this char is useful (not excess)
            if (needMap.containsKey(rightChar) && window.get(rightChar) <= needMap.get(rightChar)) {
                need--;
            }

            // Step 3: window valid — shrink to find smallest
            while (need == 0) {
                if (r - l + 1 < minLen) {
                    start = l;
                    minLen = r - l + 1;
                }

                char leftChar = s.charAt(l++);
                window.put(leftChar, window.get(leftChar) - 1);

                // only increment need if removal causes real deficit
                if (needMap.containsKey(leftChar) && window.get(leftChar) < needMap.get(leftChar)) {
                    need++;
                }
            }
        }

        return start == -1 ? "" : s.substring(start, start + minLen);
    }
}

// Dry Run:
// s = "ADOBECODEBANC", t = "ABC"
// needMap = {A:1, B:1, C:1}, need=3
//
// Expand until need==0 at r=5 'C' → window="ADOBEC" valid
// Shrink: remove 'A' → need=1 (breaks)
//
// Continue expanding/shrinking...
// Eventually find smallest valid window: "BANC" (start=9, len=4)
//
// return "BANC" ✅
