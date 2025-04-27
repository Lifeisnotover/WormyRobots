package tests;

import src.controller.MainApplicationFrame;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import static org.junit.jupiter.api.Assertions.*;

class MainApplicationFrameTest {
    @Test
    void testExitConfirmation() {
        MainApplicationFrame frame = new MainApplicationFrame();

        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

        Window[] windows = Window.getWindows();
        boolean confirmationDialogFound = false;

        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if ("Подтверждение выхода".equals(dialog.getTitle())) {
                    confirmationDialogFound = true;
                    dialog.dispose();
                    break;
                }
            }
        }

        assertTrue(confirmationDialogFound, "Диалог подтверждения выхода не найден");
        frame.dispose();
    }
}