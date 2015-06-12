/**
 * @Author: Kyle L Frisbie
 * @Version: 1
 *
 * Sources:
 * This class was modeled after the linked list "stack" displayed in the
 * Sedgewick and Wayne's Algorithms 4th Edition. Some methods included in
 * the source class were included but not used. I also added special methods
 * and modifications to the linked list including a special node class that
 * is the equivalent of a special "Car" data type, because the node is private
 * to this class, all car data is passed back to the calling classes in the
 * form of an array list, in order to protect the integrity of the nodes and
 * their information.
 *
 * Limitations:
 * As mentioned above, each node is private, and therefore the car's
 * information must be extracted in this class and added to an ArrayList
 * in order to be passed to another class who requires the information.
 */

import com.sun.org.apache.xpath.internal.operations.Equals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CarList extends Equals {

    private Node head;
    private int size;

    /**
     * special Node class to hold car info
     */
    private class Node {
        String make;
        String model;
        String year;
        String color;
        String license;
        Node next;
    }

    /**
     * Add car to linked list
     * @param make String car make
     * @param model String car model
     * @param year String car year
     * @param color String car color
     * @param license String car license plate
     */
    public void addCar(String make, String model, String year,
                       String color, String license) {
        Node temp = new Node();
        if (!isEmpty()) {
            temp = head;
            head = new Node();
            head.next = temp;
            head.make = make;
            head.model = model;
            head.year = year;
            head.color = color;
            head.license = license;
        } else {
            head = temp;
            head.make = make;
            head.model = model;
            head.year = year;
            head.color = color;
            head.license = license;
        }
        size++;
    }

    /**
     * Check to see if list is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Used in local main to test functionality of linked list, outputs every
     * car in linked list
     * @param outputFile PrintWriter file to output to
     */
    public void outputMake(PrintWriter outputFile) {
        for (Node x = head; x != null; x = x.next) {
            outputFile.println(x.make + ", " + x.model + ", " + x.year + ", "
                    + x.color + ", " + x.license);
        }
    }

    /**
     * Get the cars of a certain make
     * @return ArrayList of cars of certain make
     */
    public ArrayList<String> getMake() {
        ArrayList<String> carsMake = new ArrayList<>();
        for (Node x = head; x != null; x = x.next) {
            carsMake.add(x.make);
            carsMake.add(x.model);
            carsMake.add(x.year);
            carsMake.add(x.color);
            carsMake.add(x.license);
        }
        return carsMake;
    }

    /**
     * Search list for cars of a certain model
     * @param model String model to search for
     * @return ArrayList of cars of certain model
     */
    public ArrayList<String> getModel(String model) {
        ArrayList<String> carsModel = new ArrayList<>();
        for (Node x = head; x != null; x = x.next) {
            if (model.equals(x.model)) {
                carsModel.add(x.make);
                carsModel.add(x.model);
                carsModel.add(x.year);
                carsModel.add(x.color);
                carsModel.add(x.license);
            }
        }
        return carsModel;
    }

    /**
     * Output cars of a certain color, used in local main method
     * @param outputFile PrintWriter file to output to
     * @param color String color to search for
     */
    public void outputColor(PrintWriter outputFile, String color) {
        ArrayList<String> carsColor = new ArrayList<>();
        for (Node x = head; x != null; x = x.next) {
            if (color.equals(x.color)) {
                outputFile.println(x.make + ", " + x.model + ", " + x.year
                        + ", " + x.color + ", " + x.license);
            }
        }
    }

    /**
     * Search list for cars of a certain color
     * @param color String color to search for
     * @return ArrayList of cars of certain color
     */
    public ArrayList<String> getColor(String color) {
        ArrayList<String> carsColor = new ArrayList<>();
        for (Node x = head; x != null; x = x.next) {
            if (color.equals(x.color)) {
                carsColor.add(x.make);
                carsColor.add(x.model);
                carsColor.add(x.year);
                carsColor.add(x.color);
                carsColor.add(x.license);
            }
        }
        return carsColor;
    }

    /**
     * Output cars of a certain year, used in local main method
     * @param outputFile PrintWriter file to output to
     * @param year String year to search for
     */
    public void outputYear(PrintWriter outputFile, String year) {
        for (Node x = head; x != null; x = x.next) {
            if (year.equals(x.year)) {
                outputFile.println(x.make + ", " + x.model + ", " + x.year
                        + ", " + x.color + ", " + x.license);
            }
        }
    }

    /**
     * Search list of cars for certain year
     * @param year String year to search for
     * @return ArrayList of cars of certain year
     */
    public ArrayList<String> getYear(String year) {
        ArrayList<String> carsColor = new ArrayList<>();
        for (Node x = head; x != null; x = x.next) {
            if (year.equals(x.year)) {
                carsColor.add(x.make);
                carsColor.add(x.model);
                carsColor.add(x.year);
                carsColor.add(x.color);
                carsColor.add(x.license);
            }
        }
        return carsColor;
    }

    /**
     * Output cars of a certain license, used in local main method
     * @param outputFile PrintWriter file to output to
     * @param license String license to search for
     */
    public void outputLicense(PrintWriter outputFile, String license) {
        String newLicense = license.replaceAll("[^a-zA-Z0-9]", "");
        for (Node x = head; x != null; x = x.next) {
            if (newLicense.equals(x.license)) {
                outputFile.println(x.make + ", " + x.model + ", " + x.year
                        + ", " + x.color + ", " + x.license);
            }
        }
    }

    /**
     * Search list of cars for certain license
     * @param license String license to search for
     * @return ArrayList of cars of certain license
     */
    public ArrayList<String> getFullLicense(String license) {
        ArrayList<String> carsLicense = new ArrayList<>();
        String newLicense =
                license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        for (Node x = head; x != null; x = x.next) {
            String licenseAltered =
                    x.license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            if (newLicense.equals(licenseAltered)) {
                carsLicense.add(x.make);
                carsLicense.add(x.model);
                carsLicense.add(x.year);
                carsLicense.add(x.color);
                carsLicense.add(x.license);
            }
        }
        return carsLicense;
    }

    /**
     * Search list of cars for certain partial license
     * @param license String partial license to search for
     * @return ArrayList of cars of certain partial license
     */
    public ArrayList<String> getPartialLicense(String license) {
        ArrayList<String> carsLicense = new ArrayList<>();
        String newLicense =
                license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        for (Node x = head; x != null; x = x.next) {
            String licenseAltered =
                    x.license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            if (licenseAltered.contains(newLicense)) {
                carsLicense.add(x.make);
                carsLicense.add(x.model);
                carsLicense.add(x.year);
                carsLicense.add(x.color);
                carsLicense.add(x.license);
            }
        }
        return carsLicense;
    }

    /**
     * Main method for testing
     * @param args n/a
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String outputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\" +
                "GitRepo\\MSU-DENVER\\CS-2\\Project-9\\" +
                "Output_CarsLL_KyleLFrisbie.txt";
        String inputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\" +
                "GitRepo\\MSU-DENVER\\CS-2\\Project-9\\Input_KyleLFrisbie.txt";
        CarList cars = new CarList();

        File inputDataFile = new File(inputFileName);
        Scanner inputFile = new Scanner(inputDataFile);
        FileWriter outputDataFile = new FileWriter(outputFileName);
        PrintWriter outputFile = new PrintWriter(outputDataFile);

        while (inputFile.hasNext()) {
            String[] tokens = inputFile.nextLine().split(", ");
            cars.addCar(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
        }
        cars.outputMake(outputFile);

        outputFile.close();
        System.exit(0);
    }
}
