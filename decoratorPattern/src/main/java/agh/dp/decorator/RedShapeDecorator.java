package agh.dp.decorator;

import java.awt.*;

public class RedShapeDecorator extends ShapeDecorator{
    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        shape.draw(g2);
    }
}
