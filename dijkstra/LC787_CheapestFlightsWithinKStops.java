// ============================================================
// LC 787 - Cheapest Flights Within K Stops
// https://leetcode.com/problems/cheapest-flights-within-k-stops/
// ============================================================
// PROBLEM:
//   n cities connected by flights[i] = [from, to, price].
//   Given src, dst, and k — return cheapest price from src
//   to dst with at most k stops. Return -1 if impossible.
//
// APPROACH: Bellman-Ford (BFS with K levels)
//   1. dist[] = ∞ for all cities, dist[src] = 0
//   2. Repeat K+1 times (K stops = K+1 edges):
//      - Snapshot current dist[] into temp[]
//      - For every flight [from, to, price]:
//          if dist[from] != ∞:
//            temp[to] = min(temp[to], dist[from] + price)
//      - dist = temp
//   3. Return dist[dst] == ∞ ? -1 : dist[dst]
//
// WHY NOT PURE DIJKSTRA:
//   Dijkstra finds cheapest path but ignores stop constraint.
//   It might find a cheap path using 10 stops when k=2.
//   Bellman-Ford naturally enforces K+1 edges via K+1 iterations.
//
// WHY SNAPSHOT (temp[]):
//   Without snapshot, one iteration can chain multiple flights:
//   e.g. 0→1→2→3 all in one iteration = 3 edges in 1 round (wrong!)
//   Snapshot freezes old state so each iteration adds exactly 1 edge.
//
// K stops = K+1 edges = K+1 iterations:
//   i=0 → 1 edge  → 0 stops
//   i=1 → 2 edges → 1 stop
//   i=k → k+1 edges → k stops
//
// Time:  O(K * E) — K+1 iterations, each processes all E flights
// Space: O(n)     — dist and temp arrays
// ============================================================

import java.util.*;

class LC787_CheapestFlightsWithinKStops {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // dist[i] = cheapest price to reach city i
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;  // source costs 0 — already there

        // K stops = K+1 edges = K+1 iterations
        for (int i = 0; i <= k; i++) {
            // snapshot — read from dist, write to temp
            // prevents chaining multiple flights in one iteration
            int[] temp = Arrays.copyOf(dist, n);

            for (int[] flight : flights) {
                int from = flight[0], to = flight[1], price = flight[2];

                // can't fly from city we haven't reached yet
                if (dist[from] == Integer.MAX_VALUE) continue;

                // can we reach 'to' cheaper via this flight?
                temp[to] = Math.min(temp[to], dist[from] + price);
            }

            dist = temp;  // update AFTER processing all flights
        }

        // if dst still infinity → no valid path within k stops
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }


    // ============================================================
    // APPROACH 2: Dijkstra + Priority Queue
    // Track [cost, node, stops] in min heap
    // Prune paths that exceed k stops
    //
    // WHY DIFFERENT FROM STANDARD DIJKSTRA:
    //   Can't skip node just because visited with lower cost —
    //   a costlier path with fewer stops might be valid.
    //   So we let k stop limit naturally prune the search.
    //
    // Time:  O((V + E) log V)
    // Space: O(V + E)
    // ============================================================
    public int findCheapestPrice_Dijkstra(int n, int[][] flights, int src, int dst, int k) {
        // build adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] f : flights)
            graph.get(f[0]).add(new int[]{f[1], f[2]});  // [to, price]

        // dist[i] = cheapest cost to reach city i
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // min heap — [cost, node, stops used]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, src, 0});  // cost=0, start at src, 0 stops

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int cost = curr[0], node = curr[1], stops = curr[2];

            // reached destination — first poll = cheapest (min heap)
            if (node == dst) return cost;

            // exceeded stop limit — don't explore further
            if (stops > k) continue;

            for (int[] nb : graph.get(node)) {
                int next = nb[0], price = nb[1];
                int newCost = cost + price;
                // push ALL valid paths — don't block by dist check
                pq.offer(new int[]{newCost, next, stops + 1});
            }
        }

        return -1;  // destination unreachable within k stops
    }
}
