package src.view.utilits;

import java.awt.*;
import java.io.Serializable;

public class WindowState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Point location;
    private final Dimension size;
    private final boolean maximized;
    private final boolean iconified;


    public WindowState(Point location, Dimension size, boolean maximized, boolean iconified) {
        this.location = location;
        this.size = size;
        this.maximized = maximized;
        this.iconified = iconified;
    }


    public Point getLocation() {
        return location;
    }

    public Dimension getSize() {
        return size;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public boolean isIconified() {
        return iconified;
    }
}