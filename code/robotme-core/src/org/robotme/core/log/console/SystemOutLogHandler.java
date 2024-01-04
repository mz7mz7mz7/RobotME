package org.robotme.core.log.console;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.LogEntry;

/**
 * @author Marcin Zduniak
 */
public class SystemOutLogHandler extends LogHandler {

    public SystemOutLogHandler() {
        super();
    }

    public void log(LogEntry logEntry) {
        if (allowLogging(getLoggingLevel(), logEntry.getLevel())) {
            System.out.println("Level: [" + logEntry.getLevel() + "] Log: " + logEntry);
            System.out.println("Time: [" + logEntry.getTimestamp());
        }
    }

}
