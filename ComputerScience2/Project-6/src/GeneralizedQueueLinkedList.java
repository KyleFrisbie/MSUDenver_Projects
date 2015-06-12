/**
 * @author Kyle L Frisbie
 * @version 2
 *
 * GeneralizedQueueLinkedList.java
 * Abstract data type using a linked list of int to implement a queue style.
 * Used by DeleteKthDriver to create a queue and remove positions from it.
 *
 * Limitations:
 * Requires client to initialize size of starting queue.
 */
public class GeneralizedQueueLinkedList<Integer> {
    private Node head;
    private int size;

    /*************************************************************************/
    public GeneralizedQueueLinkedList(int queueSize) {
        for (int i = 1; i <= queueSize; i++) {
            insert(i);
        }
    }

    /*************************************************************************/
    private class Node {
        Node next;
        Node previous;
        int data;
    }

    /*************************************************************************/
    /**
     * Check to see if queue is empty
     * @return boolean, true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*************************************************************************/
    /**
     * Get size of queue
     * @return int size
     */
    public int size() {
        return size;
    }

    /*************************************************************************/
    /**
     * Insert an item at the beginning of the queue.
     * @param itemIn takes int item to insert at beginning of queue
     */
    public void insert(int itemIn) {
        Node temp = new Node();
        if(isEmpty()) {
            head = temp;
            head.data = itemIn;
            head.next = null;
            head.previous = null;
        } else {
            temp = head;
            while(temp.next != null) {
                temp = temp.next;
            }
            Node tempTwo = new Node();
            temp.next = tempTwo;
            tempTwo.previous = temp;
            tempTwo.data = itemIn;
        }
        size++;
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
        String message = "Something went wrong with your removal";
        if(kPosition > size) {
            message = "The position you've entered is outside of the queue";
        } else if(isEmpty()) {
            return "The queue is empty, nothing was removed";
        } else {
            Node x = head;
            for (int i = 1; i <= size; i++) {
                if(i == kPosition) {
                    if(size == 1) {
                        x = null;
                    } else if(x.previous == null) {
                        head = x.next;
                        head.previous = null;
                        head.next = x.next.next;
                    } else if(x.next == null) {
                        x.previous.next = null;
                    } else {
                        x.previous.next = x.next;
                        x.next.previous = x.previous;
                    }
                    size--;
                    message = "The position you selected was successfully removed";
                    return message;
                }
                x = x.next;
            }
        }
        return message;
    }

    /*************************************************************************/
    /**
     * Prints current items in queue
     * @return String queueString, items in queue
     */
    public String printQueue() {
        Node temp = head;
        String queueString = "";
        for (int i = 0; i < size; i++) {
            queueString += temp.data;
            if(temp.next == null) {
                break;
            } else {
                temp = temp.next;
            }
        }
        return queueString;
    }
}
