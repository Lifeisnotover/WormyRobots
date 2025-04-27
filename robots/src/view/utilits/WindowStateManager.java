package src.view.utilits;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WindowStateManager {
    private static final String CONFIG_FILE =
            Paths.get(System.getProperty("user.home"), "window_states.json").toString();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void saveWindowStates(JDesktopPane desktopPane) {
        Map<String, WindowState> states = new HashMap<>();

        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof RestorableWindow) {
                RestorableWindow window = (RestorableWindow) frame;
                states.put(window.getTitle(), window.getWindowState());
            }
        }

        try (Writer writer = Files.newBufferedWriter(Paths.get(CONFIG_FILE))) {
            GSON.toJson(states, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadWindowStates(JDesktopPane desktopPane) {
        if (!Files.exists(Paths.get(CONFIG_FILE))) return;

        try (Reader reader = Files.newBufferedReader(Paths.get(CONFIG_FILE))) {
            Map<String, WindowState> states = GSON.fromJson(reader,
                    new com.google.gson.reflect.TypeToken<Map<String, WindowState>>() {}.getType());

            if (states == null) return;

            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof RestorableWindow) {
                    RestorableWindow window = (RestorableWindow) frame;
                    WindowState state = states.get(frame.getTitle());
                    if (state != null) {
                        window.restoreWindowState(state);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}