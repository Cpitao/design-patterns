package agh.dp.decorator;

import java.awt.*;

public class DottedShapeDecorator extends ShapeDecorator{
    public DottedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics2D g2) {
        float[] p = {2f, 2f};
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                2f, p, 2f));
        shape.draw(g2);
    }
}
