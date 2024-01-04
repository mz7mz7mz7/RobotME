package org.robotme.xmlparser.rules;

import org.robotme.core.log.entries.AlertModificationLogEntry;
import org.robotme.core.recorder.state.AlertState;
import org.robotme.xmlparser.utils.ReflectionUtils;

/**
 * @author Marcin Zduniak
 */
public class AlertTypeSetRule extends EventPropertiesSetRule {

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        final AlertModificationLogEntry logEntry = (AlertModificationLogEntry) getDigester().peek(0);

        // handling type attribute:
        final String typeStr = attrs.getValue("alertType");
        // TODO: consider using <code>typeStr.toUpperCase()</code> 
        if (null != typeStr) {
            final byte typeFieldValue = ReflectionUtils.getFieldValue(typeStr, "ALERT_TYPE_", "", AlertState.class,
                    Byte.class);
            logEntry.setAlertType(typeFieldValue);
        }
    }

}
