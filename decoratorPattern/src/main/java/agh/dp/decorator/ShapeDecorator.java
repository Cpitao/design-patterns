package agh.dp.decorator;

import java.awt.*;

public abstract class ShapeDecorator implements Shape {

    protected Shape shape;

    public ShapeDecorator(Shape shape)
    {
        this.shape = shape;
    }

    public void draw(Graphics2D g2) {
        shape.draw(g2);
    }
}
