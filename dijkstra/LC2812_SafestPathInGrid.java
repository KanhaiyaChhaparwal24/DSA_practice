// ============================================================
// LC 2812 - Find the Safest Path in a Grid
// https://leetcode.com/problems/find-the-safest-path-in-a-grid/
// ============================================================
// PROBLEM:
//   n x n grid, some cells have thieves (1), rest are safe (0).
//   Safeness factor of a path = minimum Manhattan distance to
//   any thief cell along that path.
//   Return maximum safeness factor from (0,0) to (n-1,n-1).
//
// APPROACH: Multi-Source BFS + Dijkstra (Max Heap)
//
// STEP 1 — Multi-Source BFS from all thief cells:
//   Build safeness[][] grid where safeness[i][j] =
//   minimum distance from cell (i,j) to nearest thief.
//   Same as LC 542 (01 Matrix) — BFS outward from all thieves.
//
// STEP 2 — Dijkstra with MAX heap:
//   Find path from (0,0) to (n-1,n-1) maximizing minimum safeness.
//   dist[i][j] = max safeness achievable to reach (i,j)
//   newSafe = min(currentSafe, safeness[nr][nc])  ← bottleneck
//   Update if newSafe > dist[nr][nc]
//   Use MAX heap (largest safeness first)
//   First time destination polled = maximum safeness guaranteed
//
// KEY DIFFERENCES FROM LC 1631:
//   LC 1631: minimize MAXIMUM effort → min heap, newEffort=max(e,diff)
//   LC 2812: maximize MINIMUM safety → max heap, newSafe=min(s,safe)
//
// EDGE CASE:
//   If start (0,0) or end (n-1,n-1) is a thief → return 0
//
// Time:  O(n² log n) — BFS O(n²) + Dijkstra O(n² log n)
// Space: O(n²)
// ============================================================

import java.util.*;

class LC2812_SafestPathInGrid {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] safeness = new int[n][n];
        for (int[] row : safeness) Arrays.fill(row, -1);

        // Step 1: multi-source BFS from all thief cells
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    safeness[i][j] = 0;   // thief cell, distance = 0
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : dirs) {
                int nr = cell[0] + d[0], nc = cell[1] + d[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                if (safeness[nr][nc] != -1) continue;  // already visited
                safeness[nr][nc] = safeness[cell[0]][cell[1]] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }

        // edge case: start or end is a thief cell
        if (safeness[0][0] == 0 || safeness[n-1][n-1] == 0) return 0;

        // Step 2: Dijkstra with max heap [safeness, row, col]
        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, -1);
        dist[0][0] = safeness[0][0];

        // max heap — largest safeness first
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        pq.offer(new int[]{safeness[0][0], 0, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int safe = curr[0], r = curr[1], c = curr[2];

            // reached destination — first poll = maximum safeness
            if (r == n-1 && c == n-1) return safe;

            if (safe < dist[r][c]) continue;  // stale entry

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;

                // path safeness = minimum safeness cell on path
                int newSafe = Math.min(safe, safeness[nr][nc]);

                if (newSafe > dist[nr][nc]) {
                    dist[nr][nc] = newSafe;
                    pq.offer(new int[]{newSafe, nr, nc});
                }
            }
        }

        return dist[n-1][n-1];
    }
}
