package Nodes;

public interface Node {

    double[][] accept(IVisitor visitor) throws Exception;
}
