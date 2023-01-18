package Nodes;

public class OperationNode implements Node {

    public Node in1, in2;
    public Double scalar;
    OpType op;

    public OperationNode(Node in1, Node in2, OpType op) throws Exception {
        if (op == OpType.INV)
            throw new Exception("Unsupported operation");

        this.in1 = in1;
        this.in2 = in2;
        this.op = op;
        this.scalar = null;
    }

    public OperationNode(Node in1, double scalar, OpType op) throws Exception {
        if (op != OpType.SCALAR_MUL)
            throw new Exception("Unsupported operation");

        this.in1 = in1;
        this.in2 = null;
        this.op = op;
        this.scalar = scalar;
    }

    public OperationNode(Node in1, OpType op) throws Exception {
        if (op != OpType.INV)
            throw new Exception("Unsupported operation");

        this.in1 = in1;
        this.in2 = null;
        this.op = op;
        this.scalar = null;
    }

    @Override
    public double[][] accept(IVisitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
