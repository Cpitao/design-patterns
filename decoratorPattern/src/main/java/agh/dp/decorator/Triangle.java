package agh.dp.decorator;

import java.awt.*;

public class Triangle implements Shape {

    @Override
    public void draw(Graphics2D g2) {
        Polygon polygon = new Polygon();
        polygon.addPoint(100, 100);
        polygon.addPoint(500, 100);
        polygon.addPoint(300, 500);

        g2.drawPolygon(polygon);
    }
}
