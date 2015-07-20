import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyle on 7/20/2015.
 */
public class ForwardTable {
    private int nRouters;
    private Scanner kb = new Scanner(System.in);
    private Scanner inputFile;
    private int[][] costMatrix;
    ArrayList<Integer> nPrime = new ArrayList<>();
    ArrayList<Integer[]> yPrime = new ArrayList<>();
    int[] D, P;

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
        costMatrix = new int[nRouters][nRouters];
    }

    private boolean scanInputFile() {
        String nextLine;
        String[] tokens;
        int[] numTokens;
        int lineNumber = 0;
        while(inputFile.hasNext()) {
            lineNumber++;
            nextLine = inputFile.nextLine();
            tokens = nextLine.split("\\s+");
            numTokens = new int[] {
                    Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2])
            };

            if(((numTokens[0] < 1) && (numTokens[0] < nRouters)) ||
                    ((numTokens[1] < 1) && (numTokens[1] < nRouters))) {
                System.out.println("Invalid router number at line " +
                        lineNumber + ".");
                return false;
            } else if (numTokens[2] < 0) {
                System.out.println("Invalid link cost number at line " +
                        lineNumber + ".");
                return false;
            }
            costMatrix[numTokens[0] - 1][numTokens[1] - 1] = numTokens[2];
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
        } while(!validFile);
    }

    /**
     * Implement the Dijkstra's algorithm to build up the shortest-path tree
     * rooted at source router V1. As the intermediate results, at the end of
     * Initialization and each iteration of the Loop, display
     *      The set N'
     *      The set Y'
     *      D(i) for each i between 2 and n
     *       p(i) for each i between 2 and n
     */
    private void dijsktraAlgorithm() {
        D = new int[nRouters];
        P = new int[nRouters];

        // initialize
        int u = 0;
        nPrime.add(u);
        for (int i = 0; i < nRouters; i++) {
            if(i < 2147483647) {
                D[i] = costMatrix[u][i];
                P[i] = u;
            } else {
                D[i] = 2147483647;
                P[i] = -1;
            }
        }

        // loop
        while(nPrime.size() < nRouters) {
            ArrayList<Integer> tempN = new ArrayList<>();
            for (int k = 0; k < nRouters; k++) {
                boolean notInN = true;
                for (int i = 0; i < D.length; i++) {
                    if(k == D[i]) {
                        notInN = false;
                        break;
                    }
                }
                if(notInN) {
                    tempN.add(k);
                }
            }

            int min = costMatrix[u][tempN.get(0)];
            int k = tempN.get(0);
            for (int i : tempN) {
                if(costMatrix[u][tempN.get(i)] < min) {
                    min = costMatrix[u][tempN.get(0)];
                    k = tempN.get(i);
                }
            }

            nPrime.add(k);
            yPrime.add(new Integer[] {
                    P[k],
                    k
            });

            for (int i : tempN) {
                if (D[k] + costMatrix[k][i] < D[i]) {
                    D[i] = D[k] + costMatrix[k][i];
                    P[i] = k;
                }
            }
        }
    }

    /**
     * Use the shortest-path tree resulted from the Dijsktra's algorithm to
     * build up the forwarding table for router V1. Display the forwarding
     * table in the following format:
     *      Destination Link
     *      V2 (V1, ...)
     *      V3 (V1, ...)
     *      ...
     *      Vn (V1, ...)
     */
    private void buildTable() {

    }

    private void outputMatrix() {
        for (int i = 0; i < nRouters; i++) {
            for (int j = 0; j < nRouters; j++) {
                System.out.print(costMatrix[i][j] + "\t\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ForwardTable driver = new ForwardTable();
        driver.inputRouters();
        driver.inputFileName();

        driver.outputMatrix();

        driver.dijsktraAlgorithm();

        driver.nPrime.toString();

        driver.buildTable();
    }
}
