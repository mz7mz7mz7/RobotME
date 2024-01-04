package org.robotme.core.log.entries;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.robotme.core.log.LogHandler;

/**
 * @author Marcin Zduniak
 */
public class LogEntryTest {

    private final String XML1 = "<event level=\"INFO\" timestamp=\"12456789\" message=\"message123\" "
            + "exception=\"exception321\" replayable=\"false\" assertion=\"true\">";
    
    private final String XML2 = "<log-event/>";

    private final String XML3 = "</event>";
    

    /**
     * Test method for {@link org.robotme.core.log.entries.LogEntry#toXML()}.
     */
    @Test
    public void testToXML() {
        final LogEntry e = new LogEntry();
        e.setMessage("message123");
        e.setException("exception321");
        e.setLevel(LogHandler.INFO_LEVEL);
        e.setAssertion(true);
        e.setReplayable(false);
        e.setTimestamp(12456789L);
        String[] xml = e.toXML();
        
        assertEquals(3, xml.length);
        assertEquals(XML1, xml[0]);
        assertEquals(XML2, xml[1]);
        assertEquals(XML3, xml[2]);
    }

}
