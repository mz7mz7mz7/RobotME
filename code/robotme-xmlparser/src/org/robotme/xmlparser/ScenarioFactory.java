package org.robotme.xmlparser;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.apache.commons.digester.Rule;
import org.robotme.core.log.assertion.Failure;
import org.robotme.core.log.entries.AlertModificationLogEntry;
import org.robotme.core.log.entries.CanvasModificationLogEntry;
import org.robotme.core.log.entries.CommandLogEntry;
import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.ListModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;
import org.robotme.core.recorder.state.widget.ChoiceGroupWidgetState;
import org.robotme.core.recorder.state.widget.DateFieldWidgetState;
import org.robotme.core.recorder.state.widget.GaugeWidgetState;
import org.robotme.core.recorder.state.widget.ImageItemWidgetState;
import org.robotme.core.recorder.state.widget.SpacerWidgetState;
import org.robotme.core.recorder.state.widget.StringItemWidgetState;
import org.robotme.core.recorder.state.widget.TextFieldWidgetState;
import org.robotme.xmlparser.model.Scenario;
import org.robotme.xmlparser.rules.AlertTypeSetRule;
import org.robotme.xmlparser.rules.ChoiceGroupWidgetSelectedFlagsSpecializedRule;
import org.robotme.xmlparser.rules.ChoiceGroupWidgetStringsSpecializedRule;
import org.robotme.xmlparser.rules.WidgetPropertiesSetRule;
import org.robotme.xmlparser.rules.WidgetSpecializedRule;
import org.robotme.xmlparser.rules.DisplayableChangedPropertiesSetRule;
import org.robotme.xmlparser.rules.EventFactory;
import org.robotme.xmlparser.rules.EventPropertiesSetRule;
import org.robotme.xmlparser.rules.KeyEventPropertiesSetRule;
import org.robotme.xmlparser.rules.ListModificationSelectedFlagsSpecializedRule;
import org.robotme.xmlparser.rules.ListModificationStringsSpecializedRule;
import org.robotme.xmlparser.rules.PointerEventPropertiesSetRule;

/**
 * @author Marcin Zduniak
 */
public class ScenarioFactory {

    private static final class RobotMEXMLParserDigester extends Digester {

    	/**
    	 * We want to be sure that different than {@link LogEntry} objects
    	 * will not be processed by this class, therefore we encourage to use
    	 * this method instead of deprecated {@link #addObjectCreate(String, Class)}.
    	 */
        public void addSpecializedObjectCreate(String pattern, Class<? extends LogEntry> clazz) {
            super.addObjectCreate(pattern, clazz);
        }

        /**
         * @deprecated Use {@link #addSpecializedObjectCreate(String, Class)} instead.
         */
        @Override
        @Deprecated
        public final void addObjectCreate(String pattern, Class clazz) {
            super.addObjectCreate(pattern, clazz);
        }
    } /* end of RobotMEXMLParserDigester inner class */

    public static Scenario createScenario(InputStream is) {
        final RobotMEXMLParserDigester d = new RobotMEXMLParserDigester();

        Scenario scenario = new Scenario();
        d.push(scenario);

        // Add rules to the digester that will be triggered while parsing occurs
        addRules(d);

        // Process the input XML file:
        try {
            d.parse(is);
        } catch (java.io.IOException ioe) {
            System.out.println("Error reading input file:" + ioe.getMessage());
            throw new RuntimeException(ioe);
        } catch (org.xml.sax.SAXException se) {
            System.out.println("Error parsing input file:" + se.getMessage());
            throw new RuntimeException(se);
        }

        return scenario;
    }

