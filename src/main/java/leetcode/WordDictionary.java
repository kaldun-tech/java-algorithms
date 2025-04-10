package leetcode;

/**
 * https://leetcode.com/problems/design-add-and-search-words-data-structure/
 * 
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 * 
 * Implement the WordDictionary class:
 * - WordDictionary() Initializes the object.
 * - void addWord(word) Adds word to the data structure, it can be matched later.
 * - boolean search(word) Returns true if there is any string in the data structure that matches word, 
 *   or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 * 
 * Constraints:
 * - 1 <= word.length <= 25
 * - word in addWord consists of lowercase English letters.
 * - word in search consist of '.' or lowercase English letters.
 * - There will be at most 3 dots in word for search queries.
 * - At most 10^4 calls will be made to addWord and search.
 */
public class WordDictionary {
    /**
     * TrieNode represents a node in the Trie data structure.
     * Each node contains links to child nodes (one for each letter),
     * and a flag indicating if a word ends at this node.
     */
    private static class TrieNode {
        private final TrieNode[] children;
        private boolean isEndOfWord;
        
        /**
         * Initialize a new TrieNode with 26 possible children (a-z)
         * and set isEndOfWord to false by default.
         */
        public TrieNode() {
            children = new TrieNode[26]; // Assuming lowercase English letters only
            isEndOfWord = false;
        }
    }
    
    private final TrieNode root;
    
    /**
     * Initialize the WordDictionary with an empty Trie.
     */
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /**
     * Adds a word to the dictionary by inserting it into the Trie.
     * 
     * Time Complexity: O(m) where m is the length of the word
     * Space Complexity: O(m) in worst case if new nodes are created for each character
     * 
     * @param word The word to add to the dictionary
     */
    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            
            // Create a new node if the path doesn't exist
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            
            // Move to the next node
            current = current.children[index];
        }
        
        // Mark the end of the word
        current.isEndOfWord = true;
    }
    
    /**
     * Searches for a word in the dictionary. The word can contain dots '.'
     * where dots can be matched with any letter.
     * 
     * Time Complexity: 
     * - Best case: O(m) where m is the length of the word (no wildcards)
     * - Worst case: O(26^m) if all characters are wildcards
     * Space Complexity: O(m) for the recursion stack
     * 
     * @param word The word to search for
     * @return true if the word exists in the dictionary, false otherwise
     */
    public boolean search(String word) {
        if (word == null) {
            return false;
        }
        
        return searchInNode(word, 0, root);
    }
    
    /**
     * Helper method to recursively search for a word starting from a given node.
     * 
     * @param word The word to search for
     * @param index The current index in the word
     * @param node The current node in the Trie
     * @return true if the word exists from this node, false otherwise
     */
    private boolean searchInNode(String word, int index, TrieNode node) {
        // Base case: reached the end of the word
        if (index == word.length()) {
            return node.isEndOfWord;
        }
        
        char c = word.charAt(index);
        
        if (c == '.') {
            // Wildcard: try all possible paths
            for (TrieNode child : node.children) {
                if (child != null && searchInNode(word, index + 1, child)) {
                    return true;
                }
            }
            return false;
        } else {
            // Regular character: follow the specific path
            int childIndex = c - 'a';
            TrieNode child = node.children[childIndex];
            
            // Path doesn't exist
            if (child == null) {
                return false;
            }
            
            // Continue searching from the next node
            return searchInNode(word, index + 1, child);
        }
    }
}
