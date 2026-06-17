// ============================================================
// LC 994 - Rotting Oranges
// https://leetcode.com/problems/rotting-oranges/
// ============================================================
// PROBLEM:
//   Grid has 0 (empty), 1 (fresh orange), 2 (rotten orange).
//   Every minute, rotten oranges rot adjacent fresh oranges.
//   Return minimum minutes until no fresh orange remains.
//   Return -1 if impossible.
//
// APPROACH: Multi-Source BFS
//   1. Add ALL rotten oranges to queue at start (multi-source)
//   2. Count all fresh oranges
//   3. BFS layer by layer — each layer = 1 minute
//   4. For each rotten orange, rot its fresh neighbours
//   5. If fresh count reaches 0 → done, return minutes
//   6. If queue empties with fresh > 0 → impossible, return -1
//
// KEY INSIGHT:
//   Multiple rotten oranges rot simultaneously — so we start
//   BFS from ALL of them at once (multi-source BFS).
//   int size = queue.size() → snapshot of current minute's oranges.
//   Anything added during loop belongs to NEXT minute.
//
// WHY BFS NOT DFS:
//   We need minimum TIME (layers). BFS processes layer by layer.
//   DFS goes deep first — can't track layers correctly.
//
// Time:  O(m * n)
// Space: O(m * n)
// ============================================================

import java.util.ArrayDeque;
import java.util.Queue;

class LC994_RottingOranges {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new ArrayDeque<>();
        int fresh = 0;
        int minutes = 0;

        // Step 1: add all rotten oranges, count fresh
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                if (grid[i][j] == 1) fresh++;
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // Step 2: BFS layer by layer (minute by minute)
        while (!queue.isEmpty() && fresh > 0) {
            minutes++;
            int size = queue.size();  // oranges rotting THIS minute
            for (int k = 0; k < size; k++) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0];
                    int nc = cell[1] + d[1];
                    if (nr < 0 || nr >= grid.length || nc < 0 || nc >= grid[0].length) continue;
                    if (grid[nr][nc] != 1) continue;  // not fresh, skip
                    grid[nr][nc] = 2;   // rot it
                    fresh--;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return fresh == 0 ? minutes : -1;
    }
}
