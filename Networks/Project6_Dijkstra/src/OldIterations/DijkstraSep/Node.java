package OldIterations.DijkstraSep;

/**
 * Created by Kyle on 7/21/2015.
 */
public class Node implements Comparable<Node>{
    public final int name;
    public Link[] links;
    public double minimumDistance = Double.POSITIVE_INFINITY;
    public Node previous;
    public Node(int nameIn) {
        name = nameIn;
    }


    @Override
    public int compareTo(Node o) {
        return Double.compare(minimumDistance, o.minimumDistance);
    }
}
