import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Kyle on 12/1/2014.
 * @author Kyle L Frisbie
 * @version 1
 *
 * HashTableList.java
 * Linked list hash table of type pet, to hold pet objects. Allows for outside
 * client to add items to table, search for items in table, and remove items
 * from table.
 */

public class HashTableList {
    private String inputFileName = "Project10_Input_KyleLFrisbie.txt";
    private String outputFileName = "Project10_OutputList_KyleLFrisbie.txt";
    private Scanner inputFile;
    private PrintWriter outputFile;
    private int tableSize;
    private LinkedList[] petList;

    /**
     * Create hash table of size n
     * @param size size of hash table
     */
    public HashTableList(int size) {
        tableSize = size;
        petList = new LinkedList[tableSize];
    }

    /**
     * Create hash table of size 10 (default)
     */
    public HashTableList() {
        tableSize = 10;
        petList = new LinkedList[tableSize];
    }

    /**
     * Generate hash table values from input file (method for testing from
     * local main)
     * @param inputFile Scanner, file to read initial pet data from
     */
    public void createHashTable(Scanner inputFile) {
        while (inputFile.hasNext()) {
            Pet pet = new Pet();
            String[] tokens = inputFile.nextLine().split(", ");
            pet.setName(tokens[0]);
            pet.setKey(Integer.parseInt(tokens[1]));
            setHash(pet);
        }
    }

    /**
     * Generate hash table values from String[] pet information
     * @param tokens String[] pet information
     */
    public void createHashTable(String[] tokens) {
        Pet pet = new Pet();
        pet.setName(tokens[0]);
        pet.setKey(Integer.parseInt(tokens[1]));
        setHash(pet);
    }

    /**
     * Generate hash value from key
     * @param pet Pet to hash
     */
    public void setHash(Pet pet) {
        int hash = pet.getKey() % 10;
        pet.setHash(hash);
        placeInTable(pet, hash);
    }

    /**
     * Generate hash value from key
     * @param key int key to hash
     * @return int hash value
     */
    public int setHash(int key) {
        return key % 10;
    }

    /**
     * Place pet in table based on hash value
     * @param pet Pet to place
     * @param i int hash value
     */
    public void placeInTable(Pet pet, int i) {
        if (petList[i] == null) {
            petList[i] = new LinkedList();
            petList[i].add(pet);
        } else {
            petList[i].add(pet);
        }
    }

    /**
     * Print entire table (for use in local main() testing)
     */
    public void printTable() {
        outputFile.println("Index\tName\t\t\tKey\t\tHash");
        for (int i = 0; i < petList.length; i++) {
            if (petList[i] != null) {
                do {
                    Pet pet = petList[i].remove();
                    outputFile.println(i + "\t" + pet.getName() + "\t\t\t"
                            + pet.getKey() + "\t\t" + pet.getHash());
                } while (!petList[i].isEmpty());
            } else {
                outputFile.println(i + "\tNull\t\t\tNull\t\tNull");
            }
        }
    }

//    public void printTable(PrintWriter outputFile) {
//        outputFile.println("Index\tName\t\t\tKey\t\tHash");
//        for (int i = 0; i < petList.length; i++) {
//            if (petList[i] != null) {
//                do {
//                    Pet pet = petList[i].remove();
//                    outputFile.println(i + "\t" + pet.getName() + "\t\t\t"
//                          + pet.getKey() + "\t\t" + pet.getHash());
//                } while (!petList[i].isEmpty());
//            } else {
//                outputFile.println(i + "\tNull\t\t\tNull\t\tNull");
//            }
//        }
//    }

    /**
     * Print entire table
     * @param outputFile PrintWriter, file to print to
     */
    public void printTable(PrintWriter outputFile) {
        outputFile.println("Index\tName\t\t\tKey\t\tHash");
        for (int i = 0; i < petList.length; i++) {
            if (petList[i] != null) {
                outputFile.print(i + " ");
                petList[i].printList(outputFile);
            } else {
                outputFile.println(i + "\tNull\t\t\tNull\t\tNull");
            }
        }
    }

    /**
     * Search for int key value
     * @param key int value to search for
     * @return boolean, true if found
     */
    public boolean search(int key) {
        int hash = setHash(key);
        if (petList[hash] != null) {
            return petList[hash].search(key);
        } else {
            return false;
        }
    }

    /**
     * Delete item with int key
     * @param key int value to delete
     * @return boolean, true if deleted
     */
    public boolean delete(int key) {
        int hash = setHash(key);
        if (petList[hash] != null) {
            boolean delete = petList[hash].delete(key);
            if (petList[hash].isEmpty()) {
                petList[hash] = null;
            }
            return delete;
        } else {
            return false;
        }
    }

    /**
     * Setup input/output files, for use with local main() testing
     */
    public void setupFiles() {
        File inputDataFile = new File(inputFileName);
        try {
            inputFile = new Scanner(inputDataFile);
            FileWriter outputDataFile = new FileWriter(outputFileName);
            outputFile = new PrintWriter(outputDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        HashTableList driver = new HashTableList();
//        driver.setupFiles();
//        driver.createHashTable(driver.inputFile);
//        driver.printTable();
//
//        driver.outputFile.close();
//        System.exit(0);
//    }
}
