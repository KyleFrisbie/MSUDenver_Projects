import com.sun.org.apache.xpath.internal.operations.Equals;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by Kyle on 11/20/2014.
 */
public class CarList extends Equals {

    private Node head;
    private int size;

    private class Node {
        String make;
        String model;
        String year;
        String color;
        String license;
        Node next;
    }

    public void addCar(String make, String model, String year, String color, String license) {
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

    public boolean isEmpty() {
        return size == 0;
    }

    public void outputMake(PrintWriter outputFile) {
        for (Node x = head; x != null; x = x.next) {
            outputFile.println(x.make + ", " + x.model + ", " + x.year + ", "
                    + x.color + ", " + x.license);
        }
    }

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

    public void outputColor(PrintWriter outputFile, String color) {
        ArrayList<String> carsColor = new ArrayList<>();
        for (Node x = head; x != null; x = x.next) {
            if (color.equals(x.color)) {
                outputFile.println(x.make + ", " + x.model + ", " + x.year
                        + ", " + x.color + ", " + x.license);
            }
        }
    }

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

    public void outputYear(PrintWriter outputFile, String year) {
        for (Node x = head; x != null; x = x.next) {
            if (year.equals(x.year)) {
                outputFile.println(x.make + ", " + x.model + ", " + x.year
                        + ", " + x.color + ", " + x.license);
            }
        }
    }

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

    public void outputLicense(PrintWriter outputFile, String license) {
        String newLicense = license.replaceAll("[^a-zA-Z0-9]", "");
        for (Node x = head; x != null; x = x.next) {
            if (newLicense.equals(x.license)) {
                outputFile.println(x.make + ", " + x.model + ", " + x.year
                        + ", " + x.color + ", " + x.license);
            }
        }
    }

    public ArrayList<String> getFullLicense(String license) {
        ArrayList<String> carsLicense = new ArrayList<>();
        String newLicense = license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        for (Node x = head; x != null; x = x.next) {
            String licenseAltered = x.license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
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

    public ArrayList<String> getPartialLicense(String license) {
        ArrayList<String> carsLicense = new ArrayList<>();
        String newLicense = license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        for (Node x = head; x != null; x = x.next) {
            String licenseAltered = x.license.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
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

    public static void main(String[] args) throws IOException {
        String outputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\MSU-DENVER\\CS-2\\Project-9\\Output_CarsLL_KyleLFrisbie.txt";
        String inputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\MSU-DENVER\\CS-2\\Project-9\\Input_KyleLFrisbie.txt";
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
