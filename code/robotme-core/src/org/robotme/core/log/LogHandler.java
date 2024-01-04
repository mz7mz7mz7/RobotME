package org.robotme.core.log;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.socket.RemoteSocketLogHandler;

/**
 * @author Marcin Zduniak
 */
public abstract class LogHandler {
    
//  private final static LogHandler log = new SystemOutLogHandler();
    private static LogHandler LOG;

    public final static byte ALL_LEVEL = 0;

    public final static byte DEBUG_LEVEL = 1;

    public final static byte INFO_LEVEL = 2;

    public final static byte WARN_LEVEL = 3;

    public final static byte ERROR_LEVEL = 4;

    public final static byte FATAL_LEVEL = 5;

    public final static byte OFF_LEVEL = 6;

    public final static byte INTERNAL_LEVEL = 127;
    
    public static LogHandler getInstance() {
        if (null == LOG) {
            LOG = new RemoteSocketLogHandler(
                    "localhost", 9999, (short) 10);
        }
        return LOG;
    }

    public LogHandler() {
        super();
    }

    public abstract void log(LogEntry logEntry);
    
    public void error(String logMsg, Throwable throwable) {
        log(new LogEntry(LogHandler.ERROR_LEVEL, logMsg, throwable));
    }
    
	public void log(String logMsg) {
        System.out.println("LOG: " + logMsg);
		log(new LogEntry(LogHandler.DEBUG_LEVEL, logMsg));
	}

    /**
     * Is called before the corresponding MIDlet exits. Subclasses can override
     * this method for cleaning up.
     */
    public void shutdown() {
        // Empty implementation
    }

    public byte getLoggingLevel() {
        // logging all logs by default
        return LogHandler.ALL_LEVEL;
    }

    protected boolean allowLogging(final byte currentLevel, final byte logEntryLevel) {
        return (logEntryLevel >= currentLevel);
    }

}
