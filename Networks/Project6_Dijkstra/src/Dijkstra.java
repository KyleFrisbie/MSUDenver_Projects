import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Kyle L Frisbie
 * @date 7.21.15
 *
 * A program to build up the forwarding table at source router V1 using the
 * OldIterations.OldIterations.Dijkstra’s algorithm.
 */

public class Dijkstra {

    private Scanner kb = new Scanner(System.in);
    private ArrayList<Node> nodeCollection;
    private int nRouters;
    private Scanner inputFile;
    private PathAndPred[] pathAndPreds;

    /**
     * Special data type to hold specific information regarding each node.
     */
    public class Node implements Comparable<Node> {
        public final String name;
        public final int id;
        public ArrayList<Edge> edges = new ArrayList<>();   // Y'
        public double minDistance = Double.POSITIVE_INFINITY;   // D(i)
        public Node previous;   // p(i)

        @Override
        public int compareTo(Node o) {
            return Double.compare(minDistance, o.minDistance);
        }

        public Node(String nodeName, int nodeID) {
            name = nodeName;
            id = nodeID;
        }
    }

    /**
     * Special data type to hold specific information regarding each link.
     */
    public class Edge {
        public final Node node;
        public final double length;

        public Edge(Node nodeIn, double lengthIn) {
            node = nodeIn;
            length = lengthIn;
        }
    }

    /**
     * Special data type to hold specific information for D(i) and p(i) for
     * each target node.
     */
    public class PathAndPred {
        public String nodeName;
        public double dI;
        public String predecessor;

        public PathAndPred(String name, double currentVal, String pred) {
            nodeName = name;
            dI = currentVal;
            predecessor = pred;
        }
    }

    /**
     * Display a prompt message to ask the user to input the total number of
     * routers, n, in the network. Validate n to make sure that it is greater
     * than or equal to 2.
     */
    private void promptForNodeN() {
        do {
            System.out.print("Input the total number " +
                    "of routers in the network (must be > 1): ");
            nRouters = Integer.parseInt(kb.nextLine());
        } while (nRouters < 2);
        nodeCollection = new ArrayList<>(nRouters * 2);
        pathAndPreds = new PathAndPred[nRouters + 1];
    }


    /**
     * Validate values in input file such that the first and the second numbers
     * in each row are between 1 and n, the third number needs to be validated
     * to be positive. Record the cost information in the Cost matrix.
     *
     * @return true if file is valid
     */
    private boolean scanInputFile() {
        String lineIn;
        String[] tokens;
        int[] numTokens;
        int lineNumber = 1;
        while (inputFile.hasNext()) {
            lineIn = inputFile.nextLine();
            tokens = lineIn.split("\\s+");

            // convert tokens to int to be checked for validity
            numTokens = new int[]{
                    Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2])
            };

            // check tokens from input file for valid number ranges
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

            // add tokens to D(i)/p(i) table
            if (Integer.parseInt(tokens[0]) == 1) {
                int index = Integer.parseInt(tokens[1]);
                pathAndPreds[index] = new PathAndPred("V" + tokens[1],
                        Integer.parseInt(tokens[2]), "V" + tokens[0]);
            } else if (Integer.parseInt(tokens[1]) == 1) {
                int index = Integer.parseInt(tokens[0]);
                pathAndPreds[index] = new PathAndPred("V" + tokens[0],
                        Integer.parseInt(tokens[2]), "V" + tokens[1]);
            }

            // add a new node to the collection if it doesn't already exist
            Node primary = null;
            Node secondary = null;
            for (Node n : nodeCollection) {
                if (n.id == Integer.parseInt(tokens[0])) {
                    primary = n;
                } else if (n.id == Integer.parseInt(tokens[1])) {
                    secondary = n;
                }
            }

            if (primary == null) {
                primary = new Node("V" + tokens[0],
                        Integer.parseInt(tokens[0]));
                nodeCollection.add(primary);
            }

            if (secondary == null) {
                secondary = new Node("V" + tokens[1],
                        Integer.parseInt(tokens[1]));
                nodeCollection.add(secondary);
            }

            primary.edges.add(new Edge(secondary,
                    Double.parseDouble(tokens[2])));

            secondary.edges.add(new Edge(primary,
                    Double.parseDouble(tokens[2])));

