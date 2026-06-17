// ============================================================
// LC 785 - Is Graph Bipartite?
// https://leetcode.com/problems/is-graph-bipartite/
// ============================================================
// PROBLEM:
//   A graph is bipartite if you can split nodes into 2 groups
//   such that every edge connects nodes from different groups.
//   Return true if the graph is bipartite.
//
// APPROACH 1: BFS (2-coloring)
//   1. Try to color graph with 2 colors (0 and 1)
//   2. Start BFS from each uncolored node, color it 0
//   3. Every neighbour gets opposite color (1 - current)
//   4. If neighbour already has SAME color → conflict → not bipartite
//   5. Handle disconnected graph via outer for loop
//
// APPROACH 2: DFS (2-coloring)
//   Same idea but recursive. Pass color c into DFS.
//   Color node with c, recurse neighbours with 1-c.
//
// KEY INSIGHT:
//   Bipartite = can be 2-colored without conflict.
//   1 - color flips between 0 and 1 automatically.
//   Outer loop handles disconnected components.
//
// DISCONNECTED GRAPH:
//   Graph may have multiple separate components.
//   Must start BFS/DFS from every unvisited node.
//
// Time:  O(V + E)
// Space: O(V)
// ============================================================

import java.util.*;

class LC785_IsGraphBipartite {

    // ---------- APPROACH 1: BFS ----------
    public boolean isBipartite_BFS(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);  // -1 = uncolored

        for (int i = 0; i < n; i++) {
            if (color[i] != -1) continue;  // already colored

            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(i);
            color[i] = 0;

            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int neighbour : graph[node]) {
                    if (color[neighbour] == -1) {
                        color[neighbour] = 1 - color[node];  // opposite color
                        queue.offer(neighbour);
                    } else if (color[neighbour] == color[node]) {
                        return false;  // same color = conflict
                    }
                }
            }
        }
        return true;
    }

    // ---------- APPROACH 2: DFS ----------
    public boolean isBipartite_DFS(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!dfs(graph, color, i, 0)) return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int[] color, int node, int c) {
        color[node] = c;  // color with current color

        for (int neighbour : graph[node]) {
            if (color[neighbour] == -1) {
                // uncolored → recurse with opposite color
                if (!dfs(graph, color, neighbour, 1 - c)) return false;
            } else if (color[neighbour] == color[node]) {
                return false;  // conflict
            }
        }
        return true;
    }
}
