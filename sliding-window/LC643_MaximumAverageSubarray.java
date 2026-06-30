// ============================================================
// LC 643 - Maximum Average Subarray I
// https://leetcode.com/problems/maximum-average-subarray-i/
// ============================================================
// PROBLEM:
//   Given array nums and integer k, find contiguous subarray
//   of length k with maximum average. Return that average.
//
// APPROACH: Fixed Size Sliding Window
//   1. Build first window of size k, compute sum
//   2. Slide window one step at a time:
//      sum += nums[i]      (add new right element)
//      sum -= nums[i - k]  (remove old left element)
//   3. Track maxSum throughout
//   4. Return maxSum / k as double
//
// KEY INSIGHT:
//   No need to recompute sum from scratch each window.
//   Just add incoming element, remove outgoing element.
//   O(n) instead of O(n*k) brute force.
//
// WHY (double) CAST:
//   maxSum / k with both ints = integer division (truncated)
//   (double) maxSum / k = correct decimal division
//
// Time:  O(n)
// Space: O(1)
// ============================================================

class LC643_MaximumAverageSubarray {
    public double findMaxAverage(int[] nums, int k) {
        // build first window
        int sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];

        int maxSum = sum;

        // slide window
        for (int i = k; i < nums.length; i++) {
            sum += nums[i];        // add new element (right)
            sum -= nums[i - k];    // remove old element (left)
            maxSum = Math.max(maxSum, sum);
        }

        return (double) maxSum / k;
    }
}

// Dry Run:
// nums=[1,12,-5,-6,50,3], k=4
//
// Build first window: sum = 1+12-5-6 = 2, maxSum=2
// i=4: sum = 2+50-1   = 51, maxSum=51
// i=5: sum = 51+3-12  = 42, maxSum=51
//
// return 51/4 = 12.75 ✅
