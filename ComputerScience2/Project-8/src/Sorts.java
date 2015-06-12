/*

Program: Sorting
Authors: J. Gurka + Kyle L Frisbie
Date: November 6, 2014

Description:
 Sorts.java compares the time efficiency of different sorting algorithms by
 obtaining a time stamp before and after each sorting algorithm is used. The
 propose is to compare different algorithms and their efficiencies. Within this
 program you will find a comparison for: Bubble, Insertion, Selection, Merge,
 Quick, Shaker, and Shell's sorting methods. You will notice two different
 implementations of Shell's sort, the first implementation "good" is Shell's
 sort using a known efficient gap, as proven by Sedgewick and Wayne, in
 "Algorithms, 4th edition." The second implementation is of a modified gap
 within Shell's sort created by me to illustrate the impact of different gaps
 on time. The analyzed results of running these sorting methods is output both
 to the screen as well as to an output file.

Assumptions and limitations:
   1. Program takes random data specifically for analyzing sorting method
   efficiency. Cannot sort useful arrays submitted by the user.
   2. Array sizes are hard coded into program. In order to change array size to
   compare, user must go into code and alter array values.
   3. Assumes use for comparative purposes only.

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Sorts {

    private int n,        // number of values to be sorted
            run;          // current execution of sorts out of RUN_MAX

    // sorting counters
    private double comparisons,  // all sorts
            swaps,        // bubble sort, selection sort, quicksort
            shifts,       // insertion sort
            passes,       // bubble sort, insertion sort, selection sort
            copies,       // merge sort
            calls;        // merge sort, quicksort (n^2 sorts have 1 call each)

    private int[] data,   // initial random data, not changed during one run at
                          //    one n value
            dataCopy;     // same data, to be sorted

    private final int MAX_VALUE = 1000000,   // largest data value to be sorted
//            START_SIZE = 10000,    // smallest array size
//            END_SIZE = 100000,     // largest array size
            PRINT_MAX = 10,       // largest array that will print out
            RUN_MAX = 3;          // number of times to run each sort at each n
    private final int[] RUN_PARAMS = new int[] {10000, 50000, 100000};

    private DecimalFormat countFormatter;

    private PrintWriter outputFile;

    public Sorts() {
        // System.out.println("constructor ...");
        comparisons = swaps = shifts = passes = calls = 0;
//        n = START_SIZE;
//        n = RUN_PARAMS[0];
        data = new int[n];
        dataCopy = new int[n];
        countFormatter = new DecimalFormat("#,###");
        File outputDataFile = new File("C:\\Users\\Kyle\\OneDrive\\" +
                "MSU Denver\\GitRepo\\MSU-DENVER\\CS-2\\Project-8\\" +
                "Project8_KyleLFrisbie_Output.txt");
        try {
            outputFile = new PrintWriter(outputDataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Sorts sorter = new Sorts();
//        for (sorter.n = sorter.START_SIZE; sorter.n <= sorter.END_SIZE;
//              sorter.n *= 10) {
        for(int i = 0; i < sorter.RUN_PARAMS.length; i++) {
            sorter.n = sorter.RUN_PARAMS[i];

            sorter.generalOutput("-----");
            sorter.generalOutput("Sorts beginning, N = " +
                    sorter.countFormatter.format((long) sorter.n));
            sorter.generateData();  // new data, used by all sorts

            for (sorter.run = 1; sorter.run <= sorter.RUN_MAX; sorter.run++) {

                sorter.reset();         // counters = 0
                sorter.generateData();  // new data
                sorter.printList("Original list:", sorter.data);
                sorter.printList("Unsorted list:", sorter.dataCopy);
                sorter.generalOutput("   run #" + sorter.run);

                // Bubble
                sorter.bubbleSort();
                sorter.generalOutput("      bubble sort OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, bubble:", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //  sorting algorithm

                sorter.generalOutput("*** other sorts' output here");

                // Insertion
                sorter.insertionSort();
                sorter.generalOutput("      insertion sort OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, insertion:", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm

                // Selection
                sorter.selectionSort();
                sorter.generalOutput("      selection sort OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, selection:", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm

                // Merge
                sorter.mergeSort();
//                sorter.mergeSort(0, sorter.dataCopy.length - 1);
                sorter.generalOutput("      merge sort OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, merge:", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm

                // Quick
                sorter.quickSort();
                sorter.generalOutput("      quick sort OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, quick:", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm

                // Shaker
                sorter.shakerSort();
                sorter.generalOutput("      shaker sort OK? " +
                            (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, shaker:", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm

                // Shell's good
                sorter.shellSortGood();
                sorter.generalOutput("      Shell's sort (good) OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, Shell's (good):", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm

                // Shell's my
                sorter.shellSortMy();
                sorter.generalOutput("      Shell's sort (my) OK? " +
                        (sorter.isSorted() ? "yes" : "no"));
                sorter.printList("Sorted list, Shell's (my):", sorter.dataCopy);

                sorter.reset();         // counters = 0
                sorter.restoreData();   // copy of original data, for next
                                        //   sorting algorithm
            }  // runs

        }  // increasing n values
        sorter.outputFile.close();
        System.exit(0);
    }  // main

    public void bubbleSort() {
        // System.out.println("bubble sort ...");
        int passSwapCount,   // counter for early sort termination
                tempSwap;
        long startTime = System.currentTimeMillis();
        for (int pass = 0; pass < n; pass++) {
            passSwapCount = 0;    // swaps on this pass
            passes++;             // total passes
            // compare pairs in unsorted part of array
            for (int i = 0; i < n - pass - 1; i++) {
                // pair out of order?
                comparisons++;
                if (dataCopy[i] > dataCopy[i + 1]) {
                    // yes, swap
                    swaps++;
                    passSwapCount++;
                    tempSwap = dataCopy[i];
                    dataCopy[i] = dataCopy[i + 1];
                    dataCopy[i + 1] = tempSwap;
                }  // swap
            }  // one pass
            // end of pass - sorted?
            if (passSwapCount == 0) {
                break;
            }
        }  // all passes
        long endTime = System.currentTimeMillis();
        // results
        generalOutput("      bubble sort\n" +
                "         elapsed time = " + createTimeString(endTime -
                                                startTime) + "\n" +
                "         comparisons = " +
                                countFormatter.format(comparisons) + "\n" +
                "         swaps = " + countFormatter.format(swaps) + "\n" +
                "         passes = " + countFormatter.format(passes) + "\n" +
                "         calls = 1");
    }  // bubbleSort

    // Insertion sort
    public void insertionSort() {
        int tempSwap;
        long startTime = System.currentTimeMillis();
        int N = dataCopy.length;
        for (int i = 0; i < N; i++) {
            passes++;
            for (int j = i; j > 0; j--) {
                comparisons++;
                if(dataCopy[j] < dataCopy[j - 1]) {
                    tempSwap = dataCopy[j - 1];
                    dataCopy[j - 1] = dataCopy[j];
                    dataCopy[j] = tempSwap;
                    shifts++;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        // results
        generalOutput("      insertion sort\n" +
                "         elapsed time = " + createTimeString(endTime -
                                                startTime) + "\n" +
                "         comparisons = " +
                                countFormatter.format(comparisons) + "\n" +
                "         shifts = " + countFormatter.format(shifts) + "\n" +
                "         passes = " + countFormatter.format(passes) + "\n" +
                "         calls = 1");
    } // insertion sort

    // Selection sort
    public void selectionSort() {
        int N = dataCopy.length;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                passes++;
                if(dataCopy[j] < dataCopy[min]) {
                    min = j;
                }
                comparisons++;
            }
            if(dataCopy[min] != dataCopy[i]) {
                int temp = dataCopy[i];
                dataCopy[i] = dataCopy[min];
                dataCopy[min] = temp;
                swaps++;
            }
        }
        long endTime = System.currentTimeMillis();

        // results
        generalOutput("      selection sort\n" +
                "         elapsed time = " +
                                createTimeString(endTime - startTime) + "\n" +
                "         comparisons = " +
                                countFormatter.format(comparisons) + "\n" +
                "         swaps = " + countFormatter.format(swaps) + "\n" +
                "         passes = " + countFormatter.format(passes) + "\n" +
                "         calls = 1");
    } // selection sort

    // Merge sort
    public void mergeSort() {
        long startTime = System.currentTimeMillis();
        int[] temp = new int[dataCopy.length];
        sortMerge(temp, 0, temp.length - 1);
        long endTime = System.currentTimeMillis();

        // results
        generalOutput("      merge sort\n" +
                "         elapsed time = " +
                                createTimeString(endTime - startTime) + "\n" +
                "         comparisons = " +
                                  countFormatter.format(comparisons) + "\n" +
                "         copies = " + countFormatter.format(copies) + "\n" +
                "         calls = " + countFormatter.format(calls));
    }

    // Sort the sub arrays for merge sort
    private void sortMerge(int[] temp, int lo, int hi) {
        calls++;
        if(hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sortMerge(temp, lo, mid);
        sortMerge(temp, mid + 1, hi);
        merge(temp, lo, mid, hi);
    }

    // Merge the sub arrays for merge sort
    private void merge(int[] temp, int lo, int mid, int hi) {
        // copy items to temp array
        for (int k = lo; k <= hi; k++) {
            temp[k] = dataCopy[k];
            copies++;
        }

        // merge back to data copy array
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if( i > mid) {
                dataCopy[k] = temp[j++];
            } else if ( j > hi) {
                dataCopy[k] = temp[i++];
            } else if (temp[j] < temp[i]) {
                dataCopy[k] = temp[j++];
                comparisons++;
            } else {
                dataCopy[k] = temp[i++];
                comparisons++;
            }
        }
    }

    // Quick sort
    public void quickSort() {
        long startTime = System.currentTimeMillis();
        sortQuick(dataCopy, 0, dataCopy.length - 1);
        long endTime = System.currentTimeMillis();

        // results
        generalOutput("      quick sort\n" +
                "         elapsed time = " +
                                createTimeString(endTime - startTime) + "\n" +
                "         comparisons = " +
                                  countFormatter.format(comparisons) + "\n" +
                "         copies = " + countFormatter.format(copies) + "\n" +
                "         calls = " + countFormatter.format(calls));
    }

    // Quick sort recursive method
    private void sortQuick(int[] a, int lo, int hi) {
        calls++;
        if(hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        sortQuick(a, lo, j - 1);
        sortQuick(a, j + 1, hi);
    }

    // Quick sort partitioning of array
    private int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int v = a[lo];
        while(true) {
            comparisons++;

            // find item on lo to swap
            while(a[++i] < v) {
                if(i == hi) {
                    break;
                }
            }

            // find item on hi to swap
            while(v < a[--j]) {
                if(j == lo) {
                    break;
                }
            }

            // check if pointers cross
            if(i >= j) {
                break;
            }

            // swap hi and lo items
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            swaps++;
        }

        // put partitioning item v at a[j]
        int temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
        swaps++;

        return j;
    }

    // Shaker sort
    public void shakerSort() {
        long startTime = System.currentTimeMillis();
        int temp;
        for (int i = 0; i < dataCopy.length / 2; i++) {
            passes++;
            boolean swapped = false;
            for (int j = i; j < dataCopy.length - i - 1; j++) {
                comparisons++;
                if (dataCopy[j] > dataCopy[j + 1]) {
                    swaps++;
                    temp = dataCopy[j];
                    dataCopy[j] = dataCopy[j + 1];
                    dataCopy[j + 1] = temp;
                }
            }
            for (int j = dataCopy.length - 2 - i; j > i; j--) {
                comparisons++;
                if(dataCopy[j] < dataCopy[j - 1]) {
                    swaps++;
                    temp = dataCopy[j];
                    dataCopy[j] = dataCopy[j - 1];
                    dataCopy[j - 1] = temp;
                    swapped = true;
                }
            }
            if(!swapped) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();

        generalOutput("      shaker sort\n" +
                "         elapsed time = " +
                                createTimeString(endTime - startTime) + "\n" +
                "         comparisons = " +
                                countFormatter.format(comparisons) + "\n" +
                "         swaps = " + countFormatter.format(swaps) + "\n" +
                "         passes = " + countFormatter.format(passes) + "\n" +
                "         calls = 1");
    }

    // Shell's sort good gap distance
    public void shellSortGood() {
        long startTime = System.currentTimeMillis();
        int inner, outer;
        int temp;
        //find initial value of h
        int h = 1;
        while (h <= dataCopy.length / 3)
            h = h * 3 + 1; // (1, 4, 13, 40, 121, ...)

        while (h > 0) // decreasing h, until h=1
        {
            // h-sort the file
            for (outer = h; outer < dataCopy.length; outer++) {
                passes++;
                temp = data[outer];
                inner = outer;
                // one subpass (eg 0, 4, 8)
                while (inner > h - 1 && dataCopy[inner - h] >= temp) {
                    comparisons++;
                    swaps++;
                    dataCopy[inner] = dataCopy[inner - h];
                    inner -= h;
                }
                dataCopy[inner] = temp;
                swaps++;
            }
            h = (h - 1) / 3; // decrease h
        }
        long endTime = System.currentTimeMillis();
        generalOutput("      Shell's sort (good gap)\n" +
                "         elapsed time = " +
                                createTimeString(endTime - startTime) + "\n" +
                "         comparisons = " +
                                countFormatter.format(comparisons) + "\n" +
                "         swaps = " + countFormatter.format(swaps) + "\n" +
                "         passes = " + countFormatter.format(passes) + "\n" +
                "         calls = 1");
    }

    // Shell's sort my gap distance
    public void shellSortMy() {
        long startTime = System.currentTimeMillis();
        int inner, outer;
        int temp;
        //find initial value of h
        int h = 1;
        while (h <= dataCopy.length / 2)
            h = h * 4 + 1; // (1, 5, 21, 85, ...)

        while (h > 0) // decreasing h, until h=1
        {
            // h-sort the file
            for (outer = h; outer < dataCopy.length; outer++) {
                passes++;
                temp = data[outer];
                inner = outer;
                // one subpass (eg 0, 4, 8)
                while (inner > h - 1 && dataCopy[inner - h] >= temp) {
                    comparisons++;
                    swaps++;
                    dataCopy[inner] = dataCopy[inner - h];
                    inner -= h;
                }
                dataCopy[inner] = temp;
                swaps++;
            }
            h = (h - 1) / 4; // decrease h
        }
        long endTime = System.currentTimeMillis();
        generalOutput("      Shell's sort (my gap)\n" +
                "         elapsed time = " +
                                createTimeString(endTime - startTime) + "\n" +
                "         comparisons = " +
                                countFormatter.format(comparisons) + "\n" +
                "         swaps = " + countFormatter.format(swaps) + "\n" +
                "         passes = " + countFormatter.format(passes) + "\n" +
                "         calls = 1");
    }

    public StringBuffer createTimeString(long milliseconds) {
        // create a string with approximate elapsed time, converted from
        //  milliseconds
        // System.out.println("createTimeString ...");
        StringBuffer timeString = new StringBuffer("");
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        if (seconds == 0 && minutes == 0 && hours == 0) {
            timeString = timeString.append("less than one second");
        } else if (minutes == 0 && hours == 0) {
            timeString = timeString.append(seconds + " second");
            if (seconds != 1)
                timeString = timeString.append("s");
        } else if (hours == 0) {
            timeString = timeString.append(minutes + " minute");
            if (minutes != 1)
                timeString = timeString.append("s");
            timeString.append(", ");
            if (seconds != 0) {
                timeString = timeString.append(seconds + " second");
                if (seconds != 1)
                    timeString = timeString.append("s");
            }
        } else {  // >= 1 hour
            timeString = timeString.append(hours + " hour");
            if (hours != 1)
                timeString = timeString.append("s");
            if (minutes != 0) {
                timeString = timeString.append(minutes + " + minute");
                if (minutes != 1)
                    timeString = timeString.append("s");
            }
        }
        return timeString;
    }

    public void generateData() {
        // System.out.println("generateData ...);
        data = new int[n];
        dataCopy = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = (int) (Math.random() * MAX_VALUE + 1);
            dataCopy[i] = data[i];
        }
    }

    public void restoreData() {
        // fill copy array with original unsorted data,
        // to be sorted by next sorting algorithm
        // System.out.println("restoreData ...");
        for (int i = 0; i < n; i++) {
            dataCopy[i] = data[i];
        }
    }

    public boolean isSorted() {
        // check data order - is it sorted ascending?
        // System.out.println("isSorted ...");
        boolean sorted = true;
        int i = 0;
        while (sorted && i < n - 1) {
            if (dataCopy[i] > dataCopy[i + 1])
                sorted = false;
            else
                i++;
        }
        return sorted;
    }  // isSorted

    public void reset() {
        // set counters back to zero, create new data and copy
        // System.out.println("reset ...");
        comparisons = swaps = shifts = passes = calls = 0;
    }

    public void printList(String label, int[] list) {
        // System.out.println("printList ...");
        if (n <= PRINT_MAX) {
            generalOutput(label + " ");
            for (int i = 0; i < n; i++) {
                generalOutput(list[i] + "  ");
                if (i % 10 == 9)
                    generalOutput("");
            }
        }
    }

    public void generalOutput(String string) {
        System.out.println(string);
        outputFile.println(string);
    }
}  // Sorts