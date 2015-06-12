import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Kyle on 11/20/2014.
 */
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

//    String inputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\" +
//            "MSU-DENVER\\CS-2\\Project-9\\Input_KyleLFrisbie.txt";
//    String outputFileName = "C:\\Users\\Kyle\\OneDrive\\MSU Denver\\GitRepo\\" +
//            "MSU-DENVER\\CS-2\\Project-9\\Output_CarsMain_KyleLFrisbie.txt";
    PrintWriter outputFile;

    public CarDBDriver () {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the location of your input file: ");
        String inputFileName = keyboard.nextLine().trim();
        File inputDataFile = new File(inputFileName);
        database = new DatabaseBST();

        // options for database printing combo box
        dataPrint.add("No");
        dataPrint.add("Yes");

        try {
            Scanner inputFile = new Scanner(inputDataFile);
            System.out.print("Enter the location of your output file: ");
            String outputFileName = keyboard.nextLine().trim();
            FileWriter outputDataFile = new FileWriter(outputFileName);
            outputFile = new PrintWriter(outputDataFile);

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

    public static void main(String[] args) {
        CarDBDriver driver = new CarDBDriver();
        driver.displaySearchWindow();
        driver.outputFile.close();
        System.exit(0);
    }

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

        JOptionPane.showConfirmDialog(null, searchWindow, "Search Car Database", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);

        passSearchQueries(
                dataPrintCombo.getSelectedItem().toString(),
                makeCombo.getSelectedItem().toString(),
                modelCombo.getSelectedItem().toString(),
                colorCombo.getSelectedItem().toString(),
                yearCombo.getSelectedItem().toString(),
                fullLicenseBox.getText(),
                partialLicenseBox.getText());
    }

    private void passSearchQueries(String dataPrint, String make, String model, String color, String year, String licenseFull, String licensePartial) {
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
            outputLicense(licensePartial, database.getLicenseListPartial(licensePartial));
            outputFile.println();
        }
    }

    public void outputCars(String searchKey, ArrayList<String> carList) {
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
}
