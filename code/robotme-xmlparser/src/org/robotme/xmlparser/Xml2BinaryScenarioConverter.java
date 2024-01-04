package org.robotme.xmlparser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.xmlparser.model.Scenario;

/**
 * @author Marcin Zduniak
 */
public class Xml2BinaryScenarioConverter {
    public static byte[] convert(InputStream is) throws IOException {
        final Scenario scenario = ScenarioFactory.createScenario(is);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        for (LogEntry logEntry : scenario.getEntries()) {
            final byte[] bytes = logEntry.toByteArray();
            baos.write(bytes);
        }
        return baos.toByteArray();
    }
}
