package tests;

import org.junit.jupiter.api.Test;
import src.gui.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.beans.PropertyVetoException;

class WindowStateTest {
    @Test
    void testWindowState() throws PropertyVetoException {
        // Создаем тестовое окно
        RestorableWindow window = new RestorableWindow("Test Window") {};
        window.setSize(400, 300);
        window.setLocation(100, 100);

        assertFalse(window.isIcon());
        assertFalse(window.isMaximum());

        window.setIcon(true);
        assertTrue(window.isIcon());

        window.setIcon(false);
        assertFalse(window.isIcon());

        window.setMaximum(true);
        assertTrue(window.isMaximum());

        window.setMaximum(false);
        assertFalse(window.isMaximum());

        WindowState state = window.getWindowState();
        assertEquals(new Dimension(400, 300), state.getSize());
        assertEquals(new Point(100, 100), state.getLocation());
        assertFalse(state.isMaximized());
        assertFalse(state.isIconified());

        RestorableWindow newWindow = new RestorableWindow("Restored") {};
        newWindow.restoreWindowState(state);

        assertEquals(state.getSize(), newWindow.getSize());
        assertEquals(state.getLocation(), newWindow.getLocation());
    }
}