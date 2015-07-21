package DijkstraPack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Kyle on 7/21/2015.
 */
public class Dijkstra {

    private Scanner kb = new Scanner(System.in);
    private ArrayList<Node> nodeCollection;
    private int nRouters;
    private Scanner inputFile;


    public class Node implements Comparable<Node> {
        public final String name;
        public final int id;
        public ArrayList<Edge> edges = new ArrayList<>();
        public double minDistance = Double.POSITIVE_INFINITY;
        public Node previous;

        @Override
        public int compareTo(Node o) {
            return Double.compare(minDistance, o.minDistance);
        }

        public Node(String nodeName, int nodeID) {
            name = nodeName;
            id = nodeID;
        }
    }

    public class Edge {
        public final Node node;
        public final double length;

        public Edge(Node nodeIn, double lengthIn) {
            node = nodeIn;
            length = lengthIn;
        }
    }

    private void promptForNodeN() {
        do {
            System.out.print("Input the total number " +
                    "of routers in the network (must be > 1): ");
            nRouters = Integer.parseInt(kb.nextLine());
        } while (nRouters < 2);
        nodeCollection = new ArrayList<>(nRouters * 2);
    }

    private boolean scanInputFile() {
        String lineIn;
        String[] tokens;
        double[] numTokens;
        int lineNumber = 1;
        while (inputFile.hasNext()) {
            lineIn = inputFile.nextLine();
            tokens = lineIn.split("\\s+");

            numTokens = new double[]{
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

            //todo: need to add this edge to secondary node edge[]?
            secondary.edges.add(new Edge(primary,
                    Double.parseDouble(tokens[2])));

            lineNumber++;
        }
        return true;
    }

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

    private void walkPaths() {
        Node n = nodeCollection.get(0);
        n.minDistance = 0;

        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();
        nodeQueue.add(n);

        while (!nodeQueue.isEmpty()) {
            Node x = nodeQueue.poll();

            for (Edge e : x.edges) {
                Node next = e.node;
                double length = e.length;

                double testLengthDistance = x.minDistance + length;
                if (testLengthDistance < next.minDistance) {
                    nodeQueue.remove(next);
                    next.minDistance = testLengthDistance;
                    next.previous = x;
                    nodeQueue.add(next);
                }
            }
        }
    }

    private List<Node> getShortestPath(Node n) {
        List<Node> bestPath = new ArrayList<>();
        for (Node node = n; node != null; node = node.previous) {
            bestPath.add(node);
        }

        Collections.reverse(bestPath);

        return bestPath;
    }

    public static void main(String[] args) {
        Dijkstra driver = new Dijkstra();
        driver.promptForNodeN();
        driver.promptForInputFile();

        for (Node n : driver.nodeCollection) {
            for (Edge e : n.edges) {
                System.out.println(n.name + "\t" + e.node.name + "\t" + e.length);
            }
        }

        System.out.println("___________________________________________");

        driver.walkPaths();

        for (Node n : driver.nodeCollection) {
            for (Edge e : n.edges) {
                System.out.println(n.name + "\t" + e.node.name + "\t" + e.length);
            }
        }

        System.out.println("___________________________________________");


        for (Node n : driver.nodeCollection) {

            List<Node> bestPath = driver.getShortestPath(n);
            try {
                System.out.println(n.name + "\t" + n.previous.name + "\t" + n.minDistance);
            } catch (Exception e) {
                System.out.println("Exception.");
            }

        }
    }
}