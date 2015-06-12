/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * MoveToFrontLinkedList.java
 * Singly linked, non-circular linked list.
 *
 * Limitations:
 * Holds only 1 instance of any item. If duplicate item is added to list, item
 * is added to front of list and duplicate is removed.
 * No methods to remove additional items from list.
 */
public class MoveToFrontLinkedList<Item> {
    private Node head;
    private int size;

    private class Node {
        Node next;
        Node previous;
        Item data;
    }

    /*************************************************************************/
    /**
     * Check to see if list is empty.
     * @return boolean, true if list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*************************************************************************/
    /**
     * Get size of list.
     * @return int size of list.
     */
    public int size() {
        return size;
    }

    /*************************************************************************/
    /**
     * Add item to list, if item is already in list, add that item to the front
     * of list and remove item from previous position.
     * @param item Item to be placed at beginning of list.
     * @return int position item was previously in, -1 if item was not
     * previously in list.
     */
    public int add(Item item) {
        int position = 0;
        Node temp = new Node();
        if(isEmpty()) {
            head = temp;
            head.data = item;
            head.next = null;
            size++;
            return -1;
        } else {
            temp = head;
            head = new Node();
            head.data = item;
            head.next = temp;
            temp.previous = head;
            size++;
        }
        for(Node x = head.next; x != null; x = x.next) {
            if(x.data.equals(item)) {
                x.previous.next = x.next;
                size--;
                return position;
            }
            position++;
        }
        return -1;
    }
}
