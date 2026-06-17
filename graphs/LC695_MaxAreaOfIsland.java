// ============================================================
// LC 695 - Max Area of Island
// https://leetcode.com/problems/max-area-of-island/
// ============================================================
// PROBLEM:
//   Given a 2D binary grid, return the area of the largest
//   island. An island is a group of connected 1s (4-directional).
//   Area = number of cells in the island.
//
// APPROACH: DFS (return size)
//   1. Loop through every cell
//   2. If cell is 1 → run DFS, get island size
//   3. Track maximum size across all islands
//   4. DFS returns 1 (this cell) + size of all 4 directions
//
// KEY INSIGHT:
//   Same as LC 200 but DFS now returns int instead of void.
//   Each DFS call contributes 1 (itself) + recursive results.
//   Base case returns 0 (out of bounds / water = no contribution).
//
// Time:  O(m * n)
// Space: O(m * n) — recursion stack
// ============================================================

class LC695_MaxAreaOfIsland {
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    max = Math.max(max, dfs(grid, i, j));
                }
            }
        }
        return max;
    }

    private int dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1)
            return 0;  // base case — contributes 0 to size

        grid[i][j] = 0;  // sink (mark visited)

        return 1                         // this cell
            + dfs(grid, i + 1, j)       // down
            + dfs(grid, i - 1, j)       // up
            + dfs(grid, i, j + 1)       // right
            + dfs(grid, i, j - 1);      // left
    }
}
