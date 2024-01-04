package org.robotme.xmlparser.rules;

import org.apache.commons.digester.Rule;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.recorder.state.widget.ChoiceGroupWidgetState;

/**
 * @author Marcin Zduniak
 */
public class ChoiceGroupWidgetSelectedFlagsSpecializedRule extends Rule {

    @Override
    public void body(String namespace, String name, String text) throws Exception {
        super.body(namespace, name, text);
        // TODO: consider verifying if given text is eq "true" or "false"
        final boolean value = "true".equals(text.trim());
        
        final FormModificationLogEntry entry = (FormModificationLogEntry) getDigester().peek(0);
        final AbstractWidgetState[] widgets = entry.getAbstractWidgetStates();
        final ChoiceGroupWidgetState choiceGroup = (ChoiceGroupWidgetState) widgets[widgets.length - 1];

        boolean[] selectedFlags = choiceGroup.getSelectedFlags();
        if (null == selectedFlags) {
            selectedFlags = new boolean[1];
        } else {
            final boolean[] newSelectedFlags = new boolean[selectedFlags.length + 1];
            System.arraycopy(selectedFlags, 0, newSelectedFlags, 0, selectedFlags.length);
            selectedFlags = newSelectedFlags;
        }
        selectedFlags[selectedFlags.length - 1] = value;
        choiceGroup.setSelectedFlags(selectedFlags);
    }

}
