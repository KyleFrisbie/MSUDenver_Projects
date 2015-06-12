import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * LinkedListTimer generates a singly linked list of sample data, and times the
 * efficiency of different methods of printing the linked list in reverse
 * including: a recursive solution, copying the list into an array and
 * transversing the array in reverse, copying the list into an ArrayList and
 * transversing the ArrayList in reverse, copying the list to a stack and
 * popping the result, and generating a doubly-linked list using its own back
 * pointers.
 * Created by Kyle on 10/17/2014.
 */
public class LinkedListTimer {
    private String outputFileName = "LinkedListTimer_Output_KyleLFrisbie.txt";
    private PrintWriter outputFile;
//    private LinkedList<Integer> linkedList;
//    private DoublyLinkedList<Integer> doubleLink;
    private java.util.LinkedList<Integer> linkedList;
    private java.util.LinkedList<Integer> doubleLink;

    public static void main(String[] args) {
        LinkedListTimer linkTime = new LinkedListTimer();
        linkTime.generateOutputFile();
        for (int listSize = 100; listSize < 100000; listSize *= 10) {
            linkTime.generateLinkedList(listSize);
            long startTime, endTime;

            // recursive test
            startTime = System.nanoTime();
            linkTime.recursiveTimer();
            endTime = System.nanoTime();
            long recursiveTime = endTime - startTime;

            // remake linkedList
            linkTime.generateLinkedList(listSize);

            // array test
            startTime = System.nanoTime();
            linkTime.arrayTimer();
            endTime = System.nanoTime();
            long arrayTime = endTime - startTime;

            // remake linkedList
            linkTime.generateLinkedList(listSize);

            // ArrayList test
            startTime = System.nanoTime();
            linkTime.arrayListTimer();
            endTime = System.nanoTime();
            long arrayListTime = endTime - startTime;
            
            // remake linkedList
            linkTime.generateLinkedList(listSize);

            // stack test
            startTime = System.nanoTime();
            linkTime.stackTimer();
            endTime = System.nanoTime();
            long stackTime = endTime - startTime;

            // remake linkedList
            linkTime.generateLinkedList(listSize);

            // create doublyLinkedList
            linkTime.generateDoubleList(listSize);

            // double link list test
            startTime = System.nanoTime();
            linkTime.doubleLinkListTimer();
            endTime = System.nanoTime();
            long doubleLinkListTime = endTime - startTime;

            // print results
            linkTime.outputFileTable(listSize, recursiveTime, arrayTime, arrayListTime, stackTime, doubleLinkListTime);
        }
        linkTime.endProgram();
    }

    // general program methods
    public void generateOutputFile() {
        try {
            FileWriter outputDataFile = new FileWriter(outputFileName);
            System.out.println("Creating File: " + outputFileName);
            outputFile = new PrintWriter(outputDataFile);
            outputFile.println("LinkedListTimer.java");
            outputFile.println("Author: Kyle L Frisbie");
            outputFile.println();
        } catch(IOException e) {
            System.out.println("There was an error creating your output file," +
                    " please check your output file location.");
            System.exit(1);
        }
    }

    public void outputFileTable(int listSize, long recursiveTime, long arrayTime, long arrayListTime, long stackTime, long doubleLinkListTime) {
        outputFile.printf("%15s%15s%15s%15s%15s%15s", "List Size", "Recursive", "Array", "ArrayList", "Stack", "DoublyLinked");
        outputFile.println();
        outputFile.printf("%15d%15d%15d%15d%15d%15d", listSize, recursiveTime, arrayTime, arrayListTime, stackTime, doubleLinkListTime);
        outputFile.println();
        System.out.printf("%15s%15s%15s%15s%15s%15s", "List Size", "Recursive", "Array", "ArrayList", "Stack", "DoublyLinked");
        System.out.println();
        System.out.printf("%15d%15d%15d%15d%15d%15d", listSize, recursiveTime, arrayTime, arrayListTime, stackTime, doubleLinkListTime);
        System.out.println();
    }

    public void generateLinkedList(int listSize) {
        linkedList = new LinkedList<>();
        for (int i = 0; i < listSize; i++) {
            linkedList.add(i);
        }
    }


    // recursive solution
    public void recursiveTimer() {
        int temp;
        if(linkedList.isEmpty()) {
            // do nothing
        } else {
            temp = linkedList.remove();
            recursiveTimer();
        }
    }
    // copying list to an array and transversing array in reverse
    public void arrayTimer() {
        int[] listArray = new int[linkedList.size()];
        for (int i = 0; i < listArray.length; i++) {
            listArray[i] = linkedList.remove();
        }
        for (int i = listArray.length - 1; i >= 0 ; i--) {
            int temp = listArray[i];
        }
    }

    // copying list to ArrayList and transversing ArrayList in reverse
    public void arrayListTimer() {
        ArrayList<Integer> listArrayList=
                new ArrayList<Integer>(linkedList.size());
        for (int i = 0; i < listArrayList.size(); i++) {
            listArrayList.add(linkedList.remove());
        }
        for (int i = listArrayList.size() - 1; i >= 0 ; i--) {
            int temp = listArrayList.get(i);
        }
    }

    // copying list to stack and popping result
    public void stackTimer() {
        Stack<Integer> stack = new Stack<>(linkedList.size());
        while(!linkedList.isEmpty()) {
            stack.push(linkedList.remove());
        }
        while(!stack.isEmpty()) {
            int temp = stack.pop();
        }
    }

    // possible addition trial: doubly-linked list, using its own back pointers
    public void generateDoubleList(int listSize) {
        doubleLink = new LinkedList<Integer>();
        for (int i = 0; i < listSize; i++) {
            doubleLink.add(i);
        }
    }

    public void doubleLinkListTimer() {
        int temp;
        while(!doubleLink.isEmpty()) {
            temp = doubleLink.removeLast();
        }
    }

    public void endProgram() {
        outputFile.close();
        System.exit(0);
    }
}