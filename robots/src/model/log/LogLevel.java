package src.model.log;

public enum LogLevel {
    Trace(0),
    Debug(1),
    Info(2),
    Warning(3),
    Error(4),
    Fatal(5);

    private final int m_iLevel;

    LogLevel(int iLevel) {
        m_iLevel = iLevel;
    }

    public int level() {
        return m_iLevel;
    }
}

