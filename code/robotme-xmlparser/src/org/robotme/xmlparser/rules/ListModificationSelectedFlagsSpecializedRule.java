package org.robotme.xmlparser.rules;

import org.apache.commons.digester.Rule;
import org.robotme.core.log.entries.ListModificationLogEntry;

/**
 * @author Marcin Zduniak
 */
public class ListModificationSelectedFlagsSpecializedRule extends Rule {

    @Override
    public void body(String namespace, String name, String text) throws Exception {
        super.body(namespace, name, text);
        // TODO: consider verifying if given text is eq "true" or "false"
        final boolean value = "true".equals(text.trim());

        final ListModificationLogEntry entry = (ListModificationLogEntry) getDigester().peek(0);
        boolean[] selectedFlags = entry.getSelectedFlags();
        if (null == selectedFlags) {
            selectedFlags = new boolean[1];
        } else {
            final boolean[] newSelectedFlags = new boolean[selectedFlags.length + 1];
            System.arraycopy(selectedFlags, 0, newSelectedFlags, 0, selectedFlags.length);
            selectedFlags = newSelectedFlags;
        }
        selectedFlags[selectedFlags.length - 1] = value;
        entry.setSelectedFlags(selectedFlags);
    }

}
