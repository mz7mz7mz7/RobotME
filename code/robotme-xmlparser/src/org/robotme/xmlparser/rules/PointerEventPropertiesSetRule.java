package org.robotme.xmlparser.rules;

import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.xmlparser.utils.ReflectionUtils;

/**
 * @author Marcin Zduniak
 */
public class PointerEventPropertiesSetRule extends EventPropertiesSetRule {

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        final PointerEventOnCanvasLogEntry logEntry = (PointerEventOnCanvasLogEntry) getDigester().peek(0);

        // handling type attribute:
        final String typeStr = attrs.getValue("type");
        if (null != typeStr) {
            final byte typeFieldValue = ReflectionUtils.getFieldValue(typeStr, "POINTER_", "_TYPE",
                    PointerEventOnCanvasLogEntry.class, Byte.class);
            logEntry.setType(typeFieldValue);
        }
    }

}
