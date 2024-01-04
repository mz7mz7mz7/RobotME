package org.robotme.xmlparser.rules;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Rule;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;

/**
 * @author Marcin Zduniak
 */
public class WidgetPropertiesSetRule extends Rule {

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        super.begin(namespace, name, attrs);
        final FormModificationLogEntry entry = (FormModificationLogEntry) getDigester().peek(0);
        final AbstractWidgetState[] widgets = entry.getAbstractWidgetStates();
        final Map<String, Object> map = convertAttrsToMap(attrs);
        widgetSpecificConversion(name, map);
        BeanUtils.populate(widgets[widgets.length - 1], map);
    }

    private void widgetSpecificConversion(String name, Map<String, Object> map) {
        if ("date-field".equals(name)) {
            final String strDate = (String) map.get("date");
            if (null != strDate) {
                final Date dateDate = new Date(Long.parseLong(strDate));
                map.put("date", dateDate);
            }
        }
    }

    private Map<String, Object> convertAttrsToMap(org.xml.sax.Attributes attrs) {
        final Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < attrs.getLength(); i++) {
            map.put(attrs.getQName(i), attrs.getValue(i));
        }
        return map;
    }

}
