package agh.dp.decorator;

import java.awt.*;

public class DashedShapeDecorator extends ShapeDecorator {
    public DashedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics2D g2) {
        float[] p = {10f, 4f};
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                2f, p, 2f));
        shape.draw(g2);
    }
}
