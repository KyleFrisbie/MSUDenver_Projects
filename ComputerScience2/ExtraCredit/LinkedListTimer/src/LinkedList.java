/**
 * LinkedList:
 *  implements a stack using linked list.
 * @author Kyle L Frisbie
 * @version 2.0
 */
public class LinkedList<Item> {
    private Node head;
    private Node tail;
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
    } // Node

    /*************************************************************************/
    /**
     * pushes item to the top of the stack
     * @param itemIn Item to be placed at the top of the stack
     */
    public void enqueue(Item itemIn) {
        Node oldTail = tail;
        tail = new Node();
        tail.item = itemIn;
        if(isEmpty()) {
            head = tail;
        } else if(getSize() == 1) {
            head.next = tail;
        }else {
            tail.next = oldTail;
        }
        size++;
    } // push

    /*************************************************************************/
    /**
     * removes Item from the top of the stack
     * @return Item item from top of stack
     */
    public Item dequeue() {
        Item item = head.item;
        head = head.next;
        size--;
        return item;
    } // pop

    /*************************************************************************/
    /**
     * checks to see if the stack is empty
     * @return boolean, true if stack is empty
     */
    public boolean isEmpty() {
        return size == 0;
    } // isEmpty

    /*************************************************************************/
    /**
     * view the size of the stack
     * @return int size, size of the stack
     */
    public int getSize() {
        return size;
    } // getSize
}


///**
// * Created by Kyle on 10/16/2014.
// */
//public class LinkedList<Item> {
//    private class Node {
//        Item data;
//        Node next;
//    }
//
//    private int size;
//    private Node head;
//    private Node tail;
//
//    public void enqueue(Item item) {
//        if(isEmpty()) {
//            head.data = item;
//            head.next = null;
//            tail = head;
//        } else {
//            Node oldTail = tail;
//            tail = new Node();
//            tail.data = item;
//            tail.next = null;
//            oldTail.next = tail;
//        }
//        size++;
//
////        Node oldTail = tail;
////        tail = new Node();
////        tail.data = item;
////        tail.next = null;
////        if(isEmpty()) {
////            head = tail;
////        } else {
////            oldTail.next = tail;
////        }
////        size++;
//    }
//
//    public Item dequeue() {
//        Item item = head.data;
//        head = head.next;
//        if(isEmpty()) {
//            head = null;
//        }
//        size--;
//        return item;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    public int getSize() {
//        return size;
//    }
//}
