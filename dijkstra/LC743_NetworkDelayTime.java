// ============================================================
// LC 743 - Network Delay Time
// https://leetcode.com/problems/network-delay-time/
// ============================================================
// PROBLEM:
//   n nodes, directed weighted edges times[i] = [u, v, w].
//   Signal sent from node k.
//   Return time for ALL nodes to receive signal.
//   Return -1 if any node unreachable.
//
// APPROACH: Dijkstra's Algorithm
//   1. Build directed adjacency list from times[]
//   2. dist[] = ∞ for all nodes, dist[k] = 0
//   3. Min heap — always process closest node first [distance, node]
//   4. Poll node → for each neighbour check if shorter path exists
//   5. newDist = dist[node] + weight
//      if newDist < dist[neighbour] → update + push to heap
//   6. Skip stale entries: if (d > dist[node]) continue
//   7. Answer = max(dist[]) → last node to receive signal
//      If any dist == ∞ → unreachable → return -1
//
// KEY INSIGHT:
//   BFS = shortest path for unweighted graphs (all edges cost 1)
//   Dijkstra = shortest path for weighted graphs (non-negative weights)
//   Always process cheapest node next → greedy → correct answer
//
// WHY MAX OF ALL DIST:
//   All nodes must receive signal.
//   Last node to receive it = total time taken.
//
// STALE ENTRY:
//   When better path found, old entry stays in pq.
//   When polled, d > dist[node] → outdated → skip.
//
// Time:  O((V + E) log V)
// Space: O(V + E)
// ============================================================

import java.util.*;

class LC743_NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Step 1: build directed adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] t : times)
            graph.get(t[0]).add(new int[]{t[1], t[2]});  // [neighbour, weight]

        // Step 2: dist array — all infinity except source
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        // Step 3: min heap sorted by distance [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, k});

        // Step 4: Dijkstra
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], node = curr[1];

            if (d > dist[node]) continue;  // stale entry, skip

            for (int[] nb : graph.get(node)) {
                int next = nb[0], weight = nb[1];
                int newDist = dist[node] + weight;
                if (newDist < dist[next]) {
                    dist[next] = newDist;
                    pq.offer(new int[]{newDist, next});
                }
            }
        }

        // Step 5: answer = max dist (last node to receive signal)
        int maxDist = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;  // unreachable node
            maxDist = Math.max(maxDist, dist[i]);
        }
        return maxDist;
    }
}
