///**
// * Created by Kyle on 11/20/2014.
// */
//public class CarLinkedList extends Car {
//
//    private Node head;
//    private int size;
//
//    private class Node {
//        Node next;
//        Car data;
//    }
//
//    public void addCar(Car car) {
//        Node temp = new Node();
//        if (!isEmpty()) {
//            temp = head;
//            head = new Node();
//            head.next = temp;
//            head.data = car;
//        } else {
//            head = temp;
//            head.data = car;
//        }
//        size++;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//}
