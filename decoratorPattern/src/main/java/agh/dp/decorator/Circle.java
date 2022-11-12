package agh.dp.decorator;

import java.awt.*;

public class Circle implements Shape {

    @Override
    public void draw(Graphics2D g2) {
        g2.drawOval(100, 100, 400, 400);
    }
}
