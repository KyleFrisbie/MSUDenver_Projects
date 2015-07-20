import javax.sound.midi.Soundbank;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyle on 7/20/2015.
 */
public class Dijkstra {
    private int nodes;
    private Scanner kb = new Scanner(System.in);
    private Scanner inputFile;
    private int[][] costMatrix;
    int[] D, P;

    /**
     * Display a prompt message to ask the user to input the total number of
     * routers, n, in the network. Validate n to make sure that it is greater
     * than or equal to 2.
     */
    private void inputRouters() {
        boolean notInteger = false;
        do {
            try {
                notInteger = false;
                System.out.print("Input the total number of routers, n, " +
                        "in the network:");
                nodes = kb.nextInt();
            } catch (Exception e) {
                System.out.println("Enter only integers, try again.");
                notInteger = true;
            }
        } while ((nodes < 2) || notInteger);
    }

    private boolean scanInputFile() {
        ArrayList<Integer[]> costArray = new ArrayList<>();
        int lineNumber = 0;
        Integer[] tokens = new Integer[3];
        while (inputFile.hasNext()) {
            lineNumber++;
            tokens[0] = inputFile.nextInt();
            tokens[1] = inputFile.nextInt();
            tokens[2] = inputFile.nextInt();
            costArray.add(tokens);
            if (tokens[0] < 1 || tokens[0] > nodes) {
                System.out.println("Invalid number located at line: " +
                        lineNumber + " , cloumn: 1");
                return true;
            } else if (tokens[1] < 1 || tokens[1] > nodes) {
                System.out.println("Invalid number located at line: " +
                        lineNumber + " , cloumn: 2");
                return true;
            } else if (tokens[2] < 0) {
                System.out.println("Invalid number located at line: " +
                        lineNumber + " , cloumn: 3");
                return true;
            }
        }
        costMatrix = new int[nodes][nodes];
        for (int i = 0; i < costArray.size(); i++) {
            Integer[] thisLine = costArray.get(i);
            costMatrix[thisLine[0]][thisLine[1]] = thisLine[2];
        }
        return false;
    }

    /**
     * Display a prompt message to ask the user to input the name of a txt file
     * that contains costs of all links. Each line in this file provides the
     * cost between a pair of routers as below, where tab (‘\t’) is used to
     * separate the numbers in each line.
     */
    private void inputTextFile() {
        boolean hasError;
        do {
            hasError = false;
            System.out.print("Input the name of a txt file that contains " +
                    "costs of all links");
            try {
                File inputDataFile = new File(kb.nextLine());
                inputFile = new Scanner(inputDataFile);
                hasError = scanInputFile();
            } catch (Exception e) {

            }
        } while (hasError);
    }

    /**
     * Implement the Dijkstra’s algorithm to build up the shortest-path tree
     * rooted at source router V1. As the intermediate results, at the end of
     * Initialization and each iteration of the Loop, display
     * The set N’
     * The set Y’
     * D(i) for each i between 2 and n
     * p(i) for each i between 2 and n
     */
    private void buildTree() {
        ArrayList<Integer> N = new ArrayList<>();
        ArrayList<Integer[]> Y = new ArrayList<>();
        D = new int[nodes];
        P = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            D[i] = costMatrix[i][0];
            P[i] = 0;
            for (int j = 1; j < nodes; j++) {
                if (D[i] < costMatrix[i][j]) {
                    D[i] = costMatrix[i][j];
                    P[i] = j;
                }
            }
        }
//        for (int k = 1; k < nodes; k++) {
        N.add(0);
        int k = 1;
        int min = D[k];
        for (int i = 2; i < nodes; i++) {
            if (D[i] < min && !N.contains(i)) {
                k = i;
                min = D[k];
            }
        }

        N.add(k);

        for (int i = 1; i < nodes; i++) {
            System.out.println("End of loop initialization.");
            System.out.println("N': {" + N + "}");
            System.out.println("Y': {" + Y + "}");
            System.out.println("D(V" + (i + 1) + "): " + D[i]);
            System.out.println("p(V" + (i + 1) + "): " + P[i]);
            if (D[k] + costMatrix[k][i] < D[i]) {
                Integer[] tempY = new Integer[2];
                tempY[0] = i;
                tempY[1] = k;
                Y.add(tempY);
                D[i] = D[k] + costMatrix[k][i];
                P[i] = k;

                System.out.println("End of loop iteration: " + (i + 1));
                System.out.println("N': {" + N + "}");
                System.out.println("Y': {" + Y + "}");
                System.out.println("D(V" + (i + 1) + "): " + D[i]);
                System.out.println("p(V" + (i + 1) + "): " + P[i]);
            }
        }
//        }
    }

    /**
     * Use the shortest-path tree resulted from the Dijsktra’s algorithm to
     * build up the forwarding table for router V1. Display the forwarding
     * table in the following format:
     * Destination Link
     * V2 (V1, …)
     * V3 (V1, …)
     * …
     * Vn (V1, …)
     */
    private void buildForwardingTable() {

    }

}
