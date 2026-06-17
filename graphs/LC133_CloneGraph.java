// ============================================================
// LC 133 - Clone Graph
// https://leetcode.com/problems/clone-graph/
// ============================================================
// PROBLEM:
//   Given a reference to a node in a connected undirected graph,
//   return a deep copy (clone) of the graph.
//   Each node has a val and a list of neighbours.
//
// APPROACH: DFS + HashMap
//   1. Use HashMap<original node, cloned node>
//   2. If node already in map → return existing clone (handles cycles)
//   3. Create new clone node, add to map IMMEDIATELY (before recursing)
//   4. Recursively clone all neighbours, add to clone's neighbour list
//   5. Return clone
//
// KEY INSIGHT:
//   Graphs can have cycles. Without the HashMap, DFS would loop forever.
//   HashMap serves two purposes:
//     - visited set (prevents infinite loop)
//     - lookup table (reuse already-created clones)
//   Add to map BEFORE recursing — same reason as BFS visited mark.
//
// Time:  O(V + E) — every node and edge visited once
// Space: O(V)     — HashMap stores one entry per node
// ============================================================

import java.util.*;

class LC133_CloneGraph {
    // Definition for a Node
    class Node {
        public int val;
        public List<Node> neighbors;
        public Node(int val) {
            this.val = val;
            this.neighbors = new ArrayList<>();
        }
    }

    Map<Node, Node> visited = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        // already cloned → return existing clone (stops cycle)
        if (visited.containsKey(node)) return visited.get(node);

        // create clone, register BEFORE recursing
        Node clone = new Node(node.val);
        visited.put(node, clone);

        // recursively clone all neighbours
        for (Node neighbour : node.neighbors) {
            clone.neighbors.add(cloneGraph(neighbour));
        }

        return clone;
    }
}
