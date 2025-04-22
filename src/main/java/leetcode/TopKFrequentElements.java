package leetcode;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        // Space complexity O(n)
        HashMap<Integer, Integer> numFrequency = new HashMap<>();
        // Build mapping: number to frequency O(n)
        for (int n : nums) {
            if (numFrequency.containsKey(n)) {
                int f = numFrequency.get(n);
                numFrequency.put(n, ++f);
            } else {
                numFrequency.put(n, 1);
            }
        }
        // Now sort the frequencies and find the k-th highest O(n)
        Collection<Integer> frequencies = numFrequency.values();
        int[] fArray = frequencies.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(fArray);
        int kthHighest = fArray[fArray.length - k];

        // Now iterate to find frequencies >= kthHighest
        int[] topK = new int[k];
        int count = 0;
        for (int n : numFrequency.keySet()) {
            int f = numFrequency.get(n);
            if (kthHighest <= f) {
                topK[count] = n;
                ++count;
            }
        }

        return topK;
    }
}
