import Nodes.Graph;
import Nodes.Node;
import Nodes.OpType;
import Nodes.Visitor;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        // HOW TO USE:
        // 1) Add matrices to Graph as MatrixNode, save references to them to use them later
        // 2) Add operations to Graph as OperationNode, save references to them to get their results later
        // or use them further
        // 3) Call accept on the node you want to evaluate to get its value (returned as double[][])
        // with IVisitor as argument
        // Note: To add operation with matrix, MatrixNode must be added earlier. Scalar double values
        // can be used implicitly
        // Note2: Each call to accept will evaluate all the necessary operations again

//        example1();
        example2();
    }

    private static void example1() throws Exception {
        Graph graph = new Graph();
        double[][] a = {
                {1, 3, 3},
                {7, 4, 3},
                {1, 8, 9}
        };

        double[][] b = {
                {2, 3, 3},
                {1, 7, 7},
                {4, 1, 2}
        };

        Node aNode = graph.addMatrix(a);
        Node bNode = graph.addMatrix(b);

        Node abSum = graph.addOperation(aNode, bNode, OpType.ADD);

        Visitor visitor = new Visitor();

        System.out.println("a + b = " + Arrays.deepToString(abSum.accept(visitor)));
        System.out.println("a = " + Arrays.deepToString(aNode.accept(visitor)));

        Node abSum_a_Multiply = graph.addOperation(abSum, aNode, OpType.DOT_MUL);
        System.out.println("(a + b) * a = " + Arrays.deepToString(abSum_a_Multiply.accept(visitor)));

        Node bInv = graph.addOperation(bNode, OpType.INV);
        System.out.println("b = " + Arrays.deepToString(bNode.accept(visitor)));
        System.out.println("b^-1 = " + Arrays.deepToString(bInv.accept(visitor)));
    }

    private static void example2() throws Exception {
        Graph graph = new Graph();
        double[][] a = {
                {1, 3},
                {7, 4},
                {1, 8},
                {2, 3}
        };

        double[][] b = {
                {2, 3, 3, 3, 2},
                {1, 7, 7, 7, 1}
        };

        double[][] c = {
                {2, 3, 3, 3, 2},
                {1, 7, 7, 7, 1},
                {2, 3, 3, 3, 2},
                {1, 7, 7, 7, 1}
        };

        Node aNode = graph.addMatrix(a);
        Node bNode = graph.addMatrix(b);
        Node cNode = graph.addMatrix(c);
        Node abMultiply = graph.addOperation(aNode, bNode, OpType.DOT_MUL);
        Node c_abMultiply_Sum = graph.addOperation(cNode, abMultiply, OpType.ADD);
        Node c_times_2 = graph.addOperation(cNode, 2, OpType.SCALAR_MUL);
        Node c_abMultiply_Sum_2c_sub = graph.addOperation(c_abMultiply_Sum, c_times_2, OpType.SUB);

        Visitor visitor = new Visitor();
        System.out.println("c + a * b - 2 * c = " +
                Arrays.deepToString(c_abMultiply_Sum_2c_sub.accept(visitor)));
        System.out.println("c + a * b = " +
                Arrays.deepToString(c_abMultiply_Sum.accept(visitor)));

    }
}
