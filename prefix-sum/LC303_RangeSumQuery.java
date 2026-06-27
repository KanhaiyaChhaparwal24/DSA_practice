// ============================================================
// LC 303 - Range Sum Query - Immutable
// https://leetcode.com/problems/range-sum-query-immutable/
// ============================================================
// PROBLEM:
//   Given an integer array, handle multiple queries:
//   return sum of elements between index left and right inclusive.
//
// APPROACH: Prefix Sum
//   1. Precompute prefix[] array once in constructor — O(n)
//   2. Each query answered in O(1):
//      sumRange(left, right) = prefix[right+1] - prefix[left]
//
// KEY INSIGHT:
//   Without prefix sum: each query loops from left to right → O(n)
//   With prefix sum: precompute once → each query is O(1)
//   Multiple queries → prefix sum saves massive time
//
// PREFIX SUM FORMULA:
//   prefix[i] = sum of arr[0..i-1]  (size n+1, index 0 = empty)
//   sum(left, right) = prefix[right+1] - prefix[left]
//
// WHY SIZE n+1:
//   prefix[0] = 0 (empty prefix, no special case needed)
//   Avoids handling left==0 separately
//
// Time:  O(n) build, O(1) per query
// Space: O(n)
// ============================================================

class LC303_RangeSumQuery {
    private int[] prefix;

    public LC303_RangeSumQuery(int[] nums) {
        prefix = new int[nums.length + 1];
        prefix[0] = 0;  // empty prefix
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];  // no special case needed
    }
}

// Example:
// nums   = [-2,  0,  3, -5,  2, -1]
// prefix = [ 0, -2, -2,  1, -4, -2, -3]
//
// sumRange(0,2) = prefix[3] - prefix[0] = 1 - 0 = 1 ✅
// sumRange(2,5) = prefix[6] - prefix[2] = -3-(-2) = -1 ✅
