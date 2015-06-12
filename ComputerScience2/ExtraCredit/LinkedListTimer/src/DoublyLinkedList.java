/**
 * Created by Kyle on 10/17/2014.
 */
public class DoublyLinkedList<Item> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Node next;
        Node previous;
        Item data;
    }

    public void enqueue(Item item) {
        Node oldTail = tail;
        tail = new Node();
        tail.data = item;
        tail.next = null;
        if(getSize() > 2) {
            tail.previous = oldTail;
            oldTail.next = tail;
        } else if(isEmpty()) {
            tail.previous = null;
            head = tail;
        } else {
            tail.previous = head;
            head.next = tail;
        }
        size++;
    }

    public Item dequeue() {
        Item item = head.data;
        head = head.next;
        head.previous = null;
        if(isEmpty()) {
            head = null;
        }
        size--;
        return item;
    }

    public Item getLastItem() {
        Item item = tail.data;
        tail  = tail.previous;
        tail.next = null;
        if(isEmpty()) {
            tail = null;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}
