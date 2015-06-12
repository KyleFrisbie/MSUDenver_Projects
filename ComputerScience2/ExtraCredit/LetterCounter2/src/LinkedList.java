/**
 * LinkedList:
 *  implements a stack using linked list.
 * @author Kyle L Frisbie
 * @version 2.0
 */
public class LinkedList<Item> {
    private Node top;
    private int size;

    /*************************************************************************/
    /**
     * data type for linked list containing "next" which points to the next
     * item in the list, and "item" which contains the contents of the item
     * in the list.
     */
    private class Node {
        Node next;
        Item item;
        int count = 1;
    } // Node

    /*************************************************************************/
    /**
     * pushes item to the top of the stack
     * @param itemIn Item to be placed at the top of the stack
     */
    public void push(Item itemIn) {
        Node oldTop = top;
        top = new Node();
        top.item = itemIn;
        top.next = oldTop;
        size++;
    } // push

    /*************************************************************************/
    /**
     * verifies if an item exists in the stack
     * @param itemIn Item to be compared to the items in the stack
     * @return boolean, true if the item exists
     */
    public boolean itemExists(Item itemIn) {
        for(Node x = top; x != null; x = x.next) {
            if (itemIn.equals(x.item)) {
                x.count++;
                return true;
            }
        }
        return false;
    } // itemExists

    /*************************************************************************/
    /**
     * removes Item from the top of the stack
     * @return Item item from top of stack
     */
    public Item pop() {
        Item item = top.item;
        top = top.next;
        size--;
        return item;
    } // pop

    /*************************************************************************/
    /**
     * checks to see if the stack is empty
     * @return boolean, true if stack is empty
     */
    public boolean isEmpty() {
        return top == null;
    } // isEmpty

    /*************************************************************************/
    /**
     * view the size of the stack
     * @return int size, size of the stack
     */
    public int getSize() {
        return size;
    } // getSize

    /*************************************************************************/
    /**
     * special method allowing client to view the count of the node (the
     * number of times a word occurs in the input
     * file)
     * @return int top.count; the number of occurrences of the word on the
     *         top of the stack from the input file
     */
    public int getTopCount() {
        return top.count;
    } // getTopCount
}
