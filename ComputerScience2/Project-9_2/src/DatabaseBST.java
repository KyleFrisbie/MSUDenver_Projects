/**
 * @Author: Kyle L Frisbie
 * @Version: 1
 *
 * Sources:
 * This class was modeled after the binary search tree displayed in the
 * Sedgewick and Wayne's Algorithms 4th Edition. Some methods included in
 * the source class were included but not used. I also added special methods
 * and modifications to the BST in order to implement and handle linked lists
 * of car data to meet the specifications of the assignment. This class takes
 * search queries from the driver and passes them to the linked lists held
 * within each BST node to find the appropriate items. Information from the
 * linked lists are returned and collected within individual array lists to be
 * returned to and handled by the driver class (to be printed to an output
 * file).
 *
 * Limitations:
 * This BST was built specifically for key values of car makes, making it a
 * BST useful to only this type of tree-linked list implementation of cars.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseBST extends CarList {

    private Node root;
    private int size = 0;
    // Array lists to hold returned search values
    private ArrayList<String> modelList;
    private ArrayList<String> colorList;
    private ArrayList<String> yearList;
    private ArrayList<String> licenseList;
    private ArrayList<String> licensePartialList;
    private ArrayList<String> entireDatabase;


    private class Node {
        private String key;         // key is the make of a car
        private CarList value;      // associated value to key
        private Node left, right;   // links to subtrees
        private int N;              // # of nodes in subtree

        public Node(String key, String make, String model, String year,
                    String color, String license, int N) {
            this.key = key;
            this.value = new CarList();
            this.value.addCar(make, model, year, color, license);
            this.N = N;
        }
    }

    /**
     * Get size of subtree
     * @return int size of subtree
     */
    public int size() {
        return size(root);
    }

    /**
     * Get size of subtree
     * @return int size of subtree
     */
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    /**
     * Get node of particular key
     * @param key String value to get
     * @return node matching key
     */
    public CarList get(String key) {
        return get(root, key);
    }

    /**
     * Return value associated with key in subtree rooted at x, return null if
     * key not present in subtree rooted at x.
     *
     * @param x   : Node subtree is rooted at
     * @param key : key to be searched for in subtree
     * @return Value : data associated with key if key is found, null if not
     *          found
     */
    private CarList get(Node x, String key) {
        if (x == null) {
            return null;
        }
        int compare = key.compareTo(x.key);
        if (compare < 0) {
            return get(x.left, key);
        } else if (compare > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }
    }

    /**
     * Add car to BST node
     * @param key : key to be searched for in subtree
     * @param make String car make
     * @param model String car model
     * @param year String car year
     * @param color String car color
     * @param license String car license plate
     */
    public void put(String key, String make, String model, String year,
                    String color, String license) {
        root = put(root, key, make, model, year, color, license);
    }

    /**
     * Change key's value to value if key is in subtree rooted at x. Otherwise,
     * add new node to subtree associating key with value.
     * @param x : Node subtree is rooted at
     * @param key : key to be searched for in subtree
     * @param make String car make
     * @param model String car model
     * @param year String car year
     * @param color String car color
     * @param license String car license plate
     * @return Node
     */
    private Node put(Node x, String key,  String make, String model,
                     String year, String color, String license) {
        if (x == null) {
            size++;
            return new Node(key, make, model, year, color, license, 1);
        }
        int compare = key.compareTo(x.key);
        if (compare < 0) {
            x.left = put(x.left, key, make, model, year, color, license);
        } else if (compare > 0) {
            x.right = put(x.right, key, make, model, year, color, license);
        } else {
            x.value.addCar(make, model, year, color, license);
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Search BST for key "make"
     * @param make String make of car (also the key value)
     * @return ArrayList of cars of particular make
     */
    public ArrayList<String> getMake(String make) {
        return getMake(root, make);
    }

    /**
     * Private helper method to search BST for make
     * @param x Node to look at
     * @param key String to compare to
     * @return ArrayList of cars of particular make
     */
    private ArrayList<String> getMake(Node x, String key) {
        ArrayList<String> makeList = new ArrayList<>();
        boolean makeFound = false;
        while (!makeFound) {
            if (x == null) {
                makeList.add(key);
                break;
            }
            int compare = key.compareTo(x.key);
            if (compare > 0) {
                x = x.right;
            } else if (compare < 0) {
                x = x.left;
            } else {
                makeList = x.value.getMake();
                makeFound = true;
            }
        }
        return makeList;
    }

    /**
     * Search for cars of a particular model
     * @param model String model to search for
     * @return ArrayList of cars of a particular model
     */
    public ArrayList<String> getModel(String model) {
        modelList = new ArrayList<>();
        getModel(root, model);
        return modelList;
    }

    /**
     * Private helper method to search for cars of a particular model
     * @param x Node to look at
     * @param model String value to compare to
     */
    private void getModel(Node x, String model) {
        if (x == null) {
            // do nothing
        } else {
            modelList.addAll(x.value.getModel(model));
            getModel(x.left, model);
            getModel(x.right, model);
        }
    }

    /**
     * Search for cars of a particular color
     * @param color String color to search for
     * @return ArrayList of cars of a particular color
     */
    public ArrayList<String> getColorList(String color) {
        colorList = new ArrayList<>();
        getColor(root, color);
        return colorList;
    }

    /**
     * Private helper method to search for cars of a particular color
     * @param x Node to look at
     * @param color String value to compare to
     */
    private void getColor(Node x, String color) {
        if (x == null) {
            // do nothing
        } else {
            colorList.addAll(x.value.getColor(color));
            getColor(x.left, color);
            getColor(x.right, color);
        }
    }

    /**
     * Search for cars of a particular year
     * @param year String year to search for
     * @return ArrayList of cars of a particular year
     */
    public ArrayList<String> getYearList(String year) {
        yearList = new ArrayList<>();
        getYear(root, year);
        return yearList;
    }

    /**
     * Private helper method to search for cars of a particular year
     * @param x Node to look at
     * @param year String value to compare to
     */
    private void getYear(Node x, String year) {
        if (x == null) {
            // do nothing
        } else {
            yearList.addAll(x.value.getYear(year));
            getYear(x.left, year);
            getYear(x.right, year);
        }
    }

    /**
     * Search for cars of a particular license
     * @param license String license to search for
     * @return ArrayList of cars of a particular license
     */
    public ArrayList<String> getLicenseList(String license) {
        licenseList = new ArrayList<>();
        getLicense(root, license);
        return licenseList;
    }

    /**
     * Private helper method to search for cars of a particular license
     * @param x Node to look at
     * @param license String value to compare to
     */
    private void getLicense(Node x, String license) {
        if (x == null) {
            // do nothing
        } else {
            licenseList.addAll(x.value.getFullLicense(license));
            getLicense(x.left, license);
            getLicense(x.right, license);
        }
    }

    /**
     * Search for cars of a particular partial license
     * @param license String partial license to search for
     * @return ArrayList of cars of a particular partial license
     */
    public ArrayList<String> getLicenseListPartial(String license) {
        licensePartialList = new ArrayList<>();
        getLicensePartial(root, license);
        return licensePartialList;
    }

    /**
     * Private helper method to search for cars of a particular partial license
     * @param x Node to look at
     * @param license String value to compare to
     */
    private void getLicensePartial(Node x, String license) {
        if (x == null) {
            // do nothing
        } else {
            licensePartialList.addAll(x.value.getPartialLicense(license));
            getLicensePartial(x.left, license);
            getLicensePartial(x.right, license);
        }
    }

    /**
     * Output entire database
     * @return ArrayList of all cars in tree
     */
    public ArrayList<String> outputTree() {
        entireDatabase = new ArrayList<>();
        outputTree(root);
        return entireDatabase;
    }

    /**
     * Private helper method to output database
     * @param x Node to branch from
     */
    private void outputTree(Node x) {
        if (x == null) {
            // do nothing
        } else {
            entireDatabase.addAll(x.value.getMake());
            outputTree(x.left);
            outputTree(x.right);
        }
    }

    /**
     * Reset all ArrayLists for new values
     */
    public void resetArrays() {
        colorList = new ArrayList<>();
        yearList = new ArrayList<>();
        licenseList = new ArrayList<>();
        licensePartialList = new ArrayList<>();
    }

    /**
     * Output cars returned from particular search query
     * @param outputFile file to output to
     * @param searchKey key searched for
     * @param carList list of cars returned from search key
     */
    public void outputCars(PrintWriter outputFile,
                           String searchKey, ArrayList<String> carList) {
        if (carList.size() == 1) {
            outputFile.println(searchKey + " = 0");
        } else {
            outputFile.println(searchKey + " = " + carList.size()/5);
            for (int i = 0; i < carList.size(); i += 5) {
                outputFile.println(carList.get(i) + ", " + carList.get(i + 1)
                        + ", " + carList.get(i + 2) + ", " + carList.get(i + 3)
                        + ", " + carList.get(i + 4));
            }
        }
    }

    /**
     * Special output method for outputting cars from particular license
     *  search query
     * @param outputFile file to output to
     * @param searchKey key searched for
     * @param carList list of cars returned from search key
     */
    public void outputLicense(PrintWriter outputFile, String searchKey,
                              ArrayList<String> carList) {
        outputFile.println("License search: " + searchKey);
        if (carList.size() < 5) {
            outputFile.println("Not Found.");
        } else {
            outputFile.println("Found: " + + carList.size()/5);
            for (int i = 0; i < carList.size(); i += 5) {
                outputFile.println(carList.get(i) + ", " + carList.get(i + 1)
                        + ", " + carList.get(i + 2) + ", " + carList.get(i + 3)
                        + ", " + carList.get(i + 4));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String outputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\" +
                "GitRepo\\MSU-DENVER\\CS-2\\Project-9\\" +
                "Output_CarsDB_KyleLFrisbie.txt";
        String inputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\" +
                "GitRepo\\MSU-DENVER\\CS-2\\Project-9\\Input_KyleLFrisbie.txt";
        DatabaseBST database = new DatabaseBST();

        File inputDataFile = new File(inputFileName);
        Scanner inputFile = new Scanner(inputDataFile);
        FileWriter outputDataFile = new FileWriter(outputFileName);
        PrintWriter outputFile = new PrintWriter(outputDataFile);

        while (inputFile.hasNext()) {
            String[] tokens = inputFile.nextLine().split(", ");
            database.put(tokens[0], tokens[0], tokens[1],
                    tokens[2], tokens[3], tokens[4]);
        }

        database.outputCars(outputFile, "Cars in database",
                database.outputTree());

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter make to search for: ");
        String make = keyboard.nextLine();

        outputFile.println();
        outputFile.println("Search for make: ");
        database.outputCars(outputFile, make, database.getMake(make));

        System.out.print("Enter color to search for: ");
        String color = keyboard.nextLine();

        outputFile.println();
        outputFile.println("Search for color:");
        database.getColorList(color);
        database.outputCars(outputFile, color, database.colorList);

        System.out.print("Enter year to search for: ");
        String year = keyboard.nextLine();

        outputFile.println();
        outputFile.println("Search for year:");
        database.getYearList(year);
        database.outputCars(outputFile, year, database.yearList);

        System.out.print("Enter licence plate to search for: ");
        String license = keyboard.nextLine();

        outputFile.println();
        outputFile.println("Search for license:");
        database.getLicenseList(license);
        database.outputLicense(outputFile, license, database.licenseList);

        System.out.print("Enter licence plate partial to search for: ");
        String licensePartial = keyboard.nextLine();

        outputFile.println();
        outputFile.println("Search for license partial:");
        database.getLicenseListPartial(licensePartial);
        database.outputLicense(outputFile, licensePartial,
                database.licensePartialList);


        outputFile.close();
        System.exit(0);
    }
}
