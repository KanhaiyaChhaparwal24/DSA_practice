// ============================================================
// LC 1 - Two Sum
// https://leetcode.com/problems/two-sum/
// ============================================================
// PROBLEM:
//   Given array nums and target, return indices of two numbers
//   that add up to target. Each input has exactly one solution.
//
// APPROACH: HashMap (value → index)
//   1. For each number, compute its complement (target - num)
//   2. Check if complement already exists in map
//      → if yes, found the pair, return both indices
//      → if no, store current number with its index, continue
//   3. Single pass — O(n)
//
// KEY INSIGHT:
//   Check complement BEFORE inserting current number.
//   This naturally avoids using the same element twice.
//
// Time:  O(n) — single pass
// Space: O(n) — HashMap stores up to n elements
// ============================================================

import java.util.HashMap;
import java.util.Map;

class LC1_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                return new int[]{map.get(diff), i};
            }
            map.put(nums[i], i);
        }
        return null;  // problem guarantees a solution exists
    }
}

// Dry Run:
// nums=[2,7,11,15], target=9
//
// i=0, nums[0]=2: diff=7, map empty → not found, map={2:0}
// i=1, nums[1]=7: diff=2, map has 2! → return [0,1] ✅
