package org.robotme.xmlparser.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This model object is used only temporarily during creating stack of nodes by Apache Digester to store XML attributes
 * for some time.
 * 
 * @author Marcin Zduniak
 */
public class Event {

    private Map<String, String> attrs = new HashMap<String, String>();

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

}
