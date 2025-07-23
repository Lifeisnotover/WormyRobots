package src.controller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import src.view.utilits.WindowStateManager;
import src.model.log.Logger;
import src.model.RobotModel;
import src.view.GameWindow;
import src.view.LogWindow;
import src.view.RobotCoordinatesWindow;

import static src.model.log.Logger.getDefaultLogSource;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final MenuBuilder menuBuilder;
    private final RobotModel robotModel;

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        setContentPane(desktopPane);

        robotModel = new RobotModel();

        setupWindows();

        this.menuBuilder = new MenuBuilder(this);
        setJMenuBar(menuBuilder.buildMenuBar());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuBuilder.confirmExit();
            }
        });

        WindowStateManager.loadWindowStates(desktopPane);
        startModelUpdateTimer();
        setVisible(true);
    }

    private void setupWindows() {
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow(robotModel);
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        RobotCoordinatesWindow coordWindow = new RobotCoordinatesWindow(robotModel);
        coordWindow.setLocation(320, 10);
        addWindow(coordWindow);
    }

    private void startModelUpdateTimer() {
        Timer timer = new Timer("Model updater", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robotModel.updatePosition(10);
            }
        }, 0, 10);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public JMenuBar getJMenuBar() {
        return menuBuilder.buildMenuBar();
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}
