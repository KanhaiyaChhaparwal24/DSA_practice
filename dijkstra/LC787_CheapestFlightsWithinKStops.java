// ============================================================
// LC 787 - Cheapest Flights Within K Stops
// https://leetcode.com/problems/cheapest-flights-within-k-stops/
// ============================================================
// PROBLEM:
//   n cities connected by flights[i] = [from, to, price].
//   Given src, dst, and k — return cheapest price from src
//   to dst with at most k stops. Return -1 if impossible.
//
// BEST APPROACH: Bellman-Ford (K+1 iterations)
//   1. dist[] = ∞ for all cities, dist[src] = 0
//   2. Repeat K+1 times (K stops = K+1 edges):
//      - Snapshot current dist[] into temp[]
//      - For every flight [from, to, price]:
//          if dist[from] != ∞:
//            temp[to] = min(temp[to], dist[from] + price)
//      - dist = temp
//   3. Return dist[dst] == ∞ ? -1 : dist[dst]
//
// WHY NOT DIJKSTRA:
//   Dijkstra handles ONE constraint — minimize cost.
//   This problem has TWO constraints — minimize cost + respect stops.
//   These conflict: cheapest path may use too many stops.
//   Dijkstra either blocks valid paths (Wrong Answer) or
//   explores too many paths (Time Limit Exceeded).
//   Bellman-Ford handles both constraints cleanly.
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

import java.util.Arrays;

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
}