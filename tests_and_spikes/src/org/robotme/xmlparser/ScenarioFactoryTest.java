package org.robotme.xmlparser;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.robotme.core.log.entries.CommandLogEntry;
import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;
import org.robotme.xmlparser.model.Scenario;

/**
 * @author Marcin Zduniak
 */
public class ScenarioFactoryTest {

    private InputStream is;

    @Before
    public void setUp() throws Exception {
        is = getClass().getResourceAsStream("/example-user-scenario.xml");
    }

    /**
     * Test method for {@link org.robotme.xmlparser.ScenarioFactory#createScenario(java.io.InputStream)}.
     */
    @Test
    public void testCreateScenario() {
        final Scenario s = ScenarioFactory.createScenario(is);
        assertNotNull("Scenario must be created", s);

        List<LogEntry> e = s.getEntries();
        assertEquals("4 entries expected", 4, e.size());

        assertEquals("0-th entry of type displayable-changed", e.get(0).getClass(), DisplayableChangedLogEntry.class);
        assertEquals("1-th entry of type log-event", e.get(1).getClass(), LogEntry.class);
        assertEquals("2-th entry of type command", e.get(2).getClass(), CommandLogEntry.class);
        assertEquals("3-th entry of type textbox-modification", e.get(3).getClass(), TextBoxModificationLogEntry.class);
        
        // TODO: write other tests
    }

}
