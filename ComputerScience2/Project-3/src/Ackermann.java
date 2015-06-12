/*	Program: Project 3: Efficiency (Ackermann)
 * 		This program automatically runs numbers through an Ackermann recursive
 * 	    method using x values from 0 to 4 and y values from 0 to 15 (through
 * 	    the use of two embedded for loops). These number values are passed to
 * 	    an unoptimized ackermann method that strictly does recursion, as well
 * 	    as an optimized ackermann method that first references a table of
 * 	    values, before going into recursion. A table is printed to an output
 * 	    file containing the number of unoptimized and optimized method calls,
 * 	    as well as the number of table accesses, along with a few other values,
 * 	    so that the user might have a better understanding of the most
 * 	    efficient implementation.
 *
 * 	Author: Kyle L Frisbie	Date: 30 September 2014	Course: CS2 Fall 2014: Noon
 *
 * 	Limitations:
 * 		This program is hardcoded for x and y value generation, and the access
 * 	    table is created to fit only those number of solutions. If a user
 * 	    wanted to view a wider range of calculations, they would be required
 * 	    to go into the program code to change it manually.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kyle on 9/19/2014.
 */

public class Ackermann {
    private PrintWriter fileOutput;
    private String fileName = "Project3_Ackermann_KyleLFrisbie.txt";
    private int notOptimizedCount;
    private int optimizedCount;
    private int tableAccessCount;
    private int outOfBoundsCount;
    private int[][] ackTable;       // reference table for ack optimized method
    private int yMax;

    // Constructor for project 3 specifications
    public Ackermann() throws IOException {
        System.out.println("Running Ackermann program, check output file "
                + fileName + " for results.");
        createOutputFile();
        fileOutput.println("Ackermann Output File");
        fileOutput.println();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 16; y++) {
                // note: for loops emulate the x and y values,
                // x[0 - 4], y[0 - 15] to run values through
                // unoptimized and optimized methods and create
                // table
                resetCounters();
                setTable();
                if(y == 0) {
                    outputResultsHeading();
                }
                try {
                    ackUnoptimized(x, y);
                    ackOptimal(x, y);
                    outputResults(x, y);
                } catch (StackOverflowError e) {
                    outputOverflow(x, y, "Error, results " +
                            "in stack overflow.");
                }
            }
        }
        System.out.println("Concluding Ackermann.");
    } // Ackermann Constructor

    /*************************************************************************/
    // Constructor for expanded table values extra credit comparison
    public Ackermann(boolean ec) {
        if(ec) {
            resetCounters();
            setTable();
        }
    } // Ackermann EC Constructor

    /*************************************************************************/
    public int ackOptimal(int x, int y) {
//      System.out.println("in ackOptimal");
        optimizedCount++;
        if (y < 101) {
            tableAccessCount++;
            if (ackTable[x][y] != 0) {
                tableAccessCount++;
                return ackTable[x][y];
            } else if (x == 0) {
                tableAccessCount++;
                ackTable[x][y] = y + 1;
            } else if (y == 0) {
                tableAccessCount++;
                ackTable[x][y] = ackOptimal(x - 1, 1);
            } else {
                tableAccessCount++;
                ackTable[x][y] = ackOptimal(x - 1, ackOptimal(x, y - 1));
            }
            return ackTable[x][y];
        } else {
            outOfBoundsCount++;
            if (x == 0) {
                return y + 1;
            }
            return ackOptimal(x - 1, ackOptimal(x, y - 1));
        }
    } // ackOptimal

    /*************************************************************************/
    public int ackUnoptimized(int x, int y) {
//      System.out.println("in ackUnoptimized");
        notOptimizedCount++;
        if(y > yMax) {
            yMax = y;
        }
        if (x == 0) {
            return y + 1;
        } else if (y == 0) {
            return ackUnoptimized(x - 1, 1);
        }
        return ackUnoptimized(x - 1, ackUnoptimized(x, y - 1));
    } // ackUnoptimized

    /*************************************************************************/
    public void createOutputFile() throws IOException {
//      System.out.println("in createOutputFile");
        FileWriter outputDataFile = new FileWriter(fileName);
        fileOutput = new PrintWriter(outputDataFile, true);
    } // createOutputFile

    /*************************************************************************/
    public void outputOverflow(int x, int y, String error) {
//      System.out.println("in outputOverflow");
        fileOutput.printf("%7s%12s%105s", x, y, error);
        fileOutput.println();
    } // outputOverflow

    /*************************************************************************/
    public void outputResults(int x, int y) {
//      System.out.println("in outputResults");
        fileOutput.printf("%,7d%,12d%,10d%,22d%,20d%,23d%,20d%,10d", x, y,
                ackTable[x][y], notOptimizedCount, optimizedCount,
                tableAccessCount, outOfBoundsCount, yMax);
        fileOutput.println();
        fileOutput.println();
    } // outputResults

    /*************************************************************************/
    public void outputResultsHeading() {
//      System.out.println("in outputResultsHeading");
        fileOutput.println("------------------------------------------------" +
                "-----------------------------------------------------------" +
                "-----------------");
        fileOutput.printf("%7s%12s%10s%22s%20s%23s%20s%10s", "X Input",
                "Y Input", "Answer", "Unoptimized Calls", "Optimized Calls",
                "Table Access Count", "Y Exceeds Count", "Max Y");
        fileOutput.println();
        fileOutput.println("------------------------------------------------" +
                "-----------------------------------------------------------" +
                "-----------------");
    } // outputResultsHeading

    /*************************************************************************/
    public void resetCounters() {
        notOptimizedCount = 0;
        optimizedCount = 0;
        tableAccessCount = 0;
        outOfBoundsCount = 0;
        yMax = 0;
    }

    /*************************************************************************/
    public void setTable() {
//      System.out.println("in setTable");
        ackTable = new int[5][101];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 101; j++) {
                ackTable[i][j] = 0;
                }
            }
    } // setTable
} // Ackermann