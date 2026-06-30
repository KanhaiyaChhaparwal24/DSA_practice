// ============================================================
// LC 49 - Group Anagrams
// https://leetcode.com/problems/group-anagrams/
// ============================================================
// PROBLEM:
//   Given an array of strings, group the anagrams together.
//   Anagrams = words with same characters rearranged.
//
// APPROACH 1: Sorting Key
//   1. For each word, sort its characters → identical for all anagrams
//   2. Use sorted string as HashMap key
//   3. Group words sharing the same key
//
// APPROACH 2: Frequency Count Key
//   1. For each word, count frequency of each letter (26 lowercase)
//   2. Convert count array to a String → use as HashMap key
//   3. Group words sharing the same key
//
// KEY INSIGHT:
//   Anagrams have identical character composition.
//   Sorting OR frequency-counting produces the same signature
//   for all anagrams of each other → natural grouping key.
//
// WHY CONVERT TO STRING (frequency approach):
//   Arrays compare by reference in Java, not content.
//   Two different int[] with same values are NOT equal as HashMap keys.
//   Converting to String makes comparison content-based.
//
// Sorting vs Frequency Count:
//   Sorting:    O(k log k) per word, simpler/more intuitive code
//   Frequency:  O(k) per word, faster for large inputs
//   (k = word length)
//
// Time:  O(n * k log k) sorting / O(n * k) frequency, n=words, k=word length
// Space: O(n * k)
// ============================================================

import java.util.*;

class LC49_GroupAnagrams {

    // ---------- APPROACH 1: Sorting Key ----------
    public List<List<String>> groupAnagrams_Sorting(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    // ---------- APPROACH 2: Frequency Count Key ----------
    public List<List<String>> groupAnagrams_FreqCount(String[] strs) {
        Map<String, List<String>> res = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = Arrays.toString(count);
            res.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(res.values());
    }
}

// Dry Run (Sorting approach):
// strs = ["eat","tea","tan"]
//
// "eat" → sort → "aet" → map={aet:["eat"]}
// "tea" → sort → "aet" → map={aet:["eat","tea"]}
// "tan" → sort → "ant" → map={aet:[...], ant:["tan"]}
//
// return [["eat","tea"], ["tan"]] ✅
