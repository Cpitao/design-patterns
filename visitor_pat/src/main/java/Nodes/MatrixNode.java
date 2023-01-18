package Nodes;

public class MatrixNode implements Node {

    double[][] matrix;

    public MatrixNode(double[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public double[][] accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
