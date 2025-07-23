package src.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import src.view.utilits.RestorableWindow;
import src.model.log.LogChangeListener;
import src.model.log.LogEntry;
import src.model.log.LogWindowSource;

public class LogWindow extends RestorableWindow implements LogChangeListener {
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы");
        m_logSource = logSource;
        m_logSource.registerListener(this);

        m_logContent = new TextArea();
        m_logContent.setEditable(false);
        m_logContent.setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                dispose();
            }
        });

        updateLogContent();
        pack();
        setVisible(true);
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        synchronized (m_logSource) {
            for (LogEntry entry : m_logSource.all()) {
                content.append(entry.getMessage()).append("\n");
            }
        }
        m_logContent.setText(content.toString());
        m_logContent.repaint();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void dispose() {
        m_logSource.unregisterListener(this);
        super.dispose();
    }
}