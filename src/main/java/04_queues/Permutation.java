import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    /** Takes an integer k as a command-line argument;
     *  Reads a sequence of strings from standard input using StdIn.readString();
     *  Prints exactly k of them, uniformly at random.
     *  Print each item from the sequence at most once. */
    public static void main(String[] args) {
        int k = 0;
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            StdOut.println("Failed to read and parse k from input");
            return;
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            queue.enqueue(next);
        }

        for (int i = 0; i < k; ++i) {
            StdOut.println(queue.dequeue());
        }
    }
}
