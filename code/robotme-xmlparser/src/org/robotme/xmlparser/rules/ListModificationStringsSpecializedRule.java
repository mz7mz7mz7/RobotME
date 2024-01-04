package org.robotme.xmlparser.rules;

import org.apache.commons.digester.Rule;
import org.robotme.core.log.entries.ListModificationLogEntry;

/**
 * @author Marcin Zduniak
 */
public class ListModificationStringsSpecializedRule  extends Rule {
    
    @Override
    public void body(String namespace, String name, String text) throws Exception {
        super.body(namespace, name, text);
        final String value = text;

        final ListModificationLogEntry entry = (ListModificationLogEntry) getDigester().peek(0);
        String[] strings = entry.getStrings();
        if (null == strings) {
            strings = new String[1];
        } else {
            final String[] newStrings = new String[strings.length + 1];
            System.arraycopy(strings, 0, newStrings, 0, strings.length);
            strings = newStrings;
        }
        strings[strings.length - 1] = value;
        entry.setStrings(strings);
    }
}
