/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * MoveToFrontDriver.java
 * Takes input from file, creates array of characters from input, adds
 * characters to a linked list. Only one copy of each character is stored in
 * linked list, if duplicate characters are added, duplicate character is added
 * to front and previous position of duplicate character is removed from list.
 *
 * Limitations:
 * Assumes a properly formatted input file for character array analysis.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveToFrontDriver {
    private String inputFileName =
            "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\MSU-DENVER" +
                    "\\CS-2\\Project-6\\Input_MoveToFront_KyleLFrisbie.txt";
    private Scanner inputFile;
    private String outputFileName =
            "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\MSU-DENVER" +
                    "\\CS-2\\Project-6\\Output_MoveToFront_KyleLFrisbie.txt";
    private PrintWriter outputFile;
    private ArrayList<Character> charArray;
    private int charArraySize;
    MoveToFrontLinkedList<Character> list;

    /*************************************************************************/
    public MoveToFrontDriver() {
        processFiles();
        charArray = new ArrayList<>();
        generateCharArray();
        list = new MoveToFrontLinkedList<>();
    }

    /*************************************************************************/
    public static void main(String[] args) {
        MoveToFrontDriver driver = new MoveToFrontDriver();
        driver.addCharsToList();
        driver.outputFile.close();
        System.exit(0);
    }

    /*************************************************************************/
    /**
     * Get input file, create output file.
     */
    public void processFiles() {
        try {
            File inputDataFile = new File(inputFileName);
            inputFile = new Scanner(inputDataFile);
            FileWriter outputDataFile = new FileWriter(outputFileName);
            outputFile = new PrintWriter(outputDataFile);
        } catch (IOException e) {
            System.out.println("There was an error with your output file," +
                    " check the file location.");
            System.exit(1);
        }
    }

    /*************************************************************************/
    /**
     * Create a character array from input file.
     */
    public void generateCharArray() {
        char[] temp;
        while(inputFile.hasNext()) {
            temp = inputFile.nextLine().trim().toCharArray();
            for (char aTemp : temp) {
                charArray.add(aTemp);
                charArraySize++;
            }
        }
    }

    /*************************************************************************/
    /**
     * Add characters from charArray to linked list. Note, characters will be
     * in opposite order from order in charArray. If character already exits
     * in list, character is moved to front of list, and deleted from
     * previous position in the list.
     */
    public void addCharsToList() {
        int index;
        char temp;
        while(charArraySize != 0) {
            temp = charArray.remove(charArraySize - 1);
            index = list.add(temp);
            outputFile.println("You added " + temp + " to the list.");
            System.out.println("You added " + temp + " to the list.");
            if(index == -1) {
                outputFile.println(temp + " was not previously in the list.");
                System.out.println(temp + " was not previously in the list.");
            } else {
                outputFile.println(temp + " was previously at " + index);
                System.out.println(temp + " was previously at " + index);
            }
            charArraySize--;
        }
    }
}
