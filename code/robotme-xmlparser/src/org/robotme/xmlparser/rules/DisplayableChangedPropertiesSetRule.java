package org.robotme.xmlparser.rules;

import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.xmlparser.utils.ReflectionUtils;

/**
 * @author Marcin Zduniak
 */
public class DisplayableChangedPropertiesSetRule extends EventPropertiesSetRule {

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        final DisplayableChangedLogEntry logEntry = (DisplayableChangedLogEntry) getDigester().peek(0);

        // handling type attribute:
        final String typeStr = attrs.getValue("type");
        if (null != typeStr) {
            final byte typeFieldValue = ReflectionUtils.getFieldValue(typeStr, "", "_TYPE",
                    DisplayableChangedLogEntry.class, Byte.class);
            logEntry.setType(typeFieldValue);
        }
    }

}
