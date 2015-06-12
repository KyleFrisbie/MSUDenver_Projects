/**
 * @author Kyle L Frisbie
 * @version 1
 *
 * Big Addition:
 *  This program does positive, non-zero integer addition with the ability to
 *  large numbers with sizes outside of Java's primitive, non-floating point
 *  data types. This program reads from an input file, which includes at least
 *  one, possibly many, test case which include a line describing the test
 *  case, two lines with separate operands, and a line with an expected result.
 *  After obtaining data from the input file, the operands are pushed onto
 *  separate stacks (with single integers occupying each push). These stacks
 *  are then added together, one integer at a time, with the result pushed onto
 *  another stack containing the calculated result. Another stack comprised of
 *  the provided solution from the input file is also created, the Stack with
 *  the calculated result is reversed and stored in another stack (so as to
 *  match the order of the provided solution stack), then the provided solution
 *  and calculated result stacks are compared one integer at a time. Results
 *  are printed to an output file and contain the test case details, along with
 *  each operand, the expected and calculated results, and if the test passed
 *  or not.
 *
 * Limitation:
 *  Handles only non-negative integer values with a correctly placed input file
 *  that is readable, not empty, and formatted to the specifications of the
 *  program.
 */

import java.io.*;
import java.util.Scanner;

public class BigAddition {
    private String inputFileName;
    private Scanner inputFile;
    private String outpuFileName = "Project5_OutputFile_KyleLFrisbie.txt";
    private PrintWriter outputFile;
    private int stackSize;                      // size of stack in calculated
                                                //  solution (number of items
                                                //  pushed onto stack
    private Stack<Integer> opOneStack;          // Stack of operator one
    private Stack<Integer> opTwoStack;          // Stack of operator two
    private Stack<Integer> providedSolution;    // Stack of solution from input file
    private Stack<Integer> solutionCalculated;  // Stack of calculated solution
    private Stack<Integer> calcResultReversed;  // Calculated solution reversed order

    /********************************************************************************/
    /**
     * BigAddition constructor, initializes Scanner of input file, PrintWriter for
     * output file, prints heading to output file
     */
    public BigAddition() {
        takeInputFile();
        createOutputFile();
        printHeader();
        readInputFile();
    } // BigAddition

    /********************************************************************************/
    public static void main(String[] args) {

        BigAddition bigAddition = new BigAddition();
        while (bigAddition.inputFile.hasNext()) {
            bigAddition.stackSize = 0;
            String testCaseInfo = bigAddition.inputFile.nextLine().trim();
            String operandOneRead;
            operandOneRead = bigAddition.takeOperandOne();

            String operandTwoRead;
            operandTwoRead = bigAddition.takeOperandTwo();

            String expectedResultRead;
            expectedResultRead = bigAddition.takeFileSolution();

            bigAddition.calculateSolution();

            String calculatedResult;
            calculatedResult = bigAddition.setCalcResultReversed();

            String testPassed;
            testPassed = Boolean.toString(
                    bigAddition.compareResults(bigAddition.calcResultReversed, bigAddition.providedSolution));

            bigAddition.outputResults(testCaseInfo, operandOneRead, operandTwoRead,
                    expectedResultRead, calculatedResult, testPassed);

            // Eat blank line in input file between test cases
            if(bigAddition.inputFile.hasNext()) {
                bigAddition.inputFile.nextLine();
            }
        }
        bigAddition.outputFile.close();
        System.exit(0);
    } // main

    /********************************************************************************/
    /**
     * returns new String operand containing appropriately placed commas
     * @param number String number that needs added commas
     * @return String number with commas
     */
    public String addCommas(String number) {
        String commasAdded = "";
        int currentDigitCounter = 0;
        int stringLength = number.length();
        for (int i = 0; i < stringLength; i++) {
            commasAdded += number.charAt(i);
            currentDigitCounter ++;
            if((stringLength - currentDigitCounter) % 3 == 0 &&
                    stringLength != currentDigitCounter) {
                commasAdded += ",";
            }
        }
        return commasAdded;
    } // addCommas

