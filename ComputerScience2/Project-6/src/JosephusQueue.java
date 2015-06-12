/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * JosephusQueue.java
 * ADT queue using singly linked, non-circular linked lists.
 */
public class JosephusQueue<Item> {
    private Node tail;
    private int size;

    private class Node {
        Item data;
        Node next;
    }

    /*************************************************************************/
    /**
     * Check to see if queue is empty.
     * @return boolean, true if queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*************************************************************************/
    /**
     * Get size of queue.
     * @return int size of queue.
     */
    public int size() {
        return size;
    }

    /*************************************************************************/
    /**
     * Add item to beginning of queue.
     * @param item Item to be placed at beginning of queue.
     */
    public void enqueue(Item item) {
        Node temp = new Node();
        if(isEmpty()) {
            tail = temp;
            tail.data = item;
            tail.next = tail;
        }else if(tail == tail.next) {
            temp = tail;
            tail = new Node();
            tail.data = item;
            tail.next = temp;
            temp.next = tail;
        } else {
            temp = tail;
            tail = new Node();
            tail.data = item;
            tail.next = temp.next;
            temp.next = tail;
        }
        size++;
    }

    /*************************************************************************/
    /**
     * Remove every item in queue, starting at beginning. Removes items in
     * multiples of jumpLength.
     * @param jumpLength int length to next position to remove.
     * @return Item contents at removed position.
     */
    public Item dequeue(int jumpLength) {
        int count = 1;
        Node previousSeat = tail;
        Node currentSeat = tail.next;
        while(count < jumpLength) {
            previousSeat = previousSeat.next;
            currentSeat = currentSeat.next;
            count++;
        }
        Item item = currentSeat.data;
        previousSeat.next = currentSeat.next;
        tail = previousSeat;
        if(isEmpty()) {
            tail = null;
        }
        size--;
        return item;
    }
}
