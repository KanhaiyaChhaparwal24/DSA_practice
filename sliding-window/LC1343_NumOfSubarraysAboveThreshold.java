// ============================================================
// LC 1343 - Number of Sub-arrays of Size K and Average Greater
//           than or Equal to Threshold
// https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/
// ============================================================
// PROBLEM:
//   Given array arr, integer k (window size) and threshold,
//   return number of subarrays of size k with average >= threshold.
//
// APPROACH: Fixed Size Sliding Window
//   1. Convert average comparison to sum comparison:
//      avg >= threshold  →  sum >= threshold * k
//      (avoids floating point entirely)
//   2. Build first window, check condition
//   3. Slide window, check condition each time
//   4. Count how many windows satisfy it
//
// KEY INSIGHT (same as LC 643, different goal):
//   LC 643: track maxSum, return maxSum/k
//   LC 1343: track count of windows where sum >= target
//   Same fixed-window-sliding template, different use of each window's sum
//
// WHY sum >= threshold * k INSTEAD OF sum/k >= threshold:
//   Integer comparison is exact and avoids floating point precision issues
//
// Time:  O(n)
// Space: O(1)
// ============================================================

class LC1343_NumOfSubarraysAboveThreshold {
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int sum = 0;
        int count = 0;
        int target = threshold * k;  // avoid floating point

        // build first window
        for (int i = 0; i < k; i++) sum += arr[i];
        if (sum >= target) count++;

        // slide window
        for (int i = k; i < arr.length; i++) {
            sum += arr[i];          // add new right element
            sum -= arr[i - k];      // remove old left element
            if (sum >= target) count++;
        }

        return count;
    }
}

// Dry Run:
// arr=[2,2,2,2,5,5,5,8], k=3, threshold=4
// target = 4*3 = 12
//
// Build first window [2,2,2]: sum=6,  6>=12? ❌
//
// i=3: sum=6+2-2=6,   ❌
// i=4: sum=6+5-2=9,   ❌
// i=5: sum=9+5-2=12,  ✅ count=1
// i=6: sum=12+5-2=15, ✅ count=2
// i=7: sum=15+8-5=18, ✅ count=3
//
// return 3 ✅
