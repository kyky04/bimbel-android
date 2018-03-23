package id.bimbel.model;

/**
 * Created by pragmadev on 3/24/18.
 */

public class Edge {
    public final double cost;
    public final Node target;
    public Edge(Node targetNode, double costVal) {
        target = targetNode;
        cost = costVal;
    }
}