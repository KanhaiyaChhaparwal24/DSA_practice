// ============================================================
// LC 724 - Find Pivot Index
// https://leetcode.com/problems/find-pivot-index/
// ============================================================
// PROBLEM:
//   Return the leftmost pivot index where sum of all elements
//   to the left equals sum of all elements to the right.
//   Return -1 if no such index exists.
//
// APPROACH: Prefix Sum (no extra array needed)
//   1. Compute totalSum of entire array
//   2. Track leftSum as you iterate
//   3. At each index i:
//      rightSum = totalSum - leftSum - nums[i]
//      if leftSum == rightSum → pivot found
//   4. Update leftSum += nums[i] and continue
//
// KEY INSIGHT:
//   No need to build prefix array.
//   rightSum = totalSum - leftSum - current element
//   One pass after computing total → O(n) time, O(1) space
//
// FORMULA:
//   leftSum == totalSum - leftSum - nums[i]
//   (left)  ==  (right)
//
// Time:  O(n)
// Space: O(1)
// ============================================================

class LC724_FindPivotIndex {
    public int pivotIndex(int[] nums) {
        // Step 1: compute total sum
        int totalSum = 0;
        for (int num : nums) totalSum += num;

        // Step 2: scan for pivot
        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            // rightSum = totalSum - leftSum - nums[i]
            if (leftSum == totalSum - leftSum - nums[i]) return i;
            leftSum += nums[i];
        }

        return -1;
    }
}

// Dry Run:
// nums=[1,7,3,6,5,6], totalSum=28
//
// i=0: leftSum=0,  right=28-0-1=27   0≠27 ❌  leftSum=1
// i=1: leftSum=1,  right=28-1-7=20   1≠20 ❌  leftSum=8
// i=2: leftSum=8,  right=28-8-3=17   8≠17 ❌  leftSum=11
// i=3: leftSum=11, right=28-11-6=11  11=11 ✅ return 3
