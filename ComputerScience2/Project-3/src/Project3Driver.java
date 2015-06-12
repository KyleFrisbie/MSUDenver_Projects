/*	Program: Project 3: Efficiency (Driver)
 * 		This program prompts the user to input three a single integer value
 * 	    (1 -3) to select the particular program they would like to run
 * 	    (Fibonacci, Ackermann, or AckermannExtraCredit). If an invalid number
 * 	    or other character is input, the program will handle this exception
 * 	    and prompt user to re-input selection.
 *
 * 	Author: Kyle L Frisbie	Date: 30 September 2014	Course: CS2 Fall 2014: Noon
 *
 * 	Limitations:
 * 		This is just the program driver for the assigned Fibonacci and
 * 		Ackermann programs, and thus does nothing other than allow the user to
 * 	    select which recursive program they would like to run.
 */

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kyle on 9/19/2014.
 */
public class Project3Driver {
    public static void main(String[] args) throws IOException {
        Project3Driver project3Driver = new Project3Driver();
        int programSelect;    // holds value of user selected program selection
        do {
            programSelect = project3Driver.getUserInput();
            if(programSelect == 1) {
                Fibonacci fibonacci = new Fibonacci();
            } else if(programSelect == 2) {
                Ackermann ackermann = new Ackermann();
            } else if(programSelect == 3) {
                AckermannExtraCredit ackermannExtraCredit =
                        new AckermannExtraCredit();
            }
        } while(programSelect != -1);
        project3Driver.exitProgram();
    } // main

    /*************************************************************************/
    public int getUserInput (){
//        System.out.println("In getUserInput");
        boolean errorsPresent;
        int programSelection = 0;
        do {
            Scanner keyboard = new Scanner(System.in);
            errorsPresent = false;
            System.out.print("Which recursive method would you like to call?" +
                    "\nEnter (1) for Fibonacci, enter (2) for Ackermann," +
                    "\nenter (3) for AckermannExtraCredit " +
                    "(expanded table values)\n(enter -1 to exit): ");
            try {
                programSelection = keyboard.nextInt();
                if(programSelection == -1) {
                    return programSelection;
                } else if(programSelection > 0 || programSelection < 4) {
                    return programSelection;
                } else {
                    errorsPresent = true;
                    System.out.println("Error: Invalid input, please select " +
                            "program 1 or 2 (enter -1 to exit).");
                }
            } catch (InputMismatchException e) {
                errorsPresent = true;
                System.out.println("Error: Invalid input, please select " +
                        "program 1, 2, or 3 (enter -1 to exit).");
            }
            keyboard.close();
        } while(errorsPresent);
        return programSelection;
    } // getUserInput

    /*************************************************************************/
    public void exitProgram() {
//        System.out.println("In exitProgram");
        System.out.println("Exiting program.");
        System.exit(0);
    } // exitProgram
} // Project3Driver