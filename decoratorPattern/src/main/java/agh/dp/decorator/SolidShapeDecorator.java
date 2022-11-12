package agh.dp.decorator;

import java.awt.*;

public class SolidShapeDecorator extends ShapeDecorator{
    public SolidShapeDecorator(Shape shape) {
        super(shape);
    }

    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2f));
        shape.draw(g2);
    }
}
