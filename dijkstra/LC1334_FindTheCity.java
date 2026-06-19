// ============================================================
// LC 1334 - Find the City With the Smallest Number of Neighbors
//           at a Threshold Distance
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
// ============================================================
// PROBLEM:
//   n cities, weighted undirected edges.
//   A city is a neighbor if reachable within distanceThreshold.
//   Return city with fewest neighbors. Tie → largest index.
//
// APPROACH: Floyd-Warshall (All-Pairs Shortest Path)
//   1. Build dist[][] matrix:
//      - dist[i][i] = 0
//      - dist[i][j] = edge weight if direct edge
//      - dist[i][j] = ∞ otherwise
//   2. Floyd-Warshall — for every intermediate node k:
//      dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
//   3. For each city count neighbors within distanceThreshold
//   4. Return city with min count (largest index wins ties)
//
// WHY FLOYD-WARSHALL over DIJKSTRA:
//   Need shortest path from EVERY city to every other city.
//   Floyd-Warshall does this in one pass with 3 nested loops.
//   Dijkstra would need to run n times (once per source city).
//   n ≤ 100 here so O(n³) is perfectly fine.
//
// WHY MAX_VALUE / 2:
//   Adding two MAX_VALUE integers overflows to negative.
//   MAX_VALUE / 2 + MAX_VALUE / 2 stays positive and safe.
//
// WHY count <= minNeighbors (not <):
//   Handles tie-breaking automatically.
//   Loop runs 0 to n-1, so later cities overwrite earlier ones.
//   Last city to tie = largest index = correct answer.
//
// Time:  O(n³) — three nested loops
// Space: O(n²) — distance matrix
// ============================================================

import java.util.Arrays;

class LC1334_FindTheCity {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // Step 1: build distance matrix
        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE / 2);  // avoid overflow
        for (int i = 0; i < n; i++) dist[i][i] = 0;   // distance to self = 0
        for (int[] e : edges) {
            dist[e[0]][e[1]] = e[2];   // undirected
            dist[e[1]][e[0]] = e[2];
        }

        // Step 2: Floyd-Warshall
        // k = intermediate city (layover)
        // i = start city, j = end city
        // try routing i→j through every possible k
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        // Step 3: find city with fewest neighbors within threshold
        int resultCity = -1;
        int minNeighbors = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) count++;
            }
            // <= handles tie — larger index overwrites, giving largest index on tie
            if (count <= minNeighbors) {
                minNeighbors = count;
                resultCity = i;
            }
        }

        return resultCity;
    }
}
