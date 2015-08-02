import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Kyle on 7/20/2015.
 */
public class DijkstraArrayImplement {
    private int nRouters;
    private Scanner kb = new Scanner(System.in);
    private Scanner inputFile;
    private double[][] costMatrix;
    boolean[] nPrime;
    ArrayList<String> yPrime;
    double[] D;
    int[] P;

    /**
     * Display a prompt message to ask the user to input the total number of
     * routers, n, in the network. Validate n to make sure that it is greater
     * than or equal to 2.
     */
    private void inputRouters() {
        do {
            System.out.print("Input the total number " +
                    "of routers in the network (must be > 1): ");
            nRouters = Integer.parseInt(kb.nextLine());
        } while (nRouters < 2);
        costMatrix = new double[nRouters + 1][nRouters + 1];
        nPrime = new boolean[nRouters + 1];
        yPrime = new ArrayList<>();
    }

    private boolean scanInputFile() {
        String nextLine;
        String[] tokens;
        int[] numTokens;
        int lineNumber = 0;
        while (inputFile.hasNext()) {
            lineNumber++;
            nextLine = inputFile.nextLine();
            tokens = nextLine.split("\\s+");
            numTokens = new int[]{
                    Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2])
            };

            if (((numTokens[0] < 1) && (numTokens[0] < nRouters)) ||
                    ((numTokens[1] < 1) && (numTokens[1] < nRouters))) {
                System.out.println("Invalid router number at line " +
                        lineNumber + ".");
                return false;
            } else if (numTokens[2] < 0) {
                System.out.println("Invalid link cost number at line " +
                        lineNumber + ".");
                return false;
            }
            costMatrix[numTokens[0]][numTokens[1]] = numTokens[2];
            costMatrix[numTokens[1]][numTokens[0]] = numTokens[2];

            for (int i = 1; i < nRouters + 1; i++) {
                for (int j = 1; j < nRouters + 1; j++) {
                    if (i != j) {
                        if (costMatrix[i][j] == 0) {
                            costMatrix[i][j] = Double.POSITIVE_INFINITY;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Display a prompt message to ask the user to input the name of a txt file
     * that contains costs of all links. Each line in this file provides the
     * cost between a pair of routers as below, where tab (\t) is used to
     * separate the numbers in each line.
     */
    private void inputFileName() {
        boolean validFile;
        do {
            System.out.print("Input the name of a txt file " +
                    "that contains costs of all links: ");
            File inputDataFile = new File(kb.nextLine());
            try {
                inputFile = new Scanner(inputDataFile);
                validFile = scanInputFile();
            } catch (FileNotFoundException e) {
                System.out.println("Error reading input file.");
                validFile = false;
            }
        } while (!validFile);
    }

    private void outputTables(String phase) {
        System.out.println("___________________________________________________");
        System.out.println(phase + ":");
        ArrayList<String> N = new ArrayList<>();

        for (int i = 1; i < nRouters + 1; i++) {
            if (nPrime[i]) {
                N.add("V" + i);
            }
        }
        System.out.print("N': [");
        for (int i = 0; i < N.size() - 1; i++) {
            System.out.print(N.get(i) + ", ");
        }
        System.out.println(N.get(N.size() - 1) + "]");

        System.out.print("Y': [");
        for (int i = 0; i < yPrime.size() - 1; i++) {
            System.out.print(yPrime.get(i) + ", ");
        }
        if(yPrime.size() > 0) {
            System.out.print(yPrime.get(yPrime.size() - 1));
        }
        System.out.println("]");

        System.out.println("---------------------------------------------------");
        System.out.println("Node\tD(i)" + "\t" + "p[i]");
        for (int i = 2; i < nRouters + 1; i++) {
            if(D[i] == Double.POSITIVE_INFINITY) {
                System.out.println("V" + i + "\t\tinf\t\t-");
            } else {
                System.out.println("V" + i + "\t\t" + D[i] + "\t\t" + "V" + P[i]);
            }
        }
        System.out.println("___________________________________________________");
    }

    /**
     * Implement the OldIterations.OldIterations.Dijkstra's algorithm to build up the shortest-path tree
     * rooted at source router V1. As the intermediate results, at the end of
     * Initialization and each iteration of the Loop, display
     * The set N'
     * The set Y'
     * D(i) for each i between 2 and n
     * p(i) for each i between 2 and n
     */
    private void dijkstraAlgorithm() {
        D = new double[nRouters + 1];
        P = new int[nRouters + 1];

        // initialize
        nPrime[1] = true;
        for (int i = 2; i < nRouters + 1; i++) {
            if (costMatrix[1][i] < Double.POSITIVE_INFINITY) {
                D[i] = costMatrix[1][i];
                P[i] = 1;
            } else {
                D[i] = Double.POSITIVE_INFINITY;
                P[i] = -1;
            }
        }

        outputTables("Initialization");

        List<Integer> unusedNodes = new ArrayList<>((nRouters + 1) * 2);
        for (int i = 2; i < nRouters + 1; i++) {
            unusedNodes.add(i);
        }

        double min;
        int loopCount = 0;

        // loop
        while (unusedNodes.size() > 0) {
            loopCount++;
            int pos = unusedNodes.get(0);
            min = D[pos];

            // find the smallest edge to process first
            for (int i = 2; i < nRouters + 1; i++) {
                if (!nPrime[i] && (D[i] <= min)) {
                    min = D[i];
                    pos = i;
                }
            }

            int k = pos;
            unusedNodes.remove(unusedNodes.indexOf(pos));

            // add node to N' and edge to Y'
            nPrime[k] = true;
            yPrime.add(new String("(V" + P[k] + ", V" + k + ")"));

            // Dijkstra's Algorithm:
            for (int i = 2; i < nRouters + 1; i++) {
                if (nPrime[i] || costMatrix[k][i] == Double.POSITIVE_INFINITY) {
                    continue;
                }
                // D(k) + c(k, i) < D(i)
                if (D[k] + costMatrix[k][i] < D[i]) {
                    // D(i) = D(k) + c(k, i)
                    D[i] = D[k] + costMatrix[k][i];
                    // p(i) = k
                    P[i] = k;
                }
            }

            outputTables("Loop " + loopCount);
        }
    }

    /**
     * Use the shortest-path tree resulted from the Dijsktra's algorithm to
     * build up the forwarding table for router V1. Display the forwarding
     * table in the following format:
     * Destination Link
     * V2 (V1, ...)
     * V3 (V1, ...)
     * ...
     * Vn (V1, ...)
     */
    private String[] buildTable(double source) {
        String[] forwardTable = new String[nRouters + 1];
        for (int i = 2; i < D.length; i++) {
            double j = i;
            while (P[(int) j] != (int) source) {
                j = P[(int) j];
            }
            forwardTable[i] = new String("(" + source + ", " + j + ")");
        }
        return forwardTable;
    }

    private void outputMatrix() {
        for (int i = 1; i < nRouters + 1; i++) {
            for (int j = 1; j < nRouters + 1; j++) {
                System.out.print(costMatrix[i][j] + "\t\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DijkstraArrayImplement driver = new DijkstraArrayImplement();
        driver.inputRouters();
        driver.inputFileName();

//        driver.outputMatrix();

        driver.dijkstraAlgorithm();

        String[] table = driver.buildTable(1);

        // output forwarding table
        System.out.println("Forwarding table for V1: ");
        System.out.println("Dest:\tLink:");
        for (int i = 2; i < table.length; i++) {
            System.out.println("V" + i + "\t\t" + table[i]);
        }
    }
}
