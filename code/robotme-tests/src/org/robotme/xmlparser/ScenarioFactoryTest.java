package org.robotme.xmlparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.robotme.core.log.entries.AlertModificationLogEntry;
import org.robotme.core.log.entries.CanvasModificationLogEntry;
import org.robotme.core.log.entries.CommandLogEntry;
import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;
import org.robotme.core.recorder.state.AlertState;
import org.robotme.core.recorder.state.widget.ChoiceGroupWidgetState;
import org.robotme.core.recorder.state.widget.DateFieldWidgetState;
import org.robotme.core.recorder.state.widget.GaugeWidgetState;
import org.robotme.core.recorder.state.widget.ImageItemWidgetState;
import org.robotme.core.recorder.state.widget.SpacerWidgetState;
import org.robotme.core.recorder.state.widget.StringItemWidgetState;
import org.robotme.core.recorder.state.widget.TextFieldWidgetState;
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
        assertEquals("9 entries expected", 9, e.size());

        assertEquals("0-th entry of type displayable-changed", e.get(0).getClass(), DisplayableChangedLogEntry.class);
        assertEquals("1-th entry of type log-event", e.get(1).getClass(), LogEntry.class);
        assertEquals("2-th entry of type command", e.get(2).getClass(), CommandLogEntry.class);
        assertEquals("3-th entry of type textbox-modification", e.get(3).getClass(), TextBoxModificationLogEntry.class);
        assertEquals("4-th entry of type key-event", e.get(4).getClass(), KeyEventOnCanvasLogEntry.class);
        assertEquals("5-th entry of type pointer-event", e.get(5).getClass(), PointerEventOnCanvasLogEntry.class);
        assertEquals("6-th entry of type canvas-modification", e.get(6).getClass(), CanvasModificationLogEntry.class);
        assertEquals("7-th entry of type form-modification", e.get(7).getClass(), FormModificationLogEntry.class);
        FormModificationLogEntry formEntry = (FormModificationLogEntry) e.get(7);
        assertEquals("7 widgets expected", 7, formEntry.getAbstractWidgetStates().length);

        final Class[] expectedWidgets = new Class[] { ChoiceGroupWidgetState.class, DateFieldWidgetState.class,
                GaugeWidgetState.class, ImageItemWidgetState.class, SpacerWidgetState.class,
                StringItemWidgetState.class, TextFieldWidgetState.class };
        for (int i = 0; i < expectedWidgets.length; i++) {
            assertEquals("Wrong widget type", expectedWidgets[i], formEntry.getAbstractWidgetStates()[i].getClass());
        }
        assertEquals("8-th entry of type alert-modification", e.get(8).getClass(), AlertModificationLogEntry.class);
        final AlertModificationLogEntry alert = (AlertModificationLogEntry) e.get(8);
        assertEquals("Wrong title in alert", "Title 5", alert.getTitle());
        assertEquals("Wrong type in alert", AlertState.ALERT_TYPE_WARNING, alert.getAlertType());

        // TODO: write other tests
    }

}
