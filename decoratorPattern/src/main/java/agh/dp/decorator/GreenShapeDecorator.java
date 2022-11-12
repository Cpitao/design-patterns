package agh.dp.decorator;

import java.awt.*;

public class GreenShapeDecorator extends ShapeDecorator {
    public GreenShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        shape.draw(g2);
    }
}
