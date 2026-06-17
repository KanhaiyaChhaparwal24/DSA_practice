# Graphs

Problems solved using graph traversal techniques — DFS, BFS, Union Find, Dijkstra, and Topological Sort.

---

## Problems

| # | Problem | Approach | Difficulty |
|---|---------|----------|------------|
| LC 200 | Number of Islands | DFS | Medium |
| LC 733 | Flood Fill | DFS | Easy |
| LC 695 | Max Area of Island | DFS | Medium |
| LC 994 | Rotting Oranges | Multi-Source BFS | Medium |
| LC 542 | 01 Matrix | Multi-Source BFS | Medium |
| LC 1971 | Find if Path Exists | DFS + BFS | Easy |
| LC 133 | Clone Graph | DFS + HashMap | Medium |
| LC 785 | Is Graph Bipartite? | BFS + DFS | Medium |
| LC 547 | Number of Provinces | DFS + Union Find | Medium |
| LC 207 | Course Schedule | BFS (Kahn's) + DFS | Medium |
| LC 210 | Course Schedule II | BFS (Kahn's) + DFS | Medium |
| LC 743 | Network Delay Time | Dijkstra | Medium |

---

## Patterns

### DFS — Depth First Search
- Go as deep as possible before backtracking
- Uses recursion (implicit stack)
- Use for: exploring all paths, connected components, cycle detection
- Template:
```java
void dfs(node) {
    if (base case) return;
    mark visited;
    for each neighbour:
        if not visited: dfs(neighbour);
}
```

### BFS — Breadth First Search
- Visit all neighbours first, then their neighbours
- Uses a Queue
- Use for: shortest path, minimum steps, layer-by-layer spread
- Template:
```java
queue.offer(start);
visited[start] = true;
while (!queue.isEmpty()) {
    node = queue.poll();
    for each neighbour:
        if not visited:
            visited[neighbour] = true;
            queue.offer(neighbour);
}
```

### Multi-Source BFS
- Start BFS from MULTIPLE sources simultaneously
- Use for: "spread from all X cells at once" problems
- Examples: Rotting Oranges, 01 Matrix

### Union Find
- Track connected components efficiently
- find(x) → root of x's group (with path compression)
- union(x, y) → merge two groups (by rank)
- Use for: dynamic connectivity, cycle detection in undirected graphs

### Topological Sort (Kahn's BFS)
- Order nodes such that every edge goes from earlier to later
- Only works on DAGs (no cycles)
- If processed count < total nodes → cycle exists
- Use for: course scheduling, task dependencies

### Dijkstra
- Shortest path in weighted graphs (non-negative weights)
- Uses min priority queue
- Always process cheapest node next
- Use for: minimum cost/time/effort path problems

---

## Key Rules

```
Mark visited BEFORE adding to queue (BFS) — prevents duplicates
Mark visited AT START of DFS — prevents infinite loops on cycles
Sink cells (grid[i][j] = 0) instead of visited array — saves space
int size = queue.size() — snapshot for layer-by-layer BFS
if (d > dist[node]) continue — skip stale Dijkstra entries
```
