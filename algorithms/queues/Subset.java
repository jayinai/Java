/**
 * Client program that takes a command-line integer k; reads in a sequence of N
 * strings from standard input; and prints out exactly k of them, uniformly at
 * random. Each item from the sequence can be printed out at most once.
 * 
 * @author Shuai Wang
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        for (int i = 0; i < k; i++)
            StdOut.println(rq.dequeue());
    }

}
