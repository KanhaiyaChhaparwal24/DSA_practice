# Dijkstra's Algorithm

Dijkstra finds the shortest path from a source node to all other nodes in a **weighted, non-negative** graph.

---

## Core Concept

```
1. dist[] = ∞ for all nodes, dist[source] = 0
2. Min heap (priority queue) — always process closest node first
3. Poll node → relax all neighbours
4. If dist[node] + weight < dist[neighbour] → update + push
5. Skip stale entries: if (d > dist[node]) continue
```

## Template

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
pq.offer(new int[]{0, source});  // [distance, node]

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int d = curr[0], node = curr[1];

    if (d > dist[node]) continue;  // stale entry

    for (int[] nb : graph.get(node)) {
        int next = nb[0], weight = nb[1];
        int newDist = dist[node] + weight;
        if (newDist < dist[next]) {
            dist[next] = newDist;
            pq.offer(new int[]{newDist, next});
        }
    }
}
```

---

## Key Rules

```
Non-negative weights only          → Dijkstra breaks with negative weights
Min heap — not regular queue       → always process cheapest node first
Stale entry check                  → if (d > dist[node]) continue
LC 743 update: dist[node] + weight → minimize total cost
LC 1631 update: max(effort, diff)  → minimize maximum cost
Early exit when destination polled → first poll = minimum cost guaranteed
```

---

## Problems

| #       | Problem                           | Key Twist                                    | Difficulty |
| ------- | --------------------------------- | -------------------------------------------- | ---------- |
| LC 743  | Network Delay Time                | Classic Dijkstra, answer = max(dist[])       | Medium     |
| LC 1631 | Path With Minimum Effort          | Minimize max weight, not sum                 | Medium     |
| LC 787  | Cheapest Flights Within K Stops   | Bellman-Ford, K+1 iterations, snapshot trick | Medium     |
| LC 1334 | Find City With Smallest Neighbors | Run Dijkstra from every node                 | Medium     |
| LC 2812 | Safest Path in Grid               | Multi-source BFS + Dijkstra                  | Medium     |

---

## BFS vs Dijkstra

|                 | BFS                      | Dijkstra                  |
| --------------- | ------------------------ | ------------------------- |
| Edge weights    | All equal (1)            | Different weights         |
| Data structure  | Queue (FIFO)             | Priority Queue (min heap) |
| Order processed | Layer by layer           | Cheapest node first       |
| Time complexity | O(V + E)                 | O((V + E) log V)          |
| Use for         | Unweighted shortest path | Weighted shortest path    |
