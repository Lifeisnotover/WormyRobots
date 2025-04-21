package src.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class RobotCoordinatesWindow extends RestorableWindow implements Observer {
    private final JLabel coordinatesLabel;
    private final RobotModel robotModel;

    public RobotCoordinatesWindow(RobotModel robotModel) {
        super("Координаты робота");
        this.robotModel = robotModel;
        this.robotModel.addObserver(this);

        coordinatesLabel = new JLabel("X: 0, Y: 0, Угол: 0");
        coordinatesLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesLabel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(panel);
        pack();
        setSize(300, 100);

    }

    @Override
    public void update(Observable o, Object arg) {
        double x = robotModel.getRobotPositionX();
        double y = robotModel.getRobotPositionY();
        double direction = Math.toDegrees(robotModel.getRobotDirection());

        SwingUtilities.invokeLater(() -> {
            coordinatesLabel.setText(String.format("X: %.2f, Y: %.2f, Угол: %.2f°",
                    x, y, direction));
        });
    }

    @Override
    public void dispose() {
        robotModel.deleteObserver(this);
        super.dispose();
    }
}