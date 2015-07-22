package OldIterations.DijkstraSep;

/**
 * Created by Kyle on 7/21/2015.
 */
public class Link {
    public final Node node;
    public final double length;
    public Link(Node nodeIn, double lengthIn) {
        node = nodeIn;
        length = lengthIn;
    }
}
