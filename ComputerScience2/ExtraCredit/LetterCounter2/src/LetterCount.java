/**
 * LetterCounter:
 *  This program takes english text from an input file and returns the count
 *  of specific and total words (without formatting, eg. he's would be read
 *  into the program as hes, without the " ' "). This program also returns
 *  the number of occurrences of each letter, and the total number of letters
 *  in the input file. These values are returned to the console as well as to
 *  an output file specified in the program. You will notice a special data
 *  type "WordAndCount" embedded in this program. WordAndCount allows the
 *  program to store an object of WordAndCount (a word as a string, and the
 *  number of times that word occurs in the input file) into an ArrayList for
 *  later use. All words are stored in a separate LinkedList.
 *
 * @author Kyle L Frisbie
 * @version 2.0
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LetterCount {
    private String inputFileName = "counterTest4.txt";
    private String outputFileName = "counterTest4_Output.txt";
    private Scanner inputFile;
    private PrintWriter outputFile;
    private char[] letterArray;
    private int[] letterCountArray;
    private ArrayList<WordAndCount> wordArray = new ArrayList<>();
    private int wordArraySize;
    int totalWords;
    int totalLetters;

    /**
     *  Special data type that holds a word and its number~ of occurrences
     */
    private class WordAndCount {
        String word;
        int count;
    } //WordAndCount

    /**
     * LetterCount: initializes letterArrays and letterCountArray to size 26,
     * fills letterArray with characters a - z
     * @exception java.io.IOException
     */
    public LetterCount() {
        letterArray = new char[26];
        letterCountArray = new int[26];
        char letter = 'a';
        for (int i = 0; i < letterArray.length; i++) {
            letterArray[i] = letter;
            letter++;
        }
        try {
            File inputDataFile = new File(inputFileName);
            inputFile = new Scanner(inputDataFile);
            FileWriter outputDataFile = new FileWriter(outputFileName);
            outputFile = new PrintWriter(outputDataFile);
        } catch(IOException e) {
            System.out.println("There was an error processing your" +
                    "input/output file. Please check your file paths.");
        }
    } // LetterCount

    public static void main(String[] args) {
        LetterCount lc = new LetterCount();
        LinkedList<String> wordList = new LinkedList<>();
        String inputLine;
        while(lc.inputFile.hasNext()) {
            inputLine = lc.readInputFile();
            String[] lineSplit;
            lineSplit = inputLine.toLowerCase().split("\\s+");
            for (int i = 0; i < lineSplit.length; i++) {
                lineSplit[i] = lineSplit[i].replaceAll("[^a-zA-Z]", "");
                if(!wordList.itemExists(lineSplit[i])) {
                    wordList.push(lineSplit[i]);
                }
            }
        }
        while(!wordList.isEmpty()) {
            int count = wordList.getTopCount();
            String word = wordList.pop();
            lc.getWordCount(word, count);
            lc.getLetterCount(word, count);
        }
        lc.printHeader();
        lc.outputResults();
        lc.outputFile.close();
        System.exit(0);
    } // main

    /*************************************************************************/
    /**
     * reads next line from input file and stores in a string, if next line
     * is empty, this line is discarded and the next line is read.
     * @return String nextLine, the value of the next line with text in it
     */
    public String readInputFile() {
        String nextLine = inputFile.nextLine().trim();
        do {
            if (nextLine.isEmpty()) {
                nextLine = inputFile.nextLine().trim();
            }
        } while (nextLine.isEmpty());
        return nextLine;
    } // readInputFile

    /*************************************************************************/
    /**
     * creates a new object of WordAndCount which stores a String "word" and
     * an int "count" in a new object "wordPack" to be stored in wordArray
     * ArrayList
     * @param word: a word from the input file
     * @param count: the number of times the word occurs in the input file
     */
    public void getWordCount(String word, int count) {
        WordAndCount wordPack = new WordAndCount();
        wordPack.word = word;
        wordPack.count = count;
        wordArray.add(wordPack);
        totalWords += count;
        wordArraySize++;
    } // getWordCount

    /*************************************************************************/
    /**
     * gets the count of letters from each word
     * @param word: a word from the input file
     * @param count: the number of times the word occurs in the input file
     */
    public void getLetterCount(String word, int count) {
        char[] wordSplit = word.toCharArray();
        for (int i = 0; i < wordSplit.length; i++) {
            for (char letter = 'a'; letter <= 'z'; letter++) {
                if (wordSplit[i] == letter) {
                    letterCountArray[letter - 97] += count;
                    totalLetters += count;
                }
            }
        }
    } // getLetterCount

    /*************************************************************************/
    /**
     * prints title and author information to console/output file
     */
    public void printHeader() {
        universalOutput("Total word and letter count from : " + inputFileName);
        universalOutput("Author: Kyle L Frisbie");
    } // printHeader

    /*************************************************************************/
    /**
     * prints the resulting words, word counts, letters, and letter counts to
     * the console/output file
     */
    public void outputResults() {
        System.out.printf("%-23s%-15s", "Word Count", "Letter Count");
        outputFile.printf("%-23s%-15s", "Word Count", "Letter Count");
        universalOutput("");
        for (int i = 0; i < wordArraySize || i < letterArray.length; i++) {
            if(i <wordArraySize) {
                universalWordOutputFormatted(wordArray.get(i).word +
                        ": ", wordArray.get(i).count);
            } else {
                universalWordSpacer();
            }
            if(i < letterArray.length) {
                universalLetterOutputFormatted(letterArray[i] +
                        ": ", letterCountArray[i]);
            }
            universalOutput("");
        }
        universalOutput("Total words: " + totalWords);
        universalOutput("Total letters: " + totalLetters);
    } // outputResults

    /*************************************************************************/
    /**
     *  general method to output to console/output file using println
     *  @param string: takes a single String
     */
    public void universalOutput(String string) {
        System.out.println(string);
        outputFile.println(string);
    } // universalOutput

    /*************************************************************************/
    /**
     * output to console/output file, using printf to format a word and its
     * number of occurrences
     * @param description: a String which is a word from the input file
     * @param value: an int which is the number of times the word occurs in
     *             the input file
     */
    public void universalWordOutputFormatted(String description, int value) {
        System.out.printf("%-15s%5d%3s", description, value, " |  ");
        outputFile.printf("%-15s%5d%3s", description, value, " |  ");
    } // universalWordOutputFormatted

    /*************************************************************************/
    /**
     * general method using printf to output blank margin where
     * universalWordOutputFormatted would normally print out information, used
     * if the number of words output is less than the letter
     * output(26 lines, a-z) output to console/outputFile
     */
    public void universalWordSpacer() {
        System.out.printf("%-15s%5s%3s", "", "", " |  ");
        outputFile.printf("%-15s%5s%3s", "", "", " |  ");

    } // universalWordSpacer

    /*************************************************************************/
    /**
     * output to the console/output using printf to format a given letter and
     * its number of occurrences
     * @param description: String, letter to be output
     * @param value: int, number of occurrences of letter
     */
    public void universalLetterOutputFormatted(String description, int value) {
        System.out.printf("%-3s%5d%3s", description, value, " |");
        outputFile.printf("%-3s%5d%3s", description, value, " |");
    } // universalLetterOutputFormatted
} // class LetterCount
