// ============================================================
// LC 743 - Network Delay Time
// https://leetcode.com/problems/network-delay-time/
// ============================================================
// PROBLEM:
//   n nodes, directed weighted edges times[i] = [u, v, w].
//   Signal sent from node k. Return time for ALL nodes to
//   receive signal. Return -1 if any node unreachable.
//
// APPROACH: Dijkstra's Algorithm
//   1. Build adjacency list (directed graph)
//   2. dist[] = MAX for all nodes, dist[k] = 0
//   3. Min priority queue ordered by distance
//   4. Poll cheapest node, relax all its neighbours
//   5. If new dist < current dist → update + add to pq
//   6. Skip stale entries (d > dist[node])
//   7. Answer = max of all dist[] (last node to receive signal)
//      If any dist == MAX → unreachable → return -1
//
// KEY INSIGHT:
//   BFS finds shortest path in UNWEIGHTED graphs.
//   Dijkstra finds shortest path in WEIGHTED graphs (non-negative).
//   Always process cheapest node next → greedy → correct.
//
// STALE ENTRY:
//   When we update dist[v], old entry still in pq.
//   When we poll old entry, d > dist[node] → skip it.
//
// WHY MAX OF ALL DIST:
//   All nodes must receive signal. Last one to receive
//   determines total time.
//
// Time:  O((V + E) log V)
// Space: O(V + E)
// ============================================================

import java.util.*;

class LC743_NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        // build adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] time : times) {
            graph.get(time[0]).add(new int[]{time[1], time[2]});  // [neighbour, weight]
        }

        // dist array — all infinity except source
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        // min heap sorted by distance
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k, 0});  // [node, distance]

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], d = curr[1];

            if (d > dist[node]) continue;  // stale entry, skip

            for (int[] neighbour : graph.get(node)) {
                int next = neighbour[0], weight = neighbour[1];
                int newDist = dist[node] + weight;
                if (newDist < dist[next]) {
                    dist[next] = newDist;
                    pq.offer(new int[]{next, newDist});
                }
            }
        }

        // max dist = time for last node to receive signal
        int maxDist = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;  // unreachable
            maxDist = Math.max(maxDist, dist[i]);
        }
        return maxDist;
    }
}
