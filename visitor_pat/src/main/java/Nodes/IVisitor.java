package Nodes;

public interface IVisitor {

    double[][] visit(MatrixNode node);

    double[][] visit(OperationNode node) throws Exception;
}
