/**
 * 
 */
package org.robotme.core.log.assertion;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.robotme.core.log.LogHandler;

/**
 * @author Marcin Zduniak
 */
public class FailureTest {

    private final String XML1 = "<event level=\"ERROR\" timestamp=\"456789\" message=\"message1234\" "
            + "exception=\"exception3210\" replayable=\"false\" assertion=\"false\">";

    private final String XML2 = "<failure failureMessage=\"failureMessage1\" assertionClassName=\"com.zduniak.assertion.Class1\" "
            + "expected=\"expected1\" present=\"present1\"/>";

    private final String XML2_2 = "<failure failureMessage=\"failureMessage1\" assertionClassName=\"com.zduniak.assertion.Class1\" "
            + "expected=\"\" present=\"\"/>";

    private final String XML3 = "</event>";

    private Failure e, e2;

    @Before
    public void setUp() {
        e = new Failure();
        e.setMessage("message1234");
        e.setException("exception3210");
        e.setLevel(LogHandler.ERROR_LEVEL);
        e.setAssertion(false);
        e.setReplayable(false);
        e.setTimestamp(456789L);
        e.setFailureMessage("failureMessage1");
        e.setAssertionClassName("com.zduniak.assertion.Class1");
        e.setExpected("expected1");
        e.setPresent("present1");
    }

    /*
     * Optional attributes are not set here.
     */
    @Before
    public void setUp2() {
        e2 = new Failure();
        e2.setMessage("message1234");
        e2.setException("exception3210");
        e2.setLevel(LogHandler.ERROR_LEVEL);
        e2.setAssertion(false);
        e2.setReplayable(false);
        e2.setTimestamp(456789L);
        e2.setFailureMessage("failureMessage1");
        e2.setAssertionClassName("com.zduniak.assertion.Class1");
    }

    /**
     * Test method for {@link org.robotme.core.log.assertion.Failure#toXML()}.
     */
    @Test
    public void testToXML() {
        String[] xml = e.toXML();

        assertEquals(3, xml.length);

        assertEquals("Cmp1", XML1, xml[0]);
        assertEquals("Cmp2", XML2, xml[1]);
        assertEquals("Cmp3", XML3, xml[2]);
    }

    /**
     * Test method for {@link org.robotme.core.log.assertion.Failure#toXML()}.
     */
    @Test
    public void testToXML_WithoutOptionalAttrs() {
        String[] xml = e2.toXML();

        assertEquals(3, xml.length);

        assertEquals("Cmp1", XML1, xml[0]);
        assertEquals("Cmp2", XML2_2, xml[1]);
        assertEquals("Cmp3", XML3, xml[2]);
    }

    /**
     * Test method for {@link org.robotme.core.log.assertion.Failure#toByteArray()}.
     * 
     * @throws IOException
     */
    @Test
    public void testToByteArray() throws IOException {
        final byte[] bytes = e.toByteArray();
        assertTrue(bytes != null);
        assertTrue(bytes.length > 0);
    }

    /**
     * Test method for {@link org.robotme.core.log.assertion.Failure#toByteArray()}.
     * 
     * @throws IOException
     */
    @Test
    public void testToByteArray_WithoutOptionalAttrs() throws IOException {
        final byte[] bytes = e2.toByteArray();
        assertTrue(bytes != null);
        assertTrue(bytes.length > 0);
    }

}