            lineNumber++;
        }
        return true;
    }

    /**
     * Display a prompt message to ask the user to input the name of a txt file
     * that contains costs of all links. Each line in this file provides the
     * cost between a pair of routers as below, where tab (\t) is used to
     * separate the numbers in each line. This txt file can locate in the
     * same directory where this program runs such that a path is not needed.
     * Display a message saying that in which row the first
     * invalid number is detected, close the txt file, and KEEP asking for
     * the name of the cost input file until all numbers are checked to be
     * valid.
     */
    private void promptForInputFile() {
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

    /**
     * Method to display intermediate values within execution of OldIterations.OldIterations.Dijkstra's
     * algorithm.
     *
     * @param nPrime
     * @param yPrime
     */
    private void arrayPrintOut(ArrayList<Node> nPrime, ArrayList<String> yPrime) {
        // Print N'
        System.out.println("__________________________________________");
        System.out.print("N': [ ");
        for (Node n : nPrime) {
            if (nPrime.indexOf(n) != nPrime.size() - 1) {
                System.out.print(n.name + ", ");
            } else {
                System.out.println(n.name + " ]");
            }
        }

        // Print Y'
        if (yPrime.size() == 0) {
            System.out.println("Y': null");
        } else {
            System.out.print("Y': [ ");
            for (String y : yPrime) {
                if (yPrime.indexOf(y) != yPrime.size() - 1) {
                    System.out.print(y + ", ");
                } else {
                    System.out.println(y + " ]");
                }
            }
        }

        // Print D(i)/p(i) table
        System.out.println("------------------------------------------");

        System.out.print("Node:" + "\t" + "D(i)[]:" + "\t" + "p(i)[]: ");
        System.out.println();
        for (int i = 1; i < pathAndPreds.length; i++) {
            PathAndPred current = pathAndPreds[i];
            if (current == null) {
                if(i == 1) {
                    System.out.println("V" + i + "\t\t0\t\t" + "V" + i);
                } else {
                    System.out.println("V" + i + "\t\tinf\t\t-");
                }
            } else {
                System.out.println(current.nodeName + "\t\t" + current.dI + "\t\t" + current.predecessor);
            }
        }

        System.out.println("__________________________________________");
    }

    /**
     * Implement the OldIterations.OldIterations.Dijkstra's algorithm to build up the shortest-path tree
     * rooted at source router V1. As the intermediate results, at the end of
     * Initialization and each iteration of the Loop, display
     * <p>
     * The set N': this is stored in the Node.name value for each Node in
     * nodeCollection.
     * The set Y' : this is stored in Node.edges which is an array list of each
     * edge associated with that node.
     * D(i) for each i between 2 and n: stored as Node.minDistance
     * p(i) for each i between 2 and n: Node.previous value for each Node in
     * nodeCollection.
     */
    private void walkPaths() {
        // get the source router (node)
        Node n = nodeCollection.get(0);
        n.previous = n;
        n.minDistance = 0;

        // create lists to store values for N' and Y'
        ArrayList<Node> nPrime = new ArrayList<>();
        ArrayList<String> yPrime = new ArrayList<>();

        // new list of nodes to hold nodes that need to be processed
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(n);

        // continue executing while there are still nodes to run the algorithm
        // on
        while (!nodeList.isEmpty()) {
            nodeList.stream().sorted(Node::compareTo);
            Node x = nodeList.get(0);

            nodeList.remove(x);

            if (!nPrime.contains(x)) {
                nPrime.add(x);
            }

            // print-out after initialization
            arrayPrintOut(nPrime, yPrime);

            // continue processing while there are still unprocessed links on
            // node x
            while (x.edges.size() > 0) {
                Edge e = x.edges.get(0);
                Edge min = e;

                // find the smallest edge to process first
                for (Edge edge : x.edges) {
                    if (edge.length < e.length) {
                        min = edge;
                    }
                }
                x.edges.remove(min);

                Node next = min.node;
                if (!nPrime.contains(min.node)) {
                    nPrime.add(min.node);
                }
                double length = min.length;


                // OldIterations.OldIterations.Dijkstra's Algorithm:
                // D(k) + c(k, i)
                double testLengthDistance = x.minDistance + length;
                // D(k) + c(k, i) < D(i)
                if (testLengthDistance < next.minDistance) {
                    nodeList.remove(next);
                    // D(i) = D(k) + c(k, i)
                    next.minDistance = testLengthDistance;
                    // p(i) = k
                    next.previous = x;
                    pathAndPreds[next.id] = new PathAndPred(next.name,
                            testLengthDistance, x.name);
                    if (!yPrime.contains("(" + x.name + ", " + min.node.name + ")")) {
                        yPrime.add("(" + x.name + ", " + min.node.name + ")");
                    }
                    nodeList.add(next);

                    // print-out after loop
                    arrayPrintOut(nPrime, yPrime);
                }
            }
        }
    }

    /**
     * Use the shortest-path tree resulted from the OldIterations.OldIterations.Dijkstra's algorithm to
     * build up the forwarding table for router V1. Display the forwarding
     * table in the following format:
     * <p>
     * Destination Link
     * V2 (V1, ...)
     * V3 (V1, ...)
     * ...
     * Vn (V1, ...)
     */
    private String[] forwardingTable() {
        String[] links = new String[nRouters + 1];
        // find adjacent node to source router with resulting shortest path to
        // target node
        for (Node i : nodeCollection) {
            Node j = i;
            while (j.previous != nodeCollection.get(0)) {
                j = j.previous;
            }
            links[i.id] = j.name;
        }
        return links;
    }

    public static void main(String[] args) {
        Dijkstra driver = new Dijkstra();
        driver.promptForNodeN();
        driver.promptForInputFile();

        driver.walkPaths();


        // print out shortest-path tree
        System.out.println("Shortest Path Tree:");
        System.out.println("Destination:\tLink:");

        String[] linkPath = driver.forwardingTable();

        for (int i = 1; i < linkPath.length - 1; i++) {
            System.out.println(driver.nodeCollection.get(i).name + "\t\t\t" +
                    "(" + driver.nodeCollection.get(0).name +
                    ", " + linkPath[i + 1] + ")");
        }
    }
}