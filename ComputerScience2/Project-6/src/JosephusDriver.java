/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * JosephusDriver.java
 * Takes 2 arguments from command line and creates a queue based on these
 * arguments. Argument 0(m) is the size of the queue to make. Argument 1(n)
 * is the number of jumps to take within the queue. Program takes m, generates
 * queue, takes n, and eliminates queue positions sequentially in multiples of
 * n until there is a single position remaining. Reports to user order of
 * positions killed off and the surviving position.
 *
 * Limitations:
 * User must manually enter m and n in command line before running the program.
 * Program does not generate output file, but displays all results to console.
 */
public class JosephusDriver {
    private JosephusQueue<Integer> queue = new JosephusQueue<>();
    private int safePosition;
    public static void main(String[] args) {
        JosephusDriver josephus = new JosephusDriver();
        josephus.createQueue(Integer.parseInt(args[0]));
        josephus.killQueue(Integer.parseInt(args[1]));
    }

    /*************************************************************************/
    /**
     * Generates a new queue of size m (args[0]).
     * @param size determines size of queue to create.
     */
    public void createQueue(int size) {
        for (int i = 0; i < size; i++) {
            queue.enqueue(i);
        }
    }

    /*************************************************************************/
    /**
     * Removes every position in queue based on sequential, circular removal
     * of positions in multiples of n(args[0])
     * @param jumpLength int, size of n(args[0]) number of positions to skip
     *                   before removing another.
     */
    public void killQueue(int jumpLength) {
        int nextKilled;
        while(queue.size() > 1) {
            nextKilled = queue.dequeue(jumpLength);
            System.out.println("Position: " + nextKilled + " died :(");
        }
        safePosition = queue.dequeue(jumpLength);
        System.out.println("Position: " + safePosition + " survived!!!");
    }
}
