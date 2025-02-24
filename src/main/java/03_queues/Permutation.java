import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class Permutation {
    /** Takes an integer k as a command-line argument;
     *  Reads a sequence of strings from standard input using StdIn.readString();
     *  Prints exactly k of them, uniformly at random.
     *  Print each item from the sequence at most once. */
    public static void main(String[] args) {
        StdOut.print("Enter integer k: ");
        int k = 0;
        try {
            k = StdIn.readInt();
        } catch (NoSuchElementException e) {
            StdOut.println("Failed to read k from input");
            System.exit(1);
        }

        StdOut.println("Enter sequence of strings:");
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            queue.enqueue(next);
        }

        Iterator<String> it = queue.iterator();
        for (int i = 0; i < k; ++i) {
            String next = it.next();
            StdOut.println(next);
        }
        StdOut.println("Done!");
    }
}
