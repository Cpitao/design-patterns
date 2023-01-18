package Nodes;

import java.util.LinkedList;

public class Graph {

    public LinkedList<Node> nodes = new LinkedList<Node>();

    public Node addMatrix(double[][] in) {
        Node n = new MatrixNode(in);
        nodes.add(n);
        return n;
    }

    public Node addOperation(Node in1, Node in2, OpType op) throws Exception {
        Node n = new OperationNode(in1, in2, op);
        nodes.add(n);
        return n;
    }

    public Node addOperation(Node in1, OpType op) throws Exception {
        Node n = new OperationNode(in1, op);
        nodes.add(n);
        return n;
    }

    public Node addOperation(Node in1, double scalar, OpType op) throws Exception {
        Node n = new OperationNode(in1, scalar, op);
        nodes.add(n);
        return n;
    }
}
