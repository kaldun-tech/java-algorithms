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
        List<Integer> result = new ArrayList<>();
        Map<Integer, List<Integer>> adjList = buildAdjacencyList(numCourses, prerequisites);
        // 0 = unvisited, 1 = visiting (in current path), 2 = visited (completed)
        int[] visited = new int[numCourses];

        // Check each course
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (hasCycleDFS(i, adjList, visited, result)) {
                    // Cycle detected, impossible to complete all courses
                    return new int[0];
                }
            }
        }

        // Convert list to array (in reverse order for correct topological sort)
        int[] order = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            order[i] = result.get(numCourses - 1 - i);
        }
        
        return order;
    }

    /**
     * Helper method for DFS approach to detect cycles and build the topological ordering.
     *
     * @param course The current course to process
     * @param adjList The adjacency list representation of the graph
     * @param visited Array to track visited status: 0=unvisited, 1=visiting, 2=visited
     * @param result List to store the topological ordering
     * @return true if a cycle is detected, false otherwise
     */
    private boolean hasCycleDFS(int course, Map<Integer, List<Integer>> adjList, int[] visited, List<Integer> result) {
        // Mark as visiting
        visited[course] = 1;
        
        // Visit all neighbors
        for (int neighbor : adjList.get(course)) {
            if (visited[neighbor] == 1) {
                // Already in the current path - cycle detected
                System.out.println("Detected cycle at course " + neighbor);
                return true;
            } else if (visited[neighbor] == 0) {
                // Unvisited - recursively check
                if (hasCycleDFS(neighbor, adjList, visited, result)) {
                    return true;
                }
            }
            // If visited[neighbor] == 2, it's already processed, so skip
        }
        
        // Mark as visited and add to result
        visited[course] = 2;
        result.add(course);
        
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
        // Build graph and calculate in-degrees
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        int[] inDegree = new int[numCourses];
        
        // For each prerequisite [a,b], b is prerequisite of a
        // Add edge from b to a and increment in-degree of a
        for (int[] prereq : prerequisites) {
            int course = prereq[0];       // Course that has a prerequisite
            int prerequisite = prereq[1]; // Prerequisite course
            graph.get(prerequisite).add(course);
            inDegree[course]++;
        }
        
        // Add all courses with no prerequisites to the queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        
        // Process courses in topological order
        int[] order = new int[numCourses];
        int index = 0;
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            order[index++] = current;
            
            // For each course that depends on current course
            for (int dependent : graph.get(current)) {
                // Decrement in-degree and add to queue if in-degree becomes 0
                inDegree[dependent]--;
                if (inDegree[dependent] == 0) {
                    queue.add(dependent);
                }
            }
        }
        
        // If we couldn't process all courses, a cycle exists
        if (index != numCourses) {
            System.out.println("Cycle detected, impossible to complete all courses");
            return new int[0];
        }
        
        return order;
    }

    /**
     * Build the adjacency list representation of the graph.
     *
     * @param numCourses The number of courses
     * @param prerequisites The prerequisite relationships between courses in form [a, b]
     * @return A map where the key is a course and the value is a list of courses that depend on it
     */
    private Map<Integer, List<Integer>> buildAdjacencyList(int numCourses, int[][] prerequisites) {
        // Start by building unconnected graph of empty lists
        Map<Integer, List<Integer>> map = new HashMap<>();
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
            // Prerequisite relationship is an edge from b to a
            List<Integer> adjacent = map.get(b);
            assert adjacent != null;
            adjacent.add(a);
        }
        return map;
    }
}
