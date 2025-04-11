package leetcode;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 *
 * Example 2:
 * Input: lists = []
 * Output: []
 *
 * Example 3:
 * Input: lists = [[]]
 * Output: []
 *
 * Constraints:
 * - k == lists.length
 * - 0 <= k <= 10^4
 * - 0 <= lists[i].length <= 500
 * - -10^4 <= lists[i][j] <= 10^4
 * - lists[i] is sorted in ascending order.
 * - The sum of lists[i].length will not exceed 10^4.
 */
public class MergeKSortedLists {

    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Merges k sorted linked lists using a divide and conquer approach.
     * This approach recursively divides the list of lists into halves,
     * merges each half, and then combines the results.
     *
     * Time Complexity: O(N log k) where N is the total number of nodes and k is the number of lists
     * Space Complexity: O(log k) for the recursion stack
     *
     * @param lists Array of sorted linked lists
     * @return Merged sorted linked list
     */
    public ListNode mergeKListsDivideAndConquer(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return mergeKListsHelper(lists, 0, lists.length - 1);
    }

    /**
     * Helper method to merge two sorted linked lists.
     *
     * @param l1 First sorted linked list
     * @param l2 Second sorted linked list
     * @return Merged sorted linked list
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // The head is a dummy pointing to the merged lists
        ListNode head = new ListNode();
        ListNode tail = head;

        // Interleave the lesser nodes of both lists
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                // Append l1
                tail.next = l1;
                tail = tail.next;
                l1 = l1.next;
            } else {
                // Append l2
                tail.next = l2;
                tail = tail.next;
                l2 = l2.next;
            }
        }
        // Merge remainder of lists 1 and 2
        while (l1 != null) {
            tail.next = l1;
            tail = tail.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            tail.next = l2;
            tail = tail.next;
            l2 = l2.next;
        }

        return head.next;
    }

    /**
     * Recursive helper for the divide and conquer approach.
     * Divides the lists array into halves, recursively merges each half,
     * and then combines the results using mergeTwoLists.
     *
     * @param lists Array of sorted linked lists
     * @param start Start index
     * @param end End index
     * @return Merged sorted linked list
     */
    private ListNode mergeKListsHelper(ListNode[] lists, int start, int end) {
        // Base cases
        if (end < start) {
            return null;
        }
        if (start == end) {
            return lists[start];
        }
        
        // Divide the array in half
        int mid = start + (end - start) / 2;
        
        // Recursively merge each half
        ListNode left = mergeKListsHelper(lists, start, mid);
        ListNode right = mergeKListsHelper(lists, mid + 1, end);
        
        // Combine the results
        return mergeTwoLists(left, right);
    }

    /**
     * Merges k sorted linked lists using a priority queue (min heap).
     * This approach maintains a min heap of list nodes, always extracting the smallest node
     * and adding its next node to the heap.
     *
     * Time Complexity: O(N log k) where N is the total number of nodes and k is the number of lists
     * Space Complexity: O(k) for the priority queue
     *
     * @param lists Array of sorted linked lists
     * @return Merged sorted linked list
     */
    public ListNode mergeKListsPriorityQueue(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        // Use a min heap to compare nodes by their values
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Add all non-null head nodes to the queue
        for (ListNode node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }

        // Build the merged list
        ListNode head = new ListNode();
        ListNode tail = head;

        // Process nodes in order of increasing value
        while (!pq.isEmpty()) {
            // Poll the smallest node
            ListNode n = pq.poll();
            tail.next = n;
            tail = tail.next;

            // Add next node from the same list to the priority queue
            if (n.next != null) {
                pq.add(n.next);
            }
        }

        return head.next;
    }
}