    /********************************************************************************/
    /**
     * calculates solution from operand one and operand two, pushes solution onto
     * new stack
     */
    public void calculateSolution() {
        solutionCalculated = new Stack<Integer>(opOneStack.size()
                + opTwoStack.size());
        int carry = 0;
        while (!opOneStack.isEmpty() || !opTwoStack.isEmpty()) {
            int result;
            if (opOneStack.isEmpty()) {
                result = carry + opTwoStack.pop();
            } else if (opTwoStack.isEmpty()) {
                result = carry + opOneStack.pop();
            } else {
                result = opOneStack.pop() + opTwoStack.pop() + carry;
            }
            if (result >= 10) {
                result -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            solutionCalculated.push(result);
            stackSize++;
        }
        if (carry > 0) {
            solutionCalculated.push(carry);
            stackSize++;
        }
    } // calculatedSolution

    /********************************************************************************/
    /**
     * compares calculated result to expected result from file
     * @param calcResult Stack<Integer>, stack of calculated results from program
     * @param fileResult Stack<Integer>, stack of expected results provided by input
     *                   file
     * @return boolean, true if results match (calculated and expected results)
     */
    public boolean compareResults(Stack<Integer> calcResult,
                                  Stack<Integer> fileResult) {
        for (int i = 0; i < fileResult.size(); i++) {
            if(fileResult.pop() != calcResult.pop()) {
                return false;
            }
        }
        return true;
    } // compareResults

    /********************************************************************************/
    /**
     * initializes PrintWriter of outputFile
     * @exception java.io.IOException
     */
    public void createOutputFile() {
        try {
            FileWriter outputDataFile = new FileWriter(outpuFileName);
            outputFile = new PrintWriter(outputDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // createOutputFile

    /********************************************************************************/
    /**
     * outputs results to output file
     * @param testCase String, description of test case
     * @param op1 String, value of operand one
     * @param op2 String, value of operand two
     * @param expResult String, value of expected result (from input file)
     * @param actResult String, value of calculated result (from program)
     * @param passed boolean, true if expected and calculated results match
     */
    public void outputResults(String testCase, String op1, String op2,
                              String expResult, String actResult, String passed) {
        formatPrint("Test Case: ", testCase);
        formatPrint("Operand One: ", addCommas(op1));
        formatPrint("Operand Two: ", addCommas(op2));
        formatPrint("Expected Result: ", addCommas(expResult));
        formatPrint("Actual Result: ", addCommas(actResult));
        formatPrint("Test Passed: ", passed);
        outputFile.println();
    } // outputResults

    /********************************************************************************/
    /**
     * general output method to streamline printf output to file with a carriage
     * return (println) between each printf method
     * @param stringA String that describes the variable to follow
     * @param stringB String that holds the result of the program process
     */
    public void formatPrint(String stringA, String stringB) {
        outputFile.printf("%-17s%-100s",stringA, stringB);
        outputFile.println();
    }

    /********************************************************************************/
    /**
     * output header to output file including title, author, and input file read
     */
    public void printHeader() {
        outputFile.println("BigAddition: Program which adds large " +
                "numbers using stack operations.");
        outputFile.println("Author: Kyle L Frisbie");
        outputFile.println("Input File Read: " + inputFileName);
        outputFile.println();
    } // printHeader

    /********************************************************************************/
    /**
     * initializes Scanner of inputFile
     * @exception java.io.FileNotFoundException
     */
    public void readInputFile() {
        File inputDataFile = new File(inputFileName);
        try {
            inputFile = new Scanner(inputDataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } // readInputFile

    /********************************************************************************/
    /**
     * pushes items off of calculated result stack and onto a new stack, reversing the
     * order of items on the stack, making for easy comparison of expected and
     * calculated results
     * @return String calculated result (reversed so as to be in logical order)
     */
    public String setCalcResultReversed() {
        calcResultReversed = new Stack<Integer>(stackSize);
        String calculatedResult = "";
        for (int i = 0; i < stackSize; i++) {
            calcResultReversed.push(solutionCalculated.pop());
            calculatedResult += Integer.toString(calcResultReversed.peek());
        }
        return calculatedResult;
    } // setCalcResultReversed

    /********************************************************************************/
    /**
     * obtains expected solution from input file and creates a stack which contains
     * expected solution
     * @return String expected solution
     */
    public String takeFileSolution() {
        String fileSolution = inputFile.nextLine().trim();
        fileSolution = fileSolution.replace(",", "");
        providedSolution = new Stack(fileSolution.length());
        String expectedResultRead = "";
        for (int j = 0; j < fileSolution.length(); j++) {
            providedSolution.push(Integer.parseInt(
                    Character.toString(fileSolution.charAt(j))));
            expectedResultRead += Integer.toString(providedSolution.peek());
        }
        return expectedResultRead;
    } // takeFileSolution

    /********************************************************************************/
    /**
     * takes user defined file location and sets it to String inputFileName
     */
    public void takeInputFile() {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the location of the input file, " +
                "including the file extension (ie: .txt): ");
        inputFileName = keyboard.nextLine().trim();
        keyboard.close();
    } // takeInputFile

    /********************************************************************************/
    /**
     * obtains operand one from input file and creates a stack which contains operand
     * one
     * @return String operand one
     */
    public String takeOperandOne() {
        String operandOne = inputFile.nextLine().trim();
        operandOne = operandOne.replace(",", "");
        opOneStack = new Stack(operandOne.length());
        String operandOneRead = "";
        for (int j = 0; j < operandOne.length(); j++) {
            opOneStack.push(Integer.parseInt(
                    Character.toString(operandOne.charAt(j))));
            operandOneRead += Integer.toString(opOneStack.peek());
        }
        return operandOneRead;
    } // takeOperandOne

    /********************************************************************************/
    /**
     * obtains operand two from input file and creates a stack which contains operand
     * two
     * @return String operand two
     */
    public String takeOperandTwo() {
        String operandTwo = inputFile.nextLine().trim();
        operandTwo = operandTwo.replace(",", "");
        opTwoStack = new Stack(operandTwo.length());
        String operandTwoRead = "";
        for (int j = 0; j < operandTwo.length(); j++) {
            opTwoStack.push(Integer.parseInt(
                    Character.toString(operandTwo.charAt(j))));
            operandTwoRead += Integer.toString(opTwoStack.peek());
        }
        return operandTwoRead;
    } // takeOperandTwo

} // BigAddition class