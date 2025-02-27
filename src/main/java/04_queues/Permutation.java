import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    /** Takes an integer k as a command-line argument;
     *  Reads a sequence of strings from standard input using StdIn.readString();
     *  Prints exactly k of them, uniformly at random.
     *  Print each item from the sequence at most once. */
    public static void main(String[] args) {
        StdOut.print("Enter integer k: ");
        int k = 0;
        try {
            String str = StdIn.readString();
            k = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            StdOut.println("Failed to read and parse k from input");
            return;
        }

        StdOut.println("Enter sequence of strings:");
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            queue.enqueue(next);
        }

        for (int i = 0; i < k; ++i) {
            StdOut.println(queue.dequeue());
        }
        StdOut.println("Done!");
    }
}
