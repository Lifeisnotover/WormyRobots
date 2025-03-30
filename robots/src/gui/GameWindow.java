package src.gui;


import java.awt.*;
import javax.swing.*;

public class GameWindow extends RestorableWindow {
    public GameWindow() {
        super("Игровое поле");

        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        pack();
        setSize(500, 500);
    }
}
