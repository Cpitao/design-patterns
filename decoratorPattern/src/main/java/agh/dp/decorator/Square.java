package agh.dp.decorator;

import java.awt.*;

public class Square implements Shape {

    @Override
    public void draw(Graphics2D g2) {
        g2.drawRect(100, 100, 400, 400);
    }
}
