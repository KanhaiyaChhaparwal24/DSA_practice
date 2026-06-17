// ============================================================
// LC 1631 - Path With Minimum Effort
// https://leetcode.com/problems/path-with-minimum-effort/
// ============================================================
// PROBLEM:
//   2D grid where heights[i][j] = height of cell (i,j).
//   Effort of a path = maximum absolute height difference
//   between any two consecutive cells on that path.
//   Return minimum effort to travel from (0,0) to (m-1,n-1).
//
// APPROACH: Dijkstra on Grid
//   1. dist[][] = ∞ for all cells, dist[0][0] = 0
//   2. Min heap — [effort, row, col], sorted by effort
//   3. Poll cell with minimum effort
//   4. For each 4-direction neighbour:
//      newEffort = max(current effort, |height difference|)
//      if newEffort < dist[neighbour] → update + push
//   5. First time we poll destination = minimum effort (early exit)
//
// KEY INSIGHT:
//   LC 743: minimize TOTAL cost (sum of weights)
//   LC 1631: minimize MAXIMUM cost (max of weights on path)
//   Both use Dijkstra — just the update formula changes:
//     LC 743:  newDist   = dist[node] + weight
//     LC 1631: newEffort = max(effort, |height diff|)
//
// EARLY EXIT:
//   Dijkstra polls in order of minimum effort.
//   First time destination is polled = guaranteed minimum effort.
//   No need to process the entire graph.
//
// Time:  O(m * n * log(m * n))
// Space: O(m * n)
// ============================================================

import java.util.*;

class LC1631_PathWithMinimumEffort {
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;

        // dist[i][j] = min effort to reach cell (i,j)
        int[][] dist = new int[m][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;

        // min heap — [effort, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0});

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int effort = curr[0], r = curr[1], c = curr[2];

            // reached destination — first poll = minimum effort
            if (r == m - 1 && c == n - 1) return effort;

            if (effort > dist[r][c]) continue;  // stale entry, skip

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;

                // effort = max of current path effort and this step's diff
                int newEffort = Math.max(effort, Math.abs(heights[r][c] - heights[nr][nc]));

                if (newEffort < dist[nr][nc]) {
                    dist[nr][nc] = newEffort;
                    pq.offer(new int[]{newEffort, nr, nc});
                }
            }
        }

        return dist[m-1][n-1];
    }
}
