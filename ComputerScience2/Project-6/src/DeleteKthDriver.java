/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * DeleteKthDriver.java
 * Takes input from the user, allowing them to select the data type they would
 * like to use for the rest of the program (array or linked list). After data
 * type selection, user is then prompted to initialize the size of the queue
 * they would like to use (because they could be using an array
 * implementation). After queue initialization, user is prompted to remove
 * some position from the queue, and is presented with a success/failure
 * message. This prompt continues until the user selects -1 to exit the
 * program.
 *
 * Limitations:
 * Assumes user inputs valid integer for size of queue.
 */

import java.util.Scanner;

public class DeleteKthDriver {
    private GeneralizedQueueArray queueArray;
    private GeneralizedQueueLinkedList queueList;
    private boolean choseArray = false;     // true of data type array is chosen
    private Scanner keyboard;
    private int queueSize;                  // holds user defined size of queue

    public DeleteKthDriver() {
        keyboard = new Scanner(System.in);
        String selection;
        selection = selectQueueType();
        if (selection.equals("array")) {
            queueArray = new GeneralizedQueueArray(queueSize);
            choseArray = true;
        } else {
            queueList = new GeneralizedQueueLinkedList(queueSize);
        }
    }

    public static void main(String[] args) {
        DeleteKthDriver driver = new DeleteKthDriver();
        driver.selectRemoval();
        System.exit(0);
    }

    /*************************************************************************/
    /**
     * Prompts user for data type selection, and queue size.
     * @return String selection, holds data type value (array or linked list)
     */
    public String selectQueueType() {
        String selection;
        boolean success = false;
        do {
            System.out.print("Enter the queue type you would like to " +
                    "implement (array or linked list): ");
            selection = keyboard.nextLine().toLowerCase().trim();
            if (selection.equals("array") || selection.equals("linked list") ||
                    selection.equals("-1")) {
                success = true;
            }
        } while (!success);
        if (selection.equals("-1")) {
            System.out.println("Exiting program.");
            keyboard.close();
            System.exit(0);
        }
        System.out.print("Enter the size of the queue you would like to create: ");
        queueSize = keyboard.nextInt();
        return selection;
    }

    /*************************************************************************/
    /**
     * Prompts user to select some position in the queue to remove, continues
     * to prompt until user selects system exti.
     */
    public void selectRemoval() {
        String message;
        String resultingQueue = "";
        int selection;
//        boolean queueIsEmpty = false;
        printOriginalQueue();
        do {
//            if(choseArray) {
//                queueIsEmpty = queueArray.isEmpty();
//                System.out.println("Your queue is empty, exiting program.");
//            } else {
//                queueIsEmpty = queueList.isEmpty();
//                System.out.println("Your queue is empty, exiting program.");
//            }
            System.out.print("Enter the location you would like to delete" +
                    "(enter -1 to exit): ");
            selection = keyboard.nextInt();
            if (selection == -1) {
                message = "Exiting program";
            } else if (selection < 1) {
                message = "You must enter a position greater than 0," +
                        " please try again.";
            } else if (choseArray) {
                message = queueArray.delete(selection);
                resultingQueue = queueArray.printQueue();
            } else {
                message = queueList.delete(selection);
                resultingQueue = queueList.printQueue();
            }
            System.out.println(message);
            System.out.println("Resulting queue: " + resultingQueue);
        } while (selection != -1);
        keyboard.close();
    }

    /*************************************************************************/
    /**
     * Prints the queue, method called before any deletions have been
     * performed, resulting in the printing of the original queue.
     */
    public void printOriginalQueue() {
        if(choseArray) {
            System.out.println("Original queue: " + queueArray.printQueue());
        } else {
            System.out.println("Original queue: " + queueList.printQueue());
        }
    }
}
