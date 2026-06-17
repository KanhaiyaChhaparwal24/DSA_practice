// ============================================================
// LC 207 - Course Schedule
// https://leetcode.com/problems/course-schedule/
// ============================================================
// PROBLEM:
//   You have numCourses courses. prerequisites[i] = [a, b]
//   means you must take course b before course a.
//   Return true if you can finish all courses.
//   i.e. Does the graph have NO cycle?
//
// APPROACH 1: BFS — Kahn's Algorithm (Topological Sort)
//   1. Build graph + compute inDegree for each node
//   2. Add all nodes with inDegree 0 to queue (no prerequisites)
//   3. Process queue: take course → reduce neighbours' inDegree
//   4. If neighbour's inDegree becomes 0 → add to queue
//   5. If processed == numCourses → no cycle → true
//      Else some courses stuck in cycle → false
//
// APPROACH 2: DFS — Cycle Detection (3-state)
//   state 0 = unvisited
//   state 1 = currently on this DFS path (visiting)
//   state 2 = fully processed (no cycle found)
//   If we reach a node with state 1 → back edge → CYCLE
//
// KEY INSIGHT (BFS):
//   A cycle means those nodes never reach inDegree 0.
//   They never enter the queue → never processed.
//   So processed < numCourses → cycle exists.
//
// KEY INSIGHT (DFS):
//   State 1 = "currently on my path". If I visit a state-1
//   node, I've looped back → cycle.
//
// Time:  O(V + E)
// Space: O(V + E)
// ============================================================

import java.util.*;

class LC207_CourseSchedule {

    // ---------- APPROACH 1: BFS (Kahn's Algorithm) ----------
    public boolean canFinish_BFS(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);  // b → a
            inDegree[pre[0]]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++)
            if (inDegree[i] == 0) queue.offer(i);

        int processed = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            processed++;
            for (int next : graph.get(course)) {
                inDegree[next]--;
                if (inDegree[next] == 0) queue.offer(next);
            }
        }

        return processed == numCourses;
    }

    // ---------- APPROACH 2: DFS (3-state cycle detection) ----------
    public boolean canFinish_DFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        for (int[] pre : prerequisites) graph.get(pre[1]).add(pre[0]);

        int[] state = new int[numCourses];  // 0=unvisited 1=visiting 2=done

        for (int i = 0; i < numCourses; i++)
            if (state[i] == 0)
                if (hasCycle(graph, state, i)) return false;

        return true;
    }

    private boolean hasCycle(List<List<Integer>> graph, int[] state, int node) {
        state[node] = 1;  // mark as currently visiting

        for (int next : graph.get(node)) {
            if (state[next] == 1) return true;   // back edge = cycle
            if (state[next] == 0)
                if (hasCycle(graph, state, next)) return true;
        }

        state[node] = 2;  // fully processed, no cycle from here
        return false;
    }
}
