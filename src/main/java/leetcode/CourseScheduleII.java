package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * https://leetcode.com/problems/course-schedule-ii/
 *
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you
 * must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return the ordering of courses you should take to finish all courses. If there are many valid
 * answers, return any of them. If it is impossible to finish all courses, return an empty array.
 *
 * Constraints:
 * - 1 <= numCourses <= 2000
 * - 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * - prerequisites[i].length == 2
 * - 0 <= ai, bi < numCourses
 * - ai != bi
 * - All the pairs [ai, bi] are distinct.
 */
public class CourseScheduleII {

    /**
     * Find the order of courses to take using Depth-First Search (DFS).
     *
     * Algorithm:
     * 1. Build an adjacency list representation of the graph
     * 2. Use DFS to detect cycles and build the topological ordering
     * 3. Return the ordering if no cycles are detected, otherwise return an empty array
     *
     * Time Complexity: O(V + E) where V is the number of vertices (courses) and
     *                 E is the number of edges (prerequisites)
     * Space Complexity: O(V + E) for the adjacency list and visited arrays
     *
     * @param numCourses The number of courses
     * @param prerequisites The prerequisite relationships between courses
     * @return An array with the order of courses to take, or an empty array if impossible
     */
    public int[] findOrderDFS(int numCourses, int[][] prerequisites) {
        // TODO: Implement DFS approach for topological sorting
        return new int[0];
    }

    /**
     * Helper method for DFS approach to detect cycles and build the topological ordering.
     *
     * @param course The current course to process
     * @param adjList The adjacency list representation of the graph
     * @param visited Array to track visited status: 0=unvisited, 1=visiting, 2=visited
     * @param result List to store the topological ordering
     * @return true if no cycle is detected, false otherwise
     */
    private boolean dfs(int course, Map<Integer, List<Integer>> adjList, int[] visited, List<Integer> result) {
        // TODO: Implement DFS helper method
        return false;
    }

    /**
     * Find the order of courses to take using Breadth-First Search (BFS) - Kahn's Algorithm.
     *
     * Algorithm:
     * 1. Build an adjacency list and calculate in-degree for each vertex
     * 2. Start with all vertices that have in-degree of 0 (no prerequisites)
     * 3. For each processed vertex, decrement the in-degree of its neighbors
     * 4. Add vertices to the queue as their in-degree becomes 0
     * 5. If we can't process all vertices, a cycle exists
     *
     * Time Complexity: O(V + E) where V is the number of vertices (courses) and
     *                 E is the number of edges (prerequisites)
     * Space Complexity: O(V + E) for the adjacency list and queue
     *
     * @param numCourses The number of courses
     * @param prerequisites The prerequisite relationships between courses
     * @return An array with the order of courses to take, or an empty array if impossible
     */
    public int[] findOrderBFS(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjacencies = buildAdjacencyList(numCourses, prerequisites);
        assert adjacencies != null;
        assert adjacencies.size() == numCourses;

        Queue<Integer> q = new LinkedList();
        int inDegrees = new int[numCourses];
        for (int i = 0; i < numCourses; ++i) {
            inDegrees[i] = adjacencies.get(i).size();
            if (inDegrees[i] == 0) {
                // Add to queue
                q.add(i);
            }
        }

        int order = new int[numCourses];
        int count = 0;
        while (!q.isEmpty()) {
            // Add next in queue to order
            int next = q.remove();
            order[count++] = next;
            // Check prereqs
            List<Integer> prereqs = adjacencies.get(next);
            for (Integer p : prereqs) {
                inDegrees[p]--;
                if (inDegrees[p] == 0) {
                    q.add(a);
                }
            }
        }

        // Not getting through all the courses indicates a cycle
        if (count != numCourses) {
            for (int i = 0; i < numCourses; ++i) {
                if (0 < inDegrees[i]) {
                    System.out.println("Found cycle at course " + i);
                    return new int[];
                }
            }
        }

        return new int[0];
    }

    /**
     * Build the adjacency list representation of the graph.
     *
     * @param numCourses The number of courses
     * @param prerequisites The prerequisite relationships between courses in form [a, b]
     * @return A map where the key is a course and the value is a list of its prerequisites
     */
    private Map<Integer, List<Integer>> buildAdjacencyList(int numCourses, int[][] prerequisites) {
        // Start by building unconnected graph of empty lists
        Map<Integer, <List<Integer>>> map = new HashMap<>();
        for (int i = 0; i < numCourses; ++i) {
            List<Integer> list = new ArrayList<>();
            map.put(i, list);
        }

        if (prerequisites == null || prerequisites.length == 0) {
            return map;
        }

        // Connect the graph based on prerequisites
        for (int i = 0; i < prerequisites.length; ++i) {
            // Form is [a, b] where b is prerequisite of a
            assert prerequisites[i].length == 2;
            int a = prerequisites[i][0];
            int b = prerequisites[i][1];
            // Point prerequisite relationship from from a to b
            List<ArrayList> prereqsA = map.get(a);
            assert prereqsA != null;
            prereqsA.add(b);
        }
        return map;
    }
}
