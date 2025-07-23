package src.view;

import src.view.utilits.RestorableWindow;
import src.model.RobotModel;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends RestorableWindow {
    private final GameVisualizer visualizer;

    public GameWindow(RobotModel robotModel) {
        super("Игровое поле");

        visualizer = new GameVisualizer(robotModel);
        robotModel.addObserver(visualizer);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        pack();
        setSize(500, 500);
    }

    public GameVisualizer getVisualizer() {
        return visualizer;
    }
}
