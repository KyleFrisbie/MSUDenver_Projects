package Dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Kyle on 7/21/2015.
 */
public class Main {
    private int nRouters;
    private Scanner kb = new Scanner(System.in);
    private Scanner inputFile;
    private double[][] costMatrix;
    ArrayList<Node> nodes;

    private void inputRouters() {
        do {
            System.out.print("Input the total number " +
                    "of routers in the network (must be > 1): ");
            nRouters = Integer.parseInt(kb.nextLine());
        } while (nRouters < 2);
        costMatrix = new double[nRouters][nRouters];
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
            costMatrix[numTokens[0] - 1][numTokens[1] - 1] = numTokens[2];
            costMatrix[numTokens[1] - 1][numTokens[0] - 1] = numTokens[2];

            nodes = new ArrayList<>();
            for (int i = 0; i < nRouters; i++) {
                nodes.add(new Node(i));
            }

            ArrayList<Link> neighbors;
            Node n;
            for (int i = 0; i < nRouters; i++) {
                neighbors = new ArrayList<>();
                n = nodes.get(i);
                for (int j = 0; j < nRouters; j++) {
                    if (i != j) {
                        if (costMatrix[i][j] == 0) {
                            costMatrix[i][j] = Double.POSITIVE_INFINITY;
                            neighbors.add(new Link(nodes.get(j), Double.POSITIVE_INFINITY));
                        } else {
                            neighbors.add(new Link(nodes.get(j), costMatrix[i][j]));
                        }
                    } else {
                        neighbors.add(new Link(nodes.get(j), costMatrix[i][j]));
                    }
                }
                Link[] links = new Link[neighbors.size()];
                for(int k = 0; k < neighbors.size(); k++)  {
                    links[i] = neighbors.get(k);
                }
                n.links = links;
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

    public static void walkPaths(Node n) {
        n.minimumDistance = 0;

        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();
        nodeQueue.add(n);

        while(!nodeQueue.isEmpty()) {
            Node x = nodeQueue.poll();

            for (Link l : x.links) {
                Node next = l.node;
                double length = l.length;

                double testLengthDistance = x.minimumDistance + length;
                if(testLengthDistance < next.minimumDistance) {
                    nodeQueue.remove(next);
                    next.minimumDistance = testLengthDistance;
                    next.previous = x;
                    nodeQueue.add(next);
                }
            }
        }
    }

    public static List<Node> getShortestPath(Node n) {
        List<Node> bestPath = new ArrayList<>();
        for(Node node = n; node != null; node = node.previous) {
            bestPath.add(node);
        }

        Collections.reverse(bestPath);

        return bestPath;
    }

    public static void main(String[] args) {
        Main driver = new Main();
        driver.inputRouters();
        driver.inputFileName();

        for (Node n : driver.nodes) {
            walkPaths(n);
        }

        driver.getShortestPath(driver.nodes.get(0));
    }
}
