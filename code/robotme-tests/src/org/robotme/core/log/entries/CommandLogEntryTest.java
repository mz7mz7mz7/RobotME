package org.robotme.core.log.entries;

import static org.junit.Assert.*;

import org.junit.Test;
import org.robotme.core.log.LogHandler;

/**
 * @author Marcin Zduniak
 */
public class CommandLogEntryTest {

    private final String XML1 = "<event level=\"FATAL\" timestamp=\"456789\" message=\"message1234\" "
            + "exception=\"exception3210\" replayable=\"true\" assertion=\"false\">";

    private final String XML2 = "<command cmdLabel=\"GoGo!\" displayableTitle=\"Title456\"/>";

    private final String XML3 = "</event>";

    /**
     * Test method for {@link org.robotme.core.log.entries.CommandLogEntry#toXML()}.
     */
    @Test
    public void testToXML() {
        final CommandLogEntry e = new CommandLogEntry();
        e.setMessage("message1234");
        e.setException("exception3210");
        e.setLevel(LogHandler.FATAL_LEVEL);
        e.setAssertion(false);
        e.setReplayable(true);
        e.setTimestamp(456789L);
        e.setCmdLabel("GoGo!");
        e.setDisplayableTitle("Title456");
        String[] xml = e.toXML();

        assertEquals(3, xml.length);
        assertEquals(XML1, xml[0]);
        assertEquals(XML2, xml[1]);
        assertEquals(XML3, xml[2]);
    }

}