    private static void addRules(RobotMEXMLParserDigester d) {
        final Rule eventPropertiesSetRule = new EventPropertiesSetRule();

        // displayable-changed:
        d.addSpecializedObjectCreate("scenario/event/displayable-changed", DisplayableChangedLogEntry.class);
        d.addRule("scenario/event/displayable-changed", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/displayable-changed");
        final Rule displayableChangedPropertiesSetRule = new DisplayableChangedPropertiesSetRule();
        d.addRule("scenario/event/displayable-changed", displayableChangedPropertiesSetRule);
        d.addSetRoot("scenario/event/displayable-changed", "addLogEntry");

        // command:
        d.addSpecializedObjectCreate("scenario/event/command", CommandLogEntry.class);
        d.addRule("scenario/event/command", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/command");
        d.addSetRoot("scenario/event/command", "addLogEntry");

        // failure:
        d.addSpecializedObjectCreate("scenario/event/failure", Failure.class);
        d.addRule("scenario/event/failure", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/failure");
        d.addSetRoot("scenario/event/failure", "addLogEntry");

        // textbox-modification:
        d.addSpecializedObjectCreate("scenario/event/textbox-modification", TextBoxModificationLogEntry.class);
        d.addRule("scenario/event/textbox-modification", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/textbox-modification");
        d.addSetRoot("scenario/event/textbox-modification", "addLogEntry");

        // key-event:
        d.addSpecializedObjectCreate("scenario/event/key-event", KeyEventOnCanvasLogEntry.class);
        d.addRule("scenario/event/key-event", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/key-event");
        final Rule keyEventPropertiesSetRule = new KeyEventPropertiesSetRule();
        d.addRule("scenario/event/key-event", keyEventPropertiesSetRule);
        d.addSetRoot("scenario/event/key-event", "addLogEntry");

        // pointer-event:
        d.addSpecializedObjectCreate("scenario/event/pointer-event", PointerEventOnCanvasLogEntry.class);
        d.addRule("scenario/event/pointer-event", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/pointer-event");
        final Rule pointerEventPropertiesSetRule = new PointerEventPropertiesSetRule();
        d.addRule("scenario/event/pointer-event", pointerEventPropertiesSetRule);
        d.addSetRoot("scenario/event/pointer-event", "addLogEntry");

        // canvas-modification:
        d.addSpecializedObjectCreate("scenario/event/canvas-modification", CanvasModificationLogEntry.class);
        d.addRule("scenario/event/canvas-modification", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/canvas-modification");
        d.addSetRoot("scenario/event/canvas-modification", "addLogEntry");

        // list-modification:
        d.addSpecializedObjectCreate("scenario/event/list-modification", ListModificationLogEntry.class);
        d.addRule("scenario/event/list-modification", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/list-modification");
        final Rule listModificationSelectedFlagsSpecializedRule = new ListModificationSelectedFlagsSpecializedRule();
        d.addRule("scenario/event/list-modification/selectedFlags/value", listModificationSelectedFlagsSpecializedRule);
        final Rule listModificationStringsSpecializedRule = new ListModificationStringsSpecializedRule();
        d.addRule("scenario/event/list-modification/strings/value", listModificationStringsSpecializedRule);
        d.addSetRoot("scenario/event/list-modification", "addLogEntry");

        // form-modification:
        d.addSpecializedObjectCreate("scenario/event/form-modification", FormModificationLogEntry.class);
        d.addRule("scenario/event/form-modification", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/form-modification");
        
        final Rule widgetPropertiesSetRule = new WidgetPropertiesSetRule();
        d.addRule("scenario/event/form-modification/items/choice-group", new WidgetSpecializedRule(ChoiceGroupWidgetState.class));
        d.addRule("scenario/event/form-modification/items/choice-group", widgetPropertiesSetRule);
        d.addRule("scenario/event/form-modification/items/choice-group/strings/value", new ChoiceGroupWidgetStringsSpecializedRule());
        d.addRule("scenario/event/form-modification/items/choice-group/selectedFlags/value", new ChoiceGroupWidgetSelectedFlagsSpecializedRule());
        d.addRule("scenario/event/form-modification/items/date-field", new WidgetSpecializedRule(DateFieldWidgetState.class));
        d.addRule("scenario/event/form-modification/items/date-field", widgetPropertiesSetRule);
        d.addRule("scenario/event/form-modification/items/gauge", new WidgetSpecializedRule(GaugeWidgetState.class));
        d.addRule("scenario/event/form-modification/items/gauge", widgetPropertiesSetRule);
        d.addRule("scenario/event/form-modification/items/image", new WidgetSpecializedRule(ImageItemWidgetState.class));
        d.addRule("scenario/event/form-modification/items/image", widgetPropertiesSetRule);
        d.addRule("scenario/event/form-modification/items/spacer", new WidgetSpecializedRule(SpacerWidgetState.class));
        d.addRule("scenario/event/form-modification/items/spacer", widgetPropertiesSetRule);
        d.addRule("scenario/event/form-modification/items/string-item", new WidgetSpecializedRule(StringItemWidgetState.class));
        d.addRule("scenario/event/form-modification/items/string-item", widgetPropertiesSetRule);
        d.addRule("scenario/event/form-modification/items/text-field", new WidgetSpecializedRule(TextFieldWidgetState.class));
        d.addRule("scenario/event/form-modification/items/text-field", widgetPropertiesSetRule);
        d.addSetRoot("scenario/event/form-modification", "addLogEntry");
        
        // alert:
        d.addSpecializedObjectCreate("scenario/event/alert-modification", AlertModificationLogEntry.class);
        d.addRule("scenario/event/alert-modification", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/alert-modification");
        final Rule alertTypeSetRule = new AlertTypeSetRule();
        d.addRule("scenario/event/alert-modification", alertTypeSetRule);
        d.addSetRoot("scenario/event/alert-modification", "addLogEntry");

        // log-event:
        d.addSpecializedObjectCreate("scenario/event/log-event", LogEntry.class);
        d.addRule("scenario/event/log-event", eventPropertiesSetRule);
        d.addSetProperties("scenario/event/log-event");
        d.addSetRoot("scenario/event/log-event", "addLogEntry");

        // general rules:
        final ObjectCreationFactory eventFactory = new EventFactory();
        d.addFactoryCreate("scenario/event", eventFactory);
    }

}
