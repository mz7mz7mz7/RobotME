package org.robotme.xmlparser.model;

import java.util.ArrayList;
import java.util.List;
import org.robotme.core.log.entries.LogEntry;

/**
 * @author   Marcin Zduniak
 */
public class Scenario {

    private final List<LogEntry> entries = new ArrayList<LogEntry>();

    public void addLogEntry(LogEntry logEntry) {
        this.entries.add(logEntry);
    }

    public List<LogEntry> getEntries() {
        return entries;
    }
    
}
