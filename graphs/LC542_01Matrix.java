// ============================================================
// LC 542 - 01 Matrix
// https://leetcode.com/problems/01-matrix/
// ============================================================
// PROBLEM:
//   Given a matrix of 0s and 1s, return a matrix where each
//   cell contains the distance to the nearest 0.
//
// APPROACH: Multi-Source BFS from all 0-cells
//   1. Add ALL 0-cells to queue, set dist = 0 for them
//   2. Set all 1-cells dist = MAX_VALUE (unknown)
//   3. BFS outward — each neighbour gets dist[current] + 1
//   4. Only update if new distance is shorter (first arrival = shortest)
//
// KEY INSIGHT:
//   Instead of BFS from each 1-cell to find nearest 0 (slow),
//   do reverse — BFS outward from all 0-cells simultaneously.
//   BFS guarantees first time we reach a cell = shortest distance.
//   So condition dist[nr][nc] > dist[r][c] + 1 ensures we only
//   update cells that haven't been reached yet (or found shorter path).
//
// WHY BFS NOT DFS:
//   BFS = shortest path. DFS has no concept of "nearest".
//
// Time:  O(m * n)
// Space: O(m * n)
// ============================================================

import java.util.ArrayDeque;
import java.util.Queue;

class LC542_01Matrix {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] dist = new int[m][n];
        Queue<int[]> queue = new ArrayDeque<>();

        // Step 1: initialize dist matrix, add all 0-cells to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    dist[i][j] = 0;
                    queue.offer(new int[]{i, j});
                } else {
                    dist[i][j] = Integer.MAX_VALUE;  // unknown
                }
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // Step 2: BFS outward from all 0-cells
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : dirs) {
                int nr = cell[0] + d[0];
                int nc = cell[1] + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                // only update if we found a shorter path
                if (dist[nr][nc] > dist[cell[0]][cell[1]] + 1) {
                    dist[nr][nc] = dist[cell[0]][cell[1]] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return dist;
    }
}
