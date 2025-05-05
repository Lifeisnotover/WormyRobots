package src.view;

import src.model.RobotModel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class GameVisualizer extends JPanel implements Observer {
    private final RobotModel robotModel;
    private volatile double robotX = 0;
    private volatile double robotY = 0;
    private volatile double robotDirection = 0;
    private volatile int targetX = 0;
    private volatile int targetY = 0;

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

        Timer repaintTimer = new Timer("Repaint timer", true);
        repaintTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(GameVisualizer.this::repaint);
            }
        }, 0, 1);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.robotX = robotModel.getRobotPositionX();
        this.robotY = robotModel.getRobotPositionY();
        this.robotDirection = robotModel.getRobotDirection();
        this.targetX = robotModel.getTargetPositionX();
        this.targetY = robotModel.getTargetPositionY();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawRobot(g2d, (int) robotX, (int) robotY, robotDirection);
        drawTarget(g2d, targetX, targetY);
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