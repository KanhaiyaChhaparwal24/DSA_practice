// ============================================================
// LC 210 - Course Schedule II
// https://leetcode.com/problems/course-schedule-ii/
// ============================================================
// PROBLEM:
//   Same as LC 207, but return the order to take courses.
//   Return empty array if impossible (cycle exists).
//
// APPROACH 1: BFS — Kahn's Algorithm
//   Same as LC 207 BFS, but instead of counting processed,
//   store each processed course in order[] array.
//   If cycle exists → idx < numCourses → return empty array.
//
// APPROACH 2: DFS — Topological Sort via Stack
//   Same as LC 207 DFS, but after fully processing a node,
//   push it to a stack.
//   Stack gives REVERSE topological order → pop to get correct order.
//
// KEY INSIGHT (why stack for DFS):
//   DFS finishes deepest node first (no dependents).
//   That node should come LAST in order.
//   Stack naturally reverses: push deepest first, pop deepest last.
//
// Time:  O(V + E)
// Space: O(V + E)
// ============================================================

import java.util.*;

class LC210_CourseScheduleII {

    // ---------- APPROACH 1: BFS (Kahn's Algorithm) ----------
    public int[] findOrder_BFS(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
            inDegree[pre[0]]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++)
            if (inDegree[i] == 0) queue.offer(i);

        int[] order = new int[numCourses];
        int idx = 0;

        while (!queue.isEmpty()) {
            int course = queue.poll();
            order[idx++] = course;  // record order
            for (int next : graph.get(course)) {
                inDegree[next]--;
                if (inDegree[next] == 0) queue.offer(next);
            }
        }

        return idx == numCourses ? order : new int[]{};  // empty if cycle
    }

    // ---------- APPROACH 2: DFS (Topological Sort via Stack) ----------
    public int[] findOrder_DFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        for (int[] pre : prerequisites) graph.get(pre[1]).add(pre[0]);

        int[] state = new int[numCourses];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < numCourses; i++)
            if (state[i] == 0)
                if (hasCycle(graph, state, stack, i)) return new int[]{};

        int[] order = new int[numCourses];
        for (int i = 0; i < numCourses; i++)
            order[i] = stack.pop();  // pop stack = correct topological order

        return order;
    }

    private boolean hasCycle(List<List<Integer>> graph, int[] state, Stack<Integer> stack, int node) {
        state[node] = 1;

        for (int next : graph.get(node)) {
            if (state[next] == 1) return true;
            if (state[next] == 0)
                if (hasCycle(graph, state, stack, next)) return true;
        }

        state[node] = 2;
        stack.push(node);  // fully done → push to stack
        return false;
    }
}
