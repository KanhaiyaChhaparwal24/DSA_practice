// ============================================================
// LC 1971 - Find if Path Exists in Graph
// https://leetcode.com/problems/find-if-path-exists-in-graph/
// ============================================================
// PROBLEM:
//   Given n nodes and a list of edges, return true if there
//   is a valid path from source to destination.
//
// APPROACH 1: DFS
//   1. Build adjacency list from edges
//   2. DFS from source, mark visited nodes
//   3. If we reach destination → return true
//   4. If DFS exhausts without finding → return false
//
// APPROACH 2: BFS
//   1. Build adjacency list from edges
//   2. BFS from source, mark visited before adding to queue
//   3. If we poll destination → return true
//   4. Queue empties → return false
//
// KEY INSIGHT:
//   Simplest graph traversal — just check reachability.
//   No counting, no distances. Just "can I get there?"
//
// WHY MARK VISITED BEFORE ADDING TO QUEUE (BFS):
//   If you mark after polling, same node can be added to queue
//   multiple times → wasted work / infinite loop risk.
//
// Time:  O(V + E)
// Space: O(V + E)
// ============================================================

import java.util.*;

class LC1971_FindIfPathExists {

    // ---------- APPROACH 1: DFS ----------
    public boolean validPath_DFS(int n, int[][] edges, int source, int destination) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        return dfs(graph, visited, source, destination);
    }

    private boolean dfs(List<List<Integer>> graph, boolean[] visited, int node, int destination) {
        if (visited[node]) return false;      // already explored
        if (node == destination) return true; // found it!

        visited[node] = true;

        for (int neighbour : graph.get(node)) {
            if (dfs(graph, visited, neighbour, destination)) return true;
        }

        return false;
    }

    // ---------- APPROACH 2: BFS ----------
    public boolean validPath_BFS(int n, int[][] edges, int source, int destination) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();

        queue.offer(source);
        visited[source] = true;  // mark before adding, not after polling

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == destination) return true;
            for (int neighbour : graph.get(node)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    queue.offer(neighbour);
                }
            }
        }

        return false;
    }
}
