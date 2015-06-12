import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Kyle on 12/6/2014.
 * @author Kyle L Frisbie
 * @version 1
 *
 * Driver.java:
 * Scans an input file of pet values, creates an open addressing hash table and
 * linked list hash table of pet objects from input file. Prompts user to
 * search for specific key value, to see if they are in each hash table, a
 * boolean result is returned from search() and output to console to inform
 * user if key was found. Driver also prompts user via console to enter a key
 * to delete from the hash tables, a boolean is returned and printed to console
 * to inform user of deletion success. This driver simultaneously builds each
 * type of hash table from the input file, and does search and delete.
 *
 * Limitations:
 * Assumes proper user input for key value to search for and delete. Does
 * search and delete strictly off of key values (user must know what key is
 * for particular item in order to search for/delete it). Only adds items to
 * hash tables based on information in input file.
 */
public class Driver {
    private String inputFileName = "Project10_Input_KyleLFrisbie.txt";
    private String outputFileNameLL =
            "Project10_OutputLinkedLTable_KyleLFrisbie.txt";
    private String outputFileNameOA =
            "Project10_OutputOpenATable_KyleLFrisbie.txt";
    private Scanner inputFile;
    private PrintWriter outputFileLL;
    private PrintWriter outputFileOA;
    private int tableSize;
//    private Pet[] petsOpen;
//    private LinkedList<Pet>[] petList;

    public Driver() {
        setupFiles();
    }

    /**
     * initialize input/output files for building hash tables and printing
     * results.
     */
    public void setupFiles() {
        File inputDataFile = new File(inputFileName);
        try {
            inputFile = new Scanner(inputDataFile);
            FileWriter outputDataFileOA = new FileWriter(outputFileNameOA);
            FileWriter outputDataFileLL = new FileWriter(outputFileNameLL);
            outputFileOA = new PrintWriter(outputDataFileOA);
            outputFileLL = new PrintWriter(outputDataFileLL);
            outputFileOA.println("Output from open addressing hash table:");
            outputFileOA.println();
            outputFileLL.println("Output from linked list hash table:");
            outputFileLL.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        HashTableOpenAddress hashOpen = new HashTableOpenAddress();
        HashTableList hashList = new HashTableList();

        // build hash tables from input file
        while (driver.inputFile.hasNext()) {
            String[] tokens = driver.inputFile.nextLine().split(", ");
            hashOpen.createHashTable(tokens);
            hashList.createHashTable(tokens);
        }

        // print original hash tables
        hashOpen.printTable(driver.outputFileOA);
        hashList.printTable(driver.outputFileLL);

        int key;
        Scanner keyboard;

        // prompt user to search for particular key
        do {
            keyboard = new Scanner(System.in);
            System.out.print("Enter the key you would like to search " +
                    "for (enter -1 to exit): ");
            key = keyboard.nextInt();
            if (key != -1) {
                boolean found;
                found = hashList.search(key);
                System.out.println(key + ": found in hash list: " + found);

                found = hashOpen.search(key);
                System.out.println(key + ": found in hash open: " + found);
            }
        } while (key != -1);

        // prompt user to delete item with particular key
        do {
            System.out.print("Enter the key you would like to delete: " +
                    "(enter -1 to exit): ");
            key = keyboard.nextInt();
            if (key != -1) {
                boolean delete;
                driver.outputFileOA.println();
                delete = hashOpen.delete(key, driver.outputFileOA);
                System.out.println("Delete success open: " + delete);
                driver.outputFileOA.println("Delete key: " + key);
                driver.outputFileOA.println("Delete success: " + delete);
                hashOpen.printTable(driver.outputFileOA);

                driver.outputFileLL.println();
                delete = hashList.delete(key);
                System.out.println("Delete success list: " + delete);
                driver.outputFileLL.println("Delete key: " + key);
                driver.outputFileLL.println("Delete success: " + delete);
                hashList.printTable(driver.outputFileLL);
            }
        } while (key != -1);

        driver.outputFileOA.close();
        driver.outputFileLL.close();
        System.exit(0);
    }
}
