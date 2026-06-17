// ============================================================
// LC 733 - Flood Fill
// https://leetcode.com/problems/flood-fill/
// ============================================================
// PROBLEM:
//   Given an image (2D grid), a starting pixel (sr, sc),
//   and a new color, flood fill the image starting from
//   (sr, sc). Replace all connected pixels of the same
//   original color with the new color.
//
// APPROACH: DFS
//   1. If starting pixel already has new color → return (avoid loop)
//   2. DFS from (sr, sc), spreading to all 4 neighbours
//   3. Only spread if neighbour has the same startColor
//   4. Change color as you visit
//
// KEY INSIGHT:
//   Same as Number of Islands — DFS spreads through connected
//   cells. Stop condition is different color instead of water.
//   No need for visited array — changing color acts as visited mark.
//
// EDGE CASE:
//   If startColor == newColor → return immediately.
//   Otherwise DFS never stops (cell always equals startColor).
//
// Time:  O(m * n)
// Space: O(m * n) — recursion stack
// ============================================================

class LC733_FloodFill {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) return image;  // edge case
        dfs(image, sr, sc, image[sr][sc], color);
        return image;
    }

    private void dfs(int[][] image, int i, int j, int startColor, int newColor) {
        if (i < 0 || i >= image.length || j < 0 || j >= image[0].length)
            return;
        if (image[i][j] != startColor)
            return;

        image[i][j] = newColor;  // fill with new color (marks visited)

        dfs(image, i + 1, j, startColor, newColor);  // down
        dfs(image, i - 1, j, startColor, newColor);  // up
        dfs(image, i, j + 1, startColor, newColor);  // right
        dfs(image, i, j - 1, startColor, newColor);  // left
    }
}
