package agh.dp.decorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App {
    public static void main(String[] args) {
        Frame frame = new Frame();
        final PaintComponent[] pc = new PaintComponent[1];
        String[] features = {"circle", "red", "solid"};
        // -------------------------CHECKBOXES------------------------------------------
        // Init shapes radio buttons
        CheckboxGroup shapes = new CheckboxGroup();
        Checkbox checkboxCircle = new Checkbox("circle", shapes, true);
        checkboxCircle.addItemListener(e -> {
            features[0] = "circle";
            pc[0].shape = features[0];
            frame.repaint();
        });
        Checkbox checkboxSquare = new Checkbox("square", shapes, false);
        checkboxSquare.addItemListener(e -> {
            features[0] = "square";
            pc[0].shape = features[0];
            frame.repaint();
        });
        Checkbox checkboxTriangle = new Checkbox("triangle", shapes, false);
        checkboxTriangle.addItemListener(e -> {
            features[0] = "triangle";
            pc[0].shape = features[0];
            frame.repaint();
        });
        frame.add(checkboxCircle);
        frame.add(checkboxTriangle);
        frame.add(checkboxSquare);
        checkboxCircle.setBounds(20, 30, 100, 20);
        checkboxSquare.setBounds(20, 50, 100, 20);
        checkboxTriangle.setBounds(20, 70, 100, 20);
        // Init colors checkbox group
        CheckboxGroup colors = new CheckboxGroup();
        Checkbox checkboxRed = new Checkbox("red", colors, true);
        Checkbox checkboxBlue = new Checkbox("blue", colors, false);
        Checkbox checkboxGreen = new Checkbox("green", colors, false);
        checkboxRed.addItemListener(e -> {
            features[1] = "red";
            pc[0].color = features[1];
            frame.repaint();
        });
        checkboxBlue.addItemListener(e -> {
            features[1] = "blue";
            pc[0].color = features[1];
            frame.repaint();
        });
        checkboxGreen.addItemListener(e -> {
            features[1] = "green";
            pc[0].color = features[1];
            frame.repaint();
        });
        frame.add(checkboxRed);
        frame.add(checkboxBlue);
        frame.add(checkboxGreen);
        checkboxRed.setBounds(270, 30, 100, 20);
        checkboxBlue.setBounds(270, 50, 100, 20);
        checkboxGreen.setBounds(270, 70, 100, 20);
        // Init styles checkbox group
        CheckboxGroup styles = new CheckboxGroup();
        Checkbox checkboxSolid = new Checkbox("solid", styles, true);
        Checkbox checkboxDotted = new Checkbox("dotted", styles, false);
        Checkbox checkboxDashed = new Checkbox("dashed", styles, false);
        checkboxSolid.addItemListener(e -> {
            features[2] = "solid";
            pc[0].style = features[2];
            frame.repaint();
        });
        checkboxDotted.addItemListener(e -> {
            features[2] = "dotted";
            pc[0].style = features[2];
            frame.repaint();
        });
        checkboxDashed.addItemListener(e -> {
            features[2] = "dashed";
            pc[0].style = features[2];
            frame.repaint();
        });
        frame.add(checkboxSolid);
        frame.add(checkboxDotted);
        frame.add(checkboxDashed);
        checkboxSolid.setBounds(520, 30, 100, 20);
        checkboxDotted.setBounds(520, 50, 100, 20);
        checkboxDashed.setBounds(520, 70, 100, 20);
        // ---------------------------------------------------------------------------
        pc[0] = new PaintComponent(features[0], features[1], features[2]);
        frame.add(pc[0]);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        int frameWidth = 650, frameHeight = 650;
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
    }


    static class PaintComponent extends Component {
        private String shape, color, style;
        public PaintComponent(String shape, String color, String style) {
            this.shape = shape;
            this.color = color;
            this.style = style;
        }
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Shape s;
            s = switch (this.shape) {
                case "square" -> new Square();
                case "circle" -> new Circle();
                case "triangle" -> new Triangle();
                default -> new Circle();
            };

            s = switch (this.color) {
                case "red" -> new RedShapeDecorator(s);
                case "green" -> new GreenShapeDecorator(s);
                case "blue" -> new BlueShapeDecorator(s);
                default -> s;
            };

            s = switch (this.style) {
                case "solid" -> new SolidShapeDecorator(s);
                case "dotted" -> new DottedShapeDecorator(s);
                case "dashed" -> new DashedShapeDecorator(s);
                default -> s;
            };

            s.draw(g2);
        }
    }
}
