import edu.princeton.cs.algs4.StdIn;

/**
 * Takes an integer k as a command-line argument;
 * reads in a sequence of strings from standard input using StdIn.readString();
 * prints exactly k of them, uniformly at random.
 * Each item from the sequence is printed at most once.
 */
public class Permutation {

    /**
     * Starting point.
     *
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()){
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++){
            System.out.println(rq.dequeue());
        }
    }
}
