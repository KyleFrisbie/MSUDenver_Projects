/**
 * @Author: Kyle L Frisbie
 * @Version: 2
 *
 * CarDBDriver.java
 *
 * This class takes user input using JOptionPane dialog boxes including: I/O
 * file locations, database search queries, and an option to search again
 * without having to start the program again. This class is strictly for taking
 * user input, establishing the basic I/O files required, and passing search
 * queries from the user to the DatabaseBST.
 *
 * Limitations:
 * Assumes proper I/O file location designation as assigned by user, does not
 * fail gracefully if an improper file location is selected but crashes.
 *
 * Modeled specifically to take the car information returned from the
 * DatabaseBST as an ArrayList of car information, including "make, model, year
 * color, and license" in this order being passed from the DatabaseBST.
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CarDBDriver {
    private DatabaseBST database;   // Binary search tree of car model nodes
    // list of car database (used if user opts to print database)
    private ArrayList<String> dataPrint = new ArrayList<>();
    // list of cars by make (if user opts to search for specific car make)
    private ArrayList<String> make = new ArrayList<>();
    // list of cars by model (if user opts to search for specific car model)
    private ArrayList<String> model = new ArrayList<>();
    // list of cars by year (if user opts to search for specific car year)
    private ArrayList<String> year = new ArrayList<>();
    // list of cars by color (if user opts to search for specific car color)
    private ArrayList<String> color = new ArrayList<>();

//   String inputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\"
//         + "MSU-DENVER\\CS-2\\Project-9_2\\Input_KyleLFrisbie.txt";
//   String outputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\"
//         + "MSU-DENVER\\CS-2\\Project-9_2\\Output_CarsMain_KyleLFrisbie.txt";
    PrintWriter outputFile;

    /**
     * Driver to create CarDBDriver object, initializes input/output files,
     * creates database, populates menu options for JOptionPane from database.
     * Catches IOException.
     */
    public CarDBDriver () {
        String[] ioSelection = new String[2];
        ioSelection = displayFileInputWindow();
//        Scanner keyboard = new Scanner(System.in);
//        String inputFileName =
//              JOptionPane.showInputDialog(
//                  "Enter the location of your input file: ");
//        String inputFileName = keyboard.nextLine().trim();
        String inputFileName = ioSelection[0];
        File inputDataFile = new File(inputFileName);
        database = new DatabaseBST();

        // options for database printing combo box
        dataPrint.add("No");
        dataPrint.add("Yes");

        try {
            Scanner inputFile = new Scanner(inputDataFile);
//            String outputFileName =
//                  JOptionPane.showInputDialog(
//                      "Enter the location of your output file: ");
//            String outputFileName = keyboard.nextLine().trim();
            String outputFileName = ioSelection[1];
            FileWriter outputDataFile = new FileWriter(outputFileName);
            outputFile = new PrintWriter(outputDataFile, false);

            // default option in combo boxes
            make.add("---");
            model.add("---");
            year.add("---");
            color.add("---");

            // fill arrays for combo boxes with values populated in array
            //  (make, model, year, color)
            while (inputFile.hasNext()) {
                String[] tokens = inputFile.nextLine().split(", ");
                database.put(tokens[0], tokens[0], tokens[1], tokens[2],
                        tokens[3], tokens[4]);

                if (!make.contains(tokens[0])) {
                    make.add(tokens[0]);
                }
                if (!model.contains(tokens[1])) {
                    model.add(tokens[1]);
                }
                if (!year.contains(tokens[2])) {
                    year.add(tokens[2]);
                }
                if (!color.contains(tokens[3])) {
                    color.add(tokens[3]);
                }
            }

            // alphabetically/numerically sort arrays for readability
            Collections.sort(make);
            Collections.sort(model);
            Collections.sort(year);
            Collections.sort(color);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display JOptionPane to get input and output file locations from user
     * @return String[] input and output file locations
     */
    public String[] displayFileInputWindow() {
        String[] ioFiles = new String[2];
        JPanel fileInputWindow = new JPanel(new GridLayout(0, 1));

        JTextField inputFile = new JTextField("Input_KyleLFrisbie.txt");
        JTextField outputFile = new JTextField("Output_KyleLFrisbie.txt");

        fileInputWindow.add(new JLabel("Assign location of I/O " +
                "(or leave at default values)"));
        fileInputWindow.add(new JLabel());

        fileInputWindow.add(new JLabel("Input file location:"));
        fileInputWindow.add(inputFile);

        fileInputWindow.add(new JLabel("Output file location:"));
        fileInputWindow.add(outputFile);

        ioFiles[0] = inputFile.getText();
        ioFiles[1] = outputFile.getText();

        JOptionPane.showConfirmDialog(null, fileInputWindow, "File Locations",
                JOptionPane.PLAIN_MESSAGE);

        return ioFiles;
    }

    /**
     * Display comprehensive search window for user to enter multiple values
     * to search for
     */
    public void displaySearchWindow() {
        JPanel searchWindow = new JPanel(new GridLayout(0, 1));

        JComboBox dataPrintCombo = new JComboBox(dataPrint.toArray());
        JComboBox makeCombo = new JComboBox(make.toArray());
        JComboBox modelCombo = new JComboBox(model.toArray());
        JComboBox yearCombo = new JComboBox(year.toArray());
        JComboBox colorCombo = new JComboBox(color.toArray());
        JTextField fullLicenseBox = new JTextField("---");
        JTextField partialLicenseBox = new JTextField("---");

        searchWindow.add(new JLabel("Leave blank (---) if field unknown"));
        searchWindow.add(new JLabel());

        searchWindow.add(new JLabel("Print Database?"));
        searchWindow.add(dataPrintCombo);

        searchWindow.add(new JLabel("Search Make:"));
        searchWindow.add(makeCombo);

        searchWindow.add(new JLabel("Search Model:"));
        searchWindow.add(modelCombo);

        searchWindow.add(new JLabel("Search Year:"));
        searchWindow.add(yearCombo);

        searchWindow.add(new JLabel("Search Color:"));
        searchWindow.add(colorCombo);

        searchWindow.add(new JLabel("Search Full License:"));
        searchWindow.add(fullLicenseBox);

        searchWindow.add(new JLabel("Search Partial License:"));
        searchWindow.add(partialLicenseBox);

        JOptionPane.showConfirmDialog(null, searchWindow,
                "Search Car Database",JOptionPane.PLAIN_MESSAGE);

        passSearchQueries(
                dataPrintCombo.getSelectedItem().toString(),
                makeCombo.getSelectedItem().toString(),
                modelCombo.getSelectedItem().toString(),
                colorCombo.getSelectedItem().toString(),
                yearCombo.getSelectedItem().toString(),
                fullLicenseBox.getText(),
                partialLicenseBox.getText());
    }

    /**
     * Window to enable user to run another search
     * @return boolean true if search is complete
     */
    public boolean displaySearchComplete() {
        String[] searchOption = new String[] {"Yes", "No"};
        JPanel searchComplete = new JPanel(new GridLayout(0, 1));

        JComboBox searchAgainCombo = new JComboBox(searchOption);

        searchComplete.add(new JLabel("Select yes if you have another " +
                "search to perform."));
        searchComplete.add(new JLabel());

        searchComplete.add(new JLabel("Search again?"));
        searchComplete.add(searchAgainCombo);

        JOptionPane.showConfirmDialog(null, searchComplete,
                "Search Again", JOptionPane.PLAIN_MESSAGE);

        String selection = searchAgainCombo.getSelectedItem().toString();

        if (selection == "Yes") {
            // search is not complete
            return false;
        } else {
            // search complete
            return true;
        }
    }

    /**
     * Take search queries from search window and pass to database for search
     * @param dataPrint String database print selection
     * @param make String make to search for
     * @param model String model to search for
     * @param color String color to search for
     * @param year String year to search for
     * @param licenseFull String full license to search for
     * @param licensePartial String partial license to search for
     */
    private void passSearchQueries(
            String dataPrint, String make, String model, String color,
            String year, String licenseFull, String licensePartial) {
        if (dataPrint.equals("Yes")) {
            outputFile.println("Entire database print:");
            outputCars("Cars present", database.outputTree());
        }

        if (!make.equals("---")) {
            outputFile.println("Search for make: ");
            outputCars(make, database.getMake(make));
            outputFile.println();
        }

        if (!model.equals("---")) {
            outputFile.println("Search for model: ");
            outputCars(model, database.getModel(model));
            outputFile.println();
        }

        if (!color.equals("---")) {
            outputFile.println("Search for color:");
            outputCars(color, database.getColorList(color));
            outputFile.println();
        }

        if (!year.equals("---")) {
            outputFile.println("Search for year:");
            outputCars(year, database.getYearList(year));
            outputFile.println();
        }

        if (!licenseFull.equals("---")) {
            outputFile.println("Search for full license:");
            outputLicense(licenseFull, database.getLicenseList(licenseFull));
            outputFile.println();
        }

        if (!licensePartial.equals("---")) {
            outputFile.println("Search for partial license:");
            outputLicense(licensePartial,
                    database.getLicenseListPartial(licensePartial));
            outputFile.println();
        }
    }

    /**
     * Output cars returned from particular search query
     * @param searchKey key searched for
     * @param carList list of cars returned from search key
     */
    public void outputCars(String searchKey, ArrayList<String> carList) {
        // if print database is selected, output special formatting
        if (searchKey == "Cars present") {
            if (carList.size() == 1) {
                outputFile.println(searchKey + " = 0");
            } else {
                outputFile.println(searchKey + " = " + carList.size() / 5);
                outputFile.printf("%-13S%-12S%-7S%-8S%-15S",
                        "Make", "Model", "Year", "Color", "License");
                outputFile.println();
                for (int i = 0; i < carList.size(); i += 5) {
                    outputFile.printf("%-13S%-12S%-7S%-8S%-15S",
                            carList.get(i), carList.get(i + 1),
                            carList.get(i + 2), carList.get(i + 3),
                            carList.get(i + 4));
                    outputFile.println();
                }
            }
        } else {
                if (carList.size() == 1) {
                    outputFile.println(searchKey + " = 0");
                } else {
                    outputFile.println(searchKey + " = " + carList.size() / 5);
                    for (int i = 0; i < carList.size(); i += 5) {
                        outputFile.println(carList.get(i) + ", "
                                + carList.get(i + 1)
                                + ", " + carList.get(i + 2)
                                + ", " + carList.get(i + 3)
                                + ", " + carList.get(i + 4));
                    }
                }
            }
    }

    /**
     * Special output method for outputting cars from particular license search
     *  query
     * @param searchKey key searched for
     * @param carList list of cars returned from search key
     */
    public void outputLicense(String searchKey, ArrayList<String> carList) {
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

    public static void main(String[] args) {
        CarDBDriver driver = new CarDBDriver();
        Boolean searchComplete = false;
        do {
            driver.displaySearchWindow();
            searchComplete = driver.displaySearchComplete();
        } while(!searchComplete);
        driver.outputFile.close();
        System.exit(0);
    }
}
