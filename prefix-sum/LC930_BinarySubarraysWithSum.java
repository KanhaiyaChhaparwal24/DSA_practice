// ============================================================
// LC 930 - Binary Subarrays With Sum
// https://leetcode.com/problems/binary-subarrays-with-sum/
// ============================================================
// PROBLEM:
//   Given a binary array nums and integer goal, return the
//   number of non-empty subarrays with sum equal to goal.
//
// APPROACH: Prefix Sum + HashMap (identical to LC 560)
//   1. prefixSum = running sum as we iterate
//   2. At each index ask: has (prefixSum - goal) appeared before?
//   3. If yes → add its count to answer
//   4. map.put(0, 1) → handles subarrays starting at index 0
//
// KEY INSIGHT:
//   This is LC 560 with a binary array.
//   Array being binary (0s and 1s) changes nothing about the approach.
//   Same HashMap + prefix sum template applies directly.
//
// LC 560 vs LC 930 vs LC 1248:
//   LC 560:  prefixSum += num        (any integers)
//   LC 930:  prefixSum += num        (binary array, same formula)
//   LC 1248: prefixSum += num % 2    (count odds, not values)
//
// Time:  O(n)
// Space: O(n)
// ============================================================

import java.util.HashMap;
import java.util.Map;

class LC930_BinarySubarraysWithSum {
    public int numSubarraysWithSum(int[] nums, int goal) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);  // empty prefix

        int count = 0;
        int prefixSum = 0;

        for (int num : nums) {
            prefixSum += num;
            count += map.getOrDefault(prefixSum - goal, 0);
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

// Dry Run:
// nums=[1,0,1,0,1], goal=2
// map={0:1}, prefixSum=0, count=0
//
// i=0: prefixSum=1, look for -1 → no,     map={0:1,1:1}
// i=1: prefixSum=1, look for -1 → no,     map={0:1,1:2}
// i=2: prefixSum=2, look for  0 → yes(1), count=1, map={0:1,1:2,2:1}
// i=3: prefixSum=2, look for  0 → yes(1), count=2, map={0:1,1:2,2:2}
// i=4: prefixSum=3, look for  1 → yes(2), count=4, map={0:1,1:2,2:2,3:1}
// return 4 ✅
//
// At i=4: map[1]=2 because prefixSum was 1 at both index 0 and index 1
// So two subarrays end at index 4: [1,0,1] and [0,1,0,1]
