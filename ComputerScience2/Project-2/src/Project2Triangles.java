/*	Program: Project 2: Triangles
 * 		This program prompts the user to input three integer values that
 * 	    represent three sides of a triangle (using the command line). this
 * 	    program is robust in the fact that it handles many errors for invalid
 * 	    data, generally any input that is not a positive integer value. When
 * 	    valid data is input, the program evaluates the sides of a triangle and
 * 	    displays what type of triangle it is. This program prints a summary of
 * 	    input, output, and errors both to the console as well as a file.
 *
 * 	Author: Kyle L Frisbie	Date: 20 September 2014	Course: CS2 Fall 2014: Noon
 *
 * 	Limitations:
 * 		Because the console does not allow the input of new lines (carriage
 * 	    returns) or tabbed spaces, this program cannot identify those types of
 * 	    invalid data specifically. Instead, attempting to insert a carriage
 * 	    return (pressing enter) results in an error of "less than three numbers
 * 	    input" and attempting to insert a tab creates no space at all, usually
 * 	    resulting in another "less than three numbers input" error.
 */

import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by Kyle on 9/9/2014.
 */
public class Project2Triangles {
    private int[] sideLengths = new int[3];
    private String outputFileName = "Output_Project2_Test.txt";
    private String testAuthorName = "Kyle L Frisbie";
    private PrintWriter fileOutput;

    public static void main (String[] args) throws IOException {
        Project2Triangles triangles = new Project2Triangles();
        boolean endProgram[] = new boolean[2];
        triangles.initializeOutput();
        triangles.printHeading();
        do {
            endProgram = triangles.takeUserInput();

            // if user selected not to end program, and there are no errors,
            // continue
            if (!endProgram[0] && !endProgram[1]) {
                triangles.identifyTriangle();
                triangles.outputResults("-----");
            }
        // while user hasn't selected to end program, or there are errors
        // present, continue looping and testing triangles
        } while(!endProgram[0] || endProgram[1]);
        triangles.fileOutput.close();
        System.exit(0);
    } // main

/*****************************************************************************/
    public boolean checkForExitSelect(String tokens[]) {
        if(tokens[0].contains("-1")) {
            // a value of -1 determines the user selected to end program
            return true;
        }
        return false;
    } // checkForExitSelect

/*****************************************************************************/
    public boolean checkForInputErrors(String[] tokens) {
//        System.out.println("In checkForInputErrors method");
        boolean errorsPresent = false;
        if(checkForInputLength(tokens)) {
            errorsPresent = true;
        }
        if(checkForInvalidOperands(tokens)) {
            errorsPresent = true;
        }
        if(checkNumberValues(tokens)) {
            errorsPresent = true;
        }
        return errorsPresent;
    } // checkForInputErrors

/*****************************************************************************/
    public boolean checkForInputLength(String[] tokens) {
        if(tokens.length > 3) {
            outputError("Error: Your input contains more than three " +
                    "numbers. \nPlease enter three valid integers for the " +
                    "sides of a triangle.");
            return true;
        } else if(tokens.length < 3) {
            outputError("Error: Your input contains less than three " +
                    "numbers. \nPlease enter three valid integers for the " +
                    "sides of a triangle");
            return true;
        }
        return false;
    } // checkForInputLength

/*****************************************************************************/
    public boolean checkForInvalidOperands(String[] tokens) {
        boolean errorsPresent = false;
        String letterPattern = "[a-zA-Z]*";     // delimiter to check for
                                                //  invalid letters
        String operatorPattern = "[!-/:-@]*";   // delimiter to check for
                                                //  invalid operands
        String[] tempTokens;
        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].matches(letterPattern)) {
                errorsPresent = true;
                outputError("Error: Your input contains invalid " +
                        "letters, you entered (" + tokens[i] + "). \nYou are not " +
                        "allowed to use letters when entering the sides" +
                        " of a triangle.");
            }
            if(tokens[i].matches(operatorPattern)) {
                errorsPresent = true;
                outputError("Error: Your input contains an invalid " +
                        "operator, you entered (" + tokens[i] + "). \nYou are not " +
                        "allowed to use non-integer operators when " +
                        "entering the sides of a triangle");
            }
            tempTokens = new String[tokens[i].length()];
            tempTokens = tokens[i].split("");
            for (int j = 0; j < tempTokens.length; j++) {
                if(tempTokens[j].matches(operatorPattern) && (tempTokens.length > 1)) {
                    errorsPresent = true;
                    outputError("Error: Your input contains an invalid " +
                            "operator, you entered (" + tempTokens[j] + "). \nYou are not " +
                            "allowed to use non-integer operators when " +
                            "entering the sides of a triangle");
                }
            }

        }
        return errorsPresent;
    } // checkForInvalidOperands

