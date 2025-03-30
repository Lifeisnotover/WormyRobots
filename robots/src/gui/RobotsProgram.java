package src.gui;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RobotsProgram {
    private static final Logger logger = LoggerFactory.getLogger(RobotsProgram.class);

    public static void main(String[] args) {
        logger.info("Программа запущена");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            src.gui.MainApplicationFrame frame = new src.gui.MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
