package org.robotme.xmlparser.rules;

import org.apache.commons.digester.Rule;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.recorder.state.widget.ChoiceGroupWidgetState;

/**
 * @author Marcin Zduniak
 */
public class ChoiceGroupWidgetStringsSpecializedRule extends Rule {

    @Override
    public void body(String namespace, String name, String text) throws Exception {
        super.body(namespace, name, text);
        final String value = text;
        
        final FormModificationLogEntry entry = (FormModificationLogEntry) getDigester().peek(0);
        final AbstractWidgetState[] widgets = entry.getAbstractWidgetStates();
        final ChoiceGroupWidgetState choiceGroup = (ChoiceGroupWidgetState) widgets[widgets.length - 1];
        
        String[] strings = choiceGroup.getStrings();
        
        if (null == strings) {
            strings = new String[1];
        } else {
            final String[] newStrings = new String[strings.length + 1];
            System.arraycopy(strings, 0, newStrings, 0, strings.length);
            strings = newStrings;
        }
        strings[strings.length - 1] = value;
        choiceGroup.setStrings(strings);
    }
}
