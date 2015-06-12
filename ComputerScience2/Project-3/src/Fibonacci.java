/*	Program: Project 3: Efficiency (Fibonacci)
 * 		This program automatically runs numbers through a Fibonacci recursive
 * 	    method using values from 0 to 30 (through the use of a single for
 * 	    loop). These number values are passed to an unoptimized fibonacci
 * 	    method that strictly does recursion, as well as an optimized fibonacci
 * 	    method that first references a table of values, before going into
 * 	    recursion. A table is printed to an output file containing the number
 * 	    of unoptimized and optimized method calls, as well as the number of
 * 	    table accesses, so that the user might have a better understanding of
 * 	    the most efficient implementation.
 *
 * 	Author: Kyle L Frisbie	Date: 30 September 2014	Course: CS2 Fall 2014: Noon
 *
 * 	Limitations:
 * 		This program is hardcoded to x values from 0 to 30, and the access
 * 	    is created to fit only those number of solutions. If a user wanted to
 * 	    view a wider range of calculations, they would be required to go into
 * 	    the program code to change it manually.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kyle on 9/19/2014.
 */

public class Fibonacci {
    private PrintWriter fileOutput;
    private String fileName = "Project3_Fibonacci_KyleLFrisbie.txt";
    private int notOptimizedCount;
    private int optimizedCount;
    private int tableAccessCount;
    private int[] fibTable;         // reference table for optimized fib method


    public Fibonacci() throws IOException {
        createOutputFile();
        outputResultsHeading();
        for(int i = 1; i < 31; i++) {
            resetCounters();
            fibUnoptimized(i);
            setTable();
            fibOptimal(i);
            outputResults(i);
        }
        System.out.println("Concluding Fibonacci.");
    } // Fibonacci Constructor

    /*************************************************************************/
    public void createOutputFile() throws IOException {
//        System.out.println("in createOutputFile method");
        String outputFileName = fileName;
        FileWriter outputDataFile = new FileWriter(outputFileName);
        fileOutput = new PrintWriter(outputDataFile, true);
    } // createOutputFile

    /*************************************************************************/
    public int fibOptimal(int number) {
//        System.out.println("in fibOptimal method");
        optimizedCount++;
        if(fibTable[number - 1] != 0) {
            tableAccessCount+=2;
            return fibTable[number -1];
        } else if(number == 1) {
            tableAccessCount+=2;
            fibTable[number -1] = 1;
            return fibTable[number -1];
        } else if(number == 2) {
            tableAccessCount+=2;
            fibTable[number -1] = 1;
            return fibTable[number -1];
        } else {
            tableAccessCount++;
            fibTable[number - 1] = fibOptimal(number - 1) +
                    fibOptimal(number - 2);
        }
        tableAccessCount++;
        return fibTable[number - 1];
    } // fibOptimal

    /*************************************************************************/
    public int fibUnoptimized(int number) {
//        System.out.println("in fibUnoptimal method");
        notOptimizedCount++;
        if(number == 1) {
            return 1;
        }
        if(number == 2) {
            return  1;
        }
        return fibUnoptimized(number - 1) + fibUnoptimized(number - 2);
    } // fibUnoptimized

    /*************************************************************************/
    public void outputResults(int inputNumber) {
//        System.out.println("in outputResults method");
        fileOutput.printf("%,9d%,10d%,22d%,20d%,23d\n", inputNumber,
                fibTable[inputNumber - 1], notOptimizedCount, optimizedCount,
                tableAccessCount);
        fileOutput.println();
    } // outputResults

    /*************************************************************************/
    public void outputResultsHeading() {
//        System.out.println("in outputResultsHeading method");
        fileOutput.println("Fibonacci Output File");
        fileOutput.printf("%9s%10s%22s%20s%23s\n", "Parameter", "Answer",
                "Unoptimized Calls", "Optimized Calls", "Table Access Count");
        fileOutput.println();
    } // outputResultsHeading

    /*************************************************************************/
    public void resetCounters() {
        notOptimizedCount = 0;
        optimizedCount = 0;
        tableAccessCount = 0;
    } // resetCounters

    /*************************************************************************/
    public void setTable() {
//        System.out.println("in setTable");
        fibTable = new int[30];
        for(int i = 0; i < fibTable.length; i++) {
            fibTable[i] = 0;
        }
    } // setTable
}
