// ============================================================
// LC 200 - Number of Islands
// https://leetcode.com/problems/number-of-islands/
// ============================================================
// PROBLEM:
//   Given a 2D grid of '1's (land) and '0's (water),
//   count the number of islands.
//   An island is formed by connecting adjacent land cells
//   horizontally or vertically.
//
// APPROACH: DFS (Sink visited cells)
//   1. Loop through every cell in the grid
//   2. If cell is '1' → found a new island → count++
//   3. Run DFS to sink the entire island (mark all connected
//      land cells as '0' so we never count them again)
//   4. Repeat until all cells are visited
//
// KEY INSIGHT:
//   Grid = Graph. Each cell = node. Adjacent cells = edges.
//   "Count islands" = "Count how many times DFS starts fresh"
//
// Time:  O(m * n) — every cell visited at most once
// Space: O(m * n) — recursion stack in worst case
// ============================================================

class LC200_NumberOfIslands {
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);  // sink the entire island
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        // stop if out of bounds or not land
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1')
            return;

        grid[i][j] = '0';  // sink this cell (mark visited)

        dfs(grid, i + 1, j);  // down
        dfs(grid, i - 1, j);  // up
        dfs(grid, i, j + 1);  // right
        dfs(grid, i, j - 1);  // left
    }
}
