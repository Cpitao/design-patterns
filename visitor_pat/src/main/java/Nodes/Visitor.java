package Nodes;


import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Visitor implements IVisitor {

    @Override
    public double[][] visit(MatrixNode node) {
        double[][] copy = new double[node.matrix.length][];
        for (int i=0; i < node.matrix.length; i++)
            copy[i] = node.matrix[i].clone();
        return copy;
    }

    @Override
    public double[][] visit(OperationNode node) throws Exception {
        if (node.scalar != null) {
            double[][] arr = node.in1.accept(this).clone();
            for (int i=0; i < arr.length; i++)
                for (int j=0; j < arr[0].length; j++)
                    arr[i][j] *= node.scalar;
            return arr;
        }
        if (node.in1 != null && node.in2 != null) {
            double[][] arr1 = node.in1.accept(this);
            double[][] arr2 = node.in2.accept(this);

            switch(node.op) {
                case ADD -> {
                    if (arr1.length != arr2.length || arr1[0].length != arr2[0].length)
                        throw new Exception("Invalid matrix shapes for ADD operation");

                    for (int i=0; i < arr1.length; i++)
                        for (int j=0; j < arr1[0].length; j++)
                            arr1[i][j] += arr2[i][j];
                    return arr1;
                }
                case SUB -> {
                    if (arr1.length != arr2.length || arr1[0].length != arr2[0].length)
                        throw new Exception("Invalid matrix shapes for ADD operation");

                    for (int i=0; i < arr1.length; i++)
                        for (int j=0; j < arr1[0].length; j++)
                            arr1[i][j] -= arr2[i][j];
                    return arr1;
                }
                case DOT_MUL -> {
                    if (arr1[0].length != arr2.length)
                        throw new Exception("Invalid matrix shapes for DOT_MUL operation");

                    double[][] result = new double[arr1.length][arr2[0].length];
                    for (int i=0; i < arr1.length; i++) {
                        for (int j = 0; j < arr2[0].length; j++) {
                            double tmp = 0;
                            for (int k = 0; k < arr1[0].length; k++)
                                tmp += arr1[i][k] * arr2[k][j];
                            result[i][j] = tmp;
                        }
                    }

                    return result;
                }
                case SCALAR_MUL, INV -> {
                    throw new Exception("Invalid operation");
                }
            }
        }
        if (node.in1 != null && node.op == OpType.INV) {
            double[][] in = node.in1.accept(this);
            RealMatrix m = MatrixUtils.createRealMatrix(in);
            RealMatrix inverse = MatrixUtils.inverse(m);
            return inverse.getData();
        }
        // this case should not happen
        throw new Exception("Some error ocurred");
    }
}
