// ============================================================
// LC 560 - Subarray Sum Equals K
// https://leetcode.com/problems/subarray-sum-equals-k/
// ============================================================
// PROBLEM:
//   Given an array nums and integer k, return the total number
//   of subarrays whose sum equals k.
//
// APPROACH 1: Brute Force
//   Check every subarray — O(n²)
//
// APPROACH 2: Prefix Sum + HashMap — O(n)
//   KEY INSIGHT:
//   If prefixSum[i] - prefixSum[j] = k
//   then subarray (j+1, i) has sum = k
//   Rearranging: prefixSum[j] = prefixSum[i] - k
//
//   At every index i, ask:
//   "Has (prefixSum - k) appeared before as a prefix sum?"
//   If yes → add its count to answer
//
//   HashMap stores: { prefixSum → how many times seen }
//   map.put(0, 1) → handles subarrays starting at index 0
//                   (empty prefix before array = sum 0, seen once)
//
// Time:  O(n²) brute / O(n) HashMap
// Space: O(1) brute / O(n) HashMap
// ============================================================

import java.util.HashMap;
import java.util.Map;

class LC560_SubarraySumEqualsK {

    // ---------- APPROACH 1: Brute Force O(n²) ----------
    public int subarraySum_BruteForce(int[] nums, int k) {
        int ans = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) ans++;
            }
        }
        return ans;
    }

    // ---------- APPROACH 2: Prefix Sum + HashMap O(n) ----------
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);  // empty prefix — sum 0 seen once

        int count = 0;
        int prefixSum = 0;

        for (int num : nums) {
            prefixSum += num;
            // how many previous prefix sums = prefixSum - k?
            count += map.getOrDefault(prefixSum - k, 0);
            // record current prefix sum
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

// Dry Run (HashMap approach):
// nums=[1,1,1], k=2
// map={0:1}, prefixSum=0, count=0
//
// i=0: prefixSum=1, look for -1 → no, map={0:1,1:1}
// i=1: prefixSum=2, look for  0 → yes(1), count=1, map={0:1,1:1,2:1}
// i=2: prefixSum=3, look for  1 → yes(1), count=2, map={0:1,1:1,2:1,3:1}
// return 2 ✅
