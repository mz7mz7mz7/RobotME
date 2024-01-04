package org.robotme.xmlparser.rules;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Rule;
import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.xmlparser.model.Event;
import org.robotme.xmlparser.utils.ReflectionUtils;

/**
 * @author Marcin Zduniak
 */
public class EventPropertiesSetRule extends Rule {

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        final Event event = (Event) getDigester().peek(1);
        final LogEntry logEntry = (LogEntry) getDigester().peek(0);
        BeanUtils.populate(logEntry, event.getAttrs());

        // handling level attribute:
        String levelStr = event.getAttrs().get("level");
        if (null != levelStr) {
            final byte levelFieldValue = ReflectionUtils.getFieldValue(levelStr, "", "_LEVEL", LogHandler.class, Byte.class);
            logEntry.setLevel(levelFieldValue);
        } else {
            // by default:
            logEntry.setLevel(LogHandler.ALL_LEVEL);
        }
    }

}
