package org.robotme.core.log.entries;

import static org.junit.Assert.*;

import org.junit.Test;
import org.robotme.core.log.LogHandler;

/**
 * @author Marcin Zduniak
 */
public class DisplayableChangedLogEntryTest {

    private final String XML1 = "<event level=\"FATAL\" timestamp=\"456789\" message=\"message1234\" "
            + "exception=\"exception3210\" replayable=\"false\" assertion=\"true\">";

    private final String XML2 = "<displayable-changed title=\"Title78\" type=\"FORM\"/>";

    private final String XML3 = "</event>";

    /**
     * Test method for {@link org.robotme.core.log.entries.DisplayableChangedLogEntry#toXML()}.
     */
    @Test
    public void testToXML() {
        final DisplayableChangedLogEntry e = new DisplayableChangedLogEntry();
        e.setMessage("message1234");
        e.setException("exception3210");
        e.setLevel(LogHandler.FATAL_LEVEL);
        e.setAssertion(true);
        e.setReplayable(false);
        e.setTimestamp(456789L);
        e.setType(DisplayableChangedLogEntry.FORM_TYPE);
        e.setTitle("Title78");
        String[] xml = e.toXML();

        assertEquals(3, xml.length);
        
        assertEquals(XML1, xml[0]);
        assertEquals(XML2, xml[1]);
        assertEquals(XML3, xml[2]);
    }

}
