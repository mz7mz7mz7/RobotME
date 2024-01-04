package org.robotme.xmlparser.rules;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.robotme.xmlparser.model.Event;
import org.xml.sax.Attributes;

/**
 * @author Marcin Zduniak
 */
public class EventFactory extends AbstractObjectCreationFactory {

    @Override
    public Object createObject(Attributes attrs) throws Exception {
        final Map<String, String> map = new HashMap<String, String>();
        for (int i = 0, n = attrs.getLength(); i < n; i++) {
            final String name = attrs.getQName(i);
            final String value = attrs.getValue(i);
            map.put(name, value);
        }
        final Event event = new Event();
        event.setAttrs(map);
        return event;
    }
    
}
