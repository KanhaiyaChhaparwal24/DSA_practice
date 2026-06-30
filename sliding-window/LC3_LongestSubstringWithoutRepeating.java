// ============================================================
// LC 3 - Longest Substring Without Repeating Characters
// https://leetcode.com/problems/longest-substring-without-repeating-characters/
// ============================================================
// PROBLEM:
//   Given a string s, find the length of the longest substring
//   without repeating characters.
//
// APPROACH: Variable Size Sliding Window + Frequency Array
//   1. count[128] — frequency of each ASCII char in current window
//   2. Expand right — increment count of current char
//   3. If current char's count > 1 → duplicate found
//      shrink from left until that char's count drops to 1
//   4. After shrinking, window is valid (no duplicates)
//      record max length
//
// KEY INSIGHT:
//   When duplicate found, the duplicate is ALWAYS the character
//   we just added (s.charAt(r)). So shrink left until THAT
//   character's count returns to 1 — don't need to check other chars.
//
// WHY int[128] INSTEAD OF HashMap:
//   ASCII characters fit in 128 slots — array lookup is O(1)
//   and faster than HashMap. Use HashMap only for Unicode input.
//
// Time:  O(n) — each char added once (right) and removed once (left)
// Space: O(128) = O(1) — fixed size frequency array
// ============================================================

class LC3_LongestSubstringWithoutRepeating {
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int[] count = new int[128];

        for (int l = 0, r = 0; r < s.length(); ++r) {
            ++count[s.charAt(r)];                  // expand: add right char

            while (count[s.charAt(r)] > 1) {        // duplicate of the char we just added
                --count[s.charAt(l++)];             // shrink left until duplicate resolved
            }

            ans = Math.max(ans, r - l + 1);         // window is valid now, record length
        }
        return ans;
    }
}

// Dry Run:
// s = "abcabcbb"
//
// r=0 'a': count[a]=1, no dup, ans=1, window="a"
// r=1 'b': count[b]=1, no dup, ans=2, window="ab"
// r=2 'c': count[c]=1, no dup, ans=3, window="abc"
// r=3 'a': count[a]=2, dup! remove s[0]='a' → count[a]=1, l=1, ans=3, window="bca"
// r=4 'b': count[b]=2, dup! remove s[1]='b' → count[b]=1, l=2, ans=3, window="cab"
// r=5 'c': count[c]=2, dup! remove s[2]='c' → count[c]=1, l=3, ans=3, window="abc"
// r=6 'b': count[b]=2, dup! remove s[3]='a', s[4]='b' → l=5, ans=3
// r=7 'b': count[b]=2, dup! remove s[5]='c', s[6]='b' → l=7, ans=3
//
// return 3 ✅  ("abc" is the longest)
