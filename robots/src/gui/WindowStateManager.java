package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WindowStateManager {
    private static final String CONFIG_FILE = System.getProperty("user.home") + "/.robots_program_config";

    public void saveWindowStates(JDesktopPane desktopPane) {
        if (desktopPane == null) {
            System.err.println("DesktopPane is null. Cannot save window states.");
            return;
        }

        Map<String, WindowState> states = new HashMap<>();
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            WindowState state = new WindowState(
                    frame.getLocation(),
                    frame.getSize(),
                    frame.isMaximum(),
                    frame.isIcon()
            );
            states.put(frame.getTitle(), state);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(states);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadWindowStates(JDesktopPane desktopPane) {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(CONFIG_FILE)))) {
            Map<String, WindowState> states = (Map<String, WindowState>) ois.readObject();
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                WindowState state = states.get(frame.getTitle());
                if (state != null) {
                    frame.setLocation(state.getLocation());
                    frame.setSize(state.getSize());
                    if (state.isMaximized()) {
                        frame.setMaximum(true);
                    }
                    if (state.isIconified()){
                        frame.setIcon(true);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    private static class WindowState implements Serializable {
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
}