/**
 * @author Kyle L Frisbie
 * @version 2
 *
 * GeneralizedQueueArray.java
 * Abstract data type using an array of int to implement a queue style.
 * Used by DeleteKthDriver to create a queue and remove positions from it.
 *
 * Limitations:
 * Requires client to initialize size of starting queue.
 */
public class GeneralizedQueueArray {
    private int[] queueArray;
    private int queueArraySize;

    /*************************************************************************/
    public GeneralizedQueueArray(int queueSize) {
        queueArraySize = 0;
        queueArray = new int[queueSize];
        for (int i = 1; i <= queueSize; i++) {
            insert(i);
        }
    }

    /*************************************************************************/
    /**
     * Check to see if queue is empty
     * @return boolean, true if empty
     */
    public boolean isEmpty() {
        return queueArraySize == 0;
    }

    /*************************************************************************/
    /**
     * Get size of queue
     * @return int size
     */
    public int size() {
        return queueArraySize;
    }

    /*************************************************************************/
    /**
     * Insert an item at the beginning of the queue.
     * @param itemIn takes int item to insert at beginning of queue
     */
    public void insert(int itemIn) {
        queueArray[queueArraySize] = itemIn;
        queueArraySize++;
    }

    /*************************************************************************/
    /**
     * Remove an item from client defined position in the queue, and shifts
     * rest of the queue up to fill the space of the item.
     * @param kPosition int that designates which position will be removed from
     *                  the queue.
     * @return String containing message regarding success of deletion.
     */
    public String delete(int kPosition) {
        if(kPosition > queueArraySize) {
            return "The position you've entered is outside of the queue";
        } else if(isEmpty()) {
            return "The queue is empty, nothing was removed";
        } else {
            for (int i = kPosition; i < queueArraySize; i++) {
                queueArray[i - 1] = queueArray[i];
            }
            queueArraySize--;
            return "The position you selected was successfully removed";
        }
    }

    /*************************************************************************/
    /**
     * Prints current items in queue
     * @return String queueString, items in queue
     */
    public String printQueue() {
        String queueString = "";
        for (int i = 0; i < queueArraySize; i++) {
            queueString += queueArray[i];
        }
        return queueString;
    }
}
