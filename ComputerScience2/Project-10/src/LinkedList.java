import java.io.PrintWriter;

/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * LinkedList.java
 * Linked list of type pet, used in association with Linked List Hash Table of
 * Pet objects. Allows for add, removal, and search of items in linked list.
 *
 * Limitations:
 * Strictly used for pet objects.
 */

public class LinkedList {
    private int size;
    private Node head;

    private class Node {
        private Pet data;
        private Node next;
    }

    public LinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Check to see if list is empty
     * @return boolean true if empty
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Get size of linked list
     * @return int size of list
     */
    public int size() {
        return size;
    }

    /**
     * Add pet object to linked list
     * @param item Pet object to be added
     */
    public void add(Pet item) {
        Node oldfirst = head;
        head = new Node();
        head.data = item;
        head.next = oldfirst;
        size++;
    }

    /**
     * Remove Pet item in list
     * @param item Pet item to be removed
     * @return item Pet removed item
     */
    public Pet remove(Pet item) {
        if (isEmpty()) throw new RuntimeException("Empty list");
        if (head.data == item) {
            Pet out = head.data;
            head = head.next;
            size--;
            return out;
        }
        for(Node x = head; x != null; x = x.next) {
            if (x.next.data == item) {
                Pet out = x.next.data;
                x.next = x.next.next;
                size--;
            }
        }
        return null;
    }

    /**
     * Remove first item in list, "pop"
     * @return Pet item that was removed
     */
    public Pet remove() {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        Pet item = head.data;
        head = head.next;
        size--;
        return item;
    }

    /**
     * Print linked list
     * @param outputFile file to print to
     */
    public void printList(PrintWriter outputFile) {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        for (Node x = head; x != null; x = x.next) {
            outputFile.println(">>\t" + x.data.getName() + "\t\t\t"
                    + x.data.getKey() + "\t\t" + x.data.getHash());
        }
    }

    /**
     * Search for item with particular key in linked list
     * @param key int key being searched for
     * @return boolean, true if found
     */
    public boolean search(int key) {
        for (Node x = head; x != null; x = x.next) {
            if (x.data.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete item with specific key from list
     * @param key int item with key to be deleted
     * @return boolean, true if deleted
     */
    public boolean delete(int key) {
        Node x = head;
        if (x != null) {
            if (x.data.getKey() == key) {
                head = head.next;
                size--;
                return true;
            } else {
                for (x = head; x != null; x = x.next) {
                    try {
                        if (x.next.data.getKey() == key) {
                            x.next = x.next.next;
                            size--;
                            return true;
                        }
                    } catch(Exception e) {
                        // null pointer exception
                    }
                }
            }
        }
        return false;
    }
}