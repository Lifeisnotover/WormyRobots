package src.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class GameVisualizer extends JPanel implements Observer {
    private final RobotModel robotModel;

    public GameVisualizer(RobotModel robotModel) {
        this.robotModel = robotModel;
        this.robotModel.addObserver(this);
        setPreferredSize(new Dimension(400, 400));
        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robotModel.setTargetPosition(e.getX(), e.getY());
            }
        });
    }

    public void update(Observable o, Object arg) {
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawRobot(g2d,
                (int) robotModel.getRobotPositionX(),
                (int) robotModel.getRobotPositionY(),
                robotModel.getRobotDirection());

        drawTarget(g2d,
                robotModel.getTargetPositionX(),
                robotModel.getTargetPositionY());
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);

        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);

        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);

        g.setTransform(new AffineTransform());
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x - width / 2, y - height / 2, width, height);
    }

    private void drawOval(Graphics g, int x, int y, int width, int height) {
        g.drawOval(x - width / 2, y - height / 2, width, height);
    }

}