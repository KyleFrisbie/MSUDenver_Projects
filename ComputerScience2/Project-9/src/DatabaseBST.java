import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyle on 11/20/2014.
 */
public class DatabaseBST extends CarList {

    private Node root;
    private int size = 0;
    private ArrayList<String> modelList = new ArrayList<>();
    private ArrayList<String> colorList = new ArrayList<>();
    private ArrayList<String> yearList = new ArrayList<>();
    private ArrayList<String> licenseList = new ArrayList<>();
    private ArrayList<String> licensePartialList = new ArrayList<>();
    private ArrayList<String> entireDatabase = new ArrayList<>();


    private class Node {
        private String key;
        private CarList value;      // associated value to key
        private Node left, right;   // links to subtrees
        private int N;              // # of nodes in subtree

        public Node(String key, String make, String model, String year, String color, String license, int N) {
            this.key = key;
            this.value = new CarList();
            this.value.addCar(make, model, year, color, license);
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    public CarList get(String key) {
        return get(root, key);
    }

    /**
     * Return value associated with key in subtree rooted at x, return null if
     * key not present in subtree rooted at x.
     *
     * @param x   : Node subtree is rooted at
     * @param key : key to be searched for in subtree
     * @return Value : data associated with key if key is found, null if not found
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
     *
     * @param key : key to be searched for in subtree
     * @param make
     * @param model
     * @param year
     * @param color
     * @param license
     */
//    public void put(String key, Car value) {
//        root = put(root, key, value);
//    }
    public void put(String key, String make, String model, String year, String color, String license) {
        root = put(root, key, make, model, year, color, license);
    }

    /**
     * Change key's value to value if key is in subtree rooted at x. Otherwise,
     * add new node to subtree associating key with value.
     *
     * @param x : Node subtree is rooted at
     * @param key : key to be searched for in subtree
     * @param make
     * @param model
     * @param year
     * @param color
     * @param license
     * @return Node
     */
//    private Node put(Node x, String key, CarList value) {
    private Node put(Node x, String key,  String make, String model, String year, String color, String license) {
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

//    public void outputMake(PrintWriter outputFile, String make) {
//        outputMake(outputFile, root, make);
//    }
//
//    private void outputMake(PrintWriter outputFile, Node x, String key) {
//        if (x == null) {
//            outputFile.println(key + " = " + "0.");
//        }
//        int compare = key.compareTo(x.key);
//        if (compare < 0) {
//            outputMake(outputFile, x.left, key);
//        } else if (compare > 0) {
//            outputMake(outputFile, x.right, key);
//        } else {
//            x.value.outputMake(outputFile);
//        }
//    }

    public ArrayList<String> getMake(String make) {
        return getMake(root, make);
    }

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

    public ArrayList<String> getModel(String model) {
        getModel(root, model);
        return modelList;
    }

    private void getModel(Node x, String model) {
        if (x == null) {
            // do nothing
        } else {
            modelList.addAll(x.value.getModel(model));
            getModel(x.left, model);
            getModel(x.right, model);
        }
    }

//    public void outputColor(PrintWriter outputFile, String color) {
//        outputColor(outputFile, root, color);
//    }
//
//    private void outputColor(PrintWriter outputFile, Node x, String color) {
//        if (x == null) {
//            outputFile.println(color + " = " + "0.");
//        }
//        outputColor(outputFile, x.left, color);
//        outputColor(outputFile, x.right, color);
//        x.value.outputColor(outputFile, color);
//    }

    public ArrayList<String> getColorList(String color) {
        getColor(root, color);
        return colorList;
    }

    private void getColor(Node x, String color) {
        if (x == null) {
            // do nothing
        } else {
            colorList.addAll(x.value.getColor(color));
            getColor(x.left, color);
            getColor(x.right, color);
        }
    }

//    public void outputYear(PrintWriter outputFile, String year) {
//        outputYear(outputFile, root, year);
//    }
//
//    private void outputYear(PrintWriter outputFile, Node x, String year) {
//        if (x == null) {
//            outputFile.println(year + " = " + "0.");
//        }
//        outputYear(outputFile, x.left, year);
//        outputYear(outputFile, x.right, year);
//        x.value.outputYear(outputFile, year);
//    }

    public ArrayList<String> getYearList(String year) {
        getYear(root, year);
        return yearList;
    }

    private void getYear(Node x, String year) {
        if (x == null) {
            // do nothing
        } else {
            yearList.addAll(x.value.getYear(year));
            getYear(x.left, year);
            getYear(x.right, year);
        }
    }

//    public void outputLicense(PrintWriter outputFile, String license) {
//        outputLicense(outputFile, root, license);
//    }
//
//    private void outputLicense(PrintWriter outputFile, Node x, String license) {
//        if (x == null) {
//            outputFile.println(license + " = " + "0.");
//        }
//        outputLicense(outputFile, x.left, license);
//        outputLicense(outputFile, x.right, license);
//        x.value.outputLicense(outputFile, license);
//    }

    public ArrayList<String> getLicenseList(String license) {
        getLicense(root, license);
        return licenseList;
    }

    private void getLicense(Node x, String license) {
        if (x == null) {
            // do nothing
        } else {
            licenseList.addAll(x.value.getFullLicense(license));
            getLicense(x.left, license);
            getLicense(x.right, license);
        }
    }

    public ArrayList<String> getLicenseListPartial(String license) {
        getLicensePartial(root, license);
        return licensePartialList;
    }

    private void getLicensePartial(Node x, String license) {
        if (x == null) {
            // do nothing
        } else {
            licensePartialList.addAll(x.value.getPartialLicense(license));
            getLicensePartial(x.left, license);
            getLicensePartial(x.right, license);
        }
    }

    public ArrayList<String> outputTree() {
        outputTree(root);
        return entireDatabase;
    }

    private void outputTree(Node x) {
        if (x == null) {
            // do nothing
        } else {
            entireDatabase.addAll(x.value.getMake());
            outputTree(x.left);
            outputTree(x.right);
        }
    }

    public void resetArrays() {
        colorList = new ArrayList<>();
        yearList = new ArrayList<>();
        licenseList = new ArrayList<>();
        licensePartialList = new ArrayList<>();
    }

    public void outputCars(PrintWriter outputFile, String searchKey, ArrayList<String> carList) {
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

    public void outputLicense(PrintWriter outputFile, String searchKey, ArrayList<String> carList) {
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
        String outputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\MSU-DENVER\\CS-2\\Project-9\\Output_CarsDB_KyleLFrisbie.txt";
        String inputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\MSU-DENVER\\CS-2\\Project-9\\Input_KyleLFrisbie.txt";
        DatabaseBST database = new DatabaseBST();

        File inputDataFile = new File(inputFileName);
        Scanner inputFile = new Scanner(inputDataFile);
        FileWriter outputDataFile = new FileWriter(outputFileName);
        PrintWriter outputFile = new PrintWriter(outputDataFile);

        while (inputFile.hasNext()) {
            String[] tokens = inputFile.nextLine().split(", ");
            database.put(tokens[0], tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
        }

        database.outputCars(outputFile, "Cars in database", database.outputTree());

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
        database.outputLicense(outputFile, licensePartial, database.licensePartialList);


        outputFile.close();
        System.exit(0);
    }
}
