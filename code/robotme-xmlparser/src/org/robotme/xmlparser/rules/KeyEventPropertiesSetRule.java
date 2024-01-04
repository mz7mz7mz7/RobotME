package org.robotme.xmlparser.rules;

import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.xmlparser.utils.ReflectionUtils;

/**
 * @author Marcin Zduniak
 */
public class KeyEventPropertiesSetRule extends EventPropertiesSetRule {

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        final KeyEventOnCanvasLogEntry logEntry = (KeyEventOnCanvasLogEntry) getDigester().peek(0);

        // handling type attribute:
        final String typeStr = attrs.getValue("type");
        if (null != typeStr) {
            final byte typeFieldValue = ReflectionUtils.getFieldValue(typeStr, "KEY_", "_TYPE",
                    KeyEventOnCanvasLogEntry.class, Byte.class);
            logEntry.setType(typeFieldValue);
        }
    }

}