/*****************************************************************************/
    public boolean checkNumberValues(String[] tokens) {
        boolean errorsPresent = false;
        for (int i = 0; i < tokens.length; i++) {
            try {
                if(Integer.parseInt(tokens[i]) < 1) {
                    outputError("Error: Your input contains a non-positive " +
                            "integer, you entered (" + tokens[i] + "). \nYou " +
                            "are not allowed to use non-positive integers " +
                            "when entering the sides of a triangle");
                    errorsPresent = true;
                }
            } catch(NumberFormatException e) {
                errorsPresent = true;
            }
            try {
                if ((Double.parseDouble(tokens[i]) % 1) > 0) {
                    outputError("Error: Your input contains a decimal " +
                            "number, you entered (" + tokens[i] + "). \nYou " +
                            "are not allowed to use decimal numbers " +
                            "when entering the sides of a triangle");
                    errorsPresent = true;
                }
            } catch(NumberFormatException e) {
                errorsPresent = true;
            }
        }
        return errorsPresent;
    } // checkNumberValues

/*****************************************************************************/
    public boolean equilateralTriangle() {
//        System.out.println("In equilateralTriangle method");
        if (sideLengths[0] == sideLengths[1] && sideLengths[1] == sideLengths[2]) {
            outputResults("Result: Your triangle is EQUILATERAL");
            return true;
        }
        return false;
    } // equilateralTriangle

/*****************************************************************************/
    public void identifyTriangle() {
//        System.out.println("In identifyTriangle method");
        boolean notATriangleCheck;          // will be set to true if user
                                            //  input is not a triangle
        notATriangleCheck = notATriangle();
        if (!notATriangleCheck) {
            boolean equilateralCheck;
            equilateralCheck = equilateralTriangle();
            isoscelesTriangle(equilateralCheck);
            scaleneTriangle();
            rightTriangle();
            specialCaseTriangle();
        }
    } // checkForErrors

/*****************************************************************************/
    public void initializeOutput() throws IOException {
//        System.out.println("In initializeOutput method");
        FileWriter outputDataFile = new FileWriter(outputFileName);
        PrintWriter outputFile = new PrintWriter(outputDataFile, true);
        fileOutput = outputFile;
    } // initializeOutput

/*****************************************************************************/
    public void isoscelesTriangle(boolean equilateralCheck) {
//        System.out.println("In isoscelesTriangle method");
        if (!equilateralCheck && (sideLengths[0] == sideLengths[1] ||
                sideLengths[0] == sideLengths[2] ||
                sideLengths[1] == sideLengths[2])) {
            outputResults("Result: Your triangle is ISOSCELES");
        }
    } // isoscelesTriangle

/*****************************************************************************/
    public boolean notATriangle() {
//        System.out.println("In notATriangle method");
        boolean notATriangle = false;
        if(sideLengths[0] + sideLengths[1] < sideLengths[2]) {
            outputResults("Result: Your triangle is NOT A TRIANGLE.");
            notATriangle = true;
        }
        return notATriangle;
    } // notATriangle

