import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyle on 12/1/2014.
 * @author Kyle L Frisbie
 * @version 1
 *
 * HashTableOpenAddress.java
 * Open address hash table of type pet, to hold pet objects. Allows for outside
 * client to add items to table, search for items in table, and remove items
 * from table.
 */

public class HashTableOpenAddress {
    private String inputFileName = "Project10_Input_KyleLFrisbie.txt";
    private String outputFileName = "Project10_OutputOpen_KyleLFrisbie.txt";
    private Scanner inputFile;
    private PrintWriter outputFile;
    private int tableSize;
    private Pet[] pets;

    /**
     * Create hash table of size n
     * @param size size of hash table
     */
    public HashTableOpenAddress(int size) {
        tableSize = size;
        pets = new Pet[tableSize];
    }

    /**
     * Create hash table of size 10 (default)
     */
    public HashTableOpenAddress() {
        tableSize = 10;
        pets = new Pet[tableSize];
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
     * reHash table based on item deleted
     * @param pets_reHash Array list of items to rehash
     */
    public void reHash(ArrayList<Pet> pets_reHash) {
        for (int i = 0; i < pets_reHash.size(); i++) {
            setHash(pets_reHash.get(i));
        }
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
        if (pets[i] == null) {
            pets[i] = pet;
        } else {
            if (i + 1 < tableSize) {
                placeInTable(pet, ++i);
            } else {
                placeInTable(pet, 0);
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
        while (pets[hash] != null) {
            if (pets[hash].getKey() == key) {
                return true;
            }
            hash++;
        }
        return false;
    }

    /**
     * Delete item with int key
     * @param key int value to delete
     * @param outputFile file to print rehash values to
     * @return boolean, true if deleted
     */
    public boolean delete(int key, PrintWriter outputFile) {
        int hash = setHash(key);
        int position;
        while (pets[hash] != null) {
            if (hash < tableSize) {
                if (pets[hash].getKey() != key) {
                    hash++;
                } else {
                    pets[hash] = null;
                    position = hash;
                    if (hash + 1 < tableSize) {
                        hash++;
                    } else {
                        hash = 0;
                    }
                    ArrayList<Pet> pet_reHash = new ArrayList<Pet>();
                    while (pets[hash] != null) {
                        pet_reHash.add(pets[hash]);
                        if (hash + 1 < tableSize) {
                            hash++;
                        } else {
                            hash = 0;
                        }
                    }
                    for(int i = 1; i <= pet_reHash.size(); i++) {
                        pets[(position + i) % tableSize] = null;
                    }
                    outputFile.println("Rehashed "
                            + pet_reHash.size() + " values: ");
                    reHash(pet_reHash);
                    return true;
                }
            } else {
                hash = 0;
            }
        }
        return false;
    }

    /**
     * Print entire table (for use in local main() testing)
     */
    public void printTable() {
        outputFile.println("Index\tName\t\t\tKey\t\tHash");
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] != null) {
                outputFile.println(i + "\t" + pets[i].getName() + "\t\t\t"
                        + pets[i].getKey() + "\t\t" + pets[i].getHash());
            } else {
                outputFile.println(i + "\tNull\t\t\tNull\t\tNull");
            }
        }
    }

    /**
     * Print entire table
     * @param outputFile PrintWriter, file to print to
     */
    public void printTable(PrintWriter outputFile) {
        outputFile.println("Index\tName\t\t\tKey\t\tHash");
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] != null) {
                outputFile.println(i + "\t" + pets[i].getName() + "\t\t\t"
                        + pets[i].getKey() + "\t\t" + pets[i].getHash());
            } else {
                outputFile.println(i + "\tNull\t\t\tNull\t\tNull");
            }
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
//
//    public static void main(String[] args) {
//        HashTableOpenAddress driver = new HashTableOpenAddress();
//        driver.setupFiles();
//        driver.createHashTable(driver.inputFile);
//        driver.printTable();
//
//        driver.outputFile.close();
//        System.exit(0);
//    }
}
