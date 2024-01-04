package org.robotme.xmlparser.rules;

import org.apache.commons.digester.Rule;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;

/**
 * @author Marcin Zduniak
 */
public class WidgetSpecializedRule extends Rule {

    private final Class<? extends AbstractWidgetState> widgetToCreate;

    public WidgetSpecializedRule(Class<? extends AbstractWidgetState> widgetToCreate) {
        this.widgetToCreate = widgetToCreate;
    }

    @Override
    public void begin(String namespace, String name, org.xml.sax.Attributes attrs) throws Exception {
        super.begin(namespace, name, attrs);
        
        final FormModificationLogEntry entry = (FormModificationLogEntry) getDigester().peek(0);
        AbstractWidgetState[] widgets = entry.getAbstractWidgetStates();

        if (null == widgets) {
            widgets = new AbstractWidgetState[1];
        } else {
            final AbstractWidgetState[] newWidgets = new AbstractWidgetState[widgets.length + 1];
            System.arraycopy(widgets, 0, newWidgets, 0, widgets.length);
            widgets = newWidgets;
        }
        widgets[widgets.length - 1] = widgetToCreate.newInstance();
        entry.setAbstractWidgetStates(widgets);
    }
}
