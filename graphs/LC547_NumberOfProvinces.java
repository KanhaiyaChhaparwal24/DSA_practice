// ============================================================
// LC 547 - Number of Provinces
// https://leetcode.com/problems/number-of-provinces/
// ============================================================
// PROBLEM:
//   There are n cities. isConnected[i][j] = 1 means city i
//   and city j are directly connected. A province is a group
//   of directly or indirectly connected cities.
//   Return the number of provinces.
//
// APPROACH 1: DFS
//   1. visited[] array to track which cities are explored
//   2. Loop every city — if not visited, found new province → count++
//   3. DFS to mark all cities in this province as visited
//   4. Neighbours found via isConnected[i][j] == 1
//
// APPROACH 2: Union Find
//   1. parent[i] = i (each city is its own province initially)
//   2. count = n
//   3. For every connection isConnected[i][j] == 1:
//      if find(i) != find(j) → different provinces → union, count--
//   4. Return count
//
// KEY INSIGHT:
//   Same as LC 200 (Number of Islands) but input is adjacency
//   matrix instead of grid. Province = Island. City = Land cell.
//
// Time:  O(n²) — must scan entire matrix
// Space: O(n)  — visited array / parent array
// ============================================================

class LC547_NumberOfProvinces {

    // ---------- APPROACH 1: DFS ----------
    public int findCircleNum_DFS(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, isConnected, visited);
                provinces++;
            }
        }
        return provinces;
    }

    private void dfs(int i, int[][] isConnected, boolean[] visited) {
        visited[i] = true;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(j, isConnected, visited);
            }
        }
    }

    // ---------- APPROACH 2: Union Find ----------
    int[] parent, rank;

    public int findCircleNum_UnionFind(int[][] isConnected) {
        int n = isConnected.length;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int count = n;  // start with n provinces

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {  // j = i+1 avoids duplicates
                if (isConnected[i][j] == 1) {
                    if (find(i) != find(j)) {
                        union(i, j);
                        count--;  // two provinces merged
                    }
                }
            }
        }
        return count;
    }

    private int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);  // path compression
        return parent[x];
    }

    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;

        // union by rank — smaller tree goes under larger
        if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
