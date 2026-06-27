// ============================================================
// LC 1248 - Count Number of Nice Subarrays
// https://leetcode.com/problems/count-number-of-nice-subarrays/
// ============================================================
// PROBLEM:
//   Given array nums and integer k, return number of subarrays
//   with exactly k odd numbers.
//
// APPROACH: Prefix Sum + HashMap (LC 560 in disguise)
//   KEY INSIGHT:
//   Replace every odd number with 1, even with 0 (num % 2)
//   Now "k odd numbers" = "subarray sum = k"
//   Exact same pattern as LC 560!
//
//   prefixSum = count of odd numbers seen so far
//   At each index ask: has (prefixSum - k) appeared before?
//   If yes → valid subarrays ending here = map[prefixSum - k]
//
//   map.put(0, 1) → handles subarrays starting at index 0
//
// ONLY DIFFERENCE FROM LC 560:
//   prefixSum += num % 2   (instead of prefixSum += num)
//   Everything else identical
//
// Time:  O(n)
// Space: O(n)
// ============================================================

import java.util.HashMap;
import java.util.Map;

class LC1248_CountNiceSubarrays {
    public int numberOfSubarrays(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);  // empty prefix

        int count = 0;
        int prefixSum = 0;

        for (int num : nums) {
            prefixSum += num % 2;  // 1 if odd, 0 if even ← only change from LC560
            count += map.getOrDefault(prefixSum - k, 0);
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

// Dry Run:
// nums=[1,1,2,1,1], k=3
// map={0:1}, prefixSum=0, count=0
//
// i=0: num=1(odd)  prefixSum=1, look for -2 → no,  map={0:1,1:1}
// i=1: num=1(odd)  prefixSum=2, look for -1 → no,  map={0:1,1:1,2:1}
// i=2: num=2(even) prefixSum=2, look for -1 → no,  map={0:1,1:1,2:2}
// i=3: num=1(odd)  prefixSum=3, look for  0 → yes(1), count=1, map={...,3:1}
// i=4: num=1(odd)  prefixSum=4, look for  1 → yes(1), count=2, map={...,4:1}
// return 2 ✅