/*****************************************************************************/
    public void outputOfInput(String[] input) {
//        System.out.println("In outputOfInput method");
        String inputString = "";
        for (int i = 0; i < input.length; i++) {
            inputString += input[i] + " ";
        }
        outputResults("Input: " + inputString);
    } // outputOfInput

/*****************************************************************************/
    public void outputError(String output) {
        outputResults(output);
        outputResults("-----");
    }

/*****************************************************************************/
    public void outputResults(String output) {
//        System.out.println("In outputResults method");
        System.out.println(output);
        fileOutput.println(output);
    } // outputResults

/*****************************************************************************/
    public void printHeading() {
//        System.out.println("In printHeading method");
        Calendar calendar = Calendar.getInstance();
        fileOutput.println("Program 2: Triangle Checker");
        fileOutput.println("Program Author: Kyle L Frisbie");
        fileOutput.println("Test File Author: " + testAuthorName);
        fileOutput.println("Date Generated: " + calendar.getTime().toString());
        fileOutput.println();
    } // printHeading

/*****************************************************************************/
    public void rightTriangle() {
//        System.out.println("In rightTriangle method");
        if ((Math.pow(sideLengths[0], 2) + Math.pow(sideLengths[1], 2))
                == Math.pow(sideLengths[2], 2)) {
            outputResults("Result: Your triangle is RIGHT");
        }
    } // rightTriangle

/*****************************************************************************/
    public void scaleneTriangle() {
//        System.out.println("In scaleneTriangle method");
        if ((sideLengths[0] != sideLengths[1]) &&
            (sideLengths[1] != sideLengths[2]) &&
            (sideLengths[0] != sideLengths[2])) {
            outputResults("Result: Your triangle is SCALENE");
        }
    } // scaleneTriangle

/*****************************************************************************/
    public void specialCaseTriangle() {
        if (sideLengths[0] + sideLengths[1] == sideLengths[2]) {
            outputResults("Result: Your triangle is a SPECIAL CASE TRIANGLE");
            outputResults("The two short sides of your triangle lay directly " +
                    "\non top of the long side of your triangle.");
        }
    } // specialCaseTriangle

/*****************************************************************************/
    public boolean[] takeUserInput() {
        //        System.out.println("In takeUserInput method");
        Scanner keyboard = new Scanner(System.in);
        String inputLine;
        String[] tokens;
        boolean[] exitAndError =    // Passes 2 values back to main:
                new boolean[2];     //  index 0: exit code
                                    //  index 1: error code
        exitAndError[0] = false;    // exit program = false
        exitAndError[1] = false;    // errors present = false
        System.out.print("Enter the length of three sides of a triangle " +
                "(with a space between each number) to learn what kind of " +
                "triangle it is (enter -1 to exit): ");
        inputLine = keyboard.nextLine().trim();
        if(!inputLine.isEmpty()) {
            tokens = new String[inputLine.length()];
            tokens = inputLine.split("\\s+");
            outputOfInput(tokens);

            // if user selected to exit program
            if (checkForExitSelect(tokens)) {
                outputResults("Result: Exiting program.");
                exitAndError[0] = true;
            }
            if (!exitAndError[0]) {
                exitAndError[1] = checkForInputErrors(tokens);

                // if errors are present
                if (exitAndError[1]) {
                    return exitAndError;
                } else {
                    for (int i = 0; i < sideLengths.length; i++) {
                        sideLengths[i] = Integer.parseInt(tokens[i]);
                    }
                }
                Arrays.sort(sideLengths);
            }
        } else {
            tokens = new String[1];
            tokens[0] = "";
            outputOfInput(tokens);
            outputError("Error: You did not enter any information. Please " +
                    "input three integer values to identify a triangle.");
            exitAndError[1] = true;
        }
        return exitAndError;
    } // takeUserInput

} // Project2Traingles