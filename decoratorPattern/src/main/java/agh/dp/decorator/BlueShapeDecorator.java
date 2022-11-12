package agh.dp.decorator;

import java.awt.*;

public class BlueShapeDecorator extends ShapeDecorator{
    public BlueShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        shape.draw(g2);
    }
}
