// ============================================================
// LC 209 - Minimum Size Subarray Sum
// https://leetcode.com/problems/minimum-size-subarray-sum/
// ============================================================
// PROBLEM:
//   Given an array of positive integers and target, return the
//   minimum length subarray whose sum >= target.
//   Return 0 if no such subarray exists.
//
// APPROACH: Sliding Window (Two Pointers)
//   1. Expand right pointer — add nums[r] to sum
//   2. While sum >= target:
//      - record current window length (r - l + 1)
//      - shrink from left: sum -= nums[l], l++
//   3. Keep shrinking as long as sum still >= target
//      (might find an even smaller valid window)
//   4. Return ans (0 if never found)
//
// WHY SLIDING WINDOW OVER PREFIX SUM:
//   All elements are POSITIVE → shrinking window always reduces sum
//   → sliding window works correctly
//   Prefix sum approach needs binary search → O(n log n)
//   Sliding window → O(n), strictly better here
//
// WHY NOT SLIDING WINDOW FOR LC 560:
//   LC 560 has negative numbers → shrinking window might increase sum
//   → sliding window breaks → must use prefix sum + HashMap
//
// KEY LINE:
//   while(sum >= target) → keep shrinking, not just once
//   This ensures we find the MINIMUM length window
//
// Time:  O(n) — each element added and removed at most once
// Space: O(1)
// ============================================================

class LC209_MinimumSizeSubarraySum {
    public int minSubArrayLen(int target, int[] nums) {
        int ans = Integer.MAX_VALUE;
        int sum = 0;

        for (int l = 0, r = 0; r < nums.length; r++) {
            sum += nums[r];                          // expand right
            while (sum >= target) {                  // valid window found
                ans = Math.min(ans, r - l + 1);     // record length
                sum -= nums[l++];                    // shrink left
            }
        }

        return ans == Integer.MAX_VALUE ? 0 : ans;  // 0 if never found
    }
}

// Dry Run:
// nums=[2,3,1,2,4,3], target=7
//
// r=0: sum=2,  sum<7
// r=1: sum=5,  sum<7
// r=2: sum=6,  sum<7
// r=3: sum=8,  sum>=7 → ans=4, sum-=2→6, l=1 → sum<7, stop
// r=4: sum=10, sum>=7 → ans=4, sum-=3→7, l=2
//              sum>=7 → ans=3, sum-=1→6, l=3 → sum<7, stop
// r=5: sum=9,  sum>=7 → ans=3, sum-=2→7, l=4
//              sum>=7 → ans=2, sum-=4→3, l=5 → sum<7, stop
//
// return 2 ✅
