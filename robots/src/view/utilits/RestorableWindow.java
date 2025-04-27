package src.view.utilits;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public abstract class RestorableWindow extends JInternalFrame {
    public RestorableWindow(String title) {
        super(title, true, true, true, true);
    }

    public WindowState getWindowState() {
        getLocation();
        return new WindowState(
                getLocation(),
                getSize() != null ? getSize() : new Dimension(400, 300),
                isMaximum(),
                isIcon()
        );
    }

    public void restoreWindowState(WindowState state) {
        if (state == null) return;

        Point location = state.getLocation() != null ? state.getLocation() : new Point(0, 0);
        Dimension size = state.getSize() != null ? state.getSize() : new Dimension(400, 300);

        setLocation(location);
        setSize(size);

        try {
            if (state.isMaximized()) {
                setMaximum(true);
            }
            if (state.isIconified()) {
                setIcon(true);
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}