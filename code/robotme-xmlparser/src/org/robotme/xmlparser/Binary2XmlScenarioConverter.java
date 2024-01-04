package org.robotme.xmlparser;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.robotme.core.log.LogEntriesMapper;
import org.robotme.core.log.entries.LogEntry;

/**
 * @author Marcin Zduniak
 */
public class Binary2XmlScenarioConverter {
    public static String convert(InputStream is) throws IOException {
        final StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<scenario>\n\n");

        final DataInputStream dis = new DataInputStream(is);
        try {
            while (true) {
                final int logEntryId = dis.readInt();
                final LogEntry logEntry = LogEntriesMapper.createNewLogEntryInstance(logEntryId, dis);
                for (String xml : logEntry.toXML()) {
                    sb.append('\t').append(xml).append('\n');
                }
                sb.append('\n');
            }
        } catch (java.io.EOFException e) {
            // ok, ignore
        }

        sb.append("</scenario>\n");

        return sb.toString();
    }
}
